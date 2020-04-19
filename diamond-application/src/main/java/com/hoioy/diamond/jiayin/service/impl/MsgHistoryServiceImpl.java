package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.MsgHistory;
import com.hoioy.diamond.jiayin.mapper.MsgHistoryMapper;
import com.hoioy.diamond.jiayin.service.IMsgHistoryService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.MsgHistoryDTO;
import org.springframework.stereotype.Service;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.BaseException;
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
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            MsgHistory msgHistory = TDFMybatisPageUtil.getBean(pageDTO, MsgHistory.class);
            IPage<MsgHistory> msgHistoryIPage = msgHistoryMapper.selectPage(page, msgHistory);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(msgHistoryIPage);
            return resultPage;
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
