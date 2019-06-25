package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.SmokeService;
import com.taiji.emp.nangang.vo.SmokeForScreenVo;
import com.taiji.emp.nangang.vo.SmokeInfoVo;
import com.taiji.emp.nangang.vo.SmokeVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/11 10:03
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/smokes")
public class SmokeController {

    @Autowired
    private SmokeService service;
    @GetMapping("/getSmoke")
    public ResultEntity getSmoke(){
        SmokeVo vo = service.getSmoke();

        return ResultUtils.success(vo);
    }

    @PostMapping("/search")
    public ResultEntity findPage(){
        RestPageImpl<SmokeVo> pageVo = service.findPage();

        List<String> so2 = new ArrayList<>();
        List<String> no = new ArrayList<>();
        List<String> smoke = new ArrayList<>();
        List<String> hcl = new ArrayList<>();

        List<String> timeList = null;
        if(null != pageVo){
            List<SmokeVo> content = pageVo.getContent();
            int size = content.size();
            timeList = new ArrayList<>(size);
            if(!CollectionUtils.isEmpty(content)){
                for (int i = size - 1; i >= 0; i--) {
                    SmokeVo smokeVo = content.get(i);
                    if(null != smokeVo){
                        so2.add(smokeVo.getSo2converted());
                        no.add(smokeVo.getNoconverted());
                        smoke.add(smokeVo.getSmokeconverted());
                        hcl.add(smokeVo.getHclconverted());
                        String[] split = smokeVo.getCreateTime().split("T");
                        if(null != split && split.length == 2)
                            timeList.add(split[1].substring(0,5));
                    }
                }
            }
        }///4个 info 有时间重构一下
        List<SmokeInfoVo> list = new ArrayList();
        SmokeInfoVo smokeInfoVo1 = new SmokeInfoVo("SO2",so2);
        list.add(smokeInfoVo1);
        SmokeInfoVo smokeInfoVo2 = new SmokeInfoVo("NO",no);
        list.add(smokeInfoVo2);
        SmokeInfoVo smokeInfoVo3 = new SmokeInfoVo("烟尘",smoke);
        list.add(smokeInfoVo3);
        SmokeInfoVo smokeInfoVo4 = new SmokeInfoVo("HCL气体",hcl);
        list.add(smokeInfoVo4);

        SmokeForScreenVo result = new SmokeForScreenVo();
        result.setSeries(list);
        result.setXaxis(timeList);

        return ResultUtils.success(result);
    }
}
