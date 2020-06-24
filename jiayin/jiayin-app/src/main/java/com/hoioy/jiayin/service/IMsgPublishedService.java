package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgPublishedDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
public interface IMsgPublishedService<D extends CommonDomain>  extends IBaseService<MsgPublishedDTO,D> {


    void saveOrUpdateDraft(String userName, MessageDTO update, String msgTableName);
}
