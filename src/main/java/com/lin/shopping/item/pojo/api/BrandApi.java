package com.lin.shopping.item.pojo.api;

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

@RequestMapping("/api/item/brand")
public interface BrandApi {

    @ApiOperation("根据id查询产品信息")
    @GetMapping("/id/{id}")
    public List<Brand> queryBrandById(@PathVariable("id") Long id);

}
