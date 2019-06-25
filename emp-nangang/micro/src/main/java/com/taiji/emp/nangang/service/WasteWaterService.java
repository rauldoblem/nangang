package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.WasteWater;
import com.taiji.emp.nangang.repository.WasteWaterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author yhcookie
 * @date 2018/12/10 11:40
 */
@Slf4j
@Service
@AllArgsConstructor
public class WasteWaterService {

    @Autowired
    private WasteWaterRepository repository;

    public WasteWater getWasteWater() {
        return repository.getWasteWater();
    }
}
