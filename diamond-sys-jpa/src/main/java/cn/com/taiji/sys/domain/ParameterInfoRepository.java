package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseRepository;
import com.hoioy.diamond.sys.dto.ParameterInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单表dao
 */
@Repository
public interface ParameterInfoRepository extends IBaseRepository<ParameterInfo, String> {

    @Query("select k.id from ParameterInfo k where k.parameterKey in (:parameterKeys)")
    List<String> findIdsByParameterKeys(@Param("parameterKeys") List<String> parameterKeys);
}
