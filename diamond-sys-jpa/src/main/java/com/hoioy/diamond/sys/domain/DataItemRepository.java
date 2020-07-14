package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseTreeRepository;
import com.hoioy.diamond.sys.dto.DataItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataItemRepository extends IBaseTreeRepository<DataItem> {
	String findDataItemPageableJPQL = "from DataItem t left join DataItemType s on t.dataItemTypeId=s.id  "
			+ "where (:#{#dataItemDTO.dataItemTypeId} is null  or t.dataItemTypeId = :#{#dataItemDTO.dataItemTypeId})";

	@Query(value = "select new com.hoioy.diamond.sys.dto.DataItemDTO(t.id,t.parentId,t.code,t.codeIndex,t.name,t.state,t.dataItemTypeId,s.typeName)"
			+ findDataItemPageableJPQL, countQuery = "select count(t.id) " + findDataItemPageableJPQL)
	Page<DataItemDTO> findDataItemPageable(@Param("dataItemDTO") DataItemDTO dataItemDTO, Pageable pageable);
}
