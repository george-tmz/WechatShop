package cn.wbomb.wxshop;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

import cn.wbomb.wxshop.entity.HttpResponse;
import cn.wbomb.wxshop.entity.Response;
import cn.wbomb.wxshop.generate.Goods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GoodsIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void testCreateGoods() throws JsonProcessingException {
        //登录
        String sessionId = loginAndGetCookie();

        Goods goods = new Goods();
        goods.setName("soap");
        goods.setDescription("This is a soap");
        goods.setDetails("This is a soap");
        goods.setImgUrl("https://img.url");
        goods.setPrice(1000L);
        goods.setStock(10);
        goods.setShopId(1L);

        HttpResponse response = doHttpRequest("/api/v1/goods", false, goods, sessionId);
        Response<Goods> responseData = objectMapper.readValue(response.getBody(), new TypeReference<Response<Goods>>() {
        });
        Assertions.assertEquals(SC_CREATED, response.getCode());
        Assertions.assertEquals(goods.getName(), responseData.getData().getName());
    }

    @Test
    public void testDeleteGoods() {

    }
}
