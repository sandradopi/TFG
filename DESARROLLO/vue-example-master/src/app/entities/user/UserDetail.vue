<template>
<div>
   <div class="float-right">
      <b-btn        class="button"
                    variant="primary"
                    @click="back()"><span>Atrás</span></b-btn>
     <b-btn
                    class="button1"
                    v-if=" this.control==false"
                    :to="{ name: 'UserUpdate', params: { id: user.login }}"
                    @click="Editado()"><span>Editar</span></b-btn> <!--Solo podemos editar las notificaciones del usuario que esta logueado-->
      <b-btn
                    class="button"
                    v-if="this.control==true "
                    @click="Save()"><span>Guardar</span></b-btn> 
  </div>

  <div class= "datosUsuario">
    

     <h1 class="title" :key="user.name"> Hola {{user.name}}!</h1>  

     <h4 class="subtitle">
      </br>
                  <div class="subdatos" >
                  <p class="subtitle-tag">Nombre: {{this.user.name}} </p>                  
                  <p class="subtitle-tag">Apellidos: {{this.user.surname1}} {{this.user.surname2}} </p>                 
                  <p class="subtitle-tag">Fecha de nacimiento: {{this.user.birthday}} </p>
                  <p class="subtitle-tag">Ciudad: {{this.user.city}} </p>
                  <p class="subtitle-tag">Login: {{this.user.login}} </p>
                  <p class="subtitle-tag">Email: {{this.user.email}} </p>
                  <p class="subtitle-tag">Experiencia: {{this.user.experience}}  <star-rating class="star"
                                                                                              v-model="rating"
                                                                                               v-bind:star-size="30"
                                                                                               :read-only="true"
                                                                                            ></star-rating></p>

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
      control:false, //Es una variable de control para saber cuando tiene que aparecer el boton de Editar y el de Save
      rating:null,

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
    .then(response => {
       this.user = response.data
       return response
       
     })
    .then(response => {
        this.rating = response.data.experience
      })


     .catch(err => {
       this.error = err.message
     })

  
    },
     WhatLogin() {
      return auth.user.login
    },


    Editado() {
      this.control=true;
    },

     Save() {

      this.control=false;
      HTTP.put(`users/${this.$route.params.id}/${this.user.noti}`)
      .then(this._successHandler)
      .catch(err => {
      this.error = err.message
         })
  
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
    margin-left:70px;
    padding:3%;
    width:90%;

  }
  
  .title{
    margin-left: auto;
    margin-right: auto;
    font-size: 50px;
    font-weight: 700;
    color: #17a2b8;
  }

  .subtitle {
      font-size: 15px;
      font-weight: 600;
      color: #17a2b8;;
      .subtitle-tag1 {
       display: inline;
        }
    }

.float-right{
  margin-top:50px;
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
   background-color: #f4511e;
   float:right;
   
 }


</style>
