package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgPublishedDTO;

public interface IMsgPublishedService<D extends CommonDomain>  extends IBaseService<MsgPublishedDTO,D> {
    void saveOrUpdateDraft(String userName, MessageDTO update, String msgTableName);
}
