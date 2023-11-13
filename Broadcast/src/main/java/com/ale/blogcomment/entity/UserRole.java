package com.ale.blogcomment.entity;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserRole {
    @FieldNameConstants.Include ADMIN,
    @FieldNameConstants.Include CONTENT_CREATOR,
    @FieldNameConstants.Include USER
}
