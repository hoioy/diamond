package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.JiayinMsgCollect;
import com.hoioy.diamond.jiayin.mapper.JiayinMsgCollectMapper;
import com.hoioy.diamond.jiayin.service.IJiayinMsgCollectService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.JiayinMsgCollectDTO;
import org.springframework.stereotype.Service;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.BaseException;
/**
 * <p>
 * 消息收藏 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Service
public class JiayinMsgCollectServiceImpl extends BaseServiceImpl<JiayinMsgCollectMapper, JiayinMsgCollect,JiayinMsgCollectDTO> implements IJiayinMsgCollectService<JiayinMsgCollect> {

        @Autowired
        private JiayinMsgCollectMapper jiayinMsgCollectMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            JiayinMsgCollect jiayinMsgCollect = TDFMybatisPageUtil.getBean(pageDTO, JiayinMsgCollect.class);
            IPage<JiayinMsgCollect> jiayinMsgCollectIPage = jiayinMsgCollectMapper.selectPage(page, jiayinMsgCollect);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(jiayinMsgCollectIPage);
            return resultPage;
        }

        @Override
        public JiayinMsgCollect createDomain() {
        return new  JiayinMsgCollect();
        }

        @Override
        public JiayinMsgCollectDTO createDTO() {
        return new JiayinMsgCollectDTO();
        }
}
