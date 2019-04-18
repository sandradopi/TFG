<template>
  <div class="content"> 
   
     <b-btn class="button" @click="guardar()"><span>Guardar</span></b-btn> 
      <h1 class="title">Resultado Partido</h1> 

      <div class="information message" v-if="loading==true">
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
		      >
		 <b-form-group>
		 <span class="w3-large">Resultado Final:</span><br>
		 <div class="bloque2">
		  <span class="letra">A</span><br>
		        <b-form-input
		          id="nombre"
		          v-model="resultadoA"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""></b-form-input>
		</div>

		<div class="bloque3">
  		 <span class="letra">B</span><br>
		        <b-form-input
		          id="nombre"
		          v-model="resultadoB"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder="">
		      	</b-form-input>
		
     </div>
   	 </b-form-group>     
	 <b-form-group>
	<div class="info">
	<span class="w3-large">Goles Jugadores A:</span><br>
    <div class="bloque" v-for=" playerG in this.playersA" :key="playerG.idPlayer">
     <img class="foto"src="http://i.pravatar.cc/250?img=41" class="foto" style="width:60px">
     <div class="conj">	
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
	</br>
	</br>
  <span class="w3-large">Goles Jugadores B:</span><br>
    <div class="bloque" v-for=" playerG in this.playersB" :key="playerG.idPlayer">
     <img class="foto"src="http://i.pravatar.cc/250?img=40" class="foto" style="width:60px">
     <div class="conj">	
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
      </b-form-group>
     </b-form>
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
  
     _successHandler(response) {
      this.$router.replace({ name: 'Game'})
    },

    guardar(){
    	
    	var equipoA={};
    	var equipoB={};
    	var jugadoresA=[];
    	var jugadoresB=[];


    	 for ( var i = 0; i < this.players.length; i ++){
    	 	if(this.players[i].equipo=='A'){
    	 		jugadoresA.push({
    	 			"id":this.players[i].player.login,
    	 			"goles":this.goles[this.players[i].idPlayer]
    		});
    	 	}

    	 	if(this.players[i].equipo=='B'){
    	 		jugadoresB.push({
    	 			"id":this.players[i].player.login,
    	 			"goles":this.goles[this.players[i].idPlayer]
    		});
    	 	}
    		
    	}

    	equipoA.goles=this.resultadoA;
    	equipoB.goles=this.resultadoB;
    	equipoA.jugadoresA=jugadoresA;
    	equipoB.jugadoresB=jugadoresB;
    	this.resultado.equipoA=equipoA;
    	this.resultado.equipoB=equipoB;

    	 HTTP.put(`games/${this.game.idGame}`,this.resultado)
              .then(this._successHandler)
              .catch(this._errorHandler)

    	
    },

     _successHandler(response) {
      this.$swal('Listo!', "Sus resultados han sido añadidos correctamente", 'success')
      this.$router.replace({ name: 'FutbolResult', params: { id:this.game.idGame}})
    },
   
      WhatLogin() {
      return auth.user.login
    },

    back() {
      this.$router.go(-1)
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
  height:73%;
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
  height:73%;
  margin-left:20px;
  margin-top:20px;
  overflow: scroll;

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
    margin-right:20px;


    
 }

 .bloque{

  height:60%;
  margin-right:25px;
  margin-top:10px;

 }

 #players{
 	width:50px;

 }


 .info{
  color:white;
  margin-bottom:10px;
  margin-top:30px;


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
	width:50px;


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

.bloque3{
	float:right;
	margin-right:550px;
}

.bloque2{

	float:left;
	display:inline-block;
}
.letra{
	margin-left:20px;
}
.bloque{
	display:inline-block;
}

.conj{
	float:right;
}

</style>
