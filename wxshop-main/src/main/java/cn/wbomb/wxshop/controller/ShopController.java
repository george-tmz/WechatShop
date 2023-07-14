package cn.wbomb.wxshop.controller;

import cn.wbomb.api.data.PageResponse;
import cn.wbomb.wxshop.entity.Response;
import cn.wbomb.wxshop.generate.Shop;
import cn.wbomb.wxshop.service.ShopService;
import cn.wbomb.wxshop.service.UserContext;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public Response<Shop> createShop(@RequestBody Shop shop, HttpServletResponse response) {
        Response<Shop> ret = Response.of(shopService.createShop(shop, UserContext.getCurrentUser().getId()));
        response.setStatus(HttpStatus.CREATED.value());
        return ret;
    }

    @DeleteMapping("/{id}")
    public Response<Shop> deleteShop(@PathVariable("id") Long shopId) {
        return Response.of(shopService.deleteShop(shopId, UserContext.getCurrentUser().getId()));
    }

    @PatchMapping("/{id}")
    public Response<Shop> updateShop(@PathVariable("id") Long id,
                                     @RequestBody Shop shop) {
        shop.setId(id);
        return Response.of(shopService.updateShop(shop, UserContext.getCurrentUser().getId()));
    }

    @GetMapping("/shop")
    public PageResponse<Shop> getShop(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize) {
        return shopService.getShopByUserId(pageNum, pageSize);
    }


}
