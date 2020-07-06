package com.hoioy.jiayin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil2;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.service.IUserInfoService;
import com.hoioy.jiayin.domain.Message;
import com.hoioy.jiayin.domain.MsgPublished;
import com.hoioy.jiayin.domain.MsgType;
import com.hoioy.jiayin.domain.ZoneCode;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.mapper.*;
import com.hoioy.jiayin.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, MessageDTO> implements IMessageService<Message> {

    @Autowired
    private MsgTypeMapper msgTypeMapper;

    @Autowired
    private ZoneCodeMapper zoneCodeMapper;

    @Autowired
    private IUserInfoService iUserInfoService;

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
//        String userName = CommonSecurityUtils.getCurrentLogin();
//        MsgPublished msgPublished = new MsgPublished();
//        msgPublished.setMsgTitle(dto.getTitle());
//        msgPublished.setMsgId(dto.getId());
//        msgPublished.setMsgTypeId(dto.getMsgTypeId());
//        msgPublished.setMsgTableName("msg");
//        msgPublishedMapper.updateByPubilshId(msgPublished);
        return update;
    }

    @Override
    public Optional<MessageDTO> findById(String id) throws BaseException {
        Optional<MessageDTO> byId = super.findById(id);
        if (byId.isPresent()) {
            MessageDTO messageDTO = byId.get();
            //拼接messageType
            MsgType msgType = msgTypeMapper.selectById(messageDTO.getMsgTypeId());
            messageDTO.setMsgTypeName(msgType.getTypeName());
            messageDTO.setMsgTypeColor(msgType.getColor());

            //拼接地区
            if (StrUtil.isNotBlank(messageDTO.getTown())) {
                ZoneCode town = zoneCodeMapper.selectById(messageDTO.getTown());
                messageDTO.setTownName(town.getAddress());
            }
            if (StrUtil.isNotBlank(messageDTO.getVillage())) {
                ZoneCode village = zoneCodeMapper.selectById(messageDTO.getVillage());
                messageDTO.setVillageName(village.getAddress());
            }

            //拼接用户
            String userId = iUserInfoService.findIdByLoginName(messageDTO.getOpenid());
            UserInfoDTO dto = (UserInfoDTO) iUserInfoService.findById(userId).get();
            messageDTO.setUserName(dto.getUserName());
            messageDTO.setAvatar(dto.getAvatar());
            return Optional.ofNullable(messageDTO);
        }
        return byId;
    }

    @Override
    public Boolean cancelPublish(String msgId) throws BaseException {
        Message message = iBaseRepository.selectById(msgId);
        message.setStatus(2);
        int i = iBaseRepository.updateById(message);
        return i > 0 ? true : false;
    }
}
