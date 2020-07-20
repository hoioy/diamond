const path = require('path')
const UglifyJsPlugin = require('uglifyjs-webpack-plugin') // 用来压缩优化js文件

// 生产环境
const isProduction = process.env.NODE_ENV === 'production'

// 引入文件
function resolve(dir) {
  // 路径可能与你的项目不同
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: isProduction ? './' : '/', // 基本路径
  outputDir: 'dist', // 输出文件目录
  lintOnSave: true, // 生产环境禁用eslint-loader
  productionSourceMap: false, // 生产环境是否生成sourceMap文件
  // 启用并行化 默认的并发数os.cpus().length -1
  parallel: require('os').cpus().length > 1,
  runtimeCompiler: true, // 关键点在这
  // webpack配置
  chainWebpack: config => {
    config.resolve.alias
      .set('@svg', resolve('./src/common/icons/svg'))
      .set('@', resolve('src/common'))
      .set('@folder-outside-request', resolve('src/common/utils'))// request的路径，为了动态配置模块中请求地址
      .set('@folder-inside-views-common', resolve('src/sys/views/common/mixins'))// request的路径，为了动态配置模块中请求地址
      .set('@folder-inside-utils', resolve('src/sys/utils'))// request的路径，为了动态配置模块中请求地址
      .set('@src', resolve('src'))
      .set('@sys', resolve('src/sys'))
    // config.resolve.alias.set('querystring', 'querystring-browser')
    config.module.rules.delete('svg') // 重点:删除默认配置中处理svg,
    // 关闭 npm run build时 webpack性能提示
    config.performance
      .set('hints', false)
    // const svgRule = config.module.rule('svg')
    // svgRule.uses.clear()
    config.module
      .rule('svg-sprite-loader')
      .test(/\.svg$/)
      .include
      .add(resolve('src/common/icons/svg')) // 处理svg目录
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
    // config.performance
    //     .maxEntrypointSize(400000)
    //     .maxAssetSize(400000)
    //   生产环境配置
    if (isProduction) {
      // 删除预加载
      config.plugins.delete('preload')
      // 开启压缩代码
      config.optimization.minimize(true)
      // 分割代码
      config.optimization.splitChunks({
        chunks: 'all'
      })
      // cdn
    }
    //   测试环境
  },
  // webpack配置
  configureWebpack: config => {
    config.entry.app = ['@babel/polyfill', './src/main.js']
    if (isProduction) {
      config.plugins.push(
        new UglifyJsPlugin({
          // 删除console warning
          uglifyOptions: {
            compress: {
              warnings: false,
              drop_debugger: true,
              drop_console: true
              // pure_funcs: ['console.log'] // 移除console
            }
          },
          sourceMap: false,
          // 使用多进程并行来提高构建速度
          parallel: true
        })
      )
    } else {
      //    测试环境
    }
  },
  // css 配置相关
  css: {
    // 是否使用css分离插件 ExtractTextPlugin 生产环境下是true,开发环境下是false
    extract: true,
    // 是否开启 CSS source maps 方便开发人员的错误定位 true打包时间大大增加
    sourceMap: false,
    // css预设器配置项
    loaderOptions: {
      // sass:{
      //     data:`
      //         @import "@/styles"
      //     `
      // }

    },
    // 启用 CSS modules for all css / pre-process.or files
    requireModuleExtension: true
  },
  devServer: {
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 9527,
    https: false,
    hotOnly: false,
    // 查阅 https://github.com/vuejs/vue-doc-zh-cn/vue-cli/cli-service.md#配置代理
    proxy: {
      '/api': {
        // 目标 API 地址  需要代理的服务器
        target: 'http://localhost:7779/',
        // 如果要代理 websocket
        ws: true,
        // 将主机标头的原点更改为目标URL 是否允许跨域
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''// 这里理解成用'/api'代替target里面的地址,比如我要调用'http://40.00.100.100:3002/user/add'，直接写'/api/user/add'即可
        }
      }
    }
  }
}
