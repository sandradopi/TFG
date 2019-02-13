<template>
<div>
   <div class="float-right">
      <b-btn        class="button"
                    variant="primary"
                    @click="back()"><span>Atrás</span></b-btn>
  </div>

<div class= "sport" v-for="sport in sports" :key="sport.idSport">
    <div class= "tittle">
    {{ sport.type}}
      </div>

 

</div>
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
      sports:null,
     

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
      HTTP.get('sports')
        .then(response => {
       this.sports = response.data
       
     })
     .catch(err => {
       this.error = err.message
     })
    .finally(() => this.loading = false)
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
</script>

<style scoped lang="scss">
.tittle{
  color:black;
}


 .button {
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

  margin-right:87px;

}

.button span{
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span{
  padding-right: 20px;
}

.button:hover span:after{
  opacity: 1;
  right: 0;
}



</style>
