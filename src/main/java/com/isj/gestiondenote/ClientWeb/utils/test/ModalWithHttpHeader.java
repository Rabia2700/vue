package com.isj.gestiondenote.ClientWeb.utils.test;

import com.isj.gestiondenote.ClientWeb.Model.entities.Classe;
import com.isj.gestiondenote.ClientWeb.Model.entities.Etudiant;
import com.isj.gestiondenote.ClientWeb.Model.modeletat.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class ModalWithHttpHeader {

    public static void model(Model model, HttpSession session){

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object[]> classes = new RestTemplate().exchange(URL.BASE_URL+"classe/all", HttpMethod.GET, httpEntity, Object[].class);
        ResponseEntity<Object[]> anneeacademiques = new RestTemplate().exchange(URL.BASE_URL+"annee-accademique/all", HttpMethod.GET, httpEntity, Object[].class);
        ResponseEntity<Object[]> semestres = new RestTemplate().exchange(URL.BASE_URL + "semestre/all", HttpMethod.GET, httpEntity, Object[].class);
        if (session.getAttribute("authorithy") == "Etudiant"){
            ResponseEntity<Etudiant> etudiant = new RestTemplate().exchange(URL.BASE_URL + "etudiant/"+session.getAttribute("matricule")+"/get", HttpMethod.GET, httpEntity, Etudiant.class);
            model.addAttribute("etudiant",etudiant.getBody());
            ResponseEntity<Classe> classe = new RestTemplate().exchange(URL.BASE_URL + "classe/"+session.getAttribute("classe")+"/data", HttpMethod.GET, httpEntity, Classe.class);
            model.addAttribute("classe",classe.getBody());
        }
        ResponseEntity<Integer> etudiantNumber = new RestTemplate().exchange(URL.BASE_URL + "etudiant/nb-student/", HttpMethod.GET, httpEntity, Integer.class);
        ResponseEntity<Integer> enseignantNumber = new RestTemplate().exchange(URL.BASE_URL + "enseignant/nb-enseignant/", HttpMethod.GET, httpEntity, Integer.class);


        model.addAttribute("carteEtudiant", new CarteEtudiant());
        model.addAttribute("attestationDiplome", new AttestationEtDiplome());
        model.addAttribute("pv", new Pv());
        model.addAttribute("ficheAbsence", new FicheAbsence());
        model.addAttribute("releve", new ReleveNote());
        model.addAttribute("pvsynthese", new PVSynthese());
        model.addAttribute("connection", new Connection());
        model.addAttribute("classes",classes.getBody());
        model.addAttribute("anneeacademiques",anneeacademiques.getBody());
        model.addAttribute("semestres",semestres.getBody());
        model.addAttribute("nbstudent",etudiantNumber.getBody());
        model.addAttribute("nbenseignant",enseignantNumber.getBody());
        System.out.println(session.getAttribute("etudiant"));

    }


    public static void modelIntervention(Model model, HttpSession session){

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        String accessToken = (String) session.getAttribute("accessToken");
        String matricule = (String) session.getAttribute("matricule");
        model.addAttribute("accessToken", accessToken);

//        RestTemplate restTemplate = new RestTemplate();

        if(Objects.equals(session.getAttribute("authorithy"), "Admin")){
            System.out.println(session.getAttribute("matricule"));

            ResponseEntity<Long> code = new RestTemplate().exchange(URL.BASE_URL_INT+"user/id/"+session.getAttribute("matricule")+"/user", HttpMethod.GET, httpEntity, Long.class);
            System.out.println(code.getBody());

//          Long code = restTemplate.getForObject(URL.BASE_URL_INT + "user/id/"+session.getAttribute("matricule")+"/user", Long.class);

            model.addAttribute("code", code.getBody());
            session.setAttribute("code", code.getBody());

            System.out.println(model);}
        else {
            ResponseEntity<Long> code = new RestTemplate().exchange(URL.BASE_URL_INT+"user/id/"+session.getAttribute("matricule")+"/student", HttpMethod.GET, httpEntity, Long.class);

//            Long code = restTemplate.getForObject(URL.BASE_URL_INT + "user/id/"+session.getAttribute("matricule")+"/student", Long.class);
            session.setAttribute("code", code.getBody());
            model.addAttribute("code", code.getBody());
        }
    }

    public static void modelTrombinoscope(Model model, HttpSession session){
//        HttpHeaders headers = new HttpHeaders();
//        RequestInterceptor.addHeaders(headers, session);
//        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        model.addAttribute("accessToken", session.getAttribute("accessToken"));
        model.addAttribute("matricule", session.getAttribute("matricule"));
    }
    public static void modelBiblio(Model model, HttpSession session){
        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        String accessToken = (String) session.getAttribute("accessToken");
        String matricule = (String) session.getAttribute("matricule");
        model.addAttribute("accessToken", accessToken);
    }

    public static void modelEtatDisciplinaire(Model model, HttpSession session) {
        model.addAttribute("accessToken", session.getAttribute("accessToken"));
        model.addAttribute("matricule", session.getAttribute("matricule"));
    }
}
