package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseTreeDTO;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 树型业务表基础service
 */
public interface IBaseTreeService<DTO extends BaseTreeDTO, D extends CommonDomain> extends IBaseService<DTO, D> {

    @Transactional(rollbackFor = Exception.class)
    Boolean move(String currentParentId, String targetParentId, String currentId);

    /**
     * 根据父节点id查找所有childrent 如果传空就查所有一级,儿子只能查询一级
     * @param parentId
     * @return
     */
    List<DTO> findByParentId(String parentId);

    /**
     * 根据父节点id查询所有儿子
     * @param parentId 父节点id
     * @return
     */
    List<DTO> findChildrenByParentId(String parentId);

    /**
     * 根据id，parentId将list结构转为tree结构
     * @param list 列表
     * @param rootNodeParentId 如果parentId为rootNodeParentId，那么把这个节点作为根节点
     * @param <T>
     * @return
     */
    default <T extends BaseTreeDTO> List<T> listToTree(Collection<T> list, String rootNodeParentId) {
        List<T> treeList = new ArrayList();
        for (T node : list) {
            // parentID可能为null
            if (StrUtil.isBlank(rootNodeParentId) && StrUtil.isBlank(node.getParentId())) {
                treeList.add((findChildren(node, list)));
            } else if (node.getParentId().equals(rootNodeParentId)) {
                treeList.add((findChildren(node, list)));
            }
        }
        return treeList;
    }

    default <T extends BaseTreeDTO> T findChildren(T tree, Collection<T> list) {
        for (T node : list) {
            if (tree.getId().equals(node.getParentId())) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList());
                }
                tree.getChildren().add(findChildren(node, list));
            }
        }
        return tree;
    }

    default String createPath(DTO dto) {
        String parentId = dto.getParentId();
        if (StrUtil.isBlank(parentId)) {
            return dto.getId();
        } else {
            DTO parent = findById(parentId);
            String parentPath = parent.getPath();
            return parentPath + "," + dto.getId();
        }
    }
}
