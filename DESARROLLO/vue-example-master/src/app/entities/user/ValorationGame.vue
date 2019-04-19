<template>
  <div class="content"> 
    <b-modal
        class="formulario"
        id="modalPrevent"
        ref="modal"
        title="Comentario"
        @ok="handleOk"
        @shown="clearName">
         <form @submit.stop.prevent="handleSubmit">
          
           <b-form-textarea
              id="textarea"
              v-model="comment"
              placeholder="Sobre este jugador quiero decir..."
              rows="3"
              max-rows="6"
            ></b-form-textarea>

        </form>
    </b-modal>
     <b-btn class="button" @click="guardar()"><span>Guardar</span></b-btn> 
      <h1 class="title">Valoraciones del Partido</h1> 

      <div class="information message" v-if="loading==true">
        <h2 class="title1"> Información</h2>  
        <h6 >Creador: {{this.game.creator.name}} {{this.game.creator.surname1}} {{this.game.creator.surname2}}</h6>
        <h6>Deporte: {{this.game.sport.type}}</h6>
        <h6>Ubicación: {{this.game.location.name}}</h6>
        <h6>Horario: {{custom(this.game.timeStart)}}-{{custom(this.game.timeEnd)}} </h6>
        <h6>Fecha: {{this.game.date}}</h6>
      </div>
      <div class="information message2">
       

     <div class="form">
		  <b-form
		      class="app-form"
		      >
  
	 <b-form-group>
   <span class="w3-large">Valoración del Partido:</span><br>
         <multiselect 
            class="multisele"
            v-model="valorationGame" 
            :options="optionsC" 
            :searchable="true" 
            :close-on-select="true" 
            :show-labels="false" 
            placeholder="Nota del Partido"></multiselect>
      <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">


	<div class="info">
	<span class="w3-large">Valoraciones Jugadores A:</span><br>
    <div class="bloque" v-for=" playerG in this.playersA" :key="playerG.idPlayer">
     <img class="foto"src="http://i.pravatar.cc/250?img=41" class="foto" style="width:60px">
     <div class="conj">	
       <b-btn class="button2" @click="activarModal(playerG.idPlayer)"><font-awesome-icon icon="comment-dots"style="font-size:30px;"/></b-btn>
              <span class="w3-large1">{{playerG.player.login}}</span><br>

          <multiselect 
            class="multiselePla"
            v-model="valorationPlayer[playerG.idPlayer]" 
            @close="valorar(playerG.idPlayer)"
            :options="optionsC" 
            :searchable="true" 
            :close-on-select="true" 
            :show-labels="false" 
            placeholder="Valoracion"></multiselect>
           
		      </div>
    </div>
	</br>
	</br>
  <span class="w3-large">Valoraciones Jugadores B:</span><br>
     <div class="bloque" v-for=" playerG in this.playersB" :key="playerG.idPlayer">
     <img class="foto"src="http://i.pravatar.cc/250?img=40" class="foto" style="width:60px">
     <div class="conj">	
       <b-btn class="button2" @click="activarModal(playerG.idPlayer)"><font-awesome-icon icon="comment-dots"style="font-size:30px;"/></b-btn>
              <span class="w3-large1">{{playerG.player.login}}</span><br>
            <multiselect 
            class="multiselePla"
            v-model="valorationPlayer[playerG.idPlayer]" 
            @close="valorar(playerG.idPlayer)"
            :options="optionsC" 
            :searchable="true" 
            :close-on-select="true" 
            :show-labels="false" 
            placeholder="Valoracion"></multiselect>
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
      optionsC:['1','2','3','4','5'],
      valorationGame:'',
      player:{},
      valorationPlayer:[],
      valoraciones:[],
      comment:'',
      valoracion:{},
      login:'',
      jugador:{},
      usuario:{},
      posicion:0,
     


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
          .then(this.recuperarMyPlayer)
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
   recuperarMyPlayer(){
      HTTP.get(`players/${this.game.idGame}/${this.WhatLogin()}`) 
          .then(response => { this.player = response.data
                 return response })
          .catch(err => { this.error = err.message})
   },

	 DividirEnEquipos(){

    	 for ( var i = 0; i < this.players.length; i ++){
    	 	if((this.players[i].equipo=='A')&& (this.players[i].player.login!= this.player.player.login)){
    	 		this.playersA.push(this.players[i]);
    	 	}else if ((this.players[i].equipo=='B') && (this.players[i].player.login!= this.player.player.login)){ 
    	 		this.playersB.push(this.players[i]);
    	 	}

    	 }
     

	 },
   valorar(id){
   var valoracion=new Object();
    var bol=false;
      for ( var i = 0; i < this.valoraciones.length; i ++){
        
        if(this.valoraciones[i].player==id){
          bol=true;
          this.valoraciones[i].valoration=this.valorationPlayer[id];
        }
      }
      if(bol==false){
         valoracion.player=id;
         valoracion.valoration=this.valorationPlayer[id];
         valoracion.user=null;
         this.valoraciones.push(valoracion)
        
     }
     
   },

   checkForm () {

      if (!this.valorationGame) {
        this.error="Introduzca la valoracion del partido"
        return false;
      }else{
        return true;
      }
       

    },


    guardar(){
      if(this.checkForm()==true){
    	 for ( var i = 0; i < this.valoraciones.length; i ++){
        this.posicion;
         HTTP.get(`players/findPlayer/${this.valoraciones[i].player}`) 
              .then(response => {this.jugador = response.data
                    return response})
              .then(response => {this.usuario = response.data.player})
              .then(this.subirValoracion)
              .catch(err => { this.error = err.message})

       }

    	 HTTP.put(`players/${this.player.idPlayer}/${this.valorationGame}`)
              .then(this._successHandler)
              .catch(this._errorHandler)
      }else{
        this.$swal('Alerta!', this.error, 'error')
      }
    	
    },
    subirValoracion(){

    this.valoraciones[this.posicion].user=this.usuario;
    this.valoraciones[this.posicion].player=this.jugador;
    HTTP.post('playersValoration', this.valoraciones[this.posicion]) 
          .then(this._successHandler)
          .catch(this._errorHandler)
    this.posicion++;
    },
   
    activarModal(id){
     this.login=id;
     this.$refs.modal.show();
   },

    _successHandler(response) {
      this.$router.replace({ name: 'Game'})
    },

    clearName() {
        this.comment = "";

      },
       custom(hora){
      return hora.substring(0,5)
    },

      handleOk(evt) {
        // Prevent modal from closing
        evt.preventDefault()
        if (!this.comment) {
           this.$swal('Alerta!', "Escriba algo referido a este jugador", 'error')
        }else{ 
          this.handleSubmit()
        }
      },

      handleSubmit() {
        var valoracion=new Object();
        var bol=false;
       for ( var i = 0; i < this.valoraciones.length; i ++){
       
        if(this.valoraciones[i].player==this.login){
          bol=true;
          this.valoraciones[i].review=this.comment;
        }
      }
      if(bol==false){
       
         valoracion.player=this.login;
         valoracion.review=this.comment;
         valoracion.user=null;
         this.valoraciones.push(valoracion)
         
        
     }
      
       this.confirmacion();

      },

       confirmacion(){
        this.clearName()
        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
        this.$refs.modal.hide();
        this.$swal('Listo!', "Su comentario se ha guardado correctamente.", 'success')
      })
    
    }, 


     _successHandler(response) {
       this.$swal('Listo!', "Su valoraciones han sido enviadas correctamente", 'success')
      if(this.game.result==null){
        this.$router.replace({ name: 'GameDetail', params: { id:this.game, bol:true}})
      }else{
      this.$router.replace({ name: 'FutbolResult', params: { id:this.game.idGame}})
    }
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
    float: left;
    width: 90%;
}

.multisele{
  width:200px;
}
.multiselePla{
  width:100px;
}
.w3-large1{
  font-size:1.2em;

}
.button2{
     margin-top: 5px;
    float: right;
    border-radius: 10px;
    margin-top: 25px;

}
.formulario{
  color:#17a2b8;
}

</style>
