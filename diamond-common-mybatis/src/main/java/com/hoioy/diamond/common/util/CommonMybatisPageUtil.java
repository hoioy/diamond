package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.CommonPageDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.SortDTO;
import com.hoioy.diamond.common.exception.BaseException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.validation.ValidationUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonMybatisPageUtil {

    public static CommonMybatisPageUtil getInstance() {
        return CommonMybatisPageUtil.CommonMybatisPageUtilHolder.instance;
    }

    public Page pageDTOtoPage(CommonPageDTO pageDTO) throws BaseException {
        return pageDTOtoPage(pageDTO, "id");
    }

    public Page pageDTOtoPage(CommonPageDTO pageDTO, String defaultSortField) throws BaseException {
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
                if (StrUtil.isNotBlank(field) && StrUtil.isNotBlank(direction)) {
                    if ("asc".equalsIgnoreCase(direction)) {
                        OrderItem asc = OrderItem.asc(field);
                        orderItems.add(asc);
                    } else {
                        OrderItem desc = OrderItem.desc(field);
                        orderItems.add(desc);
                    }
                }
            }
            page.setOrders(orderItems);
        } else {
            //排序字段为空，使用乱序，因为id本身时无序随机uuid
            OrderItem asc = OrderItem.asc(defaultSortField);
            orderItems.add(asc);
            page.setOrders(orderItems);
        }
        return page;
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
        maps.forEach(d -> dtoList.add(mapToDTO(d, dtoClass)));
        return dtoList;
    }

    /**
     * mybatis的IPage对象转化为PageDTO
     *
     * @param iPage
     * @param dtoClass
     * @param originPageDTO
     * @return
     * @throws BaseException
     */
    public CommonPageDTO iPageToCommonPageDTO(IPage iPage, Class dtoClass, CommonPageDTO originPageDTO) throws BaseException {
        //转换
        originPageDTO.setPage((int) iPage.getCurrent());
        originPageDTO.setTotal(iPage.getTotal());
        originPageDTO.setPageSize((int) iPage.getSize());
        originPageDTO.setList(iPage.getRecords());

        // record数据格式或者类型转换
        if (CollectionUtil.isNotEmpty(originPageDTO.getList())) {
            List dtoList;
            if (originPageDTO.getList().get(0) instanceof Map) {
                //如果返回是Map类型
                dtoList = mapListToDTOList(originPageDTO.getList(), dtoClass);
            } else {
                //如果是Domain子类
                dtoList = new ArrayList();
                for (Object domain : originPageDTO.getList()) {
                    Object dto = null;
                    try {
                        dto = dtoClass.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (dto != null) {
                        CommonBeanUtil.saveCopy(domain, dto);
                    }
                    dtoList.add(dto);
                }
            }
            originPageDTO.setList(dtoList);
        }

        return originPageDTO;
    }

    /**
     * mybatis的IPage对象转化为PageDTO
     */
    public <DTO extends CommonDTO> PageDTO<DTO> iPageToPageDTO(IPage iPage, Class<DTO> dtoClass, CommonPageDTO<DTO> originPageDTO) throws BaseException {
        return (PageDTO<DTO>) iPageToCommonPageDTO(iPage, dtoClass, originPageDTO);
    }

    //mybatis的IPage对象转化为PageDTO
    //高版本会删除此方法或者改为私有private类型，请使用iPageToPageDTO代替
    @Deprecated
    public <DTO extends CommonDTO> PageDTO<DTO> iPageToPageDTO(IPage iPage, Class<DTO> dtoClass) throws BaseException {
        return (PageDTO<DTO>) iPageToCommonPageDTO(iPage, dtoClass, new PageDTO());
    }


    private <DTO extends CommonDTO> DTO createDTO(Class<DTO> dtoClass) {
        try {
            DTO dto = dtoClass.newInstance();
            return dto;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class CommonMybatisPageUtilHolder {
        private static CommonMybatisPageUtil instance = new CommonMybatisPageUtil();
    }
}
