<template>
  <div class="car">
    <b-carousel id="carousel1"
                style="text-shadow: 1px 1px 2px #333;"
                controls
                indicators
                background="#ababab"
                :interval="4000"
                img-width="1024"
                img-height="480"
                
    >


     <b-carousel-slide caption="¿Qué es PLAY2GETHER?">
        <img slot="img" class="d-block img-fluid w-100" 
             src="padel.png">
          <p class="texto">
          Es una plataforma para poner en contacto a jugadores de diferentes deportes para realizar competiciones en las diferentes localizaciones disponibles.
        </p>
      </b-carousel-slide>

    <b-carousel-slide caption="¿Porqué formar parte de este proyecto?">
        <img slot="img" class="d-block img-fluid w-100" w
             src="basket1.jpg">
             <p>
         Gracias a PLAY2GETHER puedes conocer a personas con los mismos gustos deportivos, aparte de no quedarte con las ganas de jugar algún partido ya que siempre habrá alguien dispuesto a competir contra o contigo.
        </p>
      </b-carousel-slide>

      <b-carousel-slide caption="¿Deportes?">
        <img slot="img" class="d-block img-fluid w-100" w
             src="tennis1.jpg">
             <p>
          Los deportes que engloba nuestra plataforma son: Fútbol, Tennis, Paddel y Baloncesto llegándose a ampliar en un futuro segun las peticiones y gustos de los usuarios.
        </p>
      </b-carousel-slide>

      <b-carousel-slide caption="¿Cómo formar parte?">

        <img slot="img" class="d-block img-fluid w-100" 
             src="fut.jpg">
             <p>
          Simplemente tienes que registrarte, introducir tus datos personales y empezar a disfrutar de nuestra Página Web organizando eventos o formando parte de ellos!
        </p>
      </b-carousel-slide>
    </b-carousel>

  </div>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel'

export default {
   components: {
    Carousel,
    Slide
  }
}
</script>


<style scoped lang="scss">

.car { 
  width:65%;
  margin-left: auto;
  margin-right: auto;
  margin-top:50px;
 
}

</style>