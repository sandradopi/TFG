<template>
 <div>
  <b-btn class="button" v-b-modal.modalPrevent><font-awesome-icon icon="sliders-h"style="font-size:30px;"/></b-btn> 
  <b-modal
        class="formulario"
        id="modalPrevent"
        ref="modal"
        title="Filtros"
        @ok="handleOk"
        @shown="clearName">

        <form @submit.stop.prevent="handleSubmit">
          <b-form-group>
              <multiselect 
                v-model="deporte" 
                :options="this.allsports"
                :multiple="true"
                :searchable="true" 
                :preserve-search="true"
                :close-on-select="true" 
                :show-labels="false"
                track-by="idSport"
                placeholder="Deportes"
                :custom-label="nameCustom1"
                >
              </multiselect>
            </b-form-group>
            <b-form-group>
            <multiselect 
                v-model="usuario" 
                :options="this.users"
                :multiple="false"
                :searchable="true" 
                :preserve-search="true"
                :close-on-select="true" 
                :show-labels="false"
                track-by="idUser"
                placeholder="Creador"
                :custom-label="nameCustom2"
                >
              </multiselect>
              </b-form-group>
               <b-form-group>
            <multiselect 
                v-model="edad" 
                :options="this.edades"
                :multiple="false"
                :searchable="true" 
                :preserve-search="true"
                :close-on-select="true" 
                :show-labels="false"
                placeholder="Edad"
                >
              </multiselect>
              </b-form-group>
              <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
        </form>
    </b-modal>


  <div class="conjunto">
	<div id="mymap" class="mymap"></div>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.1.0/dist/leaflet.css" />
    <div class="information message2" v-if="this.bol==true">
   		 <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
   		 <h3 class= "titulo">{{this.idLocName}}</h3>
       <div class="scroll">
   		 <div class="w3-container" v-for=" game in this.gamesLoc" :key="game.idGame">
       		 <b-btn class="w3-bar" :to="{ name: 'GameDetail', params: { id: game}}">
          	 <img v-if="game.sport.type=='Fútbol'"src="futbol.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
          	 <img v-if="game.sport.type=='Tennis'"src="ten.jpg" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
          	 <img v-if="game.sport.type=='Paddel'"src="paddel.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           	<img v-if="game.sport.type=='Baloncesto'"src="bal1.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
           	<div class="w3-bar-item">
              <span class="w3-large">{{game.date}} ({{custom(game.timeStart)}}-{{custom(game.timeEnd)}})</span><br>
              <span>Creador: {{game.creator.name}} {{game.creator.surname1}} {{game.creator.surname2}}</span>
         	</div>
       	 	</b-btn>
   		</div>
  	</div>
  </div>
 </div>
</div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import L from 'leaflet'
import Multiselect from 'vue-multiselect'



export default {
  components: {Multiselect},
  data() {

    return {
    	games:null,
  		mymap:null,
  		markers:{},
  		locations:[],
  		bol:false,
  		gamesLoc:null,
  		idLoc:null,
  		idLocName:"",
      allsports:[],
      deporte:null,
      users:[],
      usuario:null,
      edad:null,
      edades:['< 18','18 < edad < 25','25 < edad < 40', '> 40']
  
    }
  },
  watch: {
    '$route': 'fetchData',
    tipo: 'fetchData',

    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.getSports();
    this.getUsers();
    this.fetchData()
  },

   mounted() {
    this.fetchData().then(() => {
         this.mymap = L.map('mymap').setView([43.34, -8.3888010], 12);
                L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                }).addTo(this.mymap);
         this.marcadores();
         
    });


    var popup = L.popup();

   

 },


  methods: {
    fetchData() {
    	 return HTTP.get('games')
                  .then(response => { this.games = response.data
                        return response.data})
                  .catch(err => { this.error = err.message})
    
    },
     custom(hora){
      return hora.substring(0,5)
    },
     getSports() {
        
       HTTP.get('sports')
      .then(response => this.allsports = response.data)
      .catch(err => this.error = err.message)
    },
    getUsers(){
       HTTP.get(`users/normal`)
            .then(response => { this.users = response.data })
            .catch(err => { this.error = err.message})
    },

    clearName() {
        this.deporte = null;
        this.usuario=null;
        this.edad=null;
      },

    nameCustom1 ({ type }) {
      return `${type} `
    },

    nameCustom2 ({ login }) {
      return `${login} `
    },


      handleOk(evt) {
        // Prevent modal from closing
        evt.preventDefault()
        if ((!this.deporte) &&(!this.usuario)&& (!this.edad)) {
           this.$swal('Alerta!', "Seleccione por lo menos un filtro!", 'error')
          
        }else{
          this.handleSubmit()
        }
      },
      handleSubmit() {
         
                  return HTTP.post(`games/filtro`, this.deporte)
                  .then(response => { this.games = response.data
                        return response.data})
                  .then(this.confirmación)
                  .catch(err => { this.error = err.message})
      },

    confirmación(){

        this.$swal('', 'Se han aplicado los filtros seleccionados', 'success')
        this.clearName()
        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
          this.$refs.modal.hide()
        })

       var numero= Object.keys(this.markers).length;
        for ( var i = 1; i < numero+1; i ++){
          this.mymap.removeLayer(this.markers[i]);
          delete this.markers[i];
 
        }
        this.locations=[];
        this.marcadores();

    },

     marcadores(){
     for ( var i = 0; i < this.games.length; i ++){
     	if(this.locations.includes(this.games[i].location.name)==false){
        console.log("dentro")
             this.markers[this.games[i].idGame]=L.marker([this.games[i].location.latitud, this.games[i].location.longitud],{id:this.games[i].location.idLocation,name:this.games[i].location.name})
                      .addTo(this.mymap)
                      .bindPopup(this.games[i].location.name)
                      .openPopup()
                      .on('popupopen', onMarkerClick.bind(this));

        this.markers[this.games[i].idGame].id=this.games[i].location.idLocation;
        this.markers[this.games[i].idGame].name=this.games[i].location.name;

            function onMarkerClick(e) {
              this.bol=true;
              this.idLoc=e.popup._source.id;
              this.idLocName=e.popup._source.name;
              HTTP.get(`games/locations/${this.idLoc}`)
                  .then(response => { this.gamesLoc = response.data
                        return response.data})
                  .catch(err => { this.error = err.message})
           }
                 
        this.locations.push(this.games[i].location.name)
   	 	}
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
}
</script>

<style scoped lang="scss">

#mymap {
	 margin-left:50px;
	  margin-top:30px;
  	margin-bottom:30px;
    position: relative;
    padding: 0;
    width: 49%;
    height: 500px;
    float:left;

}
div.message2 {
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  width:40%;
  height:500px;
  margin-top:30px;
  float:right;
  background: #AFC7A9;
  margin-right:70px;
  border-radius:2em;

}


.scroll{
  height:400px;
   overflow: scroll;


}




.w3-ul li:last-child {
    
    border-bottom: none;
    background: white;
    color: #6c757d;
    border-radius: 25px;
}

ul.w3-ul.w3-card-4{
   border-radius: 25px;
    background: white;

}

.w3-bar{
    border-radius: 25px;
    background: white;
    color: #6c757d;
    margin-top:10px;
    border-color:white;
    box-shadow:0 2px 5px rgba(0,0,0,.3);

}

.titulo{
	margin-left:32%;
	margin-bottom:20px;
  font-family: 'Lato', sans-serif;
  font-size: 30px;
  font-weight: 200;

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
  width: 50px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
  margin-left:55px;
  margin-top:20px;
  background-color: #fb887c;
  height:50px;


}

.button span, .button1 span, .button3 span{
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after , .button1 span:after, .button3 span:after{
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span, .button1:hover span, .button3:hover span{
  padding-right: 20px;
}

.button:hover span:after, .button1:hover span:after, .button3:hover span:after{
  opacity: 1;
  right: 0;
}
.button1{
  
   float:right;
   width: 150px;
   
 }

  .formulario{
  color:#17a2b8;
  
}




</style>
