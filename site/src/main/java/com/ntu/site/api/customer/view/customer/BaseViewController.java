package com.ntu.site.api.customer.view.customer;

public abstract class BaseViewController {

    private static final String FRAGMENT_COMPONENT = "fragments/%s :: %s";

    public static String redirect(String url) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        return "redirect:/" + url;
    }

    public static String fragment(String fragmentFile, String fragmentName) {
        return String.format(FRAGMENT_COMPONENT, fragmentFile, fragmentName);
    }
}
