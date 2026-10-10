package com.ntu.site.api.admin.view;

import com.ntu.common.exception.BusinessException;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.ntu.common.constant.AdminMessageKeyConstant.INVALID_INPUT;

/**
 * Shared helpers of the admin pages. Forms are submitted as full page posts, so every
 * submit ends with a redirect to the list page carrying a flash message (an i18n key).
 */
public abstract class BaseAdminViewController extends BaseViewController {

    protected static final String ADMIN_FRAGMENT_FILE = "general-admin";
    protected static final String ADD_NEW_PATH = "/add-new";
    protected static final String ACTION_URL = "actionUrl";
    protected static final String SUCCESS_MESSAGE = "successMessage";
    protected static final String ERROR_MESSAGE = "errorMessage";

    /**
     * Runs {@code action} when the submitted form is valid and redirects to {@code redirectUrl}
     * with a success message, or with an error message when the form is invalid or the action
     * breaks a business rule.
     */
    protected static String submit(BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   String redirectUrl,
                                   String successMessageKey,
                                   Runnable action) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, INVALID_INPUT);
            return redirect(redirectUrl);
        }
        return execute(redirectAttributes, redirectUrl, successMessageKey, action);
    }

    /**
     * Runs {@code action} and redirects to {@code redirectUrl} with a success message, or with
     * an error message when the action breaks a business rule.
     */
    protected static String execute(RedirectAttributes redirectAttributes,
                                    String redirectUrl,
                                    String successMessageKey,
                                    Runnable action) {
        try {
            action.run();
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, successMessageKey);
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessageKey());
        }
        return redirect(redirectUrl);
    }
}
