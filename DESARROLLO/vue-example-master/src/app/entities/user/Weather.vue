<template>
  <div>
  <weather 
        class="weather"
        api-key="7dcc22ed4c6cefe78baa7f2dd9b7a944"
        :title="this.titulo"
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
      name:null,
      titulo:''
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

      var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
      var diasSemana = new Array("Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sáb");
      var f=new Date();
      var f2=new Date();
      f2.setDate(f.getDate()+parseInt(7));

      this.titulo=this.location.name+": "+diasSemana[f.getDay()] + ", " + f.getDate() + " de " + meses[f.getMonth()] + " de " + f.getFullYear()+" - "+diasSemana[f.getDay()] + ", " + f2.getDate() + " de " + meses[f.getMonth()] + " de " + f.getFullYear();
   


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
