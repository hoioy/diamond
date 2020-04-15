package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.DiamondBeanUtil;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.dto.DeptUserJoinDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.service.IDeptInfoService;
import com.hoioy.diamond.sys.service.IDeptUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = {"06.机构/部门操作接口"})
@RequestMapping("system/dept")
public class DeptController extends BaseController<IDeptInfoService, DeptInfoDTO> {
    @Autowired
    private IDeptUserService iDeptUserService;

    @Autowired
    private IUserInfoService iUserInfoService;

    /*-------------------------分离代码 start -----------------------*/

    /**
     * @return 返回值ResultJson
     * @throws @param id
     *                根据条件查询全部部门tree信息（不分页，返回tree结构数组）
     * @Author wanghw
     * @CreatDate 2018年10月25日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年10月25日
     */
    @ApiOperation(value = "根据条件查询全部部门tree信息，不分页，返回tree结构数组")
    @GetMapping(value = "/query-all-tree")
    public ResultDTO queryAllTree(@RequestParam String id) {
        List<DeptInfoDTO> list = iBaseService.findAll();
        List<DeptInfoDTO> menuTree = DiamondBeanUtil.getInstance().listToTree(list, null);
        return new ResultDTO(menuTree);
    }

    /**
     * @return 返回值ResultDTO
     * @throws @param PageDTO
     *                根据条件查询全部部门信息（不分页，非tree结构）
     * @Author wanghw
     * @CreatDate 2018年11月6日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年11月6日
     */
    @ApiOperation(value = "根据条件查询全部部门信息，不分页，非tree结构",  notes = "system/dept/query-all")
    @PostMapping(value = "/query-all")
    public ResultDTO queryAll(@RequestBody PageDTO pageDTO) {
        pageDTO.setPageSize(Integer.MAX_VALUE);
        pageDTO.setPage(1);
        pageDTO = iBaseService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }

    /**
     * @return 返回值ResultDTO
     * @throws @param DeptInfoDTO
     *                增加新部门，并完整返回该部门信息
     * @Author wanghw
     * @CreatDate 2018年10月25日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年10月25日
     */
    @ApiOperation(value = "增加新部门，完整返回该部门信息",  notes = "system/dept/add")
    @PostMapping(value = "/add")
    public ResultDTO add(@RequestBody DeptInfoDTO deptInfoDto) {
        String id = iBaseService.save(deptInfoDto);
        return new ResultDTO(id);
    }

    /**
     * @return 返回值ModelAndView
     * @throws @param DeptInfoDTO
     *                编辑部门信息，告知前端结果
     * @Author wanghw
     * @CreatDate 2018年10月25日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年10月25日
     */
    @ResponseBody
    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑部门信息，告知前端结果",  notes = "")
    public ResultDTO edit(@RequestBody DeptInfoDTO deptInfoDto) {
        String id = iBaseService.update(deptInfoDto);
        return new ResultDTO(id);

    }

    /**
     * @return 返回值ResultDTO
     * @throws @param id
     *                删除部门，告知前端结果
     * @Author wanghw
     * @CreatDate 2018年10月25日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年10月25日
     */
    @ApiOperation(value = "删除部门，告知前端结果",  notes = "system/dept/del")
    @GetMapping(value = "/del")
    public ResultDTO del(@RequestParam String id) {

        iBaseService.removeById(id);
        return new ResultDTO();


    }

    /**
     * @return 返回值ModelAndView
     * @throws @param id
     *                查询指定部门ID下的全部用户信息
     * @Author wanghw
     * @CreatDate 2018年10月25日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年10月25日
     */
    @ApiOperation(value = "查询指定部门ID下的全部用户信息",  notes = "system/dept/all-userInfos")
    @GetMapping(value = "/all-users")
    public ResultDTO queryAllUsersByDeptID(@RequestParam String id) {
        List<String> userIds = iDeptUserService.findUserIdsByDeptIds(Arrays.asList(id));
        List<UserInfoDTO> list = iUserInfoService.findByIds(userIds);
        return new ResultDTO(list);
    }

    /**
     * @return 返回值ResultDTO
     * @throws @param id
     *                根据id查询用户组
     * @Author wanghw
     * @CreatDate 2018年10月26日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年10月26日
     */
    @ApiOperation(value = "根据id查询用户组",  notes = "system/dept/query-by-id")
    @GetMapping(value = "/query-by-id")
    public ResultDTO queryById(@RequestParam(value = "id", required = false) @ApiParam(name = "id", value = "机构id", required = false) String id) {
        Optional<DeptInfoDTO> deptDTO = iBaseService.findById(id);
        return new ResultDTO(deptDTO.get());
    }

    /**
     * @return 返回值ResultDTO
     * @throws @param userId
     *                删除部门下的某个用户
     * @Author wanghw
     * @CreatDate 2018年11月7日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年11月7日
     */
    @ApiOperation(value = "删除部门下的某个用户",  notes = "system/dept/del-user")
    @PostMapping(value = "/del-user")
    public ResultDTO delUserDept(@RequestBody @ApiParam(name = "DeptUserPK", value = "机构与用户的中间表对象", required = false) DeptUserJoinDTO deptUserPK) {
        iDeptUserService.remove(deptUserPK);
        return new ResultDTO();
    }

    /*-------------------------分离代码 end -----------------------*/
}
