<template>

  <div v-if="bol"class="information message">
   
    
    <div class="half margin-right">
    <h2 class="titulo" v-if=" idDeporte==null">Nuevo Deporte</h2>
         <input type='text' class="searchButton" placeholder='Nombre del deporte' v-model="sport.type" autofocus required >
          <multiselect 
            class="multi"
            v-model="sport.locations" 
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
      <input type='text' class="searchButton" placeholder='Nombre del componente de entrada' v-model="sport.componenteEntrada" autofocus required >
      <input type='text' class="searchButton" placeholder='Nombre del componente de visualización' v-model="sport.componenteVisualizacion" autofocus required >
     

       <button class="guardar"@click="guardar()"> Guardar</button>
       <button @click="hide"> Cancelar </button> 
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
     idDeporte:null

    
  },
  data() {

    return {
      sport:{},
      bol:true,
      alllocations: [],
      sports:null,
      loc:[],
      typeSport:null,
      fallo:false
   
     

    }
  },
  watch: {
    '$route': 'fetchData',


  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.getLocations();
    this.fetchData();
  },
  methods: {

     fetchData() {
       if (this.idDeporte) {
            HTTP.get(`sports/${this.idDeporte}`)
           .then(response => {
            this.sport = response.data
            return response
            })
           .then(response => {this.typeSport = response.data.type})
            .catch(err => this.error = err.message)

        } else {
           this.sport={};
        }
     
      HTTP.get('sports')
        .then(response => {
       this.sports = response.data
       
     })
     .catch(err => {
       this.error = err.message
     })
    
    },
    hide(){
      this.bol=false;
      this.$emit('Cerrar');

    },
    guardar(){
      this.fallo=false;
      if(this.idDeporte){//ACTUALIZAR
        if(this.typeSport!= this.sport.type){//Si cambiamos nombre
          for ( var i = 0; i < this.sports.length; i ++){
            if (this.sport.type==this.sports[i].type){//si tiene el nombre de alguno ya existente
              this.fallo=true;
            }
        }
      }
      if (this.fallo==false){//si no tiene el mismo nombre
            if(this.sport.type != ''){
            HTTP.put(`sports/${this.idDeporte}`,this.sport)
               .then(this._successHandler)
               .catch(this._errorHandler)
             }else{
               Vue.notify({
                   text: "Introduzca un nombre para este deporte",
                   type: 'error'})
             }
         }else{
        Vue.notify({
               text: "Este deporte ya está dentro de la BD",
               type: 'error'})

      }

      }else{//CREAR
      if(this.checkForm()==true){
      HTTP.post('sports',this.sport)

           .then(this._successHandler)
           .catch(this._errorHandler)
         } else{
           Vue.notify({
               text: this.error,
               type: 'error'})
         }
        } 
    },
     nameCustom ({ name }) {
      return `${name} `
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

    getLocations() {
        
       HTTP.get('locations')
      .then(response => this.alllocations = response.data)
      .catch(err => this.error = err.message)
    },
   

    _successHandler(response) {
      this.bol=false;
      this.$emit('Cerrar');

    },

    _errorHandler(err) {
      this.error = err.response.data.message
      
    }

  }
  
}
</script>

<style scoped lang="scss">

.titulo{
  margin-bottom:30px;
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

div.message.information{background: #fb887c;}

.guardar{
  width: 47%;
  float:left;
  background: #fff;
  padding: 0.6em; 
  margin-top: 15%;
  margin-bottom: 0.25em;
  border-radius: 5px;
  border: 0.1px solid #f3f3f3;
  font-size:1em;
  color: grey;
  margin-right:20px;

}



  button{
  width: 47%;
  float:left;
  background: #fff;
  padding: 0.6em; 
  margin-top: 15%;
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

</style>
