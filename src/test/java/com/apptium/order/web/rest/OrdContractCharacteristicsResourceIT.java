package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdContract;
import com.apptium.order.domain.OrdContractCharacteristics;
import com.apptium.order.repository.OrdContractCharacteristicsRepository;
import com.apptium.order.service.criteria.OrdContractCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdContractCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdContractCharacteristicsMapper;
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
 * Integration tests for the {@link OrdContractCharacteristicsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdContractCharacteristicsResourceIT {

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

    private static final String ENTITY_API_URL = "/api/ord-contract-characteristics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdContractCharacteristicsRepository ordContractCharacteristicsRepository;

    @Autowired
    private OrdContractCharacteristicsMapper ordContractCharacteristicsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdContractCharacteristicsMockMvc;

    private OrdContractCharacteristics ordContractCharacteristics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdContractCharacteristics createEntity(EntityManager em) {
        OrdContractCharacteristics ordContractCharacteristics = new OrdContractCharacteristics()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return ordContractCharacteristics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdContractCharacteristics createUpdatedEntity(EntityManager em) {
        OrdContractCharacteristics ordContractCharacteristics = new OrdContractCharacteristics()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        return ordContractCharacteristics;
    }

    @BeforeEach
    public void initTest() {
        ordContractCharacteristics = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdContractCharacteristics() throws Exception {
        int databaseSizeBeforeCreate = ordContractCharacteristicsRepository.findAll().size();
        // Create the OrdContractCharacteristics
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);
        restOrdContractCharacteristicsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeCreate + 1);
        OrdContractCharacteristics testOrdContractCharacteristics = ordContractCharacteristicsList.get(
            ordContractCharacteristicsList.size() - 1
        );
        assertThat(testOrdContractCharacteristics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdContractCharacteristics.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdContractCharacteristics.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testOrdContractCharacteristics.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrdContractCharacteristics.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createOrdContractCharacteristicsWithExistingId() throws Exception {
        // Create the OrdContractCharacteristics with an existing ID
        ordContractCharacteristics.setId(1L);
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);

        int databaseSizeBeforeCreate = ordContractCharacteristicsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdContractCharacteristicsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristics() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList
        restOrdContractCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordContractCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrdContractCharacteristics() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get the ordContractCharacteristics
        restOrdContractCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL_ID, ordContractCharacteristics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordContractCharacteristics.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOrdContractCharacteristicsByIdFiltering() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        Long id = ordContractCharacteristics.getId();

        defaultOrdContractCharacteristicsShouldBeFound("id.equals=" + id);
        defaultOrdContractCharacteristicsShouldNotBeFound("id.notEquals=" + id);

        defaultOrdContractCharacteristicsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdContractCharacteristicsShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdContractCharacteristicsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdContractCharacteristicsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where name equals to DEFAULT_NAME
        defaultOrdContractCharacteristicsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordContractCharacteristicsList where name equals to UPDATED_NAME
        defaultOrdContractCharacteristicsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where name not equals to DEFAULT_NAME
        defaultOrdContractCharacteristicsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordContractCharacteristicsList where name not equals to UPDATED_NAME
        defaultOrdContractCharacteristicsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdContractCharacteristicsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordContractCharacteristicsList where name equals to UPDATED_NAME
        defaultOrdContractCharacteristicsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where name is not null
        defaultOrdContractCharacteristicsShouldBeFound("name.specified=true");

        // Get all the ordContractCharacteristicsList where name is null
        defaultOrdContractCharacteristicsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where name contains DEFAULT_NAME
        defaultOrdContractCharacteristicsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordContractCharacteristicsList where name contains UPDATED_NAME
        defaultOrdContractCharacteristicsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where name does not contain DEFAULT_NAME
        defaultOrdContractCharacteristicsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordContractCharacteristicsList where name does not contain UPDATED_NAME
        defaultOrdContractCharacteristicsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where value equals to DEFAULT_VALUE
        defaultOrdContractCharacteristicsShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the ordContractCharacteristicsList where value equals to UPDATED_VALUE
        defaultOrdContractCharacteristicsShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where value not equals to DEFAULT_VALUE
        defaultOrdContractCharacteristicsShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the ordContractCharacteristicsList where value not equals to UPDATED_VALUE
        defaultOrdContractCharacteristicsShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOrdContractCharacteristicsShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the ordContractCharacteristicsList where value equals to UPDATED_VALUE
        defaultOrdContractCharacteristicsShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where value is not null
        defaultOrdContractCharacteristicsShouldBeFound("value.specified=true");

        // Get all the ordContractCharacteristicsList where value is null
        defaultOrdContractCharacteristicsShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where value contains DEFAULT_VALUE
        defaultOrdContractCharacteristicsShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the ordContractCharacteristicsList where value contains UPDATED_VALUE
        defaultOrdContractCharacteristicsShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where value does not contain DEFAULT_VALUE
        defaultOrdContractCharacteristicsShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the ordContractCharacteristicsList where value does not contain UPDATED_VALUE
        defaultOrdContractCharacteristicsShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where valueType equals to DEFAULT_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordContractCharacteristicsList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordContractCharacteristicsList where valueType not equals to UPDATED_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the ordContractCharacteristicsList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where valueType is not null
        defaultOrdContractCharacteristicsShouldBeFound("valueType.specified=true");

        // Get all the ordContractCharacteristicsList where valueType is null
        defaultOrdContractCharacteristicsShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where valueType contains DEFAULT_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the ordContractCharacteristicsList where valueType contains UPDATED_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the ordContractCharacteristicsList where valueType does not contain UPDATED_VALUE_TYPE
        defaultOrdContractCharacteristicsShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrdContractCharacteristicsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the ordContractCharacteristicsList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdContractCharacteristicsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOrdContractCharacteristicsShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the ordContractCharacteristicsList where createdBy not equals to UPDATED_CREATED_BY
        defaultOrdContractCharacteristicsShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrdContractCharacteristicsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the ordContractCharacteristicsList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdContractCharacteristicsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdBy is not null
        defaultOrdContractCharacteristicsShouldBeFound("createdBy.specified=true");

        // Get all the ordContractCharacteristicsList where createdBy is null
        defaultOrdContractCharacteristicsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdBy contains DEFAULT_CREATED_BY
        defaultOrdContractCharacteristicsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the ordContractCharacteristicsList where createdBy contains UPDATED_CREATED_BY
        defaultOrdContractCharacteristicsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrdContractCharacteristicsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the ordContractCharacteristicsList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrdContractCharacteristicsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOrdContractCharacteristicsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the ordContractCharacteristicsList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdContractCharacteristicsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOrdContractCharacteristicsShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the ordContractCharacteristicsList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOrdContractCharacteristicsShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOrdContractCharacteristicsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the ordContractCharacteristicsList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdContractCharacteristicsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        // Get all the ordContractCharacteristicsList where createdDate is not null
        defaultOrdContractCharacteristicsShouldBeFound("createdDate.specified=true");

        // Get all the ordContractCharacteristicsList where createdDate is null
        defaultOrdContractCharacteristicsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractCharacteristicsByOrdContractIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);
        OrdContract ordContract = OrdContractResourceIT.createEntity(em);
        em.persist(ordContract);
        em.flush();
        ordContractCharacteristics.setOrdContract(ordContract);
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);
        Long ordContractId = ordContract.getId();

        // Get all the ordContractCharacteristicsList where ordContract equals to ordContractId
        defaultOrdContractCharacteristicsShouldBeFound("ordContractId.equals=" + ordContractId);

        // Get all the ordContractCharacteristicsList where ordContract equals to (ordContractId + 1)
        defaultOrdContractCharacteristicsShouldNotBeFound("ordContractId.equals=" + (ordContractId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdContractCharacteristicsShouldBeFound(String filter) throws Exception {
        restOrdContractCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordContractCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restOrdContractCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdContractCharacteristicsShouldNotBeFound(String filter) throws Exception {
        restOrdContractCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdContractCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdContractCharacteristics() throws Exception {
        // Get the ordContractCharacteristics
        restOrdContractCharacteristicsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdContractCharacteristics() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();

        // Update the ordContractCharacteristics
        OrdContractCharacteristics updatedOrdContractCharacteristics = ordContractCharacteristicsRepository
            .findById(ordContractCharacteristics.getId())
            .get();
        // Disconnect from session so that the updates on updatedOrdContractCharacteristics are not directly saved in db
        em.detach(updatedOrdContractCharacteristics);
        updatedOrdContractCharacteristics
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(
            updatedOrdContractCharacteristics
        );

        restOrdContractCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordContractCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdContractCharacteristics testOrdContractCharacteristics = ordContractCharacteristicsList.get(
            ordContractCharacteristicsList.size() - 1
        );
        assertThat(testOrdContractCharacteristics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdContractCharacteristics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdContractCharacteristics.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdContractCharacteristics.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdContractCharacteristics.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrdContractCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();
        ordContractCharacteristics.setId(count.incrementAndGet());

        // Create the OrdContractCharacteristics
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdContractCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordContractCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdContractCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();
        ordContractCharacteristics.setId(count.incrementAndGet());

        // Create the OrdContractCharacteristics
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdContractCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();
        ordContractCharacteristics.setId(count.incrementAndGet());

        // Create the OrdContractCharacteristics
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdContractCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();

        // Update the ordContractCharacteristics using partial update
        OrdContractCharacteristics partialUpdatedOrdContractCharacteristics = new OrdContractCharacteristics();
        partialUpdatedOrdContractCharacteristics.setId(ordContractCharacteristics.getId());

        partialUpdatedOrdContractCharacteristics.value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);

        restOrdContractCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdContractCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdContractCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdContractCharacteristics testOrdContractCharacteristics = ordContractCharacteristicsList.get(
            ordContractCharacteristicsList.size() - 1
        );
        assertThat(testOrdContractCharacteristics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdContractCharacteristics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdContractCharacteristics.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdContractCharacteristics.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrdContractCharacteristics.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrdContractCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();

        // Update the ordContractCharacteristics using partial update
        OrdContractCharacteristics partialUpdatedOrdContractCharacteristics = new OrdContractCharacteristics();
        partialUpdatedOrdContractCharacteristics.setId(ordContractCharacteristics.getId());

        partialUpdatedOrdContractCharacteristics
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restOrdContractCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdContractCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdContractCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdContractCharacteristics testOrdContractCharacteristics = ordContractCharacteristicsList.get(
            ordContractCharacteristicsList.size() - 1
        );
        assertThat(testOrdContractCharacteristics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdContractCharacteristics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdContractCharacteristics.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOrdContractCharacteristics.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdContractCharacteristics.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdContractCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();
        ordContractCharacteristics.setId(count.incrementAndGet());

        // Create the OrdContractCharacteristics
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdContractCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordContractCharacteristicsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdContractCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();
        ordContractCharacteristics.setId(count.incrementAndGet());

        // Create the OrdContractCharacteristics
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdContractCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordContractCharacteristicsRepository.findAll().size();
        ordContractCharacteristics.setId(count.incrementAndGet());

        // Create the OrdContractCharacteristics
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO = ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContractCharacteristicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdContractCharacteristics in the database
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdContractCharacteristics() throws Exception {
        // Initialize the database
        ordContractCharacteristicsRepository.saveAndFlush(ordContractCharacteristics);

        int databaseSizeBeforeDelete = ordContractCharacteristicsRepository.findAll().size();

        // Delete the ordContractCharacteristics
        restOrdContractCharacteristicsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordContractCharacteristics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdContractCharacteristics> ordContractCharacteristicsList = ordContractCharacteristicsRepository.findAll();
        assertThat(ordContractCharacteristicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
