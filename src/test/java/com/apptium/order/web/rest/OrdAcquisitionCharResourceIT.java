package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdAcquisition;
import com.apptium.order.domain.OrdAcquisitionChar;
import com.apptium.order.repository.OrdAcquisitionCharRepository;
import com.apptium.order.service.criteria.OrdAcquisitionCharCriteria;
import com.apptium.order.service.dto.OrdAcquisitionCharDTO;
import com.apptium.order.service.mapper.OrdAcquisitionCharMapper;
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
 * Integration tests for the {@link OrdAcquisitionCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdAcquisitionCharResourceIT {

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

    private static final String ENTITY_API_URL = "/api/ord-acquisition-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdAcquisitionCharRepository ordAcquisitionCharRepository;

    @Autowired
    private OrdAcquisitionCharMapper ordAcquisitionCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdAcquisitionCharMockMvc;

    private OrdAcquisitionChar ordAcquisitionChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdAcquisitionChar createEntity(EntityManager em) {
        OrdAcquisitionChar ordAcquisitionChar = new OrdAcquisitionChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return ordAcquisitionChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdAcquisitionChar createUpdatedEntity(EntityManager em) {
        OrdAcquisitionChar ordAcquisitionChar = new OrdAcquisitionChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        return ordAcquisitionChar;
    }

    @BeforeEach
    public void initTest() {
        ordAcquisitionChar = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdAcquisitionChar() throws Exception {
        int databaseSizeBeforeCreate = ordAcquisitionCharRepository.findAll().size();
        // Create the OrdAcquisitionChar
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);
        restOrdAcquisitionCharMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeCreate + 1);
        OrdAcquisitionChar testOrdAcquisitionChar = ordAcquisitionCharList.get(ordAcquisitionCharList.size() - 1);
        assertThat(testOrdAcquisitionChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdAcquisitionChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdAcquisitionChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testOrdAcquisitionChar.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrdAcquisitionChar.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createOrdAcquisitionCharWithExistingId() throws Exception {
        // Create the OrdAcquisitionChar with an existing ID
        ordAcquisitionChar.setId(1L);
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);

        int databaseSizeBeforeCreate = ordAcquisitionCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdAcquisitionCharMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionChars() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList
        restOrdAcquisitionCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordAcquisitionChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrdAcquisitionChar() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get the ordAcquisitionChar
        restOrdAcquisitionCharMockMvc
            .perform(get(ENTITY_API_URL_ID, ordAcquisitionChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordAcquisitionChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOrdAcquisitionCharsByIdFiltering() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        Long id = ordAcquisitionChar.getId();

        defaultOrdAcquisitionCharShouldBeFound("id.equals=" + id);
        defaultOrdAcquisitionCharShouldNotBeFound("id.notEquals=" + id);

        defaultOrdAcquisitionCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdAcquisitionCharShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdAcquisitionCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdAcquisitionCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where name equals to DEFAULT_NAME
        defaultOrdAcquisitionCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordAcquisitionCharList where name equals to UPDATED_NAME
        defaultOrdAcquisitionCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where name not equals to DEFAULT_NAME
        defaultOrdAcquisitionCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordAcquisitionCharList where name not equals to UPDATED_NAME
        defaultOrdAcquisitionCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdAcquisitionCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordAcquisitionCharList where name equals to UPDATED_NAME
        defaultOrdAcquisitionCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where name is not null
        defaultOrdAcquisitionCharShouldBeFound("name.specified=true");

        // Get all the ordAcquisitionCharList where name is null
        defaultOrdAcquisitionCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where name contains DEFAULT_NAME
        defaultOrdAcquisitionCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordAcquisitionCharList where name contains UPDATED_NAME
        defaultOrdAcquisitionCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where name does not contain DEFAULT_NAME
        defaultOrdAcquisitionCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordAcquisitionCharList where name does not contain UPDATED_NAME
        defaultOrdAcquisitionCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where value equals to DEFAULT_VALUE
        defaultOrdAcquisitionCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the ordAcquisitionCharList where value equals to UPDATED_VALUE
        defaultOrdAcquisitionCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where value not equals to DEFAULT_VALUE
        defaultOrdAcquisitionCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the ordAcquisitionCharList where value not equals to UPDATED_VALUE
        defaultOrdAcquisitionCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOrdAcquisitionCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the ordAcquisitionCharList where value equals to UPDATED_VALUE
        defaultOrdAcquisitionCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where value is not null
        defaultOrdAcquisitionCharShouldBeFound("value.specified=true");

        // Get all the ordAcquisitionCharList where value is null
        defaultOrdAcquisitionCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where value contains DEFAULT_VALUE
        defaultOrdAcquisitionCharShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the ordAcquisitionCharList where value contains UPDATED_VALUE
        defaultOrdAcquisitionCharShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where value does not contain DEFAULT_VALUE
        defaultOrdAcquisitionCharShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the ordAcquisitionCharList where value does not contain UPDATED_VALUE
        defaultOrdAcquisitionCharShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultOrdAcquisitionCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordAcquisitionCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdAcquisitionCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultOrdAcquisitionCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordAcquisitionCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultOrdAcquisitionCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultOrdAcquisitionCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the ordAcquisitionCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdAcquisitionCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where valueType is not null
        defaultOrdAcquisitionCharShouldBeFound("valueType.specified=true");

        // Get all the ordAcquisitionCharList where valueType is null
        defaultOrdAcquisitionCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultOrdAcquisitionCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the ordAcquisitionCharList where valueType contains UPDATED_VALUE_TYPE
        defaultOrdAcquisitionCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultOrdAcquisitionCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the ordAcquisitionCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultOrdAcquisitionCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrdAcquisitionCharShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the ordAcquisitionCharList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdAcquisitionCharShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOrdAcquisitionCharShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the ordAcquisitionCharList where createdBy not equals to UPDATED_CREATED_BY
        defaultOrdAcquisitionCharShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrdAcquisitionCharShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the ordAcquisitionCharList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdAcquisitionCharShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdBy is not null
        defaultOrdAcquisitionCharShouldBeFound("createdBy.specified=true");

        // Get all the ordAcquisitionCharList where createdBy is null
        defaultOrdAcquisitionCharShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdBy contains DEFAULT_CREATED_BY
        defaultOrdAcquisitionCharShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the ordAcquisitionCharList where createdBy contains UPDATED_CREATED_BY
        defaultOrdAcquisitionCharShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrdAcquisitionCharShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the ordAcquisitionCharList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrdAcquisitionCharShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOrdAcquisitionCharShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the ordAcquisitionCharList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdAcquisitionCharShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOrdAcquisitionCharShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the ordAcquisitionCharList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOrdAcquisitionCharShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOrdAcquisitionCharShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the ordAcquisitionCharList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdAcquisitionCharShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        // Get all the ordAcquisitionCharList where createdDate is not null
        defaultOrdAcquisitionCharShouldBeFound("createdDate.specified=true");

        // Get all the ordAcquisitionCharList where createdDate is null
        defaultOrdAcquisitionCharShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionCharsByOrdAcquisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);
        OrdAcquisition ordAcquisition = OrdAcquisitionResourceIT.createEntity(em);
        em.persist(ordAcquisition);
        em.flush();
        ordAcquisitionChar.setOrdAcquisition(ordAcquisition);
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);
        Long ordAcquisitionId = ordAcquisition.getId();

        // Get all the ordAcquisitionCharList where ordAcquisition equals to ordAcquisitionId
        defaultOrdAcquisitionCharShouldBeFound("ordAcquisitionId.equals=" + ordAcquisitionId);

        // Get all the ordAcquisitionCharList where ordAcquisition equals to (ordAcquisitionId + 1)
        defaultOrdAcquisitionCharShouldNotBeFound("ordAcquisitionId.equals=" + (ordAcquisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdAcquisitionCharShouldBeFound(String filter) throws Exception {
        restOrdAcquisitionCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordAcquisitionChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restOrdAcquisitionCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdAcquisitionCharShouldNotBeFound(String filter) throws Exception {
        restOrdAcquisitionCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdAcquisitionCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdAcquisitionChar() throws Exception {
        // Get the ordAcquisitionChar
        restOrdAcquisitionCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdAcquisitionChar() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();

        // Update the ordAcquisitionChar
        OrdAcquisitionChar updatedOrdAcquisitionChar = ordAcquisitionCharRepository.findById(ordAcquisitionChar.getId()).get();
        // Disconnect from session so that the updates on updatedOrdAcquisitionChar are not directly saved in db
        em.detach(updatedOrdAcquisitionChar);
        updatedOrdAcquisitionChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(updatedOrdAcquisitionChar);

        restOrdAcquisitionCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordAcquisitionCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
        OrdAcquisitionChar testOrdAcquisitionChar = ordAcquisitionCharList.get(ordAcquisitionCharList.size() - 1);
        assertThat(testOrdAcquisitionChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdAcquisitionChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdAcquisitionChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdAcquisitionChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdAcquisitionChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrdAcquisitionChar() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();
        ordAcquisitionChar.setId(count.incrementAndGet());

        // Create the OrdAcquisitionChar
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdAcquisitionCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordAcquisitionCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdAcquisitionChar() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();
        ordAcquisitionChar.setId(count.incrementAndGet());

        // Create the OrdAcquisitionChar
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdAcquisitionChar() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();
        ordAcquisitionChar.setId(count.incrementAndGet());

        // Create the OrdAcquisitionChar
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionCharMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdAcquisitionCharWithPatch() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();

        // Update the ordAcquisitionChar using partial update
        OrdAcquisitionChar partialUpdatedOrdAcquisitionChar = new OrdAcquisitionChar();
        partialUpdatedOrdAcquisitionChar.setId(ordAcquisitionChar.getId());

        partialUpdatedOrdAcquisitionChar.valueType(UPDATED_VALUE_TYPE).createdBy(UPDATED_CREATED_BY);

        restOrdAcquisitionCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdAcquisitionChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdAcquisitionChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
        OrdAcquisitionChar testOrdAcquisitionChar = ordAcquisitionCharList.get(ordAcquisitionCharList.size() - 1);
        assertThat(testOrdAcquisitionChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdAcquisitionChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdAcquisitionChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdAcquisitionChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdAcquisitionChar.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrdAcquisitionCharWithPatch() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();

        // Update the ordAcquisitionChar using partial update
        OrdAcquisitionChar partialUpdatedOrdAcquisitionChar = new OrdAcquisitionChar();
        partialUpdatedOrdAcquisitionChar.setId(ordAcquisitionChar.getId());

        partialUpdatedOrdAcquisitionChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restOrdAcquisitionCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdAcquisitionChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdAcquisitionChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
        OrdAcquisitionChar testOrdAcquisitionChar = ordAcquisitionCharList.get(ordAcquisitionCharList.size() - 1);
        assertThat(testOrdAcquisitionChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdAcquisitionChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdAcquisitionChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdAcquisitionChar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdAcquisitionChar.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdAcquisitionChar() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();
        ordAcquisitionChar.setId(count.incrementAndGet());

        // Create the OrdAcquisitionChar
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdAcquisitionCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordAcquisitionCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdAcquisitionChar() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();
        ordAcquisitionChar.setId(count.incrementAndGet());

        // Create the OrdAcquisitionChar
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdAcquisitionChar() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionCharRepository.findAll().size();
        ordAcquisitionChar.setId(count.incrementAndGet());

        // Create the OrdAcquisitionChar
        OrdAcquisitionCharDTO ordAcquisitionCharDTO = ordAcquisitionCharMapper.toDto(ordAcquisitionChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdAcquisitionChar in the database
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdAcquisitionChar() throws Exception {
        // Initialize the database
        ordAcquisitionCharRepository.saveAndFlush(ordAcquisitionChar);

        int databaseSizeBeforeDelete = ordAcquisitionCharRepository.findAll().size();

        // Delete the ordAcquisitionChar
        restOrdAcquisitionCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordAcquisitionChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdAcquisitionChar> ordAcquisitionCharList = ordAcquisitionCharRepository.findAll();
        assertThat(ordAcquisitionCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
