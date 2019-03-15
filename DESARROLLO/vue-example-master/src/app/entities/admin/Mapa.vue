<template>
<div class="loc">
<div class="formulario">
	<h1 v-if="this.$route.params.id== null">Nueva Localización</h1> 
  <h1 v-if="this.$route.params.id!= null">Editar Localización</h1> 
	<div class="inp">
		<input type='text' class="searchButton" placeholder='Nombre' v-model="location.name" autofocus required >
		<input type='text' class="searchButton" placeholder='Latitud' v-model="this.latitud" autofocus required readonly onmousedown="return false;">
		<input type='text' class="searchButton" placeholder='Longitud' v-model="this.longitud" autofocus required readonly onmousedown="return false;">
    <input type='text' class="searchButton" placeholder='Coste/Hora(€)' v-model="location.costPerHour" autofocus required >
	<b-btn v-if="this.$route.params.id== null"class="button" @click="añadir()" variant="link">Añadir</b-btn>
  <b-btn v-if="this.$route.params.id!= null"class="button" @click="editar()" variant="link">Guardar</b-btn>
	</div>
</div>
<div id="mymap" class="mymap"></div>



<link rel="stylesheet" href="https://unpkg.com/leaflet@1.1.0/dist/leaflet.css" />
</div>
  

</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import L from 'leaflet'



export default {
 
  components: {},
  data() {
    return {
    	locations:null,
    	mymap:null,
    	location:{},
    	latitud:null,
    	longitud:null,
      dato:null,
      dato2:null,
      campo:null,
      campoMod:null,
      errors:''


    }
  },
  watch: {
    '$route': 'fetchData',
  },
 
  created() { //se va a lanzar siempre en una clase de componentes

  	this.fetchData();
  
    
  },

   mounted() {

    this.fetchData();
    this.mapa();

     var mymap = L.map('mymap').setView([43.34, -8.3888010], 12);
            L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(mymap);

     this.marcadores.bind(this);
     L.marker([43.311689,  -8.374329]).addTo(mymap);

     var popup = L.popup();


  function onMapClick(e) {
      this.campo= e.latlng.toString().split('(');
      this.campoMod= "Coordenadas: (" + this.campo[1];

      this.dato=this.campo.toString().split(',');
      this.latitud=this.dato[1];
      this.dato2= this.dato[2].split(')');
      this.longitud=this.dato2[0];

      popup
          .setLatLng(e.latlng)
          .setContent(this.campoMod)
          .openOn(mymap);

   
}


mymap.on('click', onMapClick.bind(this));
    


    
 },

  methods: {
    fetchData() {

 
      this.location={};
      this.latitud='';
      this.longitud='';

      if(this.$route.params.id!= null){
        this.latitud=this.$route.params.id.latitud;
        this.longitud=this.$route.params.id.longitud;
        this.location=this.$route.params.id;
      }

      HTTP.get('locations')
        .then(response => {
       this.locations = response.data
       
     })
     .catch(err => {
       this.error = err.message
     })

    
    },
    marcadores(){
     
     for ( var i = 0; i < this.locations.length; i ++){
           L.marker([this.locations[i].latitud, this.locations[i].longitud]).addTo(mymap).bindPopup(this.locations[i].name).openPopup();
        }
  
    },
    mapa(){

    },

    checkForm1 () {
      
      if (!this.location.name) {
        this.errors= "El nombre es un campo obligatorio "
        return false;
      }

       if (!this.latitud && !this.longitud) {

        this.errors= "La latitud y longitud son campos obligatorios "
        return false;
      }

      if(this.location.name && this.latitud && this.longitud){
      return true;
    }

    },

    añadir(){
    this.location.latitud=this.latitud;
    this.location.longitud=this.longitud;

    if(this.checkForm1()==true){
        HTTP.post('locations',this.location)
    	 .then(this.resetear)
        .catch(this._errorHandler)
    }else{
       Vue.notify({
          text: this.errors,
          type: 'error'})
    }

    },
    editar(){
      this.location.latitud=this.latitud;
      this.location.longitud=this.longitud;

    if(this.checkForm1()==true){
        HTTP.put(`locations/${this.location.idLocation}`,this.location) 
       .then(this.resetear)
        .catch(this._errorHandler)
    }else{
       Vue.notify({
          text: this.errors,
          type: 'error'})
    }
    },

    

    resetear(){
   
      // L.marker([this.location.latitud, this.location.longitud]).addTo(mymap).bindPopup(this.location.name).openPopup();
        this._successHandler();


    },

     guardar(){
     HTTP.put(`locations/${this.location.idLocation}`,this.location)
           .then(this.resetear)
           .catch(this._errorHandler)
    },

    

    _successHandler(response) {
      Vue.notify({
               text: "La nueva localización ha sido añadida",
               type: 'success'})
      this.fetchData()

    },
    
   
    _errorHandler(err) {
      this.error = err.response.data.message
       Vue.notify({
               text: this.error,
               type: 'error'})
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
	height:80%;
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
