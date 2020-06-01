package com.hoioy.diamond.service;

import com.hoioy.diamond.sys.dto.DeptUserJoinDTO;
import com.hoioy.diamond.sys.dto.RoleMenuJoinDTO;
import com.hoioy.diamond.sys.service.IRoleMenuService;
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
public class IRoleMenuServiceTest {

	@Autowired
	IRoleMenuService iRoleMenuService;

	@Test
	@Ignore
	public void batchSave() {
		List<RoleMenuJoinDTO> list = new ArrayList<RoleMenuJoinDTO>();

		RoleMenuJoinDTO roleMenuJoinDTO = new RoleMenuJoinDTO();
		RoleMenuJoinDTO roleMenuJoinDTO2 = new RoleMenuJoinDTO();

		roleMenuJoinDTO.setRoleId("0a8fed60a7b26d2580de319b55711a0d");
		roleMenuJoinDTO.setMenuId("150ac6197cc94bb6bab4543b56c74322");

		roleMenuJoinDTO2.setRoleId("1f7ce0c8b80b472d95b9d346cf79e084");
		roleMenuJoinDTO2.setMenuId("1813c625de4e4295a46b4c29f1ed9510");
		
		list.add(roleMenuJoinDTO);
		list.add(roleMenuJoinDTO2);

		boolean a = iRoleMenuService.batchSave(list);
		
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		RoleMenuJoinDTO roleMenuJoinDTO = new RoleMenuJoinDTO();

		roleMenuJoinDTO.setRoleId("0a8fed60a7b26d2580de319b55711a0d");
		roleMenuJoinDTO.setMenuId("183c6c91e0094459aefed0032e2f5c67");

		String a = iRoleMenuService.save(roleMenuJoinDTO).getId();
		System.out.println(a);

	}



	@Test
	@Ignore
	public void findMenuIdsByRoleIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a8fed60a7b26d2580de319b55711a0d");
		ids.add("1f7ce0c8b80b472d95b9d346cf79e084");

		List<DeptUserJoinDTO> list = iRoleMenuService.findMenuIdsByRoleIds(ids);
		
		Assert.assertNotNull(list);

		System.out.println(list.size());

	}

	@Test
	@Ignore
	public void findRoleIdsByMenuIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("150ac6197cc94bb6bab4543b56c74322");
		ids.add("1813c625de4e4295a46b4c29f1ed9510");

		List<DeptUserJoinDTO> list = iRoleMenuService.findRoleIdsByMenuIds(ids);
		
		Assert.assertNotNull(list);

		System.out.println(list.size());

	}
	
	@Test
	@Ignore
	public void remove() {
		RoleMenuJoinDTO roleMenuJoinDTO = new RoleMenuJoinDTO();

		roleMenuJoinDTO.setId("0a8fed60a7b26d2580de319b55711a0d150ac6197cc94bb6bab4543b56c74322");
		roleMenuJoinDTO.setRoleId("0a8fed60a7b26d2580de319b55711a0d");
		roleMenuJoinDTO.setMenuId("150ac6197cc94bb6bab4543b56c74322");
		boolean a = iRoleMenuService.remove(roleMenuJoinDTO);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void batchRemove() {

		List<RoleMenuJoinDTO> list = new ArrayList<RoleMenuJoinDTO>();
		RoleMenuJoinDTO roleMenuJoinDTO = new RoleMenuJoinDTO();
		roleMenuJoinDTO.setId("0a8fed60a7b26d2580de319b55711a0d183c6c91e0094459aefed0032e2f5c67");
		roleMenuJoinDTO.setRoleId("0a8fed60a7b26d2580de319b55711a0d");
		roleMenuJoinDTO.setMenuId("183c6c91e0094459aefed0032e2f5c67");
		RoleMenuJoinDTO roleMenuJoinDTO2 = new RoleMenuJoinDTO();
		roleMenuJoinDTO2.setId("1f7ce0c8b80b472d95b9d346cf79e0841813c625de4e4295a46b4c29f1ed9510");
		roleMenuJoinDTO2.setRoleId("1f7ce0c8b80b472d95b9d346cf79e084");
		roleMenuJoinDTO2.setMenuId("1813c625de4e4295a46b4c29f1ed9510");
		list.add(roleMenuJoinDTO);
		list.add(roleMenuJoinDTO2);
		boolean a = iRoleMenuService.batchRemove(list);

		System.out.println(a);

	}
}
