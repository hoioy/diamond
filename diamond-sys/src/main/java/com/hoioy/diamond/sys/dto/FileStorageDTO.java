package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单文件管理DTO
 **/
@Data
@NoArgsConstructor
public class FileStorageDTO extends BaseDTO {

    private static final long serialVersionUID = 2558887945428876699L;

    //文件名
    private String fileName;
    //文件扩展名
    private String extension;
    //文件相对当前程序路径
    private String relativePath;
    //文件状态
    private Integer state;
}
