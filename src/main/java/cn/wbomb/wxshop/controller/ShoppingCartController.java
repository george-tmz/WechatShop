package cn.wbomb.wxshop.controller;

import cn.wbomb.wxshop.entity.PageResponse;
import cn.wbomb.wxshop.entity.Response;
import cn.wbomb.wxshop.entity.ShoppingCartData;
import cn.wbomb.wxshop.exception.HttpException;
import cn.wbomb.wxshop.service.ShoppingCartService;
import cn.wbomb.wxshop.service.UserContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shoppingCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public Response<ShoppingCartData> addToShoppingCart(@RequestBody AddToShoppingCartRequest request) {
        try {
            return Response.of(shoppingCartService.addToShoppingCart(request, UserContext.getCurrentUser().getId()));
        } catch (HttpException e) {
            return Response.of(e.getMessage(), null);
        }
    }

    @GetMapping
    public PageResponse<ShoppingCartData> getShoppingCart(@RequestParam("pageNum") int pageNum,
                                                          @RequestParam("pageSize") int pageSize) {
        return shoppingCartService.getShoppingCartOfUser(UserContext.getCurrentUser().getId(), pageNum, pageSize);
    }

    public static class AddToShoppingCartRequest {
        List<AddToShoppingCartItem> goods;

        public List<AddToShoppingCartItem> getGoods() {
            return goods;
        }

        public void setGoods(List<AddToShoppingCartItem> goods) {
            this.goods = goods;
        }
    }

    public static class AddToShoppingCartItem {
        long id;
        int number;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    @DeleteMapping("/{id}")
    public Response<ShoppingCartData> deleteGoodsInShoppingCart(@PathVariable("id") Long goodsId) {
        try {
            return Response.of(shoppingCartService.deleteGoodsInShoppingCart(goodsId, UserContext.getCurrentUser().getId()));
        } catch (HttpException e) {
            return Response.of(e.getMessage(), null);
        }
    }
}
