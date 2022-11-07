<template>
    <div>
    <h3>LOGIN :</h3>
    <div class="container">
      <form @submit="login">
        <fieldset class="form-group" name="nameField">
          <label>Email </label>
          <input type="text" class="form-control" v-model="email" required>
        </fieldset>
        <fieldset class="form-group">
          <label>Password </label>
          <input type="password" class="form-control" v-model="password" required>
        </fieldset>
        <button class="btn btn-success" type="submit">Login</button>
      </form>
      <br>
      <form @submit="val">
          <button class="btn btn-success" type="submit">Register</button>
      </form>
    </div>
  </div>



</template>

<script>
//import PatientService from '../service/PatientService';
//import DoctorService from '../service/DoctorService';
import Axios from 'axios';
//Vue.use(Vuelidate);

export default {
  name: "Login",
  data() {
    return {
        clinicAdministrators: [],
        email: "",
        password: "",
        role: "",
        loginRequest: {}
    };
  },
  methods: {
    login(event) 
    { 
      this.loginRequest = {
          "username": this.email,
          "password": this.password
      }
      event.preventDefault();
      Axios.post('http://localhost:8082/auth/login', this.loginRequest)
            .then(response => {
              /* eslint-disable no-console */
              console.log(response.data.accessToken);
              localStorage.setItem('token', response.data.accessToken);
              Axios({url: 'http://localhost:8082/auth/getUserRole', 
                        headers: {'Authorization': 'Bearer ' + response.data.accessToken}})
                .then(response => {
                  this.role = response.data;
                  if(this.role == 'doctor') {
                    Axios({url: 'http://localhost:8082/api/doctors/get-doctor',
                          headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}})
                      .then(response => {
                          this.$router.push('/'+ this.role + 'HomePage/' + response.data.id);
                      });
                  } else if(this.role == 'nurse') {
                    Axios({url: 'http://localhost:8082/api/nurses/get-nurse',
                          headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}})
                      .then(response => {
                          this.$router.push('/NurseHomePage/' + response.data.id);
                      });
                  } else if(this.role == 'clinical_admin') {
                    Axios({url: 'http://localhost:8082/api/clinicalAdministrators/getClinicalAdministrator',
                          headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}})
                      .then(response => {
                          this.$router.push('/ClinicAdministratorHomePage/' + response.data.id);
                      });
                  } else if(this.role == 'clinical_center_admin') {
                      Axios({url: 'http://localhost:8082/api/clinicalCenterAdministrators/getClinicalCenterAdministrator',
                          headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}})
                      .then(response => {
                          this.$router.push('/ClinicalCenterAdministratorHomePage/' + response.data.id);
                      });
                  } else {
                    Axios({url: 'http://localhost:8082/api/patients/get-patient',
                          headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}})
                      .then(response => {
                          this.id = response.id; // samo da mi ne ispisuje gresku
                          this.$router.push('/PatientHomePage');
                      });
                  }
                   
                });     
      });
    },
  val(e) {
    e.preventDefault();
        this.$router.push('/registerPatient') 

  }
  },
  created() {

  }
};
</script>

<style>
@import url(https://unpkg.com/bootstrap@4.1.0/dist/css/bootstrap.min.css);

</style>