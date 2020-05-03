package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgDraft;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import com.hoioy.jiayin.mapper.MsgDraftMapper;
import com.hoioy.jiayin.service.IMsgDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息草稿 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgDraftServiceImpl extends BaseServiceImpl<MsgDraftMapper, MsgDraft, MsgDraftDTO> implements IMsgDraftService<MsgDraft> {

    @Autowired
    private MsgDraftMapper msgDraftMapper;


    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = DiamondMybatisPageUtil.getPage(pageDTO);
        MsgDraft msg = DiamondMybatisPageUtil.getBean(pageDTO, MsgDraft.class);
        IPage<MsgDraft> messageIPage = msgDraftMapper.selectPage(page, msg);
        PageDTO returnPageDTO = DiamondMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }

    @Override
    public MsgDraft createDomain() {
        return new MsgDraft();
    }

    @Override
    public MsgDraftDTO createDTO() {
        return new MsgDraftDTO();
    }
}
