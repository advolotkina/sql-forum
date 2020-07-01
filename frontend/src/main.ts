import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import BootstrapVue from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import vuetify from '@/plugins/vuetify';
import Vuelidate from 'vuelidate';
import VueMoment from "vue-moment";

Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.use(Vuelidate);
Vue.use(VueMoment);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
