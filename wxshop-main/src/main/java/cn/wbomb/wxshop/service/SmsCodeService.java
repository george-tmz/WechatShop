package cn.wbomb.wxshop.service;

public interface SmsCodeService {
    /**
     * Send a code to the phone number.
     * @param tel phone number
     * @return result
     */
    String sendSmsCode(String tel);
}
