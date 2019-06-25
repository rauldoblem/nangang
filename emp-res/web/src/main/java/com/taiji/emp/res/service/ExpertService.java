package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.common.constant.ExcelGlobal;
import com.taiji.emp.res.feign.ExpertClient;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;

@Service
public class ExpertService extends BaseService{

    @Autowired
    private ExpertClient expertClient;

    //新增应急专家
    public void create(ExpertVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = principal.getName();

        vo.setCreateBy(account); //创建人
        vo.setUpdateBy(account); //更新人

        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        String eventTypeIds = vo.getEventTypeIds();
        Assert.hasText(eventTypeIds,"eventTypeIds 不能为空");
        vo.setEventTypeNames(getItemNamesByIds(eventTypeIds));

        ResponseEntity<ExpertVo> resultVo = expertClient.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //修改应急专家
    public void update(ExpertVo vo,String id, Principal principal){

        String account = principal.getName();

        vo.setUpdateBy(account); //更新人

        String eventTypeIds = vo.getEventTypeIds();
        Assert.hasText(eventTypeIds,"eventTypeIds 不能为空");
        Assert.hasText(id,"id 不能为空");

        ExpertVo tempVo = findOne(vo.getId());
        if(!eventTypeIds.equals(tempVo.getEventTypeIds())){ //有修改才更新name
            vo.setEventTypeNames(getItemNamesByIds(eventTypeIds));
        }else{
            vo.setEventTypeNames(null);
        }

        ResponseEntity<ExpertVo> resultVo = expertClient.update(vo,vo.getId());
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 根据id获取单条应急知识记录
     */
    public ExpertVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<ExpertVo> resultVo = expertClient.findOne(id);
        ExpertVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = expertClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取应急专家分页list
     */
    public RestPageImpl<ExpertVo> findPage(ExpertPageVo expertPageVo){
        Assert.notNull(expertPageVo,"expertPageVo 不能为空");
        ResponseEntity<RestPageImpl<ExpertVo>> resultVo = expertClient.findPage(expertPageVo);
        RestPageImpl<ExpertVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取应急专家list(不带分页)
     */
    public List<ExpertVo> findList(ExpertListVo expertListVo){
        Assert.notNull(expertListVo,"expertListVo 不能为空");
        ResponseEntity<List<ExpertVo>> resultVo = expertClient.findList(expertListVo);
        List<ExpertVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据查询条件导出专家信息Excel
     * @param expertListVo
     */
    public void exportToExcel(OutputStream os, ExpertListVo expertListVo)
            throws IOException,IndexOutOfBoundsException{
        List<ExpertVo> list = findList(expertListVo);
        String fileName = ExcelGlobal.EXPERT_FILE_NAME;
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
    private void process(OutputStream os,String fileName,List<ExpertVo> list)
            throws IOException,IndexOutOfBoundsException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        String array [] = ExcelGlobal.EXPERT_ARRAY;
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

        //4、数据体样式
        HSSFCellStyle dataStyle = dataStyle(workbook);
        //设置每列数据的宽度
        for(int i = 0 ;i< array.length;i++){
            sheet.setColumnWidth(i,5000);
        }

        int rowData = 2;
        for (int i=0;i< list.size();i++){
            ExpertVo vo = list.get(i);
            HSSFRow row = sheet.createRow(rowData + i);
            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(i+1);//序号
            row0.setCellStyle(dataStyle);
            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(vo.getName());//姓名
            row1.setCellStyle(dataStyle);
            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(vo.getUnit());//工作单位
            row2.setCellStyle(dataStyle);
            HSSFCell row3 = row.createCell(3);
            row3.setCellValue(vo.getTelephone());//联系方式
            row3.setCellStyle(dataStyle);
            HSSFCell row4 = row.createCell(4);
            row4.setCellValue(vo.getEventTypeNames());//事件类型
            row4.setCellStyle(dataStyle);
            HSSFCell row5 = row.createCell(5);
            row5.setCellValue(vo.getSpecialty());//专业及特长
            row5.setCellStyle(dataStyle);
        }
        workbook.write(os);
        os.flush();
        os.close();
    }
}
