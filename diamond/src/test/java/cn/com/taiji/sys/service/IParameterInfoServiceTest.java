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
import com.hoioy.diamond.sys.dto.ParameterInfoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IParameterInfoServiceTest {

	@Autowired
	IParameterInfoService iParameterInfoService;

	@Test
	@Ignore
	public void getPage() {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(1);
		pageDTO.setPageSize(10);
		pageDTO = iParameterInfoService.getPage(pageDTO);
		Assert.assertNotNull(pageDTO);
	}

	@Test
	@Ignore
	public void batchSave() {
		List<ParameterInfoDTO> list = new ArrayList<ParameterInfoDTO>();
		for (int x = 0; x < 10; x++) {
			
			ParameterInfoDTO parameterInfoDTO = new ParameterInfoDTO();
			
			parameterInfoDTO.setParameterKey("ceshiKey" + x);
			parameterInfoDTO.setParameterName("ceshiName" + x);

			list.add(parameterInfoDTO);
		}

		boolean a = iParameterInfoService.batchSave(list);
		System.out.println(a);

	}

	@Test
	@Ignore
	public void Save() {

		ParameterInfoDTO parameterInfoDTO = new ParameterInfoDTO();
		
		parameterInfoDTO.setParameterKey("ceshiKey");
		parameterInfoDTO.setParameterName("ceshiName");
		parameterInfoDTO.setParameterValue("ceshiValue");

		String id = iParameterInfoService.save(parameterInfoDTO);
		System.out.println(id);

	}

	@Test
	@Ignore
	public void update() {

		DeptInfoDTO deptInfoDTO = new DeptInfoDTO();
		deptInfoDTO.setId("730b93ca68a843b795abe7eb7c5889d3");
		deptInfoDTO.setDeptName("nameUpdate");

		String id = iParameterInfoService.update(deptInfoDTO);
		System.out.println(id);

	}


	@Test
	@Ignore
	public void findById() {

		Optional<DeptInfoDTO> deptInfoDTO = iParameterInfoService.findById("730b93ca68a843b795abe7eb7c5889d3");

		Assert.assertNotNull(deptInfoDTO);

	}

	@Test
	@Ignore
	public void findByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("730b93ca68a843b795abe7eb7c5889d3");
		ids.add("a1f6345a136c40178212475f6934bb8e");

		List<DeptInfoDTO> dtos = iParameterInfoService.findByIds(ids);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}
	
	@Test
	@Ignore
	public void findIdsByParameterKeys() {

		List<String> keys = new ArrayList<String>();

		keys.add("ceshiKey0");
		keys.add("ceshiKey2");

		List<DeptInfoDTO> dtos = iParameterInfoService.findIdsByParameterKeys(keys);

		Assert.assertNotNull(dtos);

		System.out.println(dtos.size());
	}
	//有问题
	@Test
	@Ignore
	public void removeById() {

		boolean a = iParameterInfoService.removeById("0a7ae604a98143f38e2a5e8b56517742");
		System.out.println(a);

	}

	@Test
	@Ignore
	public void removeByIds() {

		List<String> ids = new ArrayList<String>();

		ids.add("0f98f7d4216f4fc382f24ad623cb22b6");
		ids.add("7b2b2a0d69ed48f088d62867c5982904");

		boolean a = iParameterInfoService.removeByIds(ids);

		System.out.println(a);

	}

}
