package com.hoioy.diamond.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.diamond.jiayin.domain.Message;
import com.hoioy.diamond.jiayin.domain.MsgCollect;
import com.hoioy.diamond.jiayin.mapper.MsgCollectMapper;
import com.hoioy.diamond.jiayin.service.IMsgCollectService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.MsgCollectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            Page page = DiamondMybatisPageUtil.getPage(pageDTO);
            MsgCollect msg = DiamondMybatisPageUtil.getBean(pageDTO, MsgCollect.class);
            IPage<MsgCollect> messageIPage = msgCollectMapper.selectPage(page, msg);
            PageDTO returnPageDTO = DiamondMybatisPageUtil.getPageDTO(messageIPage);
            return returnPageDTO;
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
