package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItemProvisioning;
import com.apptium.order.domain.OrdProvisiongChar;
import com.apptium.order.repository.OrdProvisiongCharRepository;
import com.apptium.order.service.criteria.OrdProvisiongCharCriteria;
import com.apptium.order.service.dto.OrdProvisiongCharDTO;
import com.apptium.order.service.mapper.OrdProvisiongCharMapper;
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
 * Integration tests for the {@link OrdProvisiongCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdProvisiongCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ord-provisiong-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdProvisiongCharRepository ordProvisiongCharRepository;

    @Autowired
    private OrdProvisiongCharMapper ordProvisiongCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdProvisiongCharMockMvc;

    private OrdProvisiongChar ordProvisiongChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProvisiongChar createEntity(EntityManager em) {
        OrdProvisiongChar ordProvisiongChar = new OrdProvisiongChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return ordProvisiongChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProvisiongChar createUpdatedEntity(EntityManager em) {
        OrdProvisiongChar ordProvisiongChar = new OrdProvisiongChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        return ordProvisiongChar;
    }

    @BeforeEach
    public void initTest() {
        ordProvisiongChar = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdProvisiongChar() throws Exception {
        int databaseSizeBeforeCreate = ordProvisiongCharRepository.findAll().size();
        // Create the OrdProvisiongChar
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);
        restOrdProvisiongCharMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeCreate + 1);
        OrdProvisiongChar testOrdProvisiongChar = ordProvisiongCharList.get(ordProvisiongCharList.size() - 1);
        assertThat(testOrdProvisiongChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdProvisiongChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdProvisiongChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testOrdProvisiongChar.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrdProvisiongChar.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createOrdProvisiongCharWithExistingId() throws Exception {
        // Create the OrdProvisiongChar with an existing ID
        ordProvisiongChar.setId(1L);
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);

        int databaseSizeBeforeCreate = ordProvisiongCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdProvisiongCharMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongChars() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList
        restOrdProvisiongCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProvisiongChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrdProvisiongChar() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get the ordProvisiongChar
        restOrdProvisiongCharMockMvc
            .perform(get(ENTITY_API_URL_ID, ordProvisiongChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordProvisiongChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOrdProvisiongCharsByIdFiltering() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        Long id = ordProvisiongChar.getId();

        defaultOrdProvisiongCharShouldBeFound("id.equals=" + id);
        defaultOrdProvisiongCharShouldNotBeFound("id.notEquals=" + id);

        defaultOrdProvisiongCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdProvisiongCharShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdProvisiongCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdProvisiongCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where name equals to DEFAULT_NAME
        defaultOrdProvisiongCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordProvisiongCharList where name equals to UPDATED_NAME
        defaultOrdProvisiongCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where name not equals to DEFAULT_NAME
        defaultOrdProvisiongCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordProvisiongCharList where name not equals to UPDATED_NAME
        defaultOrdProvisiongCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdProvisiongCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordProvisiongCharList where name equals to UPDATED_NAME
        defaultOrdProvisiongCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where name is not null
        defaultOrdProvisiongCharShouldBeFound("name.specified=true");

        // Get all the ordProvisiongCharList where name is null
        defaultOrdProvisiongCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where name contains DEFAULT_NAME
        defaultOrdProvisiongCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordProvisiongCharList where name contains UPDATED_NAME
        defaultOrdProvisiongCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where name does not contain DEFAULT_NAME
        defaultOrdProvisiongCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordProvisiongCharList where name does not contain UPDATED_NAME
        defaultOrdProvisiongCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where value equals to DEFAULT_VALUE
        defaultOrdProvisiongCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the ordProvisiongCharList where value equals to UPDATED_VALUE
        defaultOrdProvisiongCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where value not equals to DEFAULT_VALUE
        defaultOrdProvisiongCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the ordProvisiongCharList where value not equals to UPDATED_VALUE
        defaultOrdProvisiongCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOrdProvisiongCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the ordProvisiongCharList where value equals to UPDATED_VALUE
        defaultOrdProvisiongCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where value is not null
        defaultOrdProvisiongCharShouldBeFound("value.specified=true");

        // Get all the ordProvisiongCharList where value is null
        defaultOrdProvisiongCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where value contains DEFAULT_VALUE
        defaultOrdProvisiongCharShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the ordProvisiongCharList where value contains UPDATED_VALUE
        defaultOrdProvisiongCharShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where value does not contain DEFAULT_VALUE
        defaultOrdProvisiongCharShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the ordProvisiongCharList where value does not contain UPDATED_VALUE
        defaultOrdProvisiongCharShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultOrdProvisiongCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProvisiongCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdProvisiongCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultOrdProvisiongCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProvisiongCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultOrdProvisiongCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultOrdProvisiongCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the ordProvisiongCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdProvisiongCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where valueType is not null
        defaultOrdProvisiongCharShouldBeFound("valueType.specified=true");

        // Get all the ordProvisiongCharList where valueType is null
        defaultOrdProvisiongCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultOrdProvisiongCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProvisiongCharList where valueType contains UPDATED_VALUE_TYPE
        defaultOrdProvisiongCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultOrdProvisiongCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProvisiongCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultOrdProvisiongCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrdProvisiongCharShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the ordProvisiongCharList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdProvisiongCharShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOrdProvisiongCharShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the ordProvisiongCharList where createdBy not equals to UPDATED_CREATED_BY
        defaultOrdProvisiongCharShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrdProvisiongCharShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the ordProvisiongCharList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdProvisiongCharShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdBy is not null
        defaultOrdProvisiongCharShouldBeFound("createdBy.specified=true");

        // Get all the ordProvisiongCharList where createdBy is null
        defaultOrdProvisiongCharShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdBy contains DEFAULT_CREATED_BY
        defaultOrdProvisiongCharShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the ordProvisiongCharList where createdBy contains UPDATED_CREATED_BY
        defaultOrdProvisiongCharShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrdProvisiongCharShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the ordProvisiongCharList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrdProvisiongCharShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOrdProvisiongCharShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the ordProvisiongCharList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdProvisiongCharShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOrdProvisiongCharShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the ordProvisiongCharList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOrdProvisiongCharShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOrdProvisiongCharShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the ordProvisiongCharList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdProvisiongCharShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        // Get all the ordProvisiongCharList where createdDate is not null
        defaultOrdProvisiongCharShouldBeFound("createdDate.specified=true");

        // Get all the ordProvisiongCharList where createdDate is null
        defaultOrdProvisiongCharShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProvisiongCharsByOrdOrderItemProvisioningIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);
        OrdOrderItemProvisioning ordOrderItemProvisioning = OrdOrderItemProvisioningResourceIT.createEntity(em);
        em.persist(ordOrderItemProvisioning);
        em.flush();
        ordProvisiongChar.setOrdOrderItemProvisioning(ordOrderItemProvisioning);
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);
        Long ordOrderItemProvisioningId = ordOrderItemProvisioning.getId();

        // Get all the ordProvisiongCharList where ordOrderItemProvisioning equals to ordOrderItemProvisioningId
        defaultOrdProvisiongCharShouldBeFound("ordOrderItemProvisioningId.equals=" + ordOrderItemProvisioningId);

        // Get all the ordProvisiongCharList where ordOrderItemProvisioning equals to (ordOrderItemProvisioningId + 1)
        defaultOrdProvisiongCharShouldNotBeFound("ordOrderItemProvisioningId.equals=" + (ordOrderItemProvisioningId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdProvisiongCharShouldBeFound(String filter) throws Exception {
        restOrdProvisiongCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProvisiongChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restOrdProvisiongCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdProvisiongCharShouldNotBeFound(String filter) throws Exception {
        restOrdProvisiongCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdProvisiongCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdProvisiongChar() throws Exception {
        // Get the ordProvisiongChar
        restOrdProvisiongCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdProvisiongChar() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();

        // Update the ordProvisiongChar
        OrdProvisiongChar updatedOrdProvisiongChar = ordProvisiongCharRepository.findById(ordProvisiongChar.getId()).get();
        // Disconnect from session so that the updates on updatedOrdProvisiongChar are not directly saved in db
        em.detach(updatedOrdProvisiongChar);
        updatedOrdProvisiongChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(updatedOrdProvisiongChar);

        restOrdProvisiongCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProvisiongCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
        OrdProvisiongChar testOrdProvisiongChar = ordProvisiongCharList.get(ordProvisiongCharList.size() - 1);
        assertThat(testOrdProvisiongChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProvisiongChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdProvisiongChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdProvisiongChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdProvisiongChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrdProvisiongChar() throws Exception {
        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();
        ordProvisiongChar.setId(count.incrementAndGet());

        // Create the OrdProvisiongChar
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProvisiongCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProvisiongCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdProvisiongChar() throws Exception {
        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();
        ordProvisiongChar.setId(count.incrementAndGet());

        // Create the OrdProvisiongChar
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProvisiongCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdProvisiongChar() throws Exception {
        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();
        ordProvisiongChar.setId(count.incrementAndGet());

        // Create the OrdProvisiongChar
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProvisiongCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdProvisiongCharWithPatch() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();

        // Update the ordProvisiongChar using partial update
        OrdProvisiongChar partialUpdatedOrdProvisiongChar = new OrdProvisiongChar();
        partialUpdatedOrdProvisiongChar.setId(ordProvisiongChar.getId());

        partialUpdatedOrdProvisiongChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restOrdProvisiongCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProvisiongChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProvisiongChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
        OrdProvisiongChar testOrdProvisiongChar = ordProvisiongCharList.get(ordProvisiongCharList.size() - 1);
        assertThat(testOrdProvisiongChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProvisiongChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdProvisiongChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdProvisiongChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdProvisiongChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrdProvisiongCharWithPatch() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();

        // Update the ordProvisiongChar using partial update
        OrdProvisiongChar partialUpdatedOrdProvisiongChar = new OrdProvisiongChar();
        partialUpdatedOrdProvisiongChar.setId(ordProvisiongChar.getId());

        partialUpdatedOrdProvisiongChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restOrdProvisiongCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProvisiongChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProvisiongChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
        OrdProvisiongChar testOrdProvisiongChar = ordProvisiongCharList.get(ordProvisiongCharList.size() - 1);
        assertThat(testOrdProvisiongChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProvisiongChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdProvisiongChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdProvisiongChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdProvisiongChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdProvisiongChar() throws Exception {
        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();
        ordProvisiongChar.setId(count.incrementAndGet());

        // Create the OrdProvisiongChar
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProvisiongCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordProvisiongCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdProvisiongChar() throws Exception {
        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();
        ordProvisiongChar.setId(count.incrementAndGet());

        // Create the OrdProvisiongChar
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProvisiongCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdProvisiongChar() throws Exception {
        int databaseSizeBeforeUpdate = ordProvisiongCharRepository.findAll().size();
        ordProvisiongChar.setId(count.incrementAndGet());

        // Create the OrdProvisiongChar
        OrdProvisiongCharDTO ordProvisiongCharDTO = ordProvisiongCharMapper.toDto(ordProvisiongChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProvisiongCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProvisiongCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProvisiongChar in the database
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdProvisiongChar() throws Exception {
        // Initialize the database
        ordProvisiongCharRepository.saveAndFlush(ordProvisiongChar);

        int databaseSizeBeforeDelete = ordProvisiongCharRepository.findAll().size();

        // Delete the ordProvisiongChar
        restOrdProvisiongCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordProvisiongChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdProvisiongChar> ordProvisiongCharList = ordProvisiongCharRepository.findAll();
        assertThat(ordProvisiongCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
