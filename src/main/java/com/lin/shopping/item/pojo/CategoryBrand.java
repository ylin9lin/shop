package com.lin.shopping.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_category_brand")
public class CategoryBrand {

    private Long categoryId;

    private Long brandId;
}
