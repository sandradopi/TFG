import VueRouter from 'vue-router'
import Vue from 'vue'
import auth from './common/auth'
import { Home, Login, NotFound,Registro } from './components'
import {CommentGame,Recomendations,TennisResult,FutbolResult,TennisForm,ValorationGame,Calendar,Weather,Mapa,FutbolForm,UserDetail,Game,UserList,Deportes,DeportesDetail,DeportesForm, LocalizacionesDetail, GameCreate,GameDetail,GameUser,GameList} from './entities'

const routes = [
  { name: 'Home', path: '/', component: Home, meta: { public: true } },
  { name: 'Registro', path: '/login/createAccount', component: Registro,meta: { public: true }},
  { name: 'Login', path: '/login', component: Login, meta: { public: true, isLoginPage: true }},
  { name: 'UserDetail', path: '/users/:id', component: UserDetail,meta: { authority: 'USER' }},
  { name: 'UserUpdate', path: '/users/:id/edit', component: Registro,meta: { authority: 'USER' }},
  { name: 'UserList', path: '/users', component: UserList,meta: { authority: 'ADMIN' }},
  { name: 'LocalizacionesDetail', path: '/localizaciones/details', component: LocalizacionesDetail,meta: { authority: 'ADMIN' }},
  { name: 'Mapa', path: '/localizaciones/mapa', component: Mapa,meta: { authority: 'ADMIN' }},
  { name: 'Deportes', path: '/deportes', component: Deportes,meta: { authority: 'ADMIN' }},
  { name: 'DeportesDetail', path: '/deportes/:id', component: DeportesDetail,meta: { authority: 'ADMIN' }},
  { name: 'DeportesCreate', path: '/deportes/new', component: DeportesForm,meta: { authority: 'ADMIN' }},
  { name: 'DeportesUpdate', path: '/deportes/:id/editar', component: DeportesForm,meta: { authority: 'ADMIN' }},
  { name: 'GameCreate', path: '/games/new', component: GameCreate,meta: { authority: 'USER' }},
  { name: 'GameDetail', path: '/games/:id', component: GameDetail,meta: { authority: 'USER' }},
  { name: 'CommentGame', path: '/games/:id/comments', component: CommentGame,meta: { authority: 'USER' }},
  { name: 'ValorationGame', path: '/games/:id/valoraciones', component: ValorationGame,meta: { authority: 'USER' }},
  { name: 'FutbolForm', path: '/games/:id/futbol/completeResultado', component: FutbolForm,meta: { authority: 'USER' }},
  { name: 'TennisForm', path: '/games/:id/tennis/completeResultado', component: TennisForm,meta: { authority: 'USER' }},
  { name: 'FutbolResult', path: '/games/:id/futbol/resultado', component: FutbolResult,meta: { authority: 'USER' }},
  { name: 'TennisResult', path: '/games/:id/tennis/resultado', component: TennisResult,meta: { authority: 'USER' }},
  { name: 'GameUser', path: '/users/:id/perfilPublico', component: GameUser,meta: { authority: 'USER' }},
  { name: 'GameList', path: '/games/organizados/user/:id', component: GameList,meta: { authority: 'USER' }},
  { name: 'Recomendations', path: '/games/recomendados', component: Recomendations,meta: { authority: 'USER' }},
  { name: 'Game', path: '/games', component: Game,meta: { authority: 'USER' }},
  { name: 'Weather', path: '/games/weather', component: Weather,meta: { authority: 'USER' }},
  { name: 'Calendar', path: '/games/calendar', component: Calendar,meta: { authority: 'USER' }},
  { path: '*', component: NotFound, meta: { public: true }},

]

const router = new VueRouter({
   routes
 })

 router.beforeEach((to, from, next) => {
  auth.isAuthenticationChecked.finally(() => {
    // por defecto las rutas restringen el acceso a usuario autenticados
    const requiresAuth = !to.meta.public

    const requiredAuthority = to.meta.authority
    const userIsLogged = auth.user.logged
    const loggedUserAuthority = auth.user.authority

    if (requiresAuth) {
      if (userIsLogged) {
        if (requiredAuthority && requiredAuthority != loggedUserAuthority) {
          // usuario logueado pero sin permisos
          alert('Access is not allowed for the current user. Try to log again.')
          auth.logout().then(() => next('/login'))
        } else {
          // usuario logueado y con permisos adecuados
          next()
        }
      } else { // usuario no está logueado
        alert('This page requires authentication.')
        next('/login')
      }
    } else { // página pública
      if (userIsLogged && to.meta.isLoginPage) {
        // si estamos logueados no hace falta volver a mostrar el login
        next({ name: 'Home', replace: true })
      } else {
        next()
      }
    }
  })
})


 export default router