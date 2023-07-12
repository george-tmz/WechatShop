package cn.wbomb.wxshop.service;

import cn.wbomb.wxshop.entity.TelAndCode;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class TelVerificationService {

    private static final Pattern TEL_PATTERN = Pattern.compile("1\\d{10}");

    /**
     * Verify the phone number by user import legal.
     * the tel param must is Chinese phone number.
     *
     * @param param request parameter
     * @return verification result
     */
    public boolean verifyTelParameter(TelAndCode param) {
        if (param == null) {
            return false;
        } else if (param.getTel() == null) {
            return false;
        } else {
            return TEL_PATTERN.matcher(param.getTel()).find();
        }
    }
}
