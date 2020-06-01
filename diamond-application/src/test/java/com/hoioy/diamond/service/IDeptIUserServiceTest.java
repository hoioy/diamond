package com.hoioy.diamond.service;

import com.hoioy.diamond.sys.dto.DeptUserJoinDTO;
import com.hoioy.diamond.sys.service.IDeptUserService;
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
public class IDeptIUserServiceTest {

	@Autowired
	IDeptUserService iDeptUserService;

	@Test
	@Ignore
	public void batchSave() {
		List<DeptUserJoinDTO> list = new ArrayList<DeptUserJoinDTO>();

		DeptUserJoinDTO deptUserDTO = new DeptUserJoinDTO();
		DeptUserJoinDTO deptUserDTO2 = new DeptUserJoinDTO();

		deptUserDTO.setDeptId("0a1a90f484854bc397494ba6929db61a");
		deptUserDTO.setUserId("0fbd0015b8a36999d0fd61439b762167");

		deptUserDTO2.setDeptId("17cadb4be5cf40158b0f8a40b7b81f7e");
		deptUserDTO2.setUserId("26cf758ad1861d69fdbf361097db32fb");
		list.add(deptUserDTO);
		list.add(deptUserDTO2);

		boolean a = iDeptUserService.batchSave(list);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		DeptUserJoinDTO deptUserDTO = new DeptUserJoinDTO();

		deptUserDTO.setDeptId("1c23af1c1f49e5812d4bdcdc410d3343");
		deptUserDTO.setUserId("2a1fd93bb85475354bac67a8e38fb262");

		String a = iDeptUserService.save(deptUserDTO).getId();
		System.out.println(a);

	}



	@Test
	@Ignore
	public void findUserIdsByDeptIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a1a90f484854bc397494ba6929db61a");
		ids.add("17cadb4be5cf40158b0f8a40b7b81f7e");

		List<DeptUserJoinDTO> list = iDeptUserService.findUserIdsByDeptIds(ids);
		
		Assert.assertNotNull(list);

		System.out.println(list.size());

	}

	@Test
	@Ignore
	public void findDeptIdsByUserIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0fbd0015b8a36999d0fd61439b762167");
		ids.add("26cf758ad1861d69fdbf361097db32fb");

		List<DeptUserJoinDTO> list = iDeptUserService.findDeptIdsByUserIds(ids);
		
		Assert.assertNotNull(list);

		System.out.println(list.size());

	}
	
	@Test
	@Ignore
	public void remove() {
		DeptUserJoinDTO deptUserDTO = new DeptUserJoinDTO();

		deptUserDTO.setId("1c23af1c1f49e5812d4bdcdc410d33432a1fd93bb85475354bac67a8e38fb262");
		deptUserDTO.setDeptId("1c23af1c1f49e5812d4bdcdc410d3343");
		deptUserDTO.setUserId("2a1fd93bb85475354bac67a8e38fb262");
		boolean a = iDeptUserService.remove(deptUserDTO);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void batchRemove() {

		List<DeptUserJoinDTO> list = new ArrayList<DeptUserJoinDTO>();
		DeptUserJoinDTO deptUserDTO = new DeptUserJoinDTO();
		deptUserDTO.setId("0a1a90f484854bc397494ba6929db61a0fbd0015b8a36999d0fd61439b762167");
		deptUserDTO.setDeptId("0a1a90f484854bc397494ba6929db61a");
		deptUserDTO.setUserId("0fbd0015b8a36999d0fd61439b762167");
		DeptUserJoinDTO deptUserDTO2 = new DeptUserJoinDTO();
		deptUserDTO2.setId("17cadb4be5cf40158b0f8a40b7b81f7e26cf758ad1861d69fdbf361097db32fb");
		deptUserDTO2.setDeptId("17cadb4be5cf40158b0f8a40b7b81f7e");
		deptUserDTO2.setUserId("26cf758ad1861d69fdbf361097db32fb");
		list.add(deptUserDTO);
		list.add(deptUserDTO2);
		boolean a = iDeptUserService.batchRemove(list);

		System.out.println(a);

	}
}
