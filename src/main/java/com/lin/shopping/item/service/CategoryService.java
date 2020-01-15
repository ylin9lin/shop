package com.lin.shopping.item.service;

import com.lin.shopping.item.mapper.CategoryMapper;
import com.lin.shopping.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> aueryCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }


    /**
     * 查询分类名称
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }

    /**
     * 添加分类信息
     * @param category
     */
    public void saveCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    /**
     * 删除分类信息
     * @param id
     * @return
     */
    public int deleteCategory(Long id) {
      return  categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新分类信息
     * @param id
     * @param category
     */
    public void updateCategoryById(Long id, Category category) {
        category.setId(id);
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public List<String> queryNameByIds(Long ids) {
        return  null;
    }
}
