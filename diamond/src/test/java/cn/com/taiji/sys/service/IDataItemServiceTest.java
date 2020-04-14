package com.hoioy.diamond.sys.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.domain.DataItem;
import com.hoioy.diamond.sys.dto.DataItemDTO;
import com.hoioy.diamond.sys.service.IDataItemService;

@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableJpaAuditing 
public class IDataItemServiceTest {

	@Autowired
	IDataItemService iDataItemService;

	@Test
	@Ignore
	public void getPage() {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(1);
		pageDTO.setPageSize(10);
		pageDTO = iDataItemService.getPage(pageDTO);
		Assert.assertNotNull(pageDTO);
	}

	@Test
	@Ignore
	public void batchSave() {
		List<DataItemDTO> list = new ArrayList<DataItemDTO>();
		for (int x = 0; x < 10; x++) {
			DataItemDTO dataItem = new DataItemDTO();
			dataItem.setCode("ceshi" + x);
			dataItem.setName("name" + x);
			list.add(dataItem);
		}

		boolean a = iDataItemService.batchSave(list);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {
		DataItemDTO dataItem = new DataItemDTO();
		dataItem.setCode("ceshiSave");
		dataItem.setName("nameSave");

		String id = iDataItemService.save(dataItem);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void update() {

		DataItemDTO dataItem = new DataItemDTO();
		dataItem.setId("fc720cf023764b1f81fdfbcecee10a21");
		dataItem.setCode("ceshiSaveUpdate");
		dataItem.setName("nameSave");

		String id = iDataItemService.update(dataItem);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void removeById() {

		boolean a = iDataItemService.removeById("fc720cf023764b1f81fdfbcecee10a21");
		System.out.println(a);

	}

	@Test
	@Ignore
	public void removeByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("bd1ae53d392a4b81a4b465dddf5de681");
		ids.add("ec9ba73f1d3d4864b089a079e885f061");

		boolean a = iDataItemService.removeByIds(ids);

		System.out.println(a);

	}
	
	@Test
	@Ignore
	public void findById() {

		Optional<DataItemDTO> dataItemDTO = iDataItemService.findById("fc720cf023764b1f81fdfbcecee10a21");
		
		Assert.assertNotNull(dataItemDTO);

	}

	@Test
	@Ignore
	public void findByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("bd1ae53d392a4b81a4b465dddf5de681");
		ids.add("ec9ba73f1d3d4864b089a079e885f061");

		List<DataItemDTO>  dtos = iDataItemService.findByIds(ids);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}
	
}
