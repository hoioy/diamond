package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil2;
import com.hoioy.jiayin.domain.MsgDraft;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import com.hoioy.jiayin.mapper.MsgDraftMapper;
import com.hoioy.jiayin.service.IMsgDraftService;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public PageDTO getPage(PageDTO<MsgDraftDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgDraftDTO filters = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.selectPage(page, filters);
        PageDTO resultPage = CommonMybatisPageUtil2.getInstance().iPageToPageDTO(pageList, MsgDraftDTO.class);
        return resultPage;
    }


    @Override
    public void saveOrUpdateDraft(String userName, MessageDTO update, String messageType) {
        int count = iBaseRepository.removeByUserNameAndMsgId(userName, update.getId(), messageType);
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
