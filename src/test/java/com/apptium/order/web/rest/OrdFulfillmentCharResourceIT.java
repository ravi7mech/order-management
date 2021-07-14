package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdFulfillment;
import com.apptium.order.domain.OrdFulfillmentChar;
import com.apptium.order.repository.OrdFulfillmentCharRepository;
import com.apptium.order.service.criteria.OrdFulfillmentCharCriteria;
import com.apptium.order.service.dto.OrdFulfillmentCharDTO;
import com.apptium.order.service.mapper.OrdFulfillmentCharMapper;
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
 * Integration tests for the {@link OrdFulfillmentCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdFulfillmentCharResourceIT {

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

    private static final String ENTITY_API_URL = "/api/ord-fulfillment-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdFulfillmentCharRepository ordFulfillmentCharRepository;

    @Autowired
    private OrdFulfillmentCharMapper ordFulfillmentCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdFulfillmentCharMockMvc;

    private OrdFulfillmentChar ordFulfillmentChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdFulfillmentChar createEntity(EntityManager em) {
        OrdFulfillmentChar ordFulfillmentChar = new OrdFulfillmentChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return ordFulfillmentChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdFulfillmentChar createUpdatedEntity(EntityManager em) {
        OrdFulfillmentChar ordFulfillmentChar = new OrdFulfillmentChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        return ordFulfillmentChar;
    }

    @BeforeEach
    public void initTest() {
        ordFulfillmentChar = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdFulfillmentChar() throws Exception {
        int databaseSizeBeforeCreate = ordFulfillmentCharRepository.findAll().size();
        // Create the OrdFulfillmentChar
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);
        restOrdFulfillmentCharMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeCreate + 1);
        OrdFulfillmentChar testOrdFulfillmentChar = ordFulfillmentCharList.get(ordFulfillmentCharList.size() - 1);
        assertThat(testOrdFulfillmentChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdFulfillmentChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdFulfillmentChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testOrdFulfillmentChar.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrdFulfillmentChar.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createOrdFulfillmentCharWithExistingId() throws Exception {
        // Create the OrdFulfillmentChar with an existing ID
        ordFulfillmentChar.setId(1L);
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);

        int databaseSizeBeforeCreate = ordFulfillmentCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdFulfillmentCharMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentChars() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList
        restOrdFulfillmentCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordFulfillmentChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrdFulfillmentChar() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get the ordFulfillmentChar
        restOrdFulfillmentCharMockMvc
            .perform(get(ENTITY_API_URL_ID, ordFulfillmentChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordFulfillmentChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOrdFulfillmentCharsByIdFiltering() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        Long id = ordFulfillmentChar.getId();

        defaultOrdFulfillmentCharShouldBeFound("id.equals=" + id);
        defaultOrdFulfillmentCharShouldNotBeFound("id.notEquals=" + id);

        defaultOrdFulfillmentCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdFulfillmentCharShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdFulfillmentCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdFulfillmentCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where name equals to DEFAULT_NAME
        defaultOrdFulfillmentCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordFulfillmentCharList where name equals to UPDATED_NAME
        defaultOrdFulfillmentCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where name not equals to DEFAULT_NAME
        defaultOrdFulfillmentCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordFulfillmentCharList where name not equals to UPDATED_NAME
        defaultOrdFulfillmentCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdFulfillmentCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordFulfillmentCharList where name equals to UPDATED_NAME
        defaultOrdFulfillmentCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where name is not null
        defaultOrdFulfillmentCharShouldBeFound("name.specified=true");

        // Get all the ordFulfillmentCharList where name is null
        defaultOrdFulfillmentCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where name contains DEFAULT_NAME
        defaultOrdFulfillmentCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordFulfillmentCharList where name contains UPDATED_NAME
        defaultOrdFulfillmentCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where name does not contain DEFAULT_NAME
        defaultOrdFulfillmentCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordFulfillmentCharList where name does not contain UPDATED_NAME
        defaultOrdFulfillmentCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where value equals to DEFAULT_VALUE
        defaultOrdFulfillmentCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the ordFulfillmentCharList where value equals to UPDATED_VALUE
        defaultOrdFulfillmentCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where value not equals to DEFAULT_VALUE
        defaultOrdFulfillmentCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the ordFulfillmentCharList where value not equals to UPDATED_VALUE
        defaultOrdFulfillmentCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOrdFulfillmentCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the ordFulfillmentCharList where value equals to UPDATED_VALUE
        defaultOrdFulfillmentCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where value is not null
        defaultOrdFulfillmentCharShouldBeFound("value.specified=true");

        // Get all the ordFulfillmentCharList where value is null
        defaultOrdFulfillmentCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where value contains DEFAULT_VALUE
        defaultOrdFulfillmentCharShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the ordFulfillmentCharList where value contains UPDATED_VALUE
        defaultOrdFulfillmentCharShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where value does not contain DEFAULT_VALUE
        defaultOrdFulfillmentCharShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the ordFulfillmentCharList where value does not contain UPDATED_VALUE
        defaultOrdFulfillmentCharShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultOrdFulfillmentCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordFulfillmentCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdFulfillmentCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultOrdFulfillmentCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordFulfillmentCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultOrdFulfillmentCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultOrdFulfillmentCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the ordFulfillmentCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdFulfillmentCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where valueType is not null
        defaultOrdFulfillmentCharShouldBeFound("valueType.specified=true");

        // Get all the ordFulfillmentCharList where valueType is null
        defaultOrdFulfillmentCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultOrdFulfillmentCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the ordFulfillmentCharList where valueType contains UPDATED_VALUE_TYPE
        defaultOrdFulfillmentCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultOrdFulfillmentCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the ordFulfillmentCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultOrdFulfillmentCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrdFulfillmentCharShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the ordFulfillmentCharList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdFulfillmentCharShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOrdFulfillmentCharShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the ordFulfillmentCharList where createdBy not equals to UPDATED_CREATED_BY
        defaultOrdFulfillmentCharShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrdFulfillmentCharShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the ordFulfillmentCharList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdFulfillmentCharShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdBy is not null
        defaultOrdFulfillmentCharShouldBeFound("createdBy.specified=true");

        // Get all the ordFulfillmentCharList where createdBy is null
        defaultOrdFulfillmentCharShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdBy contains DEFAULT_CREATED_BY
        defaultOrdFulfillmentCharShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the ordFulfillmentCharList where createdBy contains UPDATED_CREATED_BY
        defaultOrdFulfillmentCharShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrdFulfillmentCharShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the ordFulfillmentCharList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrdFulfillmentCharShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOrdFulfillmentCharShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the ordFulfillmentCharList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdFulfillmentCharShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOrdFulfillmentCharShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the ordFulfillmentCharList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOrdFulfillmentCharShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOrdFulfillmentCharShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the ordFulfillmentCharList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdFulfillmentCharShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        // Get all the ordFulfillmentCharList where createdDate is not null
        defaultOrdFulfillmentCharShouldBeFound("createdDate.specified=true");

        // Get all the ordFulfillmentCharList where createdDate is null
        defaultOrdFulfillmentCharShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentCharsByOrdFulfillmentIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);
        OrdFulfillment ordFulfillment = OrdFulfillmentResourceIT.createEntity(em);
        em.persist(ordFulfillment);
        em.flush();
        ordFulfillmentChar.setOrdFulfillment(ordFulfillment);
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);
        Long ordFulfillmentId = ordFulfillment.getId();

        // Get all the ordFulfillmentCharList where ordFulfillment equals to ordFulfillmentId
        defaultOrdFulfillmentCharShouldBeFound("ordFulfillmentId.equals=" + ordFulfillmentId);

        // Get all the ordFulfillmentCharList where ordFulfillment equals to (ordFulfillmentId + 1)
        defaultOrdFulfillmentCharShouldNotBeFound("ordFulfillmentId.equals=" + (ordFulfillmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdFulfillmentCharShouldBeFound(String filter) throws Exception {
        restOrdFulfillmentCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordFulfillmentChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restOrdFulfillmentCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdFulfillmentCharShouldNotBeFound(String filter) throws Exception {
        restOrdFulfillmentCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdFulfillmentCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdFulfillmentChar() throws Exception {
        // Get the ordFulfillmentChar
        restOrdFulfillmentCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdFulfillmentChar() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();

        // Update the ordFulfillmentChar
        OrdFulfillmentChar updatedOrdFulfillmentChar = ordFulfillmentCharRepository.findById(ordFulfillmentChar.getId()).get();
        // Disconnect from session so that the updates on updatedOrdFulfillmentChar are not directly saved in db
        em.detach(updatedOrdFulfillmentChar);
        updatedOrdFulfillmentChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(updatedOrdFulfillmentChar);

        restOrdFulfillmentCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordFulfillmentCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
        OrdFulfillmentChar testOrdFulfillmentChar = ordFulfillmentCharList.get(ordFulfillmentCharList.size() - 1);
        assertThat(testOrdFulfillmentChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdFulfillmentChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdFulfillmentChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdFulfillmentChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdFulfillmentChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrdFulfillmentChar() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();
        ordFulfillmentChar.setId(count.incrementAndGet());

        // Create the OrdFulfillmentChar
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdFulfillmentCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordFulfillmentCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdFulfillmentChar() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();
        ordFulfillmentChar.setId(count.incrementAndGet());

        // Create the OrdFulfillmentChar
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdFulfillmentChar() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();
        ordFulfillmentChar.setId(count.incrementAndGet());

        // Create the OrdFulfillmentChar
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentCharMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdFulfillmentCharWithPatch() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();

        // Update the ordFulfillmentChar using partial update
        OrdFulfillmentChar partialUpdatedOrdFulfillmentChar = new OrdFulfillmentChar();
        partialUpdatedOrdFulfillmentChar.setId(ordFulfillmentChar.getId());

        partialUpdatedOrdFulfillmentChar
            .name(UPDATED_NAME)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restOrdFulfillmentCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdFulfillmentChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdFulfillmentChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
        OrdFulfillmentChar testOrdFulfillmentChar = ordFulfillmentCharList.get(ordFulfillmentCharList.size() - 1);
        assertThat(testOrdFulfillmentChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdFulfillmentChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdFulfillmentChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdFulfillmentChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdFulfillmentChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrdFulfillmentCharWithPatch() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();

        // Update the ordFulfillmentChar using partial update
        OrdFulfillmentChar partialUpdatedOrdFulfillmentChar = new OrdFulfillmentChar();
        partialUpdatedOrdFulfillmentChar.setId(ordFulfillmentChar.getId());

        partialUpdatedOrdFulfillmentChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restOrdFulfillmentCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdFulfillmentChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdFulfillmentChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
        OrdFulfillmentChar testOrdFulfillmentChar = ordFulfillmentCharList.get(ordFulfillmentCharList.size() - 1);
        assertThat(testOrdFulfillmentChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdFulfillmentChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdFulfillmentChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdFulfillmentChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdFulfillmentChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdFulfillmentChar() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();
        ordFulfillmentChar.setId(count.incrementAndGet());

        // Create the OrdFulfillmentChar
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdFulfillmentCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordFulfillmentCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdFulfillmentChar() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();
        ordFulfillmentChar.setId(count.incrementAndGet());

        // Create the OrdFulfillmentChar
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdFulfillmentChar() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentCharRepository.findAll().size();
        ordFulfillmentChar.setId(count.incrementAndGet());

        // Create the OrdFulfillmentChar
        OrdFulfillmentCharDTO ordFulfillmentCharDTO = ordFulfillmentCharMapper.toDto(ordFulfillmentChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdFulfillmentChar in the database
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdFulfillmentChar() throws Exception {
        // Initialize the database
        ordFulfillmentCharRepository.saveAndFlush(ordFulfillmentChar);

        int databaseSizeBeforeDelete = ordFulfillmentCharRepository.findAll().size();

        // Delete the ordFulfillmentChar
        restOrdFulfillmentCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordFulfillmentChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdFulfillmentChar> ordFulfillmentCharList = ordFulfillmentCharRepository.findAll();
        assertThat(ordFulfillmentCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
