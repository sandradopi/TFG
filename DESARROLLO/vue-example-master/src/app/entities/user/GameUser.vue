<template>
  <div class="content"> 
      <h1 class="title"> Perfil Público</h1>  
    	<div class="profile1">
    		<div class="botones">
      	  	<b-btn class="b1" @click="TipoPatidos('proximo')">Próximos Partidos</b-btn>
      			<b-btn class="b1" @click="TipoPatidos('organizados')">Partidos Organizados</b-btn>
      			<b-btn class="b1" v-if="this.user.login==WhatLogin()"@click="TipoPatidos('jugados')">Partidos Jugados</b-btn>
      			<b-btn class="b1" v-if="this.user.login==WhatLogin()"@click="TipoPatidos('recomendados')">Partidos Recomendados</b-btn>
      			<b-btn class="b1" @click="TipoPatidos('comentarios')">Comentarios</b-btn>
    		</div>
	   </div>
    	<div class="layout">
			  <div class="profile">
			    <div class="profile__picture"><img src="http://i.pravatar.cc/250?img=41"/></div>
			    <div class="profile__header">
			      <div class="profile__account">
			        <h4 class="profile__username">{{this.user.name}} {{this.user.surname1}} {{this.user.surname2}} </h4>
			        <h4 class="profile__username1"> ({{this.user.city}})</h4>
			      </div>


			      <div class="profile__edit"><b-btn class="profile__button" :to="{ name: 'UserUpdate', params: { id: this.user, boleano:this.bol}}" @click="Editado()">Editar Perfil</b-btn ></div>
			    </div>
          <star-rating class="star" v-model="this.user.experience" v-bind:star-size="25" :read-only="true"></star-rating></p>
			    <div class="profile__stats">

			      <div class="profile__stat1">
			        <div class="profile__icon profile__icon--gold"><i class="fas fa-wallet"></i></div>
			        <div class="profile__value">47
			          <div class="profile__key">Seguidores</div>
			        </div>
			      </div>
			      <div class="profile__stat1">
			        <div class="profile__icon profile__icon--blue"><i class="fas fa-signal"></i></div>
			        <div class="profile__value">357
			          <div class="profile__key">Seguidos</div>
			        </div>
			      </div>
			      <div class="profile__stat">
			        <div class="profile__icon profile__icon--pink"><i class="fas fa-heart"></i></div>
			        <div class="profile__value">{{this.user.experience}}/5
			          <div class="profile__key">Experiencia</div>
			        </div>
			      </div>
			    </div>
			  </div>
		</div>
    <GameList  v-if="this.recomendado==true && this.tipo=='recomendados'||this.recomendado==false && this.tipo!='recomendados'" v-bind:tipo="this.tipo" v-bind:typeR="this.typeR" v-bind:login="this.user.login">
    </GameList>
     <Recomendations @Cerrar="reloadRecomendations" v-if="this.tipo=='recomendados' && this.recomendado==false">
    </Recomendations>
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import GameList from '../../entities/user/GameList'
import Recomendations from '../../entities/user/Recomendations'
import StarRating from 'vue-star-rating'


export default {
  components: {GameList, StarRating,Recomendations},
  data() {
    return {
      game:{},
      login:null,
      user:{},
      tipo:'proximo',
      bol:false,
      recomendado:false,
      typeR:''
    }
  },
  watch: {
    '$route': 'fetchData',
    'tipo': 'fetchData',

    
  },
 
  created() { //se va a lanzar siempre en una clase de componente
    this.fetchData()
  },
  
  methods: {
    fetchData() {
      this.recomendado=false;
     HTTP.get(`users/${this.$route.params.id}`) 
          .then(response => { this.user = response.data
                 return response })
          .catch(err => { this.error = err.message})

    },
    reloadRecomendations(type){
     
      if(type=='jugadores'){
        this.typeR='jugadores';

      }else if(type=='localizaciones'){
         this.typeR='localizaciones';
      }else if(type=='deportes'){
         this.typeR='deportes';
      }
       this.recomendado=true;

    },

    TipoPatidos(tipo){
    	if(tipo=='proximo'){
    		this.tipo='proximo';
    	}else if (tipo=='organizados'){
    		this.tipo='organizados';
    	}else if (tipo=='jugados'){
    		this.tipo='jugados';
    	}else if (tipo=='recomendados'){
    		this.tipo='recomendados';
        this.recomendado=false;
    	}else if (tipo=='comentarios'){
    		this.tipo='comentarios';
      }
    },
   
    _successHandler(response) {
      this.fetchData()
    },
    Editado(){
      this.bol=true;
    },

    back() {
      this.$router.go(-1)
    },
     WhatLogin() {
      return auth.user.login
    },
    
    _errorHandler(err) {
      this.error = err.response.data.message
    }
  }
}
</script>

<style scoped lang="scss">

.botones{
	width:100%;
  background:#17a2b8;
  border-radius:20px;


}

.b1,.b2,.b3{
  background:#17a2b8;
  margin-left:10px;
  border:none;
  color:white;

}


@keyframes slideUp {
  from {
    -webkit-transform: translateY(3px);
            transform: translateY(3px);
    opacity: 0;
  }
  to {
    -webkit-transform: translateY(0px);
            transform: translateY(0px);
    opacity: 1;
  }
}
.profile{
    -webkit-animation: popUp ease-in-out 350ms;
    animation: popUp ease-in-out 350ms;
    background: #ffffff;
    border-radius: 25px;
    box-shadow: 0 0 40px 0px rgba(0, 0, 0, 0.17);
    padding: 20px 10px 10px 10px;
    position: relative;
    width: 410px;
    color: black;
    float:left;
    margin-left:20px;

   

}

.profile1{
	 background:#17a2b8;
    position: relative;
    width: 63%;
    height:38px;
    color: black;
    float:right;
    margin-right:22px;
    border-radius:10px 10px 0px 0px;




}
.profile__account {
  align-self: center;
  display: flex;
  flex: 1;
  justify-content: flex-end;

}




.profile__button {
  border-radius: 40px;
  color: white;
  display: block;
  font-family: "Montserrat", sans-serif;
  font-size: 13px;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  transition: ease-in-out 250ms background, ease-in-out 250ms color;
   background: #17a2b8;
   margin-bottom:20px;
   border:none;
}



.profile__button:hover {
  background: #fb887c;
  color: white;
}
.profile__edit {
  flex: none;
  margin-left: 30px;
  width: 100px;
}
.profile__header {
  display: flex;
  height:35px;

}


.profile__key {
  font-family: "Montserrat", sans-serif;
  font-size: 13px;
  font-weight: 400;
  text-align: center;
}
.profile__picture {
  background: #ffffff;
  border-radius: 100px;
  border: 6px solid #ffffff;
  height: 100px;
  position: absolute;
  margin-left:25px;
  top: -70px;
  width: 100px;
}

.profile__picture img {
  border-radius: 100px;
  height: 100%;
  width: 100%;
}
.profile__stat {
  -webkit-animation: slideUp ease-in-out 350ms forwards;
          animation: slideUp ease-in-out 350ms forwards;
  display: flex;
  flex: 1;
  justify-content: center;
  opacity: 0;
  -webkit-transform: translateY(5px);
          transform: translateY(5px);
}

.profile__stat1 {
	-webkit-animation: slideUp ease-in-out 350ms forwards;
          animation: slideUp ease-in-out 350ms forwards;
  display: flex;
  flex: 1;
  justify-content: center;
  opacity: 0;
  -webkit-transform: translateY(5px);
          transform: translateY(5px);

  border-right: 1px solid #e9e9e9;

}


.profile__stats {
  display: flex;
}
.profile__username {
  font-family: "Montserrat", sans-serif;
  font-weight: 600;
  font-size: 14px;
  
}

.profile__username1 {
  margin-left:8px;
  font-family: "Montserrat", sans-serif;
  font-weight: 300;
  font-size: 13px;
}
.profile__value {
  font-family: "Montserrat", sans-serif;
  font-size: 18px;
  font-weight: 700;
  text-align: center;
}


.content{
    width: 93%;
    background: #f3f3f3;
    padding: 1em;
    border-radius: 3px;
    margin-top:50px;
    font-family: 'Lato', sans-serif;
    margin:0;
    margin-left:30px;
    margin-top:50px;
    margin-bottom:59px;
    height:66%;
    border-radius: 6px;
}

.title{
    font-family: 'Lato', sans-serif;
    margin-left:40%;
    font-size: 30px;
    font-weight: 200;
    color: #17a2b8;
    margin-top:20px;
    margin-bottom:30px;
    color:#fb887c;
  }



div.message.information{background: #fb887c;}

div.message2 {
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  background: #fff;
  width:63%;
  height:70%;
  margin-right:21.5px;
  float:right;
  border-radius:0px 0px 10px 10px;


}


div.message2.information{background: #fb887c;}

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

.star{
  margin-left:40px;

}

</style>
