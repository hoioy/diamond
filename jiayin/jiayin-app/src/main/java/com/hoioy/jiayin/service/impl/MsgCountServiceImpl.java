package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgCount;
import com.hoioy.jiayin.dto.MsgCountDTO;
import com.hoioy.jiayin.mapper.MsgCountMapper;
import com.hoioy.jiayin.service.IMsgCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息次数 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgCountServiceImpl extends BaseServiceImpl<MsgCountMapper, MsgCount, MsgCountDTO> implements IMsgCountService<MsgCount> {

    @Autowired
    private MsgCountMapper msgCountMapper;


    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = DiamondMybatisPageUtil.getPage(pageDTO);
        MsgCount msg = DiamondMybatisPageUtil.getBean(pageDTO, MsgCount.class);
        IPage<MsgCount> messageIPage = msgCountMapper.selectPage(page, msg);
        PageDTO returnPageDTO = DiamondMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }

    @Override
    public MsgCount createDomain() {
        return new MsgCount();
    }

    @Override
    public MsgCountDTO createDTO() {
        return new MsgCountDTO();
    }
}
