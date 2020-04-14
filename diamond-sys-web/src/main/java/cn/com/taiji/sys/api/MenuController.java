package com.hoioy.diamond.sys.api;import com.hoioy.diamond.common.api.BaseController;import com.hoioy.diamond.common.dto.ResultDTO;import com.hoioy.diamond.common.exception.BaseException;import com.hoioy.diamond.common.util.TDFSecurityUtils;import com.hoioy.diamond.sys.dto.MenuDTO;import com.hoioy.diamond.sys.dto.RoleMenuJoinDTO;import com.hoioy.diamond.sys.exception.SysException;import com.hoioy.diamond.sys.service.IMenuService;import com.hoioy.diamond.sys.service.IRoleMenuService;import com.hoioy.diamond.sys.service.IRoleService;import com.fasterxml.jackson.core.type.TypeReference;import com.fasterxml.jackson.databind.ObjectMapper;import io.swagger.annotations.Api;import io.swagger.annotations.ApiOperation;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.validation.BindingResult;import org.springframework.validation.ObjectError;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;import java.io.IOException;import java.util.Arrays;import java.util.List;import java.util.Map;import java.util.Optional;@RestController@Api(tags = {"04.菜单操作接口"})@RequestMapping("/system/menu")public class MenuController extends BaseController<IMenuService, MenuDTO> {    @Autowired    private IRoleMenuService iRoleMenuService;    @Autowired    private IRoleService roleService;    @Autowired    private ObjectMapper objectMapper;    /**     * @Description:获取所有菜单信息     */    @ApiOperation(value = "加载菜单树列表集合信息")    @PostMapping(value = "/query-all")    public ResultDTO menuList() {        List<MenuDTO> map = iBaseService.findAll();        return new ResultDTO(map);    }    /**     * @Description:获取所有菜单信息     * @Date: 2018/11/16     */    @ApiOperation(value = "加载菜单树列表集合信息")    @PostMapping(value = "/query-all-NoTreeList")    public ResultDTO menuListNoTree() {        List<MenuDTO> map = iBaseService.findAll();        return new ResultDTO(map);    }    /**     * @Description:菜单页面获取所有角色信息     */    @ApiOperation(value = "获取菜单中角色数据")    @GetMapping(value = "/all-roles")    public ResultDTO roleMenus(@RequestParam("menuId") String menuId) {        List<String> roleIds = this.iRoleMenuService.findRoleIdsByMenuIds(Arrays.asList(menuId));        List byIds = roleService.findByIds(roleIds);        return new ResultDTO(byIds);    }    /**     * @Description:菜单页面删除角色数据     */    @ApiOperation(value = "删除菜单中角色数据")    @PostMapping(value = "/del-role")    public ResultDTO delMenuRole(@RequestBody @Valid RoleMenuJoinDTO roleMenuPK, BindingResult result) {        if (result.hasErrors()) {            List<ObjectError> list = result.getAllErrors();            for (ObjectError error : list) {                log.error("delMenuRole_error:{},{},{}", error.getCode(), error.getArguments(), error.getDefaultMessage());            }            return new ResultDTO(roleMenuPK);        }        iRoleMenuService.remove(roleMenuPK);        return new ResultDTO(null);    }    /**     * @Description:通过id查询菜单数据     */    @ApiOperation(value = "通过id查询menu信息")    @GetMapping(value = "/query-by-id")    public ResultDTO findMenuByIds(@RequestParam String id) throws BaseException {        Optional byId = iBaseService.findById(id);        return new ResultDTO(byId.get());    }    /**     * @Description:前台同步菜单数据到后台     */    @ApiOperation(value = "添加菜单")    @PostMapping(value = "/add")    public ResultDTO menuAdd(@RequestBody String models) throws BaseException {        MenuDTO menuList = new MenuDTO();        if (models != null && models.length() > 0) {            try {                menuList = objectMapper.readValue(models, new TypeReference<MenuDTO>() {                });            } catch (IOException e) {                e.printStackTrace();                throw new SysException();            }        }        String id = iBaseService.save(menuList);        Optional<MenuDTO> dto = iBaseService.findById(id);        return new ResultDTO(dto.get());    }    /**     * @Description:前台同步菜单数据到后台     * @Param: [model, models]     * @return: com.hoioy.diamond.common.dto.ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "修改菜单")    @PostMapping(value = "/edit")    public ResultDTO menuEdit(@RequestBody String models) throws IOException {        MenuDTO menuList = new MenuDTO();        if (models != null && models.length() > 0) {            menuList = objectMapper.readValue(models, new TypeReference<MenuDTO>() {            });        }        String id = iBaseService.update(menuList);        Optional<MenuDTO> dto = iBaseService.findById(id);        return new ResultDTO(dto.get());    }    /**     * @return com.hoioy.diamond.common.dto.ResultDTO     * @Author zhoujial     * 执行菜单删除     * @Date 上午10:20 18-12-28     * @Param [id]     **/    @ApiOperation(value = "执行菜单删除")    @GetMapping(value = "/del")    public ResultDTO menuDelete(@RequestParam String id) {        boolean b = iBaseService.removeById(id);        return new ResultDTO();    }    /**     * @Description:获取所有菜单信息     * @Param: [models]     * @return: com.hoioy.diamond.common.dto.ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "加载菜单树列表集合信息")    @PostMapping(value = "/query-allTree-ForRouter")    public ResultDTO menuListForRouter() {        final String userName = TDFSecurityUtils.getCurrentLogin();        Map map = iBaseService.findMenuAllForRouter(userName);        return new ResultDTO(map);    }}