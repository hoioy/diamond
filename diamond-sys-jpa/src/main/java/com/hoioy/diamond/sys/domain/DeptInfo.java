package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.DiamondJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 机构
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "dept_info", uniqueConstraints = @UniqueConstraint(name = "flag_dept_name唯一索引", columnNames = {"flag", "dept_name"}))
@Where(clause = "flag=1")
public class DeptInfo extends BaseDomain {
    private static final long serialVersionUID = 5119673746393145493L;

    @Column(name = "dept_desc")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String deptDesc;

    @Column(name = "dept_index")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private Integer deptIndex;

    @Column(name = "dept_name")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String deptName;

    @Column(name = "dept_state")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String deptState;

    @Column(name = "dept_type")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String deptType;

    @Column(name = "dept_url")
    private String deptUrl;
}
