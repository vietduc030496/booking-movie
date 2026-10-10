package com.ntu.site.adapter.in.web.customer.view;

public abstract class BaseViewController {

    protected BaseViewController() {}

    private static final String FRAGMENT_COMPONENT = "fragments/%s :: %s";

    public static String redirect(String url) {
        String path = url.startsWith("/") ? url.substring(1) : url;
        return "redirect:/" + path;
    }

    public static String fragment(String fragmentFile, String fragmentName) {
        return String.format(FRAGMENT_COMPONENT, fragmentFile, fragmentName);
    }
}
