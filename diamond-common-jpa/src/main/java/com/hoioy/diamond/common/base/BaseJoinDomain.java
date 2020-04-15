package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.DiamondDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//@MappedSuperclass标识的类表示其不能映射到数据库表
@MappedSuperclass
@Data
@NoArgsConstructor
public class BaseJoinDomain extends DiamondDomain {

    @Id
    @Column(name = "id")
    private String id;
}
