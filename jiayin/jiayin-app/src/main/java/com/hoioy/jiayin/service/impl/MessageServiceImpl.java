package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.jiayin.domain.Message;
import com.hoioy.jiayin.domain.MsgCount;
import com.hoioy.jiayin.domain.PublishHistory;
import com.hoioy.jiayin.dto.MessageDTO;
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
        Message message = getDomainFilterFromPageDTO(pageDTO);
        IPage<Message> messageIPage = messageMapper.selectPage(page, message);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }

    /**
     * 消息发布
     * @param dto
     * @return
     */
    @Override
    public MessageDTO save(MessageDTO dto) {
        String userName = CommonSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
        //查询用户次数表 看用户是否可以发布消息，
        MsgCount msgCount = msgCountMapper.selectByOpenid(userName);
        if (msgCount != null && msgCount.getMsgCount() > 0) {
            MessageDTO save = super.save(dto);
            //发布成功减少次数
            msgCount.setMsgCount(msgCount.getMsgCount()-1);
            int i = msgCountMapper.updateById(msgCount);
            PublishHistory publishHistory = new PublishHistory();
            publishHistory.setOpenid(userName);
            publishHistory.setPublishTitle(dto.getTitle());
            publishHistory.setPublishId(save.getId());
            publishHistory.setMsgTypeId(dto.getMsgType());
            publishHistory.setPublishType("msg");
            publishHistoryMapper.insert(publishHistory);
            return save;
        }else{
            throw new JiayinException("发布消息次数不足，可以通过分享增加发布次数");
        }

    }

    @Override
    public MessageDTO update(MessageDTO dto) throws BaseException {
        MessageDTO update = super.update(dto);
        String userName = CommonSecurityUtils.getCurrentLogin();
        PublishHistory publishHistory = new PublishHistory();
        publishHistory.setPublishTitle(dto.getTitle());
        publishHistory.setPublishId(dto.getId());
        publishHistory.setMsgTypeId(dto.getMsgType());
        publishHistory.setPublishType("msg");
        publishHistoryMapper.updateByPubilshId(publishHistory);
        return update;
    }
}
