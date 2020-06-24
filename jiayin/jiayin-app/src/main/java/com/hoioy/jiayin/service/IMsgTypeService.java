package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.jiayin.dto.MsgTypeDTO;

public interface IMsgTypeService<D extends CommonDomain> extends IBaseTreeService<MsgTypeDTO, D> {

    MsgTypeDTO findParentByChildId(String childId);
}
