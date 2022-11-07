<template>
  <div>
    
    <div>
        <ul>
          <li><router-link :to="{name: 'editnurse', params: this.$route.params.id, withCredentials: true}">Change personal information</router-link></li>
          <li><router-link :to="{name: 'addabsencerequestnurse', params: this.$route.params.id}">Create absence request</router-link></li>
          <li class="logout"><router-link @click.native="logout" to="/logout">Logout</router-link></li>
        </ul>
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