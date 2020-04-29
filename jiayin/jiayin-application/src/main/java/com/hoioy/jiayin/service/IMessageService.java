package com.hoioy.jiayin.service;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.jiayin.dto.MessageDTO;
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


    String publishMsg(MessageDTO dto);
}