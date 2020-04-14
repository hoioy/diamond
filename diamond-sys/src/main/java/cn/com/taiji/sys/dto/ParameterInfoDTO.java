package com.hoioy.diamond.sys.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 类名称: ParameterInfoDTO
 * 类描述：参数管理Dto对象
 *
 * @Author: Baihy
 * @Date: 2019/3/11
 **/
@Data
@NoArgsConstructor
public class ParameterInfoDTO extends BaseDTO {

    private static final long serialVersionUID = 2558887945428876699L;

    private ArrayList parameterKeys;

    private String page;

    private String pageSize;

    private List<Map> sorts;

    private List<Map> filters;

    private Long total;

    private List<ParameterInfoDTO> list;

    /**
     * 参数键名
     */
    private String parameterKey;

    /**
     * 参数键值
     */
    private String parameterValue;

    /**
     * 参数名称
     */
    private String parameterName;

    /**
     * 更改时间String类型对象
     */
    private String updatedDateStr;

    /**
     * json格式的综合数据
     */
    private Object parameterJson;
}
