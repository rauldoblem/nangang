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
public interface CmdTaskMapper extends BaseMapper<Task,TaskVo> {

    default List<TaskVo> entityListToVoList(List<Task> entityList,List<TaskExeorg> taskExeorgList)
    {
        if ( entityList == null) {
            return null;
        }
        List<TaskVo> list = new ArrayList<>(entityList.size());
        for ( int i=0;i<entityList.size();i++) {
            Task task = entityList.get(i);
            TaskExeorg taskExeorg = taskExeorgList.get(i);
            list.add(entityToVoForAddTaskExeOrg(task,taskExeorg));
        }
        return list;
    }

    default RestPageImpl<TaskVo> entityPageToVoPage(Page<Task> entityPage, Pageable page,List<TaskExeorg> taskExeorgList)
    {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<Task> content = entityPage.getContent();
        List<TaskVo> list = new ArrayList<TaskVo>(content.size());
        for ( int i=0;i<content.size();i++) {
            Task task = content.get(i);
            TaskExeorg taskExeorg = taskExeorgList.get(i);
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
            taskExeorgVo.setTask(entityToVo(taskExeorg.getTask()));
            taskExeorgVo.setTaskId(taskExeorg.getTask().getId());
            taskVo.setTaskExeorg(taskExeorgVo);
        }
        taskVo.setCreateBy(entity.getCreateBy());
        taskVo.setCreateTime(entity.getCreateTime());
        taskVo.setUpdateBy(entity.getUpdateBy());
        taskVo.setUpdateTime(entity.getUpdateTime());
        return taskVo;
    }

}
