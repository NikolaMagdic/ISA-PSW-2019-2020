<template>


  <div>

    <nav>
      <div>
      <ul>
        <li><router-link to="/EnterClinicalCenterAdministrator">Add administrator of clinical center </router-link></li>
        <li><router-link to="/EnterClinicalAdministrator"> Add clinical administrator </router-link></li>
        <li><router-link to="/clinics"> Clinics </router-link></li>
        <li><router-link to="/doctors"> Doctors  </router-link></li>
        <li><router-link to="/medicalRooms"> Rooms </router-link></li>
        <li><router-link to="/examinationtypes"> View and manage examination types </router-link></li>
        <li><router-link to="/absencerequests"> Respond to existing absence requests </router-link></li>
        <li><router-link :to="{name: 'editadministrator', params: { id: this.$route.params.id}}"> Change personal information </router-link></li>
        <li class="logout"><router-link @click.native="logout" to="/logout">Logout</router-link></li>
      </ul>
    </div>
    </nav>

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
          <tr v-for="(patient, index) in nonAcceptedPatients" v-bind:key="patient.id">
            <td>{{patient.id}}</td>
            <td>{{patient.name}}</td>
            <td>{{patient.surname}}</td>
            <td>
              <button class="btn" v-on:click="acceptPatient(patient.id)">
                Accept
              </button>
            </td>

            <td>
              <form @submit="deletePatient(patient.id, this.messages[index])">
                  <button class="btn btn-success" type = "submit">Reject</button>
                  
                  <label> Reason: </label>
                  <input type="text" class="form-control" v-model="messages[index]" required>
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
        },
        messages: [],
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
        console.log(this.administrator);
        if(this.administrator.id == 1){
            console.log("Ima samo nece da ispise");
        } else{
          console.log("Nije ni preuzeo podatke");
        }
    },
    deletePatient(index, message){
      /* eslint-disable no-console */
      console.log('Brisanje pacijenta' + index + message);
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

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: rgb(29, 168, 64);
}

li {
  float: left;
}

li a {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover {
  background-color: rgb(0, 128, 43);
  text-decoration: none;
  color: white;
}

li.logout {
  float: right;
}

</style>