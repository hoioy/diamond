package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgType;
import com.hoioy.jiayin.dto.MsgTypeDTO;
import com.hoioy.jiayin.mapper.MsgTypeMapper;
import com.hoioy.jiayin.service.IMsgTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息类型 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgTypeServiceImpl extends BaseTreeServiceImpl<MsgTypeMapper, MsgType, MsgTypeDTO> implements IMsgTypeService<MsgType> {

    @Autowired
    private MsgTypeMapper msgTypeMapper;


    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgType msgType = getDomainFilterFromPageDTO(pageDTO);
        IPage<MsgType> messageIPage = msgTypeMapper.selectPage(page, msgType);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }


    @Override
    public List<MsgTypeDTO>  selectAllParent() {
        List<MsgType> msgTypes = msgTypeMapper.selectAllParent();
        List<MsgTypeDTO> msgTypeDTOS = this.domainListToDTOList(msgTypes);
        return msgTypeDTOS;
    }

    @Override
    public List<MsgTypeDTO> selectChildrenByParentId(String parentId) {
        List<MsgType>  msgTypes =msgTypeMapper.selectChildrenByParentId(parentId);
        List<MsgTypeDTO> msgTypeDTOS = this.domainListToDTOList(msgTypes);
        return msgTypeDTOS;
    }
}
