import './scss/main'
import Vue from 'vue'

import VueRouter from 'vue-router'
Vue.use(VueRouter)

import vuescroll from 'vue-scroll'
Vue.use(vuescroll)
import VueSwal from 'vue-swal'

Vue.use(VueSwal)

import vSelect from 'vue-select'
Vue.component('v-select', vSelect)



import FullCalendar from 'vue-full-calendar';
Vue.use(FullCalendar);


import { Icon } from "leaflet"
delete Icon.Default.prototype._getIconUrl
Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png")
})

import BootstrapVue from 'bootstrap-vue'
Vue.use(BootstrapVue)

import { library } from '@fortawesome/fontawesome-svg-core'
import { faSearch, faMinusSquare , faBrain, faSlidersH, faCalendarAlt,faMapMarkedAlt, faCloud, faBell, faBellSlash, faExclamationCircle, faFutbol, faCommentDots, faBasketballBall} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
library.add(faSearch)
library.add(faMinusSquare)
library.add(faBrain)
library.add(faSlidersH)
library.add(faCalendarAlt)
library.add(faMapMarkedAlt)
library.add(faCloud)
library.add(faBell)
library.add(faBellSlash)
library.add(faExclamationCircle)
library.add(faFutbol)
library.add(faBasketballBall)
library.add(faCommentDots)

Vue.component('font-awesome-icon', FontAwesomeIcon)


import Notifications from 'vue-notification'
Vue.use(Notifications)

import App from './app/App'
import router from './app/AppRouter'

new Vue({
	
  render: h => h(App),
  router
}).$mount('#app')