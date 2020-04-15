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
        List<Map> sorts = pageDTO.getSorts();
        Page page = new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNumber);
        List<OrderItem> orderItems = new ArrayList<>();
        if (sorts!=null) {
            for (Map sort : sorts) {
                String field = (String) sort.get("field");
                String[] ss = field.split("(?<!^)(?=[A-Z])");
                int length = ss.length;
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i <length ; i++) {
                    if (i == length -1) {
                        stringBuilder.append(ss[i].toLowerCase());
                    }else {
                        stringBuilder.append(ss[i].toLowerCase()).append("_");
                    }
                }
                String value = (String) sort.get("value");
                if ("asc".equals(value.toLowerCase())) {
                    OrderItem asc = OrderItem.asc(stringBuilder.toString());
                    orderItems.add(asc);
                } else {
                    OrderItem desc = OrderItem.desc(stringBuilder.toString());
                    orderItems.add(desc);
                }
            }
            page.setOrders(orderItems);
        }
        return page;
    }

    public static  <T> T getBean(PageDTO pageDTO, Class<T> beanClass) throws BaseException {
        List<Map> filters = pageDTO.getFilters();
        Map<Object, Object> map = new HashMap<>();
        if (filters!=null) {
            for (Map filter : filters) {
                Object field =  filter.get("field");
                Object value =  filter.get("value");
                map.put(field, value);
            }
        }
      return BeanUtil.mapToBean(map, beanClass, true);
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
