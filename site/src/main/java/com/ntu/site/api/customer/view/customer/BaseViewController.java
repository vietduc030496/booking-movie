package com.ntu.site.api.customer.view.customer;

public abstract class BaseViewController {

    public static String redirect(String url) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        return "redirect:/" + url;
    }
}
