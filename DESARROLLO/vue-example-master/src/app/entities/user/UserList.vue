<template>
<div>
  <li class="usuarios" type="disc" v-for=" user in this.users" :key="user.idUser"> {{user.name}} {{user.surname1}} {{user.surname2}}</li>
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
        .then(response => {
       this.users = response.data
       
     })
     .catch(err => {
       this.error = err.message
     })

  
    },
     

    _successHandler(response) {
      this.fetchData()
    },
    back() {
      this.$router.go(-1)
    },
    _errorHandler(err) {
      this.error = err.response.data.message
    }
  }
}
</script>

<style scoped lang="scss">
.usuarios{
  color:#17a2b8;
}

</style>
