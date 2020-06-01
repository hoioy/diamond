package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.service.AbstractBaseServiceImpl;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.common.util.*;
import com.hoioy.diamond.common.validator.exception.ValidateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 基础service实现
 *
 * @param <I>   dao层基础接口
 * @param <D>   domain
 * @param <DTO> dto
 */
public abstract class BaseServiceImpl<I extends IBaseRepository<D>, D extends BaseDomain, DTO extends BaseDTO>
        extends AbstractBaseServiceImpl<I,D,DTO> implements IBaseService<DTO, D> {
    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Override
    public Optional<DTO> findById(String id) throws BaseException {
        Optional<D> t = iBaseRepository.findById(id);
        if(!t.isPresent()) {
            return Optional.ofNullable(null);
        }
        DTO dto = domainToDTO(t.get(), true);
        return Optional.ofNullable(dto);
    }

    @Override
    public List<DTO> findByIds(List<String> ids) {
        List<D> ts = iBaseRepository.findAllById(ids);
        List<DTO> dtos = domainListToDTOList(ts);
        return dtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DTO save(DTO dto) throws BaseException {
        dto = beforeSave(dto);
        D t = dtoToDomain(dto, true);
        t = iBaseRepository.saveAndFlush(t);
        return domainToDTO(t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSave(List<DTO> dtoList) throws BaseException {
        List<D> ts = new ArrayList();
        dtoList.forEach(dto -> {
            D t = dtoToDomain(dto, true);
            ts.add(t);
        });
        iBaseRepository.saveAll(ts);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DTO update(DTO dto) throws BaseException {
        if (StringUtils.isBlank(dto.getId())) {
            throw new ValidateException("ID不能为空");
        }
        D t = dtoToDomain(dto, false);

        String id = t.getId();
        Optional<D> old = iBaseRepository.findById(id);
        if (old.isPresent()) {
            D oldDomain = old.get();
            oldDomain.setModifiedBy(CommonSecurityUtils.getCurrentLogin());
            oldDomain.setModifiedDate(LocalDateTime.now());
            CommonBeanUtil.updateCopy(t, oldDomain);
            return domainToDTO(iBaseRepository.saveAndFlush(oldDomain));
        }
        throw new CommonException("没有找到此对象，无法更新");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        DTO dto = createDTO();
        dto.setId(id);
        dto.setFlag(0);
        update(dto);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //@CacheEvict(value="role",key="#ids") //TODO
    //@CachePut(value="DataItem", keyGenerator="BaseJoinCacheKeyGenerator")
    public boolean removeByIds(List<String> ids) throws BaseException {
        //TODO 有性能问题，需要优化
        List<D> ts = new ArrayList();
        Set<String> cacheKeys = new HashSet<>();

        ids.forEach(id -> {
            if (StringUtils.isBlank(id)) {
                throw new ValidateException("ID不能为空");
            }

            Optional<D> old = iBaseRepository.findById(id);
            if (old.isPresent()) {
                old.get().setFlag(0);
                ts.add(old.get());
                cacheKeys.add(CacheKey_dto + "::" + CommonRedisUtil.getCacheKey(getDTOClass().getSimpleName(), id));
            }
        });
        if (!CollectionUtils.isEmpty(ts)) {
            iBaseRepository.saveAll(ts);
            //删除对应的缓存
            commonRedisUtil.mremove(cacheKeys);
        }
        return true;
    }

    public PageDTO<DTO> getPage(final PageDTO<DTO> pageDTO) {
        PageRequest pageable = CommonJpaPageUtil.getInstance().toPageRequest(pageDTO);
        D domain = getDomainFilterFromPageDTO(pageDTO);
        Specification<D> specification = toSpecWithLogicType(domain, "and");

        Page<D> pageList = iBaseRepository.findAll(specification, pageable);
        pageDTO.setTotal(pageList.getTotalElements());
        pageDTO.setList(domainListToDTOList(pageList.getContent()));
        return pageDTO;
    }

    /**
     * 如果不满足需求，子类可以override此方法进行扩展
     *
     * @param main      主表domain
     * @param logicType and / or
     * @return
     */
    protected Specification<D> toSpecWithLogicType(D main, String logicType) {
        return CommonJpaPageUtil.getInstance().toSpecWithLogicType(main, logicType);
    }
}
