package com.hoioy.jiayin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import com.hoioy.jiayin.dto.MsgPublishedDTO;
import com.hoioy.jiayin.service.IMessageService;
import com.hoioy.jiayin.service.IMsgDraftService;
import com.hoioy.jiayin.service.IMsgPublishedService;
import com.hoioy.jiayin.service.IPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublishServiceImpl implements IPublishService {
    @Autowired
    private IMessageService iMessageService;

    @Autowired
    private IMsgDraftService iMsgDraftService;

    @Autowired
    private IMsgPublishedService iMsgPublishedService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageDTO saveDraft(MessageDTO dto) {
        String userName = CommonSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
        if (StrUtil.isBlank(dto.getId())) {
            MessageDTO save = (MessageDTO) iMessageService.save(dto);
            MsgDraftDTO msgDraftDTO = new MsgDraftDTO();
            msgDraftDTO.setMsgTableName("message");
            msgDraftDTO.setMsgId(save.getId());
            msgDraftDTO.setMsgTitle(dto.getTitle());
            msgDraftDTO.setMsgTypeId(dto.getMsgTypeId());
            msgDraftDTO.setOpenid(dto.getOpenid());
            iMsgDraftService.save(msgDraftDTO);
            return save;
        } else {
            MessageDTO update = (MessageDTO) iMessageService.update(dto);
            iMsgDraftService.saveOrUpdateDraft(userName, update, "message");
            //查询这条消息在草稿里有没有,如果有修改如果没有新增
            return update;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageDTO publish(MessageDTO dto) {
        String userName = CommonSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
        //如果没有id代表这条数据在草稿表里没有
        if (StrUtil.isBlank(dto.getId())) {
            MessageDTO save = (MessageDTO) iMessageService.save(dto);
            MsgPublishedDTO msgPublished = new MsgPublishedDTO();
            msgPublished.setOpenid(userName);
            msgPublished.setMsgTitle(dto.getTitle());
            msgPublished.setMsgId(save.getId());
            msgPublished.setMsgTypeId(dto.getMsgTypeId());
            msgPublished.setMsgTableName("message");
            iMsgPublishedService.save(msgPublished);
            return save;
        } else {
            MessageDTO update = (MessageDTO) iMessageService.update(dto);
            //删除草稿中的这条记录
            iMsgDraftService.saveOrUpdateDraft(userName, update, "message");
            //在我的发布中新增这条记录
            iMsgPublishedService.saveOrUpdateDraft(userName, update, "message");
            return null;
        }
    }
}
