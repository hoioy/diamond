package com.hoioy.diamond.common.service;


import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.TDFBeanUtil;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 基础service，要求其他service继承
 */
public interface IBaseService<DTO extends BaseDTO, D extends TDFDomain> extends ITDFService<DTO, D> {
    PageDTO getPage(final PageDTO pageDTO);

    /**
     * 根据id，parentId将list结构转为tree结构
     *
     * @param list
     * @param rootId 根节点的Id，如果根节点Id为null，则传null即可
     */
    default List<DTO> listToTree(Collection<DTO> list, String rootId) {
        return TDFBeanUtil.getInstance().listToTree(list, rootId);
    }

    // 成功返回ID
//    @CachePut(value = "dto", key = "#dto.id")
//    @CachePut(value="DataItem", keyGenerator="TDFCacheKeyGenerator")
    String save(DTO dto);

    //成功返回ID
//    @CachePut(value = "dto", key = "#dto.id")
    String update(DTO dto);

//    @CacheEvict(value = "dto", key = "#id")
    boolean removeById(String id);

    boolean removeByIds(List<String> ids);

    // 成功返回DTO
//    @Cacheable(value = "dto", key = "#id")
    Optional<DTO> findById(String id);

    List<DTO> findByIds(List<String> ids);

    boolean batchSave(Collection<DTO> entityList);
}
