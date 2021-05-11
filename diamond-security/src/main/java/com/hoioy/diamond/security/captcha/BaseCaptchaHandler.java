package com.hoioy.diamond.security.captcha;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.security.BaseAuthenticationRequestDTO;

public interface BaseCaptchaHandler {

    ResultDTO createCaptcha(String clientIp);

    Boolean checkCaptcha(BaseAuthenticationRequestDTO authenticationRequest);
}
