package com.hoioy.diamond.jiayin.mapper;

import com.hoioy.diamond.jiayin.domain.Message;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface MessageMapper extends IBaseMapper<Message> {

    /**
     *  分页
     */
    IPage<Message> selectPage(@Param("page") Page page, @Param("message") Message message);
}
