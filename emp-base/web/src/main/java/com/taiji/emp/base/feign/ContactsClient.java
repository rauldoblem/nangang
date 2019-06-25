package com.taiji.emp.base.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-base",path = "api/contacts")
public interface ContactsClient extends IContactRestService{
}
