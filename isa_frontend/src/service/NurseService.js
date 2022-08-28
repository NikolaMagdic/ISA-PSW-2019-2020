import axios from "axios";

class NurseService{

    retrieveNurse(id){
        return axios({url: 'http://localhost:8082/api/nurses/' + id, 
                    method: 'GET',
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}});
    }

    editNurse(nurse){
        return axios.put("http://localhost:8082/api/nurses", nurse, {withCredentials: true});
    }
}

export default new NurseService();