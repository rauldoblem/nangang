package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.searchVo.PersonListVo;
import com.taiji.emp.duty.searchVo.PersonPageVo;
import com.taiji.emp.duty.vo.PersonVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 值班人员 feign 接口服务类
 */
@FeignClient(value = "micro-duty-person")
public interface IPersonRestService {
    /**
     * 新增值班人员信息 PersonVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<PersonVo> create(@RequestBody PersonVo vo);

    /**
     * 根据参数删除值班人员
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/delete")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@RequestBody PersonListVo vo);

    /**
     * 修改值班人员信息
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PersonVo> update(@RequestBody PersonVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id查询某条值班人员信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PersonVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询值班人员列表
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PersonVo>> findList(@RequestBody PersonListVo vo);

    /**
     * 根据条件查询值班人员列表——分页
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<PersonVo>> findPage(@RequestBody PersonPageVo vo);

}
