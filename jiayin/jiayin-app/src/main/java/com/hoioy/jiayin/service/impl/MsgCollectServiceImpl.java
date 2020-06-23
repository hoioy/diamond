package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil2;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.jiayin.domain.MsgCollect;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.dto.MsgCollectDTO;
import com.hoioy.jiayin.mapper.MsgCollectMapper;
import com.hoioy.jiayin.service.IMsgCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
    @Override
    public PageDTO getPage(PageDTO<MsgCollectDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgCollectDTO filters = (MsgCollectDTO) pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.selectPage(page, filters);
        PageDTO resultPage = CommonMybatisPageUtil2.getInstance().iPageToPageDTO(pageList, MsgCollectDTO.class);
        return resultPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgCollectDTO save(MsgCollectDTO dto) throws BaseException {
        String userName = CommonSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
        dto.setMessageType("message");
        return super.save(dto);
    }
}
