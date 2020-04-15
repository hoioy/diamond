package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.util.DiamondJpaQueryWord;
import com.hoioy.diamond.common.util.DiamondSecurityUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

//@MappedSuperclass标识的类表示其不能映射到数据库表
@MappedSuperclass
@Data
@NoArgsConstructor
public class BaseDomain extends DiamondDomain {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @CreatedBy
    @Column(name = "created_by")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    protected String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "modified_by")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    protected String modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;

    //备注
    @Column(name = "remark")
    private String remark;

    //删除标志
    @Column(name = "flag")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.equal)
    public Integer flag;

    //乐观锁
    @Version
    private Integer version=0;

    @PrePersist
    public void createAuditInfo() {
        String loginName = DiamondSecurityUtils.getCurrentLogin();
        setCreatedBy(loginName);
        setCreatedDate(LocalDateTime.now());
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        this.setId(id);
        this.setFlag(1);// 默认初始化
    }
}
