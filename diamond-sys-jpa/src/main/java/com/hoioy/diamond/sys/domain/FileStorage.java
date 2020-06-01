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

@Data
@NoArgsConstructor
@Entity
@Table(name = "file_storage", uniqueConstraints = @UniqueConstraint(name = "file_name-extension唯一索引", columnNames = {"flag","extension", "file_name"}))
@Where(clause = "flag=1")
public class FileStorage extends BaseDomain {
    private static final long serialVersionUID = 1L;
    //文件名
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    @Column(name = "file_name")
    private String fileName;
    //文件扩展名
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    @Column(name = "extension")
    private String extension;
    //文件路径
    @Column(name = "relative_path")
    private String relativePath;
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.equal)
    @Column(name = "state")
    private Integer state;
}