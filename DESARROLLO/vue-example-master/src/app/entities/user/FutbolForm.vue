<template>
  <div class="content"> 
   
    
      <h1 class="title">Resultado Partido</h1>  
      <div class="information message">
        <h2 class="title1"> Información</h2>  
        <h6 >Creador: {{this.game.creator.name}} {{this.game.creator.surname1}} {{this.game.creator.surname2}}</h6>
        <h6>Deporte: {{this.game.sport.type}}</h6>
        <h6>Ubicación: {{this.game.location.name}}</h6>
        <h6>Horario: {{this.game.timeStart}} - {{this.game.timeEnd}} </h6>
        <h6>Fecha: {{this.game.date}}</h6>
      </div>
      <div class="information message2">
       

     <div class="formulario">
		  <b-form
		      v-if="game"
		      class="app-form"
		      @submit="save">
		  <span class="w3-large">Resultado</span><br>
		        <b-form-input
		          id="nombre"
		          v-model="resultado"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder="Introduce el resultado"/>
		 
    </b-form>
	</br>
	 <span class="w3-large">Goles:</span><br>
    <div class="bloque" v-for=" playerG in this.players" :key="playerG.idPlayer">
    	<div class="info">
              <span class="w3-large">{{playerG.player.login}}</span><br>
                 <b-form-input
		          id="players"
		          v-model="goles[playerG.idPlayer]"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""/>
          </div>

          
		     
		     
          
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
  props:{
    idGame:null,

  },
  data() {
    return {
    	game:{},
    	players:null,
    	resultado:'',
    	goles:[]


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
    	this.game=this.$route.params.id;
    	this.jugadoresJuego();
    	
      
    },
  
	jugadoresJuego(){
		  HTTP.get(`players/${this.game.idGame}`) 
		          .then(response => { this.players = response.data
		                 return response })
		          .catch(err => { this.error = err.message})


	   },
  
     _successHandler(response) {
      this.$router.replace({ name: 'Game'})
    },

    save(){

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
    height:65%;
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

    .title2{
    font-family: 'Lato', sans-serif;
    font-size: 20px;
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
  height:60%;




 }

 #players{
 	width:50px;
 }


 .info{
  color:white;
  margin-bottom:10px;
  margin-right:10px;

  margin-top:10px;

 }


fieldset {
    border: none;
    margin: 0 2px;
    padding: .35em .625em .75em;
}

.jugadores{
  color:grey;
}

 
.app-form {
    padding-top: 0px;

}

#nombre{
	width:300px;

}

</style>
