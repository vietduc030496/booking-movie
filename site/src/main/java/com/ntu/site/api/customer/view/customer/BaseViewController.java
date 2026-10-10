package com.ntu.site.api.customer.view.customer;

public abstract class BaseViewController {

    private BaseViewController() {}

    private static final String FRAGMENT_COMPONENT = "fragments/%s :: %s";

    public static String redirect(String url) {
        String path = url.startsWith("/") ? url.substring(1) : url;
        return "redirect:/" + path;
    }

    public static String fragment(String fragmentFile, String fragmentName) {
        return String.format(FRAGMENT_COMPONENT, fragmentFile, fragmentName);
    }
}
