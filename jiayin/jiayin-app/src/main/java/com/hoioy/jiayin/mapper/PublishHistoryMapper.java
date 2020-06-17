package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.PublishHistory;
import com.hoioy.jiayin.dto.MessageDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
public interface PublishHistoryMapper extends IBaseMapper<PublishHistory> {

    /**
     *  分页
     */
    IPage<PublishHistory> selectPage(@Param("page") Page page, @Param("publishHistory") PublishHistory publishHistory);

    int updateByPubilshId(PublishHistory publishHistory);

    int updatePublishHistory(@Param("openid") String openid, @Param("dto") MessageDTO dto, @Param("publishType") String publishType);
}
