package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataItemRepository extends IBaseRepository<DataItem, String> {
    List<DataItem> findByParentId(String parentId);
}
