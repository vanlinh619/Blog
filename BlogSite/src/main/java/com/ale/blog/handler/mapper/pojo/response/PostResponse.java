package com.ale.blog.handler.mapper.pojo.response;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String slug;
    private String title;
    private String date;
    private Long view;
    private CategoryResponse category;
    private UserInfoResponse author;
}
