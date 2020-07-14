package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.SortDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.validator.ValidationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonMybatisPageUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonMybatisPageUtil.class);

    public static CommonMybatisPageUtil getInstance() {
        return CommonMybatisPageUtil.CommonMybatisPageUtilHolder.instance;
    }

    public Page pageDTOtoPage(PageDTO pageDTO) throws BaseException {
        ValidationUtil.validate(pageDTO);
        int pageSize = pageDTO.getPageSize();
        int pageNumber = pageDTO.getPage();
        List<SortDTO> sortDTOs = pageDTO.getSorts();
        Page page = new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNumber);
        List<OrderItem> orderItems = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(sortDTOs)) {
            for (SortDTO sortDTO : sortDTOs) {
                String field = StrUtil.toSymbolCase(sortDTO.getFieldName(), '_');
                String direction = sortDTO.getDirection().toLowerCase();
                if (StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(direction)) {
                    if ("asc".equals(direction.toLowerCase())) {
                        OrderItem asc = OrderItem.asc(field);
                        orderItems.add(asc);
                    } else {
                        OrderItem desc = OrderItem.desc(field);
                        orderItems.add(desc);
                    }
                }
            }
            page.setOrders(orderItems);
        }else{
            //排序字段为空，使用乱序，因为id本身时无序随机uuid
            OrderItem asc = OrderItem.asc("id");
            orderItems.add(asc);
            page.setOrders(orderItems);
        }
        return page;
    }

    private <DTO extends CommonDTO> DTO createDTO(Class<DTO> dtoClass) {
        try {
            DTO dto = dtoClass.newInstance();
            return dto;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <DTO extends CommonDTO> DTO mapToDTO(Map map, Class<DTO> dtoClass) {
        if (map == null) {
            return null;
        }

        DTO dto = createDTO(dtoClass);
        if (dto != null) {
            dto = BeanUtil.fillBeanWithMapIgnoreCase(map, dto, false);
        }
        return dto;
    }

    public <DTO extends CommonDTO> List<DTO> mapListToDTOList(List<Map> maps, Class<DTO> dtoClass) {
        List<DTO> dtoList = new ArrayList();
        maps.forEach(d -> {
            dtoList.add(mapToDTO(d, dtoClass));
        });
        return dtoList;
    }

    //mybatis的IPage对象转化为PageDTO
    public <DTO extends CommonDTO> PageDTO<DTO> iPageToPageDTO(IPage iPage, Class<DTO> dtoClass) throws BaseException {
        //转换
        PageDTO<DTO> pageDTO = new PageDTO();
        pageDTO.setPage((int) iPage.getCurrent());
        pageDTO.setTotal(iPage.getTotal());
        pageDTO.setPageSize((int) iPage.getSize());
        pageDTO.setList(iPage.getRecords());

        // record数据格式或者类型转换
        if (CollectionUtil.isNotEmpty(pageDTO.getList())) {
            List<DTO> dtoList;
            if (pageDTO.getList().get(0) instanceof Map) {
                //如果返回是Map类型
                dtoList = mapListToDTOList(pageDTO.getList(), dtoClass);
            } else {
                //如果是Domain子类
                dtoList = new ArrayList();
                for (Object domain : pageDTO.getList()) {
                    DTO dto = createDTO(dtoClass);
                    if (dto != null) {
                        CommonBeanUtil.saveCopy(domain, dto);
                    }
                    dtoList.add(dto);
                }
            }
            pageDTO.setList(dtoList);
        }

        return pageDTO;
    }

    private static class CommonMybatisPageUtilHolder {
        private static CommonMybatisPageUtil instance = new CommonMybatisPageUtil();
    }
}
