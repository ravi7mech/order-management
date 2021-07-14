package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdProduct.
 */
@Entity
@Table(name = "ord_product")
public class OrdProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version_id")
    private Long versionId;

    @Column(name = "variation_id")
    private Long variationId;

    @Column(name = "line_of_service")
    private String lineOfService;

    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "serial_no")
    private Long serialNo;

    @Column(name = "name")
    private String name;

    @JsonIgnoreProperties(value = { "ordProduct" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdProductCharacteristics ordProductCharacteristics;

    @OneToMany(mappedBy = "ordProduct")
    @JsonIgnoreProperties(value = { "ordProduct" }, allowSetters = true)
    private Set<OrdPlace> ordPlaces = new HashSet<>();

    @JsonIgnoreProperties(
        value = {
            "ordOrderPrice",
            "ordOrderItemRelationship",
            "ordProductOfferingRef",
            "ordProduct",
            "ordOrderItemProvisioning",
            "ordOrderItemChars",
            "ordProductOrder",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "ordProduct")
    private OrdOrderItem ordOrderItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdProduct id(Long id) {
        this.id = id;
        return this;
    }

    public Long getVersionId() {
        return this.versionId;
    }

    public OrdProduct versionId(Long versionId) {
        this.versionId = versionId;
        return this;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getVariationId() {
        return this.variationId;
    }

    public OrdProduct variationId(Long variationId) {
        this.variationId = variationId;
        return this;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getLineOfService() {
        return this.lineOfService;
    }

    public OrdProduct lineOfService(String lineOfService) {
        this.lineOfService = lineOfService;
        return this;
    }

    public void setLineOfService(String lineOfService) {
        this.lineOfService = lineOfService;
    }

    public Long getAssetId() {
        return this.assetId;
    }

    public OrdProduct assetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getSerialNo() {
        return this.serialNo;
    }

    public OrdProduct serialNo(Long serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return this.name;
    }

    public OrdProduct name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrdProductCharacteristics getOrdProductCharacteristics() {
        return this.ordProductCharacteristics;
    }

    public OrdProduct ordProductCharacteristics(OrdProductCharacteristics ordProductCharacteristics) {
        this.setOrdProductCharacteristics(ordProductCharacteristics);
        return this;
    }

    public void setOrdProductCharacteristics(OrdProductCharacteristics ordProductCharacteristics) {
        this.ordProductCharacteristics = ordProductCharacteristics;
    }

    public Set<OrdPlace> getOrdPlaces() {
        return this.ordPlaces;
    }

    public OrdProduct ordPlaces(Set<OrdPlace> ordPlaces) {
        this.setOrdPlaces(ordPlaces);
        return this;
    }

    public OrdProduct addOrdPlace(OrdPlace ordPlace) {
        this.ordPlaces.add(ordPlace);
        ordPlace.setOrdProduct(this);
        return this;
    }

    public OrdProduct removeOrdPlace(OrdPlace ordPlace) {
        this.ordPlaces.remove(ordPlace);
        ordPlace.setOrdProduct(null);
        return this;
    }

    public void setOrdPlaces(Set<OrdPlace> ordPlaces) {
        if (this.ordPlaces != null) {
            this.ordPlaces.forEach(i -> i.setOrdProduct(null));
        }
        if (ordPlaces != null) {
            ordPlaces.forEach(i -> i.setOrdProduct(this));
        }
        this.ordPlaces = ordPlaces;
    }

    public OrdOrderItem getOrdOrderItem() {
        return this.ordOrderItem;
    }

    public OrdProduct ordOrderItem(OrdOrderItem ordOrderItem) {
        this.setOrdOrderItem(ordOrderItem);
        return this;
    }

    public void setOrdOrderItem(OrdOrderItem ordOrderItem) {
        if (this.ordOrderItem != null) {
            this.ordOrderItem.setOrdProduct(null);
        }
        if (ordOrderItem != null) {
            ordOrderItem.setOrdProduct(this);
        }
        this.ordOrderItem = ordOrderItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProduct)) {
            return false;
        }
        return id != null && id.equals(((OrdProduct) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProduct{" +
            "id=" + getId() +
            ", versionId=" + getVersionId() +
            ", variationId=" + getVariationId() +
            ", lineOfService='" + getLineOfService() + "'" +
            ", assetId=" + getAssetId() +
            ", serialNo=" + getSerialNo() +
            ", name='" + getName() + "'" +
            "}";
    }
}
