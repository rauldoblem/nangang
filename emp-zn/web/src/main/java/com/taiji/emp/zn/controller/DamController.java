package com.taiji.emp.zn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.emp.zn.common.constant.ZNGlobal;
import com.taiji.emp.zn.util.HttpClientUtil;
import com.taiji.emp.zn.vo.DamInfoVo;
import com.taiji.emp.zn.vo.DamListInfoVo;
import com.taiji.emp.zn.vo.DamListVo;
import com.taiji.emp.zn.vo.DamVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/21 17:30
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/dams")
public class DamController {

    @Autowired
    ObjectMapper mapper;
    /**
     * 获取大坝信息
     * （code(大坝编码，北海大坝 DB001,华光潭一级坝 DB002,华光潭二级把DB003)）
     */
    @GetMapping("/{code}")
    public ResultEntity getDamInfo(
            @Validated
            @NotNull(message = "code不能为null")
            @PathVariable(value = "code")String code
    ) throws IOException {
        String url = ZNGlobal.GET_DAMINFO + code;
        String result = HttpClientUtil.httpGet(url);
        DamVo resultVo = mapper.readValue(result, DamVo.class);
        DamInfoVo data = null;
        if(null != resultVo){
            data = resultVo.getData();
        }
        return ResultUtils.success(data);
    }

    /**
     * 获取大坝列表
     * （code(大坝编码，北海大坝 DB001,华光潭一级坝 DB002,华光潭二级把DB003)）
     */
    @PostMapping("/getDamsList")
    public ResultEntity getDamsList() throws IOException {
        String url = ZNGlobal.GET_DAMList ;
        String result = HttpClientUtil.httpGet(url);
        DamListVo resultVo = mapper.readValue(result, DamListVo.class);
        List<DamListInfoVo> data ;
        List<DamListInfoVo> resultList = null;
        if(null != resultVo){
            //拿到了水库列表
            data = resultVo.getData();
            resultList = new ArrayList(3);
            //筛选出DB001 DB002 DB003 并修改名字为北海大坝 华光潭一级坝 华光潭二级坝
            if(!CollectionUtils.isEmpty(data)){
                resultList.add(data.stream().
                        filter(vo -> vo.getCode().equals(ZNGlobal.BEIHAIDABA_CODE)).findFirst().get().setName(ZNGlobal.BEIHAIDABA));
                resultList.add(data.stream().
                        filter(vo -> vo.getCode().equals(ZNGlobal.HUAGUANGTAN_YIJIBA_CODE)).findFirst().get().setName(ZNGlobal.HUAGUANGTAN_YIJIBA));
                resultList.add(data.stream().
                        filter(vo -> vo.getCode().equals(ZNGlobal.HUAGUANGTAN_ERJIBA_CODE)).findFirst().get().setName(ZNGlobal.HUAGUANGTAN_ERJIBA));
            }
        }
        return ResultUtils.success(resultList);
    }
}
