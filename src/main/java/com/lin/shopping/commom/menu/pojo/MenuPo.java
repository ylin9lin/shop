package com.lin.shopping.commom.menu.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "T_MENU")
@Data
public class MenuPo {

    @Id
    private String id;

    private String code;

    private String name;

    private Long parentId;

    private Date createTime;
}
