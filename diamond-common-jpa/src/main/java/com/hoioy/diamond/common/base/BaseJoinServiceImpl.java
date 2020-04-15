package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.common.util.DiamondBeanUtil;
import com.hoioy.diamond.common.util.DiamondReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseJoinServiceImpl<I extends IBaseJoinRepository<D, String>, D extends BaseJoinDomain, DTO extends BaseJoinDTO> implements IBaseJoinService<DTO, D> {
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

    @Override
    public boolean save(DTO dto) {
        dto.setId(DiamondBeanUtil.getInstance().generateJoinId(dto));
        D t = dtoToDomain(dto, true);
        t = iBaseRepository.saveAndFlush(t);
        return true;
    }

    @Override
    public boolean batchSave(List<DTO> dtoList) {
        dtoList.forEach(t -> {
            t.setId(DiamondBeanUtil.getInstance().generateJoinId(t));
        });
        List<D> ts = dtoListToDomainList(dtoList, true);
        iBaseRepository.saveAll(ts);
        iBaseRepository.flush();
        return true;
    }

    @Override
    public boolean remove(DTO dto) {
        dto.setId(DiamondBeanUtil.getInstance().generateJoinId(dto));
        D t = dtoToDomain(dto, true);
        iBaseRepository.delete(t);
        return true;
    }

    @Override
    public boolean batchRemove(List<DTO> dtoList) {
        dtoList.forEach(t -> {
            t.setId(DiamondBeanUtil.getInstance().generateJoinId(t));
        });
        List<D> ts = dtoListToDomainList(dtoList, true);
        iBaseRepository.deleteInBatch(ts);
        return true;
    }
}
