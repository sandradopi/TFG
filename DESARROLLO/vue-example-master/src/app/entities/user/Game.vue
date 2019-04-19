<template>
 <div class="game">
  <b-btn class="button" v-if="this.calendar==false" v-b-modal.modalPrevent><font-awesome-icon icon="sliders-h"style="font-size:30px;"/></b-btn> 
  <b-btn class="button1" v-if="this.calendar==false" @click="displayCalendar()"><font-awesome-icon icon="calendar-alt"style="font-size:30px;"/></b-btn> 
   <b-btn class="button2" v-if="this.calendar==true" @click="displayMap()"><font-awesome-icon icon="map-marked-alt"style="font-size:30px;"/></b-btn> 
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
              <b-btn class="button3" @click="deshacerFiltro()">Mostrar todos los partidos</b-btn> 
              </b-form-group>
              
              <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
        </form>
    </b-modal>


  <div class="conjunto">

  <Calendar class="calendario" v-if="this.calendar==true" ></Calendar>

	<div id="mymap" class="mymap" v-show="this.calendar==false"></div>

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.1.0/dist/leaflet.css" />
    <div class="information message2" v-if="this.bol==true">
   		 <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
   		 <h3 class= "titulo">{{this.idLocName}}</h3>
       <div class="scroll">
   		 <div class="w3-container" v-for=" game in this.gamesLoc" :key="game.idGame">
       		 <b-btn class="w3-bar" :to="{ name: 'GameDetail', params: { id: game}}">
          	 <img v-if="game.sport.type=='Futbol'"src="futbol.png" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
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
    <div class="instrucciones" v-if="this.bol==false">
       <div class="news">
        <h1>
          Busca un partido
        </h1>
        <p>
          Encuéntralo a través del mapa o por el calendario
        </p>
      </div>
      <div class="sports">
        <h1>
          Apúntate
        </h1>
        <p>
          Si alguno de ellos te convence, no te lo pienses, únete!
        </p>
      </div>
      <div class="entertainment">
        <h1>
          Que gane el mejor!
        </h1>
        <p>
          Juega lo mejor que puedas para ir ganando experiencia.
        </p>
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
import Calendar from '../../entities/user/Calendar'



export default {
  components: {Multiselect,Calendar},
  data() {

    return {
    	games:null,
  		mymap:null,
  		markers:{},
  		locations:[],
      nomdep:null,
  		bol:false,
  		gamesLoc:[],
  		idLoc:null,
  		idLocName:"",
      allsports:[],
      deporte:null,
      users:[],
      usuario:null,
      edad:null,
      edades:['< 18','18 < edad < 25','25 < edad < 40', '> 40'],
      login:"",
      calendar:false

  
    }
  },
  watch: {
    '$route': 'fetchData',

    
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
      .then(response => { this.allsports = response.data
                        return response.data})
      .catch(err => this.error = err.message)
    },
    getUsers(){
       HTTP.get(`users/normal`)
            .then(response => { this.users = response.data })
            .catch(err => { this.error = err.message})
    },

    displayCalendar(){
      this.calendar=true;
      this.bol=false;
    },
 

    displayMap(){
      this.calendar=false;
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
    deshacerFiltro(){
     this.fetchData().then(() => {this.confirmación()});
      

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
        var url=`games/filtro?creator=${this.login}&sport=$`
        var deportes;

        if (!this.usuario){
          this.login="vacio"
        }
        else{
          this.login=this.usuario.login
        }


         if (!this.deporte){
          url= `games/filtro?creator=${this.login}&sport=vacio`
          
         }else{

            //Obtener la URL segun el numero de deportes que me vengan
            if(this.deporte.length==1){
              url= `games/filtro?creator=${this.login}&sport=${this.deporte[0].type}`
       
            }else{
              url= `games/filtro?creator=${this.login}&sport=${this.deporte[0].type}`
              for ( var i = 1; i < this.deporte.length; i ++){
                url= url+ `,${this.deporte[i].type}`;
             
             }

        }
     }
         
         
                  return HTTP.get(url)
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
          this.$refs.modal.hide();
          this.bol=false;
        })

      var marcadores= Object.entries(this.markers);
          for (var [key, value] of marcadores) {
          this.mymap.removeLayer(this.markers[key]);
          delete this.markers[key];
 
        }
        this.locations=[];
        this.marcadores();
        

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
              this.gamesLoc=[];
              this.idLoc=e.popup._source.id;
              this.idLocName=e.popup._source.name;
              for ( var i = 0; i < this.games.length; i ++){
                if(this.games[i].location.name==this.idLocName)
                  this.gamesLoc.push(this.games[i])
              }
             /* HTTP.get(`games/locations/${this.idLoc}`)
                  .then(response => { this.gamesLoc = response.data
                        return response.data})
                  .catch(err => { this.error = err.message})*/
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

.game{
   margin-bottom:7%;
}
div.message2 {
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  width:40%;
  height:500px;
  margin-top:20px;
  float:right;
  background: #AFC7A9;
  margin-right:70px;
  border-radius:2em;

}


.scroll{
  height:400px;
   overflow: scroll;


}

.calendario {

    font-size: 0.8em;
    float:left;
    margin-top:50px;
    margin-left:50px;
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

.button , .button1,.button3,.button2{
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

.button span, .button1 span, .button3 span, .button2 span{
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after , .button1 span:after, .button3 span:after, .button2 span:after{
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span, .button1:hover span, .button3:hover span, .button2:hover span{
  padding-right: 20px;
}

.button:hover span:after, .button1:hover span:after, .button3:hover span:after, .button2:hover span:after{
  opacity: 1;
  right: 0;
}
.button1{
  margin-left:0;
  background-color:#17a2b8;

   
 }
 .button3{
  width :100%;
  margin-left:0;
  background-color:#17a2b8;
  margin-top:0;

   
 }

  .button2{
  margin-left:55px;

   
 }

  .formulario{
  color:#17a2b8;
  
}

fieldset {
    border: 1px solid #c0c0c000;
}


div.news h1 {
  color: #fb887c;
  border-bottom: 2px solid #fb887c;
}
div.news p {
  color: #fb887c;
}

div.sports h1 {
  color: #AFC7A9;
  border-bottom: 2px solid #AFC7A9;
}
div.sports p {
  color: #AFC7A9;
}

div.entertainment h1 {
  color: #17a2b8;
  border-bottom: 2px solid #17a2b8;
}
div.entertainment p {
  color: #17a2b8;
}
.instrucciones{
  width:30%;
  float:right;
  margin-top:120px;
  margin-right:140px;
}


.swal-title {
    color: rgba(0,0,0,.65);
    font-weight: 500px;
    text-transform: none;
    position: relative;
    display: block;
    padding: 13px 16px;
    font-size: 27px;
    line-height: normal;
    text-align: center;
    margin-bottom: 0;
}
</style>
