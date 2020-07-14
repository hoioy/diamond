package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseTreeDomain;
import com.hoioy.diamond.common.util.CommonJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 数据字典
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "data_item", uniqueConstraints = @UniqueConstraint(name = "flag_code唯一索引", columnNames = {"flag", "code"}))
@Where(clause = "flag=1")
public class DataItem extends BaseTreeDomain {

    private static final long serialVersionUID = -98516053380975909L;

    @Column(name = "code")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String code;

    @Column(name = "code_index")
    private Integer codeIndex;

    @Column(name = "name")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String name;

    @Column(name = "state")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String state;
    
    @Column(name = "data_item_type_id")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String dataItemTypeId;
    
}