package com.taiji.emp.drill.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.drill.common.constant.DrillGlobal;
import com.taiji.emp.drill.feign.DocAttClient;
import com.taiji.emp.drill.feign.DrillSchemeClient;
import com.taiji.emp.drill.msgService.InfoMsgService;
import com.taiji.emp.drill.searchVo.DrillSchemeReceiveSearchVo;
import com.taiji.emp.drill.searchVo.DrillSchemeSearchVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.emp.drill.vo.DrillSchemeSaveVo;
import com.taiji.emp.drill.vo.DrillSchemeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class DrillSchemeService extends BaseService {

    @Autowired
    private DrillSchemeClient drillSchemeClient;

    @Autowired
    private DocAttClient docAttClient;

    @Autowired
    private InfoMsgService infoMsgService;

    /**
     * 新增演练方案(含附件)
     * @param saveVo
     * @param principal
     */
    public void createDrillScheme(DrillSchemeSaveVo saveVo, Principal principal) {
        DrillSchemeVo vo = saveVo.getDrillSchemeVo();
        List<String> fileIds = saveVo.getFileIds();
        List<String> deleteIds = saveVo.getFileDeleteIds();
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();

        vo.setCreateBy(principal.getName()); //创建人
        vo.setUpdateBy(principal.getName()); //更新人
        vo.setOrgId(userProfileVo.getOrgId()); //制定单位ID
        vo.setOrgName(userProfileVo.getOrgName()); //制定单位名称

        String drillPlanId = vo.getDrillPlanId();
        Assert.hasText(drillPlanId,"drillPlanId不能为空");
        DrillPlanVo drillPlanVo = getDrillPlanById(drillPlanId);
        if(drillPlanVo!=null) {
            vo.setDrillPlanName(drillPlanVo.getDrillName());
        }

        vo.setReportStatus(DrillGlobal.INFO_STATUS_UNREPORT);
        vo.setSendStatus(DrillGlobal.INFO_STATUS_UNSEND);
        ResponseEntity<DrillSchemeVo> entityVo = drillSchemeClient.create(vo);
        if (null != fileIds && fileIds.size() > 0) {
            fileSave(entityVo, fileIds,null);
        }
        if (null != fileIds && fileIds.size() > 0) {
            fileSave(entityVo, null,deleteIds);
        }
    }

    /**
     * 附件上传
     * @param entityVo
     * @param fileIds
     */
    private void fileSave(ResponseEntity<DrillSchemeVo> entityVo, List<String> fileIds,List<String> fileDeletes) {
        DrillSchemeVo schemeVo = ResponseEntityUtils.achieveResponseEntityBody(entityVo);
        String entityId = schemeVo.getId();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId, fileIds, fileDeletes));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }

    /**
     * 根据id删除演练方案信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = drillSchemeClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 根据id修改演练方案信息
     * @param saveVo
     * @param principal
     */
    public void update(String id,DrillSchemeSaveVo saveVo, Principal principal) {
        DrillSchemeVo vo = saveVo.getDrillSchemeVo();
        List<String> fileIds = saveVo.getFileIds();
        List<String> deleteIds = saveVo.getFileDeleteIds();

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        vo.setId(id);
        //更新人
        vo.setUpdateBy(principal.getName());
        vo.setOrgId(userProfileVo.getOrgId()); //制定单位ID
        vo.setOrgName(userProfileVo.getOrgName()); //制定单位名称

        String drillPlanId = vo.getDrillPlanId();
        Assert.hasText(drillPlanId,"drillPlanId不能为空");
        DrillPlanVo drillPlanVo = getDrillPlanById(drillPlanId);
        vo.setDrillPlanName(drillPlanVo.getDrillName());
        vo.setReportStatus(DrillGlobal.INFO_STATUS_UNREPORT);
        vo.setSendStatus(DrillGlobal.INFO_STATUS_UNSEND);
        ResponseEntity<DrillSchemeVo> entityVo = drillSchemeClient.update(vo, id);
        if (null != fileIds && fileIds.size() > 0) {
            fileSave(entityVo, fileIds,null);
        }
        if (null != deleteIds && deleteIds.size() > 0) {
            fileSave(entityVo, null,deleteIds);
        }
    }

    /**
     * 根据id获取一条演练方案信息
     * @param id
     * @return
     */
    public DrillSchemeVo findOne(String id) {
        Assert.hasText("id","id不能为空");
        ResponseEntity<DrillSchemeVo> resultVo = drillSchemeClient.findOne(id);
        DrillSchemeVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询演练方案列表——分页
     * @param searchVo
     * @return
     */
    public RestPageImpl<DrillSchemeVo> findPage(DrillSchemeSearchVo searchVo) {
        Assert.notNull(searchVo,"searchVo不能为空");
        ResponseEntity<RestPageImpl<DrillSchemeVo>> resultVo = drillSchemeClient.findPage(searchVo);
        RestPageImpl<DrillSchemeVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 上报/下发演练方案——更改状态
     * @param drillSchemeVo
     * @param searchVo
     * @param principal
     */
    public void updateStatusById(DrillSchemeVo drillSchemeVo, DrillSchemeReceiveSearchVo searchVo, Principal principal,List<DrillSchemeReceiveVo> voList) {
        String sendType = searchVo.getSendType();
        drillSchemeVo.setUpdateBy(principal.getName());
        if (DrillGlobal.INFO_TYPE_REPORT.equals(sendType)){
            //上报
            drillSchemeVo.setReportStatus(DrillGlobal.INFO_STATUS_REPORT);
        }else if (DrillGlobal.INFO_TYPE_SEND.equals(sendType)){
            //下发
            drillSchemeVo.setSendStatus(DrillGlobal.INFO_STATUS_SEND);
        }
        drillSchemeClient.updateStatusById(drillSchemeVo);
        //发送演练方案上报或下发系统消息
        infoMsgService.sendSystemSchemeMsg(drillSchemeVo,voList);
    }
}
