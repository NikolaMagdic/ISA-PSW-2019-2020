import axios from "axios";


class DoctorService {
  retrieveAllDoctors() {
    return axios.get(`http://localhost:8082/api/doctors/all`);
  }

  deleteDoctor(id){
    return axios.delete(`http://localhost:8082/api/doctors/${id}`,{withCredentials: true});
  }

  retrieveDoctor(id){
    return axios.get(`http://localhost:8082/api/doctors/${id}`);
  }

  editDoctor(doctor){
    return axios.put("http://localhost:8082/api/doctors", doctor),{withCredentials: true};
  }
  retrieveDoctorsOfClinic(id){
      return  axios.get('http://localhost:8082/api/clinics/getDoctors/' + id,{withCredentials: true})
  }
  retrieveAllDoctorsWherePatientWas() {
    return axios({method: "GET",
                  url: "http://localhost:8082/api/doctors/allDoctorsOfPatient",
                  headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}});
  }

}

export default new DoctorService();