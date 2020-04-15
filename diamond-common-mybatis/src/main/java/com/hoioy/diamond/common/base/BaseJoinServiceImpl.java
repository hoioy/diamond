package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.common.util.DiamondBeanUtil;
import com.hoioy.diamond.common.util.DiamondReflectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class BaseJoinServiceImpl<M extends IBaseJoinMapper<D>, D extends BaseJoinDomain, DTO extends BaseJoinDTO>
        extends ServiceImpl<M, D> implements IBaseJoinService<DTO, D> {
    protected Class<D> domainClass = (Class<D>) DiamondReflectionUtil.getSuperClassGenericType(getClass(), 1);
    protected Class<DTO> dtoClass = (Class<DTO>) DiamondReflectionUtil.getSuperClassGenericType(getClass(), 2);

    @Autowired
    protected M iBaseMapper;

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
        iBaseMapper.insert(t);
        return true;
    }

    @Override
    public boolean batchSave(List<DTO> dtoList) {
        dtoList.forEach(t -> {
            t.setId(DiamondBeanUtil.getInstance().generateJoinId(t));
        });
        List<D> ts = dtoListToDomainList(dtoList, true);
        super.saveBatch(ts);
        return true;
    }

    @Override
    public boolean remove(DTO dto) {
        return super.removeById(DiamondBeanUtil.getInstance().generateJoinId(dto));
    }

    @Override
    public boolean batchRemove(List<DTO> dtoList) {
        List<String> ids = new ArrayList();
        dtoList.forEach(t -> {
            ids.add(DiamondBeanUtil.getInstance().generateJoinId(t));
        });

        return super.removeByIds(ids);
    }
}
