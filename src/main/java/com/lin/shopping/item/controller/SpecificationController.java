package com.lin.shopping.item.controller;

import com.lin.shopping.item.pojo.SpecGroup;
import com.lin.shopping.item.pojo.SpecParam;
import com.lin.shopping.item.service.SpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/item/spec")
@Api(tags = "规格")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @ApiOperation("根据分类id查询参数组")
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid){
      List<SpecGroup> groups = specificationService.queryGroupByCid(cid);
      if(CollectionUtils.isEmpty(groups)){
          return ResponseEntity.notFound().build();
      }
        return ResponseEntity.ok(groups);
    }

    @ApiOperation("根据条件查询规格参数")
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> queryParams(@RequestParam(value = "gid",required = false) Long gid,
                                                       @RequestParam(value = "cid",required = false) Long cid,
                                                       @RequestParam(value = "generic",required = false) Boolean generic,
                                                       @RequestParam(value = "searching",required = false) Boolean searching){
       List<SpecParam> params = specificationService.queryParams(gid,cid,generic,searching);
       if(CollectionUtils.isEmpty(params)){
           return ResponseEntity.notFound().build();
       }
        return  ResponseEntity.ok(params);
    }

}
