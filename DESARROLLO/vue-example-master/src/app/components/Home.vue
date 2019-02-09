<template>
  <div>
    <b-carousel id="carousel1"
                style="text-shadow: 1px 1px 2px #333;"
                controls
                indicators
                background="#ababab"
                :interval="4000"
                img-width="1024"
                img-height="480"
                v-model="slide"
                @sliding-start="onSlideStart"
                @sliding-end="onSlideEnd"
    >

     <b-carousel-slide>
        <img slot="img" class="d-block img-fluid w-100" width="1024" height="480"
             src="portada.jpg">
      </b-carousel-slide>

     <b-carousel-slide>
     <h1>Hello world!</h1>
        <img slot="img" class="d-block img-fluid w-100" width="1024" height="480"
             src="portada.jpg">
      </b-carousel-slide>

      <b-carousel-slide>
        <img slot="img" class="d-block img-fluid w-100" width="1024" height="480"
             src="portada.jpg">
      </b-carousel-slide>

      <b-carousel-slide>
        <img slot="img" class="d-block img-fluid w-100" width="1024" height="480"
             src="portada.jpg">
      </b-carousel-slide>

      <b-carousel-slide>
        <img slot="img" class="d-block img-fluid w-100" width="1024" height="480"
             src="portada.jpg">
             <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut pellentesque
          ut lacus vel interdum.
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
.imagen{
   width:100%;
}




</style>