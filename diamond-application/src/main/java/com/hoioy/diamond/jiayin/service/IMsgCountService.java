package com.hoioy.diamond.jiayin.service;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.jiayin.domain.MsgCount;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.jiayin.dto.MsgCountDTO;
import com.hoioy.diamond.common.domain.DiamondDomain;
/**
 * <p>
 * 消息次数 服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface IMsgCountService<D extends DiamondDomain>  extends IBaseService<MsgCountDTO,D> {



}
