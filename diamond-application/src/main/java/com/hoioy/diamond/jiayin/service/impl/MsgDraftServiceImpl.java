package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.MsgDraft;
import com.hoioy.diamond.jiayin.mapper.MsgDraftMapper;
import com.hoioy.diamond.jiayin.service.IMsgDraftService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.MsgDraftDTO;
import org.springframework.stereotype.Service;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.BaseException;
/**
 * <p>
 * 消息草稿 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgDraftServiceImpl extends BaseServiceImpl<MsgDraftMapper, MsgDraft,MsgDraftDTO> implements IMsgDraftService<MsgDraft> {

        @Autowired
        private MsgDraftMapper msgDraftMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            MsgDraft msgDraft = TDFMybatisPageUtil.getBean(pageDTO, MsgDraft.class);
            IPage<MsgDraft> msgDraftIPage = msgDraftMapper.selectPage(page, msgDraft);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(msgDraftIPage);
            return resultPage;
        }

        @Override
        public MsgDraft createDomain() {
        return new  MsgDraft();
        }

        @Override
        public MsgDraftDTO createDTO() {
        return new MsgDraftDTO();
        }
}
