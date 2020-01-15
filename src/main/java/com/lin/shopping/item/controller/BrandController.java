package com.lin.shopping.item.controller;

import com.lin.shopping.commom.pojo.PageResult;
import com.lin.shopping.item.pojo.Brand;
import com.lin.shopping.item.pojo.dto.BrandDto;
import com.lin.shopping.item.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/item/brand")
@Api(tags = "品牌")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @ApiOperation("根据查询条件分页并排序查询品牌信息")
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(@RequestParam(value = "key",required = false)String key,
                                                               @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                               @RequestParam(value = "rows",defaultValue = "5")Integer rows,
                                                               @RequestParam(value = "sortBy",required = false)String sortBy,
                                                               @RequestParam(value = "desc",required = false)Boolean desc
    ){
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key,page,rows,sortBy,desc);
        if(CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("添加品牌信息")
    @PostMapping
    public ResponseEntity<Void>saveBrand(Brand brand,@RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("根据id查询产品信息")
    @GetMapping("/id/{id}")
    public ResponseEntity<List<Brand>> queryBrandById(@PathVariable("id") Long id){
        List<Brand> brands = brandService.queryBrandById(id);
        if(CollectionUtils.isEmpty(brands)){
          return ResponseEntity.notFound().build();
      }
        return ResponseEntity.ok(brands);
    }

    @ApiOperation("删除品牌信息")
    @DeleteMapping
    public ResponseEntity<Void>deleteBrand(@RequestParam("id") Long id){
       brandService.deleteBrand(id);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("更新品牌信息")
    @PutMapping
    public ResponseEntity<BrandDto>updateBrand(@RequestBody BrandDto brandDto){
        brandService.updateBrand(brandDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @ApiOperation("图片上传")
    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
      String url =  brandService.uploadImage(file);
      if(StringUtils.isBlank(url)){
          return ResponseEntity.badRequest().build();
      }
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }

    @ApiOperation("根据分类id查询品牌列表")
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid){
       List<Brand> brands = brandService.queryBrandsByCid(cid);
       if(CollectionUtils.isEmpty(brands)){
           return ResponseEntity.notFound().build();
       }
        return  ResponseEntity.ok(brands);
    }

    @ApiOperation("索引相关")
    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryBrandByIds(@PathParam("ids") Long ids){
       Brand brand = brandService.queryBrandByIds(ids);
       if(brand == null){
           return ResponseEntity.notFound().build();
       }
        return  ResponseEntity.ok(brand);
    }
}
