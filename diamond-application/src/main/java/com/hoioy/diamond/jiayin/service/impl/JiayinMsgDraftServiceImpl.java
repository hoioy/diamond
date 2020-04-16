package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.JiayinMsgDraft;
import com.hoioy.diamond.jiayin.mapper.JiayinMsgDraftMapper;
import com.hoioy.diamond.jiayin.service.IJiayinMsgDraftService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.JiayinMsgDraftDTO;
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
 * @since 2020-04-16
 */
@Service
public class JiayinMsgDraftServiceImpl extends BaseServiceImpl<JiayinMsgDraftMapper, JiayinMsgDraft,JiayinMsgDraftDTO> implements IJiayinMsgDraftService<JiayinMsgDraft> {

        @Autowired
        private JiayinMsgDraftMapper jiayinMsgDraftMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            JiayinMsgDraft jiayinMsgDraft = TDFMybatisPageUtil.getBean(pageDTO, JiayinMsgDraft.class);
            IPage<JiayinMsgDraft> jiayinMsgDraftIPage = jiayinMsgDraftMapper.selectPage(page, jiayinMsgDraft);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(jiayinMsgDraftIPage);
            return resultPage;
        }

        @Override
        public JiayinMsgDraft createDomain() {
        return new  JiayinMsgDraft();
        }

        @Override
        public JiayinMsgDraftDTO createDTO() {
        return new JiayinMsgDraftDTO();
        }
}
