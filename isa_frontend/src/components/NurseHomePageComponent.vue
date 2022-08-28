<template>
  <div class="container">
    
    <div class="container">
        <fieldset class="form-group">
          <label>Name: </label>
          <label> {{ this.sestra.name }} </label>
        </fieldset>
        <fieldset class="form-group">
            <label>Surname: </label>
          <label>{{ this.sestra.surname }}</label>
        </fieldset>
        <fieldset class="form-group">
          <label>Email: </label>
          <label>{{ this.sestra.email }}</label>
        </fieldset>
         <fieldset class="form-group">
          <label>Password: </label>
          <label>{{ this.sestra.password }}</label>
        </fieldset>
         <fieldset class="form-group">
          <label>Adress: </label>
          <label>{{ this.sestra.adress }}</label>
        </fieldset>
         <fieldset class="form-group">
          <label>City: </label>
          <label>{{ this.sestra.city }}</label>
        </fieldset>
         <fieldset class="form-group">
          <label>State: </label>
          <label>{{ this.sestra.state }}</label>
        </fieldset>
         <fieldset class="form-group">
          <label>Phone: </label>
          <label>{{ this.sestra.phone }}</label>
        </fieldset>
        <button v-on:click="editNurseClicked()">Change personal information</button>
        <button v-on:click="createAbsenceRequestClicked()">Create absence request</button>
        <button v-on:click="logout()">Logout</button>
    </div>

    <h3>All non validated prescriptions</h3>
    <div class="container">
      <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="presc in nonValidated" v-bind:key="presc.id">
            <td>{{presc.id}}</td>
            <td>{{presc.name}}</td>
            <td>
              <button class="btn" v-on:click="validatePrescription(presc.id)">
                Validate
              </button>
            </td>

            <td>
              <button class="btn" v-on:click="deletePrescription(presc.id)">
                Delete
              </button>
            </td>

          </tr>
        </tbody>
      </table>
    </div> 



  </div>
</template>


<script>
import Axios from 'axios';
import NurseService from '../service/NurseService';
export default {
  name: "NurseHomePage",
  data() {
    return {
        sestra: null,
        nonValidated: []
    };
  },
  methods: {
    refreshNurse(){
        NurseService.retrieveNurse(this.$route.params.id)
                  .then(response => (this.sestra = response.data));
    },
    refreshPrescriptions(){
      Axios.get('http://localhost:8082/api/prescriptions/nonAccepted').then(response => (this.nonValidated = response.data))
    },
    deletePrescription(index){
      Axios.delete('http://localhost:8082/api/prescriptions/' + index)
      this.refreshPrescriptions
      this.$forceUpdate();
    },
    validatePrescription(index){
      Axios.get('http://localhost:8082/api/prescriptions/validate/' + index +'/'+ this.$route.params.id)
      this.refreshPrescriptions
      this.$forceUpdate();
    },
    editNurseClicked(){
      this.$router.push(`/editnurse/${this.$route.params.id}`, {withCredentials: true});
    },
    createAbsenceRequestClicked(){
      this.$router.push(`/addabsencerequestnurse/${this.$route.params.id}`);
    },
    logout() {
      localStorage.removeItem('token');
      this.$router.push('/');
    }

  },
  created() {
    this.refreshPrescriptions();
    this.refreshNurse();
    
  }, 
  mounted(){
    this.refreshNurse();
  }
  

};
</script>

<style>
</style>