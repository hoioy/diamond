package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgPublished;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgPublishedDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface MsgPublishedMapper extends IBaseMapper<MsgPublished> {
    IPage<Map> selectPage(@Param("page") Page page, @Param("msgPublished") MsgPublishedDTO msgPublished);

    int updateByPubilshId(MsgPublished msgPublished);

    boolean removeById(String id);

//    int updatePublished(@Param("openid") String openid, @Param("dto") MessageDTO dto, @Param("msgTableName") String msgTableName);
}
