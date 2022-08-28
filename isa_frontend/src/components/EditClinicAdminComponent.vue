<template>
  <div>
    <h3>Edit your personal information</h3>
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
          <label>Confirm password</label>
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
        <button class="btn btn-success" type="submit">Save</button>
      </form>
    </div>

  </div>
  
</template>




<script>
import Axios from 'axios';
import ClinicAdministratorService from '../service/ClinicAdministratorService';
export default {
  name: "EnterClinicalCenterAdministrator",
  data() {
    return {
        name: "", 
        surname: "", 
        email: "",
        adress: "", 
        password: undefined,
        password2: undefined,
        city: "",
        state: "", 
        phone: undefined,
        message: null,
    };
  },
  computed:{
      id(){
          return this.$route.params.id;
      }
  },
  methods: {
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
        }

        if(this.password==this.password2){
            Axios.put("http://localhost:8082/api/clinicalAdministrators", temp).then(res => {
                this.$router.push(`/ClinicAdministratorHomePage/${this.id}`);
                res.message;
            });
            
        }
    
    },
    refreshInformation(){
       ClinicAdministratorService.retrieveAdministratorInformation(this.id).then(response =>{
            this.name = response.data.name;
            this.surname = response.data.surname;
            this.email = response.data.email;
            this.adress = response.data.adress;
            this.password = response.data.password;
            this.password2 = response.data.password2;
            this.city = response.data.city;
            this.state = response.data.state;
            this.phone = response.data.phone;
        });
    }


  },
  created() {
    this.refreshInformation();
  }

};
</script>

<style>
</style>