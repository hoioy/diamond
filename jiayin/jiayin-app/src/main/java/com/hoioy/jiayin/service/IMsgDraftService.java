package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;

public interface IMsgDraftService<D extends CommonDomain>  extends IBaseService<MsgDraftDTO,D> {
    void saveOrUpdateDraft(String userName, MessageDTO update,String msgTableName);
    MsgDraftDTO findByMsgIdAndOpenIdAndTable(String userName, MessageDTO update,String msgTableName);
}
