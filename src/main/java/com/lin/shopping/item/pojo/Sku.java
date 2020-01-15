package com.lin.shopping.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "tb_sku")
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spuId;
    private String title;
    private String images;
    private Long price;
    private String ownSpec;//商品特殊规格键值对
    private String indexes;//商品特殊规格的下标
    private Boolean enable;//是否有效
    private Date createTime;
    private Date lastUpdateTime;
    @Transient
    private Integer stock;
}
