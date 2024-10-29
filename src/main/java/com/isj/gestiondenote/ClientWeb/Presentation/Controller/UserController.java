package com.isj.gestiondenote.ClientWeb.Presentation.Controller;

import com.isj.gestiondenote.ClientWeb.Model.dto.UserDto;
import com.isj.gestiondenote.ClientWeb.Model.dto.*;
import com.isj.gestiondenote.ClientWeb.Model.entities.*;
import com.isj.gestiondenote.ClientWeb.Model.modeletat.*;
import com.isj.gestiondenote.ClientWeb.utils.test.*;
import com.isj.gestiondenote.ClientWeb.utils.test.ModalWithHttpHeader;
import com.isj.gestiondenote.ClientWeb.utils.test.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
public class UserController {
    @RequestMapping(value = {"/"})
    public ModelAndView pageConnexionForm(Model model) {
        final ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("user", new UserDto());
        modelAndView.setViewName("Accueil");
        return modelAndView;
    }

    @PostMapping("/connexion")
    public String pageConnexion(@ModelAttribute(name = "utilisateur") Utilisateur utilisateur, Model model) {
        return "redirect:dashboard";
    }

    @GetMapping("/deconnexion")
    public String deconnexion(Model model) {
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String pageAccueil(Model model) {
        //      Modal.model(model);

        return "Accueil";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute UserDto userDto, HttpSession session) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDto> httpEntity = new HttpEntity<>(userDto, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<UserResponseDto> responseEntity = restTemplate.postForEntity(
                    new URI(URL.BASE_URL_AUTH + "/user/login"),
                    httpEntity,
                    UserResponseDto.class
            );

            // Vérifier le code de statut de la réponse
            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                UserResponseDto userResponseDto = responseEntity.getBody();
                if (userResponseDto != null) {
                    session.setAttribute("email", userResponseDto.getUsername());
                    session.setAttribute("matricule", userResponseDto.getMatricule());
                    session.setAttribute("filiere", userResponseDto.getFiliere());
                    session.setAttribute("classe", userResponseDto.getClasse());
                    session.setAttribute("name", userResponseDto.getName());
                    session.setAttribute("accessToken", userResponseDto.getAccessToken());
                    session.setAttribute("authorithies", userResponseDto.getAuthorities());
                    List<AuthorityDto> authorities = userResponseDto.getAuthorities();
                    if (!authorities.isEmpty()) {
                        session.setAttribute("authorithy", authorities.get(0).getAuthority());
                    }

                    return "redirect:/dashboard";
                } else {
                    model.addAttribute("errorMessage", "Identifiants incorrects. Veuillez réessayer.");
                    return "login";
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Cas d'échec de l'authentification
                model.addAttribute("errorMessage", "Identifiants incorrects. Veuillez réessayer.");
                return "login";
            } else {
                model.addAttribute("errorMessage", "Erreur lors de la connexion. Veuillez réessayer.");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la connexion. Veuillez réessayer.");
            return "login";
        }
    }



    @GetMapping("/logout")
    public String disconnectUser(Model model, HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/gestionnote")
    public String pageProfile(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        return "layout/gestionNote/gestNote";
    }

    @GetMapping("/profil")
    public String pageProfileEtudiant(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        return "layout/gestionNote/monProfil";
    }

    @GetMapping("/parcours")
    public String PageParcoursEtudiant(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        return "layout/gestionNote/academique";
    }

//    http://localhost:9087/api/users-management/api/user/afficherConsole

    @GetMapping("/creer")
    public String creer(Model model, HttpSession session){
        ModalWithHttpHeader.model(model, session);
        String accessToken = (String) session.getAttribute("accessToken");
        String matricule = (String) session.getAttribute("matricule");
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("matricule", matricule);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject("http://localhost:9087/api/users-management/api/user/afficherConsole", model,Void.class);

        System.out.println(model);
        return "redirect:dashboard";

    }

}
