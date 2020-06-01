package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.domain.UserGroup;
import com.hoioy.diamond.sys.dto.UserGroupDTO;
import com.hoioy.diamond.sys.mapper.UserGroupMapper;
import com.hoioy.diamond.sys.service.IUserGroupService;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {

    @Override
    public PageDTO<UserGroupDTO> getPage(PageDTO<UserGroupDTO> pageDTO) {
        UserGroup userGroup = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserGroup> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getPage(pageDTO), userGroup);
        return CommonMybatisPageUtil.getPageDTO(data);
    }
}
