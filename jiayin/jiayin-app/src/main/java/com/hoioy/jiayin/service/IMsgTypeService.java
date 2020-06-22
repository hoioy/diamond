package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.jiayin.dto.MsgTypeDTO;

/**
 * <p>
 * 消息类型 服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface IMsgTypeService<D extends CommonDomain> extends IBaseTreeService<MsgTypeDTO, D> {

    MsgTypeDTO findParentByChildId(String childId);
}
