package com.hoioy.diamond.security.sms;

import java.util.Map;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/4/6
 * @describe
 */
public interface SmsThirdSendService {

     Boolean send(Map<String, Object> data);
}
