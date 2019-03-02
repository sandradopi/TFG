<template>
<div>
<div id="mymap"></div>
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.1.0/dist/leaflet.css" />
</div>
  

</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import L from 'leaflet'



export default {
  mounted() {
    var mymap = L.map('mymap').setView([36, 116], 4);
            L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(mymap);
 },
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

#mymap {
    position: relative;
    padding: 0;
    width: 600px;
    height: 600px;
}


</style>
