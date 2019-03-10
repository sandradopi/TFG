<template>
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
            :clear-on-select="false" 
            :preserve-search="true"
            :close-on-select="false" 
            :show-labels="false"
            @close="selectOn()"
            track-by="idSport"
            placeholder="Deportes"
            :custom-label="nameCustom1"
            >
      </multiselect>

       <multiselect  v-if="bol==true"
            v-model="game.location" 
            :options="this.alllocations"
            :multiple="false"
            :searchable="true" 
            :clear-on-select="false" 
            :preserve-search="true"
            :close-on-select="false" 
            :show-labels="false"
            track-by="idLocation"
            placeholder="Localizaciones"
            :custom-label="nameCustom"
            >
      </multiselect>
      <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
      
      <input type='text' class="searchButton" placeholder='Número máximo de jugadores' v-model="game.maxPlayers" autofocus required >
      <input type='text' class="searchButton" placeholder='Número míximo de jugadores' v-model="game.minPlayers" autofocus required >

       <b-btn class="button"
              @click="guardar()"><span>Crear</span></b-btn>
</div>

</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import Multiselect from 'vue-multiselect'



export default {
  components: { Multiselect },
  data() {
    return {
     game:{},
     alllocations:[],
     allsports:[],
     bol:false
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
		    .then(response => {
		       this.game.creator = response.data
		       return response
       
     })
     .catch(err => {
       this.error = err.message
     })

     
  
    },
    selectOn(){
		this.bol=true;
    this.game.location='';
    debugger
  	 	HTTP.get(`locations/filter/${this.game.sport.idSport}`) 
		    .then(response => {
		       this.alllocations = response.data
		 
     })
     .catch(err => {
       this.error = err.message
     })
		
    },
    guardar(){
       HTTP.post('games',this.game)

           .then(this._successHandler)
           .catch(this._errorHandler)

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
.shopping-list{
  width: 30%;
  background: #f3f3f3;
  padding: 1em;
  border-radius: 3px;
  margin-top:50px;
  font-family: 'Lato', sans-serif;
  margin:0;
  margin-left:120px;
  margin-top:50px;
  margin-bottom:50px;
  border-radius: 6px;


  
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
    margin-bottom:30px;
    color:#fb887c;
  }
  .button{

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

.button span{
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 20px;
}

.button:hover span:after{
  opacity: 1;
  right: 0;
}


</style>
