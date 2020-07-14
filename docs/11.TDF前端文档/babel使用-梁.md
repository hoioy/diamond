
#### 三、 TDF babel介绍

TDF 前端是基于babel7.4.5进行配置的 具体介绍和配置如下

### 3.1 babel介绍

Babel是一个通用型的JS编译器，通过Babel我们可以把最新标准编写的JS代码向下编译成兼容各种浏览器或Node的通用版本。你可以通过安装预设（presets，一系列同类插件组合） 或 插件（plugins） 告诉Babel应该如何进行代码转译，例如：@babel/preset-env （转译 ES6 ~ ES9 的语法）、 @babel/preset-react （转译React )。
本文基于Babel 7.4.5
![](./image/3.1.png)

#### 3.3.1  @babel/core
@babel/core主要是进行代码转换的一些方法，可以将源代码根据配置转换成兼容目标环境的代码。
#### 3.3.2 @babel/cli
@babel/cli是 babel 提供的命令行工具，用于命令行下编译源代码。
#### 3.3.3 @babel/presets
preset-env是ES语法插件的合集，官方已经不再推荐使用preset-201x之类的包，该包可以通过配置自动兼容代码，包括自动引入polyfill垫片处理新的API（例如：Promise,Generator,Symbol等）以及 实例方法（例如Array.prototype.includes等）。在index.js中使用了多种es6的语法，一个个的导入插件很麻烦，presets是一组预设好的插件集合。官方为常见环境组装了一些 presets (当然也可以自己配置)：
-    @babel/preset-env
-    @babel/preset-flow
-    @babel/preset-react
-    @babel/preset-typescript
  (使用前需npm install @babel/preset-env)

#### 3.3.4 @babel/polyfill
@babel/polyfill由core-js2和regenerator-runtime组成，后者是facebook开源库，用来实现对generator、async函数等的支持，前者是js标准库，包含不同版本javascipt语法的实现。
在Babel > 7.4.0 之前，通常我们会安装 babel-polyfill 或 @babel/polyfill来处理实例方法和ES+新增的内置函数，而7.4.0之后，当我们选择安装 @babel/polyfill时，会收到如下的警告 ：
```javascript
warning @babel/polyfill@7.4.4: � As of Babel 7.4.0, this
package has been deprecated in favor of directly
including core-js/stable (to polyfill ECMAScript
features) and regenerator-runtime/runtime
(needed to use transpiled generator functions):
  > import "core-js/stable";
  > import "regenerator-runtime/runtime";
```
其实polyfill本身就是stable版本的core-js和regenerator-runtime的合集，即
`import @babel/polyfill`等同于：
```javascript
import 'core-js/stable';
import 'regenerator-runtime/runtime';
```
所以在针对Babel >= 7.4.0 的情况，我们需要安装 core-js 替代 babel-polyfill,而 regenerator-runtime 会在我们安装 @babel/runtime 时自动安装，所以不必单独安装了。
配合引入垫片polyfill的方式根据useBuiltIns的不同可以分为三种，即 entry, usage 和 false。
参考VUE CLI浏览器兼容性配置 [VUE CLI3.x浏览器兼容性配置](https://cli.vuejs.org/zh/guide/browser-compatibility.html#browserslist "VUE CLI3.x浏览器兼容性配置")
配置如下：
babel.config.js中
```javascript
module.exports = {
  presets: [
    ['@vue/app',
      {
        useBuiltIns: 'entry'
      }
      ]
  ]
}
```
在入口文件main.js最上方添加
`import '@babel/polyfill'`
经测试 能兼容到IE10 IE11
参考：
1.[vue-cli 3.x 在IE11 报错 问题](https://segmentfault.com/q/1010000016911284?_ea=4991793 "vue-cli 3.x 在IE11 报错 问题")
2.[BABEL官网](https://babeljs.io/docs/en/next/babel-preset-env.html#usebuiltins "BABEL官网")
3.[vue cli Polyfill](https://cli.vuejs.org/zh/guide/browser-compatibility.html#polyfill "vue cli Polyfill")
