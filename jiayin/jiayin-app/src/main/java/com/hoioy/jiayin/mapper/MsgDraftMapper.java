package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgDraft;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface MsgDraftMapper extends IBaseMapper<MsgDraft> {

    IPage<Map> selectPage(@Param("page") Page page, @Param("msgDraft") MsgDraftDTO msgDraft);

    int removeByMsgTableNameAndMsgId(@Param("openid") String openid, @Param("msgId") String msgId, @Param("msgTableName") String msgTableName);
}
