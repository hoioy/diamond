package com.hoioy.diamond.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.dto.RoleDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IRoleServiceTest {

	@Autowired
	IRoleService iRoleService;

	@Test
	@Ignore
	public void getPage() {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(1);
		pageDTO.setPageSize(10);
		pageDTO = iRoleService.getPage(pageDTO);
		Assert.assertNotNull(pageDTO);
	}

	@Test
	@Ignore
	public void batchSave() {

		List<RoleDTO> list = new ArrayList<RoleDTO>();

		for (int x = 0; x < 5; x++) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setRoleName("ceshiRole--" + x);
			list.add(roleDTO);
		}

		boolean a = iRoleService.batchSave(list);

		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		RoleDTO roleDTO = new RoleDTO();

		roleDTO.setRoleName("roleNameSave");

		String id = iRoleService.save(roleDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void update() {

		DeptInfoDTO deptInfoDTO = new DeptInfoDTO();
		deptInfoDTO.setId("0d6f5723f03b4b20bb1bc2a6f03f6b0b");
		deptInfoDTO.setDeptName("nameUpdate");

		String id = iRoleService.update(deptInfoDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void removeById() {

		boolean a = iRoleService.removeById("0d6f5723f03b4b20bb1bc2a6f03f6b0b");
		System.out.println(a);

	}

	@Test
	@Ignore
	public void removeByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0d6f5723f03b4b20bb1bc2a6f03f6b0b");
		ids.add("2aa17554e6994fbcbb432063e137b30f");

		boolean a = iRoleService.removeByIds(ids);

		System.out.println(a);

	}

	@Test
	@Ignore
	public void findById() {

		Optional<DeptInfoDTO> deptInfoDTO = iRoleService.findById("0d6f5723f03b4b20bb1bc2a6f03f6b0b");

		Assert.assertNotNull(deptInfoDTO);

	}

	@Test
	@Ignore
	public void findByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0d6f5723f03b4b20bb1bc2a6f03f6b0b");
		ids.add("2aa17554e6994fbcbb432063e137b30f");

		List<DeptInfoDTO> dtos = iRoleService.findByIds(ids);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}

	@Test
	@Ignore
	public void findAll() {

		List<DeptInfoDTO> list = iRoleService.findAll();

		Assert.assertNotNull(list);

		System.out.println(list.size());

	}

}
