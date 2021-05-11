package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.service.AbstractCommonServiceImpl;
import com.hoioy.diamond.common.service.ICommonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.PostConstruct;

public abstract class CommonServiceImpl<I extends ICommonDaoRepository<D>, D extends CommonDomain, DTO extends CommonDTO>
        extends AbstractCommonServiceImpl<I, D, DTO> implements ICommonService<DTO, D> {

    protected class MybatisPlusBaseServiceImpl extends ServiceImpl<I, D> {
        public void baseInit(I m){
            this.baseMapper = m;
            this.entityClass = domainClass;
            this.mapperClass = plusMapperClass;
        }
    }

    public MybatisPlusBaseServiceImpl mybatisPlusServiceImpl;

    @PostConstruct
    public void initMybatisPlusBaseServiceImpl() {
        mybatisPlusServiceImpl = new MybatisPlusBaseServiceImpl();
        mybatisPlusServiceImpl.baseInit(iBaseRepository);
    }

    /**
     * Mybatis的默认单表分页，不做封装
     * 需要分页的业务逻辑可以Override此方法
     * @param pageDTO
     * @return
     */
    @Override
    public PageDTO<DTO> getPage(PageDTO<DTO> pageDTO) {
        return null;
    }

}
