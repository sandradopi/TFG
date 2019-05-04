<template>
    <div id="app">
      <full-calendar ref="calendar" :event-sources="eventSources" @event-selected="eventSelected"  @event-created="eventCreated" :config="config"></full-calendar>
    
    </div>
</template>

<script>
import { HTTP } from '../../common/http-common' 
import auth from '../../common/auth'
import Vue from 'vue'
import moment from 'moment';
import 'fullcalendar/dist/fullcalendar';
import timeGridPlugin from '@fullcalendar/timegrid';
import { Calendar } from '@fullcalendar/core';
import 'fullcalendar/dist/fullcalendar.css';




export default {
  name: 'app',
  props:{
    filterLocation:null,
    filterSport:null,
    
  },

  data() {
    return {
      games:null,
      game:{},
      events: [],
      config: {
        eventClick: (event) => {
           for ( var i = 0; i < this.games.length; i ++){
              if(this.games[i].idGame==event.id){
                this.game=this.games[i];
              }
           }
           this.details();

        },
      },
      selected: {},
    }
  },
  watch: {
    '$route': 'fetchData',
     filterLocation: 'fetchData',

    
  },
 
  created(){
    this.fetchData();
    

  },
  methods: {
    fetchData(){
      this.events=[];
      if(this.filterSport==null && this.filterLocation==null){
      HTTP.get('games')
                  .then(response => { this.games = response.data
                        return response.data})
                  .then(this.crearEventos)
                  .catch(err => { this.error = err.message})
      }else{
         HTTP.get(`games/sportsLocation/${this.filterSport}/${this.filterLocation}`) 
            .then(response => { this.games = response.data
                  return response })
             .then(this.crearEventos)
            .catch(err => { this.error = err.message})
      }
    },

    crearEventos(){
     

      for ( var i = 0; i < this.games.length; i ++){
        var evento ={
          id:this.games[i].idGame,
          title:this.games[i].location.name,
          start:this.games[i].date+"T"+this.games[i].timeStart,
          end:this.games[i].date+"T"+this.games[i].timeEnd,
          allDay:false,
          backgroundColor:"#fb887c",
          borderColor:"#fb887c",
          editable:false
       }

        this.events.push(evento);
      }
     
      
       this.refreshEvents()
    },
    details(){

      this.$router.replace({ name: 'GameDetail', params: { id: this.game.idGame}})
    },
  
    refreshEvents() {
      this.$refs.calendar.$emit('refetch-events');
    },
    removeEvent() {
      this.$refs.calendar.$emit('remove-event', this.selected);
      this.selected = {};
    },
    eventSelected(event) {
      this.selected = event;
      
    },
    eventCreated(...test) {
     
    },
  },
  computed: {
    eventSources() {
      const self = this;
      return [
        {
          events(start, end, timezone, callback) {
            setTimeout(() => {
              callback(self.events);
            }, 1000);
          },
        },
      ];
    },
  },
}
</script>

<style scoped lang="scss">

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 50%;
  height:10%

}
.hola{
  color:black;
}


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
</style>
