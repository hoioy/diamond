package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseTreeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends IBaseTreeRepository<Menu> {

    List<String> findIdsByMenuUrl(String menuUrl);
}
