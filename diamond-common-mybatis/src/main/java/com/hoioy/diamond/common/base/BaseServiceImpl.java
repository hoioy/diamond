package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.common.util.TDFBeanUtil;
import com.hoioy.diamond.common.util.TDFReflectionUtil;
import com.hoioy.diamond.common.validator.exception.ValidateException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseDomain, DTO extends BaseDTO> extends ServiceImpl<M, T> implements IBaseService<DTO, T> {
    protected Class<T> domainClass = (Class<T>) TDFReflectionUtil.getSuperClassGenericType(getClass(), 1);
    protected Class<DTO> dtoClass = (Class<DTO>) TDFReflectionUtil.getSuperClassGenericType(getClass(), 2);

    @Override
    public final Class<T> getDomainClass() {
        return domainClass;
    }

    @Override
    public final Class<DTO> getDTOClass() {
        return dtoClass;
    }

    public final DTO domainToDTO(T domain) {
        return domainToDTO(domain, true);
    }

    public final T dtoToDomain(DTO dto) {
        return dtoToDomain(dto, true);
    }

    public final List<DTO> domainListToDTOList(List<T> dList) {
        return domainListToDTOList(dList, true);
    }

    public final List<T> dtoListToDomainList(List<DTO> dtoList) {
        return dtoListToDomainList(dtoList, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(DTO dto) throws BaseException {
        T t = createDomain();
        TDFBeanUtil.saveCopy(dto, t);
        super.saveOrUpdate(t);
        return t.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(DTO dto) throws BaseException {
        if (StrUtil.isBlank(dto.getId())) {
            throw new ValidateException("ID不能为空");
        }
        T t = createDomain();
        TDFBeanUtil.updateCopy(dto, t);
        super.saveOrUpdate(t);
        return t.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(List<String> ids) throws BaseException {
        return super.removeByIds(ids);
    }

    @Override
    public Optional<DTO> findById(String id) throws BaseException {
        T byId = super.getById(id);
        DTO dto = createDTO();
        TDFBeanUtil.saveCopy(byId, dto);
        Optional<DTO> optionalDTO = Optional.ofNullable(dto);
        return optionalDTO;
    }

    @Override
    public List<DTO> findByIds(List<String> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            List<T> domains = super.listByIds(ids);
            return domainListToDTOList(domains);
        } else {
            return new ArrayList<DTO>();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSave(Collection<DTO> entityList) throws BaseException {
        List<T> list = new ArrayList<>();
        entityList.stream().forEach(x -> {
            T domain = createDomain();
            TDFBeanUtil.saveCopy(x, domain);
            list.add(domain);
        });
        return super.saveBatch(list);
    }
}
