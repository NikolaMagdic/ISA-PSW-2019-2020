import axios from "axios"

<template>
  <div class="container">
    
    <h3>Register as new Patient</h3>
    <div class="container">
      <form @submit="validateAndSubmit">
        <fieldset class="form-group">
          <label>Name</label>
          <input type="text" class="form-control" v-model="name" >
        </fieldset>
        <fieldset class="form-group">
          <label>Surname</label>
          <input type="text" class="form-control" v-model="surname">
        </fieldset>
        <fieldset class="form-group">
          <label>Email</label>
          <input type="text" class="form-control" v-model="email">
        </fieldset>
         <fieldset class="form-group">
          <label>Password</label>
          <input type="text" class="form-control" v-model="password">
        </fieldset>
        <fieldset class="form-group">
          <label>Password2</label>
          <input type="text" class="form-control" v-model="password2">
        </fieldset>
         <fieldset class="form-group">
          <label>Adress</label>
          <input type="text" class="form-control" v-model="adress">
        </fieldset>
         <fieldset class="form-group">
          <label>City</label>
          <input type="text" class="form-control" v-model="city">
        </fieldset>
         <fieldset class="form-group">
          <label>State</label>
          <input type="text" class="form-control" v-model="state">
        </fieldset>
         <fieldset class="form-group">
          <label>Phone</label>
          <input type="text" class="form-control" v-model="phone">
        </fieldset>
         <fieldset class="form-group">
          <label>Lbo</label>
          <input type="text" class="form-control" v-model="lbo">
        </fieldset>
        <button class="btn btn-success" type="submit">Save</button>
      </form>
    </div>


  </div>


  
</template>

<script>
import PatientService from '../service/PatientService';
import Axios from 'axios';
export default {
  name: "ListPatients",
  data() {
    return {
        patients: [],
        message: null,
        name: "",
        surname: "",
        lbo: undefined,
        email: "",
        password: "",
        password2: "",
        adress: "",
        city: "",
        state: "",
        phone: "",
        searchName: "",
        searchSurname: "",
        searchLBO: "",
        currentSortDir: "desc"
        
    };
  },
  methods: {
    refreshPatients() {
        PatientService.retrieveAllPatients() 
            .then(response => {
                this.patients = response.data;
        });
    },
    validateAndSubmit(e) {
    e.preventDefault();
    var temp={
      "name":this.name,
      "surname":this.surname,
      "email":this.email,
      "adress":this.adress,
      "password": this.password,
      "city":this.city,
      "state":this.state,
      "phone":this.phone,
      "lbo": this.lbo,
      "height": 0,
      "weight": 0,
      "bloodType": "x"
    }
    //var patientPom
    var ispravno = true
    var i
    for(i=0; i<this.patients.length; i++){
      /* eslint-disable no-console */
        console.log("**************************")
        if(this.name==this.patients[i].name){
           ispravno = false
           console.log("Vec postoji ovakav cika")
        }
       
      
    }


    if(this.password==this.password2 && ispravno){
    Axios.post("http://localhost:8082/api/patients", temp);
            this.$router.push('/') 

    }this.refreshPatients();
  },
   makeReportClicked(id){
      this.$router.push(`/makeReport/${this.$route.params.id}/${id}`);  
    },
    patientProfileClicked(id){
      this.$router.push(`/patientprofile/${id}`);  
    },
    // Sorting functions
    sortById(){
      if(this.currentSortDir === "desc"){
        this.patients.sort((a, b) => a.id > b.id ? 1 : -1);
        this.currentSortDir = "asc";
      }else{
        this.patients.sort((a, b) => a.id < b.id ? 1 : -1);
        this.currentSortDir = "desc";
      }
    },
    sortByName(){
      if(this.currentSortDir === "desc"){
        this.patients.sort((a, b) => a.name.toLowerCase() > b.name.toLowerCase() ? 1 : -1);
        this.currentSortDir = "asc";
      }else{
        this.patients.sort((a, b) => a.name.toLowerCase() < b.name.toLowerCase() ? 1 : -1);
        this.currentSortDir = "desc";
      }
    },
    sortBySurname(){
      if(this.currentSortDir === "desc"){
        this.patients.sort((a, b) => a.surname.toLowerCase() > b.surname.toLowerCase() ? 1 : -1);
        this.currentSortDir = "asc";
      }else{
        this.patients.sort((a, b) => a.surname.toLowerCase() < b.surname.toLowerCase() ? 1 : -1);
        this.currentSortDir = "desc";
      }
    },
    sortByLBO(){
      if(this.currentSortDir === "desc"){
        this.patients.sort((a, b) => a.lbo > b.lbo ? 1 : -1);
        this.currentSortDir = "asc";
      }else{
        this.patients.sort((a, b) => a.lbo < b.lbo ? 1 : -1);
        this.currentSortDir = "desc";
      }
    }

  },
  computed: {
    filteredPatients: function() {
      return this.patients.filter((patient)=>{
        if(patient.name.toLowerCase().match(this.searchName.toLowerCase()) &&
          patient.surname.toLowerCase().match(this.searchSurname.toLowerCase()) &&
          patient.lbo.toString().match(this.searchLBO)){
          return true;
        }else{
          return false;
        }

      });
    },
    
  },
  created() {
    this.refreshPatients();
  }

};
</script>

<style>
</style>