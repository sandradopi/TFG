<template>
  <div class="car">
   <div class="responsive">
    </div>
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
             src="resources/padel.png">
          <p class="texto">
          Es una plataforma para poner en contacto a jugadores de diferentes deportes para realizar competiciones en las diferentes localizaciones disponibles.
        </p>
      </b-carousel-slide>

    <b-carousel-slide caption="¿Porqué formar parte de este proyecto?">
        <img slot="img" class="d-block img-fluid w-100" w
             src="resources/basket1.jpg">
             <p>
         Gracias a PLAY2GETHER puedes conocer a personas con los mismos gustos deportivos, aparte de no quedarte con las ganas de jugar algún partido ya que siempre habrá alguien dispuesto a competir contra o contigo.
        </p>
      </b-carousel-slide>

      <b-carousel-slide caption="¿Deportes?">
        <img slot="img" class="d-block img-fluid w-100" w
             src="resources/tennis1.jpg">
             <p>
          Los deportes que engloba nuestra plataforma son: Fútbol, Tennis, Paddel y Baloncesto llegándose a ampliar en un futuro segun las peticiones y gustos de los usuarios.
        </p>
      </b-carousel-slide>

      <b-carousel-slide caption="¿Cómo formar parte?">

        <img slot="img" class="d-block img-fluid w-100" 
             src="resources/fut.jpg">
             <p>
          Simplemente tienes que registrarte, introducir tus datos personales y empezar a disfrutar de nuestra Página Web organizando eventos o formando parte de ellos!
        </p>
      </b-carousel-slide>
     
    </b-carousel>
 
    <b-modal
        class="formulario"
        id="modal3"
        ref="modal3"
        title="Jugadores del Equipo"
        
        @ok="handleOk1"
       >

        <form @submit.stop.prevent="handleSubmit">
           <b-form-group class="jugadores">
              <h6>¿Algún jugador cambió finalmente de equipo?</h6>
               <div v-for=" playerG in this.playersChange" :key="playerG.idPlayer">
                   <li class="jugadores">{{playerG.player.name}} {{playerG.player.surname1}}</li>
              <multiselect 
                v-model="playerG.equipo" 
                :options="equipos"
                :multiple="false"
                :preserve-search="false"
                :close-on-select="true" 
                :show-labels="false"
                placeholder="Equipo"
                >
              </multiselect>
                <link rel="stylesheet" href="https://unpkg.com/vue-multiselect@2.1.0/dist/vue-multiselect.min.css">
               </div>
            </b-form-group>            
        </form>
      </b-modal>
<b-modal
        id="modalPrevent22"
        ref="modal"
        class="formulario"
        title="Completar Resultados"
        ok-only ok-variant="secondary" 
        ok-title="Cancelar"
       >

        <form @submit.stop.prevent="handleSubmit">
          <b-form-group>
             <div class="informationmessage2">
             <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
             <div class="w3-container" v-for=" game in this.games" :key="game.idGame">
             <b-btn class="w3-bar" @click="confirmacion(game)">
               <img v-bind:src="getImagenSport(game.sport.rutaImagen)" class="w3-bar-item w3-circle w3-hide-small" style="width:85px">
               
              <div class="w3-bar-item">
                <span class="w3-large">{{game.location.name}}</span><br>
                <span>{{game.date}} ({{custom(game.timeStart)}}-{{custom(game.timeEnd)}})</span>
              </div>
          </b-btn>
    </div>
  </div>
          </b-form-group>
        </form>
      </b-modal>
  </div>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel'
import { HTTP } from '../common/http-common'
import auth from '../common/auth'
import Swal from 'sweetalert2';
import Multiselect from 'vue-multiselect'
import { baseURL } from '../common/http-common'




export default {
  components: { Carousel, Slide,Multiselect},
  data() {
    return {
      games:[],
      equipos:['A','B'],
      playersChange:null,
      gameSelect:null,
      playersBefore:null,
      equiposJugadores:[]
     
    }
  },
  watch: {
    '$route': 'fetchData',
    
  },
   computed: {
    isLogged() {
      return auth.user.logged
    },
    loggedUser() {
      return auth.user.logged ? `${auth.user.login} (${auth.user.authority})` : 'not logged'
    },
    isAdmin() {
       return auth.isAdmin()
     }
  },
  created() { //se va a lanzar siempre en una clase de componentes

    this.fetchData()
  },

  methods: {
    fetchData() {  
      if(this.$route.params.id==true && !this.isAdmin){
         HTTP.get(`users/${this.WhatLogin()}/pendingResult`) 
                .then(response => { this.games= response.data
                      return response })
                .then(this.completarResultados)
                .catch(err => { this.error = err.message})
      }


    }, 
    getImagenSport(path){
      return baseURL + "sports/imagenes/" + path;
    },
     updateTeams(){
        var promises=[];
      for ( var i = 0; i < this.playersChange.length; i ++){
          if(this.playersChange[i].equipo!=this.equiposJugadores[i]){
            promises.push(HTTP.put(`players/${this.playersChange[i].idPlayer}/team/${this.playersChange[i].equipo}`)
              .catch(this._errorHandler))
          }
          
          
        }
        return Promise.all(promises);

     },
     handleOk1(evt) {
       evt.preventDefault()
       var bol=false;
         for ( var i = 0; i < this.playersChange.length; i ++){
          if (!this.playersChange[i].equipo) {
           this.$swal('Alerta!', "Todos los jugadores han de estar en un equipo", 'error')
           bol=true;
          }
        }
        if(bol==false){
          this.updateTeams().then(() => {
            this.ChangePage();
          });
        }
       
      },
     confirmacion(game){
       this.gameSelect=game;
        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
        this.$refs.modal.hide();

      })
       
         HTTP.get(`players/${game.idGame}`) 
          .then(response => { this.playersChange = response.data
                 return response })
          .then(this.copyTeams)
          .catch(err => { this.error = err.message})

    }, 
    ChangePage(){
       this.equiposJugadores=[]
        this.$router.replace({ name: this.gameSelect.sport.componenteEntrada, params: { id:this.gameSelect.idGame, intento:0}})

    },

    copyTeams(){

      for ( var i = 0; i < this.playersChange.length; i ++){
          this.equiposJugadores.push(this.playersChange[i].equipo)
        }
        this.$refs.modal3.show()


    },
     WhatLogin() {
      return auth.user.login
    },
    custom(hora){
      return hora.substring(0,5)
    },
    completarResultados(){
      if(this.games.length>0){
          this.$swal("Tienes partidos pendientes de completar sus resultados. Deseas hacerlo ahora?", {
           dangerMode: false,
           buttons: true
         }).then((result) => {
             if(result) {
                 this.$refs.modal.show();
                 
           } 
         })
      }
    }
  }
}
</script>


<style scoped lang="scss">

#carousel1 { 
  width:66%;
  margin-left: auto;
  margin-right: auto;

    
}

 .responsive{
  height:6%;
 }

fieldset {
    border: none;
    
}
.w3-bar{
   border-radius: 25px;
    background: white;
    color: #6c757d;
    margin-top:10px;
    border-color:white;
    box-shadow:0 2px 5px rgba(0,0,0,.3);
}

.formulario{
  color:#17a2b8;
}
.informationmessage2{
  height:200px;
  overflow: scroll;
}


</style>