//package com.hoioy.diamond;
//
//import com.hoioy.diamond.sys.domain.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//public class CacheService {
//    private static final Logger log = LoggerFactory.getLogger(CacheService.class);
////    @Autowired
////    private RoleRepository roleRepository;
////    @Autowired
////    private UserInfoRepository userInfoRepository;
////    @Autowired
////    private MenuRepository menuRepository;
//
////    //@CacheEvict(value = "role", key = "#roleId")
////    @Transactional(propagation = Propagation.REQUIRED)
////    public void deleteRoleByID(String roleId) {
////        // 标记为已删除-0,未删除-1
////        this.roleRepository.update(roleId);
////    }
//
////    //@CacheEvict(value = "userDto", key = "#p0.id")//todo
////    @Transactional(propagation = Propagation.REQUIRED)
////    public void deleteUser(UserInfo user) {
////        try {
////            this.userInfoRepository.updateFlag(user.getId());
////        } catch (Exception e) {
////            log.error("deleteUser:{}", e);
////        }
////    }
//
//    /**
//     * @param userId
//     * @return UserInfo
//     * @throws @author chixue
//     * @Description: 按用户Id查询用户信息
//     * @date 2016年5月11日
//     */
//    //@Cacheable(value = "userDto", key = "#p0")
//    @Transactional(propagation = Propagation.SUPPORTS)
//    public UserInfo findUserById(String userId) {
//        Optional<UserInfo> user = userInfoRepository.findById(userId);
//        if (user.isPresent()) {
//            return user.get();
//        }
//        return null;
//    }
//
//    /**
//     * 根据ID查询角色
//     *
//     * @param id
//     * @return
//     */
//    //@Cacheable(value = "roleDto", key = "#p0")
//    @Transactional(propagation = Propagation.SUPPORTS)
//    public Role findRoleById(String id) {
//        Optional<Role> role = this.roleRepository.findById(id);
//        if (role.isPresent()) {
//            return role.get();
//        }
//        return null;
//    }
//
//    /**
//     * 根据ID查询菜单
//     *
//     * @param id
//     * @return
//     */
//    //@Cacheable(value = "menuDto", key = "#p0")
//    @Transactional(propagation = Propagation.SUPPORTS)
//    public Menu findMenuById(String id) {
//        Optional<Menu> menu = this.menuRepository.findById(id);
//        if (menu.isPresent()) {
//            return menu.get();
//        }
//        return null;
//    }
//}
