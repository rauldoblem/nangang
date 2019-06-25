package com.taiji.emp.base.feign;

import com.taiji.emp.base.searchVo.contacts.ContactPageVo;
import com.taiji.emp.base.vo.ContactMidVo;
import com.taiji.emp.base.vo.ContactVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 通讯录 feign 接口服务类
 * @author sun yi
 * @date 2018年10月22日
 */
@FeignClient(value = "micro-base-contact")
public interface IContactRestService {

    /**
     * 根据参数获取 ContactVo 多条记录,不分页
     * @param contactPageVo
     * 参数 orgId(部门ID),dutyTypeId（职务ID）,name（姓名）,telephone（电话）
     * @return ResponseEntity<List<ContactVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<ContactVo>> findList(@RequestBody ContactPageVo contactPageVo);

    /**
     * 根据参数获取 ContactVo 多条记录,分页信息
     * @param contactPageVo 参数 orgId(部门ID),dutyTypeId（职务ID）,name（姓名）,telephone（电话）
     *          page,size
     *  @return ResponseEntity<RestPageImpl<ContactVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<ContactVo>> findPage(@RequestBody ContactPageVo contactPageVo);

    /**
     * 新增通讯录 ContactVo，ContactVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ContactVo> create(@RequestBody ContactVo vo);

    /**
     * 更新通讯录 ContactVo，ContactVo 不能为空
     * @param vo
     * @param id 要更新 ContactVo id
     * @return ResponseEntity<ContactVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ContactVo> update(@RequestBody ContactVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取通讯录 ContactVo
     * @param id id不能为空
     * @return ResponseEntity<ContactVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ContactVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);


    /**
     * 通讯录信息添加到分组 ContactMidVo，ContactMidVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    @ResponseBody
    ResponseEntity<Void> addContactToTeam(@RequestBody ContactMidVo vo);

    /**
     * 通讯录信息移出分组 ContactMidVo，ContactMidVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/remove")
    @ResponseBody
    ResponseEntity<Void> removeContactToTeam(@RequestBody ContactMidVo vo);

}
