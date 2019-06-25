package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.*;
import com.taiji.emp.nangang.searchVo.company.CompanyListVo;
import com.taiji.emp.nangang.vo.CompanyVo;
import com.taiji.emp.nangang.vo.ResVo;
import com.taiji.emp.res.searchVo.hazard.HazardPageVo;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.target.TargetSearchVo;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.vo.*;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResService extends BaseService {
    @Autowired
    TeamClient teamClient;
    @Autowired
    SupportClient supportClient;
    @Autowired
    HazardsClient hazardsClient;
    @Autowired
    TargetClient targetClient;
    @Autowired
    MaterialClient materialClient;
    @Autowired
    CompanyClient companyClient;

    /**
     * 获取救援队伍不分页list
     */
    public List<TeamVo> findList(TeamListVo teamListVo) {
        Assert.notNull(teamListVo, "teamListVo 不能为空");
        ResponseEntity<List<TeamVo>> resultVo = teamClient.findList(teamListVo);
        List<TeamVo> vo1 = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo1;
    }

    /**
     * 获取应急社会依托资源list(不带分页)
     */
    public List<SupportVo> findList(SupportListVo supportListVo) {
        Assert.notNull(supportListVo, "supportListVo 不能为空");
        ResponseEntity<List<SupportVo>> resultVo = supportClient.findList(supportListVo);
        List<SupportVo> vo2 = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo2;
    }

    /**
     * 获取危险源ist(不带分页)
     */
    public List<HazardVo> findList(HazardPageVo hazardPageVo) {
        Assert.notNull(hazardPageVo, "hazardPageVo 不能为空");
        ResponseEntity<List<HazardVo>> resultVo = hazardsClient.findList(hazardPageVo);
        List<HazardVo> vo3 = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo3;
    }

    /**
     * 获取防护目标list(不带分页)
     */
    public List<TargetVo> findList(TargetSearchVo targetSearchVo) {
        Assert.notNull(targetSearchVo, "targetSearchVo 不能为空");
        ResponseEntity<List<TargetVo>> resultVo = targetClient.findList(targetSearchVo);
        List<TargetVo> vo4 = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo4;
    }

    /**
     * 获取应急物资list(不带分页)
     */
    public List<MaterialVo> findList(MaterialListVo materialListVo) {
        Assert.notNull(materialListVo, "materialListVo 不能为空");
        ResponseEntity<List<MaterialVo>> resultVo = materialClient.findList(materialListVo);
        List<MaterialVo> vo5 = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo5;
    }

    public List<CompanyVo> findList(CompanyListVo companyListVo) {
        ResponseEntity<List<CompanyVo>> resultVo = companyClient.findList(companyListVo);
        List<CompanyVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    private Double[] getCoordinates(String coordinates) {
        if (!StringUtils.isEmpty(coordinates) && coordinates.contains(",")) {
            String a[] = coordinates.split(",");
            Double.valueOf(a[0]);
            Double.valueOf(a[1]);
            Double[] result = {Double.valueOf(a[0]), Double.valueOf(a[1])};
            return result;
        } else {
            return null;
        }
    }

    public List<Map<String,Object>> getResVo(String setSearchName) {
        List<ResVo> resVoList = new ArrayList<>();

        TeamListVo teamListVo = new TeamListVo();
        teamListVo.setTeamName(setSearchName);
        SupportListVo supportListVo = new SupportListVo();
        supportListVo.setName(setSearchName);
        HazardPageVo hazardPageVo = new HazardPageVo();
        hazardPageVo.setName(setSearchName);
        TargetSearchVo targetSearchVo = new TargetSearchVo();
        targetSearchVo.setName(setSearchName);
        MaterialListVo materialListVo = new MaterialListVo();
        materialListVo.setName(setSearchName);

        List<TeamVo> vo0 = findList(teamListVo);
        for (int i = 0; i < vo0.size(); ++i) {
            ResVo resVo = new ResVo();
            resVo.setTableFlag("0");
            resVo.setAddress(vo0.get(i).getAddress());
            resVo.setSearchName(vo0.get(i).getName());
            resVo.setLonAndLat(vo0.get(i).getLonAndLat());
            resVo.setPrincipal(vo0.get(i).getPrincipal());
            resVo.setPrincipalTel(vo0.get(i).getPrincipalTel());
            resVoList.add(resVo);
        }
        List<SupportVo> vo1 = findList(supportListVo);
        for (int i = 0; i < vo1.size(); ++i) {
            ResVo resVo = new ResVo();
            resVo.setTableFlag("1");
            resVo.setAddress(vo1.get(i).getAddress());
            resVo.setSearchName(vo1.get(i).getName());
            resVo.setLonAndLat(vo1.get(i).getLonAndLat());
            resVo.setPrincipal(vo1.get(i).getPrincipal());
            resVo.setPrincipalTel(vo1.get(i).getPrincipalTel());
            resVoList.add(resVo);
        }
        List<HazardVo> vo2 = findList(hazardPageVo);
        for (int i = 0; i < vo2.size(); ++i) {
            ResVo resVo = new ResVo();
            resVo.setTableFlag("2");
            resVo.setAddress(vo2.get(i).getAddress());
            resVo.setSearchName(vo2.get(i).getName());
            resVo.setLonAndLat(vo2.get(i).getLonAndLat());
            resVo.setPrincipal(vo2.get(i).getPrincipal());
            resVo.setPrincipalTel(vo2.get(i).getPrincipalTel());
            resVoList.add(resVo);
        }
        List<TargetVo> vo3 = findList(targetSearchVo);
        for (int i = 0; i < vo3.size(); ++i) {
            ResVo resVo = new ResVo();
            resVo.setTableFlag("3");
            resVo.setAddress(vo3.get(i).getAddress());
            resVo.setSearchName(vo3.get(i).getName());
            resVo.setLonAndLat(vo3.get(i).getLonAndLat());
            resVo.setPrincipal(vo3.get(i).getPrincipal());
            resVo.setPrincipalTel(vo3.get(i).getPrincipalTel());
            resVoList.add(resVo);
        }
        List<MaterialVo> vo4 = findList(materialListVo);
        for (int i = 0; i < vo4.size(); ++i) {
            ResVo resVo = new ResVo();
            resVo.setTableFlag("4");
            resVo.setSearchName(vo4.get(i).getName());
            resVo.setLonAndLat(vo4.get(i).getLonAndLat());
            resVoList.add(resVo);
        }

        CompanyListVo companyListVo = new CompanyListVo();
        companyListVo.setName(setSearchName);
        List<CompanyVo> vo5 = findList(companyListVo);
        for (int i = 0; i < vo5.size(); ++i) {
            ResVo resVo = new ResVo();
            resVo.setTableFlag("5");
            resVo.setAddress(vo5.get(i).getAddress());
            resVo.setSearchName(vo5.get(i).getName());
            resVo.setLonAndLat(vo5.get(i).getLonAndLot());
            resVo.setPrincipal(vo5.get(i).getChargePerson());
            resVo.setPrincipalTel(vo5.get(i).getChargeTel());
            resVoList.add(resVo);
        }



        List<Map<String, Object>> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resVoList)) {
            for (ResVo vo : resVoList) {
                Map<String, Object> map = new HashMap<>();
                Map<String, Object> geometryMap = new HashMap<>();
                geometryMap.put("type", "Point");
                Double[] coordinatesArray = getCoordinates(vo.getLonAndLat());
                if (null == coordinatesArray) {
                    continue; //经纬度解析结果为 null，则跳出循环
                }
                geometryMap.put("coordinates", coordinatesArray);
                map.put("properties", vo);
                map.put("type", "Feature");
                map.put("geometry", geometryMap);
                list.add(map);
            }
        }
        return list;
    }
}
        //经纬度字符串转数组


//    public List<ResVo>  getResVo(TeamListVo teamListVo, SupportListVo supportListVo, HazardPageVo hazardPageVo, TargetSearchVo targetSearchVo, MaterialListVo materialListVo) {
//
//        List<ResVo> resVoList = null;
//
//
//        List<TeamVo> vo1 = findList(teamListVo);
//        for (int i=0;i<vo1.size();++i){
//            ResVo resVo = new ResVo();
//            resVo.setTableFlag("0");
//            resVo.setAddress(vo1.get(i).getAddress());
//            resVo.setName(vo1.get(i).getName());
//
//
//            resVoList.add(resVo);
//        }
//
//        List<SupportVo> vo2 = findList(supportListVo);
//        List<HazardVo> vo3 = findList(hazardPageVo);
//        List<TargetVo> vo4 = findList(targetSearchVo);
//        List<MaterialVo> vo5 = findList(materialListVo);
//
//
//
//
//
//    return resVoList;
//    }
