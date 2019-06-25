package com.taiji.emp.base.feign;

import com.taiji.emp.base.vo.NoticeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告 feign 接口服务类
 * @author qzp-pc
 * @date 2018年10月21日14:20:37
 */
@FeignClient(value = "micro-base-notice")
public interface INoticeRestService {

    /**
     * 新增通知公告 NoticeVo 不能为空
     * @param vo
     * @return ResponseEntity<NoticeVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<NoticeVo> create(@RequestBody NoticeVo vo);

    /**
     * 根据id删除一条记录
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 更新通知公告 NoticeVo不能为空
     * @param vo
     * @param id 要更新 NoticeVo id
     * @return  ResponseEntity<NoticeVo>
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<NoticeVo> update(@RequestBody NoticeVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id获取通知公告信息
     * @param id id不能为空
     * @return ResponseEntity<NoticeVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<NoticeVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * page,size
     * @param params
     * @return ResponseEntity<RestPageImpl<NoticeVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<NoticeVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

}
