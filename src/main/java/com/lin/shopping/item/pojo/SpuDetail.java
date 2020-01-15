package com.lin.shopping.item.pojo;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_spu_detail")
@Data
public class SpuDetail {
    @Id
    private Long spuId;
    private String description;//商品描述
    private String specialSpec; //特殊商品规格及名称及可选值模板
    private String genericSpec; //商品全局规格属性
    private String packingList;//包装清单
    private String afterService; //售后服务
}
