<template>

  <div v-if="bol"class="information message">
    <button @click="hide"> X </button>             
    <h2>{{this.sport.type}}</h2>
    <br>
    <h5>Ubicaciones:</h5>
    <li type="disc" v-for=" location in this.sport.locations" :key="location.idLocation"> {{location.name}}</li>
    <button class="editar"@click="editar()"> Editar</button>  
    <button class="eliminar"@click="eliminar()"> Eliminar</button> 
</div>
  
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'



export default {
  components: {},
  props:{
    idDeporte:null,
    num:0

    
  },
  data() {

    return {
      sport:{},
      bol:true,
   
     

    }
  },
  watch: {
    '$route': 'fetchData',
     num:'fetchData',
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.fetchData()
  },
  methods: {
     fetchData() {
      this.bol=true;
       HTTP.get(`sports/${this.idDeporte}`) 
      .then(response => {
        this.sport = response.data
      
      })
      .catch(err => this.error = err.message)
    

    },
    hide(){
      this.bol=false;
    },
   

    _successHandler(response) {
      this.bol=false;
      this.$emit('CustomEventInputChanged');

    },

    _errorHandler(err) {
      this.error = err.response.data.message
      
    },
    eliminar(){
      HTTP.delete(`sports/${this.idDeporte}`)
        .then(this._successHandler)
        .catch(this._errorHandler)

    }
  }
}
</script>

<style scoped lang="scss">


div.message {
  
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:40%;
  float:right;
  margin-right:150px;
  
  
  -webkit-transition: all .5s ease;
     -moz-transition: all .5s ease;
      -ms-transition: all .5s ease;
       -o-transition: all .5s ease;
          transition: all .5s ease;
}
div.message:hover{
  box-shadow: 0 15px 20px rgba(10,0,10,.3);
  -webkit-filter: brightness(110%);
}

div.message.information{background: #fb887c;}

td {
    
    color:grey;
    margin-left:auto;
    margin-right:auto;
  }

  button{
  width: 10%;
  float:right;
  background: #fb887c;
  border-radius: 5px;
  border: 0.1px solid #fb887c;
  font-size:1.8em;
  color: white;
}

.editar{
  width: 47%;
  float:left;
  background: #fff;
  padding: 0.6em; 
  margin-top: 15%;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1em;
  color: grey;
}


.eliminar{
   width: 47%;
   float:right;
  background: #fff;
  padding: 0.6em; 
  margin-top: 15%;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1em;
  color: grey;
}


</style>
