import axios from "axios"

<template>
  <div class="container">
    <div>
    <h3>Edit Personal data</h3>
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
          <input type="text" class="form-control" v-model="email" disabled>
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
          <input type="text" class="form-control" v-model="lbo" disabled>
        </fieldset>
        <button class="btn btn-success" type="submit">Save</button>
      </form>
    </div>


  </div>

  </div>





  
</template>

<script>
import PatientService from '../service/PatientService';
import Axios from 'axios';
export default {
  name: "EditPatients",
  data() {
    return {
        id : undefined,
        name: undefined,
        surname: undefined,
        password: undefined,
        password2: undefined,
        email: undefined,
        adress: undefined,
        city: undefined, 
        state: undefined,
        phone: undefined,
        ispravno : undefined,
        lbo : undefined,
        message: null,
        INSTRUCTOR: "all"
    };
  },
  methods: {
    refreshPatients() {
        PatientService.retrieveAllPatients() 
            .then(response => {
                this.patients = response.data;
              
       
        
        
      /* eslint-disable no-console */
        console.log("**************************")
      //  console.log(index)
      //  console.log(this.patients[i].id)
      //  console.log(this.patients[i].email)
      //  console.log(this.patients.length)
      
        
       
      
   
        });
        

    },
    validateAndSubmit(e) {
    e.preventDefault();
   
    
     var temp={
      "id": this.id,
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
    

    if(this.password == this.password2 ){
      Axios.post(`http://localhost:8082/api/patients/updatePatient`, temp, {withCredentials: true});
            this.$router.push('/patientHomePage') 

    }
  },
  getPatient(){
    PatientService.retrievePatient( {withCredentials: true}).then(response => {
      this.id = response.data.id;
      this.name = response.data.name;
      this.surname = response.data.surname;
      this.password = response.data.password;
      this.email = response.data.email;
      this.adress = response.data.adress;
      this.city = response.data.city;
      this.state = response.data.state;
      this.phone = response.data.phone;
      this.lbo = response.data.lbo;
     });

    
  }


  },
  mounted() {
    this.getPatient();
  }

};
</script>

<style>
</style>