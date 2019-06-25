package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.common.constant.ExcelGlobal;
import com.taiji.emp.res.feign.MaterialClient;
import com.taiji.emp.res.feign.OrgClient;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.material.MaterialPageVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
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
public class MaterialService extends BaseService{

    @Autowired
    private MaterialClient materialClient;
    @Autowired
    private OrgClient orgClient;

    //新增应急物资
    public void create(MaterialVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

//        String repertoryIds = vo.getRepertoryId();
//        Assert.hasText(repertoryIds,"repertoryIds 不能为空");
//        vo.setRepertoryName(getItemNamesByIds(repertoryIds));
//
//        String positionIds = vo.getPositionId();
//        Assert.hasText(positionIds,"positionIds 不能为空");
//        vo.setPositionName(getItemNamesByIds(positionIds));

        String resTypeIds = vo.getResTypeId();
        Assert.hasText(resTypeIds,"resTypeIds 不能为空");
        vo.setResTypeName(getItemNameById(resTypeIds));

        ResponseEntity<MaterialVo> resultVo = materialClient.create(vo);

    }

    //修改应急物资
    public void update(MaterialVo vo, Principal principal,String id){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setUpdateBy(userName); //更新人

//        String repertoryIds = vo.getRepertoryId();
//        Assert.hasText(repertoryIds,"repertoryIds 不能为空");
          MaterialVo tempVo = findOne(vo.getId());
//        if(!repertoryIds.equals(tempVo.getRepertoryId())){ //有修改才更新name
//            vo.setRepertoryName(getItemNamesByIds(repertoryIds));
//        }else{
//            vo.setRepertoryName(null);
//        }
//
//        String positionIds = vo.getPositionId();
//        Assert.hasText(positionIds,"positionIds 不能为空");
//        if(!positionIds.equals(tempVo.getPositionId())){ //有修改才更新name
//            vo.setPositionName(getItemNamesByIds(positionIds));
//        }else{
//            vo.setPositionName(null);
//        }

        String resTypeIds = vo.getResTypeId();
        Assert.hasText(resTypeIds,"resTypeIds 不能为空");
        if(!resTypeIds.equals(tempVo.getResTypeId())){ //有修改才更新name
            vo.setResTypeName(getItemNamesByIds(resTypeIds));
        }else{
            vo.setResTypeName(null);
        }
        ResponseEntity<MaterialVo> resultVo = materialClient.update(vo,id);

    }

    /**
     * 根据id获取单条应急物资记录
     */
    public MaterialVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<MaterialVo> resultVo = materialClient.findOne(id);
        MaterialVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = materialClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取应急物资分页list
     */
    public RestPageImpl<MaterialVo> findPage(MaterialPageVo materialPageVo){
        Assert.notNull(materialPageVo,"materialPageVo 不能为空");
        ResponseEntity<RestPageImpl<MaterialVo>> resultVo = materialClient.findPage(materialPageVo);
        RestPageImpl<MaterialVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取应急物资list(不带分页)
     */
    public List<MaterialVo> findList(MaterialListVo materialListVo){
        Assert.notNull(materialListVo,"materialListVo 不能为空");
        ResponseEntity<List<MaterialVo>> resultVo = materialClient.findList(materialListVo);
        List<MaterialVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据GIS数据格式要求获取应急物资图层信息-不分页
     */
    public List<Map<String,Object>> searchAllMaterialForGis(){
        List<Map<String,Object>> list = new ArrayList<>();
        MaterialListVo materialListVo = new MaterialListVo();
        ResponseEntity<List<MaterialVo>> resultVo = materialClient.findList(materialListVo);
        List<MaterialVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(!CollectionUtils.isEmpty(vos)){
            for(MaterialVo vo : vos){
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
    private Double[] getCoordinates(String coordinates){
        if(!StringUtils.isEmpty(coordinates)&&coordinates.contains(",")){
            String[] a =  coordinates.split(",");
            Double.valueOf(a[0]);
            Double.valueOf(a[1]);
            Double[] result = {Double.valueOf(a[0]),Double.valueOf(a[1])};
            return result;
        }else{
            return null;
        }
    }

    /**
     * 浙能需求
     * 根据组织机构获取该机构下对应的所有应急物资（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    public List<Map<String,Object>> searchAllMaterialForGisByOrg(String orgCode) {
        List<Map<String,Object>> list = null;
        //materialClient.findList的参数赋值
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);
        if(null != responseOrgVo){
            String orgId = responseOrgVo.getId();
            list = new ArrayList<>();
            MaterialListVo materialListVo = new MaterialListVo();
            materialListVo.setCreateOrgId(orgId);
            ResponseEntity<List<MaterialVo>> resultVo = materialClient.findList(materialListVo);
            List<MaterialVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            if(!CollectionUtils.isEmpty(vos)){
                for(MaterialVo vo : vos){
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
     * 根据组织机构获取该机构下对应的所有应急物资（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     */
    public RestPageImpl<Map<String,Object>> findPage(PageForGisVo materialPageForGisVo) {

        //声明要返回的集合 and content中要加的list
        RestPageImpl<Map<String,Object>> pageList = null;
        List<Map<String,Object>> list = null;

        Assert.notNull(materialPageForGisVo,"teamPageForGisVo 不能为空");
        //拿到page size orgCode
        int page = materialPageForGisVo.getPage();
        int size = materialPageForGisVo.getSize();
        String orgCode = materialPageForGisVo.getOrgCode();
        //根据orgCode获取 orgVo
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);

        if(null != responseOrgVo){
            MaterialPageVo materialPageVo = new MaterialPageVo();
            materialPageVo.setPage(page);
            materialPageVo.setSize(size);
            materialPageVo.setCreateOrgId(responseOrgVo.getId());
            //获取page的result
            ResponseEntity<RestPageImpl<MaterialVo>> resultVo = materialClient.findPage(materialPageVo);
            RestPageImpl<MaterialVo> teamVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

            list = new ArrayList<>();

            List<MaterialVo> content = teamVo.getContent();
            if(!CollectionUtils.isEmpty(content)){
                for(MaterialVo vo : content){
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
     * 根据查询条件导出应急物资信息Excel
     * @param os
     * @param materialListVo
     * @throws IOException
     * @throws IndexOutOfBoundsException
     */
    public void exportToExcel(OutputStream os, MaterialListVo materialListVo)
            throws IOException,IndexOutOfBoundsException{
        List<MaterialVo> list = findList(materialListVo);
        String fileName = ExcelGlobal.MATERIAL_FILE_NAME;
        //导出EXCEL文件
        process(os,fileName,list);
    }


    private void process(OutputStream os, String fileName, List<MaterialVo> list)
            throws IOException,IndexOutOfBoundsException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        String array [] = ExcelGlobal.MATERIAL_ARRAY;
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

        //4、数据体样式
        HSSFCellStyle dataStyle = dataStyle(workbook);
        //设置每列数据的宽度
        for(int i = 0 ;i< array.length;i++){
            sheet.setColumnWidth(i,5000);
        }

        int rowData = 2;
        for (int i=0;i< list.size();i++){
            MaterialVo vo = list.get(i);
            HSSFRow row = sheet.createRow(rowData + i);
            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(i+1);//序号
            row0.setCellStyle(dataStyle);
            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(vo.getName());//物资名称
            row1.setCellStyle(dataStyle);
            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(vo.getResTypeName());//物资类型
            row2.setCellStyle(dataStyle);
            HSSFCell row3 = row.createCell(3);
            row3.setCellValue(vo.getSpecModel());//规格型号
            row3.setCellStyle(dataStyle);
            HSSFCell row4 = row.createCell(4);
            row4.setCellValue(vo.getUnitMeasure());//计量单位
            row4.setCellStyle(dataStyle);
            HSSFCell row5 = row.createCell(5);
            row5.setCellValue(vo.getInitialQuantity());//初始数量
            row5.setCellStyle(dataStyle);

            HSSFCell row6 = row.createCell(6);
            row6.setCellValue(vo.getUnit());//管理单位
            row6.setCellStyle(dataStyle);
        }
        workbook.write(os);
        os.flush();
        os.close();
    }
//
//    public static void main(String[] args) {
//        System.out.print(getCoordinates("123,456").toString());
//        Double[] a = getCoordinates("123,456");
//        System.out.print(a);
//    }

}
