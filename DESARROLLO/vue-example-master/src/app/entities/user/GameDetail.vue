<template>
  <div class="content"> 
    <b-modal
        id="modalPrevent"
        ref="modal"
        size="lg"
        ok-only no-stacking>
        <Weather v-bind:location="this.game.location"></Weather>
    </b-modal>
     <b-btn class="button3" v-if="this.game.creator.login==WhatLogin()"@click="alertDisplay()"><span>Eliminar</span></b-btn> 
    <b-btn class="button" v-if="this.bol==false" @click="apuntarse()"><span>Apuntarse</span></b-btn> 
    <b-btn class="button1" v-if="this.bol==true" @click="desapuntarse()"><span>Desapuntarse</span></b-btn> 
     <b-btn class="button2" v-b-modal.modalPrevent><font-awesome-icon icon="cloud"style="font-size:30px;"/></b-btn>
      <h1 class="title">Detalles Partido</h1>  
      <div class="information message">
        <h2 class="title1"> Información</h2>  
        <h6 >Creador: {{this.game.creator.name}} {{this.game.creator.surname1}} {{this.game.creator.surname2}}</h6>
        <h6>Deporte: {{this.game.sport.type}}</h6>
        <h6>Ubicación: {{this.game.location.name}}</h6>
        <h6>Horario: {{this.game.timeStart}} - {{this.game.timeEnd}} </h6>
        <h6>Fecha: {{this.game.date}}</h6>
      </div>
      <div class="information message2">
        <h2 class="title1"> Participantes</h2>  
        <div class="bloque" v-for=" playerG in this.players" :key="playerG.idPlayer">
           <b-btn class="user" :to="{ name: 'GameUser', params: { id: playerG.player.login}}">
             <img src="http://i.pravatar.cc/250?img=41" class="foto" style="width:85px">
          </b-btn>
          <div class="info">
              <span class="w3-large">{{playerG.player.name}} {{playerG.player.surname1}}</span><br>
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
      equipos:['a','b'],
      equipo:null,
      idPlayer:null,
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

      this.game=this.$route.params.id;
      this.playerG.game=this.game;

      HTTP.get(`players/${this.game.idGame}`) 
          .then(response => { this.players = response.data
                 return response })
          .then(this.comprobarApuntamiento)
          .catch(err => { this.error = err.message})

       HTTP.get(`users/${this.WhatLogin()}`) 
          .then(response => {this.jugador = response.data
                return response })
          .catch(err => { this.error = err.message})



    },
    comprobarApuntamiento(){
       for ( var i = 0; i < this.players.length; i ++){
          if(this.players[i].player.login==this.WhatLogin()){
            this.bol=true;
          }

       }

    },

    apuntarse(){
      this.playerG.equipo="a";
      this.playerG.player=this.jugador;
      
      HTTP.post('players', this.playerG) 
          .then(response => { this.bol = true
                 return response })
          .then(this._successHandler1)
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
   
    _successHandler1(response) {
      this.$swal('Apuntado', 'Ya formas parte del partido, disfruta!', 'success')
      this.fetchData()
    },
     _successHandler(response) {
      this.$router.replace({ name: 'Game'})
    },
    _successHandler2(response) {
      this.$swal('Desapuntado', 'Has dejado de formar parte del partido', 'success')
      this.fetchData()
    },
      WhatLogin() {
      return auth.user.login
    },

    back() {
      this.$router.go(-1)
    },
    
    _errorHandler(err) {
      this.error = err.response.data.message
       this.$swal('Lo sentimos...', 'El partido ya tiene cubierto su cupo máximo de participantes', 'error')
    }
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
    margin-bottom:50px;
    height:60%;
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
  height:70%;
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
  height:70%;
  margin-left:20px;
  margin-top:20px;

}


div.message2.information{background: #17a2b8;}

.button, .button1, .button3 , .button2{
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
   background-color: #fb887c;
   float:right;
   width: 150px;
   
 }
 .button3{
   background-color: grey;
   float:right;
   width: 150px;
   
 }

 .button2{
  float:left;
  width:6%;
  margin-left:20px;
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


</style>
