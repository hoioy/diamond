package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("file_storage")
@ApiModel(value="FileStorage对象", description="")
public class FileStorage extends BaseDomain {

    private static final long serialVersionUID=1L;

    private Integer version;

    private String extension;

    private String fileName;

    private String relativePath;

    private Integer state;


}
