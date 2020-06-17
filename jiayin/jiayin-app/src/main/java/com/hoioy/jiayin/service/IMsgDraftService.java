package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;

/**
 * <p>
 * 消息草稿 服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface IMsgDraftService<D extends CommonDomain>  extends IBaseService<MsgDraftDTO,D> {


    void saveOrUpdateDraft(String userName, MessageDTO update,String messageType);
}
