<template>
  <div class="content"> 
   
     <b-btn class="button32" @click="back()"><font-awesome-icon icon="arrow-left"style="font-size:30px;"/></b-btn>
      <h1 class="title">Partido Finalizado</h1> 


      <div class="information message" v-if="loading==true">
        <h2 class="title1"> Resultado: {{this.resultado.equipoA.setsGanados}} - {{this.resultado.equipoB.setsGanados}}</h2>  
         <h5>-------------------------------------------------</h5>  
        <h6>Deporte: {{this.game.sport.type}}</h6>
        <h6>Ubicación: {{this.game.location.name}}</h6>
        <h6>Fecha: {{this.game.date}}</h6>
         <h6>Horario: {{custom(this.game.timeStart)}}-{{custom(this.game.timeEnd)}}</h6>
          
      </div>
     
       <div class="information message2" v-if="loading==true">
     
      <table class="tabla">
        <tbody>
          <tr>
          </tr>
          <tr>
          <th> 
              <h2 class="title2"> Equipo A</h2>  
              <div class="bloque" v-for=" playerG in this.playersA" :key="playerG.idPlayer">
                 <b-btn class="buttonUser":to="{ name: 'GameUser', params: { id: playerG.player.login}}">
                  <img class="foto"v-bind:src="getImagen(playerG.player.rutaImagen)" class="foto" style="width:75px">
                   </b-btn>
                  <h6 class="w3-large">{{playerG.player.login}}</h6>
             </div>
          </th>
          <td class="num">{{this.resultado.equipoA.sets[0]}}</td>
          <td class="num">{{this.resultado.equipoA.sets[1]}}</td>
          <td class="num">{{this.resultado.equipoA.sets[2]}}</td>
          </tr>
          <tr>
            <th>
              <h2 class="title2"> Equipo B</h2>  
              <div class="bloque" v-for=" playerG in this.playersB" :key="playerG.idPlayer">
                <b-btn class="buttonUser":to="{ name: 'GameUser', params: { id: playerG.player.login}}">
                <img class="foto"v-bind:src="getImagen(playerG.player.rutaImagen)" class="foto" style="width:75px">
              </b-btn>
               <h6 class="w3-large">{{playerG.player.login}}</h6>
             </div>
            </th>
            <td class="num">{{this.resultado.equipoB.sets[0]}}</td>
            <td class="num"> {{this.resultado.equipoB.sets[1]}}</td>
            <td class="num"> {{this.resultado.equipoB.sets[2]}}</td>
          </tr>
        </tbody>
      </table>
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
import { baseURL } from '../../common/http-common'

export default {
  components: { Multiselect, Weather},
  props:{
    idGame:null,

  },
  data() {
    return {
      game:{},
      players:null,
      playersA:[],
      playersB:[],
      resultadoA:'0',
      resultadoB:'0',
      goles:[],
      loading:false,
      resultado:{},
  


    }
  },
  watch: {

    '$route.params.id':'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.fetchData()
  },


  methods: {
    fetchData() {
       this.game={}
       this.loading=false;
       HTTP.get(`games/${this.$route.params.id}`) 
          .then(response => { this.game = response.data
                 return response })
          .then(this.jugadoresJuego)
          .then(this.transformarJSON)
          .then(this.prepararInfo)
          .catch(err => { this.error = err.message})
            
      
    },


  
  jugadoresJuego(){
      HTTP.get(`players/${this.game.idGame}`) 
              .then(response => { this.players = response.data
                     return response })
              .then(this.DividirEnEquipos)
              .catch(err => { this.error = err.message})


   },
   getImagen(path){
      return baseURL + "users/imagenes/" + path;
    },




  prepararInfo(){
     this.loading=true;

   },

   DividirEnEquipos(){

       for ( var i = 0; i < this.players.length; i ++){
        if(this.players[i].equipo=='A'){
          this.playersA.push(this.players[i])
        }else{
          this.playersB.push(this.players[i])
        }
       }

   },


  transformarJSON(){
    var obj={};
    this.resultado = JSON.parse(this.game.result);

      

  },
  
     _successHandler(response) {
      this.$router.replace({ name: 'Game'})
    },
     custom(hora){
      return hora.substring(0,5)
    },

  
   
      WhatLogin() {
      return auth.user.login
    },

    back() {
       this.$router.replace({ name: 'GameUser', params: { id:this.WhatLogin()}})
    },
    
    _errorHandler(err) {
      this.error = err.response.data.message
      
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
    margin-bottom:50px;
    height:70%;
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
 
    margin-left:40px;
  }

    .title2{
    font-family: 'Lato', sans-serif;
    font-size: 20px;
    font-weight: 200;
    margin-left:20px;

  }

  .titulo{
    font-family: 'Lato', sans-serif;
    font-size: 20px;
    font-weight: 200;
    margin-right:20px;
  }

 div.message {
  
  padding: 2%;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:30%;
  float:left;
  height:45%;
  margin-left:20px;
  


}
 div.message3 {
  
  padding: 2%;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:65%;
  float:left;
  height:15%;
  margin-left:20px;
  margin-top:20px;
  background: #fb887c;
  text-align:center;

}


div.message.information{background: #fb887c;}

div.message2 {
  
 
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:65%;
  float:left;
  height:77%;
  margin-left:20px;


}


div.message2.information{background: #17a2b8;}



 .user{
    width: 90%;
    background-color:#17a2b8;
    border-color:#17a2b8;
    height:120px;
    overflow: scroll;
    margin-left:10px;

 }

.foto{
 
    border-radius:10px;
    margin-right:20px;

    
    
 }
 .buttonUser{
  background-color:#17a2b8;
  border-color:#17a2b8;
 }
 .bloque{

  height:60%;
  margin-top:10px;
  margin-left:20px;


 }

 #players{
  width:50px;

 }


 .info{
  color:white;
  margin-bottom:10px;
  margin-top:10px;
  float:left;


 }


fieldset {
    border: none;
    margin: 0 2px;
    padding: .35em .625em .75em;
}

.jugadores{
  color:grey;
}

 .bloque{
  display:inline-block;
}

.button{
  display: inline-block;
  border-radius: 4px;
  background-color: #fb887c;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 17px;
  padding: 5px;
  width: 120px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
  float:right;
  margin-right:20px;
}

.button span{
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span{
  padding-right: 20px;
}

.button:hover span:after{
  opacity: 1;
  right: 0;
}

.button22{
    background: #17a2b8;
    border-color: #17a2b8;
}
.w3-large{
  margin-left:15px;
 
}

.tabla{
  width:100%;
  font-size: 2.8em;
  
 
}
.num{
  margin-right:20px;
}

th, td {
  padding: 20px;
  text-align: left;
  border: white 5px solid;

}


tr{
  border-left:1px hidden;
  border-right:1px hidden;
  
}


table tr:first-child th { 
  border-top: none;
}



</style>