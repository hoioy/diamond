package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MsgTypeDTO;

import java.util.List;

/**
 * <p>
 * 消息类型 服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface IMsgTypeService<D extends CommonDomain>  extends IBaseService<MsgTypeDTO,D> {


    List<MsgTypeDTO>  selectAllParent();

    List<MsgTypeDTO> selectChildrenByParentId(String parentId);
}
