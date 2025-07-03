package sars.pca.app.service;

import sars.pca.app.domain.User;

/**
 *
 * @author S2026987
 */
public interface EmployeeInformationServiceLocal {
    
    public User getEmployeeUserBySid(String sid, String userSid);
    
}
