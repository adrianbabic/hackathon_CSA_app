package com.example.backend.config;


import com.example.backend.domain.Threat;
import com.example.backend.repository.ThreatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
class Generator {

    @Autowired
    private ThreatRepository threatRepository;
    ArrayList<String> deviceTypes = new ArrayList<>(List.of("Mobile", "Desktop", "Server", "IoT", "Tablet"));
    ArrayList<String> severities = new ArrayList<>(List.of("Low", "Medium", "High", "Critical"));
    ArrayList<Float> potentialImpacts = new ArrayList<>(List.of(0.32f, 0.45f, 0.67f, 0.89f, 0.99f, 0.41f, 0.13f, 0.57f, 0.79f));
    ArrayList<String> sources = new ArrayList<>(List.of("https://facebook.com", "https://twitter.com", "https://instagram.com", "https://youtube.com", "https://linkedin.com", "https://tiktok.com", "https://reddit.com", "https://pinterest.com", "https://twitch.tv", "https://whatsapp.com", "https://telegram.org", "https://snapchat.com", "https://tumblr.com", "https://discord.com", "https://vimeo.com", "https://dailymotion.com", "https://soundcloud.com", "https://spotify.com", "https://apple.com", "https://microsoft.com", "https://google.com", "https://amazon.com", "https://netflix.com", "https://ebay.com", "https://wikipedia.org", "https://yahoo.com", "https://wordpress.com", "https://github.com", "https://stackoverflow.com"));
    ArrayList<String> names = new ArrayList<>(List.of("SQL Injection", "Cross-Site Scripting", "Cross-Site Request Forgery", "Remote Code Execution", "Command Injection", "Buffer Overflow", "Path Traversal", "Denial of Service", "Insecure Direct Object Reference", "Insecure Deserialization", "LDAP Injection", "XML External Entity Injection", "Server-Side Request Forgery", "Unvalidated Redirects and Forwards", "HTTP Response Splitting", "HTTP Parameter Pollution", "Insecure Cryptographic Storage", "Insecure Randomness", "Insecure Authentication", "Insecure Password Storage", "Insecure Password Reset", "Insecure Session Management", "Insecure Direct Object Reference", "Insecure Deserialization", "LDAP Injection", "XML External Entity Injection", "Server-Side Request Forgery", "Unvalidated Redirects and Forwards", "HTTP Response Splitting", "HTTP Parameter Pollution", "Insecure Cryptographic Storage", "Insecure Randomness", "Insecure Authentication", "Insecure Password Storage", "Insecure Password Reset", "Insecure Session Management", "Insecure Direct Object Reference", "Insecure Deserialization", "LDAP Injection", "XML External Entity Injection", "Server-Side Request Forgery", "Unvalidated Redirects and Forwards", "HTTP Response Splitting", "HTTP Parameter Pollution", "Insecure Cryptographic Storage", "Insecure Randomness", "Insecure Authentication", "Insecure Password Storage", "Insecure Password Reset", "Insecure Session Management", "Insecure Direct Object Reference", "Insecure Deserialization", "LDAP Injection", "XML External Entity Injection", "Server-Side Request Forgery", "Unvalidated Redirects and Forwards", "HTTP Response Splitting", "HTTP Parameter Pollution", "Insecure Cryptographic Storage", "Insecure Randomness", "Insecure Authentication", "Insecure Password Storage", "Insecure Password Reset", "Insecure Session Management", "Insecure Direct Object Reference", "Insecure Deserialization", "LDAP Injection", "XML External Entity Injection", "Server-Side Request Forgery", "Unvalidated Redirects and Forwards", "HTTP Response Splitting", "HTTP Parameter Pollution", "Insecure Cryptographic Storage", "Insecure Randomness", "Insecure Authentication", "Insecure Password Storage", "Insecure Password Reset", "Insecure Session Management", "Insecure Direct Object Reference", "Insecure Deserialization", "LDAP"));

    @Scheduled(fixedRate = 5000)
    public void generateThreats() {
        String deviceType = deviceTypes.get(getRandomNumber(0, deviceTypes.size() - 1));
        Float potentialImpact = round(potentialImpacts.get(getRandomNumber(0, potentialImpacts.size() - 1)),2);
        String name = names.get(getRandomNumber(0, names.size() - 1));
        String source = sources.get(getRandomNumber(0, sources.size() - 1));
        String severity;
        if(potentialImpact < 0.3)
            severity = severities.get(0);
        else if(potentialImpact < 0.6)
            severity = severities.get(1);
        else if(potentialImpact < 0.9)
            severity = severities.get(2);
        else
            severity = severities.get(3);

        Threat threat = new Threat(name, severity, source, potentialImpact, deviceType);
        threatRepository.save(threat);
    }


    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
