import Mock from 'mockjs'
// import { param2Obj } from '@/utils'

const admin = {
  token: 'admin',
  roles: ['admin'],
  user: {
    id: '90a127ce319d5d93b3b49c697cfa1382',
    name: 'ADMIN',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'
  }
}

const simple = {
  token: 'simple',
  roles: ['simple'],
  user: {
    id: '90a127ce319d5d93b3b49c697cfa1381',
    name: 'simple',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'
  }
}

let captcha = null

export default {
  captcha: config => {
    captcha = Mock.mock({ 'number|1000-9999': 1000 }).number
    return {
      code: 200,
      message: '操作成功',
      data: {
        captchaCode: captcha,
        captchaKey: '9f1c9fd111f84cadb2ef08d1c6cf6860'
      }
    }
  },
  auth: config => {
    return {
      code: 200,
      message: '操作成功',
      data: 'BaseJwtToken80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E517B72FA53BFDDA282EC3D4C5A24DEC07306A286E1E477C40CF9275873CF4DFC0F951668D3468DF6C795112ECFE93A9BBCDF1EA4B81E41CA72405E5FDBB78DE67530FFDCB9EEA9AFE1847C44003D890AD578CFDAFFE1299853139A770272FB9721443B78C4DC0911D6602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB35392668C8FF749D3B6DF63F9CF639AF8BF0AFA432ED2D533AF2B780E197AD8FC18A677458F53B182535BC2EBFCA81D2A82E13E'
    }
  },
  userdetails: config => {
    return {
      code: 200,
      message: '操作成功',
      data: JSON.parse('{"id":"4","createdBy":null,"createdDate":"2019-08-07T13:30:16","modifiedBy":"admin","modifiedDate":"2020-07-01T16:00:37.649","flag":1,"remark":"","token":null,"loginName":"admin","password":null,"userName":"管理员","phoneNum":"","state":"1","userIndex":1,"email":"diamond@qq.com","nickname":"nickname","gender":"0","address":"","blog":null,"tag":"","avatar":"file/img/2.png","idNumber":"","birthday":"2012-06-15 14:45","integral":null,"avatarContent":null,"roleId":"5b66ecf45d634159a08468898b1b3217","deptId":null}')
    }
  },
  tree: config => {
    const params = JSON.parse('{"router":[{"id":"be87d0711de047efaffeef3c123b7f30","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":null,"children":[{"id":"be87d0711de047efaffeef3c123b7f00","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"be87d0711de047efaffeef3c123b7f30","children":null,"orderIndex":null,"path":"/index","component":"diamond/views/dashboard/index","hidden":false,"alwaysShow":true,"name":"be87d0711de047efaffeef3c123b7f00","meta":{"index":"0","roles":[],"title":"Diamond","icon":"index","cacheAble":false},"index":0}],"orderIndex":null,"path":"","component":"Layout","hidden":null,"alwaysShow":null,"name":null,"meta":null,"index":0},{"id":"90a127ce319d5d93b3b49c697cfa138f","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":null,"children":[{"id":"3de22ff390ab5d06bafcce547ff780bb","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"90a127ce319d5d93b3b49c697cfa138f","children":null,"orderIndex":null,"path":"group","component":"diamond/views/system-management/group/main","hidden":false,"alwaysShow":true,"name":"3de22ff390ab5d06bafcce547ff780bb","meta":{"index":"1","roles":[],"title":"用户组管理","icon":"user-group","cacheAble":false},"index":1},{"id":"8bdc5038a6585fd2b5d3ef7b1e4bf4e1","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"90a127ce319d5d93b3b49c697cfa138f","children":null,"orderIndex":null,"path":"user","component":"diamond/views/system-management/user/main","hidden":false,"alwaysShow":true,"name":"8bdc5038a6585fd2b5d3ef7b1e4bf4e1","meta":{"index":"2","roles":[],"title":"用户管理","icon":"user","cacheAble":false},"index":2},{"id":"d2249f50a3235286b173663a6c45122d","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"90a127ce319d5d93b3b49c697cfa138f","children":null,"orderIndex":null,"path":"role","component":"diamond/views/system-management/role/main","hidden":false,"alwaysShow":true,"name":"d2249f50a3235286b173663a6c45122d","meta":{"index":"3","roles":[],"title":"角色管理","icon":"user-role","cacheAble":false},"index":3},{"id":"323c76618c6b56109bd490baf0d00902","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"90a127ce319d5d93b3b49c697cfa138f","children":null,"orderIndex":null,"path":"dept","component":"diamond/views/system-management/dept/main","hidden":false,"alwaysShow":true,"name":"323c76618c6b56109bd490baf0d00902","meta":{"index":"4","roles":[],"title":"机构管理","icon":"dept","cacheAble":false},"index":4},{"id":"b837b164e8f1443b9a5dae27c8611a06","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"90a127ce319d5d93b3b49c697cfa138f","children":null,"orderIndex":null,"path":"log","component":"diamond/views/system-management/logs/main","hidden":false,"alwaysShow":true,"name":"b837b164e8f1443b9a5dae27c8611a06","meta":{"index":"5","roles":[],"title":"业务日志","icon":"logs","cacheAble":false},"index":5}],"orderIndex":null,"path":"/system","component":"Layout","hidden":false,"alwaysShow":true,"name":"90a127ce319d5d93b3b49c697cfa138f","meta":{"index":"1","roles":[],"title":"系统管理","icon":"setting","cacheAble":false},"index":1},{"id":"da4c2695c36a50eab041735842b530e7","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":null,"children":[{"id":"cb69ec0627bc40669534a85157027eeb","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"da4c2695c36a50eab041735842b530e7","children":null,"orderIndex":null,"path":"data-item-type","component":"diamond/views/system-management/dictionary-type/main","hidden":false,"alwaysShow":true,"name":"cb69ec0627bc40669534a85157027eeb","meta":{"index":"3","roles":[],"title":"数据字典类型","icon":"setting","cacheAble":false},"index":3},{"id":"6ecb9fd6e70f50b2be035485928b5cd6","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"da4c2695c36a50eab041735842b530e7","children":null,"orderIndex":null,"path":"menu","component":"diamond/views/system-management/menu/main","hidden":false,"alwaysShow":true,"name":"6ecb9fd6e70f50b2be035485928b5cd6","meta":{"index":"7","roles":[],"title":"菜单管理","icon":"auth-menu","cacheAble":false},"index":7},{"id":"d9369152df124e1aae855f743d3eedc8","createdBy":null,"createdDate":null,"modifiedBy":null,"modifiedDate":null,"flag":null,"remark":null,"token":null,"parentId":"da4c2695c36a50eab041735842b530e7","children":null,"orderIndex":null,"path":"dictionary-multi","component":"diamond/views/system-management/dictionary/multiMain","hidden":false,"alwaysShow":true,"name":"d9369152df124e1aae855f743d3eedc8","meta":{"index":"9","roles":[],"title":"数据字典","icon":"dictionary-multi","cacheAble":false},"index":9}],"orderIndex":null,"path":"/dictionary-setting","component":"Layout","hidden":false,"alwaysShow":true,"name":"da4c2695c36a50eab041735842b530e7","meta":{"index":"2","roles":[],"title":"资源管理","icon":"dictionary-setting","cacheAble":false},"index":2}]}')
    return {
      code: 200,
      message: '操作成功',
      data: params
    }
  },
  hasPermission: config => {
    const params = JSON.parse('["user/*","role/*","dept/*","menu/*","group/*","log/*"]')
    return {
      code: 200,
      message: '操作成功',
      data: params
    }
  },
  loginByUsername: config => {
    const params = JSON.parse(config.body)
    let token = null
    if ('' + captcha !== '' + params.captcha) {
      return {
        code: 1,
        data: {
          result: 2,
          message: '验证码错误'
        }
      }
    }
    if (params.username === 'admin' && params.password !== 'admin') {
      return {
        code: 1,
        data: {
          result: 3,
          message: '用户名或密码错误'
        }
      }
    }
    if (params.username === 'admin') {
      token = admin.token
    } else {
      token = simple.token
    }
    return {
      code: 1,
      data: {
        result: 1,
        message: '登录成功',
        token: token
      }
    }
  },

  getRouterRoles: (config) => {
    const routerRoles = new Map()
    routerRoles.set('90a127ce319d5d93b3b49c697cfa138f', ['simple'])
    routerRoles.set('323c76618c6b56109bd490baf0d00902', ['simple'])
    routerRoles.set('f33d83225bef590d81f61a5afcbbca14', ['simple', 'others'])
    routerRoles.set('3de22ff390ab5d06bafcce547ff780bb', ['simple', 'others'])
    routerRoles.set('8bdc5038a6585fd2b5d3ef7b1e4bf4e1', ['others'])
    return {
      code: 1,
      data: routerRoles
    }
  },
  logout:
        (config) => {
          return {
            code: 1
          }
        }
}
