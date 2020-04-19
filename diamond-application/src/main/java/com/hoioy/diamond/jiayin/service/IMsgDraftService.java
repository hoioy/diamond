package com.hoioy.diamond.jiayin.service;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.jiayin.domain.MsgDraft;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.jiayin.dto.MsgDraftDTO;
import com.hoioy.diamond.common.domain.DiamondDomain;
/**
 * <p>
 * 消息草稿 服务类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface IMsgDraftService<D extends DiamondDomain>  extends IBaseService<MsgDraftDTO,D> {



}
