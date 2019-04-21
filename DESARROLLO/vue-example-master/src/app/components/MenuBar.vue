<template>
  <div>
  <b-navbar toggleable="md" variant="info">
  <b-modal
        id="modalPrevent2"
        ref="modal2"
        class="formulario"
        :title=this.titulo
        ok-only ok-variant="secondary" 
        ok-title="Cancelar"
        @ok="handleOk"
        @shown="clearName">

        <form @submit.stop.prevent="handleSubmit">
          <b-form-group>
             <div class="informationmessage2">
             <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
             <div class="w3-container" v-for=" game in this.games" :key="game.idGame">
             <b-btn class="w3-bar" @click="confirmacion(game)">
               <img v-if="game.sport.type=='Futbol'"src="../entities/user/futbol.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
               <img v-if="game.sport.type=='Tennis'"src="../entities/user/ten.jpg" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
               <img v-if="game.sport.type=='Paddel'"src="../entities/user/paddel.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
               <img v-if="game.sport.type=='Baloncesto'"src="../entities/user/bal1.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
              <div class="w3-bar-item">
                <span class="w3-large">{{game.location.name}}</span><br>
                <span>{{game.date}} ({{custom(game.timeStart)}}-{{custom(game.timeEnd)}})</span>
              </div>
          </b-btn>
    </div>
  </div>
          </b-form-group>
        </form>
      </b-modal>

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
                 v-if="!isAdmin"
                :to="{ name: 'Game' }"
                exact>Partidos</b-nav-item> 

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
             <b-btn class="button" v-b-modal.modalPrevent2 @click="controlGamesModal(true)" v-if="isLogged && !isAdmin && this.gamesResult.length>0"><div>{{this.gamesResult.length}}</div><font-awesome-icon icon="exclamation-circle"style="font-size:35px;"/></b-btn>
              <b-btn class="button" v-b-modal.modalPrevent2  @click="controlGamesModal(false)"v-if="isLogged && !isAdmin && this.gamesValor.length>0"><div>{{this.gamesValor.length}}</div><font-awesome-icon icon="bell"style="font-size:35px;"/></b-btn>
            
            
         </b-navbar-nav>
       </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import auth from '../common/auth'
import { HTTP } from '../common/http-common'
export default {
data() {
    return {
      games:[],
      gamesResult:[],
      gamesValor:[],
      control:false,
      bol:false,
      titulo:''

     
    }
  },
  watch: {
    '$route': 'fetchData',

    
  },
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
  created(){
    this.fetchData();
  },
  methods: {
    logout() {
      auth.logout()
      this.$router.push({ name: 'Home' })
    },
    fetchData(){
      if(this.isLogged){
       HTTP.get(`users/${this.WhatLogin1()}/pendingResult`) 
                .then(response => { this.gamesResult= response.data
                      return response })
                .then(this.pendingValoration)
                .catch(err => { this.error = err.message})
              }
    },

    pendingValoration(){
       HTTP.get(`users/${this.WhatLogin1()}/valoration`) 
                .then(response => { this.gamesValor= response.data
                      return response })
                .catch(err => { this.error = err.message})
    },

    controlGamesModal(bol){
      if(bol==false){
        this.games=this.gamesValor;
        this.bol=false;
        this.titulo="Pendientes de Valorar"
      }else{
        this.games=this.gamesResult;
        this.bol=true;
        this.titulo="Rellenar resultado finales"
      }
    },
 
    WhatLogin1() {
      return auth.user.login
    },
     clearName() {
        
      },
      handleOk(evt) {
      
      },
     confirmacion(game){

        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
        this.$refs.modal2.hide();

      })
        if(this.bol==true){
          if(game.sport.type=="Futbol"|| game.sport.type=='Baloncesto'){
          this.$router.replace({ name: 'FutbolForm', params: { id:game.idGame}})
        }else if(game.sport.type=="Tennis"|| game.sport.type=='Paddel'){
          this.$router.replace({ name: 'TennisForm', params: { id:game.idGame}})
        }
     }else{
          this.$router.replace({ name: 'ValorationGame', params: { id:game.idGame}})
     }

       
    }, 
      custom(hora){
      return hora.substring(0,5)
    },
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


.button{
   background-color:#17a2b8;
   border:none;
}

.navbar-light .navbar-nav .show > .nav-link, .navbar-light .navbar-nav .active > .nav-link, .navbar-light .navbar-nav .nav-link.show, .navbar-light .navbar-nav .nav-link.active {
    color: rgba(0, 0, 0, 0.9);
    margin-top: 20px;
}
.ml-auto{
   margin-right:60px;
}

fieldset {
    border: none;
    
}
.w3-bar{
   border-radius: 25px;
    background: white;
    color: #6c757d;
    margin-top:10px;
    border-color:white;
    box-shadow:0 2px 5px rgba(0,0,0,.3);
}

.formulario{

  color:#17a2b8;
  
}
.informationmessage2{
  height:200px;
  overflow: scroll;
}
</style>