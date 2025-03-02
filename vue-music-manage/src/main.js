import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'
import VCharts from 'v-charts'
import 'v-charts/lib/style.css'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(VCharts)

new Vue({
  render: h => h(App),
  router,
  store,
  beforeCreate() {
    Vue.prototype.$bus = this // 定义中央事件总线
  }
}).$mount('#app')