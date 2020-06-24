package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgPublished;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgPublishedDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
public interface MsgPublishedMapper extends IBaseMapper<MsgPublished> {

    /**
     *  分页
     */
    IPage<Map> selectPage(@Param("page") Page page, @Param("msgPublished") MsgPublishedDTO msgPublished);

    int updateByPubilshId(MsgPublished msgPublished);

    int updatePublished(@Param("openid") String openid, @Param("dto") MessageDTO dto, @Param("msgTableName") String msgTableName);
}
