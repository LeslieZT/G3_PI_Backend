package dh.backend.music_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private SessionRegistry sessionRegistry;
    @GetMapping("/index1")
    public String index() {
        return "Asegurado index1";
    }

    @GetMapping("/index2")
    public String index2() {
        return "No asegurado index2";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSessions() {
        String sessionId = "";
        User userObject = null;
        List<Object> sessions = sessionRegistry.getAllPrincipals();
        for (Object session:sessions) {
            if(session instanceof User){
                userObject = (User) session;
            }
            List<SessionInformation>sessionInformations = sessionRegistry.getAllSessions(session,false);
            for(SessionInformation sessionInformation : sessionInformations){
                sessionId = sessionInformation.getSessionId();
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("response","Hello");
        response.put("sessionId", sessionId);
        response.put("sessionUser",userObject);
        return ResponseEntity.ok(response);
    }

    //Controllers de prueba

}
