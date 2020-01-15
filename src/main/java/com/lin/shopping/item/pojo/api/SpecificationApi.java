package com.lin.shopping.item.pojo.api;

import com.lin.shopping.item.pojo.SpecGroup;
import com.lin.shopping.item.pojo.SpecParam;
import com.lin.shopping.item.service.SpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/item/spec")
@Api(tags = "规格")
public interface SpecificationApi {


    @ApiOperation("根据条件查询规格参数")
    @GetMapping("/params")
    public List<SpecParam> queryParams(@RequestParam(value = "gid",required = false) Long gid,
                                                       @RequestParam(value = "cid",required = false) Long cid,
                                                       @RequestParam(value = "generic",required = false) Boolean generic,
                                                       @RequestParam(value = "searching",required = false) Boolean searching);


}
