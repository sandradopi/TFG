<template>

  <div v-if="bol"class="information message">
    <button @click="hide"> X </button> 
    <div> 

      <h2 >{{location.name}}</h2>
    
      <h5 class="comp">Coste por hora (€/h): {{location.costPerHour}}</h5>
    
      </br>
     <h5>Horario:</h5>
     <li type="comp" v-for=" open in opens" :key="opens.idOpening"> {{open.dayOfTheWeek}}: {{custom(open.to)}}/ {{custom(open.from)}}</li>
     </br>

      <div class="compo">
      	<h5>Ubicación:</h5>
        <li class="comp">Latitud: {{location.latitud}}</li>
        <li class="comp">Longitud: {{location.longitud}}</li>
      </div>


        <b-btn
                    class="editar"
                    :to="{ name: 'Mapa', params: { id: this.location}}"
                   ><span>Editar</span></b-btn> 
       <button class="eliminar" @click="eliminar()"> Eliminar</button>

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
  props:{
    idLoc:null,
    num:0,
    
  },
  data() {

    return {
      sport:{},
      bol:true,
      location:{},
      nuevoCoste:{},
      opens:null
   
     

    }
  },
  watch: {
    '$route': 'fetchData',
     num:'fetchData',


  },

 
  created() { //se va a lanzar siempre en una clase de componentes
    this.fetchData()
  },
  methods: {

     fetchData() {
      this.bol=true;
       HTTP.get(`locations/${this.idLoc}`) 
      .then(response => {
        this.location = response.data
        return response
      })
      .then(this.getOpeningtime)
      .catch(err => this.error = err.message)

    },
    custom(open){
    return open.substring(0,5);
  },

    getOpeningtime(){
      HTTP.get(`openings/${this.idLoc}`) 
            .then(response => {
              this.opens = response.data
            })
            .catch(err => this.error = err.message)
    },

    hide(){
      this.bol=false;
      this.edit1();
      this.$emit('Cerrar',false);

    },



    _successHandler(response) {
      this.bol=false;
      this.$emit('Cerrar');

    },

    _errorHandler(err) {
      this.error = err.response.data.message
      
    },
    eliminar(){
      HTTP.delete(`locations/${this.idLoc}`)
        .then(this._successHandler)
        .catch(this._errorHandler)


    }
  
}
}
</script>

<style scoped lang="scss">
.comp{

  font-size:1em;
}

.compo{
  margin-top:20px;
}

div.message {
  
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:40%;
  float:right;
  margin-right:150px;
  
  
  -webkit-transition: all .5s ease;
     -moz-transition: all .5s ease;
      -ms-transition: all .5s ease;
       -o-transition: all .5s ease;
          transition: all .5s ease;
}
div.message:hover{
  box-shadow: 0 15px 20px rgba(10,0,10,.3);
  -webkit-filter: brightness(110%);
}

div.message.information{background: #AFC7A9;}

.guardar{
  width: 100%;
  float:left;
  background: #fff;
  padding: 0.6em; 
  margin-top: 10%;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1em;
  color: grey;

}

td {
    
    color:grey;
    margin-left:auto;
    margin-right:auto;
  }

  button{
  width: 10%;
  float:right;
  background: #AFC7A9;
  border-radius: 5px;
  border: 0.1px solid #AFC7A9;
  font-size:1.8em;
  color: white;
}

.editar{
  width: 47%;
  float:left;
  background: #fff;
  padding: 0.6em; 
  margin-top: 10%;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1em;
  color: grey;
}


.eliminar{
   width: 47%;
   float:right;
  background: #fff;
  padding: 0.6em; 
  margin-top: 10%;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1em;
  color: grey;
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

.searchButton {

  width: 30%;
  background: #fff;
  padding: 0.6em; 
  margin-top: 0.25em;
  border-radius: 5px;
  border: 0.1px solid grey;
  font-size:0.8em;
  margin-right:170px;


 

}   

.searchButton1 {

  width: 30%;
  background: #fff;
  padding: 0.6em; 
  margin-top: 0.25em;
  border-radius: 5px;
  border: 0.1px solid grey;
  font-size:0.8em;
  margin-right:120px;


 

}   
.tit{

	margin-top:10px;
}


</style>
