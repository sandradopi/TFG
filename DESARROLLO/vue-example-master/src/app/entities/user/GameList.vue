<template>
  <div class="information message2">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <div class="w3-container" v-for=" game in this.games" :key="game.idGame">
        <b-btn class="w3-bar" @click="verDetallePartido(game)">
           <img v-if="game.sport.type=='Futbol'"src="futbol.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Tennis'"src="ten.jpg" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Paddel'"src="paddel.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Baloncesto'"src="bal1.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <div class="w3-bar-item">
              <span class="w3-large">{{game.location.name}}</span><br>
              <span>{{game.date}} ({{custom(game.timeStart)}}-{{custom(game.timeEnd)}})</span>
          </div>
        </b-btn>
    </div>
     <div class="w3-container" v-for="comentario in this.comentarios" >
        <b-btn class="w3-bar" v-if="comentario!=null">
           <div class="w3-bar-item">
              <span class="w3-large">{{comentario}}</span><br>
          </div>
        </b-btn>
    </div>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import vueCustomScrollbar from 'vue-custom-scrollbar'



export default {
  components: {vueCustomScrollbar},
  props:{
    tipo:null,
    login:null
  },
  data() {
    return {
      games:null,
      pasado:null,
      bool:false,
      jugados:null,
      comentarios:null

  
    }
  },
  watch: {
    '$route': 'fetchData',
     tipo: 'fetchData',

    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes

    this.fetchData()
  },

  computed: {
    
      
  },
  methods: {
    fetchData() {
      this.games=null
      this.comentarios=null
      if (this.tipo=='organizados'){
          HTTP.get(`users/${this.login}/organizados`) 
                .then(response => { this.games= response.data
                      return response })
                .catch(err => { this.error = err.message})
      }
      else if (this.tipo=='proximo'){
          HTTP.get(`users/${this.WhatLogin()}/proximos`) 
                .then(response => { this.games= response.data
                      return response })
                .catch(err => { this.error = err.message})
      }else if(this.tipo=='jugados'){
          HTTP.get(`users/${this.WhatLogin()}/jugados`) 
                .then(response => { this.games= response.data
                      return response })
      
       }else if(this.tipo=='comentarios'){

          HTTP.get(`users/${this.WhatLogin()}/comentarios`) 
                .then(response => { this.comentarios= response.data
                      return response })

      }

    },

    custom(hora){
      return hora.substring(0,5)
    },
    verDetallePartido(game){
      if(this.tipo=='proximo'){
        this.$router.replace({ name: 'GameDetail', params: { id:game}})
      }else if(this.tipo=='jugados'){

        if(game.result==null){
          this.$router.replace({ name: 'GameDetail', params: { id:game, bol:true}})
        }else{
         this.$router.replace({ name: 'FutbolResult', params: { id:game.idGame}})}
      } else if(this.tipo=='organizados'){

        var f=new Date();
        var cad=f.getHours()+":"+f.getMinutes()+":"+f.getSeconds(); 
        var n = f.toISOString().substring(0,10);
       if((game.date<n)|| (game.date ==n && game.timeStart<cad)){
         if(game.result==null){
          this.$router.replace({ name: 'GameDetail', params: { id:game, bol:true}})
        }else{
         this.$router.replace({ name: 'FutbolResult', params: { id:game.idGame}})}
        
      }else{

        this.$router.replace({ name: 'GameDetail', params: { id:game}})
      }
      
    }
    },
    
     WhatLogin() {
      return auth.user.login
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


div.message2 {
  overflow: scroll;
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  width:63%;
  height:70%;
  margin-right:21.5px; 
  float:right;

}



.w3-ul li:last-child {
    
    border-bottom: none;
    background: white;
    color: #6c757d;
    border-radius: 25px;
}

ul.w3-ul.w3-card-4{
   border-radius: 25px;
    background: white;

}

.w3-bar{
    border-radius: 25px;
    background: white;
    color: #6c757d;
    margin-top:10px;
    border-color:white;
    box-shadow:0 2px 5px rgba(0,0,0,.3);

}


.swal-title {
    color: rgba(0,0,0,.65);
    font-weight: 500px;
    text-transform: none;
    position: relative;
    display: block;
    padding: 13px 16px;
    font-size: 27px;
    line-height: normal;
    text-align: center;
    margin-bottom: 0;
}

</style>
