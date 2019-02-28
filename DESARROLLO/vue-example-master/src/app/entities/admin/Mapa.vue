<template>

<div id="mapid" ref="mapElement"></div>

</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import L from 'leaflet'



export default {
	mounted() {
    var mymap = L.map(this.$refs['mapElement']).setView([51.505, -0.09], 13);
  components: {},
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
    	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={}', {
    		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',maxZoom: 18,id: 'mapbox.streets',accessToken: ''}).addTo(mymap);
    
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




</style>
