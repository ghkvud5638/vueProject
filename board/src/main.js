import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueResource from 'vue-resource'

import BootstrapVue from "bootstrap-vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

import {store} from './store'



Vue.config.productionTip = false
Vue.use(BootstrapVue);
Vue.use(VueResource)

Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
