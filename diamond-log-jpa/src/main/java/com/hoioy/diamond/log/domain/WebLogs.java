package com.hoioy.diamond.log.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.util.CommonJpaQueryWord;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "web_logs")
public class WebLogs extends BaseDomain{

    private static final long serialVersionUID = 1L;

    @Column(name = "log_user_name")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String logUserName;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "log_info")
    private String logInfo;
    @Column(name = "log_operation_type")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String logOperationType;
    @Column(name = "log_table_name")
    private String logTableName;
    @Column(name = "log_primary_key")
    private String logPrimaryKey;
    @Column(name = "log_class_name")
    private String logClassName;
    @Column(name = "module")
    @CommonJpaQueryWord(func = CommonJpaQueryWord.MatchType.like)
    private String module;
    @Column(name = "remark")
    private String remark;
    @Column(name = "log_method_name")
    private String logMethodName;
    @Column(name = "log_url")
    private String logUrl;
    @Column(name = "log_server_ip")
    private String logServerIp;
    @Column(name = "log_client_ip")
    private String logClientIp;
    /**
     * 以下两个是预留字段
     */
    @Column(name = "info")
    private String info;

    @Column(name = "type")
    private String type;
}