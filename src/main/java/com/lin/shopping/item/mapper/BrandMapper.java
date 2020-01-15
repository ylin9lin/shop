package com.lin.shopping.item.mapper;

import com.lin.shopping.item.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);
    List<Brand> seleceBrandsByCid(Long cid);

    void deleteCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}
