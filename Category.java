package za.gov.sars.logbook.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import za.gov.sars.logbook.common.TaxType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "tax_type")
    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "service_level_id", nullable = false)
    private ServiceLevel serviceLevel;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = LogbookActivity.class, mappedBy = "category")
    private List<LogbookActivity> activities = new ArrayList();

    public void addActivities(LogbookActivity activitity) {
        activitity.setCategory(this);
        this.activities.add(activitity);
    }

    public void removeActivities(LogbookActivity activity) {
        this.activities.remove(activity);
    }

}
