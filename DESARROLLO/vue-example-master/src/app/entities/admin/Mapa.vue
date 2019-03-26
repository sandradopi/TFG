<template>
  <div class="loc">
    <LocalizacionesDetail @Cerrar="hide" @Editar="edicion" @Eliminar="eliminar"v-if="(nueva==false && idLoc!=null)" v-bind:idLoc="this.idLoc" v-bind:num="this.num"></LocalizacionesDetail>
    <b-btn class="button3" v-if="this.nueva==false" @click="nuevaLoc()"variant="link">Nueva Localización</b-btn>
    <div class="formulario" v-if="this.nueva==true">
        <button  class="xbut" @click="hide"> X </button> 
      	<h1 v-if="this.bool==false">Nueva Localización</h1> 
        <h1 v-if="this.bool==true">Editar Localización</h1> 
      	<div class="inp">
          	<input type='text' class="searchButton" placeholder='Nombre' v-model="location.name" autofocus required >
          	<input type='text' class="searchButton" placeholder='Latitud' v-model="this.latitud" autofocus required readonly onmousedown="return false;">
          	<input type='text' class="searchButton" placeholder='Longitud' v-model="this.longitud" autofocus required readonly onmousedown="return false;">
          	 <b-btn v-if="this.bool==false"class="button" @click="añadir()" >Añadir</b-btn>
            <b-btn v-if="this.bool==true"class="button" @click="editar()" >Guardar</b-btn>
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
import LocalizacionesDetail from '../../entities/admin/LocalizacionesDetail'



export default {
 
  components: {LocalizacionesDetail},
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
      errors:'',
      marker:{},
      nueva:false,
      idLoc:null,
      markers:{},
      bool:false,
      num:0,

    }
  },
  watch: {
    '$route': 'fetchData',
     
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
  	this.fetchData();
    
  },

   mounted() {
    this.fetchData().then(() => {
         this.mymap = L.map('mymap').setView([43.34, -8.3888010], 12);
                L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                }).addTo(this.mymap);
         this.marcadores();
         this.mymap.on('click', onMapClick.bind(this));
    });


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
            .openOn(this.mymap);
    } 


 },

  methods: {
    fetchData() {
      this.location={};
      this.latitud='';
      this.longitud='';


      return HTTP.get('locations')
                  .then(response => { this.locations = response.data
                        return response.data})
                  .catch(err => { this.error = err.message})
    },

    marcadores(){
     for ( var i = 0; i < this.locations.length; i ++){
        this.markers[this.locations[i].idLocation]=L.marker([this.locations[i].latitud, this.locations[i].longitud],{id:this.locations[i].idLocation})
                      .addTo(this.mymap)
                      .bindPopup(this.locations[i].name)
                      .openPopup()
                      .on('popupopen', onMarkerClick.bind(this));

        this.markers[this.locations[i].idLocation].id=this.locations[i].idLocation;

            function onMarkerClick(e) {
              this.nueva=false;
              this.idLoc=e.popup._source.id;
              this.num=this.num+1;
           }
                 
        }
    },

    edicion(localizacion){
       this.latitud=localizacion.latitud;
       this.longitud=localizacion.longitud;
       this.location=localizacion;
       this.nueva=true;//aparezca formulario
       this.bool=true;//editar

    },

    recargarMapa(){
      this.resetear();
      this.fetchData().then(() => {
      this.marcadores() });
       this.$swal('Guardado', 'La nueva localización ha sido añadida', 'success')
    },

    recargarMapaEdi(){
      this.mymap.removeLayer(this.markers[this.idLoc]);
      this.resetear();
      this.fetchData().then(() => {
      this.marcadores() });
       this.$swal('Guardado', 'Los cambios se han guardado correctamente', 'success')

    },


    nuevaLoc(){
      this.latitud=null;
      this.longitud=null;
      this.location={};
      this.nueva=true;
    },

    hide(){
       this.resetear();
       this.bool=false;
       this.idLoc=null;
    },

    eliminar(){
      this.mymap.removeLayer(this.markers[this.idLoc]);
      delete this.markers[this.idLoc]
      this.resetear();
      this.bool=false;
      this.idLoc=null;
       this.$swal('Eliminado', 'La localización ha sido eliminada correctamente', 'success')

    },

    resetear(){
      this.nueva=false;//desaparezca formulario
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
      	     .then(response => { this.idLoc = response.data.idLocation
                        return response.data})
             .then(this.recargarMapa)
             .catch(this._errorHandler)

      }else{
        this.$swal('Alerta!', this.errors, 'error')
      }

    },

    editar(){
      this.location.latitud=this.latitud;
      this.location.longitud=this.longitud;

      if(this.checkForm1()==true){
        HTTP.put(`locations/${this.location.idLocation}`,this.location) 
           .then(response => { this.idLoc = response.data.idLocation
                        return response.data})
             .then(this.recargarMapaEdi)
            .catch(this._errorHandler)
      }else{
      this.$swal('Alerta!', this.error, 'error')
      }
    },


    _successHandler(response) {
      this.fetchData()

    },
    
    _errorHandler(err) {
      this.error = err.response.data.message
      this.$swal('Alerta!', this.error, 'error')
    }
  }
}

</script>

<style scoped lang="scss">

#mymap {
	margin-left:60px;
	margin-top:30px;
  margin-bottom:30px;
    position: relative;
    padding: 0;
    width: 50%;
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
  width: 50%;
  color: white;
  text-transform: uppercase;
  cursor: pointer;
  border-bottom: 4px solid darken(#AFC7A9, 5%);
  margin-left:123px;
  margin-top:20px;
  margin-bottom:50px;

  
}

.button2{

  outline: none;
  background: #AFC7A9;
  border: none;
  width: 8%;
  color: white;
  text-transform: uppercase;
  border-bottom: 2px solid darken(#AFC7A9, 5%);
  margin-left:3%;


  
}

.button3{
  margin: 0.25em 0;
  display: block;
  outline: none;
  background: #AFC7A9;
  border: none;
  padding: 0.7em;
  border-radius: 6px;
  width: 50%;
  color: white;
  text-transform: uppercase;
  cursor: pointer;
  border-bottom: 4px solid darken(#AFC7A9, 5%);
  margin-left:60px;
  margin-top:20px;



  
}

.searchButton {
  width: 50%;
  background: #fff;
  padding: 0.6em; 
  margin-top: 0.25em;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid grey;
  font-size:0.9em;
  margin-left:125px;

 

}     


.formulario{
	margin-right:70px;
	float:right;
	background: #f3f3f3;
	width:40%;
	height:85%;
	border-radius: 6px;

}

.inp{
	 margin-top:50px;
}

h1 {
  color: #3a3a3a;
  text-align: center;
  margin: 1em 0;
}

.horario{
  display: inline;
}


  button.xbut{
  width: 8%;
  float:right;
  background: #f3f3f3;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1.9em;
  color: white;
  color:black;
}

.horarios{
  color:black;
  margin-left:12%;
  margin-top:10px;
}

</style>
