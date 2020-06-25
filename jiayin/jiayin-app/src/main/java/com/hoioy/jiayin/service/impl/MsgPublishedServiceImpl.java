package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil2;
import com.hoioy.jiayin.domain.MsgPublished;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgPublishedDTO;
import com.hoioy.jiayin.mapper.MsgPublishedMapper;
import com.hoioy.jiayin.service.IMsgPublishedService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsgPublishedServiceImpl extends BaseServiceImpl<MsgPublishedMapper, MsgPublished, MsgPublishedDTO> implements IMsgPublishedService<MsgPublished> {
    @Override
    public PageDTO getPage(PageDTO<MsgPublishedDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgPublishedDTO filters = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.selectPage(page, filters);
        PageDTO resultPage = CommonMybatisPageUtil2.getInstance().iPageToPageDTO(pageList, MsgPublishedDTO.class);
        return resultPage;
    }

//    @Override
//    public void saveOrUpdatePublished(String userName, MessageDTO update, String msgTableName) {
//        int count = iBaseRepository.updatePublished(userName, update, msgTableName);
//        if (count == 0) {
//            MsgPublishedDTO msgPublished = new MsgPublishedDTO();
//            msgPublished.setOpenid(userName);
//            msgPublished.setMsgTitle(update.getTitle());
//            msgPublished.setMsgId(update.getId());
//            msgPublished.setMsgTypeId(update.getMsgTypeId());
//            msgPublished.setMsgTableName("message");
//            save(msgPublished);
//        }
//    }

}
