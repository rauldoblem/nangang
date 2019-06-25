package com.taiji.emp.drill.service;

import com.taiji.emp.drill.entity.DrillPlanReceive;
import com.taiji.emp.drill.repository.DrillPlanReceiveRepository;
import com.taiji.emp.drill.searchVo.DrillPlanReceiveSearchVo;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
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
public class DrillPlanReceiveService extends BaseService<DrillPlanReceive,String> {

    @Autowired
    private DrillPlanReceiveRepository repository;

    /**
     * 根据条件查询演练计划接收列表——分页
     * @param searchVo
     * @param pageable
     * @return
     */
    public Page<DrillPlanReceive> findPage(DrillPlanReceiveSearchVo searchVo, Pageable pageable) {
        Page<DrillPlanReceive> result = repository.findPage(searchVo,pageable);
        return result;
    }

    /**
     * 根据条件查询 计划接收部门的 接收状态信息列表
     * @param searchVo
     * @return
     */
    public List<DrillPlanReceive> findList(DrillPlanReceiveSearchVo searchVo) {
        List<DrillPlanReceive> list = repository.findList(searchVo);

        return list;
    }

    /**
     * 上报/下发演练计划
     * @param entity
     * @return
     */
    public DrillPlanReceive create(DrillPlanReceive entity) {
        Assert.notNull(entity,"entity对象不能为空");
        DrillPlanReceive drillPlanReceive = repository.save(entity);
        return drillPlanReceive;
    }

    /**
     * 根据演练计划ID获取 演练计划接收信息
     * @param receiveVo
     * @return
     */
    public DrillPlanReceive findByDrillPlanId(DrillPlanReceiveVo receiveVo) {
        Assert.notNull(receiveVo,"receiveVo不能为null或空字符串");
        DrillPlanReceive result = repository.findByDrillPlanId(receiveVo);
        return result;
    }

    /**
     * 接收演练计划
     * @param entity
     * @return
     */
    public DrillPlanReceive updateStatus(DrillPlanReceive entity) {
        Assert.notNull(entity,"entity对象不能为null");
        DrillPlanReceive drillPlanReceive = repository.save(entity);
        return drillPlanReceive;
    }

    /**
     * 判断要上报、下发是否已存在
     * @param vo
     * @return
     */
    public List<DrillPlanReceive> findIsExist(DrillPlanReceiveSearchVo vo) {
        List<DrillPlanReceive> list = repository.findIsExist(vo);
        return list;
    }
}
