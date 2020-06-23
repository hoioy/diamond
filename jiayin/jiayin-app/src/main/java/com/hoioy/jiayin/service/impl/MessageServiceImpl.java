package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil2;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.jiayin.domain.Message;
import com.hoioy.jiayin.domain.MsgType;
import com.hoioy.jiayin.domain.PublishHistory;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.mapper.MessageMapper;
import com.hoioy.jiayin.mapper.MsgCountMapper;
import com.hoioy.jiayin.mapper.MsgTypeMapper;
import com.hoioy.jiayin.mapper.PublishHistoryMapper;
import com.hoioy.jiayin.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, MessageDTO> implements IMessageService<Message> {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MsgCountMapper msgCountMapper;
    @Autowired
    private MsgTypeMapper msgTypeMapper;
    @Autowired
    private PublishHistoryMapper publishHistoryMapper;

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MessagePageDTO filters = (MessagePageDTO) pageDTO.getFilters();
        IPage<Map> pageList = messageMapper.selectPage(page, filters);
        PageDTO resultPage = CommonMybatisPageUtil2.getInstance().iPageToPageDTO(pageList, MessagePageDTO.class);
        return resultPage;
    }

    @Override
    public MessageDTO update(MessageDTO dto) throws BaseException {
        MessageDTO update = super.update(dto);
        String userName = CommonSecurityUtils.getCurrentLogin();
        PublishHistory publishHistory = new PublishHistory();
        publishHistory.setPublishTitle(dto.getTitle());
        publishHistory.setPublishId(dto.getId());
        publishHistory.setMsgTypeId(dto.getMsgTypeId());
        publishHistory.setPublishType("msg");
        publishHistoryMapper.updateByPubilshId(publishHistory);
        return update;
    }

    @Override
    public Optional<MessageDTO> findById(String id) throws BaseException {
        Optional<MessageDTO> byId = super.findById(id);
        if (byId.isPresent()) {
            MessageDTO messageDTO = byId.get();
            MsgType msgType = msgTypeMapper.selectById(messageDTO.getMsgTypeId());
            messageDTO.setMsgTypeName(msgType.getTypeName());
            return Optional.ofNullable(messageDTO);
        }
        return byId;
    }
}
