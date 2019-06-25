package com.taiji.emp.drill.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.drill.common.constant.DrillGlobal;
import com.taiji.emp.drill.feign.DrillPlanReceiveClient;
import com.taiji.emp.drill.feign.UtilsFeignClient;
import com.taiji.emp.drill.searchVo.DrillPlanReceiveSearchVo;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DrillPlanReceiveService extends BaseService {

    @Autowired
    private DrillPlanReceiveClient drillPlanReceiveClient;

    @Autowired
    private UtilsFeignClient utilsFeignClient;

    /**
     * 根据条件查询演练计划接收列表——分页
     * @param searchVo
     * @param principal
     * @return
     */
    public RestPageImpl<DrillPlanReceiveVo> findPage(DrillPlanReceiveSearchVo searchVo, Principal principal) {
        Assert.notNull(searchVo,"searchVo不能为null");
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        searchVo.setReceiveOrgId(userVoProfile.getOrgId());
        ResponseEntity<RestPageImpl<DrillPlanReceiveVo>> resultVo = drillPlanReceiveClient.findPage(searchVo);
        RestPageImpl<DrillPlanReceiveVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询 计划接收部门的 接收状态信息列表
     * @param searchVo
     * @return
     */
    public List<DrillPlanReceiveVo> findList(DrillPlanReceiveSearchVo searchVo) {
        Assert.notNull(searchVo,"searchVo不能为null");
        ResponseEntity<List<DrillPlanReceiveVo>> list = drillPlanReceiveClient.findList(searchVo);
        List<DrillPlanReceiveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

    /**
     * 上报/下发演练计划
     * @param searchVo
     */
    public List<DrillPlanReceiveVo> create(DrillPlanReceiveSearchVo searchVo,Principal principal) {
        ResponseEntity<DrillPlanReceiveVo> responseEntity = null;
        List<DrillPlanReceiveVo> voList = new ArrayList<>();
        List<DrillPlanVo> list = searchVo.getOrgIdANames();
        DrillPlanReceiveVo vo = new DrillPlanReceiveVo();
        DrillPlanVo drillPlanVo = new DrillPlanVo();
        drillPlanVo.setId(searchVo.getDrillPlanId());
        vo.setDrillPlan(drillPlanVo);
        vo.setSendType(searchVo.getSendType());
        vo.setSender(principal.getName());
        vo.setSendTime(utilsFeignClient.now().getBody());
        vo.setRecieveStatus(DrillGlobal.INFO_STATUS_UNRECEIVE);
        if (null != list && list.size() > 0){
            for (DrillPlanVo drillPlan : list){
                vo.setOrgId(drillPlan.getOrgId());
                vo.setOrgName(drillPlan.getOrgName());
                responseEntity = drillPlanReceiveClient.create(vo);
                DrillPlanReceiveVo receiveVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
                voList.add(receiveVo);
            }
        }else {
            responseEntity = drillPlanReceiveClient.create(vo);
            DrillPlanReceiveVo receiveVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
            voList.add(receiveVo);
        }
        return voList;
    }

    /**
     * 根据演练计划ID获取 演练计划接收信息
     * @param drillPlanId
     * @return
     */
    public DrillPlanReceiveVo findByDrillPlanId(String drillPlanId,Principal principal) {
        Assert.hasText(drillPlanId,"drillPlanId不能为空字符串");
        String orgId = getCurrentUser(principal).getProfile().getOrgId();
        DrillPlanReceiveVo receiveVo = new DrillPlanReceiveVo();
        receiveVo.setDrillPlanId(drillPlanId);
        receiveVo.setOrgId(orgId);
        ResponseEntity<DrillPlanReceiveVo> resultVo = drillPlanReceiveClient.findByDrillPlanId(receiveVo);
        DrillPlanReceiveVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 接收演练计划
     * @param vo
     * @param principal
     */
    public void update(DrillPlanReceiveVo vo, Principal principal) {

        vo.setReciever(principal.getName());
        vo.setRecieveTime(utilsFeignClient.now().getBody());
        vo.setRecieveStatus(DrillGlobal.INFO_STATUS_RECEIVE);
        drillPlanReceiveClient.updateStatus(vo,vo.getId());
    }

    /**
     * 判断要上报、下发是否已存在
     * @param vo
     * @return
     */
    public List<DrillPlanReceiveVo> findIsExist(DrillPlanReceiveSearchVo vo) {
        Assert.notNull(vo,"vo不能为null");
        ResponseEntity<List<DrillPlanReceiveVo>> list = drillPlanReceiveClient.findIsExist(vo);
        List<DrillPlanReceiveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }
}
