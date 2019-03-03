<template>
<div class="loc">
<div class="formulario">
	<h1>Nueva Localización</h1> 
	<div class="inp">
		<input type='text' class="searchButton" placeholder='Nombre' v-model="location.name" autofocus required >
		<input type='text' class="searchButton" placeholder='Latitud' v-model="this.latitud" autofocus required >
		<input type='text' class="searchButton" placeholder='Longitud' v-model="this.longitud" autofocus required >
	
	</div>

<b-btn class="button" @click="añadir()" variant="link">Añadir</b-btn>
</div>
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

     var popup = L.popup();

	function onMapClick(e) {
		var campo= e.latlng.toString().split('(');
		var campoMod= "Coordenadas: (" + campo[1];

		var dato=campo.toString().split(',');
    	this.latitud=dato[0];
    	var dato2= dato[1].split(')');
    	this.longitud=dato2[0];

	    popup
	        .setLatLng(e.latlng)
	        .setContent(campoMod)
	        .openOn(mymap);

	 
}


mymap.on('click', onMapClick);
		


    
 },
  components: {},
  data() {
    return {
    	locations:null,
    	mymap:null,
    	location:{},
    	latitud:null,
    	longitud:null,

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

    añadir(){
    this.location.latitud=this.latitud;
    this.location.longitud=this.longitud;

    HTTP.post('locations',this.location)
	.then(this._successHandler)
    .catch(this._errorHandler)
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
	margin-left:80px;
	margin-top:30px;
    position: relative;
    padding: 0;
    width: 700px;
    height: 500px;
}


.button{
  margin: 0.25em 0;
  display: block;
  outline: none;
  background: #AFC7A9;
  border: none;
  padding: 0.7em;
  border-radius: 6px;
  width: 70%;
  color: white;
  text-transform: uppercase;
  cursor: pointer;
  border-bottom: 4px solid darken(#AFC7A9, 5%);
  margin-left:70px;
  margin-top:20px;
  margin-bottom:50px;

  
}

.searchButton {
  width: 70%;
  background: #fff;
  padding: 0.6em; 
  margin-top: 0.25em;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid grey;
  font-size:0.9em;
  margin-left:70px;

 

}      

.formulario{
	margin-right:110px;
	float:right;
	background: #f3f3f3;
	width:30%;
	height:50%;
	border-radius: 6px;
	margin-top:50px;
}
.inp{
	 margin-top:50px;
}
h1 {
  color: #3a3a3a;
  text-align: center;
  margin: 1em 0;
}

</style>
