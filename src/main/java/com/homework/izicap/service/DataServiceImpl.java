package com.homework.izicap.service;

import com.homework.izicap.dto.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class DataServiceImpl implements DataService{

    private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${url.api-siret}")
    private String siretApiUrl;

    @Override
    public Data fetchEtablissmentBySiret(String siret){
        Map<String, String> params = new HashMap<>();
        params.put("siret", siret);
        Data data = restTemplate.getForObject(siretApiUrl, Data.class,params);
        return data;
    }

    @Override
    @Async
    public CompletableFuture<List<Data>> fetchbyMultipleSirets(String[] sirets) {
        List<Data> list = new ArrayList<>();

        for(String siret : sirets){
            Map<String, String> params = new HashMap<>();
            log.info(siret);
            params.put("siret", siret.trim());
            Data data = null;
            try{
                data = restTemplate.getForObject(siretApiUrl, Data.class,params);
            }catch(Exception e){
                log.warn(e.getMessage());
            }

            if(data != null){
                list.add(data);
            }

        }
        return CompletableFuture.completedFuture(list);
    }
}
