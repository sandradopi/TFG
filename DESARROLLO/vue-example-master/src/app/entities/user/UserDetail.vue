<template>
  <div>
     <div class="float-right">
        <b-btn class="button1" :to="{ name: 'UserUpdate', params: { id: this.user, boleano:this.bol}}" @click="Editado()"><span>Editar</span></b-btn> 
    </div>
    <div class="equipo">
        <h1 class="title1">Mis equipos favoritos:</h1>
        <ol id="lista2">
            <h6 class="adv" v-if="this.user.favoritos==''">No tiene guardado ningun equipo en favoritos</h6>
            <li class="usuarios" type="disc" v-for=" favorito in this.user.favoritos" :key="favorito.idTeam"> {{favorito.name}} ({{favorito.sport.type}}) </li>
        </ol> 
  
        <div class="equipo1">
          <h1 class="title1">Mis equipos:</h1>
          <ol id="lista2">
             <h6  class="adv" v-if="this.user.juego==''">Usted actualmente no está jugando en ningun equipo</h6>
            <li class="usuarios" type="disc" v-for=" juego in this.user.juego" :key="juego.idTeam"> {{juego.name}} ({{juego.sport.type}})</li>
        </ol> 
      </div>
   </div>
   <div class= "datosUsuario">
       <h1 class="title"> Datos Personales</h1>  
       <h4 class="subtitle">
          </br>
          <div class="subdatos" >
                <p class="subtitle-tag">Nombre: {{this.user.name}} </p>                  
                <p class="subtitle-tag">Apellidos: {{this.user.surname1}} {{this.user.surname2}} </p>                 
                <p class="subtitle-tag">Fecha de nacimiento: {{this.user.birthday}} </p>
                <p class="subtitle-tag">Ciudad: {{this.user.city}} </p>
                <p class="subtitle-tag">Login: {{this.user.login}} </p>
                <p class="subtitle-tag">Email: {{this.user.email}} </p>
                <p class="subtitle-tag">Experiencia: {{this.user.experience}}  
                <star-rating class="star" v-model="rating" v-bind:star-size="30" :read-only="true"></star-rating></p>
          </div>
       </h4>    
    </div>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Multiselect from 'vue-multiselect'
import Vue from 'vue'
import StarRating from 'vue-star-rating'


export default {
  components: {  Multiselect,  StarRating},
  data() {
    return {
      user:{},
      rating:null,
      bol:false,

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

     HTTP.get(`users/${this.$route.params.id}`) 
          .then(response => {this.user = response.data
                return response })
          .then(response => { this.rating = response.data.experience})
          .catch(err => { this.error = err.message})
  
    },

     WhatLogin() {
      return auth.user.login
    },


    Editado() {
      this.control=true;
      this.bol=true;
    },


    _successHandler(response) {
      this.fetchData()
    },

    back() {
      this.$router.go(-1)
    },
    
    _errorHandler(err) {
      this.error = err.response.data.message
    }
  }
}
</script>

<style scoped lang="scss">

.datosUsuario{
  width: 30%;
  background: #f3f3f3;
  padding: 1em;
  border-radius: 3px;
  font-family: 'Lato', sans-serif;
  margin:0;
  margin-left:120px;
  margin-top:80px;
  margin-bottom:100px;



  }

  .equipo{
  float:right;
  padding: 1em;
  width: 40%;
  background: #f3f3f3;
  border-radius: 3px;
  font-family: 'Lato', sans-serif;
  margin-right:60px;

  }

  .equipo1{

    width: 100%;
    background: #f3f3f3;
    border-radius: 3px;
    font-family: 'Lato', sans-serif;

  }
  
  .title1{
   font-family: 'Lato', sans-serif;
    margin-left:10px;
    font-size: 30px;
    font-weight: 200;
    color: #17a2b8;
    margin-top:20px;
    color:#fb887c;
  
  }

  .title{
    font-family: 'Lato', sans-serif;
    margin-left:80px;
    font-size: 30px;
    font-weight: 200;
    color: #17a2b8;
    margin-top:20px;
    color:#fb887c;
  }

  .subtitle {
      font-size: 15px;
      font-weight: 600;
      color: #17a2b8;
      .subtitle-tag1 {
       display: inline;
        }
    }

.float-right{

  margin-right:5%;
}

.button, .button1 {
  display: inline-block;
  border-radius: 4px;
  background-color: #17a2b8;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 17px;
  padding: 5px;
  width: 80px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;

}

.button span, .button1 span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after , .button1 span:after{
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span,.button1:hover span {
  padding-right: 20px;
}

.button:hover span:after, .button1:hover span:after{
  opacity: 1;
  right: 0;
}
.button1{
   background-color: #fb887c;
   float:right;
   
 }
 #lista2 {
    counter-reset: li; 
    list-style: none; 
    *list-style: decimal; 
    font: 15px 'trebuchet MS', 'lucida sans';
    padding: 0;
    margin-top: 3%;
    text-shadow: 0 1px 0 rgba(255,255,255,.5);
    margin-left:2%;
    width:90%;
    margin-left:25px;


}

#lista2 ol {
    margin: 0 0 0 2em; 
}

#lista2 li{
    position: relative;
    display: block;
    padding: .9em .9em .6em 2em;
    margin: .9em 0;
    background: #ddd;
    color: #444;
    text-decoration: none;
    border-radius: .3em;
    transition: all .3s ease-out;  
    height: 50px; 
    font-size:1.2em;

}

#lista2 li:hover{
    background: #eee;
}

#lista2 li:hover:before{
    transform: rotate(360deg);  
}

#lista2 li:before{
    content: counter(li);
    color:white;
    counter-increment: li;
    position: absolute; 
    left: -1.3em;
    top: 50%;
    margin-top: -1.3em;
    background:#17a2b8;
    height: 2.7em;
    width: 2.5em;
    line-height: 2em;
    border: .3em solid #fff;
    text-align: center;
    font-weight: bold;
    border-radius: 3em;
    transition: all .3s ease-out;
}

.adv{
  width:100%;
  color: grey;
}

</style>
