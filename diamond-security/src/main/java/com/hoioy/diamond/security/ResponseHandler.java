package com.hoioy.diamond.security;

import com.hoioy.diamond.common.dto.ResultDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/4/2
 * @describe
 */
public interface ResponseHandler {

    public void fail(HttpServletRequest request, HttpServletResponse response, Exception e);

    public void success(HttpServletRequest request, HttpServletResponse response, ResultDTO resultDTO);
}
