package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.DiamondJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 数据字典
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "data_item")
@Where(clause = "flag=1")
public class DataItem extends BaseDomain {

    private static final long serialVersionUID = -98516053380975909L;

    @Column(name = "code")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String code;

    @Column(name = "code_index")
    private Integer codeIndex;

    @Column(name = "name")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String name;

    @Column(name = "state")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String state;
}