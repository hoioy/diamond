package com.hoioy.sample.domain;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("room")
@ApiModel(value = "Room对象", description = "")
public class Room extends CommonDomain {

    private static final long serialVersionUID = 1L;
    @TableId("pk")
    private String pk;

    private String roomName;

    private String roomDesc;

    private Integer flag;

}
