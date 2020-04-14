package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.TDFJpaQueryWord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the menu database table.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "menu")
@Where(clause = "flag=1")
public class Menu extends BaseDomain {
    private static final long serialVersionUID = 7381374907067127702L;

    @Column(name = "controller_class")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String controllerClass;

    @Lob
    @Column(name = "icon_path")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String iconPath;

    @Column(name = "menu_desc")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String menuDesc;

    @Column(name = "menu_index")
    private Integer menuIndex;

    @Column(name = "menu_name")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String menuName;

    @Column(name = "menu_url")
    private String menuUrl;

    @Column(name = "mark")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String mark;

    @Column(name = "small_icon_path")
    private String smallIconPath;

    @Column(name = "authority_id")
    private String authorityId;

    @Column(name = "menu_classify")
    @TDFJpaQueryWord(func = TDFJpaQueryWord.MatchType.like)
    private String menuClassify;
}