package com.ntu.common.exception;

/**
 * Thrown when a request breaks a business rule. {@code messageKey} is an i18n key
 * that the view layer can show to the user.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String messageKey;

    public BusinessException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
