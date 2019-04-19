<template>
  <div class="dep">
      <DeportesDetail @Cerrar="reloadPage" v-if= "(idDeporte!=null && nuevo==false)" v-bind:idDeporte="this.idDeporte" v-bind:num="this.num" v-bind:nuevo="this.nuevo"></DeportesDetail> <!--Le pasamos ese numero para que en el caso de cerrae y querer abrir el mismo deporte nos deje, ya que el idDeporte no cambia-->
      <DeportesForm @Cerrar="reloadPage"  v-if= "(idDeporte==null && nuevo||nuevo)" v-bind:idDeporte="this.idDeporte"></DeportesForm>

      <div  id="shopping-list">
          <h1>Deportes</h1>        
          <button id="addItem" @click="crear()">Nuevo Deporte</button>
          <div class="list">
              <table >
                <tr class="table-header">
                  <td></td>
                </tr>
                <tr >
                  <button class= "sport" v-for="sport in sports" :key="sport.idSport" @click="parametro(sport.idSport)">
                    <td>{{ sport.type}}</td>
                 </button>             
                </tr>
              </table>  
          </div>
    </div>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import Multiselect from 'vue-multiselect'
import DeportesDetail from '../../entities/admin/DeportesDetail'
import DeportesForm from '../../entities/admin/DeportesForm'

export default {
  components: {Multiselect,DeportesDetail,DeportesForm },
  data() {
    return {
      sports:null,
      sport:{},
      idDeporte:null,
      num:0,
      nuevo:false
     

    }
  },
  watch: {
    '$route': 'fetchData',
    nuevo:'fetchData'


    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.fetchData()
  },

  methods: {
    fetchData() {
      HTTP.get('sports')
        .then(response => {
              this.sports = response.data})
        .catch(err => {
              this.error = err.message})

    },
    
    parametro(idSport){
      this.nuevo=false;
      this.idDeporte=idSport;
      this.num=this.num+1;
    },

    reloadPage(bolean){
      if (bolean){

        this.nuevo=true;
        this.fetchData()

      }else{
        this.nuevo=false;
        this.fetchData();
      }
    },

    crear(){
      this.nuevo=true;  
      this.idDeporte=null;        
    },
    
    _successHandler(response) {
      this.sport.type="";
      this.sport.locations=null;
      this.fetchData()
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
.dep{
  margin-bottom:3%
}
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
$green: #AFC7B9;
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
  margin-top:100px;
  border-radius: 6px;


  
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
