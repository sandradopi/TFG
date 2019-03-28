<template>
  <weather 
        api-key="7dcc22ed4c6cefe78baa7f2dd9b7a944"
        title="Weather"
        latitude="43.340754"
        longitude="-8.437157"
        language="es"
        units="uk">
    </weather>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import VueWeatherWidget from 'vue-weather-widget';
import 'vue-weather-widget/dist/css/vue-weather-widget.css';

export default {
  components: {'weather': VueWeatherWidget },
  data() {
    return {
     
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

</style>
