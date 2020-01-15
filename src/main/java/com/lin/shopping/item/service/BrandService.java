package com.lin.shopping.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.lin.shopping.commom.pojo.PageResult;
import com.lin.shopping.item.mapper.BrandMapper;
import com.lin.shopping.item.mapper.CategoryBrandMapper;
import com.lin.shopping.item.pojo.Brand;
import com.lin.shopping.item.pojo.Category;
import com.lin.shopping.item.pojo.CategoryBrand;
import com.lin.shopping.item.pojo.dto.BrandDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/bmp");

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(BrandService.class);
    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化Example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        //根据name模糊查询，或根据首字母查询
        if(StringUtil.isNotEmpty(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }
        //添加分页条件
        PageHelper.startPage(page,rows);
        //添加排序条件
        if(StringUtil.isNotEmpty(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc ? "desc" : "asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        //包装为PageInfo对象
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集
        return  new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 添加品牌信息
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增brand表
       brandMapper.insertSelective(brand);
        //新增中间表
            cids.forEach(cid -> {
                brandMapper.insertCategoryAndBrand(cid, brand.getId());
            });
    }

    /**
     * 图片上传
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        //校验文件类型
        if (!CONTENT_TYPES.contains(contentType)) {
            LOGGER.info("文件类型不合法: {}", originalFilename);
            return null;
        }
        try {
            //校验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法：{}", originalFilename);
                return null;
            }
            //保存至文件服务器
            //file.transferTo(new File("E:\\hedgehog\\images" + originalFilename));
            String ext = StringUtils.substringAfterLast(originalFilename,".");
           StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(),ext,null);
            //返回url，进行回显
            return "http://localhost:9001/"+storePath.getFullPath();
            //return "http://localhost:9001"+originalFilename;
        } catch (IOException e) {
            LOGGER.info("服务器内部错误：{}",originalFilename);
           e.printStackTrace();
        }
        return  null;
    }

    /**
     * 根据分类id查询品牌列表
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCid(Long cid) {

        return  brandMapper.seleceBrandsByCid(cid);
    }

    /**
     * 删除品牌数据
     * @param id
     */
    public void deleteBrand(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
        //删除brand表
        brandMapper.deleteByPrimaryKey(brand);
        //删除中间表
        categoryBrandMapper.deletecategoryBrand(id);

    }

    /**
     * 更新品牌信息
     * @param brandDto
     */
    public void updateBrand(BrandDto brandDto) {
        //更新brand表
        Brand brand = new Brand();
        brand.setId(brandDto.getId());
        brand.setName(brandDto.getName());
        brand.setImage(brandDto.getImage());
        brand.setLetter(brandDto.getLetter());
        brandMapper.updateByPrimaryKeySelective(brand);
        //更新分类表格
        Category category = new Category();
        category.setId(brandDto.getId());
        category.setParentId(brandDto.getParentId());
        category.setIsParent(brandDto.getIsParent());
        category.setName(brandDto.getName());
        category.setSort(brandDto.getSort());
    }

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    public List<Brand> queryBrandById(Long id) {
       Brand brand = new Brand();
       brand.setId(id);
        return brandMapper.select(brand);
    }


    public Brand queryBrandByIds(Long ids) {
        return brandMapper.selectByPrimaryKey(ids);
    }
}

