package com.taiji.emp.base.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/28 10:47
 */
@FeignClient(value = "base-server-zuul/micro-emp-base",path = "api/orgTeam")
public interface OrgTeamClient extends IOrgTeamRestService{
}
