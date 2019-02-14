<template>

<div>
   <div class="float-right">
      <b-btn        class="button"
                    variant="primary"
                    @click="back()"><span>Atrás</span></b-btn>
  </div>


 <div class="container" ng-controller="index">

      <div class="box margin-right" id="shopping-list">
        <h1>Deportes</h1>
        
   <div class="half margin-right">
         <input type='text'   class="searchButton" placeholder='Nombre del deporte' ng-model="itemName" autofocus required>
        </div>
        
        
        <button id="addItem">Nuevo Deporte</button>
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


export default {
  components: {},
  data() {
    return {
      sports:null,
     

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
      HTTP.get('sports')
        .then(response => {
       this.sports = response.data
       
     })
     .catch(err => {
       this.error = err.message
     })
    .finally(() => this.loading = false)
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
  }
  
  tr:nth-child(2n+2){
    background: darken(#eee, 1%); 
  }
}

.searchButton {
  width: 100%;
  border: 2px solid #e0e0e0;
  background: #fff;
  outline: none;
  padding: 1em;
  border-radius: 3px;
  font-weight: 300;
  font-size: 1em;
  margin-top: 0.25em;
  margin-bottom: 0.25em;
  position: relative;
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
