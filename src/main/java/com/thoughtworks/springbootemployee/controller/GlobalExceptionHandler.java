package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.ResultBean;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultBean<Boolean> notFoundException(NotFoundException e) {
        return ResultBean.error(0, e.getMessage());
    }
}
