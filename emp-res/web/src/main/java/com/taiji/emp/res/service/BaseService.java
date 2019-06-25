package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.feign.DicItemClient;
import com.taiji.emp.res.feign.UserClient;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:BaseService.java</p >
 * <p>Description: Map转换类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
public class BaseService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private DicItemClient dicItemClient;

    public MultiValueMap<String,Object> convertMap2MultiValueMap( Map<String,Object> sourceM ){
        MultiValueMap<String,Object> multiValueMap = new LinkedMultiValueMap<>();
        for (String key : sourceM.keySet()) {
            Object obj = sourceM.get(key);
            if( obj instanceof List){
                for (Object o : (List)obj) {
                    multiValueMap.add(key,o);
                }
            }else{
                multiValueMap.add(key,sourceM.get(key));
            }
        }

        return multiValueMap;
    }

    //获取用户信息
    public UserVo getCurrentUser(Principal principal){

        String userAccount = principal.getName();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("account",userAccount);
        MultiValueMap<String, Object> multiValueMap = convertMap2MultiValueMap(userMap);
        UserVo userVo = userClient.findOne(multiValueMap).getBody();
        return userVo;
    }

    //根据字典项id获取字典项对象 --- 返还带有 dicItemName 的vo
    public DicGroupItemVo getItemById(String id){
        Assert.hasText(id,"dicItem id不能为空字符串");
        ResponseEntity<DicGroupItemVo> resultVo = dicItemClient.find(id);
        DicGroupItemVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    //根据字典项id获取字典项名称
    public String getItemNameById(String id){
        Assert.hasText(id,"dicItem id不能为空字符串");
        ResponseEntity<String> result = dicItemClient.findItemNameById(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //ids:使用英文逗号连接的 字典项id
    //返回使用英文逗号连接字典项名称
    public String getItemNamesByIds(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        if(null!=idList){
            ResponseEntity<String> result = dicItemClient.findItemNamesByIds(idList);
            return ResponseEntityUtils.achieveResponseEntityBody(result);
        }else {
            return null;
        }
    }

    /**
     * EXCEL---标题的代码
     */
    public static void title(HSSFWorkbook workbook,HSSFSheet sheet,String fileName,int lastCol){
        //1、标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);//字体大小；
        font.setFontName("宋体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗

        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setFont(font);

        //2、标题
        HSSFRow titleRow = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) lastCol));//合并单元格
        HSSFCell cell = titleRow.createCell(0);//第一个单元格
        cell.setCellStyle(titleStyle);
        cell.setCellValue(fileName);
    }

    /**
     * EXCEL---表头样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle tableRowCellStyle(HSSFWorkbook workbook){
        //表头样式
        HSSFCellStyle colStyle = workbook.createCellStyle();
        Font colFont = workbook.createFont();
        colFont.setFontHeightInPoints((short) 18);//字体大小；
        colFont.setFontName("宋体");
        colFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        colStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        colStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        colStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        colStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        colStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        colStyle.setFont(colFont);
        return colStyle;
    }

    /**
     * EXCEL---数据体样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle dataStyle(HSSFWorkbook workbook){
        HSSFCellStyle dataStyle = workbook.createCellStyle();
        Font dataFont = workbook.createFont();
        dataFont.setFontHeightInPoints((short) 14);//字体大小；
        dataFont.setFontName("宋体");

        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataStyle.setFont(dataFont);
        return dataStyle;
    }
}
