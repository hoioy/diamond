package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.exception.DiamondCommonJPAException;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.common.util.DiamondBeanUtil;
import com.hoioy.diamond.common.util.DiamondJpaPageUtil;
import com.hoioy.diamond.common.util.DiamondReflectionUtil;
import com.hoioy.diamond.common.validator.exception.ValidateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public abstract class BaseServiceImpl<I extends IBaseRepository<D, String>, D extends BaseDomain, DTO extends BaseDTO> implements IBaseService<DTO, D> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected Class<D> domainClass = (Class<D>) DiamondReflectionUtil.getSuperClassGenericType(getClass(), 1);
    protected Class<DTO> dtoClass = (Class<DTO>) DiamondReflectionUtil.getSuperClassGenericType(getClass(), 2);

    @Autowired
    protected I iBaseRepository;

    @Override
    public final Class<D> getDomainClass() {
        return domainClass;
    }

    @Override
    public final Class<DTO> getDTOClass() {
        return dtoClass;
    }

    public final DTO domainToDTO(D domain) {
        return domainToDTO(domain, true);
    }

    public final D dtoToDomain(DTO dto) {
        return dtoToDomain(dto, true);
    }

    public final List<DTO> domainListToDTOList(List<D> dList) {
        return domainListToDTOList(dList, true);
    }

    public final List<D> dtoListToDomainList(List<DTO> dtoList) {
        return dtoListToDomainList(dtoList, true);
    }

    @Override
    public Optional<DTO> findById(String id) throws BaseException {
        Optional<D> t = iBaseRepository.findById(id);
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
    //	@Caching(put={
//			@CachePut(value = "dto", key = "#p0.id")
//	})//TODO
    @Transactional(rollbackFor = Exception.class)
    public String save(DTO dto) throws BaseException {
        if (!StringUtils.isEmpty(dto.getId())) {
            throw new DiamondCommonJPAException("此方法只能用于新增操作，更新请调用update方法");
        }
        D t = dtoToDomain(dto, true);
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        t.setId(id);
        t.setFlag(1);// 初始化
        t = iBaseRepository.saveAndFlush(t);
        return t.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSave(Collection<DTO> entityList) throws BaseException {
        List<D> ts = new ArrayList();
        entityList.forEach(dto -> {
            D t = dtoToDomain(dto, true);
            ts.add(t);
        });
        iBaseRepository.saveAll(ts);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(DTO dto) throws BaseException {
        if (StringUtils.isBlank(dto.getId())) {
            throw new ValidateException("ID不能为空");
        }
        D t = dtoToDomain(dto, false);

        String id = t.getId();
        Optional<D> old = iBaseRepository.findById(id);
        if (old.isPresent()) {
            D oldDomain = old.get();
            SecurityContext context = SecurityContextHolder.getContext();
            if (null != context.getAuthentication() && context.getAuthentication().isAuthenticated()) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                oldDomain.setModifiedBy(null == userDetails ? "" : userDetails.getUsername());
            }
            oldDomain.setModifiedDate(LocalDateTime.now());
            DiamondBeanUtil.updateCopy(t, oldDomain);
            return iBaseRepository.saveAndFlush(oldDomain).getId();
        }
        throw new DiamondCommonJPAException("没有找到此对象，无法更新");
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
    public boolean removeByIds(List<String> ids) throws BaseException {
        //TODO 有性能问题，需要优化
        List<D> ts = new ArrayList();
        ids.forEach(id -> {
            if (StringUtils.isBlank(id)) {
                throw new ValidateException("ID不能为空");
            }

            Optional<D> old = iBaseRepository.findById(id);
            if (old.isPresent()) {
                old.get().setFlag(0);
                ts.add(old.get());
            }
        });
        if (!CollectionUtils.isEmpty(ts)) {
            iBaseRepository.saveAll(ts);
        }
        return true;
    }

    public PageDTO getPage(final PageDTO pageDTO) {
        PageRequest pageable = DiamondJpaPageUtil.getInstance().toPageRequest(pageDTO);
        Specification<D> specification = null;
        Map<Object, Object> map = new HashMap<>();
        map.put(DiamondJpaPageUtil.FIELD_FOR_SEARCH, "flag");
        map.put(DiamondJpaPageUtil.VALUE_FOR_SEARCH, "1");

        D main = (D) DiamondJpaPageUtil.getInstance().getBean(pageDTO, createDomain().getClass());
        specification = toSpecWithLogicType(main, "and");

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
        return DiamondJpaPageUtil.getInstance().toSpecWithLogicType(main, logicType);
    }
}
