package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.common.utils.SqlFormat;
import com.taiji.emp.res.entity.Material;
import com.taiji.emp.res.entity.QMaterial;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.material.MaterialPageVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
import com.taiji.emp.zn.vo.MaterialStatInfoVo;
import com.taiji.emp.zn.vo.MaterialStatVo;
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
public class MaterialRepository extends BaseJpaRepository<Material,String>{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.platform}")
    private String platform;

    @Autowired
    SqlFormat sqlFormat;

    //查询应急物资list -- 分页
    public Page<Material> findPage(MaterialPageVo materialPageVo, Pageable pageable){

        String name = materialPageVo.getName();
        List<String> resTypeIds = materialPageVo.getResTypeIds();
        String specModel = materialPageVo.getSpecModel();
        String repertoryId = materialPageVo.getRepertoryId();
        String positionId = materialPageVo.getPositionId();
        List<String> materialIds = materialPageVo.getSelectedMaterialIds();
        String createOrgId = materialPageVo.getCreateOrgId();

        QMaterial material = QMaterial.material;

        JPQLQuery<Material> query = from(material);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(name)){
            builder.and(material.name.contains(name));
        }

        if(null != resTypeIds && resTypeIds.size() > 0){
            builder.and(material.resTypeId.in(resTypeIds));
        }

        if(StringUtils.hasText(specModel)){
            builder.and(material.specModel.contains(specModel));
        }

        if(StringUtils.hasText(repertoryId)){
            builder.and(material.repertoryId.eq(repertoryId));
        }

        if(StringUtils.hasText(positionId)){
            builder.and(material.positionId.eq(positionId));
        }

        if(null != materialIds && materialIds.size() > 0){
            builder.and(material.id.notIn(materialIds));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(material.createOrgId.eq(createOrgId));
        }

        builder.and(material.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Material.class
                ,material.id
                ,material.name
                ,material.code
                ,material.repertoryId
                ,material.repertoryName
                ,material.positionId
                ,material.positionName
                ,material.resTypeId
                ,material.resTypeName
                ,material.lonAndLat
                ,material.manufacturers
                ,material.vendorCountry
                ,material.specModel
                ,material.productTime
                ,material.expiratTime
                ,material.unitMeasure
                ,material.unit
                ,material.initialQuantity
                ,material.outsideQuantity
                ,material.remainingQuantity
                ,material.notes
                ,material.createOrgId
        )).where(builder)
                .orderBy(material.updateTime.desc());


        return findAll(query,pageable);
    }

    //查询应急物资list -- 不分页
    public List<Material> findList(MaterialListVo materialListVo){

        String name = materialListVo.getName();
        List<String> resTypeIds = materialListVo.getResTypeIds();
        String specModel = materialListVo.getSpecModel();
        String repertoryId = materialListVo.getRepertoryId();
        String positionId = materialListVo.getPositionId();
        List<String> materialIds = materialListVo.getMaterialIds();
        List<String> selectedMaterialIds = materialListVo.getSelectedMaterialIds();
        String createOrgId = materialListVo.getCreateOrgId();

        QMaterial material = QMaterial.material;

        JPQLQuery<Material> query = from(material);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(name)){
            builder.and(material.name.contains(name));
        }

        if(null != resTypeIds && resTypeIds.size() > 0){
            builder.and(material.resTypeId.in(resTypeIds));
        }

        if(StringUtils.hasText(specModel)){
            builder.and(material.specModel.contains(specModel));
        }

        if(StringUtils.hasText(repertoryId)){
            builder.and(material.repertoryId.eq(repertoryId));
        }

        if(StringUtils.hasText(positionId)){
            builder.and(material.positionId.eq(positionId));
        }

        if(null!=selectedMaterialIds&&selectedMaterialIds.size()>0){
            builder.and(material.id.notIn(selectedMaterialIds));
        }

        if(null!=materialIds&&materialIds.size()>0){
            builder.and(material.id.in(materialIds));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(material.createOrgId.eq(createOrgId));
        }

        builder.and(material.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Material.class
                ,material.id
                ,material.name
                ,material.code
                ,material.repertoryId
                ,material.repertoryName
                ,material.positionId
                ,material.positionName
                ,material.resTypeId
                ,material.resTypeName
                ,material.lonAndLat
                ,material.manufacturers
                ,material.vendorCountry
                ,material.specModel
                ,material.productTime
                ,material.expiratTime
                ,material.unitMeasure
                ,material.unit
                ,material.initialQuantity
                ,material.outsideQuantity
                ,material.remainingQuantity
                ,material.notes
                ,material.createOrgId
        )).where(builder)
                .orderBy(material.updateTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public Material save(Material entity){
        Assert.notNull(entity,"material对象不能为 null");
        Material result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            Material temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 去重获取物资类型大类ID多条记录
     * @return
     */
    public List<Material> findGroupList(List<String> listCode) {
        QMaterial material = QMaterial.material;
        JPQLQuery<Material> query = from(material);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(material.resTypeId.in(listCode));
        query.select(Projections.bean(Material.class
                ,material.resTypeId
                ,material.resTypeName
        )).where(booleanBuilder).groupBy(material.resTypeId,material.resTypeName);
        return findAll(query);
    }

//    public List<MaterialStatVo> findInfo(MaterialSearchVo vo) {
//        List<String> orgCodes = vo.getOrgCodes();
//        String string = "";
//        if (!CollectionUtils.isEmpty(orgCodes)){
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("(");
//            for (String str : orgCodes){
//                stringBuilder.append("'").append(str).append("'").append(",");
//            }
//            string = stringBuilder.toString();
//            string = string.substring(0,string.length()-1);
//            string+=")";
//        }
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("select rg.id as org_id,er.RES_TYPE_ID,er.RES_TYPE_NAME,rg.org_code")
//        .append(" from er_material er,sys_org rg")
//        .append(" WHERE er.CREATE_ORG_ID = rg.id")
//        .append(" AND er.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("'")
//        .append(" AND rg.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("'");
//        if (!"".equals(string)){
//            builder.append(" AND rg.org_code in").append(string);
//        }
//        List<MaterialStatVo> list = jdbcTemplate.query(builder.toString(),new BeanPropertyRowMapper(MaterialStatVo.class));
//        return list;
//    }


    //改sql
    public List<MaterialStatVo> findInfo(MaterialSearchVo vo) {
        List<String> orgCodes = vo.getOrgCodes();//入参集合 = 板块个数

        List<MaterialStatVo> list = null;                   //这是最后要返回的list 长度是等级的个数
        int listSize = 0;
        List<List<MaterialStatInfoVo>> infoList = null;     //这是sql结果集 的集合
        if (!CollectionUtils.isEmpty(orgCodes)){
            infoList = new ArrayList(orgCodes.size());
            int builderLength = 550;//sql length = 504
            StringBuilder builder ;
            for (String num : orgCodes) {
                builder = new StringBuilder(builderLength);
                builder.append("SELECT '").append(num).append("' ORG_ID,").append("g.id RES_TYPE_ID,");

                if("oracle".equals(platform)){//oracle环境
                    builder.append("g.ITEM_NAME RES_TYPE_NAME, DECODE(z.num, null,0, z.num) TOTAL_NUM ");
                }else{//mysql环境或者postgresql环境
                    builder.append("g.ITEM_NAME RES_TYPE_NAME, case z.num when null then 0 else z.num end TOTAL_NUM ");
                }

                builder.append("FROM SYS_DIC_GROUP_ITEMS g LEFT JOIN (SELECT count(*) num,er.RES_TYPE_ID id ")
                        .append("FROM er_material er,sys_org rg,SYS_DIC_GROUP_ITEMS it ")
                        .append("WHERE er.CREATE_ORG_ID = rg.id AND er.RES_TYPE_ID = it.ID ")
                        .append("AND er.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("' ")
                        .append("AND rg.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("' ")
                        .append("AND it.DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("' ")
                        .append("AND rg.org_code LIKE '").append(num).append("%' ")
                        .append("GROUP BY er.RES_TYPE_ID) z on g.id = z.id ")
                        .append("WHERE g.DIC_CODE = 'dicMaterialType' AND g.DEL_FLAG = '1' ")
                        .append("order by RES_TYPE_ID");
                //根据数据源选择格式化原生sql的大小写
                String sql = sqlFormat.sqlFormat(builder.toString());
                List<MaterialStatInfoVo> result = jdbcTemplate.query(sql ,new BeanPropertyRowMapper(MaterialStatInfoVo.class));
                infoList.add(result); //infoList中有8个resultList 每个resultList有res类型个MaterialStatInfoVo
            }
        }
        if(!CollectionUtils.isEmpty(infoList)){
            List<MaterialStatInfoVo> materialStatInfoVos1 = infoList.get(0);
            if(!CollectionUtils.isEmpty(materialStatInfoVos1)){
                listSize = materialStatInfoVos1.size();
                list = new ArrayList(listSize);
                //这个遍历的作用是：创建infoList的长度个MaterialStatVo 并设置每个元素不同的resTypeId resTypeName
                //还要有一个遍历是把不同板块这一物资类型的数目添加到materialStatVo中的totalNum中
                MaterialStatVo materialStatVo = null;
                for (MaterialStatInfoVo hazardStatInfoVo : materialStatInfoVos1) {//8
                    materialStatVo = new MaterialStatVo();
                    materialStatVo.setResTypeId(hazardStatInfoVo.getResTypeId());
                    materialStatVo.setResTypeName(hazardStatInfoVo.getResTypeName());
                    materialStatVo.setTotalNum(new ArrayList());
                    list.add(materialStatVo);//此时结果集中 只需要拼到totalNumber里数据
                }
            }
            for (List<MaterialStatInfoVo> hazardStatInfoVos : infoList) {//8
                if(!CollectionUtils.isEmpty(hazardStatInfoVos)){
                    for (int i = 0 , size = hazardStatInfoVos.size(); i < size; i++) {//8
                        list.get(i).getTotalNum().add(hazardStatInfoVos.get(i).getTotalNum());
                    }
                }
            }
        }
        return list;
    }

    /**
     * 通过schemeId应急物质信息
     * @param schemeId
     * @return
     */
    public List<MaterialVo> findBySchemeId(String schemeId) {
        int builderLength = 350;
        StringBuilder builder = new StringBuilder(builderLength);
        builder.append("SELECT * FROM ER_MATERIAL WHERE ID IN ")
                .append("(SELECT MATERIAL_ID FROM EC_MATERIAL WHERE SCHEME_ID = '")
                .append(schemeId).append("' AND DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("')");
        String sql = sqlFormat.sqlFormat(builder.toString());
        return jdbcTemplate.query(sql , new BeanPropertyRowMapper(MaterialVo.class));
    }
}
