package com.hoioy.sample.mapper;

import com.hoioy.diamond.common.base.ICommonDaoRepository;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.Room;
import org.apache.ibatis.annotations.Param;

public interface RoomMapper extends ICommonDaoRepository<Room> {

    /**
     * 分页
     */
    IPage<Room> selectPage(@Param("page") Page page, @Param("room") Room room);
}
