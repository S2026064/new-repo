package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "customs_excise_client_type")
@Getter
@Setter
public class CustomsExciseClientType extends BaseEntity {

    @Column(name = "exporter")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean exporter;
    
    @Column(name = "manufacturer")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean manufacturer;
    
    @Column(name = "licensee")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean licensee;
    
    @Column(name = "importer")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean importer;
    
    @Column(name = "registrant")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean registrant;
    
    @Column(name = "ware_house")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean warehouse;
    
    @Column(name = "depot")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean depot;
    
    @Column(name = "terminal")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean terminal;
    
    @Column(name = "agent")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean agent;
    
    @Column(name = "remover")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean remover;
    
    @Column(name = "other")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean other;
    
    @Column(name = "other_des")
    private String otherDescription;

    public CustomsExciseClientType(boolean exporter, boolean manufacturer, boolean licensee, boolean importer, boolean registrant, boolean warehouse, boolean depot, boolean terminal, boolean agent, boolean remover, boolean other, String otherDescription) {
        this.exporter = exporter;
        this.manufacturer = manufacturer;
        this.licensee = licensee;
        this.importer = importer;
        this.registrant = registrant;
        this.warehouse = warehouse;
        this.depot = depot;
        this.terminal = terminal;
        this.agent = agent;
        this.remover = remover;
        this.other = other;
        this.otherDescription = otherDescription;
    }
    public CustomsExciseClientType() {
    }
}
