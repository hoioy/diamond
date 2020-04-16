package com.hoioy.diamond.jiayin.service;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.jiayin.domain.JiayinMessage;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.jiayin.dto.JiayinMessageDTO;
import com.hoioy.diamond.common.domain.DiamondDomain;
/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
public interface IJiayinMessageService<D extends DiamondDomain>  extends IBaseService<JiayinMessageDTO,D> {



}
