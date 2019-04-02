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
    
  },
 
  created(){
    this.fetchData();
    

  },
  methods: {
    fetchData(){
      HTTP.get('games')
                  .then(response => { this.games = response.data
                        return response.data})
                  .then(this.crearEventos)
                  .catch(err => { this.error = err.message})
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
      console.log(this.events)

    },
    details(){

      this.$router.replace({ name: 'GameDetail', params: { id: this.game}})
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
      console.log(this.selected)
    },
    eventCreated(...test) {
      console.log(test);
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

</style>
