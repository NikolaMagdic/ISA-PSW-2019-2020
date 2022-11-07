import axios from "axios"

<template>
  <div class="container">
    <div>
    <h3>Search clinics</h3>
    <div class="container">
      <form @submit.prevent="filter()">
        <fieldset class="form-group">
          <label>Date</label>
          <input type="date" class="form-control" v-model="date" >
        </fieldset>
        <fieldset class="form-group">
          <label>Type of examination</label>
          <input type="text" class="form-control" v-model="type">
        </fieldset>
        
        <button class="btn btn-success" type="submit">Filter</button>
      </form>
    </div>


  </div>

  </div>





  
</template>

<script>

//import Axios from 'axios';
export default {
  name: "ListPatients",
  data() {
    return {
        date: null,
        type: null
    };
  },
  methods: {
    filter(){
        
       
        this.$router.push('/patientHomePageFiltered/?date='+this.date+'&type='+this.type,  {withCredentials: true}) ;

        
        
    }
    


  },
  created() {
    
  }

};
</script>

<style>
</style>