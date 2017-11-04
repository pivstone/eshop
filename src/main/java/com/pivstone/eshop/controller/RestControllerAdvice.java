package com.pivstone.eshop.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
@ControllerAdvice
public class RestControllerAdvice {
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
