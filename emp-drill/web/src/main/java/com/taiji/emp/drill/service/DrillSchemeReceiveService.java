package com.taiji.emp.drill.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.drill.common.constant.DrillGlobal;
import com.taiji.emp.drill.feign.DrillSchemeReceiveClient;
import com.taiji.emp.drill.feign.UtilsFeignClient;
import com.taiji.emp.drill.searchVo.DrillSchemeReceiveSearchVo;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.emp.drill.vo.DrillSchemeVo;
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
public class DrillSchemeReceiveService extends BaseService {

    @Autowired
    private DrillSchemeReceiveClient drillSchemeReceiveClient;

    @Autowired
    private UtilsFeignClient utilsFeignClient;

    /**
     * 根据条件查询演练方案接收列表——分页
     * @param searchVo
     * @param principal
     * @return
     */
    public RestPageImpl<DrillSchemeReceiveVo> findPage(DrillSchemeReceiveSearchVo searchVo, Principal principal) {
        Assert.notNull(searchVo,"searchVo不能为null");
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        searchVo.setReceiveOrgId(userVoProfile.getOrgId());
        ResponseEntity<RestPageImpl<DrillSchemeReceiveVo>> resultVo = drillSchemeReceiveClient.findPage(searchVo);
        RestPageImpl<DrillSchemeReceiveVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 上报/下发演练方案
     * @param searchVo
     * @param principal
     */
    public List<DrillSchemeReceiveVo> create(DrillSchemeReceiveSearchVo searchVo, Principal principal) {
        ResponseEntity<DrillSchemeReceiveVo> responseEntity = null;
        List<DrillSchemeReceiveVo> voList = new ArrayList<>();
        List<DrillSchemeReceiveVo> list = searchVo.getOrgIdANames();
        DrillSchemeReceiveVo vo = new DrillSchemeReceiveVo();

        DrillSchemeVo drillSchemeVo = new DrillSchemeVo();
        drillSchemeVo.setId(searchVo.getDrillSchemeId());
        vo.setDrillScheme(drillSchemeVo);
        vo.setSendType(searchVo.getSendType());
        vo.setSender(principal.getName());
        vo.setSendTime(utilsFeignClient.now().getBody());
        vo.setRecieveStatus(DrillGlobal.INFO_STATUS_UNRECEIVE);
        if (null != list && list.size() > 0){
            for (DrillSchemeReceiveVo scheme : list){
                vo.setOrgId(scheme.getOrgId());
                vo.setOrgName(scheme.getOrgName());
                responseEntity = drillSchemeReceiveClient.create(vo);
                DrillSchemeReceiveVo receiveVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
                voList.add(receiveVo);
            }
        }else {
            responseEntity = drillSchemeReceiveClient.create(vo);
            DrillSchemeReceiveVo receiveVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
            voList.add(receiveVo);
        }
        return voList;
    }

    /**
     * 根据演练方案ID获取 演练方案接收信息
     * @param drillSchemeId
     */
    public DrillSchemeReceiveVo findByDrillSchemeId(String drillSchemeId) {
        Assert.hasText(drillSchemeId,"drillSchemeId不能为空字符串");
        ResponseEntity<DrillSchemeReceiveVo> resultVo = drillSchemeReceiveClient.findByDrillSchemeId(drillSchemeId);
        DrillSchemeReceiveVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 接收演练方案
     * @param vo
     * @param principal
     */
    public void update(DrillSchemeReceiveVo vo, Principal principal) {

        vo.setReciever(principal.getName());
        vo.setRecieveTime(utilsFeignClient.now().getBody());
        vo.setRecieveStatus(DrillGlobal.INFO_STATUS_RECEIVE);
        drillSchemeReceiveClient.updateStatus(vo,vo.getId());
    }

    /**
     * 根据条件查询 方案接受部门的 接受状态信息列表
     * @param searchVo
     * @return
     */
    public List<DrillSchemeReceiveVo> findList(DrillSchemeReceiveSearchVo searchVo) {
        Assert.notNull(searchVo,"searchVo不能为null");
        ResponseEntity<List<DrillSchemeReceiveVo>> list = drillSchemeReceiveClient.findList(searchVo);
        List<DrillSchemeReceiveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

    /**
     * 判断要上报、下发是否已存在
     * @param searchVo
     * @return
     */
    public List<DrillSchemeReceiveVo> findIsExist(DrillSchemeReceiveSearchVo searchVo) {
        Assert.notNull(searchVo,"searchVo不能为null");
        ResponseEntity<List<DrillSchemeReceiveVo>> list = drillSchemeReceiveClient.findIsExist(searchVo);
        List<DrillSchemeReceiveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }
}
