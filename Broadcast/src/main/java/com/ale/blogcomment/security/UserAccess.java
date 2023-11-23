package com.ale.blogcomment.security;


import com.ale.blog.entity.User;

import java.io.Serializable;

public interface UserAccess extends Serializable {
    User getUser();
}
