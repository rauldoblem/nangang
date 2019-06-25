package com.taiji.emp.res.controller;

import org.springframework.data.domain.Sort;

public class BaseController {
    final Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
}
