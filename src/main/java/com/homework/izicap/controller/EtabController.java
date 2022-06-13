package com.homework.izicap.controller;

import com.homework.izicap.dto.Data;
import com.homework.izicap.exception.InternalServerErrorException;
import com.homework.izicap.exception.ResourceNotFoundException;
import com.homework.izicap.service.DataService;
import com.homework.izicap.utility.FileUtility;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/etablissement")
@Tag(name = "etablissement", description = "Has Endpoints to fetch business data")
public class EtabController {

    private static final Logger log = LoggerFactory.getLogger(EtabController.class);
    @Autowired
    DataService dataService;

    @Value("${local.storage.dir}")
    private String pathToDir;

    @Operation(summary = "Find business data by siret number", description = "Returns a single business", tags = { "data" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Data.class))),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    @GetMapping("find/siret/{siret}")
    private ResponseEntity<Data> findById(@Parameter(description="Siret number") @PathVariable String siret){

        Data data = dataService.fetchEtablissmentBySiret(siret);
        List<Data> list = new ArrayList<>();

        if(data != null){
            list.add(data);
            try {
                FileUtility.getInstance().writeToCSV(pathToDir, list);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }else{
            throw new ResourceNotFoundException("Record not found");
        }

        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @Operation(summary = "Find all businesses that matches siret number in the list", description = "Returns all businees by siret numbers", tags = { "data" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Data.class)))),
            @ApiResponse(responseCode = "404", description = "No records found")
    })
    @GetMapping(value = {"find/all/siret", "find/all/siret/{ids}"})
    private ResponseEntity<List<Data>> findAll(@Parameter(description="List of siret numbers") @PathVariable(required = false) String[] ids){
        List<Data> list = null;
        String[] sirets = ids;

        try{
            //if no sirets provided, search by sirets defined in data.txt file, otherwise search by the supplied sirets
            if(ids == null){
                log.info("use sirets from sirets.txt file");
                sirets = FileUtility.getInstance().loadSirets();
            }

            list = dataService.fetchbyMultipleSirets(sirets).get();
            if(list.size() == 0){
                throw new ResourceNotFoundException("Resource not found");
            }else{
                // Write data to CSV
                FileUtility.getInstance().writeToCSV(pathToDir,list);
            }

        } catch (IOException | InterruptedException | ExecutionException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}