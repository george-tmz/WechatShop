package cn.wbomb.wxshop.controller;


import cn.wbomb.wxshop.entity.Response;
import cn.wbomb.api.exception.HttpException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorHandlingController {
    @ExceptionHandler(HttpException.class)
    public @ResponseBody
    Response<?> onError(HttpServletResponse response, HttpException e) {
        response.setStatus(e.getStatusCode());
        return Response.of(e.getMessage(), null);
    }
}
