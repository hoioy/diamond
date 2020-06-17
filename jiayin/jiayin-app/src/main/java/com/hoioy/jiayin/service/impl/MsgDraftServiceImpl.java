package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgDraft;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import com.hoioy.jiayin.mapper.MsgDraftMapper;
import com.hoioy.jiayin.service.IMsgDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息草稿 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgDraftServiceImpl extends BaseServiceImpl<MsgDraftMapper, MsgDraft, MsgDraftDTO> implements IMsgDraftService<MsgDraft> {

    @Autowired
    private MsgDraftMapper msgDraftMapper;


    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgDraft msgDraft = getDomainFilterFromPageDTO(pageDTO);
        IPage<MsgDraft> messageIPage = msgDraftMapper.selectPage(page, msgDraft);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }


    @Override
    public void saveOrUpdateDraft(String userName, MessageDTO update,String messageType) {
       int count =  msgDraftMapper.removeByUserNameAndMsgId(userName,update.getId(),messageType);
        if (count == 0) {
            MsgDraftDTO msgDraftDTO = new MsgDraftDTO();
            msgDraftDTO.setMessageType("message");
            msgDraftDTO.setMsgId(update.getId());
            msgDraftDTO.setMsgTitle(update.getTitle());
            msgDraftDTO.setMsgTypeId(update.getMsgTypeId());
            msgDraftDTO.setOpenid(update.getOpenid());
            save(msgDraftDTO);
        }
    }
}
