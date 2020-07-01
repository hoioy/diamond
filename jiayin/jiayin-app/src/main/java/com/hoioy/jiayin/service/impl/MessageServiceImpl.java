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
import com.hoioy.jiayin.domain.MsgPublished;
import com.hoioy.jiayin.domain.MsgType;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.mapper.MessageMapper;
import com.hoioy.jiayin.mapper.MsgCountMapper;
import com.hoioy.jiayin.mapper.MsgPublishedMapper;
import com.hoioy.jiayin.mapper.MsgTypeMapper;
import com.hoioy.jiayin.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, MessageDTO> implements IMessageService<Message> {

    @Autowired
    private MsgCountMapper msgCountMapper;
    @Autowired
    private MsgTypeMapper msgTypeMapper;
    @Autowired
    private MsgPublishedMapper msgPublishedMapper;

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MessagePageDTO filters = (MessagePageDTO) pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.selectPage(page, filters);
        PageDTO resultPage = CommonMybatisPageUtil2.getInstance().iPageToPageDTO(pageList, MessagePageDTO.class);
        return resultPage;
    }

    @Override
    public MessageDTO update(MessageDTO dto) throws BaseException {
        MessageDTO update = super.update(dto);
        String userName = CommonSecurityUtils.getCurrentLogin();
        MsgPublished msgPublished = new MsgPublished();
        msgPublished.setMsgTitle(dto.getTitle());
        msgPublished.setMsgId(dto.getId());
        msgPublished.setMsgTypeId(dto.getMsgTypeId());
        msgPublished.setMsgTableName("msg");
        msgPublishedMapper.updateByPubilshId(msgPublished);
        return update;
    }

    @Override
    public Optional<MessageDTO> findById(String id) throws BaseException {
        Optional<MessageDTO> byId = super.findById(id);
        if (byId.isPresent()) {
            MessageDTO messageDTO = byId.get();
            MsgType msgType = msgTypeMapper.selectById(messageDTO.getMsgTypeId());
            messageDTO.setMsgTypeName(msgType.getTypeName());
            messageDTO.setMsgTypeColor(msgType.getColor());
            return Optional.ofNullable(messageDTO);
        }
        return byId;
    }
}
