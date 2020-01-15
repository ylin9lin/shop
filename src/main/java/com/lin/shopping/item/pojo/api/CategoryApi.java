package com.lin.shopping.item.pojo.api;
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

@RequestMapping("/api/item/category")
@Api(tags ="类别")
public interface CategoryApi {
    @GetMapping
    public List<String> queryNameByIds(@RequestParam("ids") Long ids);
}
