package com.bdx.rainbow.vfs.dubbo;

/**
 * 支持office(word/excel/pdf)转换成PDF
 * Created by fusj on 16/4/7.
 */
public interface IFileTransferService {

    /**
     * office(word/excel/pdf)转换成PDF
     * @param url      文件http访问路径
     * @param reload   是否强制加载
     * @return         返回pdf文件访问路径
     * @throws Exception
     */
    String officeToPdf(String url, Boolean reload) throws Exception;

    /**
     * 查询转换结果
     * @param sourceUrl
     * @return
     * @throws Exception
     */
    String findFileBySourceUrl(String sourceUrl) throws Exception;
}
