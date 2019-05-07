<template>
  <div class="content" v-if="loading"> 
    <b-modal
        id="modalPrevent"
        ref="modal"
        size="lg"
        ok-only no-stacking>
        <Weather v-bind:location="this.game.location"></Weather>
    </b-modal>

    <b-modal
        class="formulario"
        id="modalPrevent1"
        ref="modal"
        title="Filtros"
        
        @ok="handleOk"
        @shown="clearName">

        <form @submit.stop.prevent="handleSubmit">
           <b-form-group>
           <b-form-group class="equipoA">
               <h6>Jugadores del equipo A</h6>
               <div v-for=" playerG in this.playersA" :key="playerG.idPlayer">
                   <li class="jugadores">{{playerG.player.name}} {{playerG.player.surname1}}</li>

               </div>
                <li class="jugadores" v-if="this.playersA.length==0">Equipo sin jugadores</li>
          </b-form-group> 
         
          <b-form-group class="equipoB">
              <h6>Jugadores del equipo B</h6>
               <div v-for=" playerG in this.playersB" :key="playerG.idPlayer">
                   <li class="jugadores">{{playerG.player.name}} {{playerG.player.surname1}}</li>
               </div>
               <li class="jugadores" v-if="this.playersB.length==0">Equipo sin jugadores</li> 
          </b-form-group> 
           
          </b-form-group>  
          <b-form-group class="equipos">
              <multiselect 
                v-model="equipo" 
                :options="this.equipos"
                :multiple="false"
                :searchable="true" 
                :preserve-search="true"
                :close-on-select="true" 
                :show-labels="false"
                placeholder="Equipo"
                >
              </multiselect>
            </b-form-group>            
            <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
        </form>
      </b-modal>

      <b-btn class="button3" v-if="this.game.creator.login==WhatLogin() && this.controlButton==false"@click="alertDisplay()"><span>Eliminar</span></b-btn> 
      <b-btn class="button" v-if="this.bol==false && this.completado==false && this.controlButton==false" v-b-modal.modalPrevent1><span>Apuntarse</span></b-btn> 
      <b-btn class="button1" v-if="this.bol==true && this.controlButton==false" @click="desapuntarse()"><span>Desapuntarse</span></b-btn> 
      <b-btn class="button31" v-if="this.completado==true && this.controlButton==false" ><span>Completo</span></b-btn> 
      <b-btn class="button32" @click="back()"><font-awesome-icon icon="backward"style="font-size:30px;"/></b-btn>
      <b-btn class="button2" v-if="this.controlButton==false" v-b-modal.modalPrevent><font-awesome-icon icon="cloud"style="font-size:30px;"/></b-btn>
      <b-btn class="button22" v-if="this.notification==false && this.controlButton==false" @click="notificar()"><font-awesome-icon icon="bell"style="font-size:30px;"/></b-btn>
      <b-btn class="button22" v-if="this.notification==true && this.controlButton==false" @click="desnotificar()"><font-awesome-icon icon="bell-slash"style="font-size:30px;"/></b-btn>
      <b-btn class="button2 colorComment" v-if="this.controlButton==false" :to="{ name: 'CommentGame', params: { id: game.idGame}}"><font-awesome-icon icon="comment-dots"style="font-size:30px;"/></b-btn>


      <h1 class="title" v-if="this.controlButton==false">Detalles Partido</h1>  
      <h1 class="title" v-if="this.controlButton==true">Partido Finalizado</h1>  
      <div class="information message">
        <h2 class="title1"> Información</h2>  
        <h6 >Creador: {{game.creator.name}} {{game.creator.surname1}} {{game.creator.surname2}}</h6>
        <h6>Deporte: {{game.sport.type}}</h6>
        <h6>Ubicación: {{game.location.name}}</h6>
        <h6>Horario: {{custom(game.timeStart)}}-{{custom(game.timeEnd)}} </h6>
        <h6>Fecha: {{game.date}}</h6>
      </div>
      <div class="information message2">
        <h2 class="title1"> Participantes</h2>  
        <div class="bloque" v-for=" playerG in this.players" :key="playerG.idPlayer">
           <b-btn class="user" :to="{ name: 'GameUser', params: { id: playerG.player.login}}">
             <img src="http://i.pravatar.cc/250?img=41" class="foto" style="width:85px">
          </b-btn>
          <div class="info">
              <span class="w3-large">{{playerG.player.login}}</span><br>
          </div>
       </div>
      </div>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import Multiselect from 'vue-multiselect'
import Weather from '../../entities/user/Weather'
export default {
  components: { Multiselect, Weather},
  data() {
    return {
      game:{},
      players:null,
      bol:false,
      playerG:{},
      jugador:null,
      equipos:['A','B'],
      equipo:null,
      idPlayer:null,
      notification:false,
      equipo:"",
      playersA:[],
      playersB:[],
      completado:false,
      controlButton:false,
      loading:false
      
    }
  },
  watch: {
    '$route': 'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.playersA=[];
      this.playersB=[];
      this.loading=false;
      if(this.$route.params.bol==true){
        this.controlButton=true;
        this.$swal('Aviso', "Estamos a la espera de que el creador rellene los resultados", 'info');
      }
       HTTP.get(`games/${this.$route.params.id}`) 
          .then(response => { this.game = response.data
                 return response })
          .then(response => { this.playerG.game = response.data
                 return response })
          .then(this.getMyPlayer)
          .then(this.getPlayers)
          .then(this.getNotifications)
          .catch(err => { this.error = err.message})
    },
    getMyPlayer(){
        return HTTP.get(`users/${this.WhatLogin()}`) 
          .then(response => {this.jugador = response.data
                return response })
          .catch(err => { this.error = err.message})
    },
    getPlayers(){
      return HTTP.get(`players/${this.game.idGame}`) 
          .then(response => { this.players = response.data
                 return response })
          .then(this.comprobarApuntamiento)
          .then(this.comprobarCompleto)
          .then(this.listarPorEquipos)
          .catch(err => { this.error = err.message})
    },
    getNotifications(){
       this.loading=true;
       return HTTP.get(`users/notifications/${this.WhatLogin()}/${this.game.idGame}`) 
          .then(response => {this.notification = response.data
                return response })
          .catch(err => { this.error = err.message})

    },
    comprobarApuntamiento(){
        for ( var i = 0; i < this.players.length; i ++){
          if(this.players[i].player.login==this.WhatLogin()){
            this.bol=true;
          }
       }
       return true;
    },
    comprobarCompleto(){
      if((this.bol==false) && (this.players.length== this.game.maxPlayers)){
        this.completado=true;
       }
       return true;
    },
    listarPorEquipos(){
        for ( var i = 0; i < this.players.length; i ++){
         if(this.players[i].equipo=="A"){
           this.playersA.push(this.players[i])
         }else{
          this.playersB.push(this.players[i])
         }
       }
       return true;
    },
    clearName() {
        this.equipo = "";
      },
      handleOk(evt) {
        // Prevent modal from closing
        evt.preventDefault()
        if (!this.equipo) {
           this.$swal('Alerta!', "Seleccione por lo menos un equipo!", 'error')
          
        }else if(this.equipo=="A" && this.playersA.length==(this.game.maxPlayers/2)){
          this.$swal('Alerta!', "El equipo A ya está al máximo, únete al otro equipo", 'error');
          
        }else if (this.equipo=="B" && this.playersB.length==(this.game.maxPlayers/2)) {
          this.$swal('Alerta!', "El equipo B ya está al máximo, únete al otro equipo", 'error');
        }else{ 
          this.handleSubmit()
        }
      },
      handleSubmit() {
        this.apuntarse();
      },
    confirmacion(){
       this.clearName()
        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
        this.$refs.modal.hide();
      })
        this.fetchData();
    }, 
    apuntarse(){
      this.playerG.equipo=this.equipo[0];
      this.playerG.player=this.jugador;
      
      HTTP.post('players', this.playerG) 
          .then(response => { this.bol = true
                 return response })
          .then(this._successHandler1)
          .then(this.confirmacion)
          .catch(this._errorHandler)
    },
    desapuntarse(){
       for ( var i = 0; i < this.players.length; i ++){
          if(this.players[i].player.login==this.WhatLogin()){
            this.idPlayer=this.players[i].idPlayer;
          }
       }
      HTTP.delete(`players/${this.idPlayer}`)
         .then(response => { this.bol = false
                 return response })
          .then(this._successHandler2)
          .catch(this._errorHandler)
    },
     alertDisplay() {
        this.$swal("¿Estás seguro de querer borrar este partido? Podrían estar apuntados a él varios jugadores", {
          dangerMode: true,
          buttons: true
        }).then((result) => {
            if(result) {
                this.$swal('Eliminado', 'Se ha borrado correctamente el partido', 'success')
                 HTTP.delete(`games/${this.game.idGame}`)
                      .then(this._successHandler)
                       .catch(this._errorHandler)
          } else {
                this.$swal('Cancelado', 'No se ha eliminado el partido', 'info')
          }
        })
    
  },
    notificar(){
      HTTP.put(`users/notifications/${this.WhatLogin()}/${this.game.idGame}/${this.notification}`)
          .then(this._successHandler3)
          .catch(this._errorHandler2)
    },
    desnotificar(){
      HTTP.put(`users/notifications/${this.WhatLogin()}/${this.game.idGame}/${this.notification}`)
          .then(this._successHandler4)
          .catch(this._errorHandler2)
    },
   
    _successHandler1(response) {
      this.$swal('Apuntado', 'Ya formas parte del partido, disfruta!', 'success')
  
    },
     _successHandler(response) {
      this.$router.replace({ name: 'Game'})
    },
    _successHandler2(response) {
      this.$swal('Desapuntado', 'Has dejado de formar parte del partido', 'success')
      this.fetchData()
    },
     _successHandler3(response) {
      this.notification=true;
      this.$swal('Notificaciones activadas', 'Se le notificará de cualquier cambio en este partido', 'success')
      this.fetchData()
    },
    _successHandler4(response) {
      this.notification=false;
      this.$swal('Notificaciones desactivadas', 'No se le notificará de cualquier cambio en este partido en adelante', 'success')
      this.fetchData()
    },
      WhatLogin() {
      return auth.user.login
    },
   
    _errorHandler(err) {
      this.error = err.response.data.message
       this.$swal('Lo sentimos...', 'El partido ya tiene cubierto su cupo máximo de participantes', 'error')
    },
    _errorHandler2(err) {
      this.error = err.response.data.message
      
    },
    custom(hora){
      return hora.substring(0,5)
    },
    back() {
      if(this.$route.params.back==true){
      
        this.$router.replace({ name: 'GameUser', params: { id:this.WhatLogin()}})
      }else{
       
      this.$router.go(-1)}
    },
  }
}
</script>

<style scoped lang="scss">
.content{
    width: 82%;
    background: #f3f3f3;
    padding: 1em;
    border-radius: 3px;
    margin-top:50px;
    font-family: 'Lato', sans-serif;
    margin:0;
    margin-left:120px;
    margin-top:50px;
    margin-bottom:59px;
    height:66%;
    border-radius: 6px;
}
.title{
    font-family: 'Lato', sans-serif;
    margin-left:40%;
    font-size: 30px;
    font-weight: 200;
    color: #17a2b8;
    margin-top:20px;
    margin-bottom:30px;
    color:#fb887c;
  }
  .title1{
    font-family: 'Lato', sans-serif;
    font-size: 30px;
    font-weight: 200;
    margin-bottom:30px;
  }
 div.message {
  
  padding: 2%;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:30%;
  float:left;
  height:72%;
  margin-left:20px;
  margin-top:20px;
}
div.message.information{background: #fb887c;}
div.message2 {
  
  padding: 2%;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:65%;
  float:left;
  height:72%;
  margin-left:20px;
  margin-top:20px;
  overflow: scroll;
}
div.message2.information{background: #17a2b8;}
.button, .button1, .button3 , .button2, .button22, .button31, .button32{
  display: inline-block;
  border-radius: 4px;
  background-color: #17a2b8;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 17px;
  padding: 5px;
  width: 150px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
  float:right;
}
.button span, .button2 span, .button1 span, .button3 span{
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}
.button span:after ,.button2 span:after , .button1 span:after, .button3 span:after{
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}
.button:hover span, .button1:hover span, .button3:hover span, .button2:hover span{
  padding-right: 20px;
}
.button:hover span:after, .button1:hover span:after, .button3:hover span:after, .button2:hover span:after{
  opacity: 1;
  right: 0;
}
.button1{
   background-color: grey;
   float:right;
   width: 150px;
   
 }
 .button3{
   background-color: red;
   float:right;
   width: 150px;
   
 }
 .button2, .button22, .button32{
  float:left;
  width:6%;
  margin-left:20px;
 }
 .colorComment{
  background-color: #e2bc27;
 }
 .button22, .button32{
    background-color: green;
    width:6%;
 }
 .button31{
    background-color: green;
 }
  .button32{
    background-color: grey;
 }
 .user{
    width: 90%;
    background-color:#17a2b8;
    border-color:#17a2b8;
    height:120px;
    overflow: scroll;
    margin-left:10px;
 }
.foto{
    margin-top:5px;
    float:left;
    border-radius:10px;
    
 }
 .bloque{
  display:inline-block; 
  float:left;
 }
 .info{
  color:white;
  margin-left:20px;
 }
.formulario{
  color:#17a2b8;
}
.equipoB{
  float:right;
  margin-right:20px;
}
.equipoA{
  float:left;
   margin-left:20px;
}
fieldset {
    border: none;
    margin: 0 2px;
    padding: .35em .625em .75em;
}
.jugadores{
  color:grey;
}
.swal-title {
    color: rgba(0,0,0,.65);
    font-weight: 500px;
    text-transform: none;
    position: relative;
    display: block;
    padding: 13px 16px;
    font-size: 27px;
    line-height: normal;
    text-align: center;
    margin-bottom: 0;
}
.w3-large{
  margin-left:13px;
}
.swal-button--danger {
    background-color: #12b6ce !important;
}
</style>