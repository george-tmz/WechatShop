package cn.wbomb.wxshop.service;

import cn.wbomb.wxshop.model.TelAndCode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TelVerificationServiceTest {
    public static final TelAndCode VALID_PARAMETER = new TelAndCode("18123419987", "123456");
    public static final TelAndCode EMPTY_PARAMETER = new TelAndCode(null, null);

    @Test
    public void returnTrueIfValid() {
        Assertions.assertTrue(
            new TelVerificationService().verifyTelParameter(VALID_PARAMETER)
        );
    }

    @Test
    public void returnFalseIfNoTel() {
        Assertions.assertFalse(
            new TelVerificationService().verifyTelParameter(EMPTY_PARAMETER)
        );
    }

    @Test
    public void returnFalseIfNull() {
        Assertions.assertFalse(
            new TelVerificationService().verifyTelParameter(null)
        );
    }
}
