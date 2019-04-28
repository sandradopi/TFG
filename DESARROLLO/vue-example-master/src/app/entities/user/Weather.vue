<template>
  <div>
  <weather 
        class="weather"
        api-key="7dcc22ed4c6cefe78baa7f2dd9b7a944"
        title="El tiempo"
        :latitude="this.latitud.toString()"
        :longitude="this.longitud.toString()"
        language="es"
        units="uk">
    </weather>
</div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import VueWeatherWidget from 'vue-weather-widget';
import 'vue-weather-widget/dist/css/vue-weather-widget.css';

export default {
  components: {'weather': VueWeatherWidget },
  props:{
    location:{}
    
  },
  data() {
    return {
      latitud:null,
      longitud:null,
      name:null
    }
  },
  watch: {
    '$route': 'fetchData',
    location: 'fetchData',
     name: 'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes

    this.fetchData()
  },

  methods: {
    fetchData() {

      this.latitud=this.location.latitud;
      this.longitud=this.location.longitud;
      this.name=this.location.name;

    },
    
    
     _successHandler(response) {
      this.$router.replace({ name: 'Game'})
    },
    
      WhatLogin() {
      return auth.user.login
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

.fe_currently{
  
}
</style>
