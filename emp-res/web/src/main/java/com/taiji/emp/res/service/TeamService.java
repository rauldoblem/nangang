package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.common.constant.ExcelGlobal;
import com.taiji.emp.res.feign.OrgClient;
import com.taiji.emp.res.feign.TeamClient;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.searchVo.team.TeamPageVo;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.emp.res.vo.TeamVo;
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
public class TeamService extends BaseService{

    @Autowired
    private TeamClient teamClient;
    @Autowired
    private OrgClient orgClient;

    //新增救援队伍
    public void create(TeamVo teamVo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = principal.getName();

        teamVo.setCreateBy(account); //创建人
        teamVo.setUpdateBy(account); //更新人

        teamVo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        teamVo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        String teamTypeId = teamVo.getTeamTypeId(); //队伍类型ID
        Assert.hasText(teamTypeId,"teamTypeId 不能为空");
        teamVo.setTeamTypeName(getItemNameById(teamTypeId));

        String propertyId = teamVo.getPropertyId(); //队伍性质ID
        Assert.hasText(propertyId,"propertyId 不能为空");
        teamVo.setPropertyName(getItemNameById(propertyId));

        ResponseEntity<TeamVo> resultVo = teamClient.create(teamVo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //更新救援队伍
    public void update(TeamVo teamVo,String id, Principal principal){

        String account = principal.getName();

        teamVo.setUpdateBy(account); //更新人

        String teamTypeId = teamVo.getTeamTypeId(); //队伍类型ID
        Assert.hasText(teamTypeId,"teamTypeId 不能为空");
        String propertyId = teamVo.getPropertyId(); //队伍性质ID
        Assert.hasText(propertyId,"propertyId 不能为空");
        Assert.hasText(id,"id 不能为空");

        TeamVo vo =findOne(teamVo.getId());
        if(!teamTypeId.equals(vo.getTeamTypeId())){
            teamVo.setTeamTypeName(getItemNameById(teamTypeId));
        }else{
            teamVo.setTeamTypeName(null);
        }
        if(!propertyId.equals(vo.getPropertyId())){
            teamVo.setPropertyName(getItemNameById(propertyId));
        }else{
            teamVo.setPropertyName(null);
        }

        ResponseEntity<TeamVo> resultVo = teamClient.update(teamVo,teamVo.getId());
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //获取单条记录
    public TeamVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<TeamVo> resultVo = teamClient.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = teamClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取救援队伍分页list
     */
    public RestPageImpl<TeamVo> findPage(TeamPageVo teamPageVo){
        Assert.notNull(teamPageVo,"teamPageVo 不能为空");
        ResponseEntity<RestPageImpl<TeamVo>> resultVo = teamClient.findPage(teamPageVo);
        RestPageImpl<TeamVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取救援队伍不分页list
     */
    public List<TeamVo> findList(TeamListVo teamListVo){
        Assert.notNull(teamListVo,"teamListVo 不能为空");
        ResponseEntity<List<TeamVo>> resultVo = teamClient.findList(teamListVo);
        List<TeamVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据GIS数据格式要求获取救援队伍图层信息-不分页
     */
    public List<Map<String,Object>> searchAllTeamForGis(){
        List<Map<String,Object>> list = new ArrayList<>();
        TeamListVo teamListVo = new TeamListVo();
        ResponseEntity<List<TeamVo>> resultVo = teamClient.findList(teamListVo);
        List<TeamVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(!CollectionUtils.isEmpty(vos)){
            for(TeamVo vo : vos){
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
     * 根据组织机构获取该机构下对应的所有应急队伍（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     * @param orgCode
     * @return
     */
    public List<Map<String,Object>> searchAllTeamForGisByOrg(String orgCode){
        List<Map<String,Object>> list = null;
        //只给teamClient.findList的参数赋值
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);
        if(null != responseOrgVo){
            String orgId = responseOrgVo.getId();
            list = new ArrayList<>();
            TeamListVo teamListVo = new TeamListVo();
            teamListVo.setCreateOrgId(orgId);
            ResponseEntity<List<TeamVo>> resultVo = teamClient.findList(teamListVo);
            List<TeamVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            if(!CollectionUtils.isEmpty(vos)){
                for(TeamVo vo : vos){
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
     * @param teamPageForGisVo
     * @return
     */
    public RestPageImpl<Map<String,Object>> findPage(PageForGisVo teamPageForGisVo) {

        //声明要返回的集合 and content中要加的list
        RestPageImpl<Map<String,Object>> pageList = null;
        List<Map<String,Object>> list = null;

        Assert.notNull(teamPageForGisVo,"teamPageForGisVo 不能为空");
        //拿到page size orgCode
        int page = teamPageForGisVo.getPage();
        int size = teamPageForGisVo.getSize();
        String orgCode = teamPageForGisVo.getOrgCode();
        //根据orgCode获取 orgVo
        ResponseEntity<OrgVo> orgVo = orgClient.findIdByOrgZnCode(orgCode);
        OrgVo responseOrgVo = ResponseEntityUtils.achieveResponseEntityBody(orgVo);

        if(null != responseOrgVo){
            TeamPageVo teamPageVo = new TeamPageVo();
            teamPageVo.setPage(page);
            teamPageVo.setSize(size);
            teamPageVo.setCreateOrgId(responseOrgVo.getId());
            //获取page的result
            ResponseEntity<RestPageImpl<TeamVo>> resultVo = teamClient.findPage(teamPageVo);
            RestPageImpl<TeamVo> teamVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

            list = new ArrayList<>();

            List<TeamVo> content = teamVo.getContent();
            if(!CollectionUtils.isEmpty(content)){
                for(TeamVo vo : content){
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
     * 根据查询条件导出救援队伍信息Excel
     * @param os
     * @param teamListVo
     */
    public void exportToExcel(OutputStream os, TeamListVo teamListVo)
            throws IOException,IndexOutOfBoundsException{
        List<TeamVo> list = findList(teamListVo);
        String fileName = ExcelGlobal.TEAM_FILE_NAME;
        process(os,fileName,list);
    }

    /**
     * 导出EXCEL文件
     * @param os
     * @param fileName
     * @param list
     */
    private void process(OutputStream os, String fileName, List<TeamVo> list)
            throws IOException,IndexOutOfBoundsException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        String array [] = ExcelGlobal.TEAM_ARRAY;
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

        HSSFCell blankCell8 = tableRow.createCell(8);
        blankCell8.setCellStyle(colStyle);
        blankCell8.setCellValue(array[8]);

        HSSFCell blankCell9 = tableRow.createCell(9);
        blankCell9.setCellStyle(colStyle);
        blankCell9.setCellValue(array[9]);

        //4、数据体样式
        HSSFCellStyle dataStyle = dataStyle(workbook);
        //设置每列数据的宽度
        for(int i = 0 ;i< array.length;i++){
            sheet.setColumnWidth(i,5000);
        }

        int rowData = 2;
        for (int i=0;i< list.size();i++){
            TeamVo vo = list.get(i);
            HSSFRow row = sheet.createRow(rowData + i);
            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(i+1);//序号
            row0.setCellStyle(dataStyle);
            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(vo.getName());//队伍名称
            row1.setCellStyle(dataStyle);
            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(vo.getTeamTypeName());//队伍类型
            row2.setCellStyle(dataStyle);
            HSSFCell row3 = row.createCell(3);
            row3.setCellValue(vo.getUnit());//所属单位
            row3.setCellStyle(dataStyle);
            HSSFCell row4 = row.createCell(4);
            row4.setCellValue(vo.getAddress());//常驻地址
            row4.setCellStyle(dataStyle);
            HSSFCell row5 = row.createCell(5);
            row5.setCellValue(vo.getPrincipal());//负责人
            row5.setCellStyle(dataStyle);
            HSSFCell row6 = row.createCell(6);
            row6.setCellValue(vo.getPrincipalTel());//负责人手机
            row6.setCellStyle(dataStyle);
            HSSFCell row7 = row.createCell(7);
            row7.setCellValue(vo.getTeamNum());//队伍人数
            row7.setCellStyle(dataStyle);
            HSSFCell row8 = row.createCell(8);
            row8.setCellValue(vo.getSpecialty());//专业及特长
            row8.setCellStyle(dataStyle);
            HSSFCell row9 = row.createCell(9);
            row9.setCellValue(vo.getDutyTel());//值班电话
            row9.setCellStyle(dataStyle);
        }
        workbook.write(os);
        os.flush();
        os.close();
    }
}
