/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.pca.app.soapclient;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import sars.pca.app.gen.GeneratePdf;
import sars.pca.app.gen.GeneratePdfResponse;

/**
 *
 * @author S2026987
 */
@Component
public class PdfWebServiceClient extends WebServiceGatewaySupport {

    public GeneratePdfResponse getPdfDocument(String requestString, String code, String soapAction) {
        GeneratePdf request = new GeneratePdf();
        request.setGenerateRequest(requestString);
        request.setCode(code);

        GeneratePdfResponse response = (GeneratePdfResponse) getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback(soapAction));
        //PDFDocumentGenerationManagementResponse response = (PDFDocumentGenerationManagementResponse) getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback(soapAction));
        return response;
    }
}
