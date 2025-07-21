package com.ntu.site.application.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.ntu.common.constant.UrlConstant.ERROR_URL;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalViewControllerAdvice {

    /**
     * Handle NoHandlerFoundException (404 Not Found)
     */
    @ExceptionHandler(value = {NoResourceFoundException.class, NoHandlerFoundException.class})
    public Object handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        String message = "Resource not found: " + path;
        log.error(message);

        return getErrorModelAndView(NOT_FOUND);
    }

    /**
     * Handle Exception (500 Not Found)
     */
    @ExceptionHandler(value = {Exception.class})
    public Object handleException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        String message = "Error at: " + path;
        log.error(message);

        return getErrorModelAndView(INTERNAL_SERVER_ERROR);
    }

    /**
     * Create ModelAndView for error pages
     */
    private ModelAndView getErrorModelAndView(HttpStatus status) {
        ModelAndView mav = new ModelAndView();

        // Set view name based on status code
        switch (status) {
            case BAD_REQUEST -> mav.setViewName("/error/400");
            case NOT_FOUND -> mav.setViewName("/error/404");
            default -> mav.setViewName("/error/500");
        }

        return mav;
    }
}
