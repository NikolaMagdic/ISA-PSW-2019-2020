<template>
  <div>
      <h1>Doctor home page</h1>
      <ul>  
        <li><router-link :to="{name: 'patients', params: { id: this.id }}">List of patients</router-link></li>        
        <li><router-link to="/diagnosis">Diagnosis</router-link></li>
        <li><router-link :to="{name: 'addabsencerequest', params: {id: this.id}}">Create new absence request</router-link></li>
        <li><router-link :to="{name: 'editdoctor', params: { id: this.id }}">See personal profile</router-link></li>
        <li><router-link to="/addexamination">Schedule new examination</router-link></li>
        <!--Ovo ne bi radilo u Vue verziji 3, ali ovde radi jer je 2-->
        <li class="logout"><router-link @click.native="logout" to="/logout">Logout</router-link></li>
      </ul>
    <div class="container">
      <br/>
      <h3>Occupations for selected date</h3>

      <fieldset class="form-group">
              <label>Date</label>
              <input type="date" class="form-control" v-model="date" required @change="changedDate($event)">
      </fieldset>


    <!--<router-link to = "/doctors"> Neki link </router-link>-->
    <div class="container">
      <table class="table">
        <thead>
          <tr>
            <th>Starts at</th>
            <th>Ends at</th>
            <th>Medical room</th>
            <th>Patient</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="occupation in doctorsOccupationsForDate" v-bind:key="occupation.id">
            <td>{{convertToHoursAndMinutes(occupation.pocetniTrenutak)}}</td>
            <td>{{convertToHoursAndMinutes(occupation.krajnjiTrenutak)}}</td>
            <td>{{occupation.medicalRoom.roomCodeName}}</td>
            <td>{{occupation.examination.patient.name}} {{occupation.examination.patient.surname}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    </div>
  </div>
</template>

<script>
import Axios from 'axios';
export default {
  data() {
    return {
        doctorsOccupationsForDate: [],
        date: undefined
    };
  },
  methods: {
    changedDate(){
        Axios.get(`http://localhost:8082/api/doctors/freeDoctorsForOccupation/${this.$route.params.id}/${this.date}`).then(response => (
            this.doctorsOccupationsForDate = response.data
        ));
    },
    convertToHoursAndMinutes(timeInMinutes){
      var satCeo = 0;
      var sat = 0;
      var minuti = 0;
      sat = timeInMinutes / 60;
      satCeo = Math.round(timeInMinutes / 60);

      if(satCeo > sat){
        satCeo = satCeo - 1;
      }

      minuti = Math.round((sat - satCeo)*60);
      return satCeo + "h " + minuti + "min";
    },
    logout() {
      localStorage.removeItem('token');
      this.$router.push('/');
    }
  },
  computed: {
      id() {
        return this.$route.params.id;
      }
    }
}
</script>

<style>
/*Navigation bar*/
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
  background-color: rgb(10, 78, 14);
  text-decoration: none;
  color: white;
}

li.logout {
  float: right;
}

</style>