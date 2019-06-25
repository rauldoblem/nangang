package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Material;
import com.taiji.emp.res.repository.MaterialRepository;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.material.MaterialPageVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
import com.taiji.emp.zn.vo.MaterialStatVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MaterialService extends BaseService<Material,String>{

    @Autowired
    private MaterialRepository repository;

    public Material createOrUpdate(Material entity){
        Assert.notNull(entity,"Material 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public Material findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Material entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<Material> findPage(MaterialPageVo materialPageVo, Pageable pageable){
        return repository.findPage(materialPageVo,pageable);
    }

    //不分页list查询
    public List<Material> findList(MaterialListVo materialListVo){
        return repository.findList(materialListVo);
    }

    /**
     * 去重获取物资类型大类ID多条记录
     * @return
     */
    public List<Material> findGroupList(List<String> listCode) {
        return repository.findGroupList(listCode);
    }

    public List<MaterialStatVo> findInfo(MaterialSearchVo vo) {
        return repository.findInfo(vo);
    }

    /**
     * 通过schemeId应急物质信息
     * @param schemeId
     * @return
     */
    public List<MaterialVo> findBySchemeId(String schemeId) {
        return repository.findBySchemeId(schemeId);
    }
}
