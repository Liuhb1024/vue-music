import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
// import global from './mixins/global'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'

Vue.config.productionTip = false
Vue.use(ElementUI)
// Vue.mixin(global)

new Vue({
  render: h => h(App),
  router,
  store,
  beforeCreate() {
    Vue.prototype.$bus = this // 定义中央事件总线
  }
}).$mount('#app')
