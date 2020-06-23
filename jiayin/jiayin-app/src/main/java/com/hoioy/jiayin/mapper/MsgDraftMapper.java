package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgDraft;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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
    IPage<Map> selectPage(@Param("page") Page page, @Param("msgDraft") MsgDraftDTO msgDraft);

    int removeByUserNameAndMsgId(@Param("openid") String openid, @Param("msgId") String msgId,@Param("messageType") String messageType);
}
