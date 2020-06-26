package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgType;
import com.hoioy.jiayin.domain.ZoneCode;
import com.hoioy.jiayin.dto.MsgTypeDTO;
import com.hoioy.jiayin.dto.ZoneCodeDTO;
import com.hoioy.jiayin.mapper.MsgTypeMapper;
import com.hoioy.jiayin.mapper.ZoneCodeMapper;
import com.hoioy.jiayin.service.IMsgTypeService;
import com.hoioy.jiayin.service.IZoneCodeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZoneCodeServiceImpl extends BaseTreeServiceImpl<ZoneCodeMapper, ZoneCode, ZoneCodeDTO> implements IZoneCodeService<ZoneCode> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        ZoneCode zoneCode = getDomainFilterFromPageDTO(pageDTO);
        IPage<ZoneCode> zoneCodeIPage = iBaseRepository.selectPage(page, zoneCode);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(zoneCodeIPage);
        return returnPageDTO;
    }

}
