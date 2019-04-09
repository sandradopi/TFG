<template>
  <div>
         
    <form @submit.prevent="userLogin">
    <div class= "formulario">
      <div class ="contenido" align="middle">
      <h1 class= "titulo" align="middle">Sign in! </h1>
    </div>
    <div class="form-group">
      <label for="username">Username</label>
      <input
        id="username"
        v-model="username"
        name="username"
        placeholder="Escribe tu login"
        type="text"
        class="form-control"
        required>
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input
        id="password"
        v-model="password"
        type="password"
         placeholder="Escribe tu contraseña"
        class="form-control"
        name="password"
        required>
    </div>
    <div class="registro">
       <button class="button"
         type="submit"><span>Log In</span></button> 
      
         <b-btn class="button1"
              :to="{ name: 'Registro'}"
              variant="link"><span>Registro</span></b-btn></div>

   </div>
  </form>
   </div>
</template>

<script>
import auth from '../common/auth'
export default {
  data () {
    return {
      username: '',
      password: '',
      error: null,

    }
  },
  methods: {
     userLogin() {
      auth.login({
        login: this.username,
        password: this.password
      })
      .then(() => {
        this.volverHome();
      })
      .catch(err => {
        this.error = err.response.data.message
      })
    },
  
    volverHome(){
      this.$router.replace({ name: 'Home', params: { id: true}})
    },
  }
}
</script>
<style scoped lang="scss">
.formulario{
  width:40%;
  padding:40px;
  margin-left:30%;
  color:#17a2b8;


}

.button, .button1 {
  display: inline-block;
  border-radius: 4px;
  background-color: #17a2b8;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 17px;
  padding: 9px;
  width: 100px;
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
.registro{
   margin-top:5%;
   width:50%;
   margin-left:120px;
   margin-bottom:110px;
}

.button1{
   background-color: #fb887c;
   float:right;
   
 }

.titulo{
 margin-top:20%;
  color:#17a2b8;

}


</style>