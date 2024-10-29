package com.isj.gestiondenote.ClientWeb.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isj.gestiondenote.ClientWeb.Model.dto.*;
import com.isj.gestiondenote.ClientWeb.utils.test.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Controller
public class BiblioController {

    private final ObjectMapper objectMapper;

    public BiblioController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/gestionbibliotheque")
    public String pageProfileBiblio(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
        RestTemplate restTemplate = new RestTemplate();

        Object[] emprunts = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/emprunt/all", Object[].class);
        Object[] ouvrages = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/ouvrages/all", Object[].class);
        Object[] categories = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/categories/all", Object[].class);
        Object[] adherants = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/utilisateur/all", Object[].class);

        model.addAttribute("emprunts", emprunts);
        model.addAttribute("ouvrages", ouvrages);
        model.addAttribute("categories", categories);
        model.addAttribute("adherants", adherants);
        return "layout/gestionbiblio/gestBiblio";
    }

    @GetMapping("/gestemprunts")
    public String pageEmpruntBiblio(Model model, HttpSession session){
        model.addAttribute("session", session);
        RestTemplate restTemplate = new RestTemplate();

        Object[] emprunts = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/emprunt/all", Object[].class);
        model.addAttribute("emprunts", emprunts);
        model.addAttribute("matricule", session.getAttribute("matricule"));
        return "layout/gestionbiblio/empruntBiblio";
    }

    @GetMapping("/newEmprunt")
    public String pageNewEmpruntBiblio(Model model, HttpSession session){
        model.addAttribute("session", session);
        RestTemplate restTemplate = new RestTemplate();

        Object[] ouvrages = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/ouvrages/all", Object[].class);
        Object[] adherants = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/utilisateur/all", Object[].class);

        EmpruntDto emprunt1 = new EmpruntDto();
        emprunt1.setDateDebut(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        model.addAttribute("emprunt1", emprunt1);
        model.addAttribute("ouvrages", ouvrages);
        model.addAttribute("adherants", adherants);
        return "layout/gestionbiblio/createEmprunt";
    }

    @PostMapping("/saveEmprunt")
    public String creerEmprunt(@ModelAttribute EmpruntDto emprunt) throws URISyntaxException {
        try {
            emprunt.setDateDebut(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (emprunt.getDateDebut().getYear() > emprunt.getDateFin().getYear()) {
                return "redirect:/newEmprunt";
            } else {
                if (emprunt.getDateDebut().getYear() == emprunt.getDateFin().getYear()) {
                    if (emprunt.getDateDebut().getMonth() > emprunt.getDateFin().getMonth()) {
                        return "redirect:/newEmprunt";
                    } else {
                        if (emprunt.getDateDebut().getMonth() == emprunt.getDateFin().getMonth()) {
                            if (emprunt.getDateDebut().getDate() > emprunt.getDateFin().getDate()) {
                                return "redirect:/newEmprunt";
                            } else {
                                if (emprunt.getDateDebut().getDate() == emprunt.getDateFin().getDate()) {
                                    return "redirect:/newEmprunt";
                                }
                            }
                        }
                    }
                }
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<EmpruntDto> httpEntity = new HttpEntity<>(emprunt, headers);
            CompletableFuture.runAsync(() -> {
                restTemplate.postForObject(URI.create(URL.BASE_URL_BIBLIO + "/emprunt/create"), httpEntity, Object[].class);
            });
            return "redirect:/gestemprunts";
        } catch (Exception e) {
            // Gérer l'erreur
            System.out.println("Erreur pour la creation d'un emprunt");
            System.out.println(e);
            return "redirect:/newEmprunt";
        }
    }

    @GetMapping("/mesfavoris")
    public String pageFavorisBiblio(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
        return "layout/gestionbiblio/favoris";
    }

    @GetMapping("/details/{id}")
    public String pageDetailsBiblio(Model model, HttpSession session, @PathVariable("id") Integer id){
        RestTemplate restTemplate = new RestTemplate();
        OuvrageDto ouvrage = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/ouvrages/"+id, OuvrageDto.class);

        model.addAttribute("session", session);
        model.addAttribute("ouvrage", ouvrage);
        return "layout/gestionbiblio/detailOuvrage";
    }

    @GetMapping("/cloturerEmprunt/{id}")
    public String cloturerEmpruntBiblio(Model model, @PathVariable("id") Integer id){

        RestTemplate restTemplate = new RestTemplate();
        EmpruntDto emprunt = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/emprunt/"+id, EmpruntDto.class);
//        System.out.println(emprunt);
        model.addAttribute("emprunt", emprunt);

        return "layout/gestionbiblio/cloturerEmprunt";
    }

    @PostMapping("/cloturer/{id}")
    public String empruntCloturerBiblio(@ModelAttribute EmpruntDto emprunt1, @PathVariable("id") Integer id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        emprunt1.setId(id);

        HttpEntity<EmpruntDto> httpEntity = new HttpEntity<>(emprunt1, headers);
        CompletableFuture.runAsync(() -> {
            restTemplate.postForObject(URL.BASE_URL_BIBLIO + "/emprunt/cloturer", httpEntity, Object[].class);
        });
        return "redirect:/gestemprunts";
    }

    @GetMapping("/createadherant")
    public String pageNewAdherantBiblio(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
        RestTemplate restTemplate = new RestTemplate();

        Object[] user = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/user/all", Object[].class);

        UtilisateurDto adherant = new UtilisateurDto();
        model.addAttribute("user", user);
        model.addAttribute("adherant", adherant);
        return "layout/gestionbiblio/createAdherant";
    }

    @GetMapping("/createcategorie")
    public String pageNewCategorieBiblio(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
        CategorieDto categorie1 = new CategorieDto();
        model.addAttribute("categorie1", categorie1);
        return "layout/gestionbiblio/createCategorie";
    }

    @GetMapping("/createemplacement")
    public String pageNewEmplacementBiblio(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
        LocalisationDto emplacement1 = new LocalisationDto();
        model.addAttribute("emplacement1", emplacement1);
        return "layout/gestionbiblio/createEmplacement";
    }

    @GetMapping("/createouvrage")
    public String pageNewOuvrageBiblio(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);

        // Récupérer les catégories depuis l'API
        RestTemplate restTemplate = new RestTemplate();
        Object[] categories = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/categories/all", Object[].class);
        // Récupérer les emplacements depuis l'API Localisation
        Object[] emplacements = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/localisation/all", Object[].class);

        OuvrageDto ouvrage1 = new OuvrageDto();
        model.addAttribute("ouvrage1", ouvrage1);
        model.addAttribute("categories", categories); // Ajouter les catégories au modèle
        model.addAttribute("emplacements", emplacements); // Ajouter les emplacements au modèle

        return "layout/gestionbiblio/createOuvrage";
    }

    @GetMapping("/gestionOuvragebiblio")
    public String pageOuvragBiblio(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
        return "layout/gestionbiblio/Ouvrages";
    }

    @GetMapping("/listAdherants")
    public ModelAndView listeAdherants(Model model, HttpSession session) {
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
//        Modal.model(model);
//        String accessToken = (String) session.getAttribute("accessToken");
//        model.addAttribute("accessToken", accessToken);
        System.out.println(model);
        RestTemplate restTemplate = new RestTemplate();

        Object[] adherants = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/utilisateur/all", Object[].class);
        model.addAttribute("adherants", adherants);

        System.out.println(model);
        return new ModelAndView("layout/gestionbiblio/ListAdherants");
    }

    @GetMapping("/listCategories")
    public ModelAndView listeCategories(Model model, HttpSession session) {
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
//        Modal.model(model);
//        String accessToken = (String) session.getAttribute("accessToken");
//        model.addAttribute("accessToken", accessToken);
        System.out.println(model);
        RestTemplate restTemplate = new RestTemplate();

        Object[] categories = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/categories/all", Object[].class);
        model.addAttribute("categories", categories);

        System.out.println(model);
        return new ModelAndView("layout/gestionbiblio/ListCategories");
    }

//    @DeleteMapping("/supprimerCategorie/{id}")
//    public ModelAndView listeCategories(@PathVariable Integer id) {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.delete(URL.BASE_URL_BIBLIO + "/categories/delete/"+id, Object[].class);
//        return new ModelAndView("layout/gestionbiblio/ListCategories");
//    }

    @GetMapping("/listEmplacements")
    public ModelAndView listeEmplacement(Model model, HttpSession session) {
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
//        Modal.model(model);
//        String accessToken = (String) session.getAttribute("accessToken");
//        model.addAttribute("accessToken", accessToken);
        System.out.println(model);
        RestTemplate restTemplate = new RestTemplate();

        Object[] emplacements = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/localisation/all", Object[].class);
        model.addAttribute("emplacements", emplacements);

        System.out.println(model);
        return new ModelAndView("layout/gestionbiblio/ListEmplacement");
    }

    @GetMapping("/listOuvrages")
    public ModelAndView listeOuvrages(Model model, HttpSession session) {
//        ModalWithHttpHeader.model(model, session);
        model.addAttribute("session", session);
//        Modal.model(model);
//        String accessToken = (String) session.getAttribute("accessToken");
//        model.addAttribute("accessToken", accessToken);
        System.out.println(model);
        RestTemplate restTemplate = new RestTemplate();

        Object[] ouvrages = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "/ouvrages/all", Object[].class);
        model.addAttribute("ouvrages", ouvrages);

        System.out.println(model);
        return new ModelAndView("layout/gestionbiblio/ListOuvrages");

    }


    @PostMapping("/newAdherant")
    public String creerAdherant(@ModelAttribute UtilisateurDto adherant, Model model) throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<UtilisateurDto> httpEntity = new HttpEntity<>(adherant, headers);

        CompletableFuture.runAsync(() -> {
            restTemplate.postForObject(
                    URI.create(URL.BASE_URL_BIBLIO + "/utilisateur/create"),
                    httpEntity,
                    Object[].class
            );
        });

        return "redirect:/listAdherants";
    }

    @PostMapping("/newCategorie")
    public String creerCategorie(@ModelAttribute CategorieDto categorie, Model model) throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<CategorieDto> httpEntity = new HttpEntity<>(categorie, headers);
        System.out.println(categorie);

        CompletableFuture.runAsync(() -> {
            restTemplate.postForObject(
                    URI.create(URL.BASE_URL_BIBLIO + "/categories/create"),
                    httpEntity,
                    Object[].class
            );
        });

        return "redirect:/listCategories";
    }

    @PostMapping("/newEmplacement")
    public String creerEmplacement(@ModelAttribute LocalisationDto emplacement, Model model) throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<LocalisationDto> httpEntity = new HttpEntity<>(emplacement, headers);
        System.out.println(emplacement);

        CompletableFuture.runAsync(() -> {
            restTemplate.postForObject(
                    URI.create(URL.BASE_URL_BIBLIO + "/localisation/create"),
                    httpEntity,
                    Object[].class
            );
        });

        return "redirect:/listEmplacements";
    }

    @PostMapping("/newOuvrage")
    public String creerOuvrage(@ModelAttribute OuvrageDto ouvrage, Model model) throws URISyntaxException {
        try {
            if (ouvrage.getNb_exemplaire() == null) ouvrage.setNb_exemplaire(1);
            if (ouvrage.getNb_exemplaire() <= 0) return "redirect:/createouvrage";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();
            ouvrage.setDisponible(true);

            HttpEntity<OuvrageDto> httpEntity = new HttpEntity<>(ouvrage, headers);

            CompletableFuture.runAsync(() -> {
                restTemplate.postForObject(
                        URI.create(URL.BASE_URL_BIBLIO + "/ouvrages/create"),
                        httpEntity,
                        Object[].class
                );
            });
            return "redirect:/listOuvrages";
        } catch (Exception e) {
            // Gérer l'erreur
            System.out.println("Erreur");
            System.out.print(e);
            return "redirect:/createouvrage";
        }
    }


}
