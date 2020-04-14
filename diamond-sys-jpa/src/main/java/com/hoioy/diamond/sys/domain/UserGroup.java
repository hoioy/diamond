package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.TDFJpaQueryWord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 类名称：UserGroup   用户组
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user_group")
@Where(clause = "flag=1")
public class UserGroup extends BaseDomain {

    @JsonIgnore
    private static final long serialVersionUID = 4186756588977538061L;

    @Column(name = "group_index")
    private Integer groupIndex;

    @Column(name = "group_name")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String groupName;

    @Column(name = "state")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String state;
}