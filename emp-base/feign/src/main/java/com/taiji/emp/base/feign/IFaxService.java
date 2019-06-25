package com.taiji.emp.base.feign;


import com.taiji.emp.base.searchVo.fax.FaxListVo;
import com.taiji.emp.base.searchVo.fax.FaxPageVo;
import com.taiji.emp.base.searchVo.fax.RecvfaxPageVo;
import com.taiji.emp.base.searchVo.fax.SendfaxPageVo;
import com.taiji.emp.base.vo.FaxVo;
import com.taiji.emp.base.vo.RecvfaxVo;
import com.taiji.emp.base.vo.SendfaxVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-base-fax")
public interface IFaxService {
    /**
     * 新增传真FaxVo,FaxVo不能为空
     * @param vo
     * @return ResponseEntity<FaxVo></>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<FaxVo> create(@RequestBody FaxVo vo);

    /**
     * 更新传真FaxVo,FaxVo不能为空
     * @param vo
     * @param id 要更新FaxVo id
     * @return ResponseEntity<FaxVo></>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<FaxVo> update(@RequestBody FaxVo vo, @PathVariable(value = "id")String id);
    /**
     * 根据id 获取传真FaxVo
     * @param id id不能为空
     * @return ResponseEntity<FaxVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<FaxVo> findOne(@PathVariable(value = "id") String id);
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
     * 根据参数获取FaxVo多条记录
     * 查询参数 title(可选),sender(可选),receiver(可选),fax(可选),createTimeStart(可选),createTimeEnd(可选)
     *  @param faxListVo
     *  @return ResponseEntity<List<FaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<FaxVo>> findList(@RequestBody FaxListVo faxListVo);
    /**
     * 根据参数获取FaxVo多条记录,分页信息
     * 查询参数 title(可选),sender(可选),receiver(可选),fax(可选),createTimeStart(可选),createTimeEnd(可选)
     *          page,size
     *  @param faxPageVo
     *  @return ResponseEntity<RestPageImpl<FaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<FaxVo>> findPage(@RequestBody FaxPageVo faxPageVo);

}
