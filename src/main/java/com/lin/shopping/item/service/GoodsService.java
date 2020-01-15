package com.lin.shopping.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.shopping.commom.pojo.PageResult;
import com.lin.shopping.item.mapper.*;
import com.lin.shopping.item.pojo.*;
import com.lin.shopping.item.pojo.dto.SpuDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    /**
     * 根据条件分页查询spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuDto> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        // 添加查询条件
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }

        // 添加上下架的过滤条件
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        // 添加分页
        PageHelper.startPage(page, rows);

        // 执行查询，获取spu集合
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);

        // spu集合转化成spubo集合
        List<SpuDto> spuDtos = spus.stream().map(spu -> {
            SpuDto spuDto = new SpuDto();
            BeanUtils.copyProperties(spu, spuDto);

            // 查询品牌名称
            Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuDto.setBname(brand.getName());
            // 查询分类名称
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            Iterator name =names.iterator();
            spuDto.setCname(StringUtils.join(name, "-"));
            return spuDto;
        }).collect(Collectors.toList());

        // 返回pageResult<spuBo>
        return new PageResult<>(pageInfo.getTotal(), spuDtos);
    }

    /**
     * 新增商品
     * @param spuDto
     */
    @Transactional
    public void saveGoods(SpuDto spuDto) {
        //新增spu
        spuDto.setId(null);
        spuDto.setSaleable(true);
        spuDto.setVailds(true);
        spuDto.setCreateTime(new Date());
        spuDto.setLastUpdateTime(spuDto.getCreateTime());
        spuMapper.insertSelective(spuDto);
        //新增spuDetail
        SpuDetail spuDetail = spuDto.getSpuDetail();
        spuDetail.setSpuId(spuDto.getId());
        spuDetailMapper.insertSelective(spuDetail);
        saveSkuAndStock(spuDto);
    }
    //新增sku
    private void saveSkuAndStock(SpuDto spuDto) {
        spuDto.getSkus().forEach(sku -> {
            // 新增sku
            sku.setId(null);
            sku.setSpuId(spuDto.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);
            // 新增stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    /**
     * 根据spuId查询spuDetail
     * @param spuId
     * @return
     */
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
      return   spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     * 根据spuId查询sku集合（商品信息回显）
     * @param spuId
     * @return
     */
    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku record = new Sku();
        record.setSpuId(spuId);
       List<Sku> skus = skuMapper.select(record);
       skus.forEach(sku -> {
         Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
           sku.setStock(stock.getStock());
       });
        return skus;
    }

    /**
     * 更新商品信息
     * @param spuDto
     */
    @Transactional
    public void updateGoods(SpuDto spuDto) {
        //根据spuId查询需删除的sku
        Sku record = new Sku();
        List<Sku> skus = skuMapper.select(record);
        skus.forEach(sku -> {
            //删除stock
            stockMapper.selectByPrimaryKey(sku.getId());
        });
       //删除sku
        Sku sku = new Sku();
        sku.setSpuId(spuDto.getId());
        skuMapper.delete(sku);
       //新增sku和stock
        saveSkuAndStock(spuDto);
       //更新spu和spuDetail（将不能设置的数据设为null）
        spuDto.setCreateTime(null);
        spuDto.setLastUpdateTime(new Date());
        spuDto.setVailds(null);
        spuDto.setSaleable(null);
        spuMapper.updateByPrimaryKeySelective(spuDto);
        spuDetailMapper.updateByPrimaryKeySelective(spuDto.getSpuDetail());
    }
}
