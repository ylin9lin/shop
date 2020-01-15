package com.lin.shopping.item.pojo.api;

import com.lin.shopping.commom.pojo.PageResult;
import com.lin.shopping.item.pojo.Sku;
import com.lin.shopping.item.pojo.SpuDetail;
import com.lin.shopping.item.pojo.dto.SpuDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GoodsApi {

    /**
     * 根据spuId查询spuDetail
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    public SpuDetail queryDetailBySpuId(@PathVariable("spuId") Long spuId);

    @ApiOperation("根据条件分页查询spu")
    @GetMapping("/api/item/spu/page")
    public PageResult<SpuDto> querySpuByPage(@RequestParam(value = "key",required = false)String key,
                                                             @RequestParam(value = "saleable",required = false)Boolean saleable,
                                                             @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                             @RequestParam(value = "rows",defaultValue = "5")Integer rows);
    @ApiOperation("根据spuId查询spuDetail")
    @GetMapping("api/item/spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    @ApiOperation("根据spuId查询sku集合")
    @GetMapping("api/item/sku/list")
    public List<Sku> querySkuBySpuId(@RequestParam("spuId") Long spuId);
}
