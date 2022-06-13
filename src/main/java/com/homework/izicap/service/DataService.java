package com.homework.izicap.service;

import com.homework.izicap.dto.Data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DataService {

    Data fetchEtablissmentBySiret(String siret);
    CompletableFuture<List<Data>> fetchbyMultipleSirets(String[] sirets);
}
