package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.SortDTO;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

public class CommonJpaPageUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonJpaPageUtil.class);

    public static CommonJpaPageUtil getInstance() {
        return CommonJpaPageUtil.CommonJpaPageUtilHolder.instance;
    }

    public PageRequest toPageRequest(final PageDTO pageDTO) {
        //1. 获取pageable
        int pageIndex = pageDTO.getPage() - 1;
        int pageSize = pageDTO.getPageSize();
        PageRequest pageable = null;

        List<SortDTO> sortDTOS = pageDTO.getSorts();
        if (CollectionUtils.isEmpty(sortDTOS)) {
            //排序字段为空，使用乱序，因为id本身时无序随机uuid
            List<Sort.Order> orders = new ArrayList();
            orders.add(new Sort.Order(Sort.Direction.valueOf("ASC"), "id"));
            pageable = of(pageIndex, pageSize, Sort.by(orders));
        } else {
            List<Sort.Order> orders = new ArrayList();
            // 排序字段不为空
            sortDTOS.forEach(sortDTO -> {
                String field = sortDTO.getFieldName();
                String direction = sortDTO.getDirection();
                if(StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(direction)){
                    orders.add(new Sort.Order(Sort.Direction.valueOf(direction.toUpperCase()), sortDTO.getFieldName()));
                }
            });
            pageable = of(pageIndex, pageSize, Sort.by(orders));
        }
        return pageable;
    }

    //logicType or/and
    public <D extends BaseDomain> Specification<D> toSpecWithLogicType(D main, String logicType) {
        return (root, criteriaQuery, cb) -> {
            //获取查询类Query的所有字段,包括父类字段
            List<Field> fields = Arrays.asList(ReflectUtil.getFields(main.getClass()));
            List<Predicate> predicates = new ArrayList<>(fields.size());
            for (Field field : fields) {
                //获取字段上的@QueryWord注解
                CommonJpaQueryWord qw = field.getAnnotation(CommonJpaQueryWord.class);
                if (qw == null) {
                    continue;
                }

                // 获取字段名
                String column = qw.column();
                //如果主注解上colume为默认值"",则以field为准
                if (column.equals("")) {
                    column = field.getName();
                }

                field.setAccessible(true);
                try {
                    // nullable
                    Object value = field.get(main);
                    //如果值为null,注解未标注nullable,跳过
                    if (value == null && !qw.nullble()) {
                        continue;
                    }
                    // can be empty
                    if (value != null && String.class.isAssignableFrom(value.getClass())) {
                        String s = (String) value;
                        //如果值为"",且注解未标注emptyable,跳过
                        if (s.equals("") && !qw.emptyAble())
                            continue;
                    }

                    //通过注解上func属性,构建路径表达式
                    Path path = root.get(column);
                    switch (qw.func()) {
                        case equal:
                            predicates.add(cb.equal(path, value));
                            break;
                        case like:
                            predicates.add(cb.like(path, "%" + value + "%"));
                            break;
                        case gt:
                            predicates.add(cb.gt(path, (Number) value));
                            break;
                        case lt:
                            predicates.add(cb.lt(path, (Number) value));
                            break;
                        case ge:
                            predicates.add(cb.ge(path, (Number) value));
                            break;
                        case le:
                            predicates.add(cb.le(path, (Number) value));
                            break;
                        case notEqual:
                            predicates.add(cb.notEqual(path, value));
                            break;
                        case notLike:
                            predicates.add(cb.notLike(path, "%" + value + "%"));
                            break;
                        case greaterThan:
                            predicates.add(cb.greaterThan(path, (Comparable) value));
                            break;
                        case greaterThanOrEqualTo:
                            predicates.add(cb.greaterThanOrEqualTo(path, (Comparable) value));
                            break;
                        case lessThan:
                            predicates.add(cb.lessThan(path, (Comparable) value));
                            break;
                        case lessThanOrEqualTo:
                            predicates.add(cb.lessThanOrEqualTo(path, (Comparable) value));
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }

            Predicate p = null;
            if (logicType == null || logicType.equals("") || logicType.equals("and")) {
                p = cb.and(predicates.toArray(new Predicate[predicates.size()]));//and连接
            } else if (logicType.equals("or")) {
                p = cb.or(predicates.toArray(new Predicate[predicates.size()]));//or连接
            }
            return p;
        };
    }

    private static class CommonJpaPageUtilHolder {
        private static CommonJpaPageUtil instance = new CommonJpaPageUtil();
    }
}
