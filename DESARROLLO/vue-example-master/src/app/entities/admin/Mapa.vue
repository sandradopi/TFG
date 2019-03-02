<template>
<div class="loc">
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

  	 mymap = L.map('mymap').setView([43.34, -8.3888010], 12);
            L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(mymap);
     this.marcadores();


    
 },
  components: {},
  data() {
    return {
    	locations:null,
    	mymap:null

    }
  },
  watch: {
    '$route': 'fetchData',
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
  	this.fetchData();
  
    
  },

  methods: {
    fetchData() {
    
    },

    marcadores(){


     for ( var i = 0; i < this.$route.params.id.length; i ++){
       		 L.marker([this.$route.params.id[i].latitud, this.$route.params.id[i].longitud]).addTo(mymap).bindPopup(this.$route.params.id[i].name).openPopup();
        }
	
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
	margin-top:30px;
    position: relative;
    padding: 0;
    width: 600px;
    height: 600px;
}




</style>
