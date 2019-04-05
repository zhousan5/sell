package com.zhou.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
@DynamicUpdate
@Data
public class ProductCategory implements Serializable {
//类目ID
    @Id
    @GeneratedValue
    private Integer categoryId;
//    类目名字
    private String categoryName;
//    类目编号
    private Integer categoryType;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

}
