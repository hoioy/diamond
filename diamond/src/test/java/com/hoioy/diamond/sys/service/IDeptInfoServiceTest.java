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

@RunWith(SpringRunner.class)
@SpringBootTest
public class IDeptInfoServiceTest {

	@Autowired
	IDeptInfoService iDeptInfoService;

	@Test
//	@Ignore
	public void getPage() {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(1);
		pageDTO.setPageSize(10);
		pageDTO = iDeptInfoService.getPage(pageDTO);
		Assert.assertNotNull(pageDTO);
	}

	@Test
	@Ignore
	public void batchSave() {
		List<DeptInfoDTO> list = new ArrayList<DeptInfoDTO>();
		for (int x = 0; x < 10; x++) {
			DeptInfoDTO deptInfoDTO = new DeptInfoDTO();
			deptInfoDTO.setDeptName("ceshi" + x);
			list.add(deptInfoDTO);
		}

		boolean a = iDeptInfoService.batchSave(list);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		DeptInfoDTO deptInfoDTO = new DeptInfoDTO();

		deptInfoDTO.setDeptName("nameSave");

		String id = iDeptInfoService.save(deptInfoDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void update() {

		DeptInfoDTO deptInfoDTO = new DeptInfoDTO();
		deptInfoDTO.setId("0a1a90f484854bc397494ba6929db61a");
		deptInfoDTO.setDeptName("nameUpdate");

		String id = iDeptInfoService.update(deptInfoDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void removeById() {

		boolean a = iDeptInfoService.removeById("0a1a90f484854bc397494ba6929db61a");
		System.out.println(a);

	}

	@Test
	@Ignore
	public void removeByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a1a90f484854bc397494ba6929db61a");
		ids.add("17cadb4be5cf40158b0f8a40b7b81f7e");

		boolean a = iDeptInfoService.removeByIds(ids);

		System.out.println(a);

	}

	@Test
	@Ignore
	public void findById() {

		Optional<DeptInfoDTO> deptInfoDTO = iDeptInfoService.findById("0a1a90f484854bc397494ba6929db61a");

		Assert.assertNotNull(deptInfoDTO);

	}

	@Test
	@Ignore
	public void findByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0a1a90f484854bc397494ba6929db61a");
		ids.add("17cadb4be5cf40158b0f8a40b7b81f7e");

		List<DeptInfoDTO> dtos = iDeptInfoService.findByIds(ids);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}

	@Test
	@Ignore
	public void findAll() {

		List<DeptInfoDTO> list = iDeptInfoService.findAll();

		Assert.assertNotNull(list);

		System.out.println(list.size());

	}

}
