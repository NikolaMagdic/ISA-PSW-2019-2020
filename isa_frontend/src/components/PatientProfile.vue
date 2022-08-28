<template>
    <div>
        <h3>Patient profile</h3>
        <div class="container">
        <form @submit="validateAndSubmit">
            <fieldset class="form-group">
                <label>Name</label>
                <input type="text" class="form-control" v-model="name" disabled >
            </fieldset>
            <fieldset class="form-group">
                <label>Surname</label>
                <input type="text" class="form-control" v-model="surname" disabled>
            </fieldset>
            <fieldset class="form-group">
                <label>Email</label>
                <input type="text" class="form-control" v-model="email" disabled>
            </fieldset>
            <fieldset class="form-group">
                <label>Address</label>
                <input type="text" class="form-control" v-model="address" disabled>
            </fieldset>
            <fieldset class="form-group">
                <label>City</label>
                <input type="text" class="form-control" v-model="city" disabled>
            </fieldset>
            <fieldset class="form-group">
                <label>State</label>
                <input type="text" class="form-control" v-model="state" disabled>
            </fieldset>
            <fieldset class="form-group">
                <label>Phone</label>
                <input type="text" class="form-control" v-model="phone" disabled>
            </fieldset>
            <fieldset class="form-group">
                <label>Lbo</label>
                <input type="text" class="form-control" v-model="lbo" disabled>
            </fieldset>
            <!--For now this buttons doesn't have functionality-->
            <button class="btn btn-success" type="submit">Start examination</button>
            <button class="btn btn-success" type="submit">View medical record</button>
        </form>
        </div>


    </div>  
</template>

<script>
import PatientService from '../service/PatientService';
export default {
    name: "PatientProfile",
    data(){
        return {
            name: "",
            surname: "",
            email: "",
            address: "",
            city: "",
            state: "",
            phone: undefined,
            lbo: undefined
        };
    },
    computed: {
        id() {
            return this.$route.params.id;
        }
    },
    methods: {
        validateAndSubmit(e){
            e.preventDefault();
        },
        getPatient(){
            PatientService.retrievePatientNormal(this.id).then(response =>{
                this.name = response.data.name;
                this.surname = response.data.surname;
                this.email = response.data.email;
                this.address = response.data.adress;
                this.city = response.data.city;
                this.state = response.data.state;
                this.phone = response.data.phone;
                this.lbo = response.data.lbo;
            });
        }
    },
    created(){
        this.getPatient();
    }
}
</script>

<style>

</style>