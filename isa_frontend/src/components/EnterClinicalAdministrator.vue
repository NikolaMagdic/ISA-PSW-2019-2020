<template>
  <div class="container">
    
    <div>
    
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
            <label>Clinic</label>
            <select class="form-control" @change="changedType($event)" required>
            <option value="" selected disabled>Choose clinic</option>
            <option v-for="clinic in this.clinics" :value="clinic.id" :key="clinic.id">{{clinic.name}}</option>
            </select>
        </fieldset>
        <button class="btn btn-success" type="submit">Save</button>
      </form>
    </div>


  </div>

  </div>
  
</template>




<script>
import Axios from 'axios';
export default {
  name: "EnterClinicalAdministrator",
  data() {
    return {
        clinics: [],
        message: null,
        selectedClinic: undefined,
        selectedClinicName: ""
    };
  },
  methods: {
    refreshClinics(){
        Axios.get('http://localhost:8082/api/clinics').then(response => (this.clinics = response.data))
    }
    ,
    changedType(event){
      this.selectedClinicName = event.target.options[event.target.options.selectedIndex].text;
      
      //ExaminationTypeService.retrieveExaminationType(event.target.value).then(response =>{
      //  this.selectedType = response.data;
      //});
      Axios.get(`http://localhost:8082/api/clinics/${event.target.value}`).then(response => (this.selectedClinic = response.data))

    }
    ,
    validateAndSubmit(e) {
    e.preventDefault();
    var temp={
      "name": this.name,
      "surname": this.surname,
      "email": this.email,
      "adress": this.adress,
      "password": this.password,
      "city": this.city,
      "state": this.state,
      "phone": this.phone,
      "clinic":this.selectedClinic // dobro pronalazi kliniku na osnovu selektovane 

    }
    //var patientPom
    if(this.password==this.password2){

        Axios.post(`http://localhost:8082/api/clinicalAdministrators`, temp).then({
          
        }); // ne stavlja kliniku unutar administratora

        /* eslint-disable no-console */
        console.log(this.selectedClinic.name)

    }

    
  }


  },
  created() {
    this.refreshClinics();
  }

};
</script>

<style>
</style>