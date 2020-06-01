package com.hoioy.diamond.sys.api;import com.hoioy.diamond.common.api.BaseController;import com.hoioy.diamond.common.dto.PageDTO;import com.hoioy.diamond.common.dto.ResultDTO;import com.hoioy.diamond.common.util.CommonSecurityUtils;import com.hoioy.diamond.sys.dto.MenuDTO;import com.hoioy.diamond.sys.service.IMenuService;import io.swagger.annotations.Api;import io.swagger.annotations.ApiOperation;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.PostMapping;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;import java.util.Map;@RestController@Api(tags = {"04.菜单操作接口"})@RequestMapping("/sys/menu")public class MenuController extends BaseController<IMenuService, MenuDTO> {    /**     * 获取所有菜单信息     * TODO zhaozhao 不建议提供全量查询接口     */    @ApiOperation(value = "加载全部菜单，列表平铺形式")    @GetMapping(value = "/query/all")    public ResultDTO menuList() {        PageDTO pageDTO = new PageDTO();        pageDTO.setPage(1);        pageDTO.setPageSize(Integer.MAX_VALUE);        pageDTO = iBaseService.getPage(pageDTO);        return new ResultDTO(pageDTO.getList());    }    /**     * @Description:获取所有菜单信息     * @Param: [models]     * @return: com.hoioy.diamond.common.dto.ResultDTO     * @Author: zhoujial     * @Date: 2018/11/16     */    @ApiOperation(value = "加载全部菜单，树状结构,只针对vue-router使用，后续考虑删除")    @GetMapping(value = "/query/all/tree")    public ResultDTO menuListForRouter() {        final String userName = CommonSecurityUtils.getCurrentLogin();        Map map = iBaseService.findMenuAllForRouter(userName);        return new ResultDTO(map);    }}