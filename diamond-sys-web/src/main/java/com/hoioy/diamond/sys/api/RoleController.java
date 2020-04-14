package com.hoioy.diamond.sys.api;import com.hoioy.diamond.common.api.BaseController;import com.hoioy.diamond.common.dto.PageDTO;import com.hoioy.diamond.common.dto.ResultDTO;import com.hoioy.diamond.sys.dto.MenuDTO;import com.hoioy.diamond.sys.dto.RoleDTO;import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;import com.hoioy.diamond.sys.dto.UserInfoDTO;import com.hoioy.diamond.sys.exception.SysException;import com.hoioy.diamond.sys.service.*;import com.fasterxml.jackson.core.type.TypeReference;import com.fasterxml.jackson.databind.ObjectMapper;import io.swagger.annotations.Api;import io.swagger.annotations.ApiOperation;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.validation.BindingResult;import org.springframework.validation.ObjectError;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;import java.io.IOException;import java.util.*;@RestController@Api(tags = {"03.角色操作接口"})@RequestMapping("/system/role/")public class RoleController extends BaseController<IRoleService, RoleDTO> {    @Autowired    private IRoleMenuService iRoleMenuService;    @Autowired    private IRoleUserService iRoleUserService;    @Autowired    private IUserInfoService userInfoService;    @Autowired    private IMenuService menuService;    @Autowired    private ObjectMapper objectMapper;    static final String ROLE_ID = "roleId";    static final String MENUIDS_FOR_SEARCH = "menuIds";    /*-------------------------分离代码 start -----------------------*/////    /**//     * @Description:添加角色数据//     * @Param: [models]//     * @return: ResultDTO//     * @Author: zhoujial//     * @Date: 2018/11/16//     *///    @ApiOperation(value = "添加用户的角色",  notes = "system/role/add-role")//    @RequestMapping("/add-role")//    public ResultDTO roleUserSaveOne(@RequestBody String models) throws IOException {//        Map pageDTO = new HashMap();////        pageDTO = objectMapper.readValue(models, new TypeReference<Map>() {//        });//        String[] userids = new String[]{(String) pageDTO.get("userId")};//        iRoleUserService.saveRoleUsers(userids, "", (String) pageDTO.get(ROLE_ID));////        return new ResultDTO();//    }    /**     * @Description:分页查询角色数据     * @Param: [model, models]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "角色分页查询",  notes = "system/role/query-page")    @PostMapping(value = "/query-page")    public ResultDTO roleListByPage(@RequestBody PageDTO pageDTO) {        PageDTO map = iBaseService.getPage(pageDTO);        return new ResultDTO(map);    }    /**     * @Description:查询全部角色数据     * @Param: []     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "角色查询",  notes = "system/role/query-all")    @PostMapping(value = "/query-all")    public ResultDTO roleList() {        List<RoleDTO> roles = iBaseService.findAll();        return new ResultDTO(roles);    }    /**     * @Description:通过id查询角色数据     * @Param: [model, id]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "通过id角色查询",  notes = "system/role/query-by-id")    @GetMapping(value = "/query-by-id")    public ResultDTO userEdit(@RequestParam(required = false, value = "id") String id) {        Optional<RoleDTO> dto = iBaseService.findById(id);        return new ResultDTO(dto.get());    }    /**     * @Description:添加角色数据     * @Param: [roleDto, result]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "添加角色信息",  notes = "system/role/add")    @PostMapping(value = "/add")    public ResultDTO roleAdd(@RequestBody @Valid RoleDTO roleDto, BindingResult result) {        if (result.hasErrors()) {            List<ObjectError> list = result.getAllErrors();            StringBuilder errorResult = new StringBuilder();            for (ObjectError error : list) {                errorResult.append(error.getDefaultMessage());                log.error("roleAdd_error:{},{},{}", error.getCode(), error.getArguments(), error.getDefaultMessage());            }            throw new SysException(errorResult.toString());        }        String id = iBaseService.save(roleDto);        Optional<RoleDTO> role = iBaseService.findById(id);        return new ResultDTO(role.get());    }    /**     * @Description:添加角色数据     * @Param: [roleDto, result]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "编辑角色信息",  notes = "system/role/edit")    @PostMapping(value = "/edit")    public ResultDTO roleEdit(@RequestBody @Valid RoleDTO roleDto, BindingResult result) {        if (result.hasErrors()) {            List<ObjectError> list = result.getAllErrors();            StringBuilder errorResult = new StringBuilder();            for (ObjectError error : list) {                errorResult.append(error.getDefaultMessage());                log.error("roleEdit_error:{},{},{}", error.getCode(), error.getArguments(), error.getDefaultMessage());            }            throw new SysException(errorResult.toString());        }        roleDto.setFlag(1);        String roleId = iBaseService.save(roleDto);        Optional<RoleDTO> roleDTO = iBaseService.findById(roleId);        return new ResultDTO(roleDTO.get());    }    /**     * @Description:删除角色数据     * @Param: [model, id]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "删除角色信息",  notes = "system/role/del")    @GetMapping(value = "/del")    public ResultDTO roleDelete(@RequestParam(required = false, value = "id") String id) {        String[] ids = new String[]{id};        this.iBaseService.removeByIds(Arrays.asList(ids));        return new ResultDTO();    }    /**     * @Description:角色页面查询所有用户数据     * @Param: [model, id]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "角色信息下的用户信息",  notes = "system/role/all-userInfos")    @GetMapping(value = "/all-users")    public ResultDTO deptUserAllList(@RequestParam(value = "id", required = false) String id) {        List<String> userIds = iRoleUserService.findUserIdsByRoleIds(Arrays.asList(id));        List<UserInfoDTO> userInfoDTOS = this.userInfoService.findByIds(userIds);        return new ResultDTO(userInfoDTOS);    }    /**     * @Description:角色页面查询所有菜单数据     * @Param: [model, roleId]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "角色信息下的菜单信息",  notes = "system/role/all-menus")    @GetMapping(value = "/all-menus")    public ResultDTO roleMenus(@RequestParam("roleId") String roleId) {        List<String> menuIds = iRoleMenuService.findMenuIdsByRoleIds(Arrays.asList(roleId));        menuService.findByIds(menuIds);        Iterator<String> iterator = menuIds.iterator();        while (iterator.hasNext()) {            String menuid = iterator.next();            List<MenuDTO> menuFromParent = menuService.findMenusByParentId(menuid);            int k = 0;            if (menuFromParent != null) {                for (MenuDTO menuDto : menuFromParent) {                    for (String id : menuIds) {                        if (menuDto.getId().equals(id)) {                            k++;                        }                    }                }                if (k != menuFromParent.size()) {                    iterator.remove();                }            }        }        return new ResultDTO(menuIds);    }    /**     * @Description:角色页面删除用户数据     * @Param: [roleUserPK, result]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "角色信息下删除用户信息",  notes = "system/role/del-user")    @PostMapping(value = "/del-user")    public ResultDTO delUserRole(@RequestBody @Valid RoleUserJoinDTO roleUserPK, BindingResult result) {        if (result.hasErrors()) {            List<ObjectError> list = result.getAllErrors();            StringBuilder errorResult = new StringBuilder();            for (ObjectError error : list) {                errorResult.append(error.getDefaultMessage());                log.error("delUserRole_error:{},{},{}", error.getCode(), error.getArguments(), error.getDefaultMessage());            }            throw new SysException(errorResult.toString());        }        iRoleUserService.remove(roleUserPK);        return new ResultDTO();    }    /**     * @Description:角色页面重置菜单信息     * @Param: [models]     * @return: ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "角色信息下重置菜单",  notes = "system/role/reset-menus")    @PostMapping(value = "/reset-menus")    public ResultDTO roleMenuReset(@RequestBody String models) throws IOException {        ResultDTO resultMap = new ResultDTO();        Map roleMenu = new HashMap();        roleMenu = objectMapper.readValue(models, new TypeReference<Map>() {        });        String roleId = (String) roleMenu.get(ROLE_ID);        List<String> preMenuIds = this.iRoleMenuService.findMenuIdsByRoleIds(Arrays.asList(roleId));        String[] menuIds = new String[((ArrayList) roleMenu.get(MENUIDS_FOR_SEARCH)).size()];        for (int i = 0; i < (((ArrayList) roleMenu.get(MENUIDS_FOR_SEARCH)).size()); i++) {            menuIds[i] = (String) ((ArrayList) roleMenu.get(MENUIDS_FOR_SEARCH)).get(i);        }        this.iRoleMenuService.saveRoleMenusForRest(menuIds, preMenuIds, (String) roleMenu.get(ROLE_ID));        resultMap = new ResultDTO();        return resultMap;    }    /*-------------------------分离代码 end -----------------------*/}