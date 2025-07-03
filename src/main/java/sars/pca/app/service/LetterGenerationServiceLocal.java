/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.pca.app.service;

import sars.pca.app.domain.Letter;
import sars.pca.app.gen.dto.res.PDFDocumentGenerationManagementResponse;


/**
 *
 * @author S2028398
 */
public interface LetterGenerationServiceLocal {
    PDFDocumentGenerationManagementResponse generateLetterDocument(Letter letter);
    public void downloadGeneratedDocument(String base64String);
}
