package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    //新增套餐
    @AutoFill(OperationType.INSERT)
    public void addSetMealMapper(Setmeal setmeal);

    //根据ID查询套餐基本信息
    public Setmeal selectSetmealByIdMapper(long id);
}
