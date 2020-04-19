package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.validator.ValidationUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiamondMybatisPageUtil {

    public static Page getPage(PageDTO pageDTO) throws BaseException {
        ValidationUtil.validate(pageDTO);
        int pageSize = pageDTO.getPageSize();
        int pageNumber = pageDTO.getPage();
        Map<String, Object> sorts = pageDTO.getSorts();
        Page page = new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNumber);
        List<OrderItem> orderItems = new ArrayList<>();
        if (sorts!=null) {
            sorts.forEach((k,v) ->{
                String sort = v.toString().toLowerCase();
                if ("asc".equals(sort)) {
                    OrderItem asc = OrderItem.asc(k);
                    orderItems.add(asc);
                } else {
                    OrderItem desc = OrderItem.desc(k);
                    orderItems.add(desc);
                }
            });
            OrderItem orderItem = BeanUtil.mapToBean(sorts, OrderItem.class, false);
            page.setOrders(orderItems);
        }
        return page;
    }

    public static  <T> T getBean(PageDTO pageDTO, Class<T> beanClass) throws BaseException {
        Map<String, Object> filters = pageDTO.getFilters();
      return BeanUtil.mapToBean(filters, beanClass, true);
    }

    public static PageDTO getPageDTO(IPage page)throws BaseException {
        long total = page.getTotal();
        long current = page.getCurrent();
        long size = page.getSize();
        List records = page.getRecords();
        PageDTO pageDTO = new PageDTO();
        pageDTO.setList(records);
        pageDTO.setPage((int) current);
        pageDTO.setTotal(total);
        pageDTO.setPageSize((int) size);
        return pageDTO;

    }
}
