package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A OrdProvisiongChar.
 */
@Entity
@Table(name = "ord_provisiong_char")
public class OrdProvisiongChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ordProvisiongChars", "ordOrderItem" }, allowSetters = true)
    private OrdOrderItemProvisioning ordOrderItemProvisioning;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdProvisiongChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OrdProvisiongChar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public OrdProvisiongChar value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public OrdProvisiongChar valueType(String valueType) {
        this.valueType = valueType;
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OrdProvisiongChar createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OrdProvisiongChar createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public OrdOrderItemProvisioning getOrdOrderItemProvisioning() {
        return this.ordOrderItemProvisioning;
    }

    public OrdProvisiongChar ordOrderItemProvisioning(OrdOrderItemProvisioning ordOrderItemProvisioning) {
        this.setOrdOrderItemProvisioning(ordOrderItemProvisioning);
        return this;
    }

    public void setOrdOrderItemProvisioning(OrdOrderItemProvisioning ordOrderItemProvisioning) {
        this.ordOrderItemProvisioning = ordOrderItemProvisioning;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProvisiongChar)) {
            return false;
        }
        return id != null && id.equals(((OrdProvisiongChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProvisiongChar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
