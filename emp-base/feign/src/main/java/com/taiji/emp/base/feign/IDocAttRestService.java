package com.taiji.emp.base.feign;

import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.DocEntityVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 附件管理 feign 接口服务类
 * @author qizhijie-pc
 * @date 2018年9月28日15:54:37
 */
@FeignClient(value = "micro-base-att")
public interface    IDocAttRestService {

    /**
     * 根据 DocAttVo id获取单个附件对象
     * @param id
     * @return ResponseEntity<DocAttVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/findOne/{id}")
    @ResponseBody
    ResponseEntity<DocAttVo> findOne(@PathVariable(value = "id")String id);

    /**
     * 根据业务id 获取附件清单list
     * @param entityId
     * @return ResponseEntity<List<DocAttVo>>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/findList/{entityId}")
    @ResponseBody
    ResponseEntity<List<DocAttVo>> findList(@PathVariable(value = "entityId")String entityId);

    /**
     * 给附件list赋予 业务id
     * @param docEntityVo
     * 包含 entityId（必须）,docAttIds（必须）
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/saveEntity")
    @ResponseBody
    ResponseEntity<Void> saveDocEntity(@RequestBody DocEntityVo docEntityVo);

    /**
     * 根据id逻辑删除一条附件记录。
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 新增保存 DocAttVo 对象
     * @param vo
     * DocAttVo 对象 --- 上传文件时保存DocAttachment对象
     */
    @RequestMapping(method = RequestMethod.POST, path = "/save")
    @ResponseBody
    ResponseEntity<DocAttVo> createDoc(@RequestBody DocAttVo vo);

//    /**
//     * 单附件上传方法
//     * @param file
//     * 页面上传单个文件 -- MultipartFile file
//     */
//    @RequestMapping(method = RequestMethod.POST,path = "/upload/single")
//    @ResponseBody
//    ResponseEntity<DocAttVo> uploadSingle(@RequestParam("singleFile") MultipartFile file);
//
//    /**
//     * 多附件上传方法
//     * @param files
//     * 页面上传多个附件 -- List<MultipartFile>
//     */
//    @RequestMapping(method = RequestMethod.POST,path = "/upload/multiple")
//    @ResponseBody
//    ResponseEntity<List<DocAttVo>> uploadMultiple(@RequestParam("mulFiles") List<MultipartFile> files);
//
//    /**
//     * 下载单个文件
//     * @param fileId
//     * 附件id
//     * @return 文件流
//     */
//    @RequestMapping(method = RequestMethod.GET,path = "/download/file")
//    @ResponseBody
//    ResponseEntity<Void> downloadFile(@RequestParam("fileId") String fileId,HttpServletResponse response);
//
//    /**
//     * 根据业务id,下载附件文件zip
//     * @param entityId
//     * 业务实体id
//     * @return zip文件流
//     */
//    @RequestMapping(method = RequestMethod.GET,path = "/download/zipFile")
//    @ResponseBody
//    ResponseEntity<Void> downloadZipFile(@RequestParam("entityId") String entityId,HttpServletResponse response);

}
