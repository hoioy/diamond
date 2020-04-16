package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.JiayinMsgHistory;
import com.hoioy.diamond.jiayin.mapper.JiayinMsgHistoryMapper;
import com.hoioy.diamond.jiayin.service.IJiayinMsgHistoryService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.JiayinMsgHistoryDTO;
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
 * @since 2020-04-16
 */
@Service
public class JiayinMsgHistoryServiceImpl extends BaseServiceImpl<JiayinMsgHistoryMapper, JiayinMsgHistory,JiayinMsgHistoryDTO> implements IJiayinMsgHistoryService<JiayinMsgHistory> {

        @Autowired
        private JiayinMsgHistoryMapper jiayinMsgHistoryMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            JiayinMsgHistory jiayinMsgHistory = TDFMybatisPageUtil.getBean(pageDTO, JiayinMsgHistory.class);
            IPage<JiayinMsgHistory> jiayinMsgHistoryIPage = jiayinMsgHistoryMapper.selectPage(page, jiayinMsgHistory);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(jiayinMsgHistoryIPage);
            return resultPage;
        }

        @Override
        public JiayinMsgHistory createDomain() {
        return new  JiayinMsgHistory();
        }

        @Override
        public JiayinMsgHistoryDTO createDTO() {
        return new JiayinMsgHistoryDTO();
        }
}
