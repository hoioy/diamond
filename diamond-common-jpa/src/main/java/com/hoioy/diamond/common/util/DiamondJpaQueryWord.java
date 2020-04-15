package com.hoioy.diamond.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DiamondJpaQueryWord {
    // 数据库中字段名,默认为空字符串,则Query类中的字段要与数据库中字段一致
    String column() default "";

    // equal, like, gt, lt...
    MatchType func() default MatchType.equal;

    // object是否可以为null
    boolean nullble() default false;

    // 字符串是否可为空
    boolean emptyAble() default false;

    //用枚举类表示查询连接条件
    enum MatchType {
        equal,        // filed = value
        //下面四个用于Number类型的比较
        gt,   // filed > value
        ge,   // field >= value
        lt,              // field < value
        le,      // field <= value
        notEqual,            // field != value
        like,   // field like value
        notLike,    // field not like value
        // 下面四个用于可比较类型(Comparable)的比较
        greaterThan,        // field > value
        greaterThanOrEqualTo,   // field >= value
        lessThan,               // field < value
        lessThanOrEqualTo,      // field <= value
        leftJoin;
    }
}
