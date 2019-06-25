package com.taiji.emp.drill.service;

import com.taiji.emp.drill.entity.DrillSchemeReceive;
import com.taiji.emp.drill.repository.DrillSchemeReceiveRepository;
import com.taiji.emp.drill.searchVo.DrillSchemeReceiveSearchVo;
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
public class DrillSchemeReceiveService extends BaseService<DrillSchemeReceive,String> {

    DrillSchemeReceiveRepository receiveRepository;

    /**
     * 根据条件查询演练方案接收列表——分页
     * @param searchVo
     * @param page
     * @return
     */
    public Page<DrillSchemeReceive> findPage(DrillSchemeReceiveSearchVo searchVo, Pageable page) {
        Page<DrillSchemeReceive> resultPage = receiveRepository.findPage(searchVo,page);
        return resultPage;
    }

    /**
     * 上报/下发演练方案
     * @param entity
     * @return
     */
    public DrillSchemeReceive create(DrillSchemeReceive entity) {
        Assert.notNull(entity,"entity对象不能为空");
        DrillSchemeReceive drillSchemeReceive = receiveRepository.save(entity);
        return drillSchemeReceive;
    }

    /**
     * 根据演练方案ID获取 演练方案接收信息
     * @param drillSchemeId
     * @return
     */
    public DrillSchemeReceive findByDrillSchemeId(String drillSchemeId) {
        Assert.hasText(drillSchemeId,"drillSchemeId不能为null或空字符串");
        DrillSchemeReceive result = receiveRepository.findByDrillSchemeId(drillSchemeId);
        return result;
    }

    /**
     * 接收演练方案
     * @param entity
     * @return
     */
    public DrillSchemeReceive updateStatus(DrillSchemeReceive entity) {
        Assert.notNull(entity,"entity对象不能为null");
        DrillSchemeReceive drillSchemeReceive = receiveRepository.save(entity);
        return drillSchemeReceive;
    }

    /**
     * 根据条件查询 方案接受部门的 接受状态信息列表
     * @param searchVo
     * @return
     */
    public List<DrillSchemeReceive> findList(DrillSchemeReceiveSearchVo searchVo) {
        List<DrillSchemeReceive> list = receiveRepository.findList(searchVo);
        return list;
    }

    /**
     * 判断要上报、下发是否已存在
     * @param searchVo
     * @return
     */
    public List<DrillSchemeReceive> findIsExist(DrillSchemeReceiveSearchVo searchVo) {
        List<DrillSchemeReceive> list = receiveRepository.findIsExist(searchVo);
        return list;
    }
}
