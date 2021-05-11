package com.hoioy.diamond.security;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/29
 * @describe 用于security过滤器链中 response 封装
 */
public class BaseDefaultSecurityResponseHandler implements ResponseHandler {
    protected final Log logger = LogFactory.getLog(getClass());


    @SneakyThrows
    @Override
    public void fail(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        Integer code = 500;
        if (e instanceof BaseException) {
            code = ((BaseException) e).getCode();
            resultDTO.setMessage(e.getMessage());
        }
        response.setStatus(500);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        resultDTO.setCode(code);
        resultDTO.setErrorMessage(e.getMessage());
        String resultToken = new ObjectMapper().writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);
    }

    @Override
    @SneakyThrows
    public void success(HttpServletRequest request, HttpServletResponse response, ResultDTO resultDTO) {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String resultToken = new ObjectMapper().writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);
    }
}
