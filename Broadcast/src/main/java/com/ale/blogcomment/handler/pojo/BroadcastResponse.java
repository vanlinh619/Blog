package com.ale.blogcomment.handler.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastResponse {
    private String userId;
    private BroadcastType type;
    private Object payload;
}
