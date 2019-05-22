package cn.gs.common.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.gs.base.AbstractBaseService;
import cn.gs.base.IBaseRepository;
import cn.gs.common.model.FileInstance;
import cn.gs.common.repository.FileInstanceRepository;
import cn.gs.common.util.PathUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件相关服务
 * @author xiapengtao
 * @date 2018年4月10日
 */
@Slf4j
@Service
public  class FileInstanceService extends AbstractBaseService<FileInstance> {
	
	@Autowired
	FileInstanceRepository fileInstanceRepository;
	
	@Override
	public IBaseRepository<FileInstance> getRepository() {
		return fileInstanceRepository;
	}
	
	/**
	 * 保存上传文件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public FileInstance save(File srcFile, String type, String id) throws IllegalStateException, IOException {
		// 首先判断此id对应的文件是否已经存在，若已经存在则直接返回
		FileInstance fileInstance = get(id);
		if (fileInstance != null) {
			return fileInstance;
		}
		
		//创建文件实例
		fileInstance = new FileInstance();
		if (srcFile != null) {
			fileInstance.setId(id);
			fileInstance.setType(type);
			String name = srcFile.getName();
			fileInstance.setName(name);
			fileInstance.setStatue("0");
			
			String extension = PathUtil.getExtension(name);
            String saveFileName = id + extension;
			String path = generateFilePathByDate(saveFileName);
			fileInstance.setPath(path);
			log.debug("上传文件：{}", path);
			String destFile = getUploadDir(fileInstance.getPath());
			
			// 移动文件
			FileUtils.moveFile(srcFile, new File(destFile));
			
            create(fileInstance);
		}
		return fileInstance;
	}
	
	/**
	 * 保存上传文件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public FileInstance save(MultipartFile file, String type, String id) throws IllegalStateException, IOException {
		
		FileInstance fileInstance = new FileInstance();
		if (file != null && !file.isEmpty()) {
			
			if (StringUtils.isEmpty(id)) {
				id = uuid();
			}
			fileInstance.setId(id);
			fileInstance.setType(type);
			String name = file.getOriginalFilename();
			fileInstance.setName(name);
			fileInstance.setStatue("0");
			
			String extension = PathUtil.getExtension(name);
            String saveFileName = id + extension;
			String path = generateFilePathByDate(saveFileName);
			fileInstance.setPath(path);
			log.debug("上传文件：{}", path);
            uploadFile(getUploadDir(fileInstance.getPath()), file);
            create(fileInstance);
		}
		return fileInstance;
	}
	
	/**
	 * 保存上传文件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public FileInstance save(MultipartFile file, String type) throws IllegalStateException, IOException {
		return save(file, type, null);
	}
	
	/**
	 * 上传文件
	 * @param destPath
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void uploadFile(String targetFilePath, MultipartFile file) throws IllegalStateException, IOException{
        File targetFile = new File(targetFilePath);  
        if(!targetFile.getParentFile().exists()){  
            targetFile.getParentFile().mkdirs();
        }  
        file.transferTo(targetFile);
    }
	
	/**
	 * 根据文件id获取文件
	 * @return
	 */
	public File getFile(String id) {
		FileInstance fi = get(id);
    	if(fi == null) return null;
    	String filePath = fi.getPath();
    	File file = new File(getUploadDir(filePath));
    	if (file.exists()) {
    		return file;
    	} else {
    		 return null;
    	}
	}
	
	/**
	 * 从url下载文件并保存
	 * @param url
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public FileInstance saveFromUrl(URL url, String type, String id) throws IOException {
		File destination = File.createTempFile(System.currentTimeMillis()+"", ".tmp");
		FileUtils.copyURLToFile(url, destination);
		return save(destination, type, id);
	}
	
}
