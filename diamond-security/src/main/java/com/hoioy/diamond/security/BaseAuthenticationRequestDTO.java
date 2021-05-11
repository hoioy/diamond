package com.hoioy.diamond.security;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAuthenticationRequestDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;
    private String captchaKey;
    private String captchaCode;

    /**
     * 从request中构造
     *
     * @param request           http请求
     * @param usernameParameter 表示用户名的字段名称
     * @param passwordParameter 表示密码的字段名称
     */
    public BaseAuthenticationRequestDTO(HttpServletRequest request, String usernameParameter, String passwordParameter) {
        //TODO 校验验证码逻辑 从请求中获取验证码参数
        //优先从parameter获取
        String username = request.getParameter(usernameParameter);
        if (StringUtils.isEmpty(username)) {
            //如果拿不到，以Json格式从body中获取
            String json = getPostData(request);

            Map<String, String> map = JSONUtil.toBean(json, Map.class);
            this.username = map.get(usernameParameter);
            this.password = map.get(passwordParameter);
            this.captchaKey = map.get("captchaKey");
            this.captchaCode = map.get("captchaCode");
        } else {
            this.username = request.getParameter(usernameParameter);
            this.password = request.getParameter(passwordParameter);
            this.captchaKey = request.getParameter("captchaKey");
            this.captchaCode = request.getParameter("captchaCode");
        }
    }

    private static String getPostData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
        } finally {
        }
        return data.toString();
    }
}
