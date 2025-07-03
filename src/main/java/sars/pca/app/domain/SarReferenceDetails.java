/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sars_ref_details")
@Getter
@Setter
public class SarReferenceDetails extends BaseEntity {
    @Column(name = "vat", length = 10)
    private String vat;

    @Column(name = "company_number", length = 14)
    private String companyNumber;

    @Column(name = "income_tax", length = 10)
    private String incomeTax;

    @Column(name = "custom_excise_code", length = 8)
    private String customExciseCode;
}
