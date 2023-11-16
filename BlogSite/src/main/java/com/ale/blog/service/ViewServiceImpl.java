package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.View;
import com.ale.blog.repository.ViewRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository viewRepository;
    private final PostService postService;

    @Async
    @Transactional(rollbackOn = {Exception.class})
    @Override
    public void increaseView(@Nullable User user, @Nonnull Post post, @Nonnull HttpServletRequest request) {
        String ipaddress = request.getRemoteAddr();
        Optional.ofNullable(user)
                .flatMap(us -> viewRepository.findFirstByPostAndUserOrderByTimestampDesc(post, us))
                .or(() -> viewRepository.findFirstByPostAndIpaddressOrderByTimestampDesc(post, ipaddress)
                        .map(view -> {
                            view.setUser(user);
                            viewRepository.save(view);
                            return view;
                        })
                )
                .ifPresentOrElse(view -> {
                    Duration duration = Duration.between(view.getTimestamp(), Instant.now());
                    if (duration.getSeconds() > 12 * 3600) {
                        increaseAndUpdateView(user, post, ipaddress);
                    }
                }, () -> {
                    increaseAndUpdateView(user, post, ipaddress);
                });
    }

    private void increaseAndUpdateView(@Nullable User user, @Nonnull Post post, @Nonnull String ipaddress) {
        View view = View.builder()
                .post(post)
                .timestamp(Instant.now())
                .user(user)
                .ipaddress(ipaddress)
                .build();
        viewRepository.save(view);
        post.setView(post.getView() + 1);
        postService.save(post);
    }

    @Override
    public Class<View> getEntityClass() {
        return View.class;
    }
}
