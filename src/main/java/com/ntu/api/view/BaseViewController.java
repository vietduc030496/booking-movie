package com.ntu.api.view;

public abstract class BaseViewController {

    public static String redirect(String url) {
        return "redirect:/" + url;
    }
}
