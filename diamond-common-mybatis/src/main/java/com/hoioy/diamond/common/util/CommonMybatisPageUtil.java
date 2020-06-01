package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.SortDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.validator.ValidationUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonMybatisPageUtil {

    public static Page getPage(PageDTO pageDTO) throws BaseException {
        ValidationUtil.validate(pageDTO);
        int pageSize = pageDTO.getPageSize();
        int pageNumber = pageDTO.getPage();
        List<SortDTO> sortDTOs = pageDTO.getSorts();
        Page page = new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNumber);
        List<OrderItem> orderItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sortDTOs)) {
            for (SortDTO sortDTO : sortDTOs) {
                String field = StrUtil.toSymbolCase(sortDTO.getFieldName(), '_');
                String direction = sortDTO.getDirection().toLowerCase();
                if(StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(direction)){
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
        }
        return page;
    }

    public static PageDTO getPageDTO(IPage page) throws BaseException {
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
