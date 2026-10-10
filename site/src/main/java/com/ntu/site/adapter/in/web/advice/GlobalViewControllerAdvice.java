package com.ntu.site.adapter.in.web.advice;

import com.ntu.common.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalViewControllerAdvice {

    /**
     * Handle unknown URLs and missing entities (404 Not Found)
     */
    @ExceptionHandler(value = {NoResourceFoundException.class, NoHandlerFoundException.class, ResourceNotFoundException.class})
    public ModelAndView handleNotFoundException(Exception ex, HttpServletRequest request) {
        log.error("Resource not found: {} ({})", request.getRequestURI(), ex.getMessage());
        return getErrorModelAndView(NOT_FOUND);
    }

    /**
     * Handle Exception (500 Internal Server Error)
     */
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        log.error("Error at: {}", request.getRequestURI(), ex);
        return getErrorModelAndView(INTERNAL_SERVER_ERROR);
    }

    /**
     * Create ModelAndView for error pages
     */
    private ModelAndView getErrorModelAndView(HttpStatus status) {
        ModelAndView mav = new ModelAndView();
        mav.setStatus(status);
        // Set view name based on status code
        switch (status) {
            case BAD_REQUEST -> mav.setViewName("/error/400");
            case NOT_FOUND -> mav.setViewName("/error/404");
            default -> mav.setViewName("/error/500");
        }
        return mav;
    }
}
