package com.taiji.emp.base.service;

import com.taiji.emp.base.feign.SendfaxClient;
import com.taiji.emp.base.searchVo.fax.SendfaxPageVo;
import com.taiji.emp.base.vo.SendfaxVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SendfaxService extends BaseService{
    @Autowired
    private SendfaxClient sendfaxClient;
    /**
     * 查询分页列表
     * @param sendfaxPageVo
     * @return vo
     */
    public RestPageImpl<SendfaxVo> findPage(SendfaxPageVo sendfaxPageVo){
        Assert.notNull(sendfaxPageVo,"sendfaxPageVo不能为null值");

        ResponseEntity<RestPageImpl<SendfaxVo>> result = sendfaxClient.findPage(sendfaxPageVo);
        RestPageImpl<SendfaxVo> vo = ResponseEntityUtils.achieveResponseEntityBody(result);

        return vo;
    }
}
