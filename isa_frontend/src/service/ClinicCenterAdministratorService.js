import axios from "axios";

class ClinicCenterAdministratorService {
    retrieveAdministratorInformation(id){
        return axios({method: "GET",
                    url: `http://localhost:8082/api/clinicalCenterAdministrators/${id}`,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}});
    }
}

export default new ClinicCenterAdministratorService();