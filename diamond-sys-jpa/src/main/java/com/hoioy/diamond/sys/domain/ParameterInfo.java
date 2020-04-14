package com.hoioy.diamond.sys.domain;


import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.TDFJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 参数管理实体类
 *
 * @Author: Baihy
 * @Date: 2019/3/11
 **/
@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "parameter_info",uniqueConstraints = {@UniqueConstraint(columnNames="parameter_key")})
@Where(clause = "flag=1")
public class ParameterInfo extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 7578292815541249813L;

    /**
     * 参数键名
     */
    @Column(name = "parameter_key")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.equal)
    private String parameterKey;

    /**
     * 参数键值
     */
    @Column(name = "parameter_value")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String parameterValue;

    /**
     * 参数名称
     */
    @Column(name = "parameter_name")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String parameterName;
}
