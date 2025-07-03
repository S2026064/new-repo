package sars.pca.app.service;

import java.util.List;

/**
 *
 * @author S2024726
 */
public interface MailServiceLocal {
    public boolean send(List<String> destinationAddress, String subject, String message);
    public boolean send(List<String> destinationAddress, String subject, String message, String attachmentPath);
}
