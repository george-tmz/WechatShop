package cn.wbomb.wxshop.controller;

import cn.wbomb.api.data.PageResponse;
import cn.wbomb.wxshop.entity.Response;
import cn.wbomb.wxshop.generate.Goods;
import cn.wbomb.wxshop.service.GoodsService;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/goods")
public class GoodsController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping
    public Response<Goods> createdGoods(@RequestBody Goods goods, HttpServletResponse response) {
        clean(goods);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return Response.of(goodsService.createGoods(goods));
    }

    private void clean(Goods goods) {
        goods.setId(null);
        goods.setCreatedAt(new Date());
        goods.setUpdatedAt(new Date());
        goods.setStatus("ok");
    }

    @PatchMapping
    public Response<Goods> updateGoods(Goods goods) {
            return Response.of(goodsService.updateGoods(goods));
    }

    @DeleteMapping("/{id}")
    public Response<Goods> deleteGoods(@PathVariable("id") Long goodsId, HttpServletResponse response) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return Response.of(goodsService.deleteGoodsById(goodsId));
    }

    @GetMapping
    public PageResponse<Goods> getGoods(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam(value = "shopId", required = false) Integer shopId
    ) {
        return goodsService.getGoods(pageNum, pageSize, shopId);
    }
}
