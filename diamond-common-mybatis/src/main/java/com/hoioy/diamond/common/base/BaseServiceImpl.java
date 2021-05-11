package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public abstract class BaseServiceImpl<I extends IBaseMapper<D>, D extends BaseDomain, DTO extends BaseDTO>
        extends BaseCommonServiceImpl<I, D, DTO> implements IBaseService<DTO, D> {

    @Override
    public Boolean removeById(String id) throws BaseException {
        super.removeById(id);

        iBaseRepository.deleteById(id);
        afterRemove(id);
        return true;
    }

    @Override
    public Boolean removeByIds(List<String> ids) throws BaseException {
        super.removeByIds(ids);

        Set<String> cacheKeys = new HashSet<>();
        ids.forEach(id ->
                cacheKeys.add(CacheKey_dto + "::" + CommonCacheUtil.getCacheKey(getDTOClass().getSimpleName(), id))
        );
        //删除对应的缓存
        commonCacheUtil.mremove(cacheKeys);
        iBaseRepository.deleteBatchIds(ids);

        afterBatchRemove(ids);
        return true;
    }
}
