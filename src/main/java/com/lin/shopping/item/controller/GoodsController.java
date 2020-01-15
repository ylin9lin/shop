package com.lin.shopping.item.controller;

import com.lin.shopping.commom.pojo.PageResult;
import com.lin.shopping.item.pojo.Sku;
import com.lin.shopping.item.pojo.SpuDetail;
import com.lin.shopping.item.pojo.dto.SpuDto;
import com.lin.shopping.item.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@Controller
@Api(tags = "商品")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @ApiOperation("根据条件分页查询spu")
    @GetMapping("/api/item/spu/page")
    public ResponseEntity<PageResult<SpuDto>> querySpuByPage(@RequestParam(value = "key",required = false)String key,
                                                             @RequestParam(value = "saleable",required = false)Boolean saleable,
                                                             @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                             @RequestParam(value = "rows",defaultValue = "5")Integer rows){
       PageResult<SpuDto> result =  goodsService.querySpuByPage(key,saleable,page,rows);
       if(result == null || CollectionUtils.isEmpty(result.getItems())){
           return ResponseEntity.notFound().build();
       }
       return  ResponseEntity.ok(result);
    }

    @ApiOperation("新增商品")
    @PostMapping("/api/item/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuDto spuDto){
        goodsService.saveGoods(spuDto);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("根据spuId查询spuDetail")
    @GetMapping("api/item/spu/detail/{spuId}")
    public ResponseEntity<SpuDetail>querySpuDetailBySpuId(@PathVariable("spuId") Long spuId){
       SpuDetail spuDetail = goodsService.querySpuDetailBySpuId(spuId);
       if(spuDetail == null){
           return  ResponseEntity.notFound().build();
       }
        return  ResponseEntity.ok(spuDetail);
    }

    @ApiOperation("根据spuId查询sku集合")
    @GetMapping("api/item/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("spuId") Long spuId){
       List<Sku>  skus = goodsService.querySkuBySpuId(spuId);
       if(CollectionUtils.isEmpty(skus)){
           return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(skus);
    }

    @ApiOperation("更新商品信息")
    @PutMapping("api/item/goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuDto spuDto){
        goodsService.updateGoods(spuDto);
        return ResponseEntity.noContent().build(); //204表示删除或者更新成功
    }
}
