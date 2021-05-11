package com.hoioy.diamond.security.sms;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.security.BaseAuthenticationToken;
import com.hoioy.diamond.security.BaseConstants;
import com.hoioy.diamond.security.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/16
 * @describe 验证码登陆的security 过滤器
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private SmsCodeHandler smsCodeHandler;
    private ResponseHandler responseHandle;


    public SmsAuthenticationFilter() {
        super(new OrRequestMatcher(new AntPathRequestMatcher("/smsSendCode", "GET"), new AntPathRequestMatcher("/smsLogin", "POST")));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        //发送验证码逻辑,return null后父类会直接返回验证码
        if (request.getMethod().equals("GET")) {
            //获取电话号码
            String phoneNum = request.getParameter(BaseConstants.PHONENUM);

            try {
                ResultDTO resultDTO = smsCodeHandler.sendSmsCode(phoneNum);
                responseHandle.success(request, response, resultDTO);
            } catch (Exception e) {
                responseHandle.fail(request, response, e);
            }
            return null;

        }
        //验证码登陆逻辑
        else {

            //返回的map中至少包含phoneNum,phoneCode,phoneCodeKey
            Map<String, String> body;
            try {
                  body = getBody(request);
            } catch (Exception e) {
                responseHandle.fail(request,response,new SmsSecurityException(5007,"请使用body传参"));
                return null;
            }


            Boolean check = checkParameter(request, response, body);
            if (!check) {
                return null;
            }
            String phoneNum = obtainPhoneNum(body);

            body.remove(BaseConstants.PHONENUM);

            BaseAuthenticationToken authRequest = new BaseAuthenticationToken(phoneNum, body);

            setDetails(request, authRequest);
            try {
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (Exception e) {
                responseHandle.fail(request, response, e);
                return null;
            }


        }


    }

    private Boolean checkParameter(HttpServletRequest request, HttpServletResponse response, Map<String, String> body) {

        if (!body.containsKey(BaseConstants.PHONECODE)
                || !body.containsKey(BaseConstants.PHONECODE_KEY)
                || !body.containsKey(BaseConstants.PHONENUM)) {

            String errorMsg = LogMessage.format("必须包含以下参数 phoneNum=%s , phoneCode=%s , phoneCodeKey=%s",
                    body.get(BaseConstants.PHONENUM),
                    body.get(BaseConstants.PHONECODE),
                    body.get(BaseConstants.PHONECODE_KEY)).toString();

            responseHandle.fail(request, response, new SmsSecurityException(5006,errorMsg));

            return false;
        }
        return true;
    }

    protected String obtainPhoneNum(Map<String, String> data) {
        String phoneNum = data.get(BaseConstants.PHONENUM);

        if (phoneNum == null) {
            phoneNum = "";
        }
        return phoneNum.trim();
    }


    protected void setDetails(HttpServletRequest request,
                              BaseAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    private Map<String, String> getBody(HttpServletRequest request) throws IOException {
        Map<String, String> data;
        BufferedReader reader = request.getReader();
        String str = "";
        String bodyStr = "";
        while ((str = reader.readLine()) != null) {
            bodyStr += str;
        }
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(bodyStr, Map.class);
        return data;
    }

    public SmsCodeHandler getSmsCodeHandler() {
        return smsCodeHandler;
    }

    public void setSmsCodeHandler(SmsCodeHandler smsCodeHandler) {
        this.smsCodeHandler = smsCodeHandler;
    }

    public ResponseHandler getResponseHandle() {
        return responseHandle;
    }

    public void setResponseHandle(ResponseHandler responseHandle) {
        this.responseHandle = responseHandle;
    }
}
