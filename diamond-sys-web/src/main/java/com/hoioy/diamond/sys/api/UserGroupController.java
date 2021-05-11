package com.hoioy.diamond.sys.api;import com.hoioy.diamond.common.api.BaseController;import com.hoioy.diamond.sys.dto.UserGroupDTO;import com.hoioy.diamond.sys.service.IUserGroupService;import com.github.xiaoymin.knife4j.annotations.ApiSupport;import io.swagger.annotations.Api;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;@RestController@Api(tags = {"05.用户组操作接口"})@ApiSupport(order = 5)@RequestMapping("/sys/group")public class UserGroupController extends BaseController<IUserGroupService, UserGroupDTO> {}