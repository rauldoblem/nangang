package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.ConventionalFactor;
import com.taiji.emp.nangang.repository.ConventionalFactorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ConventionalFactorService {
    @Autowired
    private ConventionalFactorRepository conventionalFactorRepository;

    public ConventionalFactor getConventionalFactor() {
        return conventionalFactorRepository.getConventionalFactor();
    }

    public Page<ConventionalFactor> findpage(Pageable pageable){
        Page<ConventionalFactor> result = conventionalFactorRepository.findPage(pageable);
        return result;
    }
}
