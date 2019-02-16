<template>

<div>

 <div class="container" ng-controller="index">

      <div class="box margin-right" id="shopping-list">
        <h1>Deportes</h1>
        
   <div class="half margin-right">
         <input type='text'   class="searchButton" placeholder='Nombre del deporte' v-model="sport.type" autofocus required >
          <multiselect 
            class="multi"
            v-model="sport.locations" 
            tag-placeholder="Localizaciones"
            :options="this.alllocations"
            :multiple="true"
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
        </div>
        
        
        <button id="addItem" @click="crear()">Nuevo Deporte</button>
        <div class="list">
          <table >
            <tr class="table-header">
              <td></td>
            </tr>
            <tr >
              <div class= "sport" v-for="sport in sports" :key="sport.idSport">
                <td>{{ sport.type}}</td>
             </div>             
            </tr>
          </table>  
    </div>
</div>
</div>
</div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import Multiselect from 'vue-multiselect'


export default {
  components: {Multiselect},
  data() {
    return {
      sports:null,
      sport:{},
      alllocations: [],
     

    }
  },
  watch: {
    '$route': 'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.getLocations()
    this.fetchData()
  },
  methods: {
    fetchData() {
      HTTP.get('sports')
        .then(response => {
       this.sports = response.data
       
     })
     .catch(err => {
       this.error = err.message
     })

    },
    getLocations() {
        
       HTTP.get('locations')
      .then(response => this.alllocations = response.data)
      .catch(err => this.error = err.message)
    },
  
    
    crear(){
      if(this.checkForm()==true){
      HTTP.post('sports',this.sport)

           .then(this._successHandler)
           .catch(this._errorHandler)
         } else{
           Vue.notify({
               text: this.error,
               type: 'error'})
         }
          
    },
     nameCustom ({ name }) {
      return `${name} `
    },

    _successHandler(response) {
      this.sport.type="";
      this.sport.locations=null;
      this.fetchData()

    },
    checkForm () {

      if (!this.sport.type) {
        this.error="Introduzca un deporte"
        return false;
      }
      
      for ( var i = 0; i < this.sports.length; i ++){
        if(this.sports[i].type==this.sport.type){
          this.error="El deporte "+this.sport.type+ " ya está en su BD"
          return false;
        }
      
      }

      if (this.sport.type || this.sport.type && this.spot.locations) {
        return true;
      }

    },

    notification(){
      if (this.error=="sportDTO.type no puede estar vacío"){
        this.error="Introduzca un deporte"
      }

       Vue.notify({
               text: this.error,
               type: 'error'})
    },
   
    _errorHandler(err) {
      this.error = err.response.data.message
      this.notification()
    }
  }
}
</script>

<style scoped lang="scss">
.tittle{
  color:black;
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
  width: 80px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
  margin-right:87px;

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

.button:hover span{
  padding-right: 20px;
}

.button:hover span:after{
  opacity: 1;
  right: 0;
}


$red: #fb887c;
$green: #AFC7B9;
$navy: #34495e;


.container {
  width: 500px;
  height: 100%;
  font-family: 'Lato', sans-serif;
  margin:0;
  margin-left:20px;
}

.box {
  width: 70%;
  background: #f3f3f3;
  padding: 1em;
  border-radius: 3px;
  margin-top:50px;


  
}

h1 {
  color: #3a3a3a;
  text-align: center;
  margin: 1em 0;
}

table {
  width: 100%;

  td {
    padding: 0.75em 0.5em;
    color:#3a3a3a;
    margin-left:auto;
    margin-right:auto;
  }
  
  tr:nth-child(2n+2){
    background: darken(#eee, 3%); 
  }
}

.searchButton {
  width: 100%;
  background: #fff;
  padding: 0.6em; 
  margin-top: 0.25em;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #fff;
  font-size:1em;

}      



button {
  margin: 0.25em 0;
  display: block;
  outline: none;
  background: $red;
  border: none;
  padding: 1.1em;
  border-radius: 6px;
  width: 100%;
  color: white;
  text-transform: uppercase;
  cursor: pointer;
  border-bottom: 4px solid darken($red, 5%);
  
}






</style>
