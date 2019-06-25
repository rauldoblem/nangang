package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.common.constant.ExcelGlobal;
import com.taiji.emp.res.feign.HazardClient;
import com.taiji.emp.res.feign.OrgClient;
import com.taiji.emp.res.searchVo.hazard.HazardPageVo;
import com.taiji.emp.res.vo.HazardVo;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class HazardService extends BaseService {

    private HazardClient hazardClient;

    @Autowired
    private OrgClient orgClient;

    /**
     * 新增危险源记录
     */
    public void create(HazardVo vo, Principal principal){


        String userName  = principal.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        String danTypeId = vo.getDanTypeId();
        String danGradeId = vo.getDanGradeId();

        Assert.hasText(danTypeId,"类型ID 不能为空字符串");
        Assert.hasText(danGradeId,"级别ID 不能为空字符串");

        vo.setDanTypeName(getItemNameById(danTypeId));
        vo.setDanGradeName(getItemNameById(danGradeId));

        UserVo userVo = getCurrentUser(principal);

        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        hazardClient.create(vo);

    }

    /**
     * 更新单条危险源记录
     */
    public void update(HazardVo vo, Principal principal,String id){
        String userName  = principal.getName(); //获取用户姓名
        vo.setUpdateBy(userName); //更新人

        String danTypeId = vo.getDanTypeId();
        String danGradeId = vo.getDanGradeId();

        Assert.hasText(danTypeId,"危险源类型ID 不能为空字符串");
        Assert.hasText(danGradeId,"危险源级别ID 不能为空字符串");

        HazardVo tempVo = this.findOne(id);
        if(!danTypeId.equals(tempVo.getDanTypeId())){ //危险源类型ID
            vo.setDanTypeId(danTypeId);
        }
        if(!danGradeId.equals(tempVo.getDanGradeId())){
            vo.setDanGradeId(danGradeId);
        }
        hazardClient.update(vo,id);
    }


    /**
     * 根据id获取单条危险源记录
     */
    public HazardVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<HazardVo> resultVo = hazardClient.findOne(id);
        HazardVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条危险源记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = hazardClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取危险源分页list
     */
    public RestPageImpl<HazardVo> findPage(HazardPageVo hazardPageVo){
        Assert.notNull(hazardPageVo,"hazardPageVo 不能为空");
        ResponseEntity<RestPageImpl<HazardVo>> resultVo = hazardClient.findPage(hazardPageVo);
        RestPageImpl<HazardVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取危险源ist(不带分页)
     */
    public List<HazardVo> findList(HazardPageVo hazardPageVo){
        Assert.notNull(hazardPageVo,"hazardPageVo 不能为空");
        ResponseEntity<List<HazardVo>> resultVo = hazardClient.findList(hazardPageVo);
        List<HazardVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据GIS数据格式要求获取社会依托资源图层信息-不分页
     */
    public List<Map<String,Object>> searchAllHazardForGis(){
        List<Map<String,Object>> list = new ArrayList<>();
        HazardPageVo hazardPageVo = new HazardPageVo();
        ResponseEntity<List<HazardVo>> resultVo = hazardClient.findList(hazardPageVo);
        List<HazardVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(!CollectionUtils.isEmpty(vos)){
            for(HazardVo vo : vos){
                Map<String,Object> map = new HashMap<>();

                Map<String,Object> geometryMap = new HashMap<>();
                geometryMap.put("type","Point");
                Double[] coordinatesArray = getCoordinates(vo.getLonAndLat());
                if(null==coordinatesArray){
                    continue; //经纬度解析结果为 null，则跳出循环
                }
                geometryMap.put("coordinates",coordinatesArray);

                map.put("properties",vo);
                map.put("type","Feature");

                map.put("geometry",geometryMap);

                list.add(map);
            }
        }
        return list;
    }

    //经纬度字符串转数组
    private Double[] getCoordinates(String coordinates) {
        if (!StringUtils.isEmpty(coordinates) && coordinates.contains(",")) {
            String a[] = coordinates.split(",");
            Double.valueOf(a[0]);
            Double.valueOf(a[1]);
            Double[] result = {Double.valueOf(a[0]), Double.valueOf(a[1])};
            return result;
        } else {
            return null;
        }
    }

    /**
     * 浙能需求
     * 根据组织机构获取该机构下对应的危险源（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    public List<Map<String,Object>> searchAllHazardsForGisByOrg(String orgCode) {
        List<Map<String,Object>> list = null;
        //只给teamClient.findList的参数赋值
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);
        if(null != responseOrgVo){
            String orgId = responseOrgVo.getId();
            list = new ArrayList<>();
            HazardPageVo hazardPageVo = new HazardPageVo();
            hazardPageVo.setCreateOrgId(orgId);
            ResponseEntity<List<HazardVo>> resultVo = hazardClient.findList(hazardPageVo);
            List<HazardVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            if(!CollectionUtils.isEmpty(vos)){
                for(HazardVo vo : vos){
                    Map<String,Object> map = new HashMap<>();

                    Map<String,Object> geometryMap = new HashMap<>();
                    geometryMap.put("type","Point");
                    Double[] coordinatesArray = getCoordinates(vo.getLonAndLat());
                    if(null==coordinatesArray){
                        continue; //经纬度解析结果为 null，则跳出循环
                    }
                    geometryMap.put("coordinates",coordinatesArray);

                    map.put("properties",vo);
                    map.put("type","Feature");

                    map.put("geometry",geometryMap);

                    list.add(map);
                }
            }
        }

        return list;
    }

    /**
     * 浙能需求（复用代码有时间可以提取）
     * 根据组织机构获取该机构下对应的所有应急队伍（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     * @param hazardPageForGisVo
     * @return
     */
    public RestPageImpl<Map<String,Object>> findPage(PageForGisVo hazardPageForGisVo) {

        //声明要返回的集合 and content中要加的list
        RestPageImpl<Map<String,Object>> pageList = null;
        List<Map<String,Object>> list = null;

        Assert.notNull(hazardPageForGisVo,"hazardPageForGisVo 不能为空");
        //拿到page size orgCode
        int page = hazardPageForGisVo.getPage();
        int size = hazardPageForGisVo.getSize();
        String orgCode = hazardPageForGisVo.getOrgCode();
        //根据orgCode获取 orgVo
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);

        if(null != responseOrgVo){
            HazardPageVo hazardPageVo = new HazardPageVo();
            hazardPageVo.setPage(page);
            hazardPageVo.setSize(size);
            hazardPageVo.setCreateOrgId(responseOrgVo.getId());
            //获取page的result
            ResponseEntity<RestPageImpl<HazardVo>> resultVo = hazardClient.findPage(hazardPageVo);
            RestPageImpl<HazardVo> teamVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

            list = new ArrayList<>();

            List<HazardVo> content = teamVo.getContent();
            if(!CollectionUtils.isEmpty(content)){
                for(HazardVo vo : content){
                    Map<String,Object> map = new HashMap<>();

                    Map<String,Object> geometryMap = new HashMap<>();
                    geometryMap.put("type","Point");
                    Double[] coordinatesArray = getCoordinates(vo.getLonAndLat());
                    if(null==coordinatesArray){
                        continue; //经纬度解析结果为 null，则跳出循环
                    }
                    geometryMap.put("coordinates",coordinatesArray);

                    map.put("properties",vo);
                    map.put("type","Feature");

                    map.put("geometry",geometryMap);

                    list.add(map);
                }
                //这里只需要把得到的list放到page的content里就可以了
                pageList = new RestPageImpl<>(list);
            }
        }
        return pageList;
    }

    /**
     * 根据查询条件导出危险源信息Excel
     * @param os
     * @param hazardPageVo
     */
    public void exportToExcel(OutputStream os, HazardPageVo hazardPageVo)
            throws IOException,IndexOutOfBoundsException{
        List<HazardVo> list = findList(hazardPageVo);
        String fileName = ExcelGlobal.HAZARD_FILE_NAME;
        //导出EXCEL文件
        process(os,fileName,list);
    }

    private void process(OutputStream os, String fileName, List<HazardVo> list)
            throws IOException,IndexOutOfBoundsException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        String array [] = ExcelGlobal.HAZARD_ARRAY;
        int lastCol = array.length - 1;
        //1、标题
        title(workbook,sheet,fileName,lastCol);

        //2、表头样式
        HSSFCellStyle colStyle = tableRowCellStyle(workbook);

        //3、表头
        HSSFRow tableRow = sheet.createRow(1);

        HSSFCell blankCell0 = tableRow.createCell(0);
        blankCell0.setCellStyle(colStyle);
        blankCell0.setCellValue(array[0]);

        HSSFCell blankCell1 = tableRow.createCell(1);
        blankCell1.setCellStyle(colStyle);
        blankCell1.setCellValue(array[1]);

        HSSFCell blankCell2 = tableRow.createCell(2);
        blankCell2.setCellStyle(colStyle);
        blankCell2.setCellValue(array[2]);

        HSSFCell blankCell3 = tableRow.createCell(3);
        blankCell3.setCellStyle(colStyle);
        blankCell3.setCellValue(array[3]);

        HSSFCell blankCell4 = tableRow.createCell(4);
        blankCell4.setCellStyle(colStyle);
        blankCell4.setCellValue(array[4]);

        HSSFCell blankCell5 = tableRow.createCell(5);
        blankCell5.setCellStyle(colStyle);
        blankCell5.setCellValue(array[5]);

        HSSFCell blankCell6 = tableRow.createCell(6);
        blankCell6.setCellStyle(colStyle);
        blankCell6.setCellValue(array[6]);

        HSSFCell blankCell7 = tableRow.createCell(7);
        blankCell7.setCellStyle(colStyle);
        blankCell7.setCellValue(array[7]);

        //4、数据体样式
        HSSFCellStyle dataStyle = dataStyle(workbook);
        //设置每列数据的宽度
        for(int i = 0 ;i< array.length;i++){
            sheet.setColumnWidth(i,5000);
        }

        int rowData = 2;
        for (int i=0;i< list.size();i++){
            HazardVo vo = list.get(i);
            HSSFRow row = sheet.createRow(rowData + i);
            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(i+1);//序号
            row0.setCellStyle(dataStyle);
            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(vo.getName());//危险源名称
            row1.setCellStyle(dataStyle);
            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(vo.getDanTypeName());//危险源类别
            row2.setCellStyle(dataStyle);
            HSSFCell row3 = row.createCell(3);
            row3.setCellValue(vo.getDanGradeName());//危险源级别
            row3.setCellStyle(dataStyle);
            HSSFCell row4 = row.createCell(4);
            row4.setCellValue(vo.getUnit());//所属单位
            row4.setCellStyle(dataStyle);
            HSSFCell row5 = row.createCell(5);
            row5.setCellValue(vo.getAddress());//危险源地址
            row5.setCellStyle(dataStyle);

            HSSFCell row6 = row.createCell(6);
            row6.setCellValue(vo.getDescribes());//危险源描述
            row6.setCellStyle(dataStyle);
            HSSFCell row7 = row.createCell(7);
            row7.setCellValue(vo.getMajorHazard());//主要危险物
            row7.setCellStyle(dataStyle);
        }
        workbook.write(os);
        os.flush();
        os.close();
    }


}
