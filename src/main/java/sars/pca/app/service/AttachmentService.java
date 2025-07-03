package sars.pca.app.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.Attachment;
import sars.pca.app.persistence.AttachmentRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class AttachmentService implements AttachmentServiceLocal {

    @Autowired
    private AttachmentRepository attachmentRepository;

    private final String DOWNLOAD_DOCUMENT_URL = "http://10.30.5.217:9082/sars_pca/rest/sarsdocument/properties/{0}";

    @Override
    public Attachment save(Attachment attachment) {
        return attachmentRepository.saveAndFlush(attachment);
    }

    @Override
    public Attachment findById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public Attachment update(Attachment attachment) {
        return attachmentRepository.saveAndFlush(attachment);
    }

    @Override
    public Attachment deleteById(Long id) {
        Attachment attachment = findById(id);

        if (attachment != null) {
            attachmentRepository.delete(attachment);
        }
        return attachment;
    }

    @Override
    public List<Attachment> listAll() {
        return attachmentRepository.findAll();
    }

    @Override
    public byte[] download(String objectId) {
        //Dev URL - Don't try Dev Environment
        //String query_url_post ="http://10.30.5.217:9080/rest/sarsdocument/download/09000384801f11da"; 
        //PreProd URL
        StringBuilder responseBuiler = new StringBuilder();
        String query_url_get = "http://10.30.6.41:9082/pca/rest/sarsdocument/download/" + objectId;
        try {

            URL url = new URL(query_url_get);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String result = "";
            JSONObject myResponse1 = null;
            System.out.println("Output from Server .... \n");

            while ((result = br.readLine()) != null) {
                myResponse1 = new JSONObject(result);
                responseBuiler.append(myResponse1.getString("content"));
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception : " + e.getLocalizedMessage());
        }
        return responseBuiler.toString().getBytes();
    }

}
