<template>
  <div class="information message2">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <div class="w3-container" v-for=" (game,index) in this.games" :key="game.idGame">
        <b-btn class="w3-bar" @click="verDetallePartido(game)">
           <img v-if="game.sport.type=='Futbol'"src="futbol.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Tennis'"src="ten.jpg" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Paddel'"src="paddel.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Baloncesto'"src="bal1.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <div class="w3-bar-item">
              <span class="w3-large">{{game.location.name}}</span><br>
              <span>{{game.date}} ({{custom(game.timeStart)}}-{{custom(game.timeEnd)}})</span>

          </div>
          <span class="recomendacion" v-if="mensaje!=null">{{mensaje[index]}}</span>
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
    login:null,
    typeR:''
  },
  data() {
    return {
      games:[],
      gamesRecomendados:null,
      pasado:null,
      bool:false,
      jugados:null,
      comentarios:null,
      mensaje:null,


  
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
      this.mensaje=null
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

       }else if(this.tipo=='recomendados'){
         
          HTTP.get(`users/${this.WhatLogin()}/recomendados`) 
                .then(response => { this.gamesRecomendados = response.data
                        return response.data})
                  .then(this.unionGamesRecomendados)
      }

    },
    unionGamesRecomendados(){ 

     var gameR=new Array();
     var msg=new Array();
    if(this.gamesRecomendados.length>0){
    if(this.typeR=='jugadores'){
      for ( var x = 0; x < this.gamesRecomendados[0].games.length; x ++){
         gameR.push(this.gamesRecomendados[0].games[x]);
         msg.push(this.gamesRecomendados[0].mensaje);
         
        
       }
          this.games=gameR;
          this.mensaje=msg;
       
     
     


      }else if(this.typeR=='deportes'){
      for ( var x = 0; x < this.gamesRecomendados[1].games.length; x ++){
         gameR.push(this.gamesRecomendados[1].games[x]);
         msg.push(this.gamesRecomendados[1].mensaje);
         
        
       }
          this.games=gameR;
          this.mensaje=msg;
       
      
       }else if(this.typeR=='localizaciones'){
      for ( var x = 0; x < this.gamesRecomendados[2].games.length; x ++){
         gameR.push(this.gamesRecomendados[2].games[x]);
         msg.push(this.gamesRecomendados[2].mensaje);
         
        
       }
          this.games=gameR;
          this.mensaje=msg;
       
      }

      }


     
    },

    custom(hora){
      return hora.substring(0,5)
    },
    verDetallePartido(game){
      if(this.tipo=='proximo'||this.tipo=='recomendados'){
        this.$router.replace({ name: 'GameDetail', params: { id:game.idGame, back:true}})
      }else if(this.tipo=='jugados'){

        if(game.result==null){
          this.$router.replace({ name: 'GameDetail', params: { id:game.idGame, bol:true,back:true}})
        }else{
          if(game.sport.type=='Futbol'|| game.sport.type=='Baloncesto'){
              this.$router.replace({ name: 'FutbolResult', params: { id:game.idGame}})
            }else if (game.sport.type=='Tennis'||game.sport.type=='Paddel'){
              this.$router.replace({ name: 'TennisResult', params: { id:game.idGame}})}
            }

       
      } else if(this.tipo=='organizados'){

        var f=new Date();
        var cad=f.getHours()+":"+f.getMinutes()+":"+f.getSeconds(); 
        var n = f.toISOString().substring(0,10);
       if((game.date<n)|| (game.date ==n && game.timeStart<cad)){
         if(game.result==null){
          this.$router.replace({ name: 'GameDetail', params: { id:game.idGame, bol:true,back:true}})
        }else{
          if(game.sport.type=='Futbol'|| game.sport.type=='Baloncesto'){
              this.$router.replace({ name: 'FutbolResult', params: { id:game.idGame}})
            }else if (game.sport.type=='Tennis'|| game.sport.type=='Paddel'){
              this.$router.replace({ name: 'TennisResult', params: { id:game.idGame}})}
            }
        
      }else{

        this.$router.replace({ name: 'GameDetail', params: { id:game.idGame,back:true}})
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

.recomendacion {
    color: #17a2b8;
    font-size: 0.65em;
    /* margin-top: 100px; */
    /* padding: 10px; */
    /* padding-top: 20px; */
    float: right;
    margin-right: 20px;
    margin-top: 40px;
}
</style>
