<template>
  <div class="container">
    <h3>All Diagnosis</h3>
    <div class="container">
      <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Ime</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="diagnosis1 in diagnosis" v-bind:key="diagnosis1.id">
            <td>{{diagnosis1.id}}</td>
            <td>{{diagnosis1.name}}</td>
          </tr>
        </tbody>
      </table>
    </div>


    <div>
    <h3>Add new diagnosis</h3>
    <div class="container">
      <form @submit="validateAndSubmit">
        <fieldset class="form-group" name="nameField">
          <label>Name</label>
          <input type="text" class="form-control" v-model="name" required>
        </fieldset>
        <button class="btn btn-success" type="submit">Save</button>
      </form>
    </div>
  </div>
    
  </div>
</template>

<script>
import DiagnosisService from '../service/DiagnosisServise';
import Axios from 'axios';
export default {
  name: "ListDiagnosis",
  data() {
    return {
        diagnosis: [],
        name: "",
        message: null,
        INSTRUCTOR: "all"
    };
  },
  methods: {
    refreshDiagnosis() {
        DiagnosisService.retrieveAllDiagnosis() 
            .then(response => {
                this.diagnosis = response.data;
        });
    },
    validateAndSubmit(e) {
    e.preventDefault();

    var temp={
      "name":this.name
    }
    this.name = ""
    Axios.post("http://localhost:8082/api/diagnosis", temp)
    this.refreshDiagnosis();
      
  }
  },
  created() {
    this.refreshDiagnosis();
  }

};
</script>

<style>
</style>