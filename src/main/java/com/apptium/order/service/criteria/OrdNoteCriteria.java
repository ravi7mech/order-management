package com.apptium.order.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.order.domain.OrdNote} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdNoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdNoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter author;

    private StringFilter text;

    private InstantFilter createdDate;

    private LongFilter ordProductOrderId;

    public OrdNoteCriteria() {}

    public OrdNoteCriteria(OrdNoteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdNoteCriteria copy() {
        return new OrdNoteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAuthor() {
        return author;
    }

    public StringFilter author() {
        if (author == null) {
            author = new StringFilter();
        }
        return author;
    }

    public void setAuthor(StringFilter author) {
        this.author = author;
    }

    public StringFilter getText() {
        return text;
    }

    public StringFilter text() {
        if (text == null) {
            text = new StringFilter();
        }
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getOrdProductOrderId() {
        return ordProductOrderId;
    }

    public LongFilter ordProductOrderId() {
        if (ordProductOrderId == null) {
            ordProductOrderId = new LongFilter();
        }
        return ordProductOrderId;
    }

    public void setOrdProductOrderId(LongFilter ordProductOrderId) {
        this.ordProductOrderId = ordProductOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdNoteCriteria that = (OrdNoteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(author, that.author) &&
            Objects.equals(text, that.text) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, text, createdDate, ordProductOrderId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdNoteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (author != null ? "author=" + author + ", " : "") +
            (text != null ? "text=" + text + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}
