package com.taiji.emp.base.service;

import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.feign.DocAttClient;
import com.taiji.emp.base.feign.UserClient;
import com.taiji.emp.base.util.FileUtil;
import com.taiji.emp.base.vo.DocAttDTO;
import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;

@Service
public class DocAttService extends BaseService{

    Logger logger = LoggerFactory.getLogger(DocAttService.class);

    @Autowired
    UserClient userClient;
    @Autowired
    DocAttClient docAttClient;

    //本地附件磁盘存储地址
    @Value("${local.file.storage.address}")
    private String receiveFileAddress;


    /**
     * 单附件上传方法
     * @param file
     * @param principal
     * */
    public DocAttDTO uploadsingle(MultipartFile file, Principal principal){
        //拿到用户信息
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        return uploadFile(file,userVo);
    }

    /**
     * 多附件上传方法
     *
     * @param principal
     */
    public List<DocAttDTO> uploadmultiple(MultipartFile[] files,Principal principal){
        //拿到用户信息
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        List<DocAttDTO> list = new ArrayList<>();
        for(MultipartFile file : files){
            if(file.isEmpty()){ //文件为empty则跳出
                continue;
            }
            list.add(uploadFile(file,userVo));
        }
        return list;
    }

    /**
     * 文件上传方法
     * @param file
     */
    public DocAttDTO uploadFile(MultipartFile file, UserVo userVo){

        String originalFileName = file.getOriginalFilename();

        //获取当前日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String subStr = formatter.format(new Date());

        if(!receiveFileAddress.endsWith(File.separator)){
            receiveFileAddress+=File.separator;
        }

        String relativePath = receiveFileAddress+subStr;  // 文件夹格式为：  D:/uploadFile_boot/yyyyMMdd

        File fileDir = new File(relativePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();  //判断是否存在该路径，若不存在则创建
        }

        String fileName = FileUtil.getFileName(originalFileName);
        if(!FileUtil.isValidName(fileName)){
            logger.error("上传的文件名 {} 含有非法字符！", fileName);
            return null;
        }

        String fileExt = FileUtil.getFileExtension(fileName); //文件后缀

        String savedFileName = FileUtil.generateRandomFileName(fileExt);  //随机文件名+后缀
        String savedFilePath = relativePath + File.separator + savedFileName;  //文件在本地磁盘保存路径

        String docAttRelativePath = subStr+File.separator + savedFileName; //入库的doc对象相对地址

        File newFile = new File(savedFilePath);

        try{
            file.transferTo(newFile);   //下载到本地
        }catch(IOException e){
            e.printStackTrace();
        }

        DocAttVo docAttVo = new DocAttVo();
        docAttVo.setName(fileName); //文件名称
        docAttVo.setLocation(docAttRelativePath); //文件相对路径
        docAttVo.setSuffix(fileExt);//文件后缀
        docAttVo.setUploadUserId(userVo.getId());//上传用户id
        docAttVo.setUploadUserName(userVo.getAccount()); //上传用户姓名
        ResponseEntity<DocAttVo> resultVo= docAttClient.createDoc(docAttVo);
        DocAttVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return new DocAttDTO(vo);
    }

    /**
     * 根据实体id获取附件列表
     */
    public List<DocAttDTO> findFilesByEntityId(String entityId){
        Assert.hasText(entityId,"业务id不能为空");
        ResponseEntity<List<DocAttVo>> resultVo = docAttClient.findList(entityId);
        List<DocAttVo> voList = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        List<DocAttDTO> dtoList = new ArrayList<>();
        if(null!=voList&&voList.size()>0){
            for(DocAttVo vo:voList){
                dtoList.add(new DocAttDTO(vo));
            }
        }
        return dtoList;
    }

    /**
     * 根据附件id获取附件对象
     */
    public DocAttDTO findDocByFileId(String fileId){
        Assert.hasText(fileId,"附件id不能为空");
        ResponseEntity<DocAttVo> resultVo = docAttClient.findOne(fileId);
        DocAttVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return new DocAttDTO(vo);
    }

    /**
     * 逻辑删除附件对象
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"附件id不能为空");
        docAttClient.deleteLogic(id);
    }

    /**
     * 给已上传的附件赋值实体id
     */
    public void saveDocEntity(DocEntityVo docEntityVo){
        docAttClient.saveDocEntity(docEntityVo);
    }

    /**
     * 单文件下载
     */
    public void downloadFile(String fileId,HttpServletResponse response){

        Assert.hasText(fileId,"附件id不能为空");
        ResponseEntity<DocAttVo> resultVo = docAttClient.findOne(fileId);
        DocAttVo doc = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        String relativePath = doc.getLocation();
        String fileName = doc.getName();
        if(!receiveFileAddress.endsWith(File.separator)){
            receiveFileAddress+=File.separator;
        }
        String allPath = receiveFileAddress+relativePath;
        File file = new File(allPath);
        if(!file.exists()){
            return;
        }
        try{
            downloadFile(response,file,fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * zip文件下载
     */
    public Boolean downloadZip(String entityId,HttpServletResponse response){
        Assert.hasText(entityId,"实体业务id不能为空");
        ResponseEntity<List<DocAttVo>> resultVo = docAttClient.findList(entityId);
        List<DocAttVo> voList = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        try{
            if(!CollectionUtils.isEmpty(voList)){
                downloadZip(voList,response);
                return true;
            }else{
                return false;
            }
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 压缩并下载文件zip
     */
    private void downloadZip(List<DocAttVo> docs,HttpServletResponse response) throws IOException{
        String zipFileName = FileUtil.generateRandomFileName("zip"); //待下载zip包名称
        if(!receiveFileAddress.endsWith(File.separator)){
            receiveFileAddress+=File.separator;
        }
        String zipPath = receiveFileAddress+zipFileName; //zip包路径

        //打压缩包过程----------------start------------

        //----压缩文件：
        FileOutputStream zipF = new FileOutputStream(new File(zipPath));
        //使用指定校验和创建输出流
        CheckedOutputStream csum = new CheckedOutputStream(zipF, new CRC32());
        ZipOutputStream zos = new ZipOutputStream(csum, Charset.forName("GBK"));
        //启用压缩
        zos.setMethod(ZipOutputStream.DEFLATED);
        //压缩级别为速度最快
        zos.setLevel(Deflater.BEST_SPEED);

        for(DocAttVo doc:docs){
            String filePath = receiveFileAddress+doc.getLocation();
            File file = new File(filePath);
            if(file.exists()) {
                InputStream in = new FileInputStream(file);
                ZipEntry en = new ZipEntry(handleZipFileName(doc));
                en.setSize(file.length());
                zos.putNextEntry(en);

                int len = 0;
                byte[] buffer = new byte[1024];
                while (-1 != (len = in.read(buffer))) {
                    zos.write(buffer, 0, len);
                }
                in.close();

            }else{
                logger.error("上传的文件名 {} 不存在！", doc.getName());
                continue;
            }
        }
        zos.setComment("附件打包下载");
        zos.close();
        //打压缩包过程----------------end------------

        //下载到客户端
        File zipFile = new File(zipPath);
        downloadFile(response,zipFile,zipFileName);
        zipFile.delete(); //下载完成之后，删除服务器中的zip包
    }

    /**
     * 处理zip包中的文件路径
     * @param doc 附件对象
     * @return 处理后的文件相对路径
     *
     * String fileRealName = "事件删除脚本.txt"
     * String relativeLocation = "20180925\1537862006333_354061684.txt"
     * return "20180925\事件删除脚本_1537937502058.txt"
     * */
    private String handleZipFileName(DocAttVo doc){

        //处理附件真实名称
        String fileRealName = doc.getName();
        StringBuffer nameBuffer = new StringBuffer(fileRealName);
        nameBuffer.insert(fileRealName.lastIndexOf("."+doc.getSuffix()),"_" + System.currentTimeMillis());
        fileRealName =nameBuffer.toString();

        //处理相对路径
        String location = doc.getLocation(); //相对路径
        StringBuffer locationBuffer = new StringBuffer(location.substring(0,location.lastIndexOf(File.separator)));
        locationBuffer.append(File.separator).append(fileRealName);

        return locationBuffer.toString();
    }

    /**
     * 文件下载到客户端方法
     * @param response 请求响应
     * @param file 待下载文件
     * @param fileName 下载文件名
     * @return 文件流
     */
    private void downloadFile(HttpServletResponse response, File file, String fileName)throws IOException{
        String fileNameEncode = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fileNameEncode);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int i = 0;
        FileInputStream fis = new FileInputStream(file);
        while ((i = fis.read(b)) > 0) {
            servletOutputStream.write(b, 0, i);
        }
        fis.close();
        servletOutputStream.flush();
        servletOutputStream.close();
    }

}
