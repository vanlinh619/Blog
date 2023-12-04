package com.ale.blog.handler.mapper.pojo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
    private MultipartFile avatar;

    @NotBlank
    private String fullName;

    private String story;
}
