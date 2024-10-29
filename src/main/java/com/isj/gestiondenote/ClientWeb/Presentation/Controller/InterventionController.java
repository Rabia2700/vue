package com.isj.gestiondenote.ClientWeb.Presentation.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isj.gestiondenote.ClientWeb.Model.modelIntervention.Intervention;
import com.isj.gestiondenote.ClientWeb.Model.modelIntervention.PieceJointe;
import com.isj.gestiondenote.ClientWeb.Model.modelIntervention.Statut;
import com.isj.gestiondenote.ClientWeb.config.CustomObjectMapper;
import com.isj.gestiondenote.ClientWeb.utils.test.Modal;
import com.isj.gestiondenote.ClientWeb.utils.test.ModalWithHttpHeader;
import com.isj.gestiondenote.ClientWeb.utils.test.RequestInterceptor;
import com.isj.gestiondenote.ClientWeb.utils.test.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Slf4j
@Controller
public class InterventionController {

    @GetMapping("/gestionintervention")
    public ModelAndView pageintervention(Model model, HttpSession session) {
//        ModalWithHttpHeader.modelIntervention(model, session);
        model.addAttribute("session", session);
        System.out.println(model);

        System.out.println("pageintervention");
        statistiqueDashboard(model, session);
        System.out.println("pageinterventionTermine");
        
        return new ModelAndView("pages/gestion-interventions/dashboard-interventions");

    }

    @GetMapping("/interventionPersonnelle")
    public ModelAndView pageTous(Model model, HttpSession session) {
        model.addAttribute("session", session);
        return new ModelAndView("pages/gestion-interventions/interventions-personnelles");
    }

    @PostMapping("/creerIntervention")
    public String creerIntervention(Model model, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(model);

        System.out.println("creerIntervention");

        return "redirect:/listeIntervention";
    }
    @GetMapping("/listeIntervention")
    public ModelAndView listeIntervention(Model model, HttpSession session) {
//        ModalWithHttpHeader.modelIntervention(model, session);
        model.addAttribute("code", session.getAttribute("code"));

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        System.out.println(model);

        System.out.println("listeIntervention");
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> interventions = new RestTemplate().exchange(URL.BASE_URL_INT + "Liste/etudiant/"+session.getAttribute("code"), HttpMethod.GET, httpEntity,Object[].class);
//        Object[] interventions = restTemplate.getForObject(URL.BASE_URL_INT + "Liste/etudiant/"+session.getAttribute("code"), Object[].class);
        model.addAttribute("interventions", interventions.getBody());
        System.out.println(model);
        System.out.println("listeInterventionTermine");

        return new ModelAndView("pages/gestion-interventions/liste-des-interventions");


    }
    @GetMapping("/oneIntervention/{idIntervention}")
    public ModelAndView oneIntervention(Model model, HttpSession session,@PathVariable Long idIntervention) {

        model.addAttribute("code", session.getAttribute("code"));

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        System.out.println(model);

        System.out.println("oneIntervention");
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> intervention = new RestTemplate().exchange(URL.BASE_URL_INT+"intervention/"+idIntervention, HttpMethod.GET, httpEntity,Object.class);

//        Object intervention = restTemplate.getForEntity(URL.BASE_URL_INT+"intervention/"+idIntervention, Object.class).getBody();
        model.addAttribute("oneIntervention",intervention.getBody());
        System.out.println(intervention);
        System.out.println("oneInterventionTermine");

        return new ModelAndView("pages/gestion-interventions/details-des-interventions");

    }


    @GetMapping("/listeInterventionParDepartement")
    public ModelAndView listeInterventionParDepartement(Model model, HttpSession session) {
        model.addAttribute("code", session.getAttribute("code"));

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        System.out.println(model);

        System.out.println("listeInterventionParDepartement");
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object[]> interventions = new RestTemplate().exchange(URL.BASE_URL_INT + "Liste/Departement/"+session.getAttribute("code"), HttpMethod.GET, httpEntity,Object[].class);

//        Object[] interventions = restTemplate.getForObject(URL.BASE_URL_INT + "Liste/Departement/"+session.getAttribute("code"), Object[].class);
        model.addAttribute("interventions", interventions.getBody());
        System.out.println("listeInterventionParDepartementTermine");

        return new ModelAndView("pages/gestion-interventions/liste-des-interventions");
    }
    @GetMapping("/intervention/personnel/{statut}")
    public ModelAndView interventionPersonnel(Model model, HttpSession session, @PathVariable Statut statut) {
        model.addAttribute("code", session.getAttribute("code"));

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        System.out.println(model);

        System.out.println("DebutinterventionPersonnelenCours");
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> interventions = new RestTemplate().exchange(URL.BASE_URL_INT + "personnel/"+session.getAttribute("code")+"/statut/"+statut, HttpMethod.GET, httpEntity,Object[].class);

//        Object[] interventions = restTemplate.getForObject(URL.BASE_URL_INT + "personnel/"+session.getAttribute("code")+"/statut/"+statut, Object[].class);
        model.addAttribute("interventions", interventions.getBody());
        System.out.println("interventionPersonnelenCoursTermine");

        return new ModelAndView("pages/gestion-interventions/liste-des-interventions-personnel");
    }

//    http://localhost:9090/api/interventions/prendre-en-charge/{{interventionId}}/{{personnelId}}
@GetMapping("/prendreEnCharge/{idIntervention}")
    public String prendreEnCharge(Model model, HttpSession session,@PathVariable Long idIntervention){
//    ModalWithHttpHeader.modelIntervention(model, session);
    System.out.println(model);

    System.out.println("prendreEnCharge");
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.put(URL.BASE_URL_INT + "prendre-en-charge/"+idIntervention+"/"+session.getAttribute("code"), null);
    System.out.println("prendreEnChargeTermine");

    return "redirect:/listeInterventionParDepartement";
}

    @PostMapping("/terminer/{idIntervention}")
    public String terminer(Model model, HttpSession session, @PathVariable Long idIntervention,
                           @RequestParam(value = "emailContent",required = false) String emailContent,
                           @RequestParam(value = "description",required = false) String description,
                           @RequestParam(value = "piecesJointes",required = false) List<MultipartFile> piecesJointes){
//        ModalWithHttpHeader.modelIntervention(model, session);
        System.out.println(model);

        System.out.println(piecesJointes.get(0).isEmpty());
        System.out.println(piecesJointes.size());
        System.out.println(piecesJointes);
        System.out.println(piecesJointes);
        System.out.println(piecesJointes);
        System.out.println(piecesJointes);
        try {
//            System.out.println(URL.BASE_URL_INT + "Termine/"+idIntervention+"?emailContent="+emailContent+"?piecesJointes="+piecesJointes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("emailContent", emailContent);
            if (!piecesJointes.get(0).isEmpty()){
                System.out.println("ekie");
                map.add("piecesJointes", piecesJointes);
            }

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);


            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new MappingJackson2HttpMessageConverter(new CustomObjectMapper()));
//            restTemplate.postForObject(URL.BASE_URL_INT + "Termine/" + idIntervention, requestEntity, Intervention.class);
            restTemplate.put(URL.BASE_URL_INT + "Termine/"+idIntervention+"?emailContent="+emailContent+"?description="+description+"?piecesJointes="+piecesJointes, null);


        } catch (HttpServerErrorException.InternalServerError e){
            System.err.println(e.getMessage());
        }
        return "redirect:/listeInterventionParDepartement";
    }

    @GetMapping("/cancel/{idIntervention}")
    public String cancelIntervention(Model model, HttpSession session,@PathVariable Long idIntervention){
//        ModalWithHttpHeader.modelIntervention(model, session);
        System.out.println(model);

        System.out.println("cancelIntervention");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(URL.BASE_URL_INT + "cancel/"+idIntervention, null,void.class);
        System.out.println("cancelInterventionTermine");

        return "redirect:/listeIntervention";
    }

    @GetMapping("/rejeter/{interventionId}")
    public String rejeterIntervention(Model model, HttpSession session,@PathVariable Long idIntervention){
//        ModalWithHttpHeader.modelIntervention(model, session);
        System.out.println(model);

        System.out.println("rejeterIntervention");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(URL.BASE_URL_INT + "rejeter/"+idIntervention, null,void.class);
        System.out.println("rejeterInterventionTermine");

        return "redirect:/listeInterventionParDepartement";
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(Model model, HttpSession session,@PathVariable Long id) {
//        ModalWithHttpHeader.modelIntervention(model, session);
        System.out.println(model);

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PieceJointe> fichier = new RestTemplate().exchange(URL.BASE_URL_INT + "pieceJointe/"+id, HttpMethod.GET, httpEntity,PieceJointe.class);

//        PieceJointe fichier = restTemplate.getForObject(URL.BASE_URL_INT + "pieceJointe/"+id, PieceJointe.class);;

        System.out.println(fichier.getBody());
        System.out.println("download");
//
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fichier.getBody().getNom() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(fichier.getBody().getContenu()));
    }

//    http://localhost:9090/api/interventions/statistique/2/true
//    @GetMapping("/statistiqueDashboard")
    public void statistiqueDashboard(Model model, HttpSession session) {
        ModalWithHttpHeader.modelIntervention(model, session);

        HttpHeaders headers = new HttpHeaders();
        RequestInterceptor.addHeaders(headers, session);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        System.out.println(model);
        System.out.println("Statdashbooooard");

//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Object[]> contacts = new RestTemplate().exchange(URL.BASE_URL_TRO+ "contact/all", HttpMethod.GET, httpEntity,Object[].class);

        ResponseEntity<Integer[]> statList = new RestTemplate().exchange(URL.BASE_URL_INT + "statistique/"+session.getAttribute("code")+"/false/"+session.getAttribute("authorithy"), HttpMethod.GET, httpEntity,Integer[].class);
        System.out.println("Statdashbooooard");

//        List<Integer> statList = restTemplate.getForObject(URL.BASE_URL_INT + "statistique/"+session.getAttribute("code")+"/false/"+session.getAttribute("authorithy"), List.class);
        model.addAttribute("statistiques", statList.getBody());

        System.out.println(model);
        System.out.println("Statdashbooooard");

        return ;

    }
    @GetMapping("/reject/{id}")
    public String test(Model model, HttpSession session,@PathVariable Long id){
//        ModalWithHttpHeader.modelIntervention(model, session);
        System.out.println("rejeterIntervention");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(URL.BASE_URL_INT + "rejeter/"+id, null,void.class);
        System.out.println("rejeterInterventionTermine");

        return "redirect:/listeInterventionParDepartement";
//                .header(HttpHeaders.CONTENT_DISPOSITION);
//                .body("new ByteArrayResource(fichier.getContenu())");
    }
}
