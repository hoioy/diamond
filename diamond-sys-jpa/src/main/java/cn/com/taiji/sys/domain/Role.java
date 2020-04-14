package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.TDFJpaQueryWord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 类名称：Role   角色
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(name = "flag_role_name唯一索引", columnNames = {"flag", "role_name"}))
@Where(clause = "flag=1")
public class Role extends BaseDomain {

    private static final long serialVersionUID = 4641095039785783252L;

    @Column(name = "role_description")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String roleDesc;

    @Column(name = "role_index")
    private Integer roleIndex;

    @Column(name = "role_name")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String roleName;
}