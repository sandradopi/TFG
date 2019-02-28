<template>

<div>

  <div  id="shopping-list">
        <h1>Localizaciones</h1>        

         <b-btn class="button"
              :to="{ name: 'Mapa'}"
              variant="link">Nueva Localización</b-btn>


        <div class="list">
          <table >
            <tr class="table-header">
              <td></td>
            </tr>
            <tr >
              <button class= "sport" v-for="location in locations" :key="location.idLocation" >
                <td>{{ location.name}}</td>
             </button>             
            </tr>
          </table> 


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
      locations:null,
      
     

    }
  },
  watch: {
    '$route': 'fetchData',


    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    
    this.fetchData()
  },
  methods: {
    fetchData() {
      HTTP.get('locations')
        .then(response => {
       this.locations = response.data
       
     })
     .catch(err => {
       this.error = err.message
     })

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

.sport{
  width: 100%;
  background: #fff;
  padding: 0.6em; 
  margin-top: 0.25em;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1em;
  color: #3a3a3a;
}



$red: #fb887c;
$green: #AFC7A9;
$navy: #34495e;



div#shopping-list{
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


  
}

h1 {
  color: #3a3a3a;
  text-align: center;
  margin: 1em 0;
}

table {
  width: 100%;

  td {
    
    color:grey;
    margin-left:auto;
    margin-right:auto;
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
  font-size:0.9em;

}      



button, .button{
  margin: 0.25em 0;
  display: block;
  outline: none;
  background: $green;
  border: none;
  padding: 1.1em;
  border-radius: 6px;
  width: 100%;
  color: white;
  text-transform: uppercase;
  cursor: pointer;
  border-bottom: 4px solid darken($green, 5%);
  
}


</style>
