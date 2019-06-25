package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.track.Task;
import com.taiji.emp.event.cmd.entity.track.TaskExeorg;
import com.taiji.emp.event.cmd.vo.trackVo.TaskExeorgVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskVo;
import com.taiji.emp.event.eva.mapper.BaseMapper;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CmdTaskExeorgMapper extends BaseMapper<TaskExeorg, TaskExeorgVo>{

    default List<TaskVo> entityToTaskVoList(List<TaskExeorg> entityList)
    {
        if ( entityList == null) {
            return null;
        }
        List<TaskVo> list = new ArrayList<TaskVo>(entityList.size());
        for ( int i=0;i<entityList.size();i++) {
            TaskExeorg taskExeorg = entityList.get(i);
            Task task = taskExeorg.getTask();
            list.add(entityToVoForAddTaskExeOrg(task,taskExeorg));
        }
        return list;
    }
    default RestPageImpl<TaskVo> entityPageToTaskVoPage(Page<TaskExeorg> entityPage, Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<TaskExeorg> content = entityPage.getContent();
        List<TaskVo> list = new ArrayList<TaskVo>(content.size());
        for ( int i=0;i<content.size();i++) {
            TaskExeorg taskExeorg = content.get(i);
            Task task = taskExeorg.getTask();
            list.add(entityToVoForAddTaskExeOrg(task,taskExeorg));
        }
        RestPageImpl<TaskVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());
        return voPage;
    }
    //将两个entity合并一个TaskVo
    default TaskVo entityToVoForAddTaskExeOrg(Task entity, TaskExeorg taskExeorg) {
        TaskVo taskVo = new TaskVo();
        if (entity == null) {
            return null;
        }
        taskVo.setId(entity.getId());
        taskVo.setSchemeId(entity.getSchemeId());
        taskVo.setSchemeName(entity.getSchemeName());
        taskVo.setEventId(entity.getEventId());
        taskVo.setEventName(entity.getEventName());
        taskVo.setPlanId(entity.getPlanId());
        taskVo.setCreateOrgId(entity.getCreateOrgId());
        taskVo.setCreateOrgName(entity.getCreateOrgName());
        taskVo.setName(entity.getName());
        taskVo.setContent(entity.getContent());
        taskVo.setStartTime(entity.getStartTime());
        taskVo.setEndTime(entity.getEndTime());
        taskVo.setTaskStatus(entity.getTaskStatus());
        if (taskExeorg != null) {
            TaskExeorgVo taskExeorgVo = new TaskExeorgVo();
            taskExeorgVo.setId(taskExeorg.getId());
            taskExeorgVo.setOrgId(taskExeorg.getOrgId());
            taskExeorgVo.setOrgName(taskExeorg.getOrgName());
            taskExeorgVo.setPrincipal(taskExeorg.getPrincipal());
            taskExeorgVo.setPrincipalTel(taskExeorg.getPrincipalTel());
            taskExeorgVo.setSendTime(taskExeorg.getSendTime());
            taskExeorgVo.setTaskId(entity.getId());
            taskVo.setTaskExeorg(taskExeorgVo);
        }
        taskVo.setCreateBy(entity.getCreateBy());
        taskVo.setCreateTime(entity.getCreateTime());
        taskVo.setUpdateBy(entity.getUpdateBy());
        taskVo.setUpdateTime(entity.getUpdateTime());
        return taskVo;
    }

}
