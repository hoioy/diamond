package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.service.AbstractBaseServiceImpl;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.common.util.CommonBeanUtil;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.common.validator.exception.ValidateException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public abstract class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseDomain, DTO extends BaseDTO>
        extends AbstractBaseServiceImpl<M, T, DTO> implements IBaseService<DTO, T> {
    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DTO create(DTO dto) throws BaseException {
        dto = beforeSave(dto);
        T t = createDomain();
        CommonBeanUtil.saveCopy(dto, t);
        iBaseRepository.insert(t);
        return domainToDTO(t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DTO update(DTO dto) throws BaseException {
        if (StrUtil.isBlank(dto.getId())) {
            throw new ValidateException("ID不能为空");
        }
        T t = createDomain();
        CommonBeanUtil.updateCopy(dto, t);
        if (iBaseRepository.updateById(t) > 0) {
            return domainToDTO(t);
        }
        throw new CommonException("更新失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        beforeRemove(Arrays.asList(id));
        iBaseRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(List<String> ids) throws BaseException {
        beforeRemove(ids);
        Set<String> cacheKeys = new HashSet<>();
        ids.forEach(id -> {
            cacheKeys.add(CacheKey_dto + "::" + CommonRedisUtil.getCacheKey(getDTOClass().getSimpleName(), id));
        });
        //删除对应的缓存
        commonRedisUtil.mremove(cacheKeys);
        iBaseRepository.deleteBatchIds(ids);
        return true;
    }

    @Override
    public DTO findById(String id) throws BaseException {
        T domain = iBaseRepository.selectById(id);
        if (domain == null) {
            return null;
        }
        DTO dto = domainToDTO(domain, true);
        return dto;
    }

    @Override
    public List<DTO> findByIds(List<String> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            List<T> domains = iBaseRepository.selectBatchIds(ids);
            return domainListToDTOList(domains);
        } else {
            return new ArrayList<DTO>();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCreate(List<DTO> dtoList) throws BaseException {
        dtoList.stream().forEach(dto -> {
            dto = beforeSave(dto);
            T domain = createDomain();
            CommonBeanUtil.saveCopy(dto, domain);
            iBaseRepository.insert(domain);
        });
        return true;
    }
}
