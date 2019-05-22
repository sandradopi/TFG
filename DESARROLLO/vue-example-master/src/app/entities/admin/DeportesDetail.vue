<template>
  
    <div v-if="bol"class="information message">
      <button @click="hide"> X </button> 
      <div>   
        <img v-bind:src="getImagenSport(this.sport.rutaImagen)" class="w3-bar-item w3-circle w3-hide-small iconStyle" style="width:40px"></img>
          <h2>{{this.sport.type}}</h2>
           
          <br>

          <h5>Ubicaciones:</h5>
          <li type="disc" v-for=" location in this.sport.locations" :key="location.idLocation"> {{location.name}}</li>
          <li v-if="this.loc.length==0"> No hay localizaciones aun registradas para este deporte</li>
          <div class="compo">
              <h2 class="comp">Componente de Entrada: {{this.sport.componenteEntrada}}</h2>
              <h2 class="comp">Componente de Visualización: {{this.sport.componenteVisualizacion}}</h2>
         </div>

          <button class="editar" @click="editar()">Editar</button>
          <button class="eliminar"@click="eliminar()"> Eliminar</button> 
    </div>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import Multiselect from 'vue-multiselect'
import { baseURL } from '../../common/http-common'




export default {
  components: {Multiselect},
  props:{
    idDeporte:null,
    num:0,
  },

  data() {
    return {
      sport:{},
      bol:true,
      sports:null,
      loc:[]
    }
  },

  watch: {
    '$route': 'fetchData',
     num:'fetchData',

  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.getLocations()
    this.fetchData()
  },

  methods: {
     fetchData() {
      this.bol=true;
      HTTP.get(`sports/${this.idDeporte}`) 
              .then(response => {this.sport = response.data
                    return response})
              .then(response => {this.loc = response.data.locations})
              .catch(err => this.error = err.message)
    },

    hide(){
      this.bol=false;
      this.$emit('Cerrar',false);
    },
     getImagenSport(path){
      return baseURL + "sports/imagenes/" + path;
    },

    editar(){
      this.$emit('Cerrar',true);
    },
    
     nameCustom ({ name }) {
      return `${name} `
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
        Vue.notify({
               text: this.error,
               type: 'error'})
      
    },
    alertDisplay() {
        this.$swal("¿Estás seguro de querer borrar este deporte? Podrían borrarse algunas localizaciones asociadas a él.", {
          dangerMode: true,
          buttons: true
        }).then((result) => {
            if(result) {
                this.$swal('Eliminado', 'Se ha borrado correctamente el deporte', 'success')
                 HTTP.delete(`sports/${this.idDeporte}`)
                      .then(this._successHandler)
                       .catch(this._errorHandler)
          } else {
                this.$swal('Cancelado', 'No se ha realizado ningún cambio', 'info')
          }
        })
    
  },
    
    eliminar(){
      this.alertDisplay();
     
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

.alertas{
  background:black;
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
  width: 100%;
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

td {
    
    color:grey;
    margin-left:auto;
    margin-right:auto;
  }

  button{
  width: 10%;
  float:right;
  background: #fb887c;
  border-radius: 5px;
  border: 0.1px solid #fb887c;
  font-size:1.8em;
  color: white;
}

.editar{
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


.eliminar{
   width: 47%;
   float:right;
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

.iconStyle{
  float:left;
  margin-right:10px;

}
</style>
