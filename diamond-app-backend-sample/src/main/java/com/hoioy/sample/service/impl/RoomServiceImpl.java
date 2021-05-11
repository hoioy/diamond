package com.hoioy.sample.service.impl;


import com.hoioy.diamond.common.base.CommonServiceImpl;
import com.hoioy.diamond.common.dto.CommonPageDTO;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.exception.SysException;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.Room;
import com.hoioy.sample.dto.RoomDTO;
import com.hoioy.sample.dto.StudentDTO;
import com.hoioy.sample.mapper.RoomMapper;
import com.hoioy.sample.service.IRoomService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoomServiceImpl extends CommonServiceImpl<RoomMapper, Room, RoomDTO> implements IRoomService {

    public CommonPageDTO<RoomDTO> getPage(CommonPageDTO<RoomDTO> commonPageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(commonPageDTO);
        RoomDTO dto = commonPageDTO.getFilters();
        Room domain = dtoToDomain(dto, true);
        IPage<Room> studentIPage = iBaseRepository.selectPage(page, domain);
        CommonPageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToCommonPageDTO(studentIPage, StudentDTO.class, commonPageDTO);
        return resultPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomDTO create(RoomDTO roomDTO) {
        roomDTO = beforeCreate(roomDTO);
        Room t = dtoToDomain(roomDTO, true);

        if (iBaseRepository.insert(t) > 0) {
            return domainToDTO(t, true);
        }
        throw new CommonException("保存失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) {
        beforeRemove(Arrays.asList(id));
        iBaseRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(List<String> ids) {
        beforeRemove(ids);
        Set<String> cacheKeys = new HashSet<>();
        iBaseRepository.deleteBatchIds(ids);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCreate(List<RoomDTO> roomDTOList) {
        List<Room> ts = new ArrayList();
        roomDTOList.forEach(dto -> {
            dto = beforeCreate(dto);
            Room t = dtoToDomain(dto, true);
            ts.add(t);
        });
        mybatisPlusServiceImpl.saveBatch(ts);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomDTO update(RoomDTO roomDTO) {
        if (StrUtil.isBlank(roomDTO.getPk())) {
            throw new SysException("ID不能为空");
        }
        Room t = dtoToDomain(roomDTO, false);
        if (iBaseRepository.updateById(t) > 0) {
            return domainToDTO(t, true);
        }

        throw new CommonException("更新失败");
    }

    @Override
    public RoomDTO findById(String id) {
        Room domain = iBaseRepository.selectById(id);
        if (domain == null) {
            return null;
        }
        return domainToDTO(domain, true);
    }

    @Override
    public List<RoomDTO> findByIds(List<String> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            List<Room> domains = iBaseRepository.selectBatchIds(ids);
            return domainListToDTOList(domains, true);
        } else {
            return new ArrayList<>();
        }
    }

}
