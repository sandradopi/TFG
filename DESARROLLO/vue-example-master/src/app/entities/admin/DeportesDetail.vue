<template>

  <div class="information message">
  <h2>{{this.sport.type}}</h2>
  <br>
  <h5>Ubicaciones:</h5>
  <li type="disc" v-for=" location in this.sport.locations" :key="location.idLocation"> {{location.name}}</li>
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
    
  },
  data() {

    return {
      sport:{}
     

    }
  },
  watch: {
    '$route': 'fetchData',
    idDeporte:'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.fetchData()
  },
  methods: {
     fetchData() {
       HTTP.get(`sports/${this.idDeporte}`) 
      .then(response => {
        this.sport = response.data
      
      })
      .catch(err => this.error = err.message)
    

    },
   

    _successHandler(response) {
      this.fetchData()

    },

    _errorHandler(err) {
      this.error = err.response.data.message
      
    }
  }
}
</script>

<style scoped lang="scss">


div.message {
  
  padding: 10px;
  padding-left: 35px;
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









</style>
