package com.bdx.rainbow.controller.common;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.ExcelRowDataResult;
import com.bdx.rainbow.service.common.IExcelReaderService;
import com.bdx.rainbow.toolbox.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel文件解析
 * Created by fusj on 16/1/20.
 */
@Controller
@RequestMapping("excelUpload")
public class ExcelReaderController {

    private final Logger logger = LoggerFactory.getLogger(ExcelReaderController.class);

    @Autowired
    private IExcelReaderService excelUploadService;

    /**
     * excel读取
     * @param filePath     excel文件路径
     * @param serviceKey   ExcelServiceNameMapper对应key值
     * @param colTotal     Excel数据列数
     * @param rowStart     数据开始行数，从0开始数
     * @param request
     * @param response
     */
    @RequestMapping
    @ResponseBody
    public String excelRead(@RequestParam("filePath") String filePath, @RequestParam("serviceKey") String serviceKey,
                          @RequestParam("colTotal") String colTotal, @RequestParam("rowStart") String rowStart,
                          HttpServletRequest request, HttpServletResponse response) {
        try {
            // excel导入所需参数
            Map<String, String> param = new HashMap<String, String>();
            param.put("serviceKey", serviceKey);
            param.put("colTotal", colTotal);
            param.put("rowStart", rowStart);

            param.put("enterpriseId", SessionManager.getEnterpriseId(request) + "");

            // 跨服务器读取文件
            URL url = new URL(filePath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            List<ExcelRowDataResult> readResult = excelUploadService.excelRead(inputStream, param, request);

            return "{\"success\" : true}";
        } catch (Exception ex) {
            if(ex instanceof BusinessException ) {
                logger.error(((BusinessException) ex).getErrorMsg(), ex);
            } else if(ex instanceof SystemException ) {
                logger.error(((SystemException) ex).getErrorMsg(), ex);
            } else {
                logger.error(ex.getMessage(), ex);
            }
        }

        return "{\"success\" : false}";
    }
}
