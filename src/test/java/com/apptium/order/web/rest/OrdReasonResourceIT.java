package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.domain.OrdReason;
import com.apptium.order.repository.OrdReasonRepository;
import com.apptium.order.service.criteria.OrdReasonCriteria;
import com.apptium.order.service.dto.OrdReasonDTO;
import com.apptium.order.service.mapper.OrdReasonMapper;
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
 * Integration tests for the {@link OrdReasonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdReasonResourceIT {

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-reasons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdReasonRepository ordReasonRepository;

    @Autowired
    private OrdReasonMapper ordReasonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdReasonMockMvc;

    private OrdReason ordReason;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdReason createEntity(EntityManager em) {
        OrdReason ordReason = new OrdReason().reason(DEFAULT_REASON).description(DEFAULT_DESCRIPTION);
        return ordReason;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdReason createUpdatedEntity(EntityManager em) {
        OrdReason ordReason = new OrdReason().reason(UPDATED_REASON).description(UPDATED_DESCRIPTION);
        return ordReason;
    }

    @BeforeEach
    public void initTest() {
        ordReason = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdReason() throws Exception {
        int databaseSizeBeforeCreate = ordReasonRepository.findAll().size();
        // Create the OrdReason
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);
        restOrdReasonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordReasonDTO)))
            .andExpect(status().isCreated());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeCreate + 1);
        OrdReason testOrdReason = ordReasonList.get(ordReasonList.size() - 1);
        assertThat(testOrdReason.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testOrdReason.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createOrdReasonWithExistingId() throws Exception {
        // Create the OrdReason with an existing ID
        ordReason.setId(1L);
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);

        int databaseSizeBeforeCreate = ordReasonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdReasonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordReasonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdReasons() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList
        restOrdReasonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordReason.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getOrdReason() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get the ordReason
        restOrdReasonMockMvc
            .perform(get(ENTITY_API_URL_ID, ordReason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordReason.getId().intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getOrdReasonsByIdFiltering() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        Long id = ordReason.getId();

        defaultOrdReasonShouldBeFound("id.equals=" + id);
        defaultOrdReasonShouldNotBeFound("id.notEquals=" + id);

        defaultOrdReasonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdReasonShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdReasonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdReasonShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where reason equals to DEFAULT_REASON
        defaultOrdReasonShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the ordReasonList where reason equals to UPDATED_REASON
        defaultOrdReasonShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where reason not equals to DEFAULT_REASON
        defaultOrdReasonShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the ordReasonList where reason not equals to UPDATED_REASON
        defaultOrdReasonShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultOrdReasonShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the ordReasonList where reason equals to UPDATED_REASON
        defaultOrdReasonShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where reason is not null
        defaultOrdReasonShouldBeFound("reason.specified=true");

        // Get all the ordReasonList where reason is null
        defaultOrdReasonShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdReasonsByReasonContainsSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where reason contains DEFAULT_REASON
        defaultOrdReasonShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the ordReasonList where reason contains UPDATED_REASON
        defaultOrdReasonShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where reason does not contain DEFAULT_REASON
        defaultOrdReasonShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the ordReasonList where reason does not contain UPDATED_REASON
        defaultOrdReasonShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where description equals to DEFAULT_DESCRIPTION
        defaultOrdReasonShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ordReasonList where description equals to UPDATED_DESCRIPTION
        defaultOrdReasonShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where description not equals to DEFAULT_DESCRIPTION
        defaultOrdReasonShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the ordReasonList where description not equals to UPDATED_DESCRIPTION
        defaultOrdReasonShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOrdReasonShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ordReasonList where description equals to UPDATED_DESCRIPTION
        defaultOrdReasonShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where description is not null
        defaultOrdReasonShouldBeFound("description.specified=true");

        // Get all the ordReasonList where description is null
        defaultOrdReasonShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdReasonsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where description contains DEFAULT_DESCRIPTION
        defaultOrdReasonShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ordReasonList where description contains UPDATED_DESCRIPTION
        defaultOrdReasonShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        // Get all the ordReasonList where description does not contain DEFAULT_DESCRIPTION
        defaultOrdReasonShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ordReasonList where description does not contain UPDATED_DESCRIPTION
        defaultOrdReasonShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdReasonsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordReason.setOrdProductOrder(ordProductOrder);
        ordReasonRepository.saveAndFlush(ordReason);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordReasonList where ordProductOrder equals to ordProductOrderId
        defaultOrdReasonShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordReasonList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdReasonShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdReasonShouldBeFound(String filter) throws Exception {
        restOrdReasonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordReason.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restOrdReasonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdReasonShouldNotBeFound(String filter) throws Exception {
        restOrdReasonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdReasonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdReason() throws Exception {
        // Get the ordReason
        restOrdReasonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdReason() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();

        // Update the ordReason
        OrdReason updatedOrdReason = ordReasonRepository.findById(ordReason.getId()).get();
        // Disconnect from session so that the updates on updatedOrdReason are not directly saved in db
        em.detach(updatedOrdReason);
        updatedOrdReason.reason(UPDATED_REASON).description(UPDATED_DESCRIPTION);
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(updatedOrdReason);

        restOrdReasonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordReasonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordReasonDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
        OrdReason testOrdReason = ordReasonList.get(ordReasonList.size() - 1);
        assertThat(testOrdReason.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testOrdReason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingOrdReason() throws Exception {
        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();
        ordReason.setId(count.incrementAndGet());

        // Create the OrdReason
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdReasonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordReasonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordReasonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdReason() throws Exception {
        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();
        ordReason.setId(count.incrementAndGet());

        // Create the OrdReason
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdReasonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordReasonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdReason() throws Exception {
        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();
        ordReason.setId(count.incrementAndGet());

        // Create the OrdReason
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdReasonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordReasonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdReasonWithPatch() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();

        // Update the ordReason using partial update
        OrdReason partialUpdatedOrdReason = new OrdReason();
        partialUpdatedOrdReason.setId(ordReason.getId());

        partialUpdatedOrdReason.reason(UPDATED_REASON).description(UPDATED_DESCRIPTION);

        restOrdReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdReason.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdReason))
            )
            .andExpect(status().isOk());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
        OrdReason testOrdReason = ordReasonList.get(ordReasonList.size() - 1);
        assertThat(testOrdReason.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testOrdReason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateOrdReasonWithPatch() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();

        // Update the ordReason using partial update
        OrdReason partialUpdatedOrdReason = new OrdReason();
        partialUpdatedOrdReason.setId(ordReason.getId());

        partialUpdatedOrdReason.reason(UPDATED_REASON).description(UPDATED_DESCRIPTION);

        restOrdReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdReason.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdReason))
            )
            .andExpect(status().isOk());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
        OrdReason testOrdReason = ordReasonList.get(ordReasonList.size() - 1);
        assertThat(testOrdReason.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testOrdReason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingOrdReason() throws Exception {
        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();
        ordReason.setId(count.incrementAndGet());

        // Create the OrdReason
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordReasonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordReasonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdReason() throws Exception {
        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();
        ordReason.setId(count.incrementAndGet());

        // Create the OrdReason
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordReasonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdReason() throws Exception {
        int databaseSizeBeforeUpdate = ordReasonRepository.findAll().size();
        ordReason.setId(count.incrementAndGet());

        // Create the OrdReason
        OrdReasonDTO ordReasonDTO = ordReasonMapper.toDto(ordReason);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdReasonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordReasonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdReason in the database
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdReason() throws Exception {
        // Initialize the database
        ordReasonRepository.saveAndFlush(ordReason);

        int databaseSizeBeforeDelete = ordReasonRepository.findAll().size();

        // Delete the ordReason
        restOrdReasonMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordReason.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdReason> ordReasonList = ordReasonRepository.findAll();
        assertThat(ordReasonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
