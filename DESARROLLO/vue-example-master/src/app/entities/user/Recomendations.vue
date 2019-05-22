<template>
   <div class="information message2">
  <div class='spotlightWrapper'>  

    <!-- start unordered list -->  
    <ul> 
       <div>
          <li><a @click="touch('jugadores')"><img src="resources/friends1.jpg" /></a></li> 
          <h1 class="txt">Jugadores</h1>
       </div>
        <div>
         <li><a @click="touch('localizaciones')"><img src="resources/location.jpg" /></a></li>   
         <h1 class="txt1">Localizaciones</h1>
       </div>
        <div>
          <li><a @click="touch('deportes')"><img src="resources/sport2.jpg" /></a></li>   
          <h1 class="txt2">Deportes</h1>
       
       </div>
     
        
    </ul>  
    <!-- end unordered list -->  
</div>  
 </div>
</template>

<script>
export default {
data() {
    return {
    
     
    }
  },
  watch: {
    '$route': 'fetchData',

    
  },
  
  created(){
    this.fetchData();
  },
  methods: {
    
    fetchData(){
    
    },
    touch(tipo){
     
       this.$emit('Cerrar',tipo);
    }
    

   
  }
}
</script>
<style scoped lang="scss">


div.message2 {
  padding: 10px;
  padding-left: 20px;
  box-shadow:0 2px 5px rgba(0,0,0,.3);
  width:63%;
  height:70%;
  margin-right:21.5px; 
  float:right;

}
.foto{
    width:100%; 
    height:100%; 
}

.spotlightWrapper {

  display:inline-block;
  margin: 2px; 
  width:100%; 

  
  ul {  
    margin-top:50px;
    list-style-type: none; 
    display:inline-block;
    padding: 0;
    width:100%;
    margin-left:3.5%;
    


   
    li {  
    float:left;
    width:30%; 
    height:100%; 
    margin: 2px;  
    a img {  
    width:100%; 
    height:100%;  

   
  
}  
  img{
    display: block;
    position: relative;   
    width: 300px;
    filter:brightness(0.5);
    border-radius:10px;

  }


}  
    
}  
}
.txt{
    font-size:2em;
    position: absolute;
    top: 48%;
    left: 51.7%;
    transform: translate(-50%, -50%);
    bottom:40%;
    height:3%;

}

.txt1{
font-size:2em;
    position: absolute;
    top: 48%;
    left: 67%;
    transform: translate(-50%, -50%);
    bottom:40%;
    height:3%;

}

.txt2{
font-size:2em;
    position: absolute;
    top: 48%;
    left: 82%;
    transform: translate(-50%, -50%);
    bottom:40%;
    height:3%;

}

.clear { clear:both; } 

</style>
