package com.ale.blog.security;

import com.ale.blog.entity.User;

import java.io.Serializable;

public interface UserAccess extends Serializable {
    User getCurrentUser();
}
