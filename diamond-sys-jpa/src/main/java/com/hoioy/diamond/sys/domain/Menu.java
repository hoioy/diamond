package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseTreeDomain;
import com.hoioy.diamond.common.util.CommonJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the menu database table.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "menu")
@Where(clause = "flag=1")
public class Menu extends BaseTreeDomain {
    private static final long serialVersionUID = 7381374907067127702L;

    @Column(name = "controller_class")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String controllerClass;

    @Column(name = "icon_path")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String iconPath;

    @Column(name = "menu_desc")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String menuDesc;

    @Column(name = "menu_index")
    private Integer menuIndex;

    @Column(name = "menu_name")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String menuName;

    @Column(name = "menu_url")
    private String menuUrl;

    @Column(name = "mark")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String mark;

    @Column(name = "small_icon_path")
    private String smallIconPath;

    @Column(name = "authority_id")
    private String authorityId;

    @Column(name = "menu_classify")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String menuClassify;
}