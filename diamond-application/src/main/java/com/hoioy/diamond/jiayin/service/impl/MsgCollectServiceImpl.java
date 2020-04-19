package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.MsgCollect;
import com.hoioy.diamond.jiayin.mapper.MsgCollectMapper;
import com.hoioy.diamond.jiayin.service.IMsgCollectService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.MsgCollectDTO;
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
 * @since 2020-04-19
 */
@Service
public class MsgCollectServiceImpl extends BaseServiceImpl<MsgCollectMapper, MsgCollect,MsgCollectDTO> implements IMsgCollectService<MsgCollect> {

        @Autowired
        private MsgCollectMapper msgCollectMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            MsgCollect msgCollect = TDFMybatisPageUtil.getBean(pageDTO, MsgCollect.class);
            IPage<MsgCollect> msgCollectIPage = msgCollectMapper.selectPage(page, msgCollect);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(msgCollectIPage);
            return resultPage;
        }

        @Override
        public MsgCollect createDomain() {
        return new  MsgCollect();
        }

        @Override
        public MsgCollectDTO createDTO() {
        return new MsgCollectDTO();
        }
}
