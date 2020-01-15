package com.lin.shopping.item.controller;
import com.lin.shopping.item.pojo.Category;
import com.lin.shopping.item.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item/category")
@Api(tags ="类别")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取类别列表")
    @GetMapping("/list")
    public ResponseEntity<List<Category>> aueryCategoryByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid){
        if(pid == null && pid < 0){
            return ResponseEntity.badRequest().build();
        }
      List<Category> categories = categoryService.aueryCategoryByPid(pid);
        if(CollectionUtils.isEmpty(categories)){
            ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(categories);
    }

    @ApiOperation("索引相关")
    @GetMapping
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") Long ids){
        List<String> names =categoryService.queryNameByIds(ids);
        if(CollectionUtils.isEmpty(names)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    @ApiOperation("添加分类信息")
    @PostMapping
    public ResponseEntity<Void>saveCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("删除分类信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteCategory(@PathVariable("id") Long id){
      int result = categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("更新分类信息")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable Long id,@RequestBody Category category){
       categoryService.updateCategoryById(id,category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
