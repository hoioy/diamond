package com.hoioy.diamond.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.diamond.jiayin.domain.MsgType;
import com.hoioy.diamond.jiayin.dto.MsgTypeDTO;
import com.hoioy.diamond.jiayin.mapper.MsgTypeMapper;
import com.hoioy.diamond.jiayin.service.IMsgTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 消息类型 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgTypeServiceImpl extends BaseServiceImpl<MsgTypeMapper, MsgType,MsgTypeDTO> implements IMsgTypeService<MsgType> {

        @Autowired
        private MsgTypeMapper msgTypeMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = DiamondMybatisPageUtil.getPage(pageDTO);
            MsgType msgType = DiamondMybatisPageUtil.getBean(pageDTO, MsgType.class);
            IPage<MsgType> messageIPage = msgTypeMapper.selectPage(page, msgType);
            PageDTO returnPageDTO = DiamondMybatisPageUtil.getPageDTO(messageIPage);
            return returnPageDTO;
        }

        @Override
        public MsgType createDomain() {
        return new  MsgType();
        }

        @Override
        public MsgTypeDTO createDTO() {
        return new MsgTypeDTO();
        }
}