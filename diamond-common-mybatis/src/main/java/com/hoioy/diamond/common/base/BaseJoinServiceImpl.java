package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.common.util.JoinIDUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Service
public abstract class BaseJoinServiceImpl<I extends IBaseJoinMapper<D>, D extends BaseJoinDomain, DTO extends BaseJoinDTO>
        extends BaseCommonServiceImpl<I, D, DTO> implements IBaseJoinService<DTO, D> {
    /**
     * domain中表示Id的属性,按照BaseJoinId顺序排列, 为了提升查询性能，不用每次都通过反射来获取
     */
    List<String> domainIdFieldNames = Arrays.asList("", "", "", "", "");
    /**
     * domain中表示Id的属性相应的getXXId方法，为了提升查询性能，不用每次都通过反射来获取
     */
    Map<String, Method> domainIdMethodGetMap = new HashMap<>();

    @Override
    public List<String> getDomainIdFieldNames() {
        return domainIdFieldNames;
    }

    @Override
    public Map<String, Method> getDomainIdMethodGetMap() {
        return domainIdMethodGetMap;
    }

    @Override
    public Boolean removeById(String id) throws BaseException {
        super.removeById(id);

        deleteCache();
        iBaseRepository.deleteById(id);
        afterRemove(id);
        return true;
    }

    @Override
    public Boolean removeByIds(List<String> ids) throws BaseException {
        super.removeByIds(ids);

        deleteCache();
        iBaseRepository.deleteBatchIds(ids);
        afterBatchRemove(ids);
        return true;
    }

    @Override
    public Boolean remove(DTO dto) {
        D t = dtoToDomain(dto, true);
        dto.setId(JoinIDUtil.generateJoinId(t, getDomainIdMethodGetMap()));
        return super.remove(dto);
    }

    @Override
    public Boolean batchRemove(List<DTO> dtoList) {
        dtoList.forEach(dto -> {
            dto.setId(JoinIDUtil.generateJoinId(dtoToDomain(dto), getDomainIdMethodGetMap()));
        });
        return super.batchRemove(dtoList);
    }

    @Override
    public List<String> findFirstIdsBySecondIds(List<String> secondIds) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()), secondIds);
        return ids;
    }

    @Override
    public List<String> findSecondIdsByFirstIds(List<String> firstIds) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()), firstIds);
        return ids;
    }

    @Override
    public List<String> findFirstIdsBySecondId(String secondId) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()), Arrays.asList(secondId));
        return ids;
    }

    @Override
    public List<String> findSecondIdsByFirstId(String firstId) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()), Arrays.asList(firstId));
        return ids;
    }

    @Override
    public List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex) {
        return getResult("id",
                JoinIDUtil.getIdFieldNameByIndex(joinIndex, getDomainIdFieldNames(), getDomainClass()), joinIds);
    }

    @Override
    public List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex) {
        return getResult("id",
                JoinIDUtil.getIdFieldNameByIndex(joinIndex, getDomainIdFieldNames(), getDomainClass()), Arrays.asList(joinId));
    }

    private List<String> getResult(String selectIdFieldName, String whereIdFieldName, List<String> selectIdValues) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(StrUtil.toUnderlineCase(selectIdFieldName))
                .in(StrUtil.toUnderlineCase(whereIdFieldName), selectIdValues);
        List<D> domainList = iBaseRepository.selectList(queryWrapper);
        List<String> ids = new ArrayList<>();
        domainList.forEach(domain -> ids.add(JoinIDUtil.callIdFieldGetMethod(domain, getDomainIdMethodGetMap(), selectIdFieldName)));
        return ids;
    }
}
