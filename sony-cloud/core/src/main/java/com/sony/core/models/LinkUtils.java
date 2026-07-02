package com.sony.core.models;

public class LinkUtils {

    public static String addHtmlExtension(String path) {

        if (path == null || path.isEmpty()) {
            return path;
        }


        if (path.startsWith("http://") || path.startsWith("https://")) {
            return path;
        }


        if (path.endsWith(".html")) {
            return path;
        }


        if (path.startsWith("/content")) {
            return path + ".html";
        }

        return path;
    }
}