/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.pca.app.soapclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


/**
 *
 * @author S2026987
 */
@Configuration
public class PdfWebServiceClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("sars.pca.app.gen");
        return marshaller;
    }

    @Bean
    public PdfWebServiceClient pdfWebServiceClient(Jaxb2Marshaller marshaller) {
        PdfWebServiceClient pdfWebServiceClient = new PdfWebServiceClient();
        pdfWebServiceClient.setDefaultUri("http://ptadviis11:90/pdfwebservice/service1.asmx");
        pdfWebServiceClient.setMarshaller(marshaller);
        pdfWebServiceClient.setUnmarshaller(marshaller);
        return pdfWebServiceClient;
    }
}
