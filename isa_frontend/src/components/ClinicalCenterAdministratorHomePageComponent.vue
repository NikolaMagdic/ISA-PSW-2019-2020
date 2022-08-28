<template>


  <div class="container">

    
    <button @click="()=>$router.push('/EnterClinicalCenterAdministrator')">Add administrator of clinical center  </button>
    <br>
    <br>
    <button @click="()=>$router.push(`/EnterClinicalAdministrator`)"> Add clinical administrator </button>
    <br>
    <br>
    <button @click="()=>$router.push('/clinics')"> Add clinic  </button>
    <br>
    <br>
    <button @click="()=>$router.push('/doctors')"> Add doctor  </button>
    <br>
    <br>
    <button @click="()=>$router.push('/medicalRooms')"> Add rooms  </button>
    <br>
    <br>
    <button @click="()=>$router.push('/examinationtypes')"> View and manage examination types </button>
    <br>
    <br>
    <button @click="()=>$router.push('/absencerequests')"> Respond to existing absence requests </button>
    <br>
    <br>
    <button @click="()=>$router.push(`/editadministrator/${this.$route.params.id}`)"> Change personal information </button>
    <br>
    <br>
    <button @click="logout()">Logout</button>
    <br>

    <br>
    

    <h3>All non accepted patients</h3>
    <div class="container">
      <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="patient in nonAcceptedPatients" v-bind:key="patient.id">
            <td>{{patient.id}}</td>
            <td>{{patient.name}}</td>
            <td>{{patient.surname}}</td>
            <td>
              <button class="btn" v-on:click="acceptPatient(patient.id)">
                Accept
              </button>
            </td>

            <td>
              <form @submit="deletePatient(patient.id, poruka)">
                  <button class="btn btn-success" type = "submit">Reject</button>
                  
                  <label> Reason: </label>
                  <input type="text" class="form-control" v-model="poruka" required>
               </form>
            </td>
            
          </tr>
        </tbody>
      </table>
    </div>


    </div>
</template>

<script>
//import PatientService from '../service/PatientService';
import ClinicCenterAdministratorService from '../service/ClinicCenterAdministratorService';
import Axios from 'axios';
export default {
  name: "ListPatients",
  data() {
    return {
        nonAcceptedPatients: [],
        administrator: {
          id: 0,
          name: '',
          surname:'',
          email:'',
          password:'',
          adress:'',
          city: '',
          state: '',
          phone: 0,
          validated: true,
          poruka: ''
        },
        message: null,
    };
  },
  methods: {
    refreshPatients() {
        Axios.get('http://localhost:8082/api/patients/nonAccepted').then(response => (this.nonAcceptedPatients = response.data))
        
    },
    refreshAdministrator(){
        ClinicCenterAdministratorService.retrieveAdministratorInformation(this.$route.params.id).then(response => (this.administrator = response.data))
        /* eslint-disable no-console */
        console.log("**************************")
        if(this.administrator.id == 1){
            console.log("Ima samo nece da ispise");
        } else{
          console.log("Nije ni preuzeo podatke");
        }
    },
    deletePatient(index, poruka){
    /* eslint-disable no-console */
    console.log('Brisanje pacijenta'+index + poruka);
    var message = poruka
    Axios.put('http://localhost:8082/api/patients/rejectPatient/' + index + "/" + message)
    this.refreshPatients();

    },
    acceptPatient(index){
      /* eslint-disable no-console */
      console.log('Prihvatanje pacijenta' + index);
      Axios.get('http://localhost:8082/api/patients/accept/' + index)
      this.refreshPatients();
    },
    logout(){
      localStorage.removeItem('token');
      this.$router.push('/');
    }
  },
  created() {
    this.refreshPatients();
    this.refreshAdministrator();
  }
  
}
</script>

<style>
@import url(https://unpkg.com/bootstrap@4.1.0/dist/css/bootstrap.min.css);


</style>