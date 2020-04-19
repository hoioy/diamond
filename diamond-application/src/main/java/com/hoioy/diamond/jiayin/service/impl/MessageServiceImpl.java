package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.Message;
import com.hoioy.diamond.jiayin.mapper.MessageMapper;
import com.hoioy.diamond.jiayin.service.IMessageService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.MessageDTO;
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
 * @since 2020-04-19
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message,MessageDTO> implements IMessageService<Message> {

        @Autowired
        private MessageMapper messageMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            Message message = TDFMybatisPageUtil.getBean(pageDTO, Message.class);
            IPage<Message> messageIPage = messageMapper.selectPage(page, message);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(messageIPage);
            return resultPage;
        }

        @Override
        public Message createDomain() {
        return new  Message();
        }

        @Override
        public MessageDTO createDTO() {
        return new MessageDTO();
        }
}
