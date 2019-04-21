<template>
  <div class="content"> 
   
     <b-btn class="button" @click="guardar()"><span>Guardar</span></b-btn> 
      <h1 class="title">Resultado Partido</h1> 

      <div class="information message" v-if="loading==true">
        <h2 class="title1"> Información</h2>  
        <h6 >Creador: {{this.game.creator.name}} {{this.game.creator.surname1}} {{this.game.creator.surname2}}</h6>
        <h6>Deporte: {{this.game.sport.type}}</h6>
        <h6>Ubicación: {{this.game.location.name}}</h6>
        <h6>Horario: {{custom(this.game.timeStart)}}-{{custom(this.game.timeEnd)}}</h6>
        <h6>Fecha: {{this.game.date}}</h6>
      </div>
      <div class="information message2">
       

     <div class="formulario">
		   
	 <b-form-group>
	<div class="info">
	<span class="w3-large">Sets Equipo A:</span><br>
    <div class="bloque" v-for=" playerG in this.playersA" :key="playerG.idPlayer">
     <img class="foto"src="http://i.pravatar.cc/250?img=41" class="foto" style="width:60px">
     <div class="conj">	
     <span class="w3-large">{{playerG.player.login}}</span><br>
            <b-form-group class="conj1">
                 <b-form-input
		          id="players"
		          v-model="setsA[0]"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""></b-form-input>

		        <b-form-input
		          id="players"
		          v-model="setsA[1]"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""></b-form-input>

		          <b-form-input
		          id="players"
		          v-model="setsA[2]"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""></b-form-input>

		         
		    </b-form-group>

	</div>

    </div>
	</br>
	<span class="w3-large">--------------------------------------------------------------------------------------------------------------</span><br>
	</br>
  <span class="w3-large">Sets Equipo B:</span><br>
    <div class="bloque" v-for=" playerG in this.playersB" :key="playerG.idPlayer">
     <img class="foto"src="http://i.pravatar.cc/250?img=40" class="foto" style="width:60px">
     <div class="conj">	
              <span class="w3-large">{{playerG.player.login}}</span><br>
                <b-form-group class="conj1">
                 <b-form-input
		          id="players"
		          v-model="setsB[0]"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""></b-form-input>

		        <b-form-input
		          id="players"
		          v-model="setsB[1]"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""></b-form-input>

		          <b-form-input
		          id="players"
		          v-model="setsB[2]"
		          type="text"
		          autocomplete="off"
		          required
		          placeholder=""></b-form-input>

		         
		    </b-form-group>

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
    	setsA:[],
    	setsB:[],
    	loading:false,
    	resultado:{},
    	error:''
   


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

    checkForm () {
     this.error='';
	 if(this.setsA.length<2){
      		this.error="Le falta por rellenar los resultados de algun set del equipo A"
        	return false;
       }

      if(this.setsB.length<2){
      		this.error="Le falta por rellenar los resultados de algun set del equipo B"
        	return false;
       }

       if(this.setsB.length==2){
        if((this.setsA[0]>this.setsB[0] && this.setsA[1]<this.setsB[1]) || (this.setsA[0]<this.setsB[0] && this.setsA[1]>this.setsB[1])){
          this.error="Revise los puntos se los sets, si solo jugaron 2 sets, ambos sets tuvieron que haber sido ganados por el mismo equipo"
          return false;
        }
       }


      for ( var i = 0; i < this.setsA.length; i ++){
      	if(this.setsA[i]>7){
      		var set=i+1;
        	this.error="Revise los puntos del set "+set+" del equipo A, no pueden se superior a 7"
        	return false;

        }else if(this.setsA[i]==7){
        	var set=i+1;
        	if((7-this.setsB[i])>2){
				console.log(this.setsB[i])
        		console.log(7-this.setsB[i])
        		this.error="Revise los puntos del set "+set+" del equipo A y B, si se ha llegado a 7 puntos la diferencia con el equipo contrincante a de ser de 2 o 1 en caso de timeBreak"
        	return false;

        	}
        }else if((this.setsA[i]<6) && (this.setsB[i]<6)){
        	var set=i+1;
        	this.error="Revise los puntos del set "+set+" del equipo A y B, ninguno a llegado a 6 puntos, los necesarios para acabar ese set"
        	return false;

        }
      }

       for ( var i = 0; i < this.setsB.length; i ++){
       	
        if(this.setsB[i]>7){
        	var set=i+1;
        	this.error="Revise los puntos del set "+set+" del equipo B, no pueden se superior a 7"
        	return false;
        }else if(this.setsB[i]==7){
        	var set=i+1;
        	if((7-this.setsA[i]) >2){
        		this.error="Revise los puntos del set "+set+" del equipo A y B, si se ha llegado a 7 puntos la diferencia con el equipo contrincante a de ser de 2 o 1 en caso de timeBreak"
        	return false;

        	}
        } else if((this.setsB[i]<6) && (this.setsA[i]<6)){
        	var set=i+1;
        	this.error="Revise los puntos del set "+set+" del equipo A y B, ninguno a llegado a 6 puntos, los necesarios para acabar ese set"
        	return false;

        }
      }

      if(this.error.length==0){
      	return true;
      }

     
    },

    guardar(){
     if(this.checkForm()==true){
    	var equipoA={};
    	var equipoB={};
      var ganadosA=0;
      var ganadosB=0;

      for ( var i = 0; i < this.setsA.length; i ++){
        if(this.setsA[i]> this.setsB){
          ganadosA++;
        }else{
          ganadosB++;
        }

      }
      equipoA.setsGanados=ganadosA;
      equipoB.setsGanados=ganadosB;
    	equipoA.sets=this.setsA;
    	equipoB.sets=this.setsB;
    	this.resultado.equipoA=equipoA;
    	this.resultado.equipoB=equipoB;

    	 HTTP.put(`games/${this.game.idGame}`,this.resultado)
              .then(this._successHandler)
              .catch(this._errorHandler)

    	}else{
         this.$swal('Alerta!', this.error, 'error')
      }
    },

     _successHandler(response) {
      this.$swal('Listo!', "Sus resultados han sido añadidos correctamente", 'success')
      this.$router.replace({ name: 'TennisResult', params: { id:this.game.idGame}})
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
     custom(hora){
      return hora.substring(0,5)
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
 	display: inline-block;
 	margin-right:20px;

 }


 .info{
  color:white;
  margin-bottom:10px;


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

}
.letra{
	margin-left:20px;
}

.form-group conj1{
	display:inline-block;
}

.w3-large {
    font-size: 18px!important;
    margin-left: 10px;
}

</style>
