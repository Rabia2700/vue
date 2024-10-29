package com.isj.gestiondenote.ClientWeb.Presentation.Controller;


import com.isj.gestiondenote.ClientWeb.Model.dto.RapportDisciplinaireDTO;
import com.isj.gestiondenote.ClientWeb.Model.entities.RapportDisciplinaire;
import com.isj.gestiondenote.ClientWeb.utils.test.ModalWithHttpHeader;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DisciplineController {


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/etatdiscipline")
    public ModelAndView EtatDisciplinaire(Model model, HttpSession session) {
        ModalWithHttpHeader.modelEtatDisciplinaire(model, session);
        System.out.println(model);

        System.out.println("EtatDisciplinaire");
        return new ModelAndView("pages/gestion-discipline/dashboard-discipline");
    }

    @GetMapping("/listeabsences")
    public ModelAndView ListeAbsences(Model model, HttpSession session) {
        String matricule = (String) session.getAttribute("matricule");
        String classe = (String) session.getAttribute("classe");
        model.addAttribute("matricule", session.getAttribute("matricule"));
        model.addAttribute("classe", session.getAttribute("classe"));
        System.out.println("Liste des retards");
        System.out.println("Matricule: " + matricule);
        System.out.println("Classe: " + classe);
        System.out.println("Liste des absences");
        return new ModelAndView("pages/gestion-discipline/liste-absences");
    }

    @GetMapping("/listeretards")
    public ModelAndView ListeRetards(Model model, HttpSession session) {
        model.addAttribute("session", session);
        String matricule = (String) session.getAttribute("matricule");
        String classe = (String) session.getAttribute("classe");
        model.addAttribute("matricule", session.getAttribute("matricule"));
        model.addAttribute("classe", session.getAttribute("classe"));
        System.out.println("Liste des retards");
        System.out.println("Matricule: " + matricule);
        System.out.println("Classe: " + classe);
        return new ModelAndView("pages/gestion-discipline/liste-retards");
    }

    @GetMapping("/citeUetat")
    public ModelAndView CiteU(Model model, HttpSession session) {
        model.addAttribute("session", session);
//        List<RapportDisciplinaire> rapports = rapportService.getAllRapports();
//        model.addAttribute("rapports", rapports);
        System.out.println("Etat Cité U");
        return new ModelAndView("pages/gestion-discipline/cite-universitaire");
    }

    @GetMapping("/rediger-rapport")
    public String showCreateForm(Model model) {
        model.addAttribute("rapportDisciplinaireDTO", new RapportDisciplinaireDTO());
        return "pages/gestion-discipline/rediger-rapport";
    }

    @PostMapping("/creer-rapports")
    public String createRapport(RapportDisciplinaireDTO rapportDisciplinaireDTO, Model model) {
        try {
            // Créez des en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Créez une entité HTTP avec le DTO et les en-têtes
            HttpEntity<RapportDisciplinaireDTO> request = new HttpEntity<>(rapportDisciplinaireDTO, headers);

            // URL de votre service discipline
            String url = "http://localhost:9100/api/rapports";

            // Envoyer la requête POST et récupérer la réponse
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            // Vérifiez la réponse et ajoutez un message
            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("message", "Rapport créé avec succès");
            } else {
                model.addAttribute("message", "Erreur lors de la création du rapport");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Erreur lors de la communication avec le service discipline");
            e.printStackTrace();
        }

        // Retourne la vue "rediger-rapport" avec le message de réponse
        return "pages/gestion-discipline/rediger-rapport";
    }

    @GetMapping("/profils")
    public String Profils(Model model, HttpSession session) {
        model.addAttribute("session", session);

        System.out.println("Page de rédaction rapport");
        return "pages/gestion-discipline/profil-etudiant";
    }

}
