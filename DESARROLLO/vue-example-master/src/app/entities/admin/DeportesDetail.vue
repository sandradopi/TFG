<template>

  <div class="information message">
  <h2>Information</h2>
  <p>This is an 'information message' div. It helps people get to know the site a little better. </p>
</div>
  
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'



export default {
  components: {},
  data() {
    return {
      
     

    }
  },
  watch: {
    '$route': 'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    
  },
  methods: {
    fetchData() {
  
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
