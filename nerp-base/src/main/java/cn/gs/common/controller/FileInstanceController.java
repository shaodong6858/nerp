package cn.gs.common.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.gs.base.AbstractController;
import cn.gs.base.JsonResult;
import cn.gs.common.model.FileInstance;
import cn.gs.common.service.FileInstanceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件管理
 * @author xiapengtao
 * @date 2018年2月6日
 */
@Slf4j
@Api(tags={"系统 - 文件管理"})
@RestController
@RequestMapping("common/file")
public class FileInstanceController extends AbstractController {
	
	@Autowired
	FileInstanceService fileInstanceService;

	/**
	 * 上传文件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping
    public ResponseEntity<JsonResult> create(@RequestParam(value = "file", required = false) MultipartFile file,
    										 @RequestParam(value = "uploadfile", required = false) MultipartFile uploadfile,
    										 @RequestParam(value = "sessionId", required = false) String sessionId,
            								 @RequestParam(value="type", required=false) String type) throws IllegalStateException, IOException {
		if (file == null) {
			file = uploadfile;
		}
		FileInstance fi = fileInstanceService.save(file, type, sessionId);
		JsonResult result = JsonResult.success(fi);
		result.setName(fi.getId());
		ResponseEntity<JsonResult> responseEntity = entityResult(result);
		return responseEntity;
    }
	
	 /**
     * 文件下载
     *
     * @param fileId
     * @param type
     * @param response
     */
    @GetMapping("view/{id}")    
    public void view(@PathVariable("id") String fileId, HttpServletResponse response){
    	 // 去除扩展名
    	fileId = FilenameUtils.getBaseName(fileId);
    	
    	FileInstance fi = fileInstanceService.get(fileId);
        if(fi == null) return;
        
        String fileName = fi.getName();
        String filePath = fi.getPath();
        
        response.setContentType("text/html;charset=utf-8");    
        try {
            File file = new File(getUploadDir(filePath));
            writeFileToResponse(file, fileName, true, response);
        } catch (Exception e) {
           log.error("文件fileId{}下载失败：{}", fileId, e.getLocalizedMessage(), e);
        }
    }
	
    /**
     * 文件下载
     *
     * @param fileId
     * @param type
     * @param response
     */
    @GetMapping("download/{id}")
    public void download(@PathVariable("id") String fileId, HttpServletResponse response){
    	 // 去除扩展名
    	fileId = FilenameUtils.getBaseName(fileId);
        
    	FileInstance fi = fileInstanceService.get(fileId);
    	if(fi == null) return;
    	
    	String fileName = fi.getName();
    	String filePath = fi.getPath();
    	
    	response.setContentType("text/html;charset=utf-8");
    	try {
    		File file = new File(getUploadDir(filePath));
    		writeFileToResponse(file, fileName, false, response);
    	} catch (Exception e) {
    		log.error("文件fileId{}下载失败：{}", fileId, e.getLocalizedMessage(), e);
    	}
    }
    
    /**
     * 文件列表
     * @param fileId
     * @param type
     * @param response
     */
    @GetMapping
    public ResponseEntity<JsonResult> list(@RequestParam(value = "page", required = false, defaultValue = "0")int page, 
    							   @RequestParam(value = "size", required = false, defaultValue = "20")int size){
    	Page<FileInstance> pageList = fileInstanceService.findAll(PageRequest.of(page, size));
    	return success(pageList);
    }
    
}
