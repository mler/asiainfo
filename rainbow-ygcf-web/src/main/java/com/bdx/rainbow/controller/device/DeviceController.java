package com.bdx.rainbow.controller.device;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.BaseController;
import com.bdx.rainbow.common.bean.ResultBean;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.service.sk.IDeviceService;
import com.bdx.rainbow.view.DeviceView;
import com.bdx.rainbow.ygcf.entity.Device;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 设备管理
 * Created by fusj on 16/3/8.
 */
@Controller
@RequestMapping("device")
public class DeviceController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private static final String VM_ROOT_PATH = "/sk/device/%s";

    @Autowired
    private IDeviceService deviceService;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = {"", "/", "index"}, method = RequestMethod.GET)
    public String index() {
        return String.format(VM_ROOT_PATH, "index");
    }

    /**
     * 列表数据
     * @param model
     * @param pageInfo
     * @param device
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ModelAndView list(Model model, PageInfo pageInfo, Device device, String enterpriseName) throws Exception {

        // 企业名称模糊查询企业信息
        List<DubboEnterpriseInfo> list = dubboQueryEnterpriseByName(enterpriseName);

        if(StringUtil.isNotEmptyObject(enterpriseName) && CollectionUtils.isEmpty(list)) {
            pageInfo.setList(new ArrayList());
            pageInfo.setTotalCount(0);
        } else {
            pageInfo = deviceService.list(device, pageInfo, getEnterpriseIdList(list));

            Set<Integer> enterpriseIds = new HashSet<>();
            List<DeviceView> resultList = new ArrayList<>();

            // 模型转化
            for (Device deviceData : (List<Device>) pageInfo.getList()) {
                enterpriseIds.add(deviceData.getEnterpriseId());

                DeviceView deviceView = new DeviceView();

                deviceView.setDeviceId(deviceData.getDeviceId());
                deviceView.setEnterpriseId(deviceData.getEnterpriseId());
                deviceView.setDeviceName(deviceData.getDeviceName());
                deviceView.setDeviceKey(deviceData.getDeviceKey());

                resultList.add(deviceView);
            }

            // 查询企业信息
            Map<Integer, DubboEnterpriseInfo> map = dubboQueryEnterpriseInfoByIds(new ArrayList<>(enterpriseIds));

            for (DeviceView deviceView : resultList) {
                DubboEnterpriseInfo enterpriseInfo = map.get(deviceView.getEnterpriseId());
                deviceView.setEnterpriseName(enterpriseInfo.getEnterpriseName());
            }

            pageInfo.setList(resultList);
        }

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "list"));
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean add(String[] deviceKey, String[] deviceName, Integer enterpriseId) {
        try {

            deviceService.insertDevice(deviceKey, deviceName, enterpriseId);

            ResultBean resultBean = new ResultBean(true);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 删除
     * @param device
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean delete(Device device) {
        try {

            deviceService.deleteDevice(device);

            ResultBean resultBean = new ResultBean(true);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 修改
     * @param device
     * @return
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean modify(Device device) {
        try {

            deviceService.updateDevice(device);

            ResultBean resultBean = new ResultBean(true);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }
}
