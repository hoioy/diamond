package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgDraft;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 消息草稿 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface MsgDraftMapper extends IBaseMapper<MsgDraft> {

    /**
     *  分页
     */
    IPage<MsgDraft> selectPage(@Param("page") Page page, @Param("msgDraft") MsgDraft msgDraft);

    int removeByUserNameAndMsgId(@Param("userName") String userName, @Param("msgId") String msgId,@Param("messageType") String messageType);
}
