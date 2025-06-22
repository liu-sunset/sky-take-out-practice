package com.sky.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 菜品口味
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    //菜品id
    private Long dishId;

    //口味名称
    private String name;

    //口味数据list
    private String value;

}
