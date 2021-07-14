package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdNote;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdNoteRepository;
import com.apptium.order.service.criteria.OrdNoteCriteria;
import com.apptium.order.service.dto.OrdNoteDTO;
import com.apptium.order.service.mapper.OrdNoteMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrdNoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdNoteResourceIT {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ord-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdNoteRepository ordNoteRepository;

    @Autowired
    private OrdNoteMapper ordNoteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdNoteMockMvc;

    private OrdNote ordNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdNote createEntity(EntityManager em) {
        OrdNote ordNote = new OrdNote().author(DEFAULT_AUTHOR).text(DEFAULT_TEXT).createdDate(DEFAULT_CREATED_DATE);
        return ordNote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdNote createUpdatedEntity(EntityManager em) {
        OrdNote ordNote = new OrdNote().author(UPDATED_AUTHOR).text(UPDATED_TEXT).createdDate(UPDATED_CREATED_DATE);
        return ordNote;
    }

    @BeforeEach
    public void initTest() {
        ordNote = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdNote() throws Exception {
        int databaseSizeBeforeCreate = ordNoteRepository.findAll().size();
        // Create the OrdNote
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);
        restOrdNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordNoteDTO)))
            .andExpect(status().isCreated());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeCreate + 1);
        OrdNote testOrdNote = ordNoteList.get(ordNoteList.size() - 1);
        assertThat(testOrdNote.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testOrdNote.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testOrdNote.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createOrdNoteWithExistingId() throws Exception {
        // Create the OrdNote with an existing ID
        ordNote.setId(1L);
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);

        int databaseSizeBeforeCreate = ordNoteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordNoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdNotes() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList
        restOrdNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrdNote() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get the ordNote
        restOrdNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, ordNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordNote.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOrdNotesByIdFiltering() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        Long id = ordNote.getId();

        defaultOrdNoteShouldBeFound("id.equals=" + id);
        defaultOrdNoteShouldNotBeFound("id.notEquals=" + id);

        defaultOrdNoteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdNoteShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdNoteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdNoteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdNotesByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where author equals to DEFAULT_AUTHOR
        defaultOrdNoteShouldBeFound("author.equals=" + DEFAULT_AUTHOR);

        // Get all the ordNoteList where author equals to UPDATED_AUTHOR
        defaultOrdNoteShouldNotBeFound("author.equals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllOrdNotesByAuthorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where author not equals to DEFAULT_AUTHOR
        defaultOrdNoteShouldNotBeFound("author.notEquals=" + DEFAULT_AUTHOR);

        // Get all the ordNoteList where author not equals to UPDATED_AUTHOR
        defaultOrdNoteShouldBeFound("author.notEquals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllOrdNotesByAuthorIsInShouldWork() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where author in DEFAULT_AUTHOR or UPDATED_AUTHOR
        defaultOrdNoteShouldBeFound("author.in=" + DEFAULT_AUTHOR + "," + UPDATED_AUTHOR);

        // Get all the ordNoteList where author equals to UPDATED_AUTHOR
        defaultOrdNoteShouldNotBeFound("author.in=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllOrdNotesByAuthorIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where author is not null
        defaultOrdNoteShouldBeFound("author.specified=true");

        // Get all the ordNoteList where author is null
        defaultOrdNoteShouldNotBeFound("author.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdNotesByAuthorContainsSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where author contains DEFAULT_AUTHOR
        defaultOrdNoteShouldBeFound("author.contains=" + DEFAULT_AUTHOR);

        // Get all the ordNoteList where author contains UPDATED_AUTHOR
        defaultOrdNoteShouldNotBeFound("author.contains=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllOrdNotesByAuthorNotContainsSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where author does not contain DEFAULT_AUTHOR
        defaultOrdNoteShouldNotBeFound("author.doesNotContain=" + DEFAULT_AUTHOR);

        // Get all the ordNoteList where author does not contain UPDATED_AUTHOR
        defaultOrdNoteShouldBeFound("author.doesNotContain=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllOrdNotesByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where text equals to DEFAULT_TEXT
        defaultOrdNoteShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the ordNoteList where text equals to UPDATED_TEXT
        defaultOrdNoteShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOrdNotesByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where text not equals to DEFAULT_TEXT
        defaultOrdNoteShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the ordNoteList where text not equals to UPDATED_TEXT
        defaultOrdNoteShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOrdNotesByTextIsInShouldWork() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultOrdNoteShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the ordNoteList where text equals to UPDATED_TEXT
        defaultOrdNoteShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOrdNotesByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where text is not null
        defaultOrdNoteShouldBeFound("text.specified=true");

        // Get all the ordNoteList where text is null
        defaultOrdNoteShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdNotesByTextContainsSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where text contains DEFAULT_TEXT
        defaultOrdNoteShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the ordNoteList where text contains UPDATED_TEXT
        defaultOrdNoteShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOrdNotesByTextNotContainsSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where text does not contain DEFAULT_TEXT
        defaultOrdNoteShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the ordNoteList where text does not contain UPDATED_TEXT
        defaultOrdNoteShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOrdNotesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOrdNoteShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the ordNoteList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdNoteShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdNotesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOrdNoteShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the ordNoteList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOrdNoteShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdNotesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOrdNoteShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the ordNoteList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdNoteShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdNotesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        // Get all the ordNoteList where createdDate is not null
        defaultOrdNoteShouldBeFound("createdDate.specified=true");

        // Get all the ordNoteList where createdDate is null
        defaultOrdNoteShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdNotesByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordNote.setOrdProductOrder(ordProductOrder);
        ordProductOrder.setOrdNote(ordNote);
        ordNoteRepository.saveAndFlush(ordNote);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordNoteList where ordProductOrder equals to ordProductOrderId
        defaultOrdNoteShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordNoteList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdNoteShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdNoteShouldBeFound(String filter) throws Exception {
        restOrdNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restOrdNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdNoteShouldNotBeFound(String filter) throws Exception {
        restOrdNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdNote() throws Exception {
        // Get the ordNote
        restOrdNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdNote() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();

        // Update the ordNote
        OrdNote updatedOrdNote = ordNoteRepository.findById(ordNote.getId()).get();
        // Disconnect from session so that the updates on updatedOrdNote are not directly saved in db
        em.detach(updatedOrdNote);
        updatedOrdNote.author(UPDATED_AUTHOR).text(UPDATED_TEXT).createdDate(UPDATED_CREATED_DATE);
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(updatedOrdNote);

        restOrdNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordNoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordNoteDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
        OrdNote testOrdNote = ordNoteList.get(ordNoteList.size() - 1);
        assertThat(testOrdNote.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testOrdNote.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testOrdNote.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrdNote() throws Exception {
        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();
        ordNote.setId(count.incrementAndGet());

        // Create the OrdNote
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordNoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdNote() throws Exception {
        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();
        ordNote.setId(count.incrementAndGet());

        // Create the OrdNote
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdNote() throws Exception {
        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();
        ordNote.setId(count.incrementAndGet());

        // Create the OrdNote
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdNoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordNoteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdNoteWithPatch() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();

        // Update the ordNote using partial update
        OrdNote partialUpdatedOrdNote = new OrdNote();
        partialUpdatedOrdNote.setId(ordNote.getId());

        partialUpdatedOrdNote.text(UPDATED_TEXT).createdDate(UPDATED_CREATED_DATE);

        restOrdNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdNote))
            )
            .andExpect(status().isOk());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
        OrdNote testOrdNote = ordNoteList.get(ordNoteList.size() - 1);
        assertThat(testOrdNote.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testOrdNote.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testOrdNote.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrdNoteWithPatch() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();

        // Update the ordNote using partial update
        OrdNote partialUpdatedOrdNote = new OrdNote();
        partialUpdatedOrdNote.setId(ordNote.getId());

        partialUpdatedOrdNote.author(UPDATED_AUTHOR).text(UPDATED_TEXT).createdDate(UPDATED_CREATED_DATE);

        restOrdNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdNote))
            )
            .andExpect(status().isOk());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
        OrdNote testOrdNote = ordNoteList.get(ordNoteList.size() - 1);
        assertThat(testOrdNote.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testOrdNote.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testOrdNote.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdNote() throws Exception {
        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();
        ordNote.setId(count.incrementAndGet());

        // Create the OrdNote
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordNoteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdNote() throws Exception {
        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();
        ordNote.setId(count.incrementAndGet());

        // Create the OrdNote
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdNote() throws Exception {
        int databaseSizeBeforeUpdate = ordNoteRepository.findAll().size();
        ordNote.setId(count.incrementAndGet());

        // Create the OrdNote
        OrdNoteDTO ordNoteDTO = ordNoteMapper.toDto(ordNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdNoteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordNoteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdNote in the database
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdNote() throws Exception {
        // Initialize the database
        ordNoteRepository.saveAndFlush(ordNote);

        int databaseSizeBeforeDelete = ordNoteRepository.findAll().size();

        // Delete the ordNote
        restOrdNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordNote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdNote> ordNoteList = ordNoteRepository.findAll();
        assertThat(ordNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
