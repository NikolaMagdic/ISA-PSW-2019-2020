import axios from "axios";

class ClinicAdministratorService {
    retrieveAdministratorInformation(id){
        return axios({method: "GET",
                    url: `http://localhost:8082/api/clinicalAdministrators/${id}`,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}});
    }
}``

export default new ClinicAdministratorService();