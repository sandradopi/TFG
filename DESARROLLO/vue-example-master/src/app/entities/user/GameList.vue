<template>
  <div class="information message2">
        <h1 class="title">{{this.login}} {{this.tipo}}</h1>  
  </div>

</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'


export default {
  components: { },
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
      if (this.tipo=='organizados'){
        debugger
        HTTP.get(`users/${this.login}/organizados`) 
          .then(response => { this.games= response.data
                return response })
          .catch(err => {
                  this.error = err.message})

      }

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


div.message.information{background: #fb887c;}

div.message2 {
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:63%;
  height:70%;
  margin-right:21.5px;
  float:right;

}


div.message2.information{background: #fb887c;}


</style>
