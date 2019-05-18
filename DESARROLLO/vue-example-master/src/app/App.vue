<template>
  
   <div id="app">
    <notifications
      :max="3"
      :width="400"
      position="top center">
      <!-- -->
    </notifications>
    <MenuBar>
      <!-- -->
    </MenuBar>

    <router-view class="content">
      <!-- -->
    </router-view>
    <footer class="footer-distributed">

      <div class="footer-left">

        <h3>PLAY<span>2</span>GETHER</h3>

        <p class="footer-links">
          <a href="#">Iniciar Sesión</a>
          ·
          <a href="#">Partidos</a>
          ·
          <a href="Home">Sobre Nosotros</a>
          .
          <a href="#">Contacto</a>
          ·
          <a href="#">Politica de Privacidad</a>
           .
          <a href="#">Licencias</a>
          
        </p>
       
        <p class="footer-company-name">PLAY2GETHER &copy; 2019</p>
      </div>
    
  </div>
</template>

<script>
import auth from './common/auth'
import MenuBar from './components/MenuBar'
import { HTTP } from './common/http-common'


export default {
  name: 'App',
   components: { MenuBar },
   data() {
     return {
       // enlazamos el objeto donde vamos a guardar los datos de autenticación
       // de esta forma se activa el data-binding y los podemos usar como
       // propiedades públicas en los componentes
       storeAuth: auth,
      
      
     }
  
   },
   
    
    
   
}
</script>

<style scoped lang="scss">
  #app {
    color:  #ffffff;
    
  }


  .notification {
     margin: 10px;
     margin-bottom: 0;
     border-radius: 3px;
     font-size: 13px;
     padding: 10px 20px;
     color: #495061;
     background: #EAF4FE;
     border: 1px solid #D4E8FD;}

     .error {
       background: #ffffff;
     }

     .success{
      background: #68CD86;
     }

     .notification-title {
       letter-spacing: 1px;
       text-transform: uppercase;
       font-size: 10px;
       color: #2589F3;
     }

  .footer-distributed{
      background-color: #292c2f;
      width: 100%;
      font: bold 16px sans-serif;
      padding: 20px 40px;

  }

.footer-distributed .footer-left,
.footer-distributed .footer-center,
.footer-distributed .footer-right{
  display: inline-block;
  vertical-align: top;
}

/* Footer left */

.footer-distributed .footer-left{
  width: 60%;
}

/* logo */

.footer-distributed h3{
  color:  #ffffff;

  margin: 0;
}

.footer-distributed h3 span{
  color:  #17a2b8;
}

/*links*/

.footer-distributed .footer-links a{
  display:inline-block;
  line-height: 1.8;
  text-decoration: none;
  color:  inherit;
}

/*@*/
.footer-distributed .footer-company-name{
  color:  #8f9296;
  font-size: 14px;
  font-weight: normal;
  margin: 0;
}




@media (max-width: 880px) {

  .footer-distributed{
    font: bold 14px sans-serif;
  }

  .footer-distributed .footer-left,
  .footer-distributed .footer-center,
  .footer-distributed .footer-right{
    display: block;
    width: 100%;
    margin-bottom: 40px;
    text-align: center;
  }

  .footer-distributed .footer-center i{
    margin-left: 0;
  }

}
.search{
  width:10%;
  float:right;
  margin-right:1%;
  margin-top:1%;
}
   
</style>
