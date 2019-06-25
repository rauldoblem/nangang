package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急专家 feign 接口服务类
 * @author qizhijie-pc
 * @date 2018年10月10日10:30:04
 */
@FeignClient(value = "micro-res-expert")
public interface IExpertRestService {

    /**
     * 新增应急专家ExpertVo，ExpertVo不能为空
     * @param vo
     * @return ResponseEntity<ExpertVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ExpertVo> create(@RequestBody ExpertVo vo);

    /**
     * 根据id 获取应急专家ExpertVo
     * @param id id不能为空
     * @return ResponseEntity<ExpertVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ExpertVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新应急专家ExpertVo，ExpertVo不能为空
     * @param vo,
     * @param id 要更新ExpertVo id
     * @return ResponseEntity<ExpertVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ExpertVo> update(@RequestBody ExpertVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据参数获取ExpertVo多条记录,分页信息
     * 参数key为 name,unit,eventTypeIds(数组),specialty,createOrgId
     *          page,size
     *  @param expertPageVo
     *  @return ResponseEntity<RestPageImpl<ExpertVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<ExpertVo>> findPage(@RequestBody ExpertPageVo expertPageVo);

    /**
     * 根据参数获取ExpertVo多条记录
     * 参数key为 name,unit,eventTypeIds(数组),specialty,createOrgId
     *  @param expertListVo
     *  @return ResponseEntity<List<ExpertVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<ExpertVo>> findList(@RequestBody ExpertListVo expertListVo);
}
