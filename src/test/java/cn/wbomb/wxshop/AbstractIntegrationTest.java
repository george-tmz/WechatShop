package cn.wbomb.wxshop;

import static java.net.HttpURLConnection.HTTP_OK;

import cn.wbomb.wxshop.entity.HttpResponse;
import cn.wbomb.wxshop.entity.LoginResponse;
import cn.wbomb.wxshop.service.TelVerificationServiceTest;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

@SpringBootTest
public class AbstractIntegrationTest {
    @Autowired
    Environment environment;
    public final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据库
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(databaseUrl, databaseUsername, databasePassword);
        Flyway flyway = new Flyway(conf);
        flyway.clean();
        flyway.migrate();
    }

    public HttpResponse doHttpRequest(String apiName, boolean isGet, Object requestBody, String cookie)
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

    public String getUrl(String apiName) {
        // 获取集成测试的端口号
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    public String loginAndGetCookie() throws JsonProcessingException {
        HttpResponse httpRequest = doHttpRequest("/api/status", true, null, null);
        LoginResponse response = objectMapper.readValue(httpRequest.getBody(), LoginResponse.class);
        Assertions.assertFalse(response.isLogin());

        httpRequest = doHttpRequest("/api/code", false,
            TelVerificationServiceTest.VALID_PARAMETER, null);
        Assertions.assertEquals(HTTP_OK, httpRequest.getCode());

        httpRequest = doHttpRequest("/api/login", false,
            TelVerificationServiceTest.VALID_PARAMETER_CODE, null);
        List<String> setCookie = httpRequest.getHeaders().get("Set-Cookie");
        return getSessionIdFromSetCookie(
            setCookie.stream().filter(cookie -> cookie.contains("JSESSIONID")).findFirst().get());
    }

    protected String getSessionIdFromSetCookie(String setCookie) {
        int semiColonIndex = setCookie.indexOf(";");

        return setCookie.substring(0, semiColonIndex);
    }
}
