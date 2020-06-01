package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.CommonJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 数据字典类型
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "data_item_type", uniqueConstraints = @UniqueConstraint(name = "type_name唯一索引", columnNames = {"flag","type_name"}))
@Where(clause = "flag=1")
public class DataItemType extends BaseDomain {

    private static final long serialVersionUID = -9975909L;

    @Column(name = "type_name")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String typeName;
}