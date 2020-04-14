package com.hoioy.diamond.log.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WebLogsDTO extends BaseDTO {
    private static final long serialVersionUID = 8654485582322279564L;
    /**
     * 实体类
     */
    private String logClassName;
    /**
     * 方法描述
     */
    private String logInfo;
    /**
     * 方法名
     */
    private String logMethodName;
    /**
     * 操作方法
     */
    private String logOperationType;
    /**
     *
     */
    private String logPrimaryKey;
    /**
     * 日志表名称
     */
    private String logTableName;
    /**
     *
     */
    private String logUrl;
    /**
     * 访问人
     */
    private String logUserName;
    /**
     * 模块名称
     */
    private String module;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String logServerIp;
    private String logClientIp;
    /**
     * 以下两个是预留字段
     */
    private String info;
    private String type;

}