<template>
  <div v-if="bol"class="information message">
    <div class="half margin-right">
        <h2 class="titulo" v-if=" idDeporte==null">Nuevo Deporte</h2>
        <h2 class="titulo" v-if=" idDeporte!=null">{{this.sport.type}}</h2>
        <input type='text' class="searchButton" v-if=" idDeporte==null" placeholder='Nombre del deporte' v-model="sport.type" autofocus required >
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

         <b-form-group
        label="Icono del deporte: "
        label-for="perfil">
        <div v-if="!file">
          <input class="inputfile" type="file" id="file" ref="file" @change="onFileChange"/>
        </div>
        <div v-else>
          <img :src="loaded" />
          <img v-if="this.sport.rutaImagen"v-bind:src="getImagen(this.sport.rutaImagen)" />
          <b-btn id="botonEliminaImagen" variant="danger" @click="removeImage">Eliminar</b-btn>
        </div>

      </b-form-group>
      <div class="buttonsActions">
        <button class="guardar"@click="guardar()"> Guardar</button>
        <button @click="hide"> Cancelar </button> 
      </div>
    </div>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import Multiselect from 'vue-multiselect'
import { baseURL } from '../../common/http-common'
import PictureInput from 'vue-picture-input'
import FormDataPost from '../../common/upload'



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
      fallo:false,
      file: '',
      loaded: '',
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
                  .then(response => {this.sport = response.data 
                        return response })
                  .then(this.recogerFoto)
                  .then(response => {this.typeSport = response.data.type})
                  .catch(err => this.error = err.message)
      }else{
           this.sport={};
        }
     
      HTTP.get('sports')
              .then(response => {this.sports = response.data})
              .catch(err => { this.error = err.message})
    
    },


    hide(){
      this.bol=false;
      this.$emit('Cerrar');

    },

    recogerFoto(){
      this.file=this.getImagen();
    },
    
     onFileChange(e){
      var files = e.target.files || e.dataTransfer.files; //OBJECT FILE para cuando se añade o se arrastra
      if (!files.length)
        return;
      this.createImage(files[0]);
      this.file = files[0];

     },
    createImage(file) {
      var image = new Image();
      var reader = new FileReader();
      var vm = this;

      reader.onload = (e) => {
        vm.loaded = e.target.result;//contenido del fichero
      };
      reader.readAsDataURL(file);// result contiene  la información como una URL representando la información del archivo como una cadena de caracteres codificados en base64.
    },
     removeImage(e){
      this.sport.rutaImagen='';
      this.file = '';
  

     },
      getImagen(path){
      return baseURL + "sports/imagenes/" + path;
    },
      submitFile(){
      if (this.file != ''){
        let formData = new FormData();//Vamos a enviar los datos con la misma codificación del formulario, se establece en "multipart/form-data".
        formData.append('file', this.file, this.sport.type + ".png");

        this.sport.rutaImagen = this.sport.type  + ".png";

        HTTP.post('sports/uploadFile',
          formData,
          {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
          )
        .catch(function(){
          console.log('FAILURE!!');
        });

      }
    },
    guardar(){
      if(this.idDeporte){//ACTUALIZAR
         
        if(this.checkForm()==true){
            this.submitFile();
              HTTP.put(`sports/${this.idDeporte}`,this.sport)
                      .then(this._successHandler)
                      .catch(err => { this.error = err.message})
           }else{
            this.$swal('Alerta!', this.error, 'error')
           }
         
          

      }else{//CREAR
        if(this.checkForm()==true){
          this.submitFile();
            HTTP.post('sports',this.sport)
                    .then(this._successHandler)
                    .catch(err => { this.error = err.message})
         }else{
           this.$swal('Alerta!', this.error, 'error')
        }
      } 
    },

     nameCustom ({ name }) {
      return `${name} `
    },

    checkForm () {

      if (!this.sport.type) {
        this.error="Introduzca un nombre para el deporte"
        return false;
      }
      if(this.file == ''){
        this.error= "Porfavor elija una foto de icono para el deporte "
        return false;
      }

      if (this.sport.type || this.sport.type && this.spot.locations) {
        return true;
      }
       


    },

    getLocations() {
        
       HTTP.get('locations')
            .then(response => this.alllocations = response.data)
            .catch(err => this.error = err.message)
    },
   
    _successHandler(response) {
      this.$swal('Guardado', 'Los cambios se han guardado correctamente', 'success')
      this.bol=false;
      this.$emit('Cerrar');

    },

    _errorHandler(err) {
      this.error = err.response.data.message
      this.$swal('Alerta!', this.error, 'error')
      
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
  float:right;
  background: #fff;
  padding: 0.6em; 
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
img {
  width: 30%;
  display: block;
  margin-bottom: 10px;
}
#botonEliminaImagen {
    background-color: red;
    border: none;
    display: inline-block;
    border-radius: 4px;
    border: none;
    color: #FFFFFF;
    text-align: center;
    font-size: 13px;
    padding: 5px;
    width: 80px;
    transition: all 0.5s;
    cursor: pointer;
    margin: 5px;

}

.buttonsActions{
margin-top:17%;
}
</style>
