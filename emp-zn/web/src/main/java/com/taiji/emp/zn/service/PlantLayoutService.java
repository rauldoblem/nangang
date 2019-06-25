package com.taiji.emp.zn.service;

import com.taiji.emp.base.searchVo.PlantLayoutSearchVo;
import com.taiji.emp.base.vo.PlantLayoutVo;
import com.taiji.emp.zn.feign.PlantLayoutClient;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class PlantLayoutService extends BaseService {

    @Autowired
    PlantLayoutClient client;

    /**
     * 新增厂区平面图
     * @param vo
     */
    public void create(PlantLayoutVo vo,Principal principal) {
        String userName = principal.getName();
        //创建人
        vo.setCreateBy(userName);
        //更新人
        vo.setUpdateBy(userName);

        client.create(vo);
    }

    /**
     * 根据id删除某条厂区平面图信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        client.deleteLogic(id);
    }

    /**
     * 修改厂区平面图信息
     * @param plantLayoutVo
     * @param principal
     */
    public void update(PlantLayoutVo plantLayoutVo, Principal principal) {
        String userName = principal.getName();
        //创建人
        plantLayoutVo.setCreateBy(userName);
        //更新人
        plantLayoutVo.setUpdateBy(userName);
        client.update(plantLayoutVo);
    }

    /**
     * 根据id获取某条厂区平面图信息
     * @param id
     * @return
     */
    public PlantLayoutVo findOne(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlantLayoutVo> resultVo = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 查询厂区平面图信息列表------分页
     * @param plantLayoutVo
     * @return
     */
    public RestPageImpl<PlantLayoutVo> findPage(PlantLayoutSearchVo plantLayoutVo) {
        Assert.notNull(plantLayoutVo,"plantLayoutVo 不能为空");
        ResponseEntity<RestPageImpl<PlantLayoutVo>> resultVo = client.findPage(plantLayoutVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 查询厂区平面图信息列表------不分页
     * @param plantLayoutVo
     * @return
     */
    public List<PlantLayoutVo> findList(PlantLayoutSearchVo plantLayoutVo) {
        Assert.notNull(plantLayoutVo,"plantLayoutVo 不能为空");
        ResponseEntity<List<PlantLayoutVo>> resultVo = client.findList(plantLayoutVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }
}
