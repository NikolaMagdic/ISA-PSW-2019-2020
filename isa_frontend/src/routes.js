import Vue from "vue";
import Router from "vue-router"
Vue.use(Router);

const router = new Router({
  mode: "history", // Use browser history
  routes: [
    {
      path: "/",
      name: "Home",
      component: () => import("./components/LoginComponent")
    },
    {
      path: "/patients",
      name: "Patients",
      component: () => import("./components/ListPatientsComponent")                 
      
    },
    {
      path: "/requestPatient",
      name: "requestPatientt",
      component: () => import("./components/ExaminationRequestPatientComponent")                 
      
    },
    {
      path: "/patientExaminations",
      name: "requestPatient",
      component: () => import("./components/listPatientExaminations")                 
      
    },
    {
      path: "/patientHomePage",
      name: "HomePagePatient",
      component: () => import("./components/HomePagePatientComponent")
    },
    {
      path: "/patientHomePageFiltered",
      name: "HomePagePatient3",
      component: () => import("./components/HomePagePatientFilteredComponent")
    },
    {
      path: "/pretragaPatient",
      name: "HomePagePatient2",
      component: () => import("./components/pretragaPatientComponent")
    },
    {
      path: "/ExaminationsPatient",
      name: "Examinations2",
      component: () => import("./components/ListExaminationsPatientComponent")
    },
    {
      path: "/ClinicExaminationsPatient",
      name: "Examinations23",
      component: () => import("./components/ListDoctorsPatientComponent")
    },
    {
      path: "/rateClinic",
      name: "rateC",
      component: () => import("./components/rateClinicComponent")                 
      
    },
    {
      path: "/PatientHistory",
      name: "History",
      component: () => import("./components/listPatientExaminations")
    },
    {
      path: "/rateDoctor",
      name: "rateD",
      component: () => import("./components/rateDoctorComponent")                 
      
    },

    {
      path: "/EditPersonalData",
      name: "EditPersonalData",
      component: () => import("./components/EditPersonalDataComponent")
    },
    {
      path: "/clinics",
      name: "Clinics",
      component: () => import("./components/ListClinicsComponent")
    },
    {
      path: "/doctors",
      name: "ListDoctors",
      component: () => import("./components/ListDoctorsComponent")
    },
    {
      path: "/diagnosis",
      name: "ListDiagnosis",
      component: () => import("./components/ListDiagnosisComponent")
    },
    {
      path: "/medicalrooms",
      name: "ListMedicalRooms",
      component: () => import("./components/ListMedicalRoomsComponent")
    },
    {
      path: "/EnterClinicalCenterAdministrator",
      name: "EnterClinicalCenterAdministrator",
      component: () => import("./components/EnterClinicalCenterAdministratorComponent")
    },
    {
      path: "/EnterClinicalAdministrator",
      name: "EnterClinicalAdministrator",
      component: () => import("./components/EnterClinicalAdministrator")
    },
    {
      path: "/ValidateClinicalCenterAdministrator/:id",
      name: "ValidateClinicalCenterAdministrator",
      component: () => import("./components/ValidateClinicalCenterAdministratorComponent")
    },
    {
      path: "/ClinicalCenterAdministratorHomePage/:id",
      name: "ClinicalCenterAdministratorHomePage",
      component: () => import("./components/ClinicalCenterAdministratorHomePageComponent")
    },
    {
      path: "/login",
      name: "LoginComponent",
      component: () => import("./components/LoginComponent")
    },
    {
      path: "/patients",
      name: "ListPatientsComponent",
      component: () => import("./components/ListPatientsComponent")
    },
    {
      path: "/start",
      name: "StartPage",
      component: () => import("./components/StartPage")
    },
    {
      path: "/all",
      name: "All",
      component: () => import("./components/All")
    },
    {
      path: "/examinations",
      name: "ListExaminationsComponent",
      component: () => import("./components/ListExaminationsComponent")
    },
    {
      path: "/NurseHomePage/:id",
      name: "NurseHomePage",
      component: () => import("./components/NurseHomePageComponent")
    },
    {
      path: "/doctorHomePage/:id",
      name: "DoctorHomePage",
      component: () => import("./components/HomePageDoctorComponent")
    },
    {
      path:"/examinationtypes",
      name: "ListExaminationTypesComponent",
      component: () => import("./components/ListExaminationTypesComponent")
    },
    {
      path: "/addexaminationtype/:id", 
      name: "ExaminationTypeComponent",
      component: () => import("./components/AddExaminationTypeComponent")
    },
    {
      path: "/absencerequests",
      name: "ListAbsenceRequestsComponent",
      component: () => import("./components/ListAbsenceRequestsComponent")
    },
    {
      path: "/addabsencerequest/:id", 
      name: "addabsencerequest",
      component: () => import("./components/CreateAbsenceRequestComponent")
    }, 
    {
      path: "/addabsencerequestnurse/:id", 
      name: "addabsencerequestnurse",
      component: () => import("./components/CreateAbsenceRequestNurse")
    }, 
    {
      path: "/editadministrator/:id",
      name: "editadministrator",
      component: () => import("./components/EditAdministratorComponent")
    }, 
    {
      path: '/editdoctor/:id',
      //Name is important in this case because I target link in a different way (because i have link instead a button)
      name: 'editdoctor', 
      component: () => import("./components/EditDoctorComponent")
    },
    {
      path: '/patientprofile/:id',
      name: 'patientprofile',
      component: () => import("./components/PatientProfile")
    },
    {
      path: '/editnurse/:id',
      name: 'editnurse',
      component: () => import("./components/EditNurseComponent")
    },
    { // Pravljenje reporta za pacijenta koji je izabran sa liste 
      path: '/makeReport/:idd/:idp',
      name: 'makeReport',
      component: () => import("./components/makeReportComponent")
    },
    {
      path: '/editroom/:id',
      name: 'editroom',
      component: () => import("./components/EditMedicalRoomComponent")
    },
    {
      path: '/addexamination/:idd/:idp',
      name: 'addexamination',
      component: () => import("./components/AddExaminationDoctorComponent")
    },
    {
      path: '/editclinic/:id',
      name: 'editclinic',
      component: () => import("./components/EditClinicComponent")
    },
    {
      path: '/bookOperationRoom/:examinationId',
      name: 'bookOperationRoom',
      component: () => import("./components/BookOperationRoomComponent")
    },
    // Patients of one doctor, id is of doctor who is logged-in
    {
      path: "/patients/:id",
      name: "patients",
      component: () => import("./components/ListPatientsComponent")                  
    },
    // Clinic admin sees this component when doctor sends him a request for examination
    {
      path: '/bookExaminationRoom/:examinationId',
      name: 'bookExaminationRoom',
      component: () => import("./components/BookExaminationRoomComponent")
    },
    {
      path: "/ClinicAdministratorHomePage/:id",
      name: "ClinicAdministratorHomePage",
      component: () => import("./components/ClinicAdministratorHomePageComponent")
    },
    {
      path: "/editclinicadmin/:id",
      name: "editclinicadmin",
      component: () => import("./components/EditClinicAdminComponent")
    },
    {
      path: "/businessreport/:id",
      name: "businessreport",
      component: () => import("./components/BusinessReportComponent")
    },
    {
      path: "/registerPatient",
      name: "registerPatient",
      component: () => import("./components/RegisterPatient")
    }  
  ]
});

export default router;