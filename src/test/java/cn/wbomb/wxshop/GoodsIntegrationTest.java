package cn.wbomb.wxshop;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

import cn.wbomb.wxshop.entity.HttpResponse;
import cn.wbomb.wxshop.entity.Response;
import cn.wbomb.wxshop.generate.Goods;
import cn.wbomb.wxshop.generate.Shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
public class GoodsIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void testCreateGoods() throws JsonProcessingException {
        //登录
        UserLoginResponse loginResponse = loginAndGetCookie();

        Shop shop = new Shop();
        shop.setName("我的微信店铺");
        shop.setDescription("我的小店开张啦");
        shop.setImgUrl("http://shopUrl");

        HttpResponse shopResponse = doHttpRequest(
            "/api/v1/shop",
            "POST",
            shop,
            loginResponse.cookie);
        Response<Shop> shopInResponse =
            objectMapper.readValue(shopResponse.getBody(), new TypeReference<Response<Shop>>() {
            });

        Assertions.assertEquals(SC_CREATED, shopResponse.getCode());
        Assertions.assertEquals("我的微信店铺", shopInResponse.getData().getName());
        Assertions.assertEquals("我的小店开张啦", shopInResponse.getData().getDescription());
        Assertions.assertEquals("http://shopUrl", shopInResponse.getData().getImgUrl());
        Assertions.assertEquals("ok", shopInResponse.getData().getStatus());
        Assertions.assertEquals(shopInResponse.getData().getOwnerUserId(), loginResponse.user.getId());

        Goods goods = new Goods();
        goods.setName("soap");
        goods.setDescription("This is a soap");
        goods.setDetails("This is a soap");
        goods.setImgUrl("https://img.url");
        goods.setPrice(1000L);
        goods.setStock(10);
        goods.setShopId(1L);

        HttpResponse response = doHttpRequest("/api/v1/goods", "POST", goods, loginResponse.cookie);
        Response<Goods> responseData = objectMapper.readValue(response.getBody(), new TypeReference<Response<Goods>>() {
        });
        Assertions.assertEquals(SC_CREATED, response.getCode());
        Assertions.assertEquals(goods.getName(), responseData.getData().getName());
    }

    @Test
    public void return404IfGoodsToDeleteNotExist() throws JsonProcessingException {
        String cookie = loginAndGetCookie().cookie;
        HttpResponse response = doHttpRequest(
            "/api/v1/goods/12345678",
            "DELETE",
            null,
            cookie);
        Assertions.assertEquals(SC_NOT_FOUND, response.getCode());
    }
}
