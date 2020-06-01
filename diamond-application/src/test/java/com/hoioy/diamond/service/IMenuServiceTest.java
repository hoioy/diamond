package com.hoioy.diamond.service;

import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.service.IMenuService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IMenuServiceTest {

	@Autowired
	IMenuService iMenuService;

	@Test
	@Ignore
	public void getPage() {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(1);
		pageDTO.setPageSize(10);
		pageDTO = iMenuService.getPage(pageDTO);
		Assert.assertNotNull(pageDTO);
	}

	@Test
	@Ignore
	public void batchSave() {
		List<MenuDTO> list = new ArrayList<MenuDTO>();
		for (int x = 0; x < 10; x++) {
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setMenuName("ceshi"+x);
			list.add(menuDTO);
		}

		boolean a = iMenuService.batchSave(list);
		System.out.println(a);
	}

	@Test
	@Ignore
	public void Save() {

		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setMenuName("ceshiSave");

		String id = iMenuService.save(menuDTO).getId();
		System.out.println(id);

	}

	@Test
	@Ignore
	public void update() {

		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId("d9f8bb20942643188bcf3a618e5e2dd6");
		menuDTO.setMenuName("nameUpdate");

		String id = iMenuService.update(menuDTO).getId();
		System.out.println(id);

	}


	@Test
	@Ignore
	public void findById() {

		Optional<MenuDTO> deptInfoDTO = iMenuService.findById("d9f8bb20942643188bcf3a618e5e2dd6");

		Assert.assertNotNull(deptInfoDTO);

	}

	@Test
	@Ignore
	public void findByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("a8e6864dfbf74e69b677011d64403887");
		ids.add("d9f8bb20942643188bcf3a618e5e2dd6");

		List<MenuDTO> dtos = iMenuService.findByIds(ids);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}


	@Test
	@Ignore
	public void findMenusByParentId() {

		List<MenuDTO> listTree = iMenuService.findByParentId("90a127ce319d5d93b3b49c697cfa138f");

		Assert.assertNotNull(listTree);

		System.out.println(listTree.size());

		
	}
	
	

	@Test
	@Ignore
	public void removeById() {

		boolean a = iMenuService.removeById("d9f8bb20942643188bcf3a618e5e2dd6");
		System.out.println(a);

	}

	@Test
	@Ignore
	public void removeByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("a8e6864dfbf74e69b677011d64403887");
		ids.add("d9f8bb20942643188bcf3a618e5e2dd6");

		boolean a = iMenuService.removeByIds(ids);

		System.out.println(a);

	}
	
}
