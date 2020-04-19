package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.MsgCount;
import com.hoioy.diamond.jiayin.mapper.MsgCountMapper;
import com.hoioy.diamond.jiayin.service.IMsgCountService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.MsgCountDTO;
import org.springframework.stereotype.Service;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.BaseException;
/**
 * <p>
 * 消息次数 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Service
public class MsgCountServiceImpl extends BaseServiceImpl<MsgCountMapper, MsgCount,MsgCountDTO> implements IMsgCountService<MsgCount> {

        @Autowired
        private MsgCountMapper msgCountMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            MsgCount msgCount = TDFMybatisPageUtil.getBean(pageDTO, MsgCount.class);
            IPage<MsgCount> msgCountIPage = msgCountMapper.selectPage(page, msgCount);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(msgCountIPage);
            return resultPage;
        }

        @Override
        public MsgCount createDomain() {
        return new  MsgCount();
        }

        @Override
        public MsgCountDTO createDTO() {
        return new MsgCountDTO();
        }
}
