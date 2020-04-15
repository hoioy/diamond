package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.diamond.sys.domain.ParameterInfo;
import com.hoioy.diamond.sys.dto.ParameterInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.ParameterInfoMapper;
import com.hoioy.diamond.sys.service.IParameterInfoService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Service
public class ParameterInfoServiceImpl extends BaseServiceImpl<ParameterInfoMapper, ParameterInfo, ParameterInfoDTO> implements IParameterInfoService<ParameterInfo> {

    @Override
    public String save(ParameterInfoDTO parameterInfoDto) throws BaseException {
        if (StringUtils.isNotEmpty(parameterInfoDto.getId())) {
            throw new SysException("包含id");
        }
        if (parameterInfoDto.getParameterKey() == null) {
            throw new SysException("参数键名不能为空");
        }
        if (parameterInfoDto.getParameterName() == null) {
            throw new SysException("参数名称不能为空");
        }
        if (parameterInfoDto.getParameterValue() == null) {
            throw new SysException("参数键值不能为空");
        }
        parameterInfoDto.setParameterJson(JSONObject.toJSONString(parameterInfoDto.getParameterJson()));
        return super.save(parameterInfoDto);
    }

    @Override
    public String update(ParameterInfoDTO parameterInfoDto) throws BaseException {
        if (parameterInfoDto.getParameterKey() == null) {
            throw new SysException("参数键名不能为空");
        }
        if (parameterInfoDto.getParameterName() == null) {
            throw new SysException("参数名称不能为空");
        }
        if (parameterInfoDto.getParameterValue() == null) {
            throw new SysException("参数键值不能为空");
        }
        parameterInfoDto.setParameterJson(JSONObject.toJSONString(parameterInfoDto.getParameterJson()));
        return super.update(parameterInfoDto);
    }

    @Override
    public PageDTO getPage(PageDTO pageDTO) {
        ParameterInfo domain = DiamondMybatisPageUtil.getBean(pageDTO, ParameterInfo.class);
        IPage<ParameterInfo> data = baseMapper.selectPage(DiamondMybatisPageUtil.getPage(pageDTO), domain);
        return DiamondMybatisPageUtil.getPageDTO(data);
    }

    @Override
    public List<String> findIdsByParameterKeys(List<String> parameterKeys) {
        List<String> ids = baseMapper.findIdsByParameterKeys(parameterKeys);
        return ids;
    }
}
