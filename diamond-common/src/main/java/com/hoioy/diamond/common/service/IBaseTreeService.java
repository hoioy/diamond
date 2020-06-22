package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseTreeDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 基础service，要求其他service继承
 */
public interface IBaseTreeService<DTO extends BaseTreeDTO, D extends CommonDomain> extends IBaseService<DTO, D> {

    /**
     * 根据parentId查找所有childId 如果传空就查所有一级
     */
    List<DTO> findByParentId(String parentId);

    /**
     * 根据id，parentId将list结构转为tree结构
     * @param list 列表
     * @param rootNodeParentId 如果parentId为rootNodeParentId，那么把这个节点作为根节点
     * @param <T>
     * @return 树结构
     */
    default <T extends BaseTreeDTO> List<T> listToTree(Collection<T> list, String rootNodeParentId) {
        List<T> treeList = new ArrayList();
        for (T node : list) {
            // parentID可能为null
            if (StringUtils.isEmpty(rootNodeParentId) && StringUtils.isEmpty(node.getParentId())) {
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
}
