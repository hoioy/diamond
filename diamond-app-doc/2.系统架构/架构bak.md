本文档不对外发布，草稿

### 专注在高效率领域
1. 创造生态，给其他上层产品提供生态空间
    1. 基于diamond的lowcode
    1. 基于diamond的大数据
    1. 基于diamond的其他CBB
 
    
### 运行时架构


### 后端架构


#### 扩展性
扩展模型：利用java多态
1. Diamond Common审计字段createdBy动态获取当前操作人
1. 扩展数据表字段：如给用户表增加字段
1. ID生成扩展方法
1. 审计字段自动赋值
1. RBAC扩展鉴权模型扩展

#### diamond-common的CommonUserDTO
按照设计思路，为了实现Diamond Common模块的最大的通用性，Diamond Common应该是从技术角度的进行抽象和封装，
不应该对项目的业务逻辑进行抽象和干扰。但是因为“人（用户）”这个实体对业务系统的独一无二的重要性，
Diamond Common中抽象了唯一的业务逻辑对象：CommonUserDTO，泛化理解为“人",也可以抽象表示用户、管理员等等
任何使用软件的主体。比如假如实际项目是一个物联网系统，那么每个物联网设备使用了系统，因此CommonUserDTO可以表示为物联网设备.

注意 CommonUserDTO 可以表示任何实体，是从软件的技术和业务角度来说，不是从现实世界的角度来说，
比如一个现实世界的人A可以再系统中有多个身份，那么就对应多个CommonUserDTO记录即可，
也就是多条id不同的CommonUserDTO记录其实背后都是这个现实世界的用户A。

对应的，需要一个获取当前用户的Service。默认实现的Service是从Spring Security框架中获取，用户
如果没有使用Spring Security框架，那么用户需要重写，比如从session中获取当前用户等等。


#### 模块拆分
1. diamond-common中是主要核心，diamond-common-jpa只需要依赖diamond-common实现jpa特有，
diamond-common-mybatis类似
1. diamond-log依赖diamond-common,而不依赖具体的diamond-common(jpa/mybatis),
而是在diamond-log-jpa具体依赖diamond-common-jpa
1. diamond-sys-web只依赖diamond-sys，接口，没有具体实现diamond-sys-jpa(mybatis),
也就是diamond-sys-web中的所有逻辑都是基于diamond-sys的接口的，要想实际工作。需要在
入口工程，如diamond模块中，依赖具体的diamond-sys-jpa(mybatis),这样做是为了使得diamond-sys-web通用
不必再准备diamond-sys-web-jpa(mybatis)了只需要diamond-sys-web模块即可。


#### maven构建配置
1. diamond-bom直接做为parent，diamond-common管理公共依赖


#### gradle工程构建配置
1. gradle中的多项目构建（相对于maven中的多模块构建）
1. setting.gradle用来控制多项目
1.