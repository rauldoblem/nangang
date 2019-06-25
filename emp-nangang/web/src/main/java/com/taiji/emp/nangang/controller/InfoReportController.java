package com.taiji.emp.nangang.controller;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.DicGroupVo;
import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.emp.event.infoDispatch.vo.AccDealVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.emp.nangang.common.constant.EventGlobal;
import com.taiji.emp.nangang.common.constant.FileGlobal;
import com.taiji.emp.nangang.service.InfoReportService;
import com.taiji.emp.nangang.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/appNg")
public class InfoReportController extends BaseController{

    @Autowired
    private InfoReportService service;

    /**
     * 根据参数获取AccDealVo多条记录,分页信息
     * 参数key为 buttonType,eventName,eventTypeIds(数组),eventGradeId,startDate,endDate
     * page,size
     * @param infoPageVo
     */
    @PostMapping(path = "/findInfoReports")
    public ResultEntity findInfoReports(
            @NotNull(message = "infoPageVo 不能为null")
            @RequestBody InfoPageVo infoPageVo, Principal principal){
        RestPageImpl<AccDealVo> resultVo = service.findPage(infoPageVo,principal);
        return ResultUtils.success(resultVo);
    }

    /**
     * 信息填报
     * 包括初报、续报
     * @param acceptSaveVo
     */
    @PostMapping(path = "/addInfo")
    public ResultEntity addInfo(
            @Validated
            @NotNull(message = "acceptSaveVo 不能为null")
            @RequestBody AcceptSaveVo acceptSaveVo, Principal principal){
        service.addInfo(acceptSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改信息 -- 只更新主表
     *
     * @param acceptSaveVo
     * @param id       信息id
     */
    @PutMapping(path = "/infoMags/{id}")
    public ResultEntity updateInfo(
            @Validated
            @NotNull(message = "acceptSaveVo 不能为null")
            @RequestBody AcceptSaveVo acceptSaveVo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id, Principal principal){
        service.updateInfo(acceptSaveVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 获取单条上报、接报信息
     * @param id 信息id
     */
    @GetMapping(path = "/infoMags/{id}")
    public ResultEntity findInfoReportById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        AcceptVo resultVo = service.findOne(id);
        //获取附件信息
        List<DocAttVo> docAttVos = service.findDocAtts(id);
        List<PicturesVo> pictures = new ArrayList<PicturesVo>();
        List<VideosVo> videos = new ArrayList<VideosVo>();
        List<AudiosVo> audios = new ArrayList<AudiosVo>();
        //区分不同附件类型
        //picture,audio,video
        for (DocAttVo docAttVo:docAttVos) {
            String type = docAttVo.getType();
            if (StringUtils.isNotEmpty(type)){
                if (type.equals(FileGlobal.DOC_PIC_TYPE)){
                    //图片
                    PicturesVo picturesVo = new PicturesVo();
                    picturesVo.setId(docAttVo.getId());
                    picturesVo.setFileName(docAttVo.getName());
                    picturesVo.setFileType(docAttVo.getType());
                    picturesVo.setLocation(docAttVo.getLocation());
                    pictures.add(picturesVo);
                }else if (type.equals(FileGlobal.DOC_VIDEO_TYPE)){
                    //视频
                    VideosVo videosVo = new VideosVo();
                    videosVo.setId(docAttVo.getId());
                    videosVo.setFileName(docAttVo.getName());
                    videosVo.setFileType(docAttVo.getType());
                    videosVo.setLocation(docAttVo.getLocation());
                    videos.add(videosVo);
                }else if (type.equals(FileGlobal.DOC_AUDIO_TYPE)){
                    //音频
                    AudiosVo audiosVo = new AudiosVo();
                    audiosVo.setId(docAttVo.getId());
                    audiosVo.setFileName(docAttVo.getName());
                    audiosVo.setFileType(docAttVo.getType());
                    audiosVo.setLocation(docAttVo.getLocation());
                    audios.add(audiosVo);
                }
            }
        }

        //封装数据
        InfoMagsVo infoMagsVo = new InfoMagsVo();
        infoMagsVo.setImAccept(resultVo);
        infoMagsVo.setPictures(pictures);
        infoMagsVo.setVideos(videos);
        infoMagsVo.setAudios(audios);

        return ResultUtils.success(infoMagsVo);
    }


    /**
     * 逻辑删除信息 -- 只删除主表
     *
     * @param id 信息id
     */
    @DeleteMapping(path = "/infoMags/{id}")
    public ResultEntity deleteInfo(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 保存加上报
     * @param acceptSaveVo  web需要从 accept id --->firstReportId --->eventId
     * @param sendFlag - 发送1、退回2、办结3、生成事件4、更新事件5
     */
    @PostMapping(path = "/saveAndSendInfo")
    public ResultEntity dealInfo(
            @Validated
            @NotNull(message = "accDealVo 不能为空")
            @RequestBody AcceptSaveVo acceptSaveVo,
            @NotEmpty(message = "sendFlag 不能为空字符串")
            @RequestParam(value = "sendFlag") String sendFlag, Principal principal){
        if(EventGlobal.buttonTypeSet.contains(sendFlag)){
            service.sendInfo(acceptSaveVo,sendFlag,principal);
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 发送信息
     * @param accDealVo  web需要从 accept id --->firstReportId --->eventId
     * @param buttonFlag - 发送1、退回2、办结3、生成事件4、更新事件5
     */
    @PostMapping(path = "/sendInfo")
    public ResultEntity sendInfo(
            @Validated
            @NotNull(message = "accDealVo 不能为空")
            @RequestBody AccDealVo accDealVo,
            @NotEmpty(message = "buttonFlag 不能为空字符串")
            @RequestParam(value = "buttonFlag") String buttonFlag,Principal principal){
        if(EventGlobal.buttonTypeSet.contains(buttonFlag)){
            service.sendInfos(accDealVo,buttonFlag,principal);
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 获取事件类型选择列表（获取所有叶子节点的事件类型）
     * @return
     */
    @GetMapping(path = "/findEventTypeList")
    public ResultEntity findItemsList(){

        Map<String,Object> params = new HashMap<>();
        params.put(EventGlobal.DIC_CODE,EventGlobal.DIC_EVENT_TYPE);

        DicGroupVo dicGroupVo = service.findByDicCode(EventGlobal.DIC_EVENT_TYPE);

        List<DicGroupItemVo> itemVos = service.findItemsAll(params);

        DicGroupItemVo top = new DicGroupItemVo();
        top.setId("-1");
        top.setParentId("-1");
        top.setItemName(dicGroupVo.getDicName());
        top.setType(dicGroupVo.getType());
        itemVos.add(top);

        List<DicGroupItemVo> root = AssemblyTreeUtils.assemblyTree(itemVos);
        List<ItemEventVo> itemEventVos = new ArrayList<>();
        //找出所有叶子节点
        if (!CollectionUtils.isEmpty(root)){
            DicGroupItemVo dicGroupItemVo = root.get(0);
            //子节点集合
            getChildren(dicGroupItemVo,itemEventVos);

        }
        return ResultUtils.success(itemEventVos);
    }
    //递归方法查询子节点
    public List<ItemEventVo>  getChildren (DicGroupItemVo dicGroupItemVo, List<ItemEventVo> itemEventVos){
        List<DicGroupItemVo> children = dicGroupItemVo.getChildren();
        if(!CollectionUtils.isEmpty(children)) {
            for (DicGroupItemVo itemVo : children) {
                if (!CollectionUtils.isEmpty(itemVo.getChildren())) {
                    List<DicGroupItemVo> children1 = itemVo.getChildren();
                    if(CollectionUtils.isEmpty(children1)){
                        ItemEventVo itemEventVo = new ItemEventVo();
                        itemEventVo.setId(itemVo.getId());
                        itemEventVo.setTypeName(itemVo.getItemName());
                        itemEventVos.add(itemEventVo);
                    }else {
                        for (DicGroupItemVo itemVo1 : children1) {
                            if (CollectionUtils.isEmpty(itemVo1.getChildren())) {
                                ItemEventVo itemEventVo = new ItemEventVo();
                                itemEventVo.setId(itemVo1.getId());
                                itemEventVo.setTypeName(itemVo1.getItemName());
                                itemEventVos.add(itemEventVo);
                            } else {
                                getChildren(itemVo1, itemEventVos);
                            }
                        }
                    }
                }else {
                    ItemEventVo itemEventVo = new ItemEventVo();
                    itemEventVo.setId(itemVo.getId());
                    itemEventVo.setTypeName(itemVo.getItemName());
                    itemEventVos.add(itemEventVo);
                }
            }
            return itemEventVos;
        }else {
            return itemEventVos;
        }
    }
    /**
     * 获取事件等级选择列表
     * @return
     */
    @GetMapping(path = "/findEventGradeList")
    public ResultEntity findOptions(){

        Map<String,Object> params = new HashMap<>();
        params.put(EventGlobal.DIC_CODE,EventGlobal.DIC_EVENT_GRADE);
        List<DicGroupItemVo> itemVos = service.findItemsAll(params);

        //由于从redis中获取到是没有根据order排序的，因此在这里排序 -- add by qizj 2018年12月23日16:02:30
        itemVos.sort(new Comparator<DicGroupItemVo>() {
            @Override
            public int compare(DicGroupItemVo o1, DicGroupItemVo o2) {
                try {
                    return o1.getOrders() - o2.getOrders();
                } catch (Exception var3) {
                    return 0;
                }
            }
        });
        //---排序结束

        List<ItemEventVo> itemEventVos = new ArrayList<>();
        for(DicGroupItemVo vo : itemVos){
            ItemEventVo itemEventVo = new ItemEventVo();
            itemEventVo.setId(vo.getId());
            itemEventVo.setTypeName(vo.getItemName());
            itemEventVos.add(itemEventVo);
        }
        return ResultUtils.success(itemEventVos);
    }


}
