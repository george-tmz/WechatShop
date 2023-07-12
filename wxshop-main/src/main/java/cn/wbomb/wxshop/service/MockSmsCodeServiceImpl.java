package cn.wbomb.wxshop.service;

import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class MockSmsCodeServiceImpl implements SmsCodeService{
    @Override
    public String sendSmsCode(String tel) {
        return "123456";
    }
}
