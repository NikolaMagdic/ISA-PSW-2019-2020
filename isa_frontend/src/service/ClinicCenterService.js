import axios from "axios";



class ClinicCenterService {
  retrieveAllClinics() {
    return axios({method: "GET",
                  url: "http://localhost:8082/api/clinics/all",
                  headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}
                });
  }
  retrieveAllClinicsFiltered(date,type) {
    return axios({method: "GET",
                  url: 'http://localhost:8082/api/clinics/allFiltered/'+date+'/'+type,
                  headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}
                });

  }
  retrieveClinic(id){
    return axios.get(`http://localhost:8082/api/clinics/${id}`, {withCredentials: true});
  }
  
  updateClinic(id, clinic) {
    return axios.put(`http://localhost:8082/api/clinics/${id}`, clinic, {withCredentials: true});
  }

  editClinic(clinic){
    return axios.put("http://localhost:8082/api/clinics", clinic,{withCredentials: true});
  }

  deleteClinic(id){
    return axios.delete(`http://localhost:8082/api/clinics/${id}`,{withCredentials: true})
  }
  retrieveDoctorsOfClinic(date,type,id){
    return  axios.get('http://localhost:8082/api/clinics/select/'+date+'/'+type+'/' + id,{withCredentials: true})
  }
  retrieveAllClinicsWherePatientWas() {
    return axios({method:"GET",
                  url: "http://localhost:8082/api/clinics/allClinicsOfPatient",
                  headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}});
  }

}


export default new ClinicCenterService();