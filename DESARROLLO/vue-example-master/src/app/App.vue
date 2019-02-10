<template>
  
  <div id="app">

  	 <notifications
       :max="3"
       :width="400"
       position="top center"/>

    <MenuBar/>

    <router-view class="content"/>
    <footer class="footer-distributed">

      <div class="footer-left">

        <h3>Company<span>logo</span></h3>

        <p class="footer-links">
          <a href="#">Home</a>
          ·
          <a href="#">Blog</a>
          ·
          <a href="#">Pricing</a>
          ·
          <a href="#">About</a>
          ·
          <a href="#">Faq</a>
          ·
          <a href="#">Contact</a>
        </p>

        <p class="footer-company-name">Company Name &copy; 2015</p>
      </div>

      <div class="footer-center">

        <div>
          <i class="fa fa-map-marker"></i>
          <p><span>21 Revolution Street</span> Paris, France</p>
        </div>

        <div>
          <i class="fa fa-phone"></i>
          <p>+1 555 123456</p>
        </div>

        <div>
          <i class="fa fa-envelope"></i>
         <!-- <p><a href="mailto:sandra.dopico1@udc.es">sandra.dopico1@udc.es</a></p>-->
        </div>

      </div>

      <div class="footer-right">

        <p class="footer-company-about">
          <span>About the company</span>
          Lorem ipsum dolor sit amet, consectateur adispicing elit. Fusce euismod convallis velit, eu auctor lacus vehicula sit amet.
        </p>

        <div class="footer-icons">

          <a href="#"><i class="fab fa-facebook"></i></a>
          <a href="#"><i class="fa fa-twitter"></i></a>
          <a href="#"><i class="fa fa-linkedin"></i></a>
          <a href="#"><i class="fa fa-github"></i></a>

        </div>

      </div>

    </footer>
  </div>
</template>

<script>
import auth from './common/auth'
import MenuBar from './components/MenuBar'


export default {
  name: 'App',
   components: { MenuBar },
   data() {
     return {
       // enlazamos el objeto donde vamos a guardar los datos de autenticación
       // de esta forma se activa el data-binding y los podemos usar como
       // propiedades públicas en los componentes
       storeAuth: auth
     }
   },
   created() {
     // nada más ejecutar la aplicación comprobamos si estamos autenticados
     if (auth.getToken()) {
       auth.authenticate().catch(() => auth.logout())
     }
   }
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
      padding: 55px 50px;
      margin-top: 60px;
  }

.footer-distributed .footer-left,
.footer-distributed .footer-center,
.footer-distributed .footer-right{
  display: inline-block;
  vertical-align: top;
}

/* Footer left */

.footer-distributed .footer-left{
  width: 40%;
}

/* logo */

.footer-distributed h3{
  color:  #ffffff;
  font: normal 36px 'Cookie', cursive;
  margin: 0;
}

.footer-distributed h3 span{
  color:  #5383d3;
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

/* Footer Center */

.footer-distributed .footer-center{
  width: 35%;
  margin-top:25px;
}


/* Footer Right */

.footer-distributed .footer-right{
  width: 20%;
}

.footer-distributed .footer-company-about{
  line-height: 20px;
  color:  #92999f;
  font-size: 13px;
  font-weight: normal;
  margin: 0;
}

.footer-distributed .footer-company-about span{
  display: block;
  color:  #ffffff;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 20px;
}

.footer-distributed .footer-icons{
  margin-top: 25px;
}

.footer-distributed .footer-icons a{
  display: inline-block;
  width: 35px;
  height: 35px;
  cursor: pointer;
  background-color:  #33383b;
  border-radius: 2px;

  font-size: 20px;
  color: #ffffff;
  text-align: center;
  line-height: 35px;

  margin-right: 3px;
  margin-bottom: 5px;
}

/* If you don't want the footer to be responsive, remove these media queries */

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


   
</style>
