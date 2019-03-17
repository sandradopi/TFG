<template>
  <div>
  <b-navbar toggleable="md" variant="info">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle target>
      <b-navbar-brand class="hello" to="/">PLAY2GETHER</b-navbar-brand>
      <b-collapse id="nav_collapse" is-nav>

          <b-navbar-nav>
            <b-nav-item
                 v-if="isAdmin"
                :to="{ name: 'Deportes' }"
                exact>Deportes</b-nav-item> 

            <b-nav-item
                 v-if="isAdmin"
                :to="{ name: 'Mapa' }"
                exact>Localizaciones</b-nav-item> 

            <b-nav-item
                 v-if="isAdmin"
                :to="{ name: 'UserList' }"
                exact>Usuarios</b-nav-item> 
         </b-navbar-nav>
          

          <b-navbar-nav class="ml-auto" >
            <b-nav-item
                v-if="!isLogged"
                :to="{ name: 'Login' }"
                exact>Login</b-nav-item>

            <b-nav-item-dropdown v-if="isLogged || isAdmin" text="Conectado" variant="primary" class="m-2"> 
              <b-dropdown-item disabled> {{loggedUser}}</b-dropdown-item>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item exact v-if="isLogged && !isAdmin"  :to="{ name: 'GameCreate' , params: { id: WhatLogin}} ">Crear Evento</b-dropdown-item>
              <b-dropdown-item exact v-if="isLogged && !isAdmin"  :to="{ name: 'GameUser' , params: { id: WhatLogin}} ">Perfil Público</b-dropdown-item>
                <b-dropdown-item exact v-if="isLogged && !isAdmin"  :to="{ name: 'UserDetail' , params: { id: WhatLogin}} ">Datos Personales</b-dropdown-item>
                <b-dropdown-divider></b-dropdown-divider>
                <b-dropdown-item exact v-if="isLogged" @click="logout()" :to="{ name: ''}">Cerrar Sesión</b-dropdown-item>
            </b-nav-item-dropdown>
         </b-navbar-nav>


       </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import auth from '../common/auth'
export default {

  computed: {
    isLogged() {
      return auth.user.logged
    },
    WhatLogin() {
      return auth.user.login
    },
    loggedUser() {
      return auth.user.logged ? `${auth.user.login} (${auth.user.authority})` : 'not logged'
    },
    isAdmin() {
       return auth.isAdmin()
     }
  },
  methods: {
    logout() {
      auth.logout()
      this.$router.push({ name: 'Home' })
    }
  }
}
</script>
<style scoped lang="scss">
.hello{
  font-size: 25px;
  text-align: center;
  text-transform: uppercase;
}
 li.nav-item a {
  color:black;
  font-size:20px;
}
ul.navbar-nav {
  color:white;
  font-size:18px;
}
.navbar-light .navbar-nav .show > .nav-link, .navbar-light .navbar-nav .active > .nav-link, .navbar-light .navbar-nav .nav-link.show, .navbar-light .navbar-nav .nav-link.active{
  color:white;
}
.navbar-light .navbar-nav .nav-link:hover, .navbar-light .navbar-nav .nav-link:focus {
   color:white;
}
.imagen{
   width:10%;
   margin-left:20px;
   margin-top:20px;
}
.ml-auto{
   margin-right:60px;
}


</style>