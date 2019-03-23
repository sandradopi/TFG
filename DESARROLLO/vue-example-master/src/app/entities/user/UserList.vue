<template>
  <div>
      <ol id="lista2">
        <li class="usuarios" type="disc" v-for=" user in this.users" :key="user.idUser"> 
          {{user.name}} {{user.surname1}} {{user.surname2}}
          <div class="but">
            <b-btn class="button" @click="autoridad(user.idUser)"><span>{{user.authority}}</span></b-btn>
            <b-btn class="button1" @click="eliminar(user.idUser)"><span>Eliminar</span></b-btn>
          </div>
        </li>
    </ol> 
  </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'


export default {
  components: { },
  data() {
    return {
      users:null,


    }
  },
  watch: {
    '$route': 'fetchData',
    
  },
 
  created() { //se va a lanzar siempre en una clase de componentes
    this.fetchData()
  },
  methods: {
    fetchData() {
       HTTP.get('users')
            .then(response => { this.users = response.data })
            .catch(err => { this.error = err.message})
    },
     

    _successHandler(response) {
      this.fetchData()
    },

    back() {
      this.$router.go(-1)
    },
    
    eliminar(idUser){
       HTTP.delete(`users/${idUser}`)
          .then(this._successHandler)
          .catch(this._errorHandler)
    },

    _errorHandler(err) {
      this.error = err.response.data.message
       this.$swal('Alerta!', this.error, 'error')
    },
    
    autoridad(idUser){
      
       HTTP.put(`users/${idUser}/authority`)
              .then(this._successHandler)
              .catch(this._errorHandler)
    }
  }
}
</script>

<style scoped lang="scss">
#lista2 {
    counter-reset: li; 
    list-style: none; 
    *list-style: decimal; 
    font: 15px 'trebuchet MS', 'lucida sans';
    padding: 0;
    margin-top: 6%;
    text-shadow: 0 1px 0 rgba(255,255,255,.5);
    margin-left:5%;
    width:35%;


}

#lista2 ol {
    margin: 0 0 0 2em; 
}

#lista2 li{
    position: relative;
    display: block;
    padding: .9em .9em .6em 2em;
    margin: .9em 0;
    background: #ddd;
    color: #444;
    text-decoration: none;
    border-radius: .3em;
    transition: all .3s ease-out;  
    height: 70px; 
    font-size:1.2em;

}

#lista2 li:hover{
    background: #eee;
}

#lista2 li:hover:before{
    transform: rotate(360deg);  
}

#lista2 li:before{
    content: counter(li);
    color:white;
    counter-increment: li;
    position: absolute; 
    left: -1.3em;
    top: 50%;
    margin-top: -1.3em;
    background: #fb887c;
    height: 2.7em;
    width: 2.5em;
    line-height: 2em;
    border: .3em solid #fff;
    text-align: center;
    font-weight: bold;
    border-radius: 3em;
    transition: all .3s ease-out;
}



.but{
float:right;

}

.button {
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

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 20px;
}

.button:hover span:after, .button1:hover span:after{
  opacity: 1;
  right: 0;
}



</style>
