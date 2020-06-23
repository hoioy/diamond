package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil2;
import com.hoioy.jiayin.domain.PublishHistory;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import com.hoioy.jiayin.dto.PublishHistoryDTO;
import com.hoioy.jiayin.mapper.PublishHistoryMapper;
import com.hoioy.jiayin.service.IPublishHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
@Service
public class PublishHistoryServiceImpl extends BaseServiceImpl<PublishHistoryMapper, PublishHistory, PublishHistoryDTO> implements IPublishHistoryService<PublishHistory> {

    @Autowired
    private PublishHistoryMapper publishHistoryMapper;


    @Override
    public PageDTO getPage(PageDTO<PublishHistoryDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        PublishHistoryDTO filters = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.selectPage(page, filters);
        PageDTO resultPage = CommonMybatisPageUtil2.getInstance().iPageToPageDTO(pageList, PublishHistoryDTO.class);
        return resultPage;
    }

    @Override
    public void saveOrUpdateDraft(String userName, MessageDTO update, String publishType) {
        int count = publishHistoryMapper.updatePublishHistory(userName, update, publishType);
        if (count == 0) {
            PublishHistoryDTO publishHistory = new PublishHistoryDTO();
            publishHistory.setOpenid(userName);
            publishHistory.setPublishTitle(update.getTitle());
            publishHistory.setPublishId(update.getId());
            publishHistory.setMsgTypeId(update.getMsgTypeId());
            publishHistory.setPublishType("message");
            save(publishHistory);
        }
    }

}
