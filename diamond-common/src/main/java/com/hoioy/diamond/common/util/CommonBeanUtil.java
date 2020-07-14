package com.hoioy.diamond.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

public class CommonBeanUtil {

    public static CommonBeanUtil getInstance() {
        return CommonBeanUtilHolder.instance;
    }

    public static void saveCopy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static void updateCopy(Object source, Object target) {
        beanCopyWithoutNull(source, target);
    }

    public static String generateBeanId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
//
//    // 根据id，parentId将list结构转为tree结构
//    public <T extends BaseTreeDTO> List<T> listToTree(Collection<T> list, String rootId) {
//        List<T> treeList = new ArrayList();
//        for (T node : list) {
//            // parentID可能为null
//            if (StringUtils.isBlank(rootId) && StringUtils.isBlank(node.getParentId())) {
//                treeList.add((findChildren(node, list)));
//            } else if (node.getParentId().equals(rootId)) {
//                treeList.add((findChildren(node, list)));
//            }
//        }
//        return treeList;
//    }

    //source中的非空属性复制到target中
    public static <T> void beanCopyWithoutNull(T source, T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    //source中的非空属性复制到target中，但是忽略指定的属性，也就是说有些属性是不可修改的（个人业务需要）
    public static <T> void beanCopyWithIngore(T source, T target, String... ignoreProperties) {
        String[] pns = getNullAndIgnorePropertyNames(source, ignoreProperties);
        BeanUtils.copyProperties(source, target, pns);
    }

    public static String[] getNullAndIgnorePropertyNames(Object source, String... ignoreProperties) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        for (String s : ignoreProperties) {
            emptyNames.add(s);
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static Set<String> getNullPropertyNameSet(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames;
    }


    public static Map<String, Object> beanTomap(Object bean) throws Exception {
        Map<String, Object> map = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
        PropertyDescriptor[] list = beanInfo.getPropertyDescriptors();
        PropertyDescriptor[] var4 = list;
        int var5 = list.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            String key = pd.getName();
            Object value = pd.getReadMethod().invoke(bean);
            map.put(key, value);
        }

        return map;
    }

//    private <T extends BaseTreeDTO> T findChildren(T tree, Collection<T> list) {
//        for (T node : list) {
//            if (tree.getId().equals(node.getParentId())) {
//                if (tree.getChildren() == null) {
//                    tree.setChildren(new ArrayList());
//                }
//                tree.getChildren().add(findChildren(node, list));
//            }
//        }
//        return tree;
//    }

    private static class CommonBeanUtilHolder {
        private static CommonBeanUtil instance = new CommonBeanUtil();
    }
}
