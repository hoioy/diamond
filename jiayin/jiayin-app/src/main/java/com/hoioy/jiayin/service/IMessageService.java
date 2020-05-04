package com.hoioy.jiayin.service;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MessageDTO;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface IMessageService<D extends DiamondDomain>  extends IBaseService<MessageDTO,D> {


}
