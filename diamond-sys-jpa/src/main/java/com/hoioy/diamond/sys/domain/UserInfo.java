package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.DiamondJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * 类描述： 用户数据库表的持久类
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user_info", uniqueConstraints = @UniqueConstraint(name = "flag_loginname唯一索引", columnNames = {"flag", "login_name"}))
@Where(clause = "flag=1")
public class UserInfo extends BaseDomain {
    private static final long serialVersionUID = -3149974916027750041L;

    @Column(name = "user_name")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String userName;

    @Column(name = "user_index")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private Integer userIndex;

    @Column(name = "login_name")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String loginName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String email;

    @Column(name = "phone_num")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String phoneNum;

    @Column(name = "state")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String state;

    @Column(name = "nickname")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String nickname;

    @Column(name = "gender")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String gender;

    @Column(name = "address")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String address;

    @Column(name = "blog")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String blog;

    @Column(name = "tag")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String tag;

    @Column(name = "avatar")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String avatar;

    @Lob
    @Column(name = "avatarContent")
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatarContent;

    @Column(name = "idNumber")
    private String idNumber;

    @Column(name = "birthday")
    @DiamondJpaQueryWord(func = DiamondJpaQueryWord.MatchType.like)
    private String birthday;

    @Column(name = "integral")
    private Integer integral;
}