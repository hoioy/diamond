package com.hoioy.diamond.jiayin.service;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.jiayin.domain.Message;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.jiayin.dto.MessageDTO;
import com.hoioy.diamond.common.domain.DiamondDomain;
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
