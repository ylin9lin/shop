package com.lin.shopping.item.pojo;

import lombok.Data;

import javax.persistence.*;

@Table(name = "tb_spec_param")
@Data
public class SpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    private Boolean numeric1;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}
