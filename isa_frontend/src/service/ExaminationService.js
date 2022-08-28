import axios from "axios";


class ExaminationService {
  retrieveAllExaminations() {
    return axios.get(`http://localhost:8082/api/examinations/all`,{withCredentials: true});
  }
  retrieveAllExaminationsOfSelectedClinic() {
    return axios.get(`http://localhost:8082/api/examinations/allByClinic`,{withCredentials: true});
  }
  retrieveAllNonReservedExaminations() {
    return axios({method: "GET",
                  url:`http://localhost:8082/api/examinations/nonReserved`,
                  headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}
            });
  }
  retrieveExamination(id) {
    return axios.get(`http://localhost:8082/api/examinations/${id}`, {withCredentials: true});
  }
  retrieveAllPatientExaminations() {
    return axios.get(`http://localhost:8082/api/examinations/allByPatient`,{withCredentials: true});
  }
}

export default new ExaminationService();