<template>
  <div class="content"> 
   
      <h1 class="title">Actividades</h1>  
      <div class="information message2">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <li v-if="this.activities.length==0">No hay actividades en su tablón</li>
        <div class="w3-container" v-for=" activity in this.activities">
          <b-btn class="w3-bar" @click="verDetalleActividad(activity)">
              <img v-bind:src="getImagen(activity.friend.rutaImagen)" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
              <div class="conj">
                <span class="w3-large">{{activity.friend.name}} {{activity.friend.surname1}} {{activity.friend.surname2}}</span><br>
                <span class="rectangulo">{{activity.action}} ({{customDate(activity.date)}})</span>
              </div>
          </b-btn>
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
  data() {
    return {
    activities:[]
      
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
        HTTP.get(`users/activities/${this.WhatLogin()}`) 
          .then(response => { this.activities = response.data
                 return response })
          .catch(err => { this.error = err.message})
   
     
    },
    verDetalleActividad(activity){
      if(activity.comment==false){
      this.$router.replace({ name: 'GameDetail', params: { id:activity.idActivities,bol:false,back:false,activity:true}})
    }else{
       this.$router.replace({ name: 'CommentGame', params: { id:activity.idActivities}})
    }

    },
     customDate(date){
      return date.substring(0,10)
    },
     customHour(hour){
      return hour.substring(12,16)
    },
    getImagen(path){
      return baseURL + "users/imagenes/" + path;
    },
    
   
    _successHandler4(response) {
      this.fetchData()
    },
      WhatLogin() {
      return auth.user.login
    },
   
    },
    _errorHandler(err) {
      this.error = err.response.data.message
      
    },
    
    back() {
      this.$router.go(-1)
    },
  
}
</script>

<style scoped lang="scss">
.responsive{
  height:10%;
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
    height:71.5%;
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
 
div.message2 {
  
  padding: 2%;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:80%;
  float:left;
  height:72%;
  margin-left:10%;
  margin-top:20px;
  overflow: scroll;
}
div.message2.information{background: #17a2b8;}
.w3-bar{
    border-radius: 25px;
    background: white;
    color: #6c757d;
    margin-top:10px;
    border-color:white;
    box-shadow:0 2px 5px rgba(0,0,0,.3);

}
.conj{
  text-align:left !important;
}

</style>