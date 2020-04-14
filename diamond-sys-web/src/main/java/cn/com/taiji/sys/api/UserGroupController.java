package com.hoioy.diamond.sys.api;import com.hoioy.diamond.common.api.BaseController;import com.hoioy.diamond.common.dto.PageDTO;import com.hoioy.diamond.common.dto.ResultDTO;import com.hoioy.diamond.sys.dto.UserGroupDTO;import com.hoioy.diamond.sys.dto.UserGroupUserJoinDTO;import com.hoioy.diamond.sys.dto.UserInfoDTO;import com.hoioy.diamond.sys.service.*;import io.swagger.annotations.Api;import io.swagger.annotations.ApiOperation;import io.swagger.annotations.ApiParam;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;import java.util.Arrays;import java.util.List;import java.util.Optional;@RestController@Api(tags = {"05.用户组操作接口"})@RequestMapping("/system/group")public class UserGroupController extends BaseController<IUserGroupService, UserGroupDTO> {    @Autowired    private IUserInfoService userService;    @Autowired    private IUserGroupUserService iUserGroupUserService;    /*-------------------------分离代码 start -----------------------*/    /**     * @return 返回值ResultJson     * @throws @param id     *                查询所有用户组     * @Author wanghw     * @CreatDate 2018年10月25日     * @UpdateUser 方法更新者     * @UpdateDate 2018年10月25日     */    @ResponseBody    @ApiOperation(value = "查询所有用户组",  notes = "system/dictionary/query-page")    @PostMapping(value = "/query-page")    public ResultDTO queryPage(@RequestBody PageDTO pageDTO) {        log.info("查询所有用户组信息列表入参：{}", pageDTO);        pageDTO = iBaseService.getPage(pageDTO);        return new ResultDTO(pageDTO);    }    /**     * @return 返回值ResultDTO     * @throws @param models     *                查询所有用户组，非分页     * @Author wanghw     * @CreatDate 2018年10月26日     * @UpdateUser 方法更新者     * @UpdateDate 2018年10月26日     */    @ResponseBody    @ApiOperation(value = "查询所有用户组，非分页",  notes = "system/dictionary/query-all")    @PostMapping(value = "/query-all")    public ResultDTO queryAll(@RequestBody PageDTO pageDTO) {        log.info("查询所有用户组入参：{}", pageDTO);        pageDTO.setPage(1);        pageDTO.setPageSize(Integer.MAX_VALUE);        pageDTO = iBaseService.getPage(pageDTO);        return new ResultDTO(pageDTO);    }    /**     *                新增用户组     */    @ResponseBody    @ApiOperation(value = "新增用户组",  notes = "system/dictionary/add")    @PostMapping(value = "/add")    @Valid    public ResultDTO add(@RequestBody UserGroupDTO userGroupDto) {        String groupId = iBaseService.save(userGroupDto);        Optional<UserGroupDTO> userGroupDTO = iBaseService.findById(groupId);        return new ResultDTO(userGroupDTO.get());    }    /**     *                修改用户组     */    @ResponseBody    @ApiOperation(value = "修改用户组",  notes = "system/dictionary/edit")    @PostMapping(value = "/edit")    @Valid    public ResultDTO edit(@RequestBody UserGroupDTO userGroupDto) {        iBaseService.update(userGroupDto);        return new ResultDTO(userGroupDto);    }    /**     *                删除用户组     */    @ApiOperation(value = "删除用户组",  notes = "system/dictionary/del")    @GetMapping(value = "/del")    public ResultDTO del(@RequestParam(value = "id", required = false) @ApiParam(name = "id", value = "用户组id", required = false) String id) {        Boolean success = iBaseService.removeById(id);        return new ResultDTO(success);    }    /**     *                根据id查询用户组     */    @ApiOperation(value = "根据id查询用户组",  notes = "system/dictionary/query-by-id")    @GetMapping(value = "/query-by-id")    public ResultDTO queryById(@RequestParam(value = "id", required = false) @ApiParam(name = "id", value = "用户组id", required = false) String id) {        Optional<UserGroupDTO> userGroupDtp = iBaseService.findById(id);        return new ResultDTO(userGroupDtp.get());    }    /**     * @return 返回值ResultDTO     * @throws @param id     *                查询用户组下所有人员     * @Author wanghw     * @CreatDate 2018年10月29日     * @UpdateUser 方法更新者     * @UpdateDate 2018年10月29日     */    @ApiOperation(value = "查询用户组下所有人员",  notes = "system/dictionary/all-userInfos")    @GetMapping(value = "/all-users")    public ResultDTO queryAllUsersByGroupID(@RequestParam(value = "id", required = false) @ApiParam(name = "id", value = "用户组id", required = false) String id) {        List<String> userIds = iBaseService.findUserIdsByGroupIds(Arrays.asList(id));        List<UserInfoDTO> list = userService.findByIds(userIds);        return new ResultDTO(list);    }    /**     * @return 返回值ResultDTO     * @throws @param userId     *                删除分组下的用户     * @Author wanghw     * @CreatDate 2018年11月7日     * @UpdateUser 方法更新者     * @UpdateDate 2018年11月7日     */    @ApiOperation(value = "删除分组下的用户",  notes = "system/dictionary/del-user")    @PostMapping(value = "/del-user")    public ResultDTO delUser(@RequestBody UserGroupUserJoinDTO userGroupUserPK) {        iUserGroupUserService.remove(userGroupUserPK);        return new ResultDTO();    }    /*-------------------------分离代码 end -----------------------*/}