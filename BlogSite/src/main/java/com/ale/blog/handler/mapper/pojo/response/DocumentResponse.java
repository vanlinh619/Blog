package com.ale.blog.handler.mapper.pojo.response;

import com.ale.blog.entity.state.ShareState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
    private Long id;
    private String title;
    private String introduction;
    private ShareState state;
    private Instant createDate;
}
