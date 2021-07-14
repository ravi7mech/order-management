package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdOrderItemProvisioning;
import com.apptium.order.domain.OrdProvisiongChar;
import com.apptium.order.repository.OrdOrderItemProvisioningRepository;
import com.apptium.order.service.criteria.OrdOrderItemProvisioningCriteria;
import com.apptium.order.service.dto.OrdOrderItemProvisioningDTO;
import com.apptium.order.service.mapper.OrdOrderItemProvisioningMapper;
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
 * Integration tests for the {@link OrdOrderItemProvisioningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdOrderItemProvisioningResourceIT {

    private static final Long DEFAULT_PROVISIONING_ID = 1L;
    private static final Long UPDATED_PROVISIONING_ID = 2L;
    private static final Long SMALLER_PROVISIONING_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-order-item-provisionings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdOrderItemProvisioningRepository ordOrderItemProvisioningRepository;

    @Autowired
    private OrdOrderItemProvisioningMapper ordOrderItemProvisioningMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdOrderItemProvisioningMockMvc;

    private OrdOrderItemProvisioning ordOrderItemProvisioning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItemProvisioning createEntity(EntityManager em) {
        OrdOrderItemProvisioning ordOrderItemProvisioning = new OrdOrderItemProvisioning()
            .provisioningId(DEFAULT_PROVISIONING_ID)
            .status(DEFAULT_STATUS);
        return ordOrderItemProvisioning;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItemProvisioning createUpdatedEntity(EntityManager em) {
        OrdOrderItemProvisioning ordOrderItemProvisioning = new OrdOrderItemProvisioning()
            .provisioningId(UPDATED_PROVISIONING_ID)
            .status(UPDATED_STATUS);
        return ordOrderItemProvisioning;
    }

    @BeforeEach
    public void initTest() {
        ordOrderItemProvisioning = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdOrderItemProvisioning() throws Exception {
        int databaseSizeBeforeCreate = ordOrderItemProvisioningRepository.findAll().size();
        // Create the OrdOrderItemProvisioning
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);
        restOrdOrderItemProvisioningMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeCreate + 1);
        OrdOrderItemProvisioning testOrdOrderItemProvisioning = ordOrderItemProvisioningList.get(ordOrderItemProvisioningList.size() - 1);
        assertThat(testOrdOrderItemProvisioning.getProvisioningId()).isEqualTo(DEFAULT_PROVISIONING_ID);
        assertThat(testOrdOrderItemProvisioning.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createOrdOrderItemProvisioningWithExistingId() throws Exception {
        // Create the OrdOrderItemProvisioning with an existing ID
        ordOrderItemProvisioning.setId(1L);
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);

        int databaseSizeBeforeCreate = ordOrderItemProvisioningRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdOrderItemProvisioningMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisionings() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList
        restOrdOrderItemProvisioningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItemProvisioning.getId().intValue())))
            .andExpect(jsonPath("$.[*].provisioningId").value(hasItem(DEFAULT_PROVISIONING_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getOrdOrderItemProvisioning() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get the ordOrderItemProvisioning
        restOrdOrderItemProvisioningMockMvc
            .perform(get(ENTITY_API_URL_ID, ordOrderItemProvisioning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordOrderItemProvisioning.getId().intValue()))
            .andExpect(jsonPath("$.provisioningId").value(DEFAULT_PROVISIONING_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getOrdOrderItemProvisioningsByIdFiltering() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        Long id = ordOrderItemProvisioning.getId();

        defaultOrdOrderItemProvisioningShouldBeFound("id.equals=" + id);
        defaultOrdOrderItemProvisioningShouldNotBeFound("id.notEquals=" + id);

        defaultOrdOrderItemProvisioningShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdOrderItemProvisioningShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdOrderItemProvisioningShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdOrderItemProvisioningShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId equals to DEFAULT_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.equals=" + DEFAULT_PROVISIONING_ID);

        // Get all the ordOrderItemProvisioningList where provisioningId equals to UPDATED_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.equals=" + UPDATED_PROVISIONING_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId not equals to DEFAULT_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.notEquals=" + DEFAULT_PROVISIONING_ID);

        // Get all the ordOrderItemProvisioningList where provisioningId not equals to UPDATED_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.notEquals=" + UPDATED_PROVISIONING_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId in DEFAULT_PROVISIONING_ID or UPDATED_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.in=" + DEFAULT_PROVISIONING_ID + "," + UPDATED_PROVISIONING_ID);

        // Get all the ordOrderItemProvisioningList where provisioningId equals to UPDATED_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.in=" + UPDATED_PROVISIONING_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId is not null
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.specified=true");

        // Get all the ordOrderItemProvisioningList where provisioningId is null
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId is greater than or equal to DEFAULT_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.greaterThanOrEqual=" + DEFAULT_PROVISIONING_ID);

        // Get all the ordOrderItemProvisioningList where provisioningId is greater than or equal to UPDATED_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.greaterThanOrEqual=" + UPDATED_PROVISIONING_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId is less than or equal to DEFAULT_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.lessThanOrEqual=" + DEFAULT_PROVISIONING_ID);

        // Get all the ordOrderItemProvisioningList where provisioningId is less than or equal to SMALLER_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.lessThanOrEqual=" + SMALLER_PROVISIONING_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId is less than DEFAULT_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.lessThan=" + DEFAULT_PROVISIONING_ID);

        // Get all the ordOrderItemProvisioningList where provisioningId is less than UPDATED_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.lessThan=" + UPDATED_PROVISIONING_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByProvisioningIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where provisioningId is greater than DEFAULT_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldNotBeFound("provisioningId.greaterThan=" + DEFAULT_PROVISIONING_ID);

        // Get all the ordOrderItemProvisioningList where provisioningId is greater than SMALLER_PROVISIONING_ID
        defaultOrdOrderItemProvisioningShouldBeFound("provisioningId.greaterThan=" + SMALLER_PROVISIONING_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where status equals to DEFAULT_STATUS
        defaultOrdOrderItemProvisioningShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ordOrderItemProvisioningList where status equals to UPDATED_STATUS
        defaultOrdOrderItemProvisioningShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where status not equals to DEFAULT_STATUS
        defaultOrdOrderItemProvisioningShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ordOrderItemProvisioningList where status not equals to UPDATED_STATUS
        defaultOrdOrderItemProvisioningShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrdOrderItemProvisioningShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ordOrderItemProvisioningList where status equals to UPDATED_STATUS
        defaultOrdOrderItemProvisioningShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where status is not null
        defaultOrdOrderItemProvisioningShouldBeFound("status.specified=true");

        // Get all the ordOrderItemProvisioningList where status is null
        defaultOrdOrderItemProvisioningShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByStatusContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where status contains DEFAULT_STATUS
        defaultOrdOrderItemProvisioningShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the ordOrderItemProvisioningList where status contains UPDATED_STATUS
        defaultOrdOrderItemProvisioningShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        // Get all the ordOrderItemProvisioningList where status does not contain DEFAULT_STATUS
        defaultOrdOrderItemProvisioningShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the ordOrderItemProvisioningList where status does not contain UPDATED_STATUS
        defaultOrdOrderItemProvisioningShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByOrdProvisiongCharIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);
        OrdProvisiongChar ordProvisiongChar = OrdProvisiongCharResourceIT.createEntity(em);
        em.persist(ordProvisiongChar);
        em.flush();
        ordOrderItemProvisioning.addOrdProvisiongChar(ordProvisiongChar);
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);
        Long ordProvisiongCharId = ordProvisiongChar.getId();

        // Get all the ordOrderItemProvisioningList where ordProvisiongChar equals to ordProvisiongCharId
        defaultOrdOrderItemProvisioningShouldBeFound("ordProvisiongCharId.equals=" + ordProvisiongCharId);

        // Get all the ordOrderItemProvisioningList where ordProvisiongChar equals to (ordProvisiongCharId + 1)
        defaultOrdOrderItemProvisioningShouldNotBeFound("ordProvisiongCharId.equals=" + (ordProvisiongCharId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderItemProvisioningsByOrdOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);
        OrdOrderItem ordOrderItem = OrdOrderItemResourceIT.createEntity(em);
        em.persist(ordOrderItem);
        em.flush();
        ordOrderItemProvisioning.setOrdOrderItem(ordOrderItem);
        ordOrderItem.setOrdOrderItemProvisioning(ordOrderItemProvisioning);
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);
        Long ordOrderItemId = ordOrderItem.getId();

        // Get all the ordOrderItemProvisioningList where ordOrderItem equals to ordOrderItemId
        defaultOrdOrderItemProvisioningShouldBeFound("ordOrderItemId.equals=" + ordOrderItemId);

        // Get all the ordOrderItemProvisioningList where ordOrderItem equals to (ordOrderItemId + 1)
        defaultOrdOrderItemProvisioningShouldNotBeFound("ordOrderItemId.equals=" + (ordOrderItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdOrderItemProvisioningShouldBeFound(String filter) throws Exception {
        restOrdOrderItemProvisioningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItemProvisioning.getId().intValue())))
            .andExpect(jsonPath("$.[*].provisioningId").value(hasItem(DEFAULT_PROVISIONING_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restOrdOrderItemProvisioningMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdOrderItemProvisioningShouldNotBeFound(String filter) throws Exception {
        restOrdOrderItemProvisioningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdOrderItemProvisioningMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdOrderItemProvisioning() throws Exception {
        // Get the ordOrderItemProvisioning
        restOrdOrderItemProvisioningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdOrderItemProvisioning() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();

        // Update the ordOrderItemProvisioning
        OrdOrderItemProvisioning updatedOrdOrderItemProvisioning = ordOrderItemProvisioningRepository
            .findById(ordOrderItemProvisioning.getId())
            .get();
        // Disconnect from session so that the updates on updatedOrdOrderItemProvisioning are not directly saved in db
        em.detach(updatedOrdOrderItemProvisioning);
        updatedOrdOrderItemProvisioning.provisioningId(UPDATED_PROVISIONING_ID).status(UPDATED_STATUS);
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(updatedOrdOrderItemProvisioning);

        restOrdOrderItemProvisioningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemProvisioningDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemProvisioning testOrdOrderItemProvisioning = ordOrderItemProvisioningList.get(ordOrderItemProvisioningList.size() - 1);
        assertThat(testOrdOrderItemProvisioning.getProvisioningId()).isEqualTo(UPDATED_PROVISIONING_ID);
        assertThat(testOrdOrderItemProvisioning.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOrdOrderItemProvisioning() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();
        ordOrderItemProvisioning.setId(count.incrementAndGet());

        // Create the OrdOrderItemProvisioning
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemProvisioningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemProvisioningDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdOrderItemProvisioning() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();
        ordOrderItemProvisioning.setId(count.incrementAndGet());

        // Create the OrdOrderItemProvisioning
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemProvisioningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdOrderItemProvisioning() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();
        ordOrderItemProvisioning.setId(count.incrementAndGet());

        // Create the OrdOrderItemProvisioning
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemProvisioningMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdOrderItemProvisioningWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();

        // Update the ordOrderItemProvisioning using partial update
        OrdOrderItemProvisioning partialUpdatedOrdOrderItemProvisioning = new OrdOrderItemProvisioning();
        partialUpdatedOrdOrderItemProvisioning.setId(ordOrderItemProvisioning.getId());

        partialUpdatedOrdOrderItemProvisioning.status(UPDATED_STATUS);

        restOrdOrderItemProvisioningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItemProvisioning.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItemProvisioning))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemProvisioning testOrdOrderItemProvisioning = ordOrderItemProvisioningList.get(ordOrderItemProvisioningList.size() - 1);
        assertThat(testOrdOrderItemProvisioning.getProvisioningId()).isEqualTo(DEFAULT_PROVISIONING_ID);
        assertThat(testOrdOrderItemProvisioning.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOrdOrderItemProvisioningWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();

        // Update the ordOrderItemProvisioning using partial update
        OrdOrderItemProvisioning partialUpdatedOrdOrderItemProvisioning = new OrdOrderItemProvisioning();
        partialUpdatedOrdOrderItemProvisioning.setId(ordOrderItemProvisioning.getId());

        partialUpdatedOrdOrderItemProvisioning.provisioningId(UPDATED_PROVISIONING_ID).status(UPDATED_STATUS);

        restOrdOrderItemProvisioningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItemProvisioning.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItemProvisioning))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemProvisioning testOrdOrderItemProvisioning = ordOrderItemProvisioningList.get(ordOrderItemProvisioningList.size() - 1);
        assertThat(testOrdOrderItemProvisioning.getProvisioningId()).isEqualTo(UPDATED_PROVISIONING_ID);
        assertThat(testOrdOrderItemProvisioning.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOrdOrderItemProvisioning() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();
        ordOrderItemProvisioning.setId(count.incrementAndGet());

        // Create the OrdOrderItemProvisioning
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemProvisioningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordOrderItemProvisioningDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdOrderItemProvisioning() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();
        ordOrderItemProvisioning.setId(count.incrementAndGet());

        // Create the OrdOrderItemProvisioning
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemProvisioningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdOrderItemProvisioning() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemProvisioningRepository.findAll().size();
        ordOrderItemProvisioning.setId(count.incrementAndGet());

        // Create the OrdOrderItemProvisioning
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemProvisioningMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemProvisioningDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItemProvisioning in the database
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdOrderItemProvisioning() throws Exception {
        // Initialize the database
        ordOrderItemProvisioningRepository.saveAndFlush(ordOrderItemProvisioning);

        int databaseSizeBeforeDelete = ordOrderItemProvisioningRepository.findAll().size();

        // Delete the ordOrderItemProvisioning
        restOrdOrderItemProvisioningMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordOrderItemProvisioning.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdOrderItemProvisioning> ordOrderItemProvisioningList = ordOrderItemProvisioningRepository.findAll();
        assertThat(ordOrderItemProvisioningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
