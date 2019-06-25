package com.taiji.emp.nangang.repository;

import com.taiji.emp.nangang.entity.CompanyInfo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/20 11:22
 */
@Repository
@Transactional(readOnly = true)
public class CompanyInfoRepository extends BaseJpaRepository<CompanyInfo,String> {

}
