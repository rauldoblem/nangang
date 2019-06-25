package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Hazard;
import com.taiji.emp.res.repository.HazardsRepository;
import com.taiji.emp.res.searchVo.hazard.HazardPageVo;
import com.taiji.emp.zn.vo.HazardStatVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class HazardsService extends BaseService<Hazard,String> {

    private HazardsRepository repository;

    public Hazard create(Hazard entity){
        Assert.notNull(entity,"Hazard 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Hazard result = repository.save(entity);
        return result;
    }

    public Hazard update(Hazard entity,String id){
        Assert.notNull(entity,"Hazard 对象不能为 null");
        Assert.notNull(id,"id 不能为 null");
        entity.setId(id);
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Hazard result = repository.save(entity);
        return result;
    }

    public Hazard findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Hazard result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Hazard entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    public Page<Hazard> findPage(HazardPageVo hazardPageVo, Pageable pageable){
        Page<Hazard> result = repository.findPage(hazardPageVo,pageable);
        return result;
    }

    public List<Hazard> findList(HazardPageVo hazardPageVo){
        List<Hazard> result = repository.findList(hazardPageVo);
        return result;
    }

    /**
     * 危险源级别ID集合
     * @return
     */
    public List<Hazard> findGroupList(List<String> listCode) {
        List<Hazard> result = repository.findGroupList(listCode);
        return result;
    }

    public List<HazardStatVo> findInfo(MaterialSearchVo vo) {
        return repository.findInfo(vo);
    }
}
