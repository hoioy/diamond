package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.PublishHistoryDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
public interface IPublishHistoryService<D extends CommonDomain>  extends IBaseService<PublishHistoryDTO,D> {


    void saveOrUpdateDraft(String userName, MessageDTO update, String publishType);
}
