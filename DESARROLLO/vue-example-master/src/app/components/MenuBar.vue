<template>
  <div>
  <b-navbar toggleable="md" variant="info">
   <b-modal
        v-if="mensajesUsuario"
        class="formulario"
        id="modal5"
        ref="modal5"
        :title=this.titulo1
        ok-title="Enviar"
        ok-only 
        ok-variant="outline-success" 
        @shown="clearName">
        <div slot="modal-header" class="w-100">
         <h5 class="modal-tittle">{{this.titulo1}}</h5>
        <b-button
          variant="outline-secondary"
          size="sm"
          class="button_back"
          @click="backModal"
        ><font-awesome-icon icon="arrow-left"style="font-size:20px;"/>
        </b-button>
      </div>
        <form @submit.stop.prevent="handleSubmit">
          
          <b-form-group>
         
             <b-form-group>

             <div class="informationmessage2" v-if="mensajesUsuario.length!=0">
             <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"></link>
             <div v-bind:class="{ w3container: mes.fromUser.login==WhatLogin1(), w3container1:mes.fromUser.login!=WhatLogin1()}" v-for=" mes in this.mensajesUsuario" :key="mes.idComment">
             <b-btn class="w3-bar" >
             <div class="conj">
              <span v-bind:class="{ rectangulo1: mes.fromUser.login==WhatLogin1(), rectangulo3:mes.fromUser.login!=WhatLogin1()}">{{mes.fromUser.name}} {{mes.fromUser.surname1}} {{mes.fromUser.surname2}}</span><br>
              <span class="rectangulo">{{mes.contentComment}}</span>
            </div>
            </b-btn>
          </div>
            </div>
        </b-form-group>
       
         </b-form-group>
      </form>
      <div slot="modal-footer" class="w-100">
        <b-form-textarea
              id="textarea"
              class="textarea"
              v-model="newMensaje"
              v-on:keyup.enter="crearMensajeUsuario"
              placeholder="Mensaje"
              rows="3"
              max-rows="6"
            ></b-form-textarea>
        <b-button
          variant="outline-success"
          size="sm"
          class="float-right buttonFooter"
          @click="crearMensajeUsuario"
        >
          Enviar
        </b-button>
      </div>
      
      </b-modal>

  <b-modal
        class="formulario"
        id="modalPrevent3"
        ref="modalPrevent3"
        title="Mensajes Directos"
        ok-title="Mensajes Directos"
        ok-only 
        ok-variant="outline-secondary" 
        @ok="handleOk3"
        @shown="clearName">
       
        <form @submit.stop.prevent="handleSubmit">
          <b-form-group>
             <div class="emptyMesDiv" v-if="messages.length==0">
                <h5 class="emptyMessage">No tiene mensajes en su buzón de entrada</h5>
             <div>
          </b-form-group>
               <b-form-group v-if="messages.length!=0">
             <div class="informationmessage2" >
             <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"></link>
             <div class="w3-container" v-for=" mes in this.messages" :key="mes.idUser">
             <b-btn class="w3-bar" @click="irAMensajesUsuario(mes.fromUser)">
               <img v-bind:src="getImagen(mes.fromUser.rutaImagen)" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
              <div class="conj2">
              <h5 class="rectangulo2">{{mes.fromUser.name}} {{mes.fromUser.surname1}} {{mes.fromUser.surname2}}</h5><br>
              <div class="circulo" v-if="mes.countMessagesNotViewed>0">
                <h5 class="countMessagesNotViewed" >{{mes.countMessagesNotViewed}}</h5><br>
              </div>
              </div>
            </b-btn>
          </div>
            </div>
        </b-form-group>
      </form>
      </b-modal>

       <b-modal
        class="formulario"
        id="modal4"
        ref="modal4"
        title="Mensaje nuevo"
        ok-title="Siguiente"
        ok-only 
        ok-variant="outline-secondary" 
        @ok="handleOk4"
        @shown="clearName">
         <div slot="modal-header" class="w-100">
         <h5 class="modal-tittle">Mensajes Nuevos</h5>

        <b-button
          variant="outline-secondary"
          size="sm"
          class="button_back"
          @click="backModal1"
        ><font-awesome-icon icon="arrow-left"style="font-size:20px;"/>
        </b-button>
      </div>
        <form @submit.stop.prevent="handleSubmit">
        <b-form-group>
          <div>
            <multiselect 
                v-model="usuarioMessages" 
                :options="users"
                :multiple="false"
                :preserve-search="false"
                :close-on-select="true" 
                :show-labels="false"
                placeholder="Enviar a..."
                :custom-label="nameCustom"
                >

              </multiselect>
               <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
  
          </div>
        </b-form-group>
      </form>
      </b-modal>

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
             <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"></link>
             <div class="w3-container" v-for=" game in this.games" :key="game.idGame">
             <b-btn class="w3-bar" @click="confirmacion(game)">
               <img v-bind:src="getImagenSport(game.sport.rutaImagen)" class="w3-bar-item w3-circle w3-hide-small" style="width:85px"></img>
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
      <b-modal
        class="formulario"
        id="modal3"
        ref="modal3"
        title="Jugadores del Equipo"
        
        @ok="handleOk1"
        @shown="clearName">

        <form @submit.stop.prevent="handleSubmit">
           <b-form-group class="jugadores">
              <h6>¿Algún jugador cambió finalmente de equipo?</h6>
               <div v-for=" playerG in this.playersChange" :key="playerG.idPlayer">
                   <li class="jugadores">{{playerG.player.name}} {{playerG.player.surname1}}</li>
              <multiselect 
                v-model="playerG.equipo" 
                :options="equipos"
                :multiple="false"
                :preserve-search="false"
                :close-on-select="true" 
                :show-labels="false"
                placeholder="Equipo"
                >
              </multiselect>
                <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
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
                 v-if="isLogged && !isAdmin"
                :to="{ name: 'Game' }"
                exact>Partidos</b-nav-item> 

             <b-nav-item
                 v-if="!isAdmin && isLogged"
                :to="{ name: 'ActivitiesFollowed' }"
                exact>Actividades</b-nav-item> 
               

            <b-nav-item
                 v-if="isAdmin"
                :to="{ name: 'UserList' }"
                exact>Usuarios</b-nav-item> 
         </b-navbar-nav>
          

          <b-navbar-nav class="ml-auto">
           <b-nav-item  v-if="isLogged && !isAdmin">
            <multiselect 
                v-model="usuarioSearch" 
                :options="this.users"
                :multiple="false"
                :searchable="true" 
                :preserve-search="true"
                :close-on-select="true" 
                :show-labels="false"
                @close="selectOnSelect()"
                placeholder="Buscar en PLAY2GETHER"
                :custom-label="nameCustom"
                >
               <span slot="noResult">Usuario no encontrado</span>
            </multiselect>
               
             </b-nav-item>
            <b-nav-item
                v-if="!isLogged"
                :to="{ name: 'Login' }"
                exact>Login</b-nav-item>
           

            <b-nav-item-dropdown v-if="isLogged || isAdmin" text="Conectado" variant="primary" class="m-2"> 
              <b-dropdown-item disabled> {{loggedUser}}</b-dropdown-item>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item exact v-if="isLogged && !isAdmin"  :to="{ name: 'GameCreate'} ">Crear Evento</b-dropdown-item>
              <b-dropdown-item exact v-if="isLogged && !isAdmin"  :to="{ name: 'GameUser' , params: { id: WhatLogin}} ">Perfil Público</b-dropdown-item>
                <b-dropdown-item exact v-if="isLogged && !isAdmin"  :to="{ name: 'UserDetail',params: {id:WhatLogin}} ">Datos Personales</b-dropdown-item>
                <b-dropdown-divider></b-dropdown-divider>
                <b-dropdown-item exact v-if="isLogged" @click="logout()" :to="{ name: ''}">Cerrar Sesión</b-dropdown-item>
            </b-nav-item-dropdown>
             <b-nav-item v-if="isAdmin" text="" variant="primary" class="m-2">  </b-nav-item>
              <b-nav-item v-if="isAdmin" text="" variant="primary" class="m-2">  </b-nav-item>
               <b-nav-item v-if="isLogged && !isAdmin" text="" variant="primary" class="m-2">  </b-nav-item>

             <b-btn class="button" v-b-modal.modalPrevent2 @click="controlGamesModal(true)" v-if="isLogged && !isAdmin && this.gamesResult.length>0"><div>{{this.gamesResult.length}}</div><font-awesome-icon icon="exclamation-circle"style="font-size:35px;"/></b-btn>

              <b-btn class="button" v-b-modal.modalPrevent2  @click="controlGamesModal(false)"v-if="isLogged && !isAdmin && this.gamesValor.length>0"><div>{{this.gamesValor.length}}</div><font-awesome-icon icon="bell"style="font-size:35px;"/></b-btn>

               <b-btn class="button" v-b-modal.modalPrevent3 @click="messagesUser()"v-if="isLogged && !isAdmin"><div>{{countMessages}}</div><font-awesome-icon icon="envelope"style="font-size:35px;"/></b-btn>

         </b-navbar-nav>
       </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import auth from '../common/auth'
import { HTTP } from '../common/http-common'
import Multiselect from 'vue-multiselect'
import { baseURL } from '../common/http-common'
export default {
components: { Multiselect},
data() {
    return {
      games:[],
      gamesResult:[],
      gamesValor:[],
      control:false,
      bol:false,
      titulo:'',
      equipos:['A','B'],
      playersChange:null,
      gameSelect:null,
      playersBefore:null,
      equiposJugadores:[],
      messages:[],
      usuarioMessages:null,
      users:[],
      titulo1:'',
      mensajesUsuario:null,
      newMensaje:'',
      comentario:{},
      userFrom:{},
      userTo:{},
      usuarioSearch:null,
      countMessages:0

     
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
      this.usuarioMessages=null
      this.newMensaje=""
      if(this.isLogged){
       HTTP.get(`users/${this.WhatLogin1()}/pendingResult`) 
                .then(response => { this.gamesResult= response.data
                      return response })
                .then(this.pendingValoration)
                .then(this.mostrarMensajesParaMi)
                .catch(err => { this.error = err.message})
     if(!this.isAdmin){        
        HTTP.get(`users/normal`)
            .then(response => { this.users = response.data })
            .then(this.filtarLogueado)
            .catch(err => { this.error = err.message}) }
          }
    },
    filtarLogueado(){
       for ( var x = 0; x < this.users.length; x ++){
          if(this.users[x].login==this.WhatLogin1()){
            this.users.splice(x,1);
          }
         
        
       }
    },
     selectOnSelect(){
      if(this.usuarioSearch){
        this.$router.replace({ name: 'GameUser', params: { id: this.usuarioSearch.login}})
        this.usuarioSearch=null
      }
    },

    pendingValoration(){
       return HTTP.get(`users/${this.WhatLogin1()}/valoration`) 
                .then(response => { this.gamesValor= response.data
                      return response })
                .catch(err => { this.error = err.message})
    },
    messagesUser(){
       return HTTP.get(`comments/user/${this.WhatLogin1()}`) 
                .then(response => { this.messages= response.data
                      return response })
                .then(this.filtarLogueado1)
                .catch(err => { this.error = err.message})
       
    },
    mostrarMensajesParaMi(){
       return HTTP.get(`comments/user/countMessages/${this.WhatLogin1()}`) 
                .then(response => { this.countMessages= response.data    
                      return response })
                .catch(err => { this.error = err.message})
    },
     filtarLogueado1(){
       for ( var x = 0; x < this.messages.length; x ++){
          if(this.messages[x].fromUser.login==this.WhatLogin1()){
            this.messages.splice(x,1);
          }
         
        
       }
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
      handleOk3(evt) {
        this.usuarioMessages=null
         this.newMensaje=""
         this.$refs.modal4.show();
      
      },
       handleOk4(evt) {
        if (!this.usuarioMessages) {
          evt.preventDefault()
           this.$swal('Alerta!', "Seleccione un destinatario", 'error')
         }else{
           HTTP.get(`comments/user/${this.WhatLogin1()}/${this.usuarioMessages.login}`) 
                .then(response => { this.mensajesUsuario= response.data
                      return response })
                .then(this.accionesDeModales)
                .catch(err => { this.error = err.message})
         }
          
      },
      accionesDeModales(){

         this.titulo1=this.usuarioMessages.name + " "+ this.usuarioMessages.surname1+" "+this.usuarioMessages.surname2;
          this.$refs.modal4.hide();
          this.$refs.modal5.show();

      },
      crearMensajeUsuario() {
         HTTP.get(`users/${this.WhatLogin1()}`) 
          .then(response => { this.userFrom = response.data
                 return response })
          .then(this.traerUsuarioTo)
          .then(this.enviarMensaje)
          .catch(err => { this.error = err.message})

        
      
      },
      actualizarEstadoMensajes(){
        HTTP.put(`comments/user/${this.usuarioMessages.login}/${this.WhatLogin1()}`) 
                .then(this.mostrarMensajesParaMi)
                .catch(err => { this.error = err.message})

      },
      irAMensajesUsuario(usuario){
         this.usuarioMessages=null
         this.newMensaje=""
        this.usuarioMessages=usuario;
         HTTP.get(`comments/user/${this.WhatLogin1()}/${this.usuarioMessages.login}`) 
                .then(response => { this.mensajesUsuario= response.data
                  this.$refs.modalPrevent3.hide();
                      return response })
                .then(this.accionesDeModales)
                .then(this.actualizarEstadoMensajes)
                .catch(err => { this.error = err.message})
      },
      traerUsuarioTo(){

          return HTTP.get(`users/${this.usuarioMessages.login}`) 
          .then(response => { this.userTo = response.data
                 return response })
          .catch(err => { this.error = err.message})

      },
      enviarMensaje(){
        this.comentario.contentComment=this.newMensaje;
        this.comentario.fromUser=this.userFrom;
        this.comentario.toUser=this.userTo;
         HTTP.post(`comments/user`,this.comentario) 
                .then(this.recargarModal)
                .catch(err => { this.error = err.message})
      },
      recargarModal(){
         this.newMensaje=""
          HTTP.get(`comments/user/${this.WhatLogin1()}/${this.userTo.login}`) 
                .then(response => { this.mensajesUsuario= response.data
                      return response })
         

      },
      updateTeams(){
        var promises=[];
      for ( var i = 0; i < this.playersChange.length; i ++){
          if(this.playersChange[i].equipo!=this.equiposJugadores[i]){
            promises.push(HTTP.put(`players/${this.playersChange[i].idPlayer}/team/${this.playersChange[i].equipo}`)
              .catch(this._errorHandler))
          }
          
          
        }
        return Promise.all(promises);

     },
     nameCustom({ name, surname1, surname2 }) {
      return `${name +" "+ surname1 +" "+surname2 } `
    },
      handleOk1(evt) {
       evt.preventDefault()
       var bol=false;
         for ( var i = 0; i < this.playersChange.length; i ++){
          if (!this.playersChange[i].equipo) {
           this.$swal('Alerta!', "Todos los jugadores han de estar en un equipo", 'error')
           bol=true;
          }
        }
        if(bol==false){
          this.updateTeams().then(() => {
            this.ChangePage();
          });
        }
       
      
      },
     
     confirmacion(game){
        this.gameSelect=game;
        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
        this.$refs.modal2.hide();

      })
        if(this.bol==true) {
         HTTP.get(`players/${game.idGame}`) 
          .then(response => { this.playersChange = response.data
                 return response })
          .then(this.copyTeams)
          .catch(err => { this.error = err.message})
        }else{
          this.ChangePage();
        }

       
    }, 
    getImagen(path){
      return baseURL + "users/imagenes/" + path;
    },
     getImagenSport(path){
      return baseURL + "sports/imagenes/" + path;
    },
   
    copyTeams(){
      for ( var i = 0; i < this.playersChange.length; i ++){
          this.equiposJugadores.push(this.playersChange[i].equipo)
        }

        this.$refs.modal3.show()

    },
    ChangePage(){
      this.equiposJugadores=[]
       this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
        this.$refs.modal3.hide();

      })
       if(this.bol==true){
        
          this.$router.replace({ name: this.gameSelect.sport.componenteEntrada, params: { id:this.gameSelect.idGame}})
        
     }else{
          this.$router.replace({ name: 'ValorationGame', params: { id:this.gameSelect.idGame}})
     }

    },
      custom(hora){
      return hora.substring(0,5)
    },
    backModal(){
      this.$refs.modal5.hide()
      this.$refs.modal4.show()

    },
    backModal1(){
      this.$refs.modal4.hide()
      this.messagesUser();
      this.$refs.modalPrevent3.show()
      
      

    },
    
  }
}
</script>
<style scoped lang="scss">
.emptyMesDiv{
  width:100%;
}
.emptyMessage{
 
    margin-top: 10%;
    text-align: center;

}

.buttonModal{
  float:right;
  background:#17a2b8;
  border-color:#17a2b8;
}
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
.ml-auto {
    margin-right: 10px;
}
.informationmessage2{
  height:230px;
  overflow: scroll;

}
textarea.form-control {
    height: auto;
    float: left;
    width: 80%;
}
.buttonFooter{
  margin-top:15px;
}
.conj{
  text-align:left !important;
  width:100%;

}
.rectangulo{
  font-size:0.8em;
  word-wrap:break-word;
}

.rectangulo2 {
    font-size: 1.0em;
    color: #17a2b8;
    width: 60%;
    float: left;
    margin-top: 7%;
}
.rectangulo1{
  font-size:0.9em;
  color: #17a2b8;
  margin-left: 3%;
}
.rectangulo3{
   font-size:0.9em;
   color: #fb887c;
   margin-left: 3%;

}
.w3container, .w3-panel {
    padding: 0.01em 16px;
    margin-bottom: 5px;
    width:60%;
    white-space: pre-wrap;
    break-word:break-wrap;


}
.w3container1{
   padding: 0.01em 16px;
   margin-bottom: 5px;
   width:60%;
  float:right;
  white-space: pre-wrap;
  break-word:break-wrap;
}

.modal-tittle{
  float:right;
}

.button_back{
  float:left;
}

.circulo {
    background-color: #17a2b8;
    -moz-border-radius: 200px 200px 200px 200px;
    -webkit-border-radius: 200px 200px 200px 200px;
    width: 9%;
    height: 35px;
    float: right;
}

.countMessagesNotViewed {
    color: white;
    margin: 0;
    margin-top: 7px;
}
.search{
  color:white;
  float:right;
}


</style>