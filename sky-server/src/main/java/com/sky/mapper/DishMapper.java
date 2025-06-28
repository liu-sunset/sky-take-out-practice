package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    //新增菜品
    public void addDish(Dish dish);
    //菜品的分页查询
    public Page<DishVO> selectDishPageMapper(DishPageQueryDTO dishPageQueryDTO);
    //根据ID集合查询出所有没有启用的id集合
    public List<Long> selectDishStatusMapper(List<Long> ids);
    //批量删除菜品
    public void deleteDishMapper(List<Long> ids);
    //菜品的查询回显
    public DishVO selectByIdMapper(long id);
    //修改菜品的基本信息
    @AutoFill(OperationType.UPDATE)
    public void updateDishMapper(Dish dish);
    //动态条件查询菜品
    List<Dish> list(Dish dish);
    //根据套餐ID查询旗下对应的餐品
    public List<Dish> selectDishByCategoryIdMapper(long categoryId);
}
