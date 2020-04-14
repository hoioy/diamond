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
import com.hoioy.diamond.sys.dto.UserGroupDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserGroupServiceTest {

	@Autowired
	IUserGroupService iUserGroupService;

	@Test
	@Ignore
	public void getPage() {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(1);
		pageDTO.setPageSize(10);
		pageDTO = iUserGroupService.getPage(pageDTO);
		Assert.assertNotNull(pageDTO);
	}

	@Test
	@Ignore
	public void batchSave() {
		List<UserGroupDTO> list = new ArrayList<UserGroupDTO>();
		for (int x = 0; x < 10; x++) {
			UserGroupDTO userGroupDTO = new UserGroupDTO();
			userGroupDTO.setGroupName("ceshiUserGroupDTO" + x);
			list.add(userGroupDTO);
		}

		boolean a = iUserGroupService.batchSave(list);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setGroupName("nameSave");

		String id = iUserGroupService.save(userGroupDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void update() {

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setGroupName("nameUpdate");
		userGroupDTO.setId("891dd69144a541648d1fcfc578fde26e");

		String id = iUserGroupService.update(userGroupDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void removeById() {

		boolean a = iUserGroupService.removeById("0a1a90f484854bc397494ba6929db61a");
		System.out.println(a);

	}

	@Test
	@Ignore
	public void removeByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a1a90f484854bc397494ba6929db61a");
		ids.add("17cadb4be5cf40158b0f8a40b7b81f7e");

		boolean a = iUserGroupService.removeByIds(ids);

		System.out.println(a);

	}

	@Test
	@Ignore
	public void findById() {

		Optional<DeptInfoDTO> deptInfoDTO = iUserGroupService.findById("0a1a90f484854bc397494ba6929db61a");

		Assert.assertNotNull(deptInfoDTO);

	}

	@Test
	@Ignore
	public void findByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a1a90f484854bc397494ba6929db61a");
		ids.add("17cadb4be5cf40158b0f8a40b7b81f7e");

		List<DeptInfoDTO> dtos = iUserGroupService.findByIds(ids);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}

	@Test
	@Ignore
	public void findUserIdsByGroupIds() {
		
		List<String> ids = new ArrayList<String>();

		ids.add("0a1a90f484854bc397494ba6929db61a");
		ids.add("17cadb4be5cf40158b0f8a40b7b81f7e");

		List<DeptInfoDTO> list = iUserGroupService.findUserIdsByGroupIds(ids);

		Assert.assertNotNull(list);

		System.out.println(list.size());

	}
}
