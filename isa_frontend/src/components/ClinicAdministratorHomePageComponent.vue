<template>


  <div>

    <ul>
        <li><router-link :to="{name: 'editclinic', params: {id: this.$route.params.id}}">My Clinic</router-link></li>
        <li><router-link to="/doctors">Doctors</router-link></li>
        <li><router-link to="/medicalRooms">Medical Rooms</router-link></li> 
        <li><router-link to="/examinationtypes">Examination Types</router-link></li>
        <li><router-link :to="{name: 'businessreport', params: { id: this.clinic_id}}">Business Report</router-link></li>  
        <li><router-link to="/absencerequests">Absence Requests</router-link></li>       
        <li><router-link :to="{name: 'editclinicadmin', params: { id: this.$route.params.id }}">Edit personal profile</router-link></li>
        <li class="logout"><router-link @click.native="logout" to="/logout">Logout</router-link></li>                
    </ul>
  </div>
</template>

<script>
//import Axios from 'axios';
import ClinicAdministratorService from '../service/ClinicAdministratorService';
import ClinicCenterService from '../service/ClinicCenterService';
export default {
  name: "ClinicAdministratorHomePage",
  data() {
    return {
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
          clinic: undefined,
        },
        message: null,
        clinic_id: undefined
    };
  },
  methods: {
    refreshAdministrator(){
        ClinicAdministratorService.retrieveAdministratorInformation(this.$route.params.id).then(response => {this.administrator = response.data, 
                                                                                                            this.clinic_id = response.data.clinic.id
                                                                                                            this.retrieveClinic(this.clinic_id)});
        /* eslint-disable no-console */
        console.log("**************************")
        if(this.administrator.id == 1){
            console.log("Ima samo nece da ispise");
        } else{
          console.log("Nije ni preuzeo podatke");
        }

    },
    retrieveClinic() {
      ClinicCenterService.retrieveClinic(this.clinic_id);
    },
    logout() {
      localStorage.removeItem('token');
      this.$router.push('/');
    }
  },

  created() {
    this.refreshAdministrator();
  }
  
}
</script>

<style>
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