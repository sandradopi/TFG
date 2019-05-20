<template>
  <div class="content"> 
   
     <b-btn class="button32" @click="back()"><font-awesome-icon icon="arrow-left"style="font-size:30px;"/></b-btn>
      <h1 class="title">Foro del Partido</h1> 
      <div class="information message" v-if="loading==true">
      	<h6 >Creador: {{game.creator.name}} {{game.creator.surname1}} {{game.creator.surname2}}</h6>
        <h6>Deporte: {{this.game.sport.type}}</h6>
        <h6>Ubicación: {{this.game.location.name}}</h6>
        <h6>Fecha: {{this.game.date}}</h6>
        <h6>Horario: {{custom(this.game.timeStart)}}-{{custom(this.game.timeEnd)}}</h6> 
      </div>
     
       <div class="information message1" v-if="loading==true">
       	 <h2 class="title1"> Comentarios</h2> 
       	  <b-btn class="addbutton" @click="añadirComentario()"><font-awesome-icon icon="plus"style="font-size:30px;"/></b-btn> 
      	 <b-form-textarea
              id="textarea"
              class="textarea"
              v-model="comment"
              placeholder="Quiero comentar sobre este partido..."
              rows="3"
              max-rows="6"
            ></b-form-textarea>
      <GameList v-bind:game="this.game.idGame" v-bind:tipo="this.tipo" v-bind:typeR="this.typeR" v-bind:login="this.WhatLogin()">
      </GameList>
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
import GameList from '../../entities/user/GameList'

export default {
  components: { Multiselect, Weather,GameList},
 
  data() {
    return {
      game:{},
      loading:false,
      comment:'',
      typeR:'',
      tipo:'commentGame',
      user:null,
      comentario:{}
      
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
          .then(this.changeLoading)
          .catch(err => { this.error = err.message})

           HTTP.get(`users/${this.WhatLogin()}`) 
          .then(response => { this.user = response.data
                 return response })
          .catch(err => { this.error = err.message})
      
    },
    changeLoading(){
    	this.loading=true;
    	return;
    },

     _successHandler(response) {
      this.comment='';
      this.fetchData()
    },
     custom(hora){
      return hora.substring(0,5)
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
    añadirComentario(){
    	 if (!this.comment) {
           this.$swal('Alerta!', "Escriba algo su commentario", 'error')
        }else{ 
          this.$swal("¿Estás seguro de quierer dejar de editar tu comentario? Luego no podrás cambiarlo", {
          dangerMode: true,
          buttons: true
        }).then((result) => {
            if(result) {
            	
            	
            	console.log(this.comentario)
            	this.comentario.contentComment=this.comment;
            	this.comentario.fromUser=this.user;
            	this.comentario.gameComment=this.game;
				console.log(this.comentario)
                this.$swal('Listo!', "Comentario añadido correctamente", 'success')
                this.añadir();
                
          }
        })
    
          
        }
    },
    añadir(){
    	return HTTP.post(`comments/game`, this.comentario) 
          					.then(this._successHandler)
          					.catch(this._errorHandler)
    },


   
  }
}
</script>

<style scoped lang="scss">
.addbutton{
	float:right;
	background:#fb887c;
	border-color:#fb887c;
	border-radius:100px;
	width:7%;

}

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
    float:left;
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
div.message1 {
  
  padding: 2%;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:65%;
  float:left;
  height:72%;
  margin-left:20px;
  margin-top:20px;
}
div.message1.information{background: #17a2b8;}
.button32{
  float:left;
  width:6%;
  margin-left:20px;
}

div.message2 {
    width: 100%;
    margin-right: 0;
    box-shadow: none;
    height:60%;
    
}

.textarea{
    height: 81px;
    width: 90%;
    margin-left: 5%;
}


</style>