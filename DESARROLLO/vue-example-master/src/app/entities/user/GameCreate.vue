<template>
  <div class="evento">

    <Calendar class="calendario"></Calendar>
    <b-modal
        id="modalPrevent"
        ref="modal"
        size="lg"
        ok-only no-stacking>
        <Weather v-if="this.game.location!=null" v-bind:location="this.game.location"></Weather>



      </b-modal>

  <div class="shopping-list">
    <h1 class="title"> Crear Partido</h1>  
    <input type='date' class="searchButton" placeholder='Fecha' v-model="game.date" autofocus required >
    <input type='time' class="searchButton" placeholder='Hora inicio' v-model="game.timeStart" autofocus required >
    <input type='time' class="searchButton" placeholder='Hora inicio' v-model="game.timeEnd" autofocus required >

     
    <multiselect 
            v-model="game.sport" 
            :options="this.allsports"
            :multiple="false"
            :searchable="true" 
            :preserve-search="true"
            :close-on-select="true" 
            :show-labels="false"
            @close="selectOn()"
            track-by="idSport"
            placeholder="Deportes"
            :custom-label="nameCustom1"
            >
    </multiselect>


    <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
    <v-select 
          v-if="bol==true"
          v-model="game.location" 
          :options="this.alllocations" 
          label="name"
          :close-on-select="true"  ></v-select>

    <input type='text' class="searchButton" placeholder='Número máximo de jugadores' v-model="game.maxPlayers" >
    <input type='text' class="searchButton" placeholder='Número míximo de jugadores' v-model="game.minPlayers"  >
      <b-btn class="button1" v-b-modal.modalPrevent v-if="this.game.location!=null"><span>Metereología</span></b-btn>
    <b-btn class="button" @click="guardar()"><span>Crear</span></b-btn>

    </div>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import Multiselect from 'vue-multiselect'
import Calendar from '../../entities/user/Calendar'
import Weather from '../../entities/user/Weather'
import vSelect from 'vue-select'




export default {
  components: { Multiselect, Calendar, Weather, vSelect },
  
  data() {
    return {
     game:{},
     alllocations:[],
     allsports:[],
     bol:false,
     showModal:false,
     error:""
    }
  },
  watch: {
    '$route': 'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
  	this.getLocations();
  	this.getSports();
    this.fetchData()
  },
  methods: {
    fetchData() {
    	 HTTP.get(`users/${this.$route.params.id}`) 
		        .then(response => { this.game.creator = response.data
		              return response })
            .catch(err => { this.error = err.message})
    },

    selectOn(){
      if(this.game.sport!=null){
		  this.bol=true;
  	 	HTTP.get(`locations/filter/${this.game.sport.idSport}`) 
		        .then(response => { this.alllocations = response.data
                                this.game.location=this.alllocations[0]
                                })
            .catch(err => { this.error = err.message})}
		

    },
    checkForm () {

      if (!this.game.date) {
        this.error="Introduzca una fecha para su evento"
        return false;
      }
       if (!this.game.timeStart) {
        this.error="Introduzca una hora de inicio para su evento"
        return false;
      }

       if (!this.game.timeEnd) {
        this.error="Introduzca una hora de finalización para su evento"
        return false;
      }
      if (!this.game.sport) {
        this.error="Seleccione un deporte"
        return false;
      }

      if (!this.game.location) {
        this.error="Seleccione una localizacion"
        return false;
      }

      if (!this.game.maxPlayers) {
        this.error="Introduzca el número máximo de jugadores"
        return false;
      }

       if ((this.game.maxPlayers%2) !=0) {
        this.error="Introduzca un número par para poder realizar una mejor gestión de los equipos"
        return false;
      }

      if ((this.game.sport.type=="Tennis"||this.game.sport.type=="Paddel")&& (this.game.maxPlayers>4) ) {
        this.error="El máximo de jugadores permitidos para este deportes son 4"
        return false;
      }

      if (!this.game.minPlayers) {
        this.error="Introduzca el número mínimo de jugadores"
        return false;
      }

      if (this.game.date && this.game.timeStart && this.game.timeEnd && this.game.sport&& this.game.location&& this.game.maxPlayers &&this.game.minPlayers) {
        return true;
      }

    },


    guardar(){
       if(this.checkForm()==true){
         HTTP.post('games',this.game)
              .then(response => {this.game = response.data
                return response})
             .then(this._successHandler)
             .catch(this._errorHandler)
        }else{
          this.$swal('Alerta!', this.error, 'error')
        }

    },

    getLocations() {
      
      HTTP.get('locations')
     	     .then(response => this.alllocations = response.data)
      	   .catch(err => this.error = err.message)
    },

    getSports() {
        HTTP.get('sports')
            .then(response => this.allsports = response.data)
            .catch(err => this.error = err.message)
    },
    
     nameCustom ({ name }) {
      return `${name} `
    },
     
    nameCustom1 ({ type }) {
      return `${type} `
    },
     
    _successHandler(response) {
       this.$router.replace({ name: 'GameDetail', params: { id: this.game}})

    },
    
    back() {
      this.$router.go(-1)
    },

   _errorHandler(err) {
      this.error = err.response.data.message
      this.$swal('Alerta!', this.error, 'error')
      
    }
  }
}
</script>

<style scoped lang="scss">
.shopping-list{
  width: 30%;
  background: #f3f3f3;
  padding: 1em;
  border-radius: 3px;
  font-family: 'Lato', sans-serif;
  margin:0;
  margin-left:120px;
  margin-top:80px;
  border-radius: 6px;
  height:90%;
  margin-bottom:20px;
  float:left;


  
}

dropdown v-select single searchable{}
.evento{
  margin-bottom:7%;
}

.searchButton {
  width: 100%;
  background: #fff;
  padding: 0.6em; 
  margin-top: 0.25em;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #fff;
  font-size:0.9em;

}  
.title{
    font-family: 'Lato', sans-serif;
    margin-left:25%;
    font-size: 30px;
    font-weight: 200;
    color: #17a2b8;
    margin-top:20px;
    margin-bottom:15px;
    color:#17a2b8;
  }
  .button, .button1{

  display: inline-block;
  border-radius: 4px;
  background-color: #17a2b8;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 17px;
  padding: 5px;
  width: 80px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
  margin-top: 20px;
  margin-left: 40%;

}

.button span, .button1 span{
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after, .button1 span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span, .button1:hover span {
  padding-right: 20px;
}

.button:hover span:after, .button1:hover span:after{
  opacity: 1;
  right: 0;
}

.button1{

    background-color:#fb887c;
    width:100%;
    margin-left:0px;
    margin-top:10px;

}
.calendario {

    font-size: 0.8em;
    width: 50%;
    float: right;
    margin-top:80px;
    margin-right: 7%;
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
.v-select {
    min-height: 40px;
    display: block;
    border-radius: 5px;
    border: 1px solid #e8e8e8;
    background: #fff;


}
.v-select.searchable {
    margin-top:3px;
    cursor: text;
    border: none;
}

</style>
