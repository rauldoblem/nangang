package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.Signin;
import com.taiji.emp.nangang.feign.ISignin;
import com.taiji.emp.nangang.mapper.SigninMapper;
import com.taiji.emp.nangang.searchVo.signin.SigninListVo;
import com.taiji.emp.nangang.searchVo.signin.SigninPageVo;
import com.taiji.emp.nangang.service.SigninService;
import com.taiji.emp.nangang.vo.SigninVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.service.UtilsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/signins")
public class SigninController extends BaseController implements ISignin {
    SigninService signinService;
    SigninMapper signinMapper;
    UtilsService utilsService;
    /**
     * 根据参数获取SigninVo多条记录
     * 查询参数 dutyDate(可选),dutyShiftPattern(可选),dutyPersonId(可选)
     *  @param signinListVo
     *  @return ResponseEntity<List<FaxVo>>
     */
    @Override
    public ResponseEntity<List<SigninVo>> findList(@RequestBody SigninListVo signinListVo) {
        List<Signin> list = signinService.findList(signinListVo);
        List<SigninVo> voList = signinMapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }


    /**
     * 根据参数获取FaxVo多条记录,分页信息
     * 查询参数 dutyPersonName(可选),checkDateStart(可选),checkDateEnd(可选)
     *          page,size
     *  @param signinPageVo
     *  @return ResponseEntity<RestPageImpl<FaxVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<SigninVo>> findPage(@RequestBody SigninPageVo signinPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",signinPageVo.getPage());
        map.add("size",signinPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<Signin> pageResult = signinService.findPage(signinPageVo,page);
        RestPageImpl<SigninVo> voPage = signinMapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增传真SigninVo,SigninVo不能为空
     * @param vo
     * @return ResponseEntity<FaxVo></>
     */
    @Override
    public ResponseEntity<SigninVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody SigninVo vo) {
        String signStatus = vo.getSignStatus();
        if (!StringUtils.isEmpty(signStatus)){
            if (signStatus.equals("1")){
                vo.setCheckInTime(utilsService.now());
            }else if(signStatus.equals("2")){
                vo.setCheckOutTime(utilsService.now());
            }
        }
        Signin entity = signinMapper.voToEntity(vo);
        Signin result = signinService.create(entity);
        SigninVo resultVo = signinMapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

}
