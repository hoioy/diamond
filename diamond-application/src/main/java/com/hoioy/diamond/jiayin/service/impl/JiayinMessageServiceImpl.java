package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.JiayinMessage;
import com.hoioy.diamond.jiayin.mapper.JiayinMessageMapper;
import com.hoioy.diamond.jiayin.service.IJiayinMessageService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.JiayinMessageDTO;
import org.springframework.stereotype.Service;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.BaseException;
/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Service
public class JiayinMessageServiceImpl extends BaseServiceImpl<JiayinMessageMapper, JiayinMessage,JiayinMessageDTO> implements IJiayinMessageService<JiayinMessage> {

        @Autowired
        private JiayinMessageMapper jiayinMessageMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            JiayinMessage jiayinMessage = TDFMybatisPageUtil.getBean(pageDTO, JiayinMessage.class);
            IPage<JiayinMessage> jiayinMessageIPage = jiayinMessageMapper.selectPage(page, jiayinMessage);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(jiayinMessageIPage);
            return resultPage;
        }

        @Override
        public JiayinMessage createDomain() {
        return new  JiayinMessage();
        }

        @Override
        public JiayinMessageDTO createDTO() {
        return new JiayinMessageDTO();
        }
}
