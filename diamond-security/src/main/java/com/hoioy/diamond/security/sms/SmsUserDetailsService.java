package com.hoioy.diamond.security.sms;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/16
 * @describe
 */
public interface SmsUserDetailsService {

    UserDetails loadByPhoneNum(String phoneNum);
}
