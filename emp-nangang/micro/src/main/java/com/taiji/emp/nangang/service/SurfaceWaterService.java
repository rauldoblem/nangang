package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.SurfaceWater;
import com.taiji.emp.nangang.repository.SurfaceWaterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SurfaceWaterService {
    @Autowired
    private SurfaceWaterRepository surfaceWaterRepository;

    public SurfaceWater getSurfaceWater() {
        return surfaceWaterRepository.getSurfaceWater();
    }

}
