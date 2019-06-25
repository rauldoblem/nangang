package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.knowledge.KnowListVo;
import com.taiji.emp.res.searchVo.knowledge.KnowPageVo;
import com.taiji.emp.res.vo.KnowledgeVo;
import com.taiji.emp.res.searchVo.knowledge.KnowListVo;
import com.taiji.emp.res.searchVo.knowledge.KnowPageVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急知识 feign 接口服务类
 * @author qizhijie-pc
 * @date 2018年9月18日17:24:06
 */
@FeignClient(value = "micro-res-knowledge")
public interface IKnowRestService {

    /**
     * 根据参数获取KnowledgeVo多条记录
     * 查询参数为 title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *  @param knowListVo
     *  @return ResponseEntity<List<KnowledgeVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<KnowledgeVo>> findList(@RequestBody KnowListVo knowListVo);

    /**
     * 根据参数获取KnowledgeVo多条记录,分页信息
     * 查询参数 title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *          page,size
     *  @param knowPageVo
     *  @return ResponseEntity<RestPageImpl<KnowledgeVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<KnowledgeVo>> findPage(@RequestBody KnowPageVo knowPageVo);

    /**
     * 新增应急知识KnowledgeVo，KnowledgeVo不能为空
     * @param vo
     * @return ResponseEntity<KnowledgeVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<KnowledgeVo> create(@RequestBody KnowledgeVo vo);

    /**
     * 更新应急知识KnowledgeVo，KnowledgeVo不能为空
     * @param vo,
     * @param id 要更新KnowledgeVo id
     * @return ResponseEntity<KnowledgeVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<KnowledgeVo> update(@RequestBody KnowledgeVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据id 获取应急知识KnowledgeVo
     * @param id id不能为空
     * @return ResponseEntity<KnowledgeVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<KnowledgeVo> findOne(@PathVariable(value = "id") String id);

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
