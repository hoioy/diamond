package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseCommonDTO;
import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.service.IBaseCommonService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

abstract class BaseCommonServiceImpl<I extends IBaseCommonRepository<D>, D extends BaseCommonDomain, DTO extends BaseCommonDTO>
        extends CommonServiceImpl<I, D, DTO> implements IBaseCommonService<DTO, D> {

    @Override
    public DTO findById(String id) throws BaseException {
        D domain = iBaseRepository.selectById(id);
        if (domain == null) {
            return null;
        }
        return domainToDTO(domain, true);
    }

    @Override
    public List<DTO> findByIds(List<String> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            List<D> domains = iBaseRepository.selectBatchIds(ids);
            return domainListToDTOList(domains);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public DTO create(DTO dto) throws BaseException {
        dto = IBaseCommonService.super.create(dto);

        D t = dtoToDomain(dto, true);

        if (iBaseRepository.insert(t) > 0) {
            if (dto instanceof BaseJoinDTO) {
                deleteCache();
            }

            afterCreate(dto);
            return domainToDTO(t, true);
        }
        throw new CommonException("保存失败");
    }

    @Override
    public Boolean batchCreate(List<DTO> dtoList) throws BaseException {
        IBaseCommonService.super.batchCreate(dtoList);

        List<D> ts = new ArrayList();
        dtoList.forEach(dto -> {
            D t = dtoToDomain(dto, true);
            ts.add(t);
        });
        mybatisPlusServiceImpl.saveBatch(ts);

        if (CollectionUtil.isNotEmpty(dtoList)
                && dtoList.get(0) instanceof BaseJoinDTO) {
            deleteCache();
        }

        afterBatchCreate(dtoList);
        return true;
    }

    @Override
    public DTO update(DTO dto) throws BaseException {
        dto = IBaseCommonService.super.update(dto);
        dto = commonUpdate(dto);
        return afterUpdate(dto);
    }

    protected final DTO commonUpdate(DTO dto) {
        D t = dtoToDomain(dto, false);
        if (iBaseRepository.updateById(t) > 0) {
            return domainToDTO(t);
        }

        if (t instanceof BaseJoinDomain) {
            deleteCache();
        }
        throw new CommonException("更新失败");
    }
}
