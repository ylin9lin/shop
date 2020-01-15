package com.lin.shopping.item.pojo.dto;

import com.lin.shopping.item.pojo.Sku;
import com.lin.shopping.item.pojo.Spu;
import com.lin.shopping.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

@Data
public class SpuDto extends Spu {

    private String cname;
    private String bname;
    private SpuDetail spuDetail;
    private List<Sku> skus ;
}
