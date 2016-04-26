package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.Device;

import java.util.List;

/**
 * 监控设备接口
 * Created by fusj on 16/1/19.
 */
public interface IDeviceService {

    /**
     * 插入监控设备
     */
    void insertDevice(String[] deviceKey, String[] deviceName, Integer enterpriseId) throws Exception;

    /**
     * 删除监控设备
     * @param device
     * @return
     */
    void deleteDevice(Device device) throws Exception;

    /**
     * 修改监控设备
     * @param device
     * @returnHu
     */
    void updateDevice(Device device) throws Exception;

    /**
     * 获取列表页数据
     * @param device
     * @param pageInfo
     * @return
     */
    PageInfo list(Device device, PageInfo pageInfo, List<Integer> enterpriseIdList);

    /**
     * 根据企业编号查询监控设备列表
     * @param enterpriseId
     * @return
     */
    List<Device> selectByList(int enterpriseId);
}
