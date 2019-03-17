<template>
  <div class="content"> 
    <h1 class="title"> Detalles del partido</h1>  
    <div class="information message">
      <h6>Creador: {{this.game.creator.name}} {{this.game.creator.surname1}} {{this.game.creator.surname2}}</h6>
      <h6>Deporte: {{this.game.sport.type}}</h6>
      <h6>Ubicación: {{this.game.location.name}}</h6>
      <h6>Horario: {{this.game.timeStart}} - {{this.game.timeEnd}} </h6>
      <h6>Fecha: {{this.game.date}}</h6>
    </div>
    <div class="information message2">
    </div>
  </div>

</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'


export default {
  components: { },
  data() {
    return {
      game:{}
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
      this.game=this.$route.params.id;
    	
  
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
    height:60%;
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

 div.message {
  
  padding: 4%;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:30%;
  float:left;
  height:70%;
  margin-left:20px;
  margin-top:20px;


}


div.message.information{background: #fb887c;}

div.message2 {
  
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:60%;
  float:left;
  height:70%;
  margin-left:20px;
  margin-top:20px;

}


div.message2.information{background: #17a2b8;}


</style>
