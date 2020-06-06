package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.jiayin.domain.MsgCollect;
import com.hoioy.jiayin.dto.MsgCollectDTO;
import com.hoioy.jiayin.mapper.MsgCollectMapper;
import com.hoioy.jiayin.service.IMsgCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 消息收藏 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgCollectServiceImpl extends BaseServiceImpl<MsgCollectMapper, MsgCollect, MsgCollectDTO> implements IMsgCollectService<MsgCollect> {

    @Autowired
    private MsgCollectMapper msgCollectMapper;


    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgCollect msgCollect = getDomainFilterFromPageDTO(pageDTO);
        IPage<MsgCollect> messageIPage = msgCollectMapper.selectPage(page, msgCollect);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgCollectDTO save(MsgCollectDTO dto) throws BaseException {
        String userName = CommonSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
        return super.save(dto);
    }
}
