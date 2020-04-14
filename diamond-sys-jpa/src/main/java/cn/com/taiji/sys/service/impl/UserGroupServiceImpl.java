package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.domain.UserGroup;
import com.hoioy.diamond.sys.domain.UserGroupRepository;
import com.hoioy.diamond.sys.domain.UserGroupUserRepository;
import com.hoioy.diamond.sys.dto.UserGroupDTO;
import com.hoioy.diamond.sys.service.IUserGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.PageRequest.of;

/**
 * 类名称：用户组Group Service 类描述： 创建人：wanghw 创建时间：2018年2月5日 下午8:30:29
 */
@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupRepository, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {

    @Autowired
    private UserGroupUserRepository userGroupUserRepository;

    @Override
    public List<String> findUserIdsByGroupIds(List<String> groupIds) {
        return userGroupUserRepository.findUserIdsByGroupIds(groupIds);
    }
}
