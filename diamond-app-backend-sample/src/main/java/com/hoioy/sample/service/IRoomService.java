package com.hoioy.sample.service;

import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.service.ICommonService;
import com.hoioy.diamond.sys.exception.SysException;
import cn.hutool.core.util.IdUtil;
import com.hoioy.sample.domain.Room;
import com.hoioy.sample.dto.RoomDTO;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public interface IRoomService extends ICommonService<RoomDTO, Room> {

    RoomDTO create(RoomDTO roomDTO);

    boolean removeById(String id);

    boolean removeByIds(List<String> ids);

    RoomDTO findById(String id);

    List<RoomDTO> findByIds(List<String> ids);

    boolean batchCreate(List<RoomDTO> teacherDTOList);

    RoomDTO update(RoomDTO roomDTO);

    default boolean remove(RoomDTO roomDTO) {
        return removeById(roomDTO.getPk());
    }

    @Transactional(rollbackFor = Exception.class)
    default boolean batchRemove(List<RoomDTO> teacherDTOList) {
        List<String> idList = new ArrayList<>();

        teacherDTOList.forEach(t -> {
            idList.add(t.getPk());
        });
        return removeByIds(idList);
    }

    default void beforeRemove(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new SysException("ID集合不能为空");
        }
        for (String id : ids) {
            if (StrUtil.isBlank(id)) {
                throw new SysException("ID不能为空");
            }
        }
    }

    default RoomDTO beforeCreate(RoomDTO roomDTO) {
        if (!StrUtil.isBlank(roomDTO.getPk())) {
            throw new CommonException("新增时候Id属性不能有值,此方法只能用于新增操作，更新请调用update方法");
        }
        roomDTO.setPk(IdUtil.simpleUUID());
        // 初始化
        roomDTO.setFlag(1);
        return roomDTO;
    }
}