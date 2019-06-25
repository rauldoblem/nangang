package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.caseVo.CasePageVo;
import com.taiji.emp.res.vo.CaseEntityVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 案例信息 feign 接口服务类
 * @author sun yi
 * @date 2018年11月2日
 */
@FeignClient(value = "micro-base-case")
public interface ICaseRestService {

    /**
     * 根据参数获取 CaseEntityVo 多条记录,不分页
     * params参数key为 "title":"标题","eventTypeIds":["事件类型ID"],"eventGradeId": "事件等级ID",
     * "sourceFlag": "案例来源标识码","occurStartTime": "开始事发时间","occurEndTime": "结束事发时间",
     * "createOrgId": "创建单位ID"
     *  @param params
     *  @return ResponseEntity<List<CaseEntityVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<CaseEntityVo>> findList(@RequestBody CasePageVo casePageVo);

    /**
     * 根据参数获取 CaseEntityVo 多条记录,分页信息
     * params参数key为 同上
     *          page,size
     *  @param params
     *  @return ResponseEntity<RestPageImpl<CaseEntityVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<CaseEntityVo>> findPage(@RequestBody CasePageVo casePageVo);

    /**
     * 新增案例信息 CaseVo，CaseVo 不能为空
     * @param vo
     * @return ResponseEntity<CaseVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<CaseEntityVo> create(@RequestBody CaseEntityVo vo);

    /**
     * 更新案例信息 CaseEntityVo，CaseEntityVo 不能为空
     * @param vo,
     * @param id 要更新 CaseEntityVo id
     * @return ResponseEntity<CaseEntityVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<CaseEntityVo> update(@RequestBody CaseEntityVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取案例信息 CaseEntityVo
     * @param id id不能为空
     * @return ResponseEntity<CaseEntityVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<CaseEntityVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);


}
