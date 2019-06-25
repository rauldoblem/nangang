package com.taiji.emp.drill.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.drill.common.constant.DrillGlobal;
import com.taiji.emp.drill.feign.DrillPlanClient;
import com.taiji.emp.drill.msgService.InfoMsgService;
import com.taiji.emp.drill.searchVo.DrillPlanReceiveSearchVo;
import com.taiji.emp.drill.searchVo.DrillPlanSearchVo;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class DrillPlanService extends BaseService {

    @Autowired
    private DrillPlanClient drillPlanClient;
    @Autowired
    private InfoMsgService infoMsgService;

    /**
     * 新增演练计划
     * @param vo
     * @param principal
     */
    public void create(DrillPlanVo vo, Principal principal) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();

        vo.setCreateBy(principal.getName()); //创建人
        vo.setUpdateBy(principal.getName()); //更新人
        vo.setOrgId(userVoProfile.getOrgId()); //制定单位ID
        vo.setOrgName(userVoProfile.getOrgName()); //制定单位名称
        //演练方式ID
        String drillWayId = vo.getDrillWayId();
        Assert.hasText(drillWayId,"drillWayId不能为空");
        //演练方式名称
        vo.setDrillWayName(getItemNameById(drillWayId));
        //发送状态
        vo.setSendStatus(DrillGlobal.INFO_STATUS_UNSEND);
        //接收状态
        vo.setReportStatus(DrillGlobal.INFO_STATUS_UNREPORT);
        drillPlanClient.create(vo);
    }

    /**
     * 根据id删除演练计划信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = drillPlanClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 更新演练计划
     * @param vo
     * @param principal
     */
    public void update(String id,DrillPlanVo vo, Principal principal) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();

        Assert.hasText(id,"id不能为空");
        vo.setId(id);
        //更新人
        vo.setUpdateBy(principal.getName());
        vo.setOrgId(userProfileVo.getOrgId()); //制定单位ID
        vo.setOrgName(userProfileVo.getOrgName()); //制定单位名称
        //演练方式ID
        String drillWayId = vo.getDrillWayId();
        Assert.hasText(drillWayId,"drillWayId不能为空");
        //演练方式名称
        vo.setDrillWayName(getItemNameById(drillWayId));
        //发送状态
        vo.setSendStatus(DrillGlobal.INFO_STATUS_UNSEND);
        //接收状态
        vo.setReportStatus(DrillGlobal.INFO_STATUS_UNREPORT);
        drillPlanClient.update(vo,vo.getId());
    }

    /**
     * 根据id获取演练计划信息
     * @param id
     * @return
     */
    public DrillPlanVo findOne(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<DrillPlanVo> resultVo = drillPlanClient.findOne(id);
        DrillPlanVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询演练计划列表——分页
     * @param searchVo
     * @return
     */
    public RestPageImpl<DrillPlanVo> findPage(DrillPlanSearchVo searchVo) {
        Assert.notNull(searchVo,"searchVo不能为null");
        ResponseEntity<RestPageImpl<DrillPlanVo>> resultVo = drillPlanClient.findPage(searchVo);
        RestPageImpl<DrillPlanVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 上报/下发演练计划——更改状态
     * @param drillPlanVo
     * @param vo
     */
    public void updateStatusById(DrillPlanVo drillPlanVo, DrillPlanReceiveSearchVo vo,Principal principal,List<DrillPlanReceiveVo> voList) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        //获取用户姓名
        String userName = userProfileVo.getName();

        String sendType = vo.getSendType();
        drillPlanVo.setUpdateBy(userName);
        if (DrillGlobal.INFO_TYPE_REPORT.equals(sendType)){
            //上报
            drillPlanVo.setReportStatus(DrillGlobal.INFO_STATUS_REPORT);
        }else if (DrillGlobal.INFO_TYPE_SEND.equals(sendType)){
            //下发
            drillPlanVo.setSendStatus(DrillGlobal.INFO_STATUS_SEND);
        }
        drillPlanClient.updateStatusById(drillPlanVo);

        //发送演练计划上报或下发系统消息
        infoMsgService.sendSystemPlanMsg(drillPlanVo,voList);
    }
}
