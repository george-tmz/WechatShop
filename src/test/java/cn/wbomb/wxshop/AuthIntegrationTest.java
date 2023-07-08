package cn.wbomb.wxshop;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

import cn.wbomb.wxshop.entity.HttpResponse;
import cn.wbomb.wxshop.entity.LoginResponse;
import cn.wbomb.wxshop.service.TelVerificationServiceTest;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
public class AuthIntegrationTest {
    @Autowired
    Environment environment;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private HttpResponse doHttpRequest(String apiName, boolean isGet, Object requestBody, String cookie)
        throws JsonProcessingException {
        HttpRequest httpRequest = isGet ? HttpRequest.get(getUrl(apiName)) : HttpRequest.post(getUrl(apiName));
        if (cookie != null) {
            httpRequest.header("Cookie", cookie);
        }
        httpRequest.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
        if (requestBody != null) {
            httpRequest.send(objectMapper.writeValueAsString(requestBody));
        }
        return HttpResponse.builder().code(httpRequest.code()).body(httpRequest.body()).headers(httpRequest.headers())
            .build();
    }

    @Test
    public void loginLogoutTest() throws JsonProcessingException {
        // 最开始默认情况下，访问/api/status/ 处于未登录状态
        // 发送验证码
        // 带着验证码进行登录，等到cookie
        // 带着cookie访问/api/status 应用处于登录状态
        // 调用 logout
        // 带着cookie访问/api/status 应用处于登录状态
        HttpResponse httpRequest = doHttpRequest("/api/status", true, null, null);
        LoginResponse response = objectMapper.readValue(httpRequest.getBody(), LoginResponse.class);
        Assertions.assertFalse(response.isLogin());

        httpRequest = doHttpRequest("/api/code", false,
            TelVerificationServiceTest.VALID_PARAMETER, null);
        Assertions.assertEquals(HTTP_OK, httpRequest.getCode());

        httpRequest = doHttpRequest("/api/login", false,
            TelVerificationServiceTest.VALID_PARAMETER_CODE, null);
        List<String> setCookie = httpRequest.getHeaders().get("Set-Cookie");
        String sessionId = getSessionIdFromSetCookie(
            setCookie.stream().filter(cookie -> cookie.contains("JSESSIONID")).findFirst().get());

        httpRequest = doHttpRequest("/api/status", true, null, sessionId);

        LoginResponse loginResponse = objectMapper.readValue(httpRequest.getBody(), LoginResponse.class);
        Assertions.assertTrue(loginResponse.isLogin());
        Assertions.assertEquals(TelVerificationServiceTest.VALID_PARAMETER.getTel(), loginResponse.getUser().getTel());

        doHttpRequest("/api/logout", false, null, sessionId);

        httpRequest = doHttpRequest("/api/status", true, null, sessionId);
        loginResponse = objectMapper.readValue(httpRequest.getBody(), LoginResponse.class);
        Assertions.assertFalse(loginResponse.isLogin());
    }

    private String getSessionIdFromSetCookie(String setCookie) {
        int semiColonIndex = setCookie.indexOf(";");

        return setCookie.substring(0, semiColonIndex);
    }

    @Test
    public void returnHttpOkWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/code")).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .send(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_PARAMETER)).code();
        Assertions.assertEquals(HTTP_OK, responseCode);
    }

    @Test
    public void returnHttpBadRequestWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/code")).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .send(objectMapper.writeValueAsString(TelVerificationServiceTest.EMPTY_PARAMETER)).code();
        Assertions.assertEquals(HTTP_BAD_REQUEST, responseCode);
    }

    private String getUrl(String apiName) {
        // 获取集成测试的端口号
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }
}
