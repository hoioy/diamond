package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.TDFJpaPageUtil;
import com.hoioy.diamond.sys.domain.ParameterInfo;
import com.hoioy.diamond.sys.domain.ParameterInfoRepository;
import com.hoioy.diamond.sys.dto.ParameterInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IParameterInfoService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class ParameterInfoServiceImpl extends BaseServiceImpl<ParameterInfoRepository, ParameterInfo, ParameterInfoDTO> implements IParameterInfoService<ParameterInfo> {

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
    public List<String> findIdsByParameterKeys(List<String> parameterKeys) {
        return iBaseRepository.findIdsByParameterKeys(parameterKeys);
    }
}
