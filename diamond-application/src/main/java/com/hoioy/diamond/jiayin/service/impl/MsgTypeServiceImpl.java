package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.MsgType;
import com.hoioy.diamond.jiayin.mapper.MsgTypeMapper;
import com.hoioy.diamond.jiayin.service.IMsgTypeService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.MsgTypeDTO;
import org.springframework.stereotype.Service;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.BaseException;
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
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            MsgType msgType = TDFMybatisPageUtil.getBean(pageDTO, MsgType.class);
            IPage<MsgType> msgTypeIPage = msgTypeMapper.selectPage(page, msgType);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(msgTypeIPage);
            return resultPage;
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
