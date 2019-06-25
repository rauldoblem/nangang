package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.common.constant.ExcelGlobal;
import com.taiji.emp.res.feign.DicItemClient;
import com.taiji.emp.res.feign.OrgClient;
import com.taiji.emp.res.feign.TargetClient;
import com.taiji.emp.res.searchVo.target.TargetSearchVo;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.emp.res.vo.TargetVo;
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
public class TargetService extends BaseService {

    @Autowired
    private TargetClient targetClient;

    @Autowired
    private DicItemClient dicItemClient;

    @Autowired
    private OrgClient orgClient;

    /**
     * 新增防护目标
     * @param vo
     * @param principal
     */
    public void create(TargetVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        String userName = principal.getName();

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        String targetTypeId = vo.getTargetTypeId();

        Assert.hasText(targetTypeId,"目标类型ID 不能为空字符串");
        vo.setTargetTypeName(getItemNameById(targetTypeId));

        vo.setCreateOrgId(userVoProfile.getOrgId());  //创建单位id
        vo.setCreateOrgName(userVoProfile.getOrgName()); //创建单位名称

        //防护目标实体入库
        targetClient.create(vo);
    }

    /**
     * 更新防护目标记录
     * @param vo
     * @param principal
     */
    public void update(TargetVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        //获取用户姓名
        String userName = principal.getName();
        //更新人
        vo.setUpdateBy(userName);
        //创建单位id
        vo.setCreateOrgId(userProfileVo.getOrgId());
        //创建单位名称
        vo.setCreateOrgName(userProfileVo.getOrgName());

        String targetTypeId = vo.getTargetTypeId();
        Assert.hasText(targetTypeId,"目标类型ID 不能为空字符串");
        TargetVo tempVo = this.findOne(vo.getId());
        if (!targetTypeId.equals(tempVo.getTargetTypeId())){
            //目标类型有修改才替换
            vo.setTargetTypeName(getItemNameById(targetTypeId));
        }
        //防护目标修改实体入库
        targetClient.update(vo, vo.getId());
    }

    /**
     * 根据id逻辑删除单条防护目标记录
     * @param id
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = targetClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取防护目标分页list
     * @param searchVo
     * @return
     */
    public RestPageImpl<TargetVo> findPage(TargetSearchVo searchVo){
        Assert.notNull(searchVo,"params 不能为空");
        ResponseEntity<RestPageImpl<TargetVo>> resultVo = targetClient.findPage(searchVo);
        RestPageImpl<TargetVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取防护目标list(不带分页)
     * @param targetSearchVo
     * @return
     */
    public List<TargetVo> findList(TargetSearchVo targetSearchVo){
        ResponseEntity<List<TargetVo>> resultVo = targetClient.findList(targetSearchVo);
        List<TargetVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id获取单条防护目标记录
     * @param id
     * @return
     */
    public TargetVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<TargetVo> resultVo = targetClient.findOne(id);
        TargetVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据GIS数据格式要求获取防护目标图层信息-不分页
     */
    public List<Map<String,Object>> searchAllTargetForGis(){
        List<Map<String,Object>> list = new ArrayList<>();
        TargetSearchVo searchVo = new TargetSearchVo();
        ResponseEntity<List<TargetVo>> resultVo = targetClient.findList(searchVo);
        List<TargetVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(!CollectionUtils.isEmpty(vos)){
            for(TargetVo vo : vos){
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
     * 根据组织机构获取该机构下对应的所有防护目标（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    public List<Map<String,Object>> searchAllTargetForGisByOrg(String orgCode) {
        List<Map<String,Object>> list = null;
        //只给targetClient.findList的参数赋值
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);
        if(null != responseOrgVo){
            String orgId = responseOrgVo.getId();
            list = new ArrayList<>();
            TargetSearchVo targetSearchVo = new TargetSearchVo();
            targetSearchVo.setCreateOrgId(orgId);
            ResponseEntity<List<TargetVo>> resultVo = targetClient.findList(targetSearchVo);
            List<TargetVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            if(!CollectionUtils.isEmpty(vos)){
                for(TargetVo vo : vos){
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
     * @param targetPageForGisVo
     * @return
     */
    public RestPageImpl<Map<String,Object>> findPage(PageForGisVo targetPageForGisVo) {

        //声明要返回的集合 and content中要加的list
        RestPageImpl<Map<String,Object>> pageList = null;
        List<Map<String,Object>> list = null;

        Assert.notNull(targetPageForGisVo,"targetPageForGisVo 不能为空");
        //拿到page size orgCode
        int page = targetPageForGisVo.getPage();
        int size = targetPageForGisVo.getSize();
        String orgCode = targetPageForGisVo.getOrgCode();
        //根据orgCode获取 orgVo
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);

        if(null != responseOrgVo){
            TargetSearchVo targetSearchVo = new TargetSearchVo();
            targetSearchVo.setPage(page);
            targetSearchVo.setSize(size);
            targetSearchVo.setCreateOrgId(responseOrgVo.getId());
            //获取page的result
            ResponseEntity<RestPageImpl<TargetVo>> resultVo = targetClient.findPage(targetSearchVo);
            RestPageImpl<TargetVo> teamVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

            list = new ArrayList<>();

            List<TargetVo> content = teamVo.getContent();
            if(!CollectionUtils.isEmpty(content)){
                for(TargetVo vo : content){
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
     * 根据查询条件导出防护目标信息Excel
     * @param os
     * @param targetSearchVo
     */
    public void exportToExcel(OutputStream os, TargetSearchVo targetSearchVo)
            throws IOException,IndexOutOfBoundsException{
        List<TargetVo> list = findList(targetSearchVo);
        String fileName = ExcelGlobal.TARGET_FILE_NAME;
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
    private void process(OutputStream os, String fileName, List<TargetVo> list)
            throws IOException,IndexOutOfBoundsException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        String array [] = ExcelGlobal.TARGET_ARRAY;
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
            TargetVo vo = list.get(i);
            HSSFRow row = sheet.createRow(rowData + i);
            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(i+1);//序号
            row0.setCellStyle(dataStyle);
            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(vo.getName());//目标名称
            row1.setCellStyle(dataStyle);
            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(vo.getTargetTypeName());//目标类别
            row2.setCellStyle(dataStyle);
            HSSFCell row3 = row.createCell(3);
            row3.setCellValue(vo.getUnit());//所属单位
            row3.setCellStyle(dataStyle);
            HSSFCell row4 = row.createCell(4);
            row4.setCellValue(vo.getPrincipal());//联系人
            row4.setCellStyle(dataStyle);
            HSSFCell row5 = row.createCell(5);
            row5.setCellValue(vo.getAddress());//目标地址
            row5.setCellStyle(dataStyle);

            HSSFCell row6 = row.createCell(6);
            row6.setCellValue(vo.getDescribes());//目标描述
            row6.setCellStyle(dataStyle);
            HSSFCell row7 = row.createCell(7);
            row7.setCellValue(vo.getPrincipalTel());//联系方式
            row7.setCellStyle(dataStyle);
        }
        workbook.write(os);
        os.flush();
        os.close();
    }
}
