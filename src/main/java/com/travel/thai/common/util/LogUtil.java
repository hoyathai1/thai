package com.travel.thai.common.util;

import com.travel.thai.user.domain.User;

import javax.servlet.http.HttpServletRequest;

public class LogUtil {
    public static String setUserInfo(HttpServletRequest request, User user) {
        StringBuilder sb = new StringBuilder();

        if (user != null) {
            sb.append("[" + user.getUserId() + "]");
        } else {
            sb.append("[" + request.getRemoteAddr() + "]");
        }

        return sb.toString();
    }

    
}
