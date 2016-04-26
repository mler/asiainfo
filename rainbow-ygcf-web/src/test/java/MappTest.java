//import com.bdx.rainbow.common.util.HttpClientUtil;
//import com.bdx.rainbow.mapp.core.JsonMapper;
//import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
//import com.bdx.rainbow.mapp.model.BDXDatapackage;
//import com.bdx.rainbow.mapp.model.BDXHeader;
//import com.bdx.rainbow.mapp.model.req.*;
//import com.bdx.rainbow.mapp.model.rsp.SK0001Response;

import java.util.HashMap;
import java.util.Map;

/**
 * mapp测试类
 * Created by fusj on 16/3/21.
 */
public class MappTest {
//    public static void main(String[] args) {
//        try {
//            // sk0001
//            IMappDatapackage bdxDatapackage = sk0001Package();
//            String result = dealHttpClient(bdxDatapackage, null);
//
//            JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
//            bdxDatapackage = jsonMapper.fromJson(result, IMappDatapackage.class);
//
//            SK0001Response body = (SK0001Response) bdxDatapackage.getBody();
//            String sessionId = body.getSessionId();
//
//            Map<String,String> headers = new HashMap<>();
//            headers.put("Cookie", "JSESSIONID=" + sessionId);
//
//            // sk0002
////            bdxDatapackage = sk0002Package();
//
//            // sk0003
////            bdxDatapackage = sk0003Package();
//
//            // sk0005
//            bdxDatapackage = sk0005Package();
//
//            // sk0013
////            bdxDatapackage = sk0013Package();
//
//            // sk0015
////            bdxDatapackage = sk0015Package();
//
//            // sk0017
////            bdxDatapackage = sk0017Package();
//
//            // sk0018
////            bdxDatapackage = sk0018Package();
//
//            // sk0020
////            bdxDatapackage = sk0020Package();
//
//            // sk0023
////            bdxDatapackage = sk0023Package();
//
//            dealHttpClient(bdxDatapackage, headers);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * 调用接口请求
//     * @param bdxDatapackage
//     * @throws Exception
//     */
//    private static String dealHttpClient(IMappDatapackage bdxDatapackage, Map<String,String> headers) throws Exception {
//        String url = "http://localhost:8080/rest/";
//        Map<String, String> param = new HashMap<>();
//
//        // 组装JSON
//        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
//        System.out.println(jsonMapper.toJson(bdxDatapackage));
//
//        // 传递参数
//        param.put("msg", jsonMapper.toJson(bdxDatapackage));
//
//        // 请求
//        return HttpClientUtil.getStringResponse(url, headers, null, param, "UTF-8");
//    }
//
//    /**
//     * 0001登陆
//     * @return
//     */
//    private static IMappDatapackage sk0001Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0001Request request = new SK0001Request();
//        request.setUserName("test");
//        request.setPwd("123456");
//        request.setDeviceType("1");
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0001");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    /**
//     * 0002获取员工列表，可分页
//     * @return
//     */
//    private static IMappDatapackage sk0002Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0002Request request = new SK0002Request();
//        request.setPageCount("10");
//        request.setPageNumber("0");
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0002");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    /**
//     * 0003添加/修改员工
//     * @return
//     */
//    private static IMappDatapackage sk0003Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0003Request request = new SK0003Request();
//        request.setName("傅水军mapp");
//        request.setJobTitle("1");
//        request.setPost("1");
//        request.setDocumentType("1");
//        request.setiDNumber("339005");
//        request.setPersonaPhone("15158133910");
//        request.setAreBlacklist("1");
//        request.setHealthId("88888");
//        request.setCheckResult("1");
//        request.setIssuingDate("2016-03-01");
//        request.setValidity("2017-03-01");
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0003");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    /**
//     * 0005台账明细
//     * @return
//     */
//    private static IMappDatapackage sk0005Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0005Request request = new SK0005Request();
//        request.setLedgerType("0");
//        request.setPageNumber("0");
//        request.setPageCount("5");
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0005");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//
//    }
//
//    /**
//     * 0013获取企业信息
//     * @return
//     */
//    private static IMappDatapackage sk0013Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0013Request request = new SK0013Request();
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0013");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    /**
//     * 0015获取监控列表
//     * @return
//     */
//    private static IMappDatapackage sk0015Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0015Request request = new SK0015Request();
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0015");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    /**
//     * 0017获取员工详细信息
//     * @return
//     */
//    private static IMappDatapackage sk0017Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0017Request request = new SK0017Request();
//        request.setId("1");
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0017");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    /**
//     * 0018基础数据
//     * @return
//     */
//    private static IMappDatapackage sk0018Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0018Request request = new SK0018Request();
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0018");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    /**
//     *
//     * @return
//     */
//    private static IMappDatapackage sk0020Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0020Request request = new SK0020Request();
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0020");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
//
//    private static IMappDatapackage sk0023Package() {
//        IMappDatapackage bdxDatapackage = new BDXDatapackage();
//
//        // body
//        SK0023Request request = new SK0023Request();
//        request.setId("130");
//        request.setLedgerType("1");
//
//        // header
//        BDXHeader bdxHeader = new BDXHeader();
//        bdxHeader.setBizCode("sk0023");
//
//        bdxDatapackage.setBody(request);
//        bdxDatapackage.setHeader(bdxHeader);
//
//        return bdxDatapackage;
//    }
}
