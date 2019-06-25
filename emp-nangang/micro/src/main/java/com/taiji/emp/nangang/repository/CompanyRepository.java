package com.taiji.emp.nangang.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.Company;
import com.taiji.emp.nangang.entity.QCompany;
import com.taiji.emp.nangang.searchVo.company.CompanyListVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class CompanyRepository extends BaseJpaRepository<Company,String> {
    //不带分页信息查询
    public List<Company> findList(CompanyListVo companyListVo){
        QCompany company = QCompany.company;
        JPQLQuery<Company> query = from(company);

        BooleanBuilder builder = new BooleanBuilder();

        String name = companyListVo.getName();

        if(StringUtils.hasText(name)){
            builder.and(company.name.like(name));
        }

        builder.and(company.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Company.class
                        ,company.name
                        ,company.address
                        ,company.lonAndLot
                        ,company.chargePerson
                        ,company.chargeTel
                )).where(builder)
                .orderBy(company.updateTime.desc());

        return findAll(query);
    }
}
