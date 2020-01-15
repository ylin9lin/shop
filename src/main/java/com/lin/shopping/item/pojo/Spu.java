package com.lin.shopping.item.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name ="tb_spu")
@Data
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long brandId;
    private Long cid1; //1级类目
    private Long cid2; //2级类目
    private Long cid3;//3级类目
    private String title; //标题
    private String subTitle; //子标题
    private Boolean saleable; //是否上架
    private Boolean vailds; //是否有效
    private Date createTime;
    private Date lastUpdateTime;
}
