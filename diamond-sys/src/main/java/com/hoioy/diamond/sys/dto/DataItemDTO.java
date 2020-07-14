package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 类名称：数据字典dto
 */
@Data
@NoArgsConstructor
public class DataItemDTO extends BaseTreeDTO {

    private static final long serialVersionUID = 7275727535778417507L;
    @NotBlank(message = "code不能为空")
    private String code;

    private Integer codeIndex;

    private String name;

    //是否启用:0不启用
    private String state;
    //字典类型
    private String dataItemTypeId;
    //字典类型名称   
    private String dataItemTypeName;
    
    public DataItemDTO(String id,String parentId,String code,Integer codeIndex,String name,String state,String dataItemTypeId,String dataItemTypeName){
    	super.setId(id);
    	super.setParentId(parentId);        
    	this.code=code;
    	this.codeIndex=codeIndex;        
    	this.name=name;               
    	this.state=state;            
    	this.dataItemTypeId=dataItemTypeId;            
    	this.dataItemTypeName=dataItemTypeName;
    }
}
