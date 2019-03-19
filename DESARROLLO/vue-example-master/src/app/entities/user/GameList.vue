<template>
  <div class="information message2">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <div class="w3-container" v-for=" game in this.games" :key="game.idGame">
        <b-btn class="w3-bar" :to="{ name: 'GameDetail', params: { id: game}}">
           <img v-if="game.sport.type=='Fútbol'"src="futbol.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Tennis'"src="ten.jpg" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Paddel'"src="paddel.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <img v-if="game.sport.type=='Baloncesto'"src="bal1.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           <div class="w3-bar-item">
              <span class="w3-large">{{game.location.name}}</span><br>
              <span>{{game.date}} ({{custom(game.timeStart)}}-{{custom(game.timeEnd)}})</span>
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
      games:null
  
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
      if (this.tipo=='organizados'){
          HTTP.get(`users/${this.login}/organizados`) 
                .then(response => { this.games= response.data
                      return response })
                .catch(err => { this.error = err.message})
      }

    },

    custom(hora){
      return hora.substring(0,5)
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



</style>
