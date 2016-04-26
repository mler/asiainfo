package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.service.sk.IDeviceService;
import com.bdx.rainbow.ygcf.entity.Device;
import com.bdx.rainbow.ygcf.entity.DeviceExample;
import com.bdx.rainbow.ygcf.mapper.DeviceMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 监控设备接口实现
 * Created by fusj on 16/1/19.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 插入监控设备
     * @return
     */
    @Override
    public void insertDevice(String[] deviceKey, String[] deviceName, Integer enterpriseId) throws Exception {
        for(int i = 0; i < deviceKey.length; i++) {

            Device deviceData = new Device();
            deviceData.setEnterpriseId(enterpriseId);
            deviceData.setDeviceName(deviceName[i]);
            deviceData.setDeviceKey(deviceKey[i]);
            deviceData.setIsDel("0");
            deviceData.setCreateDate(new Timestamp(new Date().getTime()));
            deviceData.setCreateUserid(1);

            deviceMapper.insertSelective(deviceData);
        }

    }

    /**
     * 删除监控设备
     * @param device
     * @throws SystemException
     * @throws BusinessException
     */
    @Override
    public void deleteDevice(Device device) throws Exception {
        device.setIsDel("1");

        deviceMapper.updateByPrimaryKeySelective(device);
    }

    /**
     * 修改监控设备
     * @param device
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    @Override
    public void updateDevice(Device device) throws Exception {
        device.setUpdateDate(new Timestamp(new Date().getTime()));
        device.setUpdateUserid(1);

        deviceMapper.updateByPrimaryKeySelective(device);
    }

    /**
     * 获取列表页数据
     * @param device
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(Device device, PageInfo pageInfo, List<Integer> enterpriseIdList) {
        device.setIsDel("0");

        DeviceExample example = getCondition(device, enterpriseIdList);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<Device> list = deviceMapper.selectByExample(example);
        int totalCount = deviceMapper.countByExample(example);

        pageInfo.setList(list);
        pageInfo.setTotalCount(totalCount);

        return pageInfo;
    }

    /**
     * 根据企业编号查询监控设备编号
     * @param enterpriseId
     * @return
     */
    @Override
    public List<Device> selectByList(int enterpriseId) {
        DeviceExample example = new DeviceExample();
        example.createCriteria().andIsDelEqualTo("0")
                .andEnterpriseIdEqualTo(enterpriseId);

        List<Device> list = deviceMapper.selectByExample(example);

        return list;
    }

    /**
     * 拼装查询条件
     * @param device
     * @return
     */
    private DeviceExample getCondition(Device device, List<Integer> enterpriseIds) {
        DeviceExample example = new DeviceExample();

        DeviceExample.Criteria criteria = example.createCriteria();

        if(null != device) {

            if(StringUtil.isNotEmptyObject(device.getIsDel())) {
                criteria.andIsDelEqualTo(device.getIsDel());
            }

        }
        if(!CollectionUtils.isEmpty(enterpriseIds)) {
            criteria.andEnterpriseIdIn(enterpriseIds);
        }

        return example;
    }
}
