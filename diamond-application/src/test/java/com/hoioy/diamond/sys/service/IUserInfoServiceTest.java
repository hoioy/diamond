package com.hoioy.diamond.sys.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.domain.Role;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserInfoServiceTest {
	@Autowired
	private IUserInfoService iUserInfoService;

	@Autowired
	private IRoleService roleService;

	@Test
	@Ignore
	public void getPage() {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(1);
		pageDTO.setPageSize(10);
		pageDTO = iUserInfoService.getPage(pageDTO);
		Assert.assertNotNull(pageDTO);
	}

	@Test
	@Ignore
	public void saveUserWithRole() {
		try {

			Set<RoleDTO> roles = new HashSet<RoleDTO>();

			Role role = new Role();
			role.setId("0a8fed60a7b26d2580de319b55711a0d");

			UserInfoDTO userInfoDTO = new UserInfoDTO();
			userInfoDTO.setUserName("ceshiName");
			userInfoDTO.setLoginName("ceshiLoginName");
			userInfoDTO.setPassword("ceshi123456");
//			userInfoDTO.setRoles(roles);
			String userId = null;

			if (userInfoDTO.getId() == null) {
				userId = iUserInfoService.save(userInfoDTO);
			} else {
				userId = iUserInfoService.update(userInfoDTO);
			}
//			Boolean success = roleService.batchSave(userInfoDTO.getRoles());
//			System.out.println(success);
		} catch (Exception e) {
			System.out.println("用户数据异常，保存失败");
		}

	}

	@Test
	@Ignore
	public void savePwd() {
		iUserInfoService.savePwd("e481a4ad19f84475a3032d330151423e", "123456");
	}

	@Test
	@Ignore
	public void findByLoginName() {
		UserInfoDTO userInfoDTO = iUserInfoService.findByLoginName("ceshiLoginName");
		Assert.assertNotNull(userInfoDTO);
		System.out.println(userInfoDTO);
	}
	
	@Test
	@Ignore
	public void batchSave() {
		List<UserInfoDTO> list = new ArrayList<UserInfoDTO>();
		for (int x = 0; x < 10; x++) {
			
			UserInfoDTO userInfoDTO = new UserInfoDTO();
			
			userInfoDTO.setUserName("ceshiName-"+x);
			userInfoDTO.setLoginName("ceshiLoginName-"+x);
			userInfoDTO.setPassword("ceshi123456"+x);

			list.add(userInfoDTO);
		}

		boolean a = iUserInfoService.batchSave(list);
		System.out.println(a);
	}

	@Test
	@Ignore
	public void Save() {

		UserInfoDTO userInfoDTO = new UserInfoDTO();
		
		userInfoDTO.setUserName("ceshiName-ing");
		userInfoDTO.setLoginName("ceshiLoginName-ing");
		userInfoDTO.setPassword("ceshi123456");

		String id = iUserInfoService.save(userInfoDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void update() {

		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setId("092a87748deb4a6bb4b973eb24d7fcaf");
		userInfoDTO.setLoginName("nameUpdate");

		String id = iUserInfoService.update(userInfoDTO);
		System.out.println(id);

	}


	@Test
	@Ignore
	public void findById() {

		Optional<UserInfoDTO> userInfoDTO = iUserInfoService.findById("092a87748deb4a6bb4b973eb24d7fcaf");

		Assert.assertNotNull(userInfoDTO);

	}
	// lazy问题  应该是关联的角色没查出来 导致后面转换的时候出现了问题
	@Test
	@Ignore
	public void findByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("092a87748deb4a6bb4b973eb24d7fcaf");
		ids.add("c117f1fa5efd41d3844f19a727cb0d28");
		ids.add("ec0b4a66bdee4131b5ca12511858a2a0");

		List<UserInfoDTO> dtos = iUserInfoService.findByIds(ids);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}

	@Test
	@Ignore
	public void removeById() {

		boolean a = iUserInfoService.removeById("c117f1fa5efd41d3844f19a727cb0d28");
		System.out.println(a);

	}
	
	// 权限问题 没测试成功
	@Test
	@Ignore
	public void removeByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("c117f1fa5efd41d3844f19a727cb0d28");
		ids.add("ec0b4a66bdee4131b5ca12511858a2a0");

		boolean a = iUserInfoService.removeByIds(ids);

		System.out.println(a);

	}
	
	// @Test
	// @Ignore
	// public void changeUserState() {
	// }
	//
	// @Test
	// @Ignore
	// public void findAllUsers() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void passwordIsCorrect() {
	// }
	//

	//
	// @org.junit.jupiter.api.Test
	// void saveUserAvatar() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void findRoleIdsByUserId() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void findDepts() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void findUserGroups() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void delUserDept() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void saveUserDept() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void delUserGroup() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void saveUserGroup() {
	// }
	//
	// @org.junit.jupiter.api.Test
	// void delUserRole() {
	// }
}