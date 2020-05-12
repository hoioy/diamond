package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.DiamondUserDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.diamond.common.util.DiamondSecurityUtils;
import com.hoioy.diamond.security.DiamondUser;
import com.hoioy.diamond.security.SecurityUtils;
import com.hoioy.jiayin.domain.Message;
import com.hoioy.jiayin.domain.MsgCount;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.exception.JiayinException;
import com.hoioy.jiayin.mapper.MessageMapper;
import com.hoioy.jiayin.mapper.MsgCountMapper;
import com.hoioy.jiayin.service.IMessageService;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, MessageDTO> implements IMessageService<Message> {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MsgCountMapper msgCountMapper;


    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = DiamondMybatisPageUtil.getPage(pageDTO);
        Message msg = DiamondMybatisPageUtil.getBean(pageDTO, Message.class);
        IPage<Message> messageIPage = messageMapper.selectPage(page, msg);
        PageDTO returnPageDTO = DiamondMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }

    /**
     * 消息发布
     * @param dto
     * @return
     */
    @Override
    public String save(MessageDTO dto) {
        String userName = DiamondSecurityUtils.getCurrentLogin();
        dto.setOpenid(userName);
        //查询用户次数表 看用户是否可以发布消息，
        MsgCount msgCount = msgCountMapper.selectByOpenid(userName);
        if (msgCount != null && msgCount.getMsgCount() > 0) {
            String save = super.save(dto);
            //发布成功减少次数
            msgCount.setMsgCount(msgCount.getMsgCount()-1);
            int i = msgCountMapper.updateById(msgCount);
            return save;
        }else{
            throw new JiayinException("发布消息次数不足，可以通过分享增加发布次数");
        }

    }
}
