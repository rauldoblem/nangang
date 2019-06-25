package com.taiji.emp.nangang.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.nangang.feign.UserClient;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;

@Service
@AllArgsConstructor
public class PersonalInfoService {

    UserClient client;

    /**
     * 根据id获取用户信息
     * @return
     */
    public UserVo findById(OAuth2Authentication principal){

        //获取当前用户id
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String id = userMap.get("id").toString();
        ResponseEntity<UserVo> result = client.find(id);
        UserVo body = ResponseEntityUtils.achieveResponseEntityBody(result);
        return body;
    }
}
