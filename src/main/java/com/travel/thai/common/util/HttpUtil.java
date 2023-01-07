package com.travel.thai.common.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    public static String convertHtmlStr(String str) {
        return str
//                .replaceAll("#", "&#35;")
//                .replaceAll("&", "&#38;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\\*", "&#42;")
                .replaceAll("\\)", "&#41;")
                .replaceAll("\\(", "&#40;")
                .replaceAll("'", "&#39;")
                .replaceAll("%", "&#37;")
                .replaceAll("\\\\", "&#34;")
                .replaceAll("!", "&#33;")
                ;
    }

    public static String getDevice(HttpServletRequest req) {
        String userAgent = req.getHeader("USER-AGENT");
        userAgent = userAgent.toLowerCase();
        String os = "";

        if (userAgent.indexOf("windows nt 10.0") > -1) {
            os = "Windows";
        } else if (userAgent.indexOf("windows nt 6.1") > -1) {
            os = "Windows";
        } else if (userAgent.indexOf("windows nt 6.2") > -1 || userAgent.indexOf("windows nt 6.3") > -1 ) {
            os = "Windows";
        } else if (userAgent.indexOf("iphone") > -1) {
            os = "iPhone";
        } else if (userAgent.indexOf("ipad") > -1) {
            os = "iPad";
        } else if (userAgent.indexOf("android") > -1) {
            os = "android";
        } else if (userAgent.indexOf("mac") > -1) {
            os = "mac";
        } else if (userAgent.indexOf("linux") > -1) {
            os = "Linux";
        } else {
            os = "Other";
        }
        return os;
    }

    public static String getBrowserInfo(HttpServletRequest req) {
        String userAgent = req.getHeader("USER-AGENT");
        String browser = "";

        if (userAgent.indexOf("Trident/7.0") > -1) {
            browser = "ie11";
        } else if (userAgent.indexOf("MSIE 10") > -1) {
            browser = "ie10";
        } else if (userAgent.indexOf("MSIE 9") > -1) {
            browser = "ie9";
        } else if (userAgent.indexOf("MSIE 8") > -1) {
            browser = "ie8";
        } else if(userAgent.indexOf("Edge") > -1) {											// Edge
            browser = "edge";
        } else if(userAgent.indexOf("Whale") > -1) { 										// Naver Whale
            browser = "whale";
        } else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { 		// Opera
            browser = "opera";
        } else if (userAgent.indexOf("Chrome") > -1) {
            browser = "Chrome";
        } else if (userAgent.indexOf("Chrome") == -1 && userAgent.indexOf("Safari") > -1) {
            browser = "Safari";
        } else if (userAgent.indexOf("Firefox") > -1) {
            browser = "Firefox";
        } else {
            browser ="Other";
        }

        return browser;
    }

}
