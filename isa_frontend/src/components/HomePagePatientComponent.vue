Vue.use(Vuelidate);
<template>
  <div>
    <ul>
        <li><router-link to="/examinationsPatient">Reserve an existing examination</router-link></li>
        <li><router-link :to="{name: 'EditPersonalData', params: {id: this.id}}">Edit personal data</router-link></li>
        <li><router-link to="/pretragaPatient">Clinic search</router-link></li>
        <li><router-link to="/patientExaminations">History of examinations</router-link></li>
        <li class="logout"><router-link @click.native="logout" to="/logout">Logout</router-link></li>
    </ul>
    <h3>Rate Clinic</h3>
    <div class="container">
        <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Adress</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="clinic in clinics" v-bind:key="clinic.id">
            <td>{{clinic.id}}</td>
            <td>{{clinic.name}}</td>
            <td>{{clinic.adress}}</td>
            <td>{{clinic.description}}</td>
            <td><form @submit.prevent="select(clinic.id)">
                <button class="btn btn-success" type="submit">Select Clinic</button>

              </form></td>
          </tr>
        </tbody>
      </table>

      <h3> Rate doctors </h3>
      <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="doctor in doctors" v-bind:key="doctor.id">
            <td>{{doctor.id}}</td>
            <td>{{doctor.name}}</td>
            <td>{{doctor.surname}}</td>
            <td>{{doctor.email}}</td>
            <td><form @submit.prevent="select2(doctor.id)">
                <button class="btn btn-success" type="submit">Select Doctor</button>

              </form></td>
          </tr>
        </tbody>
      </table>

    </div>

    
  </div>




</template>

<script>
import ClinicCenterService from '../service/ClinicCenterService';
import ExaminationService from '../service/ExaminationService';
import DoctorService from '../service/DoctorService';
//import Axios from 'axios';
export default {
  name: "ListClinics",
  data() {
    return {
        idLogovan: [],
        clinics: [],
        doctors: [],
        examinations: [],
        message: null,
        searchDate: "",
        searchType: "", 
        INSTRUCTOR: "all",
    };
  },
  methods: {
    refreshClinics() {
        ClinicCenterService.retrieveAllClinicsWherePatientWas() 
            .then(response => {
                this.clinics = response.data;
        });
        
    },
    refreshDoctors() {
        DoctorService.retrieveAllDoctorsWherePatientWas() 
            .then(response => {
                this.doctors = response.data;
        });
        
    },
    refreshExainations(){
      ExaminationService.retrieveAllExaminations()
            .then(response => {
                this.examinations = response.data;
            }
            );
    },
    validateAndSubmit(e) {
    e.preventDefault();


    this.refreshClinics();
      },
    /*appoint(id1) {
      /* eslint-disable no-console */
    /*  console.log(id1)
      console.log(this.$route.query.id)
       this.$router.push('/patientHomePage?id='+this.$route.query.id) 
    Axios.get('http://localhost:8082/api/examinations/reserve/' + id1 + '/' + this.$route.query.id)
    
    //this.refreshClinics();
      },  */
      select(id1){
        /* eslint-disable no-console */
       // Axios.get('http://localhost:8082/api/clinics/select/' + id1,{withCredentials: true})
        this.$router.push('/rateClinic/?idClinic='+id1,{withCredentials: true}) ;
      },
      select2(id1){
        /* eslint-disable no-console */
       // Axios.get('http://localhost:8082/api/clinics/select/' + id1,{withCredentials: true})
        this.$router.push('/rateDoctor/?idDoctor='+id1,{withCredentials: true}) ;
      },
      
      logout(){
        localStorage.removeItem('token');
        this.$router.push('/');
      }
  },
 
  created() {
 
    this.refreshClinics();
    this.refreshDoctors();
    

  },

  computed: {
    id() {
      return this.$route.params.id;
    }
  }
};
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