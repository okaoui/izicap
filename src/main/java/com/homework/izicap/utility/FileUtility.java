package com.homework.izicap.utility;

import com.homework.izicap.dto.Data;
import com.homework.izicap.dto.Etablissement;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class FileUtility {

    private static final Logger log = LoggerFactory.getLogger(FileUtility.class);
    private static FileUtility fileUtility;

    private FileUtility(){
    }

    public static FileUtility getInstance(){
        if(fileUtility == null)
            fileUtility = new FileUtility();

        return fileUtility;
    }
    /**
     * Export businesses data to CSV file
     * @param list
     * @throws IOException
     * @throws CsvRequiredFieldEmptyException
     * @throws CsvDataTypeMismatchException
     */
    public void writeToCSV(String dirPath, List<Data> list) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String filename = new Date().getTime()+".csv";

        FileWriter outputfile = new FileWriter(dirPath+filename);
        CSVWriter writer = new CSVWriter(outputfile);

        // adding header to csv
        String[] header = new String[6];
        header[0] = Constants.COLUMN_TITLE_ID;
        header[1] = Constants.COLUMN_TITLE_NIC;
        header[2] = Constants.COLUMN_TITLE_FULL_ADDRESS;
        header[3] = Constants.COLUMN_TITLE_CREATION_DATE;
        header[4] = Constants.COLUMN_TITLE_FULL_NAME;
        header[5] = Constants.COLUMN_TITLE_TVA_NUMBER;

        writer.writeNext(header);

        // add data to csv

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        for (Data data : list) {
            String[] etabInfo = new String[6];

            Etablissement etab = data.getEtablissement();
            log.info(etab.toString());
            etabInfo[0] = String.valueOf(etab.getId());
            etabInfo[1] = etab.getNic();
            etabInfo[2] = etab.getLibelleCommune().concat(" ").concat(etab.getCodeCommune()).concat(" ").concat(etab.getLibelleVoie());
            etabInfo[3] = (etab.getCreationDate() != null) ? sdf.format(etab.getCreationDate()) : "";
            etabInfo[4] = etab.getUniteLegale().getNom() + " " + etab.getUniteLegale().getPrenomUsuel();
            etabInfo[5] = etab.getNumeroTva();

            writer.writeNext(etabInfo);
        }

        writer.close();
    }

    /**
     * loads sirets from data.txt file
     * @return list of sirets
     * @throws IOException
     */
    public String[] loadSirets() throws IOException {
        Resource resource = new ClassPathResource("classpath:sirets.txt");
        InputStream inputStream = null;
        String[] sirets;

        inputStream = resource.getInputStream();
        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
        String data = new String(bdata, StandardCharsets.UTF_8);
        sirets = data.split("\n");

        return sirets;
    }

}
