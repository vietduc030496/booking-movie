package com.ntu.api.view;

public abstract class BaseViewController {

    public static String redirect(String url) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        return "redirect:/" + url;
    }
}
