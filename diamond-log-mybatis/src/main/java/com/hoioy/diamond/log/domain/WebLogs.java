package com.hoioy.diamond.log.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("web_logs")
public class WebLogs extends BaseDomain {

    private static final long serialVersionUID=1L;

    @TableField("log_user_name")
    private String logUserName;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("log_info")
    private String logInfo;

    @TableField("log_operation_type")
    private String logOperationType;

    @TableField("log_table_name")
    private String logTableName;

    @TableField("log_primary_key")
    private String logPrimaryKey;

    @TableField("log_class_name")
    private String logClassName;

    @TableField("module")
    private String module;

    @TableField("log_method_name")
    private String logMethodName;

    @TableField("log_url")
    private String logUrl;

    @TableField("log_server_ip")
    private String logServerIp;

    @TableField("log_client_ip")
    private String logClientIp;

    @TableField("info")
    private String info;

    @TableField("type")
    private String type;

}
