package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.jiayin.domain.Message;
import com.hoioy.jiayin.domain.MsgCount;
import com.hoioy.jiayin.domain.PublishHistory;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.exception.JiayinException;
import com.hoioy.jiayin.mapper.MessageMapper;
import com.hoioy.jiayin.mapper.MsgCountMapper;
import com.hoioy.jiayin.mapper.PublishHistoryMapper;
import com.hoioy.jiayin.service.IMessageService;
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
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, MessageDTO> implements IMessageService<Message> {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MsgCountMapper msgCountMapper;

    @Autowired
    private PublishHistoryMapper publishHistoryMapper;


    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MessagePageDTO filters = (MessagePageDTO) pageDTO.getFilters();
        IPage<Message> messageIPage = messageMapper.selectPage(page, filters);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
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
}
