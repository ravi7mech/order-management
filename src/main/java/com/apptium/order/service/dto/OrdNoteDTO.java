package com.apptium.order.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdNote} entity.
 */
public class OrdNoteDTO implements Serializable {

    private Long id;

    private String author;

    private String text;

    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdNoteDTO)) {
            return false;
        }

        OrdNoteDTO ordNoteDTO = (OrdNoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordNoteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdNoteDTO{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", text='" + getText() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
