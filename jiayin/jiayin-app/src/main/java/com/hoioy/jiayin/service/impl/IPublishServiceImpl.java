package com.hoioy.jiayin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.diamond.sys.service.IFileStorageService;
import com.hoioy.jiayin.domain.MsgCount;
import com.hoioy.jiayin.domain.MsgDraft;
import com.hoioy.jiayin.domain.PublishHistory;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import com.hoioy.jiayin.dto.PublishHistoryDTO;
import com.hoioy.jiayin.exception.JiayinException;
import com.hoioy.jiayin.service.IMessageService;
import com.hoioy.jiayin.service.IMsgDraftService;
import com.hoioy.jiayin.service.IPublishHistoryService;
import com.hoioy.jiayin.service.IPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class IPublishServiceImpl implements IPublishService {
    @Autowired
    private IMessageService iMessageService;

    @Autowired
    private IMsgDraftService iMsgDraftService;

    @Autowired
     private IPublishHistoryService iPublishHistoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageDTO saveDraft(MessageDTO dto) {
        String userName = CommonSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
         if (StrUtil.isBlank(dto.getId())){
             MessageDTO save = (MessageDTO) iMessageService.save(dto);
             MsgDraftDTO msgDraftDTO = new MsgDraftDTO();
             msgDraftDTO.setMessageType("message");
             msgDraftDTO.setMsgId(save.getId());
             msgDraftDTO.setMsgTitle(dto.getTitle());
             msgDraftDTO.setMsgTypeId(dto.getMsgTypeId());
             msgDraftDTO.setOpenid(dto.getOpenid());
             iMsgDraftService.save(msgDraftDTO);
             return save;
         }else {
             MessageDTO update = (MessageDTO) iMessageService.update(dto);
             //查询这条消息在草稿里有没有,如果有修改如果没有新增
             return null;
         }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageDTO publish(MessageDTO dto) {
        String userName = CommonSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
        //如果没有id代表这条数据在草稿表里没有
        if (StrUtil.isBlank(dto.getId())){
            MessageDTO save = (MessageDTO) iMessageService.save(dto);
            PublishHistoryDTO publishHistory = new PublishHistoryDTO();
            publishHistory.setOpenid(userName);
            publishHistory.setPublishTitle(dto.getTitle());
            publishHistory.setPublishId(save.getId());
            publishHistory.setMsgTypeId(dto.getMsgTypeId());
            publishHistory.setPublishType("msg");
            iPublishHistoryService.save(publishHistory);
            return save;
        }else {
            MessageDTO update = (MessageDTO) iMessageService.update(dto);
            //删除草稿中的这条记录
            //在我的发布中新增这条记录
            return null;
        }
    }
}
