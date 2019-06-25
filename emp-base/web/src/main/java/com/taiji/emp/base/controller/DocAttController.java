package com.taiji.emp.base.controller;

import com.taiji.emp.base.service.DocAttService;
import com.taiji.emp.base.vo.DocAttDTO;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files")
public class DocAttController extends BaseController{

    @Autowired
    DocAttService docAttService;

    /**
     * 单附件上传
     */
    @PostMapping(path="/uploadSingle")
    public ResultEntity uploadSingle(
            @NotNull(message = "file不能为null")
            @RequestParam("singleFile") MultipartFile file,Principal principal){
        if(file.isEmpty()){
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
        DocAttDTO docAttDTO = docAttService.uploadsingle(file,principal);
        return ResultUtils.success(docAttDTO);
    }

    /**
     * 多附件上传
     */
    @PostMapping(path="/uploadMultiple")
    public ResultEntity uploadMultiple(
            @NotNull(message = "files不能为null")
            @RequestParam("mulFiles") MultipartFile[] files,Principal principal){
        if(null==files||files.length<=0){
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
        List<DocAttDTO> docAttDTOs = docAttService.uploadmultiple(files,principal);
        return ResultUtils.success(docAttDTOs);
    }

    /**
     * 根据实体id获取附件列表
     */
    @GetMapping(path="/{id}")
    public ResultEntity findFilesById(
            @NotEmpty(message = "entityId不能为null或空字符串")
            @PathVariable("id")String entityId){
        List<DocAttDTO> docAttDTOs =docAttService.findFilesByEntityId(entityId);
        return ResultUtils.success(docAttDTOs);
    }

    /**
     * 根据附件id获取附件对象
     */
    @GetMapping(path = "/getDoc/{id}")
    public ResultEntity findDocByFileId(
            @NotEmpty(message = "entityId不能为null或空字符串")
            @PathVariable("id")String fileId){
        DocAttDTO docAttDTO = docAttService.findDocByFileId(fileId);
        return ResultUtils.success(docAttDTO);
    }

    /**
     * 删除附件对象(逻辑删除)
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteFile(
            @NotEmpty(message = "fileId不能为null或空字符串")
            @PathVariable("id")String fileId){

        docAttService.deleteLogic(fileId);
        return ResultUtils.success();
    }

    /**
     * 给已上传的附件赋值实体id
     * @param docEntityVo
     * 包含 实体业务id(必须)
     * 包含 docAttIds(必须)-- 附件id list
     */
    @PostMapping(path="/saveDocEntity")
    public ResultEntity saveDocEntity(
            @Validated
            @NotNull
            @RequestBody DocEntityVo docEntityVo){
        docAttService.saveDocEntity(docEntityVo);
        return ResultUtils.success();
    }


    /**
     * 下载单个文件
     * @param fileId 附件id
     * @return 文件流
     */
    @GetMapping(path = "/downloadFile")
    public ResultEntity downloadFile(
            @NotEmpty(message = "fileId不能为null或空字符串")
            @RequestParam("fileId") String fileId
            ,HttpServletResponse response){
        docAttService.downloadFile(fileId, response);
        return ResultUtils.success();
    }

    /**
     * 下载文件zip
     * @param entityId 业务实体id
     * @return zip文件流
     */
    @GetMapping(path = "/downloadZipFile")
    public ResultEntity downloadZipFile(
            @NotEmpty(message = "entityId不能为null或空字符串")
            @RequestParam("entityId") String entityId
            ,HttpServletResponse response){
        Boolean downLoad = docAttService.downloadZip(entityId,response);
        if(downLoad){
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"该记录无附件或参数异常");
        }
    }

}
