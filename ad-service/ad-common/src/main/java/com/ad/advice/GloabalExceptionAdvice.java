package com.ad.advice;

import com.ad.exception.AdException;
import com.ad.client.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 11:37
 */
@RestControllerAdvice
public class GloabalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request,
                                                     AdException exception) {
        CommonResponse<String> response = new CommonResponse<>(400,
                "business error");
        response.setData(exception.getMessage());
        return response;
    }
}
