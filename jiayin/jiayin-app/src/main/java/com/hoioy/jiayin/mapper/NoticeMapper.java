package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.Message;
import com.hoioy.jiayin.domain.Notice;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.dto.NoticeDTO;
import org.apache.ibatis.annotations.Param;

public interface NoticeMapper extends IBaseMapper<Notice> {

    /**
     *  分页
     */
    IPage<Notice> selectPage(@Param("page") Page page, @Param("noticeDTO") NoticeDTO noticeDTO);
}
