package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.jiayin.domain.Message;
import com.hoioy.jiayin.mapper.MessageMapper;
import com.hoioy.jiayin.service.IMessageService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.jiayin.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            Page page = DiamondMybatisPageUtil.getPage(pageDTO);
            Message msg = DiamondMybatisPageUtil.getBean(pageDTO, Message.class);
            IPage<Message> messageIPage = messageMapper.selectPage(page, msg);
            PageDTO returnPageDTO = DiamondMybatisPageUtil.getPageDTO(messageIPage);
            return returnPageDTO;
        }

    @Override
    public String publishMsg(MessageDTO dto) {
        return null;
    }
}
