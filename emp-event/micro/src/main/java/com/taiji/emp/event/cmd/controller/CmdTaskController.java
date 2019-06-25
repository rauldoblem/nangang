package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.track.Task;
import com.taiji.emp.event.cmd.entity.track.TaskExeorg;
import com.taiji.emp.event.cmd.feign.ITrackRestService;
import com.taiji.emp.event.cmd.mapper.CmdTaskExeorgMapper;
import com.taiji.emp.event.cmd.mapper.CmdTaskFeedbackMapper;
import com.taiji.emp.event.cmd.mapper.CmdTaskMapper;
import com.taiji.emp.event.cmd.searchVo.*;
import com.taiji.emp.event.cmd.service.CmdTaskExeorgService;
import com.taiji.emp.event.cmd.service.CmdTaskFeedBackService;
import com.taiji.emp.event.cmd.service.CmdTaskService;
import com.taiji.emp.event.cmd.vo.trackVo.*;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/task")
public class CmdTaskController extends BaseController implements ITrackRestService {

    private CmdTaskMapper mapper;
    private CmdTaskService service;
    private CmdTaskExeorgMapper cmdTaskExeorgMapper;
    private CmdTaskExeorgService cmdTaskExeorgService;
    private CmdTaskFeedBackService cmdTaskFeedBackService;
    private CmdTaskFeedbackMapper cmdTaskFeedbackMapper;
    /**
     * 新增 应急任务 TaskVo，TaskVo 不能为空
     * @param vo
     * @return ResponseEntity<TaskVo>
     */
    @Override
    public ResponseEntity<TaskVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody TaskVo vo) {

        String planTaskId = vo.getPlanTaskId();
        String schemeId = vo.getSchemeId();
        if(!StringUtils.isEmpty(planTaskId)) {
            boolean yes = service.findOneByPlanTaskId(planTaskId,schemeId);
            if(!yes){
                TaskVo resultVo =  createOne(vo);
                return ResponseEntity.ok(resultVo);
            }else {
                return ResponseEntity.ok(null);
            }
        }else {
            TaskVo resultVo =  createOne(vo);
            return ResponseEntity.ok(resultVo);
        }
    }

    @Override
    public void createList(@RequestBody List<TaskVo> vo) {
        List<TaskVo> voList = new ArrayList<>();
        for (TaskVo taskVo :vo) {
            TaskVo one = createOne(taskVo);
        }
    }

    public TaskVo createOne(TaskVo vo){
        Task entity = mapper.voToEntity(vo);
        vo.setTaskStatus(EventGlobal.EVENT_TASK_UNSEND);
        Task result = service.create(entity);
        //根据返回结果进行TaskExeorg的插入
        TaskExeorg taskExeorg = cmdTaskExeorgMapper.voToEntity(vo.getTaskExeorg());
        taskExeorg.setTask(result);
        TaskExeorg taskExeorgResult = cmdTaskExeorgService.create(taskExeorg);
        //将两个entity合并一个VO
       return mapper.entityToVoForAddTaskExeOrg(result,taskExeorgResult);
    }


    /**
     * 更新 应急任务 TaskVo，TaskVo 不能为空
     * @param vo
     * @param id 要更新 TaskVo id
     * @return ResponseEntity<TaskVo>
     */
    @Override
    public ResponseEntity<TaskVo> update(
            @Validated
            @NotNull(message = "TaskVo 不能为null")
            @RequestBody TaskVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        Task entity = mapper.voToEntity(vo);
        Task result = service.update(entity,id);
        //根据结果修改对应 TaskExeorg
        TaskExeorg taskExeorg = cmdTaskExeorgMapper.voToEntity(vo.getTaskExeorg());
        TaskExeorg taskExeorgResult = cmdTaskExeorgService.update(taskExeorg,result.getId());
        TaskVo resultVo = mapper.entityToVoForAddTaskExeOrg(result,taskExeorgResult);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id 获取应急任务 TaskVo
     * @param id id不能为空
     * @return ResponseEntity<TaskVo>
     */
    @Override
    public ResponseEntity<TaskVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        Task result = service.findOne(id);
        TaskExeorg taskExeorg = cmdTaskExeorgService.findOneByTaskId(result.getId(),null);
        TaskVo resultVo = mapper.entityToVoForAddTaskExeOrg(result,taskExeorg);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }


    /**
     * 根据id逻辑删除一条记录。
     * 同时删除，任务执行单位ID为taskID的数据
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        //taskExeorgService.deleteByTaskId(id);需要删除字表打开注释即可
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * 根据参数获取 TaskVo 多条记录,不分页
     * @param taskPageVo
     * @return ResponseEntity<List < TaskVo>>
     */
    @Override
    public ResponseEntity<List<TaskVo>> findList(
            @RequestBody TaskPageVo taskPageVo) {
        List<TaskExeorg> list = cmdTaskExeorgService.findList(taskPageVo);
        return ResponseEntity.ok(cmdTaskExeorgMapper.entityToTaskVoList(list));
    }

    /**
     * 根据参数获取 ContactVo 多条记录,分页信息
     * @param taskPageVo
     * @return ResponseEntity<RestPageImpl <ContactVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<TaskVo>> findPage(
            @RequestBody TaskPageVo taskPageVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        int page = taskPageVo.getPage();
        int size = taskPageVo.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        params.add("page",page);
        params.add("size",size);
        Pageable pageable = PageUtils.getPageable(params);
        Page<TaskExeorg> page1 = cmdTaskExeorgService.findPage(taskPageVo, pageable);
        RestPageImpl<TaskVo> taskExeorgVos = cmdTaskExeorgMapper.entityPageToTaskVoPage(page1, pageable);
        return ResponseEntity.ok(taskExeorgVos);
    }

    /**
     * 根据id将一条应急任务下发
     * @param vo@return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> dispatch(
            @NotNull(message = "DispatchVo 不能为null")
            @RequestBody DispatchVo vo) {
        service.dispatch(vo);
        TaskExeorg taskExeorg = cmdTaskExeorgService.findOneByTaskId(vo.getTaskId(),null);
        taskExeorg.setSendTime(vo.getSendTime());
        cmdTaskExeorgService.create(taskExeorg);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据处置方案id 查询任务信息 以时间轴显示
     * @param vo
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<List<EcTaskListVo>> timeAxisTask(
            @NotNull(message = "TimeAxisTaskVo 不能为null")
            @RequestBody TimeAxisTaskVo vo) {
        List<EcTaskListVo> voList = new ArrayList<>();
        //根据schemeId,可传入状态 获取多条 TaskExeorg，得到下发时间排序后的数据
        List<TaskExeorg> taskExeorgList = cmdTaskExeorgService.findListBySchemeId(vo);
        LocalDate localDate = null;
            for (int i =0;i<taskExeorgList.size();i++) {
                EcTaskListVo ecTaskListVo = new EcTaskListVo();//要返回的数据
                List<EcTaskVo> taskVos = new ArrayList<>();
                TaskExeorg taskExeorg = taskExeorgList.get(i);//获得当前 TaskExeorg 对象
                localDate = taskExeorg.getSendTime().toLocalDate();//获得当前时间
                ecTaskListVo.setTaskDate(localDate);
                for(int j=0;j<taskExeorgList.size();j++){
                    TaskExeorg taskExeorgOne = taskExeorgList.get(j);
                    LocalDate localDateIf = taskExeorgOne.getSendTime().toLocalDate();
                    //组装数据
                    Task task =taskExeorgOne.getTask();
                    EcTaskVo ecTaskVo =new EcTaskVo();
                    ecTaskVo.setId(task.getId());
                    ecTaskVo.setName(task.getName());
                    ecTaskVo.setTaskExeorg(taskExeorgOne.getOrgName());
                    ecTaskVo.setTaskStatus(task.getTaskStatus());
                    ecTaskVo.setTaskSendTime(taskExeorgOne.getSendTime());
                    List<TaskFeedbackVo> list = cmdTaskFeedbackMapper.entityListToVoList(cmdTaskFeedBackService.findList(task.getId()));

                    ecTaskVo.setTaskFeedback(list);
                    if(j==i){//当TaskExeorg 与 taskExeorgOne为同一对象时
                        taskVos.add(ecTaskVo);
                    }else {
                        if (localDate.equals(localDateIf)) {//时间判断
                            taskVos.add(ecTaskVo);
                            i++;
                        }
                    }
                }
                ecTaskListVo.setTasks(taskVos);
                voList.add(ecTaskListVo);
            }
        return ResponseEntity.ok(voList);
    }

}
