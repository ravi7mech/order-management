package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdOrderItemRelationship;
import com.apptium.order.repository.OrdOrderItemRelationshipRepository;
import com.apptium.order.service.criteria.OrdOrderItemRelationshipCriteria;
import com.apptium.order.service.dto.OrdOrderItemRelationshipDTO;
import com.apptium.order.service.mapper.OrdOrderItemRelationshipMapper;
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
 * Integration tests for the {@link OrdOrderItemRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdOrderItemRelationshipResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIMARY_ORDER_ITEM_ID = 1L;
    private static final Long UPDATED_PRIMARY_ORDER_ITEM_ID = 2L;
    private static final Long SMALLER_PRIMARY_ORDER_ITEM_ID = 1L - 1L;

    private static final Long DEFAULT_SECONDARY_ORDER_ITEM_ID = 1L;
    private static final Long UPDATED_SECONDARY_ORDER_ITEM_ID = 2L;
    private static final Long SMALLER_SECONDARY_ORDER_ITEM_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ord-order-item-relationships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdOrderItemRelationshipRepository ordOrderItemRelationshipRepository;

    @Autowired
    private OrdOrderItemRelationshipMapper ordOrderItemRelationshipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdOrderItemRelationshipMockMvc;

    private OrdOrderItemRelationship ordOrderItemRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItemRelationship createEntity(EntityManager em) {
        OrdOrderItemRelationship ordOrderItemRelationship = new OrdOrderItemRelationship()
            .type(DEFAULT_TYPE)
            .primaryOrderItemId(DEFAULT_PRIMARY_ORDER_ITEM_ID)
            .secondaryOrderItemId(DEFAULT_SECONDARY_ORDER_ITEM_ID);
        return ordOrderItemRelationship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItemRelationship createUpdatedEntity(EntityManager em) {
        OrdOrderItemRelationship ordOrderItemRelationship = new OrdOrderItemRelationship()
            .type(UPDATED_TYPE)
            .primaryOrderItemId(UPDATED_PRIMARY_ORDER_ITEM_ID)
            .secondaryOrderItemId(UPDATED_SECONDARY_ORDER_ITEM_ID);
        return ordOrderItemRelationship;
    }

    @BeforeEach
    public void initTest() {
        ordOrderItemRelationship = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdOrderItemRelationship() throws Exception {
        int databaseSizeBeforeCreate = ordOrderItemRelationshipRepository.findAll().size();
        // Create the OrdOrderItemRelationship
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);
        restOrdOrderItemRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        OrdOrderItemRelationship testOrdOrderItemRelationship = ordOrderItemRelationshipList.get(ordOrderItemRelationshipList.size() - 1);
        assertThat(testOrdOrderItemRelationship.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrdOrderItemRelationship.getPrimaryOrderItemId()).isEqualTo(DEFAULT_PRIMARY_ORDER_ITEM_ID);
        assertThat(testOrdOrderItemRelationship.getSecondaryOrderItemId()).isEqualTo(DEFAULT_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void createOrdOrderItemRelationshipWithExistingId() throws Exception {
        // Create the OrdOrderItemRelationship with an existing ID
        ordOrderItemRelationship.setId(1L);
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);

        int databaseSizeBeforeCreate = ordOrderItemRelationshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdOrderItemRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationships() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList
        restOrdOrderItemRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItemRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].primaryOrderItemId").value(hasItem(DEFAULT_PRIMARY_ORDER_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].secondaryOrderItemId").value(hasItem(DEFAULT_SECONDARY_ORDER_ITEM_ID.intValue())));
    }

    @Test
    @Transactional
    void getOrdOrderItemRelationship() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get the ordOrderItemRelationship
        restOrdOrderItemRelationshipMockMvc
            .perform(get(ENTITY_API_URL_ID, ordOrderItemRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordOrderItemRelationship.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.primaryOrderItemId").value(DEFAULT_PRIMARY_ORDER_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.secondaryOrderItemId").value(DEFAULT_SECONDARY_ORDER_ITEM_ID.intValue()));
    }

    @Test
    @Transactional
    void getOrdOrderItemRelationshipsByIdFiltering() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        Long id = ordOrderItemRelationship.getId();

        defaultOrdOrderItemRelationshipShouldBeFound("id.equals=" + id);
        defaultOrdOrderItemRelationshipShouldNotBeFound("id.notEquals=" + id);

        defaultOrdOrderItemRelationshipShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdOrderItemRelationshipShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdOrderItemRelationshipShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdOrderItemRelationshipShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where type equals to DEFAULT_TYPE
        defaultOrdOrderItemRelationshipShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the ordOrderItemRelationshipList where type equals to UPDATED_TYPE
        defaultOrdOrderItemRelationshipShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where type not equals to DEFAULT_TYPE
        defaultOrdOrderItemRelationshipShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the ordOrderItemRelationshipList where type not equals to UPDATED_TYPE
        defaultOrdOrderItemRelationshipShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultOrdOrderItemRelationshipShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the ordOrderItemRelationshipList where type equals to UPDATED_TYPE
        defaultOrdOrderItemRelationshipShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where type is not null
        defaultOrdOrderItemRelationshipShouldBeFound("type.specified=true");

        // Get all the ordOrderItemRelationshipList where type is null
        defaultOrdOrderItemRelationshipShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByTypeContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where type contains DEFAULT_TYPE
        defaultOrdOrderItemRelationshipShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the ordOrderItemRelationshipList where type contains UPDATED_TYPE
        defaultOrdOrderItemRelationshipShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where type does not contain DEFAULT_TYPE
        defaultOrdOrderItemRelationshipShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the ordOrderItemRelationshipList where type does not contain UPDATED_TYPE
        defaultOrdOrderItemRelationshipShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId equals to DEFAULT_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("primaryOrderItemId.equals=" + DEFAULT_PRIMARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId equals to UPDATED_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.equals=" + UPDATED_PRIMARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId not equals to DEFAULT_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.notEquals=" + DEFAULT_PRIMARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId not equals to UPDATED_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("primaryOrderItemId.notEquals=" + UPDATED_PRIMARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId in DEFAULT_PRIMARY_ORDER_ITEM_ID or UPDATED_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound(
            "primaryOrderItemId.in=" + DEFAULT_PRIMARY_ORDER_ITEM_ID + "," + UPDATED_PRIMARY_ORDER_ITEM_ID
        );

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId equals to UPDATED_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.in=" + UPDATED_PRIMARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is not null
        defaultOrdOrderItemRelationshipShouldBeFound("primaryOrderItemId.specified=true");

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is null
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is greater than or equal to DEFAULT_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("primaryOrderItemId.greaterThanOrEqual=" + DEFAULT_PRIMARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is greater than or equal to UPDATED_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.greaterThanOrEqual=" + UPDATED_PRIMARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is less than or equal to DEFAULT_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("primaryOrderItemId.lessThanOrEqual=" + DEFAULT_PRIMARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is less than or equal to SMALLER_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.lessThanOrEqual=" + SMALLER_PRIMARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is less than DEFAULT_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.lessThan=" + DEFAULT_PRIMARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is less than UPDATED_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("primaryOrderItemId.lessThan=" + UPDATED_PRIMARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByPrimaryOrderItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is greater than DEFAULT_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("primaryOrderItemId.greaterThan=" + DEFAULT_PRIMARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where primaryOrderItemId is greater than SMALLER_PRIMARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("primaryOrderItemId.greaterThan=" + SMALLER_PRIMARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId equals to DEFAULT_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("secondaryOrderItemId.equals=" + DEFAULT_SECONDARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId equals to UPDATED_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.equals=" + UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId not equals to DEFAULT_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.notEquals=" + DEFAULT_SECONDARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId not equals to UPDATED_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("secondaryOrderItemId.notEquals=" + UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId in DEFAULT_SECONDARY_ORDER_ITEM_ID or UPDATED_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound(
            "secondaryOrderItemId.in=" + DEFAULT_SECONDARY_ORDER_ITEM_ID + "," + UPDATED_SECONDARY_ORDER_ITEM_ID
        );

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId equals to UPDATED_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.in=" + UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is not null
        defaultOrdOrderItemRelationshipShouldBeFound("secondaryOrderItemId.specified=true");

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is null
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is greater than or equal to DEFAULT_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("secondaryOrderItemId.greaterThanOrEqual=" + DEFAULT_SECONDARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is greater than or equal to UPDATED_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.greaterThanOrEqual=" + UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is less than or equal to DEFAULT_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("secondaryOrderItemId.lessThanOrEqual=" + DEFAULT_SECONDARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is less than or equal to SMALLER_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.lessThanOrEqual=" + SMALLER_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is less than DEFAULT_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.lessThan=" + DEFAULT_SECONDARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is less than UPDATED_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("secondaryOrderItemId.lessThan=" + UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsBySecondaryOrderItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is greater than DEFAULT_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldNotBeFound("secondaryOrderItemId.greaterThan=" + DEFAULT_SECONDARY_ORDER_ITEM_ID);

        // Get all the ordOrderItemRelationshipList where secondaryOrderItemId is greater than SMALLER_SECONDARY_ORDER_ITEM_ID
        defaultOrdOrderItemRelationshipShouldBeFound("secondaryOrderItemId.greaterThan=" + SMALLER_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemRelationshipsByOrdOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);
        OrdOrderItem ordOrderItem = OrdOrderItemResourceIT.createEntity(em);
        em.persist(ordOrderItem);
        em.flush();
        ordOrderItemRelationship.setOrdOrderItem(ordOrderItem);
        ordOrderItem.setOrdOrderItemRelationship(ordOrderItemRelationship);
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);
        Long ordOrderItemId = ordOrderItem.getId();

        // Get all the ordOrderItemRelationshipList where ordOrderItem equals to ordOrderItemId
        defaultOrdOrderItemRelationshipShouldBeFound("ordOrderItemId.equals=" + ordOrderItemId);

        // Get all the ordOrderItemRelationshipList where ordOrderItem equals to (ordOrderItemId + 1)
        defaultOrdOrderItemRelationshipShouldNotBeFound("ordOrderItemId.equals=" + (ordOrderItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdOrderItemRelationshipShouldBeFound(String filter) throws Exception {
        restOrdOrderItemRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItemRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].primaryOrderItemId").value(hasItem(DEFAULT_PRIMARY_ORDER_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].secondaryOrderItemId").value(hasItem(DEFAULT_SECONDARY_ORDER_ITEM_ID.intValue())));

        // Check, that the count call also returns 1
        restOrdOrderItemRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdOrderItemRelationshipShouldNotBeFound(String filter) throws Exception {
        restOrdOrderItemRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdOrderItemRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdOrderItemRelationship() throws Exception {
        // Get the ordOrderItemRelationship
        restOrdOrderItemRelationshipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdOrderItemRelationship() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();

        // Update the ordOrderItemRelationship
        OrdOrderItemRelationship updatedOrdOrderItemRelationship = ordOrderItemRelationshipRepository
            .findById(ordOrderItemRelationship.getId())
            .get();
        // Disconnect from session so that the updates on updatedOrdOrderItemRelationship are not directly saved in db
        em.detach(updatedOrdOrderItemRelationship);
        updatedOrdOrderItemRelationship
            .type(UPDATED_TYPE)
            .primaryOrderItemId(UPDATED_PRIMARY_ORDER_ITEM_ID)
            .secondaryOrderItemId(UPDATED_SECONDARY_ORDER_ITEM_ID);
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(updatedOrdOrderItemRelationship);

        restOrdOrderItemRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemRelationship testOrdOrderItemRelationship = ordOrderItemRelationshipList.get(ordOrderItemRelationshipList.size() - 1);
        assertThat(testOrdOrderItemRelationship.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrdOrderItemRelationship.getPrimaryOrderItemId()).isEqualTo(UPDATED_PRIMARY_ORDER_ITEM_ID);
        assertThat(testOrdOrderItemRelationship.getSecondaryOrderItemId()).isEqualTo(UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void putNonExistingOrdOrderItemRelationship() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();
        ordOrderItemRelationship.setId(count.incrementAndGet());

        // Create the OrdOrderItemRelationship
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdOrderItemRelationship() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();
        ordOrderItemRelationship.setId(count.incrementAndGet());

        // Create the OrdOrderItemRelationship
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdOrderItemRelationship() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();
        ordOrderItemRelationship.setId(count.incrementAndGet());

        // Create the OrdOrderItemRelationship
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdOrderItemRelationshipWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();

        // Update the ordOrderItemRelationship using partial update
        OrdOrderItemRelationship partialUpdatedOrdOrderItemRelationship = new OrdOrderItemRelationship();
        partialUpdatedOrdOrderItemRelationship.setId(ordOrderItemRelationship.getId());

        partialUpdatedOrdOrderItemRelationship
            .type(UPDATED_TYPE)
            .primaryOrderItemId(UPDATED_PRIMARY_ORDER_ITEM_ID)
            .secondaryOrderItemId(UPDATED_SECONDARY_ORDER_ITEM_ID);

        restOrdOrderItemRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItemRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItemRelationship))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemRelationship testOrdOrderItemRelationship = ordOrderItemRelationshipList.get(ordOrderItemRelationshipList.size() - 1);
        assertThat(testOrdOrderItemRelationship.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrdOrderItemRelationship.getPrimaryOrderItemId()).isEqualTo(UPDATED_PRIMARY_ORDER_ITEM_ID);
        assertThat(testOrdOrderItemRelationship.getSecondaryOrderItemId()).isEqualTo(UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void fullUpdateOrdOrderItemRelationshipWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();

        // Update the ordOrderItemRelationship using partial update
        OrdOrderItemRelationship partialUpdatedOrdOrderItemRelationship = new OrdOrderItemRelationship();
        partialUpdatedOrdOrderItemRelationship.setId(ordOrderItemRelationship.getId());

        partialUpdatedOrdOrderItemRelationship
            .type(UPDATED_TYPE)
            .primaryOrderItemId(UPDATED_PRIMARY_ORDER_ITEM_ID)
            .secondaryOrderItemId(UPDATED_SECONDARY_ORDER_ITEM_ID);

        restOrdOrderItemRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItemRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItemRelationship))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemRelationship testOrdOrderItemRelationship = ordOrderItemRelationshipList.get(ordOrderItemRelationshipList.size() - 1);
        assertThat(testOrdOrderItemRelationship.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrdOrderItemRelationship.getPrimaryOrderItemId()).isEqualTo(UPDATED_PRIMARY_ORDER_ITEM_ID);
        assertThat(testOrdOrderItemRelationship.getSecondaryOrderItemId()).isEqualTo(UPDATED_SECONDARY_ORDER_ITEM_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOrdOrderItemRelationship() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();
        ordOrderItemRelationship.setId(count.incrementAndGet());

        // Create the OrdOrderItemRelationship
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordOrderItemRelationshipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdOrderItemRelationship() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();
        ordOrderItemRelationship.setId(count.incrementAndGet());

        // Create the OrdOrderItemRelationship
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdOrderItemRelationship() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRelationshipRepository.findAll().size();
        ordOrderItemRelationship.setId(count.incrementAndGet());

        // Create the OrdOrderItemRelationship
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItemRelationship in the database
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdOrderItemRelationship() throws Exception {
        // Initialize the database
        ordOrderItemRelationshipRepository.saveAndFlush(ordOrderItemRelationship);

        int databaseSizeBeforeDelete = ordOrderItemRelationshipRepository.findAll().size();

        // Delete the ordOrderItemRelationship
        restOrdOrderItemRelationshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordOrderItemRelationship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdOrderItemRelationship> ordOrderItemRelationshipList = ordOrderItemRelationshipRepository.findAll();
        assertThat(ordOrderItemRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
