package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.feign.*;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.repertory.RepertoryPageVo;
import com.taiji.emp.res.vo.PositionVo;
import com.taiji.emp.res.vo.RepertoryVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepeService extends BaseService {

    @Autowired
    private RepeClient repeClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private DicItemClient dicItemClient;
    @Autowired
    private PositionClient positionClient;
    /**
     * 新增应急储备库记录
     */
    public void create(RepertoryVo vo , Principal principal){


        UserVo userVo = getCurrentUser(principal);

        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        String name = vo.getName();

        Assert.hasText(name,"储备库name 不能为空字符串");

        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        ResponseEntity<RepertoryVo> resultVo = repeClient.create(vo); //储备库入库保存
    }

    /**
     * 更新单条应急储备库记录
     */
    public void update(RepertoryVo vo, Principal principal,String id){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setUpdateBy(userName); //更新人
        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        String name = vo.getName();
    //  String code = vo.getCode();

        Assert.hasText(name,"储备库name 不能为空字符串");
    //  Assert.hasText(code,"储备库code 不能为空字符串");

        ResponseEntity<RepertoryVo> resultVo = repeClient.update(vo,id);

    }

    //获取用户信息
    public UserVo getCurrentUser(Principal principal){

        String userAccount = principal.getName();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("account",userAccount);
        MultiValueMap<String, Object> multiValueMap = convertMap2MultiValueMap(userMap);
        UserVo userVo = userClient.findOne(multiValueMap).getBody();
        return userVo;
    }

    //根据字典项id获取字典项对象 --- 返还带有 dicItemName 的vo
    public DicGroupItemVo getItemById(String id){
        Assert.hasText(id,"dicItem id不能为空字符串");
        ResponseEntity<DicGroupItemVo> resultVo = dicItemClient.find(id);
        DicGroupItemVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id获取单条应急储备库记录
     */
    public RepertoryVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<RepertoryVo> resultVo = repeClient.findOne(id);
        RepertoryVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条应急储备库记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = repeClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取应急储备库分页list
     */
    public RestPageImpl<RepertoryVo> findPage(RepertoryPageVo pageVo){
        Assert.notNull(pageVo,"RepertoryPageVo 不能为空");
        ResponseEntity<RestPageImpl<RepertoryVo>> resultVo = repeClient.findPage(pageVo);
        RestPageImpl<RepertoryVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取应急储备库list(不带分页)
     */
    public List<PositionVo> findList(PositionVo positionVo){
        Assert.notNull(positionVo,"PositionVo 不能为空");
        ResponseEntity<List<PositionVo>> resultVo = positionClient.findList(positionVo);
        List<PositionVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询物资库列表-不分页
     * @param vo
     * @return
     */
    public List<RepertoryVo> findRepertoryList(RepertoryPageVo vo) {
        Assert.notNull(vo,"vo 不能为空");
        ResponseEntity<List<RepertoryVo>> resultVo = repeClient.findRepertoryList(vo);
        List<RepertoryVo> result = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return result;
    }

    /**
     * 根据GIS数据格式要求获取应急物资图层信息-不分页
     */
    public List<Map<String,Object>> searchAllMaterialForGis(){
        List<Map<String,Object>> list = new ArrayList<>();
        MaterialListVo materialListVo = new MaterialListVo();
        ResponseEntity<List<RepertoryVo>> resultVo = repeClient.findRepertoryList(new RepertoryPageVo());
        List<RepertoryVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(!CollectionUtils.isEmpty(vos)){
            for(RepertoryVo vo : vos){
                Map<String,Object> map = new HashMap<>();
                Map<String,Object> geometryMap = new HashMap<>();
                geometryMap.put("type","Point");
                Double[] coordinatesArray = getCoordinates(vo.getLonAndLat());
                if(null==coordinatesArray){
                    continue; //经纬度解析结果为 null，则跳出循环
                }
                geometryMap.put("coordinates",coordinatesArray);

                map.put("properties",vo);
                map.put("type","Feature");

                map.put("geometry",geometryMap);
                list.add(map);
            }
        }
        return list;
    }
    //经纬度字符串转数组
    private Double[] getCoordinates(String coordinates){
        if(!StringUtils.isEmpty(coordinates)&&coordinates.contains(",")){
            String[] a =  coordinates.split(",");
            Double.valueOf(a[0]);
            Double.valueOf(a[1]);
            Double[] result = {Double.valueOf(a[0]),Double.valueOf(a[1])};
            return result;
        }else{
            return null;
        }
    }
}
