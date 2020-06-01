package com.hoioy.diamond.service;

import com.hoioy.diamond.sys.dto.DeptUserJoinDTO;
import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;
import com.hoioy.diamond.sys.service.IRoleUserService;
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
public class IRoleUserServiceTest {

	@Autowired
	IRoleUserService iRoleUserService;

	@Test
	@Ignore
	public void batchSave() {
		
		List<RoleUserJoinDTO> list = new ArrayList<RoleUserJoinDTO>();

		RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO();
		RoleUserJoinDTO roleUserJoinDTO2 = new RoleUserJoinDTO();

		roleUserJoinDTO.setRoleId("0a8fed60a7b26d2580de319b55711a0d");
		roleUserJoinDTO.setUserId("6613831cac9e4597abbd0138116a8f3c");

		roleUserJoinDTO2.setRoleId("0d6f5723f03b4b20bb1bc2a6f03f6b0b");
		roleUserJoinDTO2.setUserId("6cabaa5aa0d90d99883eb446f6aca027");
		
		list.add(roleUserJoinDTO);
		list.add(roleUserJoinDTO2);

		boolean a = iRoleUserService.batchSave(list);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO();

		roleUserJoinDTO.setRoleId("1f7ce0c8b80b472d95b9d346cf79e084");
		roleUserJoinDTO.setUserId("26cf758ad1861d69fdbf361097db32fb");

		String a = iRoleUserService.save(roleUserJoinDTO).getId();
		System.out.println(a);

	}



	@Test
	@Ignore
	public void findUserIdsByRoleIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a8fed60a7b26d2580de319b55711a0d");
		ids.add("0d6f5723f03b4b20bb1bc2a6f03f6b0b");

		List<DeptUserJoinDTO> list = iRoleUserService.findUserIdsByRoleIds(ids);
		
		Assert.assertNotNull(list);

		System.out.println(list.size());

	}

	@Test
	@Ignore
	public void findRoleIdsByUserIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("6613831cac9e4597abbd0138116a8f3c");
		ids.add("6cabaa5aa0d90d99883eb446f6aca027");

		List<DeptUserJoinDTO> list = iRoleUserService.findRoleIdsByUserIds(ids);
		
		Assert.assertNotNull(list);

		System.out.println(list.size());

	}
	
	@Test
	@Ignore
	public void remove() {
		
		RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO();
		roleUserJoinDTO.setId("0a8fed60a7b26d2580de319b55711a0d6613831cac9e4597abbd0138116a8f3c");
		roleUserJoinDTO.setRoleId("1f7ce0c8b80b472d95b9d346cf79e084");
		roleUserJoinDTO.setUserId("26cf758ad1861d69fdbf361097db32fb");
		boolean a = iRoleUserService.remove(roleUserJoinDTO);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void batchRemove() {

		List<RoleUserJoinDTO> list = new ArrayList<RoleUserJoinDTO>();
		
		RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO();
		roleUserJoinDTO.setId("0a8fed60a7b26d2580de319b55711a0d6613831cac9e4597abbd0138116a8f3c");
		roleUserJoinDTO.setRoleId("0a8fed60a7b26d2580de319b55711a0d");
		roleUserJoinDTO.setUserId("6613831cac9e4597abbd0138116a8f3c");
		
		RoleUserJoinDTO roleUserJoinDTO2 = new RoleUserJoinDTO();
		roleUserJoinDTO2.setId("0d6f5723f03b4b20bb1bc2a6f03f6b0b6cabaa5aa0d90d99883eb446f6aca027");
		roleUserJoinDTO2.setRoleId("0d6f5723f03b4b20bb1bc2a6f03f6b0b");
		roleUserJoinDTO2.setUserId("6cabaa5aa0d90d99883eb446f6aca027");
		
		list.add(roleUserJoinDTO);
		list.add(roleUserJoinDTO2);
		
		boolean a = iRoleUserService.batchRemove(list);

		System.out.println(a);

	}
}
