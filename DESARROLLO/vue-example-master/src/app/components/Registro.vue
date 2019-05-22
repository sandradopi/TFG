<template>
  <div class="all">

    <div class="float-right">
      <b-btn class="button"
        variant="primary"
        @click="back()"><span><font-awesome-icon icon="arrow-left"style="font-size:20px;"/></span></b-btn>
      <b-btn class="button1"
        variant="success"
        @click="save()"><span>Guardar</span></b-btn>
    </div>
<div>


  <div class="formulario">
  <div class ="contenido" align="middle">
      <h1 class= "titulo" align="left">Datos Personales:</h1>
  </div>
  <b-form
      v-if="user"
      class="app-form"
      @submit="save">
      <div class="personal">  
  <b-form-group
        label="Nombre: *"
        label-for="nombre">
        <b-form-input
          id="nombre"
          v-model="user.name"
          type="text"
          autocomplete="off"
          required
          placeholder="Introduce el nombre"/>
      </b-form-group>

      <b-form-group
        label="Primer Apellido: *"
        label-for="apellido1">
        <b-form-input
          id="surname1"
          v-model="user.surname1"
          type="text"
          autocomplete="off"
          required
          placeholder="Introduce el primer apellido"/>
      </b-form-group>

      <b-form-group
        label="Segundo Apellido: *"
        label-for="apellido2">
        <b-form-input
          id="surname2"
          v-model="user.surname2"
          type="text"
          autocomplete="off"
          required
          placeholder="Introduce el segundo apellido"/>
      </b-form-group>



       <b-form-group
        label="Fecha de Nacimiento: "
        label-for="Data">
        <b-form-input
          id="data"
          v-model="user.birthday"
          type="date"
          autocomplete="off"
          required
          placeholder="Introduce la fecha de nacimiento"/>
      </b-form-group>


        <b-form-group
          label="Ciudad: "
          label-for="City">
         <multiselect 
            v-model="user.city" 
            :options="city" 
            :searchable="true" 
            :close-on-select="true" 
            :show-labels="false" 
            placeholder="Ciudad de residencia"></multiselect>
      <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
      </b-form-group>
      <b-form-group
        label="Foto de perfil: "
        label-for="perfil">

        <div v-if="!file">

          <input class="inputfile" type="file" id="file" ref="file" @change="onFileChange"/>
        </div>
        <div v-else>
          <img :src="loaded" />
          <img v-if="this.user.rutaImagen"v-bind:src="getImagen(this.user.rutaImagen)" />
          <b-btn id="botonEliminaImagen" variant="danger" @click="removeImage">Eliminar</b-btn>
        </div>
      </b-form-group>


     
   
</div>
   
<hr class="linea">
 <div class="web">
  <b-modal
        id="modalPrevent1"
        ref="modal1"
        title="Nueva Contraseña"
        @ok="handleOk1"
        @shown="clearName1">

        <form @submit.stop.prevent="handleSubmit">
          <b-form-group>
               <b-form-input type="password" placeholder="Introduce la nueva contraseña" v-model="passwordNew" />
          </b-form-group>
          <b-form-group>
            <b-form-input type="password" placeholder="Repite la contraseña" v-model="passwordNew1" />
          </b-form-group>
        </form>
      </b-modal>

       <b-form-group
        label="Email: *"
        label-for="email">
        <b-form-input
          id="email"
          v-model="user.email"
          type="email"
          autocomplete="off"
          required
          placeholder="Introduce el email"/>
      </b-form-group>

       <b-form-group
        label="Contraseña: *"
        label-for="contraseña" v-if="this.$route.params.id != null">
        <b-form-input
          id="passwordChange"
          v-model="password"
          type="password"
          autocomplete="off"
          required
          placeholder="Introduce la contraseña"/></b-form-input>
          <b-btn variant="outline-secondary" v-b-modal.modalPrevent1 ><span>Cambiar Contraseña</span></b-btn>
      </b-form-group>
       

<div class="equipos" v-if="this.$route.params.boleano!=null">
      <b-form-group v-if="this.$route.params.boleano!=null"
          label="Equipos favorito: "
          label-for="Equipos Favoritos">
         <multiselect 
            class="multi"
            v-model="user.favoritos" 
            :options="this.allteams"
            :multiple="true"
            :searchable="true" 
            :clear-on-select="false" 
            tag-placeholder="Equipo/Deporte"
            :preserve-search="true"
            :close-on-select="true" 
            :show-labels="false"
            track-by="idTeam"
            placeholder="Equipos favoritos"
             :custom-label="nameCustom"
            
            >

      </multiselect>
      <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
      </b-form-group>

        <b-form-group v-if="this.$route.params.boleano!=null"
          label="Equipos donde juego: "
          label-for="Equipos donde juego">
         <multiselect 
            class="multi"
            v-model="user.juego" 
            :options="this.allteams"
            :multiple="true"
            :searchable="true" 
            tag-placeholder="Equipo/Deporte"
            :clear-on-select="false" 
            :preserve-search="true"
            :close-on-select="true" 
            :show-labels="false"
            track-by="idTeam"
            placeholder="Equipos donde juego"
            :custom-label="nameCustom"
            >
      </multiselect>
      <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
      <b-btn class="button2" variant="outline-success" v-b-modal.modalPrevent ><span>Añadir Equipo</span></b-btn>

      <b-modal
        id="modalPrevent"
        ref="modal"
        title="Equipo nuevo"
        @ok="handleOk"
        @shown="clearName">

        <form @submit.stop.prevent="handleSubmit">
          <b-form-group>
              <multiselect 
                v-model="deporte" 
                :options="this.allsports"
                :multiple="false"
                :searchable="true" 
                :preserve-search="true"
                :close-on-select="true" 
                :show-labels="false"
                track-by="idSport"
                placeholder="Deportes"
                :custom-label="nameCustom1"
            >
    </multiselect>
          </b-form-group>
          <b-form-group>
            <b-form-input type="text" placeholder="Introduce el Equipo" v-model="equipo" />
          </b-form-group>
        </form>
      </b-modal>
      </b-form-group>
    </div>

      <b-form-group
        label="Login: *"
        label-for="login" v-if="this.$route.params.id == null">
        <b-form-input
          id="login"
          v-model="user.login"
          type="text"
          autocomplete="off"
          required
          placeholder="Introduce el login"/>
      </b-form-group>

      <b-form-group
        label="Contraseña: *"
        label-for="contraseña" v-if="this.$route.params.id == null">
        <b-form-input
          id="password"
          v-model="user.password"
          type="password"
          autocomplete="off"
          required
          placeholder="Introduce la contraseña"/>
      </b-form-group>


      
    </div>
  
 
    </b-form>

  </div>
     
</div>
</div>
</template>

<script>
import { HTTP } from '../common/http-common'
import auth from '../common/auth'
import Multiselect from 'vue-multiselect'
import PictureInput from 'vue-picture-input'
import FormDataPost from '../common/upload'
import { baseURL } from '../common/http-common'
import Vue from 'vue'
export default {
  mounted(){
  

  },
  components: {  Multiselect,PictureInput},
  data() {
    return {
      user: {},
      error: null,
      city:['A Coruña','Alaba','Albacete', 'Alicante','Almeria','Asturias','Avila','Badajoz','Barcelona','Burgos','Cáceres','Cádiz','Cantabria','Castellón','Ceuta','Ciudad Real','Córdoba','Cuenca','Formentera','Girona','Granada','Guadalajara','Guipuzcoa','Huelva','Huesca','Ibiza','Jaén', 'La Rioja', 'Las Palmas de Gran Canaria','León','Lérida','Lugo','Madrid','Málaga','Mallorca','Menorca','Murcia','Navarra','Orense','Palencia','Pontevedra','Salamanca','Santa Cruz de Tenerife','Segovia', 'Sevilla','Soria','Tarragona','Teruel','Toledo','Valencia','Valladolid','Vizcaya','Zamora','Zaragoza'],
      allteams:[],
      allsports:[],
      nameTag:null,
      thesport:null,
      allsportType:[],
      newTeam:null,
      bol:false,
      name:null,
      deporte:null,
      equipo:null,
      file: '',
      loaded: '',
      password:'**********',
      passwordNew:'',
      passwordNew1:''
    }

  },
 created() {
    this.fetchData()
    
  },
   methods: {
     fetchData() {
     if(this.$route.params.id != null){
       this.login=this.$route.params.id;
       HTTP.get(`users/${this.$route.params.id}`) 
          .then(response => { this.user = response.data
                 return response })
          .then(this.recogerFoto)
          .then(this.getTeams)
          .then(this.getSports)
          .catch(err => { this.error = err.message})
       
        
     }
    
      
     },
      getImagen(path){
      return baseURL + "users/imagenes/" + path;
    },
    recogerFoto(){
      this.file=this.getImagen();
    },
    handleOk1(evt){
      evt.preventDefault()
    
      if(this.passwordNew!=this.passwordNew1){
         this.$swal('Alerta!', "Las contraseñas no coinciden!", 'error')
      }else if(this.passwordNew==''||this.passwordNew1==''){
        this.$swal('Alerta!', "Rellene ambos campos!", 'error')
      }else{
         HTTP.put(`users/${this.WhatLogin()}/${this.passwordNew}`)
              .then(this._successHandler1)
              .catch(this._errorHandler)
        

      }

    },
    clearName1(){
      this.passwordNew1=''
      this.passwordNew=''
      
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
      this.user.rutaImagen='';
      this.file = '';
  

     },
      clearName() {
        this.equipo = ''
        this.deporte = ''
      },
      handleOk(evt) {
        // Prevent modal from closing
        evt.preventDefault()
        if (!this.deporte) {
           this.$swal('Alerta!', "Seleccione un deporte!", 'error')
        
        } else if (!this.equipo){
           this.$swal('Alerta!', "Intoduzca un nombre para el equipo!", 'error')
          
        }else{
          this.handleSubmit()
        }
      },
      handleSubmit() {

        var tag= {
          idTeam:null,
          name: this.equipo,
          sport:this.deporte
      }

        HTTP.post('teams', tag)
        .then(response =>  this.allteams.push(tag))
        .catch(this._errorHandler)

        this.$swal('Guardado', 'Se ha añadido el equipo correctamente', 'success')
        this.clearName()
        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
          this.$refs.modal.hide()
        })
      },

    userLogin() {
      auth.login({
        login: this.user.login,
        password: this.user.password
      })
      .then(this._successHandler)
      .catch(this._errorHandler)
      
    },
    nameCustom ({ name, sport}) {
      return `${name}/${sport.type}`
    },
    
     nameCustom1 ({ type }) {
      return `${type} `
    },

    getTeams() {
        
       HTTP.get('teams')
      .then(response => this.allteams = response.data)
      .catch(err => this.error = err.message)
    },

     getSports() {
        
       HTTP.get('sports')
      .then(response => this.allsports = response.data)
      .catch(err => this.error = err.message)
    },
   
   
     

    checkForm () {
      if (!this.user.login) {
        this.errors = "El login es un campo obligatorio"
        return false;
      }

      if (!this.user.password) {
        this.errors ="La contraseña es un campo obligatorio "
        return false;
      } else if (this.user.password.length <4 ) {
        this.errors ="La contraseña es muy corta. Mínimo 4 letras!"
        return false;
      }

      if (!this.user.email) {
        this.errors= "El email es un campo obligatorio "
        return false;
      }

      var expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
      if (!expr.test(this.user.email)) {
        this.errors= "El email"+" ' "+this.user.email +" ' "+" no tiene el formato ejemplo@ejemplo.com "
        return false;
      } 

      if (!this.user.name) {
        this.errors= "El nombre es un campo obligatorio "
        return false;
      }

      if (!this.user.surname1) {
        this.errors= "El primer apellido es un campo obligatorio. "
        return false;
      }

      if (!this.user.surname2) {
        this.errors= "El segundo apellido es un campo obligatorio. "
        return false;
      }
      


      
     
      if (this.user.login && this.user.password && this.user.email && this.user.name && this.user.surname1 && this.user.surname2) {
        return true;
      }
    },
    checkForm1 () {
      
      if (!this.user.email) {
        this.errors= "El email es un campo obligatorio "
        return false;
      }

      var expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
      if (!expr.test(this.user.email)) {
        this.errors= "El email: "+" ' "+ this.user.email +" ' "+" no tiene el formato ejemplo@ejemplo.com "
        return false;
      } 

      if (!this.user.name) {
        this.errors= "El nombre es un campo obligatorio "
        return false;
      }

      if (!this.user.surname1) {
        this.errors= "El primer apellido es un campo obligatorio. "
        return false;
      }

      if (!this.user.surname2) {
        this.errors= "El segundo apellido es un campo obligatorio. "
        return false;
      }
      if(this.file == ''){
        this.errors= "Porfavor elija una foto para su perfil "
        return false;
      }


      
     
      if (this.user.email && this.user.name && this.user.surname1 && this.user.surname2 ) {
        return true;
      }
    },
    submitFile(){
      if (this.file != ''){
        let formData = new FormData();//Vamos a enviar los datos con la misma codificación del formulario, se establece en "multipart/form-data".
        formData.append('file', this.file, this.user.login + ".jpg");

        this.user.rutaImagen = this.user.login  + ".jpg";

        HTTP.post('users/uploadFile',
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
    save() {
      if(this.$route.params.id != null){
        if (this.checkForm1() == true) {

              HTTP.put(`users/${this.user.idUser}`,this.user)
              .then( this.submitFile)
              .then(this._successHandler)
              .catch(this._errorHandler)

          }else{
            this.$swal('Alerta!', this.errors, 'error')
         }
    
      }else{
      
       if (this.checkForm() == true) {
          this.submitFile();
          return HTTP.post('register', this.user)
          .then(this.userLogin)
          .catch(this._errorHandler)
      }else{
        
       this.$swal('Alerta!', this.errors, 'error')
    
      }
      }
    },
    notification(){ 
     this.$swal('Alerta!', this.error, 'error')
    },
    back() {
      this.$router.go(-1)
    },
    WhatLogin() {
      return auth.user.login
    },
    _successHandler(response) {
      this.$swal('Guardado', 'Los cambios se han guardado correctamente', 'success')
      this.$router.replace({ name: 'UserDetail', params: { id: this.user.login}})
    },
    _successHandler1(response){
      this.$swal('Guardado', 'Se ha actualizado correctamente la contraseña', 'success')
      this.clearName()
      this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
          this.$refs.modal1.hide()
        })

    },
      _errorHandler(err) {
      this.error = err.response.data.message
      this.notification()
     },

  }
}
</script>

<style scoped lang="scss">
  .error{
    font: 150% sans-serif;
    font: message-box;
  }
  .titulo{
    margin-left:22px;
    margin-top:100px;
  }
  .all{
    margin-bottom:9%;
  }

  .linea{
  border-left: 6px solid #17a2b8;
  height: 470px;
  position: absolute;
  left: 50%;


  }

  .formulario{
  margin-top:4%;
  margin-bottom:37%;
  width:60%;
  margin-left:20%;
  color:#17a2b8;
  
}
.web{
  width:40%;
  float:right;
  margin-left:20px;
}

.personal{
  width:40%;
  float:left;
}

.float-right{
  margin-top:10px;
  margin-right:20%;
}
 .button, .button1 {
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

}

 .button1 span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

 .button1 span:after{
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button1:hover span {
  padding-right: 20px;
}

.button:hover span:after, .button1:hover span:after{
  opacity: 1;
  right: 0;
}
.button1{
   background-color: #fb887c;
   float:right;
   
 }

 .button2{
  margin-top:20px;
  margin-left:90px;
 }

 .equipos{
  background: #f3f3f3;
  padding:10px;
 }

 fieldset {
    border: none;
    margin: 0 2px;
    padding: .35em .625em .75em;
}

#botonEliminaImagen {
    background-color: red;
    font-size: 13px;
    margin-left: 10px;
    border: none;
}

img {
  width: 30%;
  display: block;
  margin-bottom: 10px;
}

#passwordChange{
    float: left;
    width: 44%;
    margin-right:10px;
}

</style>