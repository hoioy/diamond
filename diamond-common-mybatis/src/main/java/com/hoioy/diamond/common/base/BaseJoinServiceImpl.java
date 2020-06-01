package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.service.AbstractBaseJoinServiceImpl;
import com.hoioy.diamond.common.service.IBaseJoinService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseJoinServiceImpl<M extends IBaseJoinMapper<D>, D extends BaseJoinDomain, DTO extends BaseJoinDTO>
        extends AbstractBaseJoinServiceImpl<M, D, DTO> implements IBaseJoinService<DTO, D> {

    @Override
    public Optional<DTO> findById(String id) throws BaseException {
        D domain = iBaseRepository.selectById(id);
        if (domain == null) {
            return Optional.ofNullable(null);
        }
        DTO dto = domainToDTO(domain, true);
        return Optional.ofNullable(dto);
    }

    @Override
    public List<DTO> findByIds(List<String> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            List<D> domains = iBaseRepository.selectBatchIds(ids);
            return domainListToDTOList(domains);
        } else {
            return new ArrayList<DTO>();
        }
    }

    @Override
    public DTO save(DTO dto) {
        D t = dtoToDomain(dto, true);
        t.setId(generateJoinId(t));
        if (iBaseRepository.insert(t) > 0) {
            deleteCache();
            return domainToDTO(t, true);
        }
        throw new CommonException("保存失败");
    }

    @Override
    public boolean batchSave(List<DTO> dtoList) {
        List<D> ts = dtoListToDomainList(dtoList, true);
        ts.forEach(t -> {
            t.setId(generateJoinId(t));
            iBaseRepository.insert(t);
        });
        deleteCache();
        return true;
    }

    @Override
    public boolean remove(DTO dto) {
        D t = dtoToDomain(dto, true);
        String id = generateJoinId(t);
        t.setId(id);
        deleteCache();
        iBaseRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean batchRemove(List<DTO> dtoList) {
        List<D> ts = dtoListToDomainList(dtoList, true);

        List<String> ids = new ArrayList();
        ts.forEach(t -> {
            String id = generateJoinId(t);
            ids.add(id);
        });
        deleteCache();
        iBaseRepository.deleteBatchIds(ids);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        deleteCache();
        iBaseRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(List<String> ids) throws BaseException {
        deleteCache();
        iBaseRepository.deleteBatchIds(ids);
        return true;
    }

    @Override
    public List<String> findFirstIdsBySecondIds(List<String> secondIds) {
        List<String> ids = getResult(getIdFieldNameByIndex(BaseJoinId.Index.first),
                getIdFieldNameByIndex(BaseJoinId.Index.second), secondIds);
        return ids;
    }

    @Override
    public List<String> findSecondIdsByFirstIds(List<String> firstIds) {
        List<String> ids = getResult(getIdFieldNameByIndex(BaseJoinId.Index.second),
                getIdFieldNameByIndex(BaseJoinId.Index.first), firstIds);
        return ids;
    }

    @Override
    public List<String> findFirstIdsBySecondId(String secondId) {
        List<String> ids = getResult(getIdFieldNameByIndex(BaseJoinId.Index.first),
                getIdFieldNameByIndex(BaseJoinId.Index.second), Arrays.asList(secondId));
        return ids;
    }

    @Override
    public List<String> findSecondIdsByFirstId(String firstId) {
        List<String> ids = getResult(getIdFieldNameByIndex(BaseJoinId.Index.second),
                getIdFieldNameByIndex(BaseJoinId.Index.first), Arrays.asList(firstId));
        return ids;
    }

    @Override
    public List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex) {
        return getResult("id", getIdFieldNameByIndex(joinIndex), joinIds);
    }

    @Override
    public List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex) {
        return getResult("id", getIdFieldNameByIndex(joinIndex), Arrays.asList(joinId));
    }

    private List<String> getResult(String selectIdFieldName, String whereIdFieldName, List<String> selectIdValues) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(StrUtil.toUnderlineCase(selectIdFieldName))
                .in(StrUtil.toUnderlineCase(whereIdFieldName), selectIdValues);
        List<D> domainList = iBaseRepository.selectList(queryWrapper);
        List<String> ids = new ArrayList<>();
        domainList.forEach(domain -> {
            ids.add(callIdFieldGetMethod(domain, selectIdFieldName));
        });
        return ids;
    }
}
