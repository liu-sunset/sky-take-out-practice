package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    //根据分类id查询套餐的数量
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);
    //新增套餐
    @AutoFill(OperationType.INSERT)
    public void addSetMealMapper(Setmeal setmeal);
    //根据ID查询套餐基本信息
    public Setmeal selectSetmealByIdMapper(long id);
    //套餐的分页查询
    public Page<Setmeal> selectSetmealPageMapper(SetmealPageQueryDTO setmealPageQueryDTO);
    //批量删除套餐
    public void deleteSetmealMapper(List<Long> ids);
    //根据ID修改套餐
    @AutoFill(OperationType.UPDATE)
    public void modifySetmealMapper(Setmeal setmeal);
    //动态条件查询套餐
    List<Setmeal> list(Setmeal setmeal);
    //根据套餐id查询菜品选项
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
    //根据条件统计套餐数量
    Integer countByMap(Map map);
}
