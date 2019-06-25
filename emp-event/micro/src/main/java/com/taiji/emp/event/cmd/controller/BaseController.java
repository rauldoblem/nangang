package com.taiji.emp.event.cmd.controller;

import org.springframework.data.domain.Sort;

public class BaseController {
    final Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
}
