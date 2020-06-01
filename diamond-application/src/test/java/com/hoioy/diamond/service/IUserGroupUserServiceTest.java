package com.hoioy.diamond.service;

import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.dto.UserGroupUserJoinDTO;
import com.hoioy.diamond.sys.service.IUserGroupUserService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserGroupUserServiceTest {

	@Autowired
	IUserGroupUserService iUserGroupUserService;

	@Test
	@Ignore
	public void batchSave() {
		List<UserGroupUserJoinDTO> list = new ArrayList<UserGroupUserJoinDTO>();

		UserGroupUserJoinDTO userGroupUserJoinDTO = new UserGroupUserJoinDTO();
		UserGroupUserJoinDTO userGroupUserJoinDTO2 = new UserGroupUserJoinDTO();

		userGroupUserJoinDTO.setGroupId("047355597c4240cf8db40245996ddc99");
		userGroupUserJoinDTO.setUserId("0fbd0015b8a36999d0fd61439b762167");

		userGroupUserJoinDTO2.setGroupId("38d073af7c0c49b8acd146ccad71a3a7");
		userGroupUserJoinDTO2.setUserId("26cf758ad1861d69fdbf361097db32fb");
		list.add(userGroupUserJoinDTO);
		list.add(userGroupUserJoinDTO2);

		boolean a = iUserGroupUserService.batchSave(list);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		UserGroupUserJoinDTO userGroupUserJoinDTO = new UserGroupUserJoinDTO();

		userGroupUserJoinDTO.setGroupId("9dc83e4b6c864f6bbbf0f25d431f9e07");
		userGroupUserJoinDTO.setUserId("2a1fd93bb85475354bac67a8e38fb262");

		String a = iUserGroupUserService.save(userGroupUserJoinDTO).getId();
		System.out.println(a);

	}

	
	@Test
	@Ignore
	public void remove() {
		
		UserGroupUserJoinDTO userGroupUserJoinDTO = new UserGroupUserJoinDTO();
		userGroupUserJoinDTO.setId("0fbd0015b8a36999d0fd61439b762167047355597c4240cf8db40245996ddc99");

		userGroupUserJoinDTO.setGroupId("9dc83e4b6c864f6bbbf0f25d431f9e07");
		userGroupUserJoinDTO.setUserId("2a1fd93bb85475354bac67a8e38fb262");
		boolean a = iUserGroupUserService.remove(userGroupUserJoinDTO);
		System.out.println(a);

	}

	
	@Test
	@Ignore
	public void findGroupIdsByUserIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0fbd0015b8a36999d0fd61439b762167");
		ids.add("26cf758ad1861d69fdbf361097db32fb");

		List<UserGroupUserJoinDTO> list = iUserGroupUserService.findGroupIdsByUserIds(ids);
		
		Assert.assertNotNull(list);

		System.out.println(list.size());

	}

	@Test
	@Ignore
	public void findUserIdsByGroupIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a1a90f484854bc397494ba6929db61a");
		ids.add("17cadb4be5cf40158b0f8a40b7b81f7e");

		List<DeptInfoDTO> list = iUserGroupUserService.findUserIdsByGroupIds(ids);

		Assert.assertNotNull(list);

		System.out.println(list.size());

	}

	@Test
	@Ignore
	public void batchRemove() {

		List<UserGroupUserJoinDTO> list = new ArrayList<UserGroupUserJoinDTO>();
		UserGroupUserJoinDTO userGroupUserJoinDTO = new UserGroupUserJoinDTO();
		userGroupUserJoinDTO.setId("0fbd0015b8a36999d0fd61439b762167047355597c4240cf8db40245996ddc99");

		userGroupUserJoinDTO.setGroupId("047355597c4240cf8db40245996ddc99");
		userGroupUserJoinDTO.setUserId("0fbd0015b8a36999d0fd61439b762167");
		
		UserGroupUserJoinDTO userGroupUserJoinDTO2 = new UserGroupUserJoinDTO();
		userGroupUserJoinDTO.setId("26cf758ad1861d69fdbf361097db32fb38d073af7c0c49b8acd146ccad71a3a7");

		userGroupUserJoinDTO2.setGroupId("38d073af7c0c49b8acd146ccad71a3a7");
		userGroupUserJoinDTO2.setUserId("26cf758ad1861d69fdbf361097db32fb");
		list.add(userGroupUserJoinDTO2);
		list.add(userGroupUserJoinDTO);
		boolean a = iUserGroupUserService.batchRemove(list);

		System.out.println(a);

	}
}
