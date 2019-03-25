<template>
 <div>
  <b-btn class="button"  @click="filtrar()"><font-awesome-icon icon="sliders-h"style="font-size:30px;"/></b-btn> 
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



export default {
  components: {},
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
  
    }
  },
  watch: {
    '$route': 'fetchData',
    tipo: 'fetchData',

    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes

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

     marcadores(){
     for ( var i = 0; i < this.games.length; i ++){
     	if(this.locations.includes(this.games[i].location.name)==false){

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
	 margin-left:30px;
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

 .filtros{

  background-color: #fb887c;
  margin-top:20px;
  width:20%;
  height:79.5%;

 }



</style>
