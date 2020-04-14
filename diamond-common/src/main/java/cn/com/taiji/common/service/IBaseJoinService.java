package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.dto.BaseJoinDTO;

import java.util.List;

/**
 * 两个表关联表基础service
 */
public interface IBaseJoinService<DTO extends BaseJoinDTO, D extends TDFDomain> extends ITDFService<DTO, D> {

    boolean save(DTO dto);

    boolean batchSave(List<DTO> dtoList);

    boolean remove(DTO dto);

    boolean batchRemove(List<DTO> dtoList);
}
