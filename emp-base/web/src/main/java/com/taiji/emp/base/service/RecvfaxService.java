package com.taiji.emp.base.service;

import com.taiji.emp.base.feign.RecvfaxClient;
import com.taiji.emp.base.searchVo.fax.RecvfaxPageVo;
import com.taiji.emp.base.vo.RecvfaxVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RecvfaxService extends BaseService{
    @Autowired
    private RecvfaxClient recvfaxClient;
    /**
     * 查询分页列表
     * @param recvfaxPageVo
     * @return vo
     */
    public RestPageImpl<RecvfaxVo> findPage(RecvfaxPageVo recvfaxPageVo){
        Assert.notNull(recvfaxPageVo,"recvfaxPageVo不能为null值");

        ResponseEntity<RestPageImpl<RecvfaxVo>> result = recvfaxClient.findPage(recvfaxPageVo);
        RestPageImpl<RecvfaxVo> vo = ResponseEntityUtils.achieveResponseEntityBody(result);

        return vo;
    }
}
