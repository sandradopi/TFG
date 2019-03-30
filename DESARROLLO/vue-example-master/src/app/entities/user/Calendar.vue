<template>
    <div id="app">
      <full-calendar ref="calendar" :event-sources="eventSources" @event-selected="eventSelected" @event-created="eventCreated" :config="config"></full-calendar>
    
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
      events: [
        {
          id: 1,
          title: 'event1',
          start: moment().hours(12).minutes(0),
        },
        {
          id: 2,
          title: 'event2',
          start: moment().add(-1, 'days'),
          end: moment().add(1, 'days'),
          allDay: true,
        },
        {
          id: 3,
          title: 'event3',
          start: moment().add(2, 'days'),
          end: moment().add(2, 'days').add(6, 'hours'),
          allDay: false,
        },
      ],
      config: {
        eventClick: (event) => {
          this.selected = event;
        },
      },
      selected: {},
    };
  },
  created(){

  },
  methods: {
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
              callback(self.events.filter(() => Math.random() > 0.5));
            }, 1000);
          },
        },
      ];
    },
  },
};
</script>

<style scoped lang="scss">

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 50%;
}

</style>
