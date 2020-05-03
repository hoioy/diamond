package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import cn.hutool.core.bean.BeanUtil;
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
import java.util.*;

import static org.springframework.data.domain.PageRequest.of;

public class DiamondJpaPageUtil {
    private static final Logger logger = LoggerFactory.getLogger(DiamondJpaPageUtil.class);

    public static final String FIELD_FOR_SEARCH = "field";
    public static final String VALUE_FOR_SEARCH = "value";

    public static DiamondJpaPageUtil getInstance() {
        return DiamondJpaPageUtil.DiamondJpaPageUtilHolder.instance;
    }

    public PageRequest toPageRequest(final PageDTO pageDTO) {
        //1. 获取pageable
        int pageIndex = pageDTO.getPage() - 1;
        int pageSize = pageDTO.getPageSize();
        PageRequest pageable = null;

        List<Map> sortMaps = null;
        if (CollectionUtils.isEmpty(sortMaps)) {
            pageable = of(pageIndex, pageSize);
        } else {
            List<Sort.Order> orders = new ArrayList();
            // 排序字段不为空
            sortMaps.forEach(map -> {
                if (map.get(FIELD_FOR_SEARCH) != null) {
                    String field = map.get(DiamondJpaPageUtil.FIELD_FOR_SEARCH).toString();
                    String dir = map.get(DiamondJpaPageUtil.VALUE_FOR_SEARCH).toString();
                    if (StringUtils.isNotEmpty(field)) {
                        if ("DESC".equalsIgnoreCase(dir)) {
                            orders.add(new Sort.Order(Sort.Direction.DESC, field));
                        } else {
                            orders.add(new Sort.Order(Sort.Direction.ASC, field));
                        }
                    }
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
                DiamondJpaQueryWord qw = field.getAnnotation(DiamondJpaQueryWord.class);
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

    public <D extends Object> D getBean(PageDTO pageDTO, Class<D> beanClass) throws BaseException {
        List<Map> filters = null;
        Map<Object, Object> map = new HashMap<>();
        if (filters != null) {
            for (Map filter : filters) {
                Object field = filter.get("field");
                Object value = filter.get("value");
                map.put(field, value);
            }
        }
        return BeanUtil.mapToBean(map, beanClass, true);
    }

    private static class DiamondJpaPageUtilHolder {
        private static DiamondJpaPageUtil instance = new DiamondJpaPageUtil();
    }
}
