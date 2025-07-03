package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.YesNoEnum;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "seizure_and_lien")
@Getter
@Setter
public class SeizureAndLien extends BaseEntity {

    @Column(name = "total_rand_value_per_section_93")
    private double totalRandValuePerSection93;

    @Column(name = "was_goods_seizure_and_detained")
    @Enumerated(EnumType.STRING)
    private YesNoEnum seizureAndDetained;

    @Column(name = "was_there_lien_on_goods")
    @Enumerated(EnumType.STRING)
    private YesNoEnum wasLienOnGood;

    @Column(name = "did_client_request_goods_back")
    @Enumerated(EnumType.STRING)
    private YesNoEnum clientRequestGoodsBack;

    public SeizureAndLien() {
        this.totalRandValuePerSection93 = 0.00;
        this.seizureAndDetained = YesNoEnum.NO;
        this.wasLienOnGood = YesNoEnum.NO;
        this.clientRequestGoodsBack = YesNoEnum.NO;
    }

}
