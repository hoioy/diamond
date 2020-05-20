package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.jiayin.domain.PublishHistory;
import com.hoioy.jiayin.dto.PublishHistoryDTO;
import com.hoioy.jiayin.mapper.PublishHistoryMapper;
import com.hoioy.jiayin.service.IPublishHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
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
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = DiamondMybatisPageUtil.getPage(pageDTO);
            PublishHistory publishHistory = DiamondMybatisPageUtil.getBean(pageDTO, PublishHistory.class);
            IPage<PublishHistory> publishHistoryIPage = publishHistoryMapper.selectPage(page, publishHistory);
            PageDTO resultPage = DiamondMybatisPageUtil.getPageDTO(publishHistoryIPage);
            return resultPage;
        }

        @Override
        public PublishHistory createDomain() {
        return new  PublishHistory();
        }

        @Override
        public PublishHistoryDTO createDTO() {
        return new PublishHistoryDTO();
        }
}
