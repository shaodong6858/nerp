package cn.gs.common.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.gs.base.AbstractController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 临时文件管理
 * @author xiapengtao
 * @date 2018年2月6日
 */
@Slf4j
@Api(tags={"系统 -临时文件管理"})
@RestController
@RequestMapping("common/file-temp")
public class FileTempController extends AbstractController {
	
    /**
     * 根据临时文件下载文件
     *
     * @param fileName
     * @param response
     * @throws Exception
     */
    @GetMapping("download/{fileName}")
    public void downloadTemp(@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception {
        log.info("下载临时文件:{}", fileName);
        writeTempFile(fileName, response);
    }
    
}
