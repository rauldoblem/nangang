package com.taiji.emp.base.service;

import com.taiji.emp.base.feign.TelrecordClient;
import com.taiji.emp.base.searchVo.telrecord.TelrecordPageVo;
import com.taiji.emp.base.vo.TelrecordVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

@Service
public class TelrecordService extends BaseService{
    @Autowired
    private TelrecordClient telrecordClient;
    /**
     * 查询分页列表
     * @param telrecordPageVo
     * @return vo
     */
    public RestPageImpl<TelrecordVo> findPage(TelrecordPageVo telrecordPageVo){
        Assert.notNull(telrecordPageVo,"telrecordPageVo不能为null值");
        ResponseEntity<RestPageImpl<TelrecordVo>> resultVo = telrecordClient.findPage(telrecordPageVo);
        RestPageImpl<TelrecordVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        return vo;
    }
}
