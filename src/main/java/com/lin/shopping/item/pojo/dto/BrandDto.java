package com.lin.shopping.item.pojo.dto;

import lombok.Data;

@Data
public class BrandDto {
    private Long id;
    private String name; //品牌名称
    private String image;//品牌图片
    private Character letter; //Character  char的封装对象
    private Long parentId;
    private Boolean isParent;
    private Integer sort;
}
