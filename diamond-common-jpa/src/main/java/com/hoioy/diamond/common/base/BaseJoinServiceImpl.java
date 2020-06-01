package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.AbstractBaseJoinServiceImpl;
import com.hoioy.diamond.common.service.IBaseJoinService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class BaseJoinServiceImpl<I extends IBaseJoinRepository<D>, D extends BaseJoinDomain, DTO extends BaseJoinDTO>
        extends AbstractBaseJoinServiceImpl<I, D, DTO> implements IBaseJoinService<DTO, D> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DTO> findById(String id) throws BaseException {
        Optional<D> t = iBaseRepository.findById(id);
        if (!t.isPresent()) {
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
    public DTO save(DTO dto) {
        D t = dtoToDomain(dto, true);
        t.setId(generateJoinId(t));
        t = iBaseRepository.saveAndFlush(t);
        deleteCache();
        return domainToDTO(t, true);
    }

    @Override
    public boolean batchSave(List<DTO> dtoList) {
        List<D> ts = dtoListToDomainList(dtoList, true);
        ts.forEach(t -> {
            t.setId(generateJoinId(t));
        });
        iBaseRepository.saveAll(ts);
        iBaseRepository.flush();
        deleteCache();
        return true;
    }

    @Override
    public boolean remove(DTO dto) {
        D t = dtoToDomain(dto, true);
        t.setId(generateJoinId(t));
        iBaseRepository.delete(t);
        deleteCache();
        return true;
    }

    @Override
    public boolean batchRemove(List<DTO> dtoList) {
        List<D> ts = dtoListToDomainList(dtoList, true);

        ts.forEach(t -> {
            String id = generateJoinId(t);
            t.setId(generateJoinId(t));
        });
        iBaseRepository.deleteInBatch(ts);
        deleteCache();
        return true;
    }

    @Override
    public List<String> findFirstIdsBySecondIds(List<String> secondIds) {
        String jpql = generateFindIdsJPQL(getIdFieldNameByIndex(BaseJoinId.Index.first),
                getIdFieldNameByIndex(BaseJoinId.Index.second), secondIds);
        return getJPQLResult(jpql);
    }

    @Override
    public List<String> findSecondIdsByFirstIds(List<String> firstIds) {
        String jpql = generateFindIdsJPQL(getIdFieldNameByIndex(BaseJoinId.Index.second),
                getIdFieldNameByIndex(BaseJoinId.Index.first), firstIds);
        return getJPQLResult(jpql);
    }

    @Override
    public List<String> findFirstIdsBySecondId(String secondId) {
        String jpql = generateFindIdsJPQL(getIdFieldNameByIndex(BaseJoinId.Index.first),
                getIdFieldNameByIndex(BaseJoinId.Index.second), Arrays.asList(secondId));
        return getJPQLResult(jpql);
    }

    @Override
    public List<String> findSecondIdsByFirstId(String firstId) {
        String jpql = generateFindIdsJPQL(getIdFieldNameByIndex(BaseJoinId.Index.second),
                getIdFieldNameByIndex(BaseJoinId.Index.first), Arrays.asList(firstId));
        return getJPQLResult(jpql);
    }

    @Override
    public List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex) {
        String jpql = generateFindIdsJPQL("id",
                getIdFieldNameByIndex(joinIndex), joinIds);
        return getJPQLResult(jpql);
    }

    @Override
    public List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex) {
        String jpql = generateFindIdsJPQL("id",
                getIdFieldNameByIndex(joinIndex), Arrays.asList(joinId));
        return getJPQLResult(jpql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        iBaseRepository.deleteById(id);
        deleteCache();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(List<String> ids) throws BaseException {
        //TODO 性能问题
        ids.forEach(id -> {
            iBaseRepository.deleteById(id);
        });
        deleteCache();
        return true;
    }

    private List<String> getJPQLResult(String jpql) {
        if (jpql == null) {
            return null;
        }
        Query query = entityManager.createQuery(jpql);
        List<String> result = query.getResultList();
        return result;
    }

    private String generateFindIdsJPQL(String selectFieldName, String whereFieldName, List<String> whereIds) {
        if (CollectionUtils.isEmpty(whereIds)) {
            return null;
        }
        String domainClassName = domainClass.getSimpleName();
        StringBuilder where = new StringBuilder(" where ");
        int size = whereIds.size();
        for (int i = 0; i < size; i++) {
            String id = whereIds.get(i);
            where.append(" p." + whereFieldName + "='" + id + "' ");
            if (i + 1 < size) {
                where.append(" or ");
            }
        }

        return "select p." + selectFieldName + " from " + domainClassName + " p " + where;
    }

}
