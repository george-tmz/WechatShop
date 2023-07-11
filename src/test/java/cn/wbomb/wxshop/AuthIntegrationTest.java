package cn.wbomb.wxshop;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

import cn.wbomb.wxshop.entity.LoginResponse;
import cn.wbomb.wxshop.service.TelVerificationServiceTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
public class AuthIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void loginLogoutTest() throws JsonProcessingException {
        // 最开始默认情况下，访问/api/status/ 处于未登录状态
        // 发送验证码
        // 带着验证码进行登录，等到cookie
        // 带着cookie访问/api/status 应用处于登录状态
        // 调用 logout
        // 带着cookie访问/api/status 应用处于登录状态

        String sessionId = loginAndGetCookie().cookie;
        HttpResponse httpRequest = doHttpRequest("/api/v1/status", "GET", null, sessionId);

        LoginResponse loginResponse = objectMapper.readValue(httpRequest.body, LoginResponse.class);
        Assertions.assertTrue(loginResponse.isLogin());
        Assertions.assertEquals(TelVerificationServiceTest.VALID_PARAMETER.getTel(), loginResponse.getUser().getTel());

        doHttpRequest("/api/v1/logout", "POST", null, sessionId);

        httpRequest = doHttpRequest("/api/v1/status", "GET", null, sessionId);
        loginResponse = objectMapper.readValue(httpRequest.body, LoginResponse.class);
        Assertions.assertFalse(loginResponse.isLogin());
    }

    @Test
    public void returnHttpOkWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/v1/code")).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .send(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_PARAMETER)).code();
        Assertions.assertEquals(HTTP_OK, responseCode);
    }

    @Test
    public void returnHttpBadRequestWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/v1/code")).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .send(objectMapper.writeValueAsString(TelVerificationServiceTest.EMPTY_PARAMETER)).code();
        Assertions.assertEquals(HTTP_BAD_REQUEST, responseCode);
    }
}
