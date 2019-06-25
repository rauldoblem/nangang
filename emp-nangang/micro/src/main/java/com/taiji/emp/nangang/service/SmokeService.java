package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.Smoke;
import com.taiji.emp.nangang.repository.SmokeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author yhcookie
 * @date 2018/12/10 11:40
 */
@Slf4j
@Service
@AllArgsConstructor
public class SmokeService {

    @Autowired
    private SmokeRepository repository;

    public Smoke getSmoke() {
        return repository.getSmoke();
    }

    public Page<Smoke> findPage(Pageable page) {
        return repository.findPage(page);
    }
}
