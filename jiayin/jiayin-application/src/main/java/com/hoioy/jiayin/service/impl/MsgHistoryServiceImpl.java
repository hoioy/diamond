package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgHistory;
import com.hoioy.jiayin.dto.MsgHistoryDTO;
import com.hoioy.jiayin.mapper.MsgHistoryMapper;
import com.hoioy.jiayin.service.IMsgHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 观看历史 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgHistoryServiceImpl extends BaseServiceImpl<MsgHistoryMapper, MsgHistory,MsgHistoryDTO> implements IMsgHistoryService<MsgHistory> {

        @Autowired
        private MsgHistoryMapper msgHistoryMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = DiamondMybatisPageUtil.getPage(pageDTO);
            MsgHistory msgHistory = DiamondMybatisPageUtil.getBean(pageDTO, MsgHistory.class);
            IPage<MsgHistory> messageIPage = msgHistoryMapper.selectPage(page, msgHistory);
            PageDTO returnPageDTO = DiamondMybatisPageUtil.getPageDTO(messageIPage);
            return returnPageDTO;
        }

        @Override
        public MsgHistory createDomain() {
        return new  MsgHistory();
        }

        @Override
        public MsgHistoryDTO createDTO() {
        return new MsgHistoryDTO();
        }
}
