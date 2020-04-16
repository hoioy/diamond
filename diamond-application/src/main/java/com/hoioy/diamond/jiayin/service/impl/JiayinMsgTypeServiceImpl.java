package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.JiayinMsgType;
import com.hoioy.diamond.jiayin.mapper.JiayinMsgTypeMapper;
import com.hoioy.diamond.jiayin.service.IJiayinMsgTypeService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.JiayinMsgTypeDTO;
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
 * @since 2020-04-16
 */
@Service
public class JiayinMsgTypeServiceImpl extends BaseServiceImpl<JiayinMsgTypeMapper, JiayinMsgType,JiayinMsgTypeDTO> implements IJiayinMsgTypeService<JiayinMsgType> {

        @Autowired
        private JiayinMsgTypeMapper jiayinMsgTypeMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            JiayinMsgType jiayinMsgType = TDFMybatisPageUtil.getBean(pageDTO, JiayinMsgType.class);
            IPage<JiayinMsgType> jiayinMsgTypeIPage = jiayinMsgTypeMapper.selectPage(page, jiayinMsgType);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(jiayinMsgTypeIPage);
            return resultPage;
        }

        @Override
        public JiayinMsgType createDomain() {
        return new  JiayinMsgType();
        }

        @Override
        public JiayinMsgTypeDTO createDTO() {
        return new JiayinMsgTypeDTO();
        }
}
