package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.common.constant.ExcelGlobal;
import com.taiji.emp.res.feign.OrgClient;
import com.taiji.emp.res.feign.SupportClient;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.support.SupportPageVo;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.emp.res.vo.SupportVo;
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
public class SupportService extends BaseService{

    @Autowired
    private SupportClient supportClient;
    @Autowired
    private OrgClient orgClient;

    //新增社会依托资源
    public void create(SupportVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        String typeIds = vo.getTypeId();
        Assert.hasText(typeIds,"typeIds 不能为空");
        vo.setTypeName(getItemNameById(typeIds));

        ResponseEntity<SupportVo> resultVo = supportClient.create(vo);

    }

    //修改应急社会依托资源
    public void update(SupportVo vo, Principal principal,String id){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setUpdateBy(userName); //更新人

//        String suppTypeIds = vo.getSuppTypeId();
//        Assert.hasText(suppTypeIds,"suppTypeIds 不能为空");
//        SupportVo tempVo = findOne(vo.getId());
//        if(!suppTypeIds.equals(tempVo.getSuppTypeId())){ //有修改才更新name
//            vo.setSuppTypeName(getItemNamesByIds(suppTypeIds));
//        }else{
//            vo.setSuppTypeName(null);
//        }

        ResponseEntity<SupportVo> resultVo = supportClient.update(vo,id);

    }

    /**
     * 根据id获取单条应急社会依托资源
     */
    public SupportVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<SupportVo> resultVo = supportClient.findOne(id);
        SupportVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = supportClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取应急社会依托资源分页list
     */
    public RestPageImpl<SupportVo> findPage(SupportPageVo supportPageVo){
        Assert.notNull(supportPageVo,"supportPageVo 不能为空");
        ResponseEntity<RestPageImpl<SupportVo>> resultVo = supportClient.findPage(supportPageVo);
        RestPageImpl<SupportVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取应急社会依托资源list(不带分页)
     */
    public List<SupportVo> findList(SupportListVo supportListVo){
        Assert.notNull(supportListVo,"supportListVo 不能为空");
        ResponseEntity<List<SupportVo>> resultVo = supportClient.findList(supportListVo);
        List<SupportVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据GIS数据格式要求获取社会依托资源图层信息-不分页
     */
    public List<Map<String,Object>> searchAllSupportForGis(){
        List<Map<String,Object>> list = new ArrayList<>();
        SupportListVo supportListVo = new SupportListVo();
        ResponseEntity<List<SupportVo>> resultVo = supportClient.findList(supportListVo);
        List<SupportVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(!CollectionUtils.isEmpty(vos)){
            for(SupportVo vo : vos){
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
            String a[] =coordinates.split(",");
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
     * 根据组织机构获取该机构下对应的社会依托资源（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    public List<Map<String,Object>> searchAllSupportForGisByOrg(String orgCode) {

        List<Map<String,Object>> list = null;
        //只给supportClient.findList的参数赋值
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);
        if(null != responseOrgVo){
            String orgId = responseOrgVo.getId();
            list = new ArrayList<>();
            SupportListVo supportListVo = new SupportListVo();
            supportListVo.setCreateOrgId(orgId);
            ResponseEntity<List<SupportVo>> resultVo = supportClient.findList(supportListVo);
            List<SupportVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            if(!CollectionUtils.isEmpty(vos)){
                for(SupportVo vo : vos){
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
     * 浙能需求
     * 根据组织机构获取该机构下对应的所有应急队伍（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     * @param supportPageForGisVo
     * @return
     */
    public RestPageImpl<Map<String,Object>> findPage(PageForGisVo supportPageForGisVo) {

        //声明要返回的集合 and content中要加的list
        RestPageImpl<Map<String,Object>> pageList = null;
        List<Map<String,Object>> list = null;

        Assert.notNull(supportPageForGisVo,"supportPageForGisVo 不能为空");
        //拿到page size orgCode
        int page = supportPageForGisVo.getPage();
        int size = supportPageForGisVo.getSize();
        String orgCode = supportPageForGisVo.getOrgCode();
        //根据orgCode获取 orgVo
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);

        if(null != responseOrgVo){
            SupportPageVo supportPageVo = new SupportPageVo();
            supportPageVo.setPage(page);
            supportPageVo.setSize(size);
            supportPageVo.setCreateOrgId(responseOrgVo.getId());
            //获取page的result
            ResponseEntity<RestPageImpl<SupportVo>> resultVo = supportClient.findPage(supportPageVo);
            RestPageImpl<SupportVo> supportVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

            list = new ArrayList<>();

            List<SupportVo> content = supportVo.getContent();
            if(!CollectionUtils.isEmpty(content)){
                for(SupportVo vo : content){
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
     * 根据查询条件导出社会依托资源信息Excel
     * @param os
     * @param supportListVo
     */
    public void exportToExcel(OutputStream os, SupportListVo supportListVo)
            throws IOException,IndexOutOfBoundsException{
        List<SupportVo> list = findList(supportListVo);
        String fileName = ExcelGlobal.SUPPORT_FILE_NAME;
        //导出EXCEL文件
        process(os,fileName,list);
    }

    /**
     * 导出EXCEL文件
     * @param os
     * @param fileName
     * @param list
     * @throws IOException
     * @throws IndexOutOfBoundsException
     */
    private void process(OutputStream os, String fileName, List<SupportVo> list)
            throws IOException,IndexOutOfBoundsException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        String array [] = ExcelGlobal.SUPPORT_ARRAY;
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
            SupportVo vo = list.get(i);
            HSSFRow row = sheet.createRow(rowData + i);
            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(i+1);//序号
            row0.setCellStyle(dataStyle);
            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(vo.getName());//场所名称
            row1.setCellStyle(dataStyle);
            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(vo.getAddress());//地址
            row2.setCellStyle(dataStyle);
            HSSFCell row3 = row.createCell(3);
            row3.setCellValue("");//场所大小（㎡）-----待添加上
            row3.setCellStyle(dataStyle);
            HSSFCell row4 = row.createCell(4);
            row4.setCellValue(vo.getCapacity());//可容纳人数
            row4.setCellStyle(dataStyle);
            HSSFCell row5 = row.createCell(5);
            row5.setCellValue(vo.getNotes());//场所描述
            row5.setCellStyle(dataStyle);
            HSSFCell row6 = row.createCell(6);
            row6.setCellValue(vo.getCreateOrgName());//管理单位
            row6.setCellStyle(dataStyle);
        }
        workbook.write(os);
        os.flush();
        os.close();
    }
}
