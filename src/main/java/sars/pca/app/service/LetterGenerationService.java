/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.pca.app.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sars.pca.app.domain.Letter;

import sars.pca.app.gen.GeneratePdfResponse;
import sars.pca.app.gen.dto.res.PDFDocumentGenerationManagementResponse;

import sars.pca.app.soapclient.PdfWebServiceClient;
//import sun.misc.BASE64Decoder;

/**
 *
 * @author S2028398
 */
@Service
public class LetterGenerationService implements LetterGenerationServiceLocal {

    @Autowired
    private PdfWebServiceClient pdfWebServiceClient;

    private EnhancedLetterGenerationService enhancedLetterGenerationService = new EnhancedLetterGenerationService();

    private static final String CODE = "W1RITl+MiZA3JDPhUOYDtA==";

    @Override
    public PDFDocumentGenerationManagementResponse generateLetterDocument(Letter letter) {

        try {

            String file = StringUtils.toEncodedString(enhancedLetterGenerationService.constructMsg(letter).getBytes(), StandardCharsets.UTF_8);
            GeneratePdfResponse response = pdfWebServiceClient.getPdfDocument(file, CODE, "http://tempuri.org/GeneratePdf");
            String encodedReponse = StringUtils.toEncodedString(response.getGeneratePdfResult().getBytes(), StandardCharsets.UTF_8);
            StringReader reader = new StringReader(encodedReponse);
            JAXBContext objectContext = JAXBContext.newInstance(PDFDocumentGenerationManagementResponse.class);
            Unmarshaller unmarshaller = objectContext.createUnmarshaller();
            return (PDFDocumentGenerationManagementResponse) unmarshaller.unmarshal(reader);

        } catch (JAXBException ex) {
            Logger.getLogger(LetterGenerationService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LetterGenerationService.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            JAXBContext context = JAXBContext.newInstance(PDFDocumentGenerationManagementRequest.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
//            StringWriter stringWriter = new StringWriter();
//            marshaller.marshal(letterGenerationUtil.constructOfLetterXmlObject(letter), stringWriter);
//
//            String file = StringUtils.toEncodedString(stringWriter.toString().getBytes(), StandardCharsets.UTF_8);
//            GeneratePdfResponse response = pdfWebServiceClient.getPdfDocument(file, CODE, "http://tempuri.org/GeneratePdf");
//            String encodedReponse = StringUtils.toEncodedString(response.getGeneratePdfResult().getBytes(), StandardCharsets.UTF_8);
//            StringReader reader = new StringReader(encodedReponse);
//            JAXBContext objectContext = JAXBContext.newInstance(PDFDocumentGenerationManagementResponse.class);
//            Unmarshaller unmarshaller = objectContext.createUnmarshaller();
//            return (PDFDocumentGenerationManagementResponse) unmarshaller.unmarshal(reader);
//        } catch (JAXBException ex) {
//            Logger.getLogger(LetterGenerationUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
    }

    @Override
    public void downloadGeneratedDocument(String base64String) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./document.txt"))) {
//                    writer.flush();
            writer.write(base64String);
        } catch (IOException ex) {
            Logger.getLogger(LetterGenerationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
