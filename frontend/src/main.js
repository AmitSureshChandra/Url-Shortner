import Vue from 'vue'
import App from './App.vue'
import vuetify from '@/plugins/vuetify' // path to vuetify export
import axios from "axios"
import VueSweetalert2 from 'vue-sweetalert2';

Vue.config.productionTip = false

console.log(process.env.VUE_APP_API_URL)

// axios.defaults.baseURL = "https://fitness-app-00029.herokuapp.com";

axios.defaults.baseURL = process.env.VUE_APP_API_URL;

Vue.use(VueSweetalert2);
import 'sweetalert2/dist/sweetalert2.min.css';

new Vue({
  vuetify,
  render: h => h(App),
}).$mount('#app')
