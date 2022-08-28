import axios from "axios";



class PatientService {
  retrieveAllPatients() {
    return axios.get("http://localhost:8082/api/patients/all", {withCredentials: true});
 
  }

  // Had to add this to avoid session
  retrievePatientNormal(id){
    return axios.get(`http://localhost:8082/api/patients/${id}`)
  }

  retrievePatient(){
    return axios({method: "GET",
                  url: `http://localhost:8082/api/patients/loggedPatient`,
                  headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}
            });
  }

  updateMedicalRecord(record) {
    return axios({method: "PUT",
                  url: `http://localhost:8082/api/patients/updateMedicalRedord`,
                  data: record
            });
  }
}

export default new PatientService();