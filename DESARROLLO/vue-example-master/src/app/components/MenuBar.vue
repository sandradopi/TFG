<template>
  <b-navbar
    toggleable="md">
    <b-navbar-toggle target="nav_collapse"/>

    <b-navbar-brand 
      class="hello"
      to="/">PLAY2GETHER
    </b-navbar-brand>

    <b-collapse
      id="nav_collapse"
      is-nav>

      <b-navbar-nav>
        
        <b-nav-item
          v-if="!isLogged"
          :to="{ name: 'Login' }"
          exact>Login</b-nav-item>
      
        
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-item
         v-if="isLogged && isAdmin" >
        {{ loggedUser }} </b-nav-item>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto1">
      <b-nav-item
       v-if="!isLogged">
        {{ loggedUser }} </b-nav-item>

      </b-navbar-nav>


    </b-collapse>
  </b-navbar>
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
  font-size: 20px;
  text-align: center;
  text-transform: uppercase;
  
}

ul.navbar-nav li.nav-item a {
  font-size:19px;
  color:black;

}

.navbar-light .navbar-nav .show > .nav-link, .navbar-light .navbar-nav .active > .nav-link, .navbar-light .navbar-nav .nav-link.show, .navbar-light .navbar-nav .nav-link.active{
  color:black;
}

.navbar-light .navbar-nav .nav-link:hover, .navbar-light .navbar-nav .nav-link:focus {
   color:black;
}


</style>