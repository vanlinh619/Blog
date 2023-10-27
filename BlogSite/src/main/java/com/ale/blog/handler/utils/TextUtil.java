package com.ale.blog.handler.utils;

import com.ale.blog.entity.User;
import org.apache.logging.log4j.util.StringBuilders;

public class TextUtil {
    public static final String HOME = "Trang chủ";
    public static final String DOCUMENT = "Tài liệu";
    public static final String ALL_DOCUMENT = "Tất cả tài liệu";
    public static final String ALL_CATEGORY = "Tất cả bài viết";


    public static String getLinkAllDocument(User author) {
        return "/doc/" + author.getUsername() + "?scope=public&page=1";
    }
}
