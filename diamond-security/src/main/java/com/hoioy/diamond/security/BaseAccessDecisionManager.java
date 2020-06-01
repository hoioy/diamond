package com.hoioy.diamond.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;


/**
 * AccessdecisionManager在Spring security中是很重要的。
 * <p>
 * 在验证部分简略提过了，所有的Authentication实现需要保存在一个GrantedAuthority对象数组中。
 * 这就是赋予给主体的权限。 GrantedAuthority对象通过AuthenticationManager
 * 保存到 Authentication对象里，然后从AccessDecisionManager读出来，进行授权判断。
 * <p>
 * Spring Security提供了一些拦截器，来控制对安全对象的访问权限，例如方法调用或web请求。
 * 一个是否允许执行调用的预调用决定，是由AccessDecisionManager实现的。
 * 这个 AccessDecisionManager 被AbstractSecurityInterceptor调用，
 * 它用来作最终访问控制的决定。 这个AccessDecisionManager接口包含三个方法：
 * <p>
 * void decide(Authentication authentication, Object secureObject,
 * List<ConfigAttributeDefinition> config) throws AccessDeniedException;
 * boolean supports(ConfigAttribute attribute);
 * boolean supports(Class clazz);
 * <p>
 * 从第一个方法可以看出来，AccessDecisionManager使用方法参数传递所有信息，这好像在认证评估时进行决定。
 * 特别是，在真实的安全方法期望调用的时候，传递安全Object启用那些参数。
 * 比如，让我们假设安全对象是一个MethodInvocation。
 * 很容易为任何Customer参数查询MethodInvocation，
 * 然后在AccessDecisionManager里实现一些有序的安全逻辑，来确认主体是否允许在那个客户上操作。
 * 如果访问被拒绝，实现将抛出一个AccessDeniedException异常。
 * <p>
 * <p>
 * 这个 supports(ConfigAttribute) 方法在启动的时候被
 * AbstractSecurityInterceptor调用，来决定AccessDecisionManager
 * 是否可以执行传递ConfigAttribute。
 * supports(Class)方法被安全拦截器实现调用，
 * 包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型。
 */

//security提供了三种策略，分别对应那个策略类： 
//UnanimousBased.java 只要有一个Voter不能完全通过权限要求，就禁止访问。 
//AffirmativeBased.java只要有一个Voter可以通过权限要求，就可以访问。 
//ConsensusBased.java只要通过的Voter比禁止的Voter数目多就可以访问了
//在此说一点，ConsensusBased这个类有点特别，如果通过的票数与禁止的票数相同怎么办？ 
//这个类有个allowIfEqualGrantedDeniedDecisions属性，默认为true，关键代码：
public class BaseAccessDecisionManager implements AccessDecisionManager {
    /**
     * Resolves an access control decision for the passed parameters.
     * 解决了通过参数访问控制决策。
     *
     * @param authentication   the caller invoking the method (not null)
     * @param obj              the secured object being called
     * @param configAttributes 配置属性与担保对象调用
     * @throws AccessDeniedException if access is denied as the authentication does not hold a required authority or
     *                               accessdeniedexception如果访问被拒绝作为认证没有所需的权限或
     * @param认证调用者调用方法（非空）
     * @param担保对象的对象被称为
     */

    public void decide(Authentication authentication, Object obj,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        //所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> it = configAttributes.iterator();
        while (it.hasNext()) {
            ConfigAttribute configAttribute = it.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
            // 用户所拥有的权限authentication
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (ga.getAuthority().equals(needPermission)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(" 没有权限访问！ ");
    }


    /**
     * 启动时候被AbstractSecurityInterceptor调用，决定AccessDecisionManager是否可以执行传递ConfigAttribute。
     */
    public boolean supports(ConfigAttribute configAttribute) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型
     */
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return true;
    }


}