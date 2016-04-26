package com.bdx.rainbow.service.common;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.ExcelRowDataResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel数据读取接口
 * Created by fusj on 16/1/20.
 */
public interface IExcelReaderService {

    /**
     * excel数据读取
     * @param inputStream
     * @param param
     * @return
     */
    List<ExcelRowDataResult> excelRead(InputStream inputStream, Map<String, String> param, HttpServletRequest request) throws SystemException, BusinessException;
}
