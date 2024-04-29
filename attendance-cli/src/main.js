import Vue from 'vue'
import App from './App'
import router from './router'
import store from './vuex/store.js'

import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'

import locale from 'element-ui/lib/locale'
import lang from 'element-ui/lib/locale/lang/en'
locale.use(lang)

Vue.use(ElementUI, { size: 'small' })

Vue.config.productionTip = false
Vue.prototype.$bus = new Vue()

new Vue({
  router,
  render: h => h(App),
  store
}).$mount('#app')
