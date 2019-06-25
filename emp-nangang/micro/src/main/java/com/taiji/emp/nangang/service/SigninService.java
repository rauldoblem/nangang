package com.taiji.emp.nangang.service;


import com.taiji.emp.nangang.entity.Signin;
import com.taiji.emp.nangang.repository.SigninRespository;
import com.taiji.emp.nangang.searchVo.signin.SigninListVo;
import com.taiji.emp.nangang.searchVo.signin.SigninPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SigninService extends BaseService<Signin,String> {
    @Autowired
    private SigninRespository signinRepository;

    public Signin create(Signin entity){
        Assert.notNull(entity,"Fax 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Signin result = signinRepository.save(entity);
        return result;
    }
    //提供给controller使用的 分页list查询方法
    public Page<Signin> findPage(SigninPageVo signinPageVo, Pageable pageable){
        Page<Signin> result = signinRepository.findPage(signinPageVo,pageable);
        return result;
    }

    public List<Signin> findList(SigninListVo signinListVo){
        List<Signin> result = signinRepository.findList(signinListVo);
        return result;
    }

}
