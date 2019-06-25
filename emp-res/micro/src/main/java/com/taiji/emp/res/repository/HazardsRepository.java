package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.common.utils.SqlFormat;
import com.taiji.emp.res.entity.Hazard;
import com.taiji.emp.res.entity.QHazard;
import com.taiji.emp.res.searchVo.hazard.HazardPageVo;
import com.taiji.emp.zn.vo.HazardStatInfoVo;
import com.taiji.emp.zn.vo.HazardStatVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class HazardsRepository extends BaseJpaRepository<Hazard,String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.platform}")
    private String platform;

    @Autowired
    SqlFormat sqlFormat;

    //带分页信息，查询危险源列表
    public Page<Hazard> findPage(HazardPageVo hazardPageVo, Pageable pageable){
        JPQLQuery<Hazard> query = buildQuery(hazardPageVo);
        return findAll(query,pageable);
    }

    //不带分页信息，查询危险源列表
    public List<Hazard> findList(HazardPageVo hazardPageVo){
        JPQLQuery<Hazard> query = buildQuery(hazardPageVo);
        return findAll(query);
    }

    private JPQLQuery<Hazard> buildQuery(HazardPageVo hazardPageVo){
        QHazard hazard = QHazard.hazard;
        JPQLQuery<Hazard> query = from(hazard);
        BooleanBuilder builder = new BooleanBuilder();
        String name = hazardPageVo.getName();
        String unid = hazardPageVo.getUnit();
        String createOrgId = hazardPageVo.getCreateOrgId();
        String danGradeId = hazardPageVo.getDanGradeId();
        List<String> danTypeIds = hazardPageVo.getDanTypeIds();
        String majorHazard = hazardPageVo.getMajorHazard();

        if(StringUtils.hasText(name)){ //姓名
            builder.and(hazard.name.contains(name));
        }

        if(StringUtils.hasText(unid)){
            builder.and(hazard.unit.contains(unid));
        }

        if(null!=danTypeIds&&danTypeIds.size()>0){
                builder.and(hazard.danTypeId.in(danTypeIds));
        }

        if(StringUtils.hasText(danGradeId)){
            builder.and(hazard.danGradeId.eq(danGradeId));
        }

        if(StringUtils.hasText(majorHazard)){
            builder.and(hazard.majorHazard.contains(majorHazard));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(hazard.createOrgId.eq(createOrgId));
        }

        builder.and(hazard.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Hazard.class
                        ,hazard.id
                        ,hazard.name
                        ,hazard.unit
                        ,hazard.danTypeId
                        ,hazard.danTypeName
                        ,hazard.danGradeId
                        ,hazard.danGradeName
                        ,hazard.lonAndLat
                        ,hazard.address
                        ,hazard.criticalValue
                        ,hazard.maxDanArea
                        ,hazard.describes
                        ,hazard.majorHazard
                        ,hazard.disaster
                        ,hazard.measure
                        ,hazard.principalTel
                        ,hazard.principal
                        ,hazard.createOrgId
                        ,hazard.createOrgName
                        ,hazard.reason
                )).where(builder)
                .orderBy(hazard.updateTime.desc());
        return query;
    }

    @Override
    @Transactional
    public Hazard save(Hazard entity){
        Assert.notNull(entity,"Hazard 对象不能为 null");

        Hazard result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            Hazard temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }

    /**
     * 危险源级别ID集合
     * @return
     */
    public List<Hazard> findGroupList(List<String> listCode) {
        QHazard hazard = QHazard.hazard;
        JPQLQuery<Hazard> query = from(hazard);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(hazard.danGradeId.in(listCode));
        query.select(
                Projections.bean(Hazard.class
                        ,hazard.danGradeId
                        ,hazard.danGradeName
                )).where(booleanBuilder).groupBy(hazard.danGradeId,hazard.danGradeName);
        return findAll(query);
    }

//    public List<HazardStatVo> findInfo(MaterialSearchVo vo) {
//        List<String> orgCodes = vo.getOrgCodes();
//        String stringCode = "";
//        if (!CollectionUtils.isEmpty(orgCodes)){
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("(");
//            for (String str : orgCodes){
//                stringBuilder.append("'").append(str).append("'").append(",");
//            }
//            stringCode = stringBuilder.toString();
//            stringCode = stringCode.substring(0,stringCode.length()-1);
//            stringCode+=")";
//        }
//        StringBuilder builder = new StringBuilder();
//        builder.append("SELECT rc.CREATE_ORG_ID as org_id,rc.DAN_GRADE_ID,rc.DAN_GRADE_NAME,rg.org_code")
//                .append(" FROM rc_hazard rc,sys_org rg")
//                .append(" WHERE rc.CREATE_ORG_ID = rg.ID")
//                .append(" AND rc.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("'")
//                .append(" AND rg.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("'");
//        if (!"".equals(stringCode)){
//            builder.append(" AND rg.org_code in").append(stringCode);
//        }
//        List<HazardStatVo> list = jdbcTemplate.query(builder.toString(),new BeanPropertyRowMapper(HazardStatVo.class));
//        return list;
//    }


    public List<HazardStatVo> findInfo(MaterialSearchVo vo) {
        List<String> orgCodes = vo.getOrgCodes();   //入参集合 = 板块个数

        List<HazardStatVo> list = null;                   //这是最后要返回的list 长度是等级的个数
        int listSize = 0;                                 //这个是为了规定list初始化空间长度的 等于每个结果集的长度 此左连接sql查询的每个结果集长度一致
        List<List<HazardStatInfoVo>> infoList = null;     //这个是每一条sql结果的list 长度是orgCodes的长度
        if (!CollectionUtils.isEmpty(orgCodes)){
            infoList = new ArrayList(orgCodes.size());
            int builderLength = 550;//sql length = 508
            StringBuilder builder ;
            for (String num : orgCodes) {
                builder = new StringBuilder(builderLength);
                builder.append("SELECT '").append(num)
                        .append("' ORG_ID , g.id DAN_GRADE_ID,g.ITEM_NAME DAN_GRADE_NAME,");

                if("oracle".equals(platform)){//oracle环境
                    builder.append("DECODE( z.num, NULL, 0, z.num ) TOTAL_NUMS ");
                }else{//mysql环境或者postgresql环境
                    builder.append("case z.num when NULL then 0 else z.num end TOTAL_NUMS ");
                }

                builder.append("FROM SYS_DIC_GROUP_ITEMS g LEFT JOIN ")
                        .append("(SELECT count( * ) num , er.DAN_GRADE_ID id ")
                        .append("FROM RC_HAZARD er,sys_org rg,SYS_DIC_GROUP_ITEMS it ")
                        .append("WHERE er.CREATE_ORG_ID = rg.id AND er.DAN_GRADE_ID = it.ID ")
                        .append("AND er.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("' ")
                        .append("AND rg.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("' ")
                        .append("AND rg.org_code LIKE '").append(num).append("%' " )
                        .append("GROUP BY er.DAN_GRADE_ID ) z ON g.id = z.id ")
                        .append("WHERE g.DIC_CODE = 'dicHazradGrade' AND g.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("' ")
                        .append("ORDER BY g.ORDERS");

                //根据数据源选择格式化原生sql的大小写
                String sql = sqlFormat.sqlFormat(builder.toString());
                List<HazardStatInfoVo> resultList = jdbcTemplate.query(sql , new BeanPropertyRowMapper(HazardStatInfoVo.class));
                infoList.add(resultList); //infoList中有8个resultList 每个resultList有等级个HazardStatInfoVo
            }
        }
        if(!CollectionUtils.isEmpty(infoList)){
            List<HazardStatInfoVo> hazardStatInfoVos1 = infoList.get(0);
            if(!CollectionUtils.isEmpty(hazardStatInfoVos1)){
                listSize = hazardStatInfoVos1.size();
                list = new ArrayList(listSize);
                //这个遍历的作用是：创建infoList的长度个HazardStatVo 并设置每个元素不同的DanGradeId DanGradeName
                //还要有一个遍历是把不同板块这一等级的数目添加到hazardStatVo中的totalNumber中
                HazardStatVo hazardStatVo = null;
                for (HazardStatInfoVo hazardStatInfoVo : hazardStatInfoVos1) {//4
                    hazardStatVo = new HazardStatVo();
                    hazardStatVo.setDanGradeId(hazardStatInfoVo.getDanGradeId());
                    hazardStatVo.setDanGradeName(hazardStatInfoVo.getDanGradeName());
                    hazardStatVo.setTotalNums(new ArrayList());
                    list.add(hazardStatVo);//此时结果集中 只需要拼到totalNumber里数据
                }
            }
            for (List<HazardStatInfoVo> hazardStatInfoVos : infoList) {//8
                if(!CollectionUtils.isEmpty(hazardStatInfoVos)){
                    for (int i = 0 , size = hazardStatInfoVos.size(); i < size; i++) {//4
                         list.get(i).getTotalNums().add(hazardStatInfoVos.get(i).getTotalNums());
                    }
                }
            }
        }
        return list;
    }
}
