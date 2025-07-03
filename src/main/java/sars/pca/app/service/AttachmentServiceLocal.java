package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.Attachment;

/**
 *
 * @author S2026987
 */
public interface AttachmentServiceLocal {

    Attachment save(Attachment attachment);

    Attachment findById(Long id);

    Attachment update(Attachment attachment);

    Attachment deleteById(Long id);

    List<Attachment> listAll();
    
    public byte[] download(String objectId);
}
