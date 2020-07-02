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
import com.hoioy.jiayin.service.IMessageService;
import com.hoioy.jiayin.service.IMsgDraftService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsgDraftServiceImpl extends BaseServiceImpl<MsgDraftMapper, MsgDraft, MsgDraftDTO> implements IMsgDraftService<MsgDraft> {

    @Autowired
    private IMessageService iMessageService;

    @Override
    public PageDTO getPage(PageDTO<MsgDraftDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgDraftDTO filters = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.selectPage(page, filters);
        PageDTO resultPage = CommonMybatisPageUtil2.getInstance().iPageToPageDTO(pageList, MsgDraftDTO.class);
        return resultPage;
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        MsgDraftDTO draftDTO = findById(id).get();
        Boolean rD = super.removeById(id);
        Boolean mD = iMessageService.removeById(draftDTO.getMsgId());
        return rD && mD;
    }

    @Override
    public void saveOrUpdateDraft(String userName, MessageDTO update, String msgTableName) {
        int count = iBaseRepository.removeByMsgTableNameAndMsgId(userName, update.getId(), msgTableName);
        if (count == 0) {
            MsgDraftDTO msgDraftDTO = new MsgDraftDTO();
            msgDraftDTO.setMsgTableName("message");
            msgDraftDTO.setMsgId(update.getId());
            msgDraftDTO.setMsgTitle(update.getTitle());
            msgDraftDTO.setMsgTypeId(update.getMsgTypeId());
            msgDraftDTO.setOpenid(update.getOpenid());
            save(msgDraftDTO);
        }
    }

    @Override
    public MsgDraftDTO findByMsgIdAndOpenIdAndTable(String userName, MessageDTO update, String msgTableName) {
        Map map = iBaseRepository.findByMsgIdAndOpenIdAndTable(userName, update.getId(), msgTableName);
        MsgDraftDTO dto = CommonMybatisPageUtil2.getInstance().mapToDTO(map,MsgDraftDTO.class);
        return dto;
    }
}
