<template>
  <div>

    <div class="float-right">
      <b-btn class="button"
        variant="primary"
        @click="back()"><span>Atrás</span></b-btn>
      <b-btn class="button1"
        variant="success"
        @click="save()"><span>Crear</span></b-btn>
    </div>
<div>

  <div class="formulario">
  <div class ="contenido" align="middle">
      <h1 class= "titulo" align="left">Crea tu cuenta! </h1>
  </div>
  <b-form
      v-if="user"
      class="app-form"
      @submit="save">
    <div class="web">
      <b-form-group
        label="Login: *"
        label-for="login">
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
        label-for="contraseña">
        <b-form-input
          id="password"
          v-model="user.password"
          type="password"
          autocomplete="off"
          required
          placeholder="Introduce la contraseña"/>
      </b-form-group>



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
    </div>
<hr class="linea">
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
        label="Fecha de Nacimiento: *"
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
        label="Ciudad: *"
        label-for="ciudad">
        <b-form-input
          id="city"
          v-model="user.city"
          type="text"
          autocomplete="off"
          required
          placeholder="Introduce su ciudad"/>
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
import Vue from 'vue'
export default {
  components: {  Multiselect},
  data() {
    return {
      user: {},
      error: null,
    }

  },
 created() {
    this.fetchData()
    
  },
   methods: {
     fetchData() {
      
     },
    userLogin() {
      auth.login({
        login: this.user.login,
        password: this.user.password
      })
      .then(this._successHandler)
      .catch(this._errorHandler)
      
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
        this.errors= "The email: "+ this.user.email +" don´t have the good format, review it "
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
      if (!this.user.birthday) {
        this.errors= "La fecha de nacimiento es un campo obligatorio "
        return false;
      }

      if (!this.user.city) {
        this.errors= "La ciudad es un campo obligatorio "
        return false;
      }


      
     
      if (this.user.login && this.user.password && this.user.email && this.user.name && this.user.surname1 && this.user.surname2 && this.user.birthday && this.user.city) {
        return true;
      }
    },
    save() {

       if (this.checkForm() == true) {
    
          return HTTP.post('register', this.user)
          .then(this.userLogin)
          .catch(this._errorHandler)
      }else{
        
        Vue.notify({
          text: this.errors,
          type: 'error'})
    
      }
      
    },
    notification(){ 
      Vue.notify({
               text: this.error,
               type: 'error'})
    },
    back() {
      this.$router.go(-1)
    },
    _successHandler(response) {
      this.$router.replace({ name: 'Partidos'})
    },
      _errorHandler(err) {
      this.error = err.response.data.message
      this.notification()
     }
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
  }

  .linea{
  border-left: 6px solid #17a2b8;
  height: 410px;
  position: absolute;
  left: 50%;

  }

  .formulario{
  margin-top:4%;
  margin-bottom:35%;
  width:60%;
  margin-left:20%;
  color:#17a2b8;
  
}
.web{
  width:40%;
  float:left;
  margin-left:20px;
}

.personal{
  width:40%;
  float:right;
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

.button span, .button1 span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after , .button1 span:after{
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span,.button1:hover span {
  padding-right: 20px;
}

.button:hover span:after, .button1:hover span:after{
  opacity: 1;
  right: 0;
}
.button1{
   background-color: #f4511e;
   float:right;
   
 }
</style>