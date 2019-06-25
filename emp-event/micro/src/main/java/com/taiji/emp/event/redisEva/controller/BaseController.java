package com.taiji.emp.event.redisEva.controller;

import org.springframework.data.domain.Sort;

public class BaseController {
    final Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
}
