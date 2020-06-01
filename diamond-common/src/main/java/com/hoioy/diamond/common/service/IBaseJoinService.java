package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseJoinDTO;

import java.util.List;

/**
 * 两个表关联表基础service
 */
public interface IBaseJoinService<DTO extends BaseJoinDTO, D extends CommonDomain> extends ICommonService<DTO, D> {
    boolean remove(DTO dto);

    boolean batchRemove(List<DTO> dtoList);

    List<String> findFirstIdsBySecondIds(List<String> secondIds);

    List<String> findSecondIdsByFirstIds(List<String> firstIds);

    List<String> findFirstIdsBySecondId(String secondId);

    List<String> findSecondIdsByFirstId(String firstId);

    List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex);

    List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex);
}
