package com.taiji.emp.base.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-base",path = "api/contactteams")
public interface ContactsTeamClient extends IContactTeamRestService{
}
