package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.Sendfax;
import com.taiji.emp.base.feign.ISendfaxService;
import com.taiji.emp.base.mapper.SendfaxMapper;
import com.taiji.emp.base.searchVo.fax.SendfaxPageVo;
import com.taiji.emp.base.service.SendfaxService;
import com.taiji.emp.base.vo.SendfaxVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/sendfax")
@AllArgsConstructor
public class SendfaxController extends BaseController implements ISendfaxService {
    SendfaxService service;
    SendfaxMapper mapper;

    @Override
    public ResponseEntity<RestPageImpl<SendfaxVo>> findPage(@RequestBody SendfaxPageVo sendfaxPageVo){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",sendfaxPageVo.getPage());
        map.add("size",sendfaxPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<Sendfax> pageresult = service.findPage(sendfaxPageVo,page);
        RestPageImpl<SendfaxVo> voPage = mapper.entityPageToVoPage(pageresult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }
}
