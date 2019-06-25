package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.Recvfax;
import com.taiji.emp.base.feign.IRecvfaxService;
import com.taiji.emp.base.mapper.RecvfaxMapper;
import com.taiji.emp.base.searchVo.fax.RecvfaxPageVo;
import com.taiji.emp.base.service.RecvfaxService;
import com.taiji.emp.base.vo.RecvfaxVo;
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
@RequestMapping("/api/recvfax")
@AllArgsConstructor
public class RecvfaxController extends BaseController implements IRecvfaxService {
    RecvfaxService service;
    RecvfaxMapper mapper;

    /**
     * 根据参数获取RecvfaxVo多条记录，分页
     * @param参数
     *      page,size
     * @param recvfaxPageVo
     * @return ResponseEntity<RestPageImpl<RecvfaxVo></>></>
     */
    @Override
    public ResponseEntity<RestPageImpl<RecvfaxVo>> findPage(@RequestBody RecvfaxPageVo recvfaxPageVo){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",recvfaxPageVo.getPage());
        map.add("size",recvfaxPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<Recvfax> pageresult = service.findPage(recvfaxPageVo,page);
        RestPageImpl<RecvfaxVo> voPage = mapper.entityPageToVoPage(pageresult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }
}
