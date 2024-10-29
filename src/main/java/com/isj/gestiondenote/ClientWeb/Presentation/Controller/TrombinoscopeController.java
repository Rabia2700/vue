package com.isj.gestiondenote.ClientWeb.Presentation.Controller;


import com.isj.gestiondenote.ClientWeb.utils.test.ModalWithHttpHeader;
import com.isj.gestiondenote.ClientWeb.utils.test.RequestInterceptor;
import com.isj.gestiondenote.ClientWeb.utils.test.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
@Slf4j
@Controller
@CrossOrigin
public class TrombinoscopeController {
    @GetMapping("/trombinoscope")
    public ModelAndView trombinoscope(Model model, HttpSession session){
        ModalWithHttpHeader.modelTrombinoscope(model, session);
        System.out.println(model);

        System.out.println("trombinoscope");
        return new ModelAndView("pages/gestion-trombinoscope/dashboard-trombinoscope");

    }

    @GetMapping("/emploisdetemps")
    public ModelAndView test1(Model model, HttpSession session){
        //ModalWithHttpHeader.modelTrombinoscope(model, session);


        RestTemplate restTemplate = new RestTemplate();
        String test = restTemplate.getForObject(URL.BASE_URL_EMP+ "contact/tests",String.class);
        // ResponseEntity<String> test = new RestTemplate().exchange(URL.BASE_URL_TRO+ "contact/test", HttpMethod.GET, httpEntity,String.class);
        //System.out.println("test"+test.getBody());
        model.addAttribute("test",test);
        return new ModelAndView("pages/gestion-trombinoscope/dashboard-trombinoscope");

    }
    @GetMapping("/disponibilite/Test")
    public ModelAndView test(Model model, HttpSession session){
        //ModalWithHttpHeader.modelTrombinoscope(model, session);


        RestTemplate restTemplate = new RestTemplate();
        String test = restTemplate.getForObject(URL.BASE_URL_DISP+ "contact/test",String.class);
       // ResponseEntity<String> test = new RestTemplate().exchange(URL.BASE_URL_TRO+ "contact/test", HttpMethod.GET, httpEntity,String.class);
        //System.out.println("test"+test.getBody());
        model.addAttribute("test",test);
        return new ModelAndView("pages/gestion-trombinoscope/dashboard-trombinoscope");

    }


}
