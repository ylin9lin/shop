package com.lin.shopping.item.mapper;

import com.lin.shopping.item.pojo.Brand;
import com.lin.shopping.item.pojo.CategoryBrand;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryBrandMapper extends Mapper<CategoryBrand> {
    void deletecategoryBrand(Long id);

    void updatecategoryBrand(Long id);
}
