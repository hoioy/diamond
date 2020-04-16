package com.hoioy.diamond.jiayin.service.impl;

import com.hoioy.diamond.jiayin.domain.JiayinMsgCount;
import com.hoioy.diamond.jiayin.mapper.JiayinMsgCountMapper;
import com.hoioy.diamond.jiayin.service.IJiayinMsgCountService;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.jiayin.dto.JiayinMsgCountDTO;
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
 * @since 2020-04-16
 */
@Service
public class JiayinMsgCountServiceImpl extends BaseServiceImpl<JiayinMsgCountMapper, JiayinMsgCount,JiayinMsgCountDTO> implements IJiayinMsgCountService<JiayinMsgCount> {

        @Autowired
        private JiayinMsgCountMapper jiayinMsgCountMapper;


        @Override
        public PageDTO getPage(PageDTO pageDTO) throws BaseException {
            Page page = TDFMybatisPageUtil.getPage(pageDTO);
            JiayinMsgCount jiayinMsgCount = TDFMybatisPageUtil.getBean(pageDTO, JiayinMsgCount.class);
            IPage<JiayinMsgCount> jiayinMsgCountIPage = jiayinMsgCountMapper.selectPage(page, jiayinMsgCount);
            PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(jiayinMsgCountIPage);
            return resultPage;
        }

        @Override
        public JiayinMsgCount createDomain() {
        return new  JiayinMsgCount();
        }

        @Override
        public JiayinMsgCountDTO createDTO() {
        return new JiayinMsgCountDTO();
        }
}
