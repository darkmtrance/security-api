package com.matomaylla.security.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matomaylla.security.config.LoggingInterceptor;
import com.matomaylla.security.model.event.EventRequest;
import com.matomaylla.security.model.externalApi.ExternalDataResponse;
import com.matomaylla.security.model.externalApi.ExternalParentResponse;
import com.matomaylla.security.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

@RequiredArgsConstructor
@Service
public class ExternalApiCaller {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiCaller.class);
    public static final String API_LOGIN_INGRESAR = "https://martinadan.sieweb.com.pe/lms/api/login/Ingresar";

    private final EventService service;

    @Value("${sieweb.user}")
    private String siewebUser;

    @Value("${sieweb.pass}")
    private String siewebPass;

    @Value("${codigoUsuario1}")
    private String codigoUsuario1;

    @Value("${codigoUsuario2}")
    private String codigoUsuario2;

    public void callExternalApi() throws JsonProcessingException {
        logger.info("ingreso a callExternalApi");
        RestTemplate restTemplate = new RestTemplate();

        // Configurar las cabeceras, si es necesario
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String apiUrl = API_LOGIN_INGRESAR;
        String requestBody = "{\"user\": \""+siewebUser+"\",\"pass\": \""+siewebPass+"\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Realizar la llamada POST a la API externa
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);



        // Manejar la respuesta
        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String token = jsonNode.get("json").get("token").textValue();
            logger.info(token);
            // Procesar la respuesta
            obtenerInformacion(token);
        } else {
            // Manejar el error
            logger.info("error");
        }
    }

    private void obtenerInformacion(String token) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new LoggingInterceptor());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Agregar otras cabeceras si es necesario
        headers.add("Sie-Token", token);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String yearString = String.valueOf(year);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        String apiUrl = "https://martinadan.sieweb.com.pe/lms/api/HyoAgenda/obtTodo?idTipo=1&idAgenda=&ano="+yearString+"&mes="+month+"&codigos[]="+codigoUsuario1+"&codigos[]="+codigoUsuario2;

            // Realizar la solicitud GET y obtener la respuesta
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            // Verificar el código de estado de la respuesta
            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                ExternalParentResponse externalParentResponse = objectMapper.readValue(response.getBody(), ExternalParentResponse.class);

                if(!externalParentResponse.getJson().isEmpty()){
                    externalParentResponse.getJson().forEach(data -> validarData(data));
                }
                logger.info("Respuesta: " + externalParentResponse);
            } else {
                // La solicitud falló, imprimir el código de estado
                logger.info("La solicitud falló con el código de estado: " + response.getStatusCodeValue());
            }




    }

    private void validarData(ExternalDataResponse data){

        if(!service.existeIdSieweb(data.getID())){
            //grabar
            EventRequest event = EventRequest.builder().start(data.getFECINI()).end(data.getFECFIN()).title(data.getTITULO()).usuNomPublicacion(data.getUSUNOM()).backgroundColor(data.getCOLOR()).idSieweb(data.getID()).build();
            service.save(event);
        }



    }


}
