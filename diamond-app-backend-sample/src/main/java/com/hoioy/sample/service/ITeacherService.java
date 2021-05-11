package com.hoioy.sample.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.hoioy.diamond.common.dto.CommonPageDTO;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.sample.dto.TeacherDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public interface ITeacherService {

    CommonPageDTO<TeacherDTO> getPage(CommonPageDTO<TeacherDTO> commonPageDTO);

    TeacherDTO create(TeacherDTO teacherDTO);

    boolean removeById(String id);

    boolean removeByIds(List<String> ids);

    TeacherDTO findById(String id);

    List<TeacherDTO> findByIds(List<String> ids);

    boolean batchCreate(List<TeacherDTO> teacherDTOList);

    TeacherDTO update(TeacherDTO teacherDTO);

    default boolean remove(TeacherDTO teacherDTO) {
        return removeById(teacherDTO.getPk());
    }

    @Transactional(rollbackFor = Exception.class)
    default boolean batchRemove(List<TeacherDTO> teacherDTOList) {
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

    default TeacherDTO beforeCreate(TeacherDTO teacherDTO) {
        if (StrUtil.isNotBlank(teacherDTO.getPk())) {
            throw new CommonException("新增时候Id属性不能有值,此方法只能用于新增操作，更新请调用update方法");
        }
        teacherDTO.setPk(IdUtil.simpleUUID());
        // 初始化
        teacherDTO.setFlag(1);
        return teacherDTO;
    }
}