package edu.mum.ea.socialnetwork.services;

import edu.mum.ea.socialnetwork.config.CaptchaSettings;
import edu.mum.ea.socialnetwork.util.RecaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CaptchaService {
    @Autowired
    private CaptchaSettings captchaSettings;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public String verifyRecaptcha(String ip,
                                  String recaptchaResponse){
        Map<String, String> body = new HashMap<>();
        body.put("secret", captchaSettings.getSecret());
        body.put("response", recaptchaResponse);
        body.put("remoteip", ip);
        ResponseEntity<Map> recaptchaResponseEntity =
                restTemplateBuilder.build()
                        .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL+
                                        "?secret={secret}&response={response}&remoteip={remoteip}",
                                body, Map.class, body);


        Map<String, Object> responseBody =
                recaptchaResponseEntity.getBody();

        boolean recaptchaSucess = (Boolean)responseBody.get("success");
        if ( !recaptchaSucess) {
            List<String> errorCodes =
                    (List)responseBody.get("error-codes");

            String errorMessage = errorCodes.stream()
                    .map(s -> RecaptchaUtil.RECAPTCHA_ERROR_CODE.get(s))
                    .collect(Collectors.joining(", "));

            return errorMessage;
        }else {
            return "";
        }
    }
}
