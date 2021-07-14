package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderPrice;
import com.apptium.order.domain.OrdPriceAlteration;
import com.apptium.order.domain.OrdPriceAmount;
import com.apptium.order.repository.OrdPriceAlterationRepository;
import com.apptium.order.service.criteria.OrdPriceAlterationCriteria;
import com.apptium.order.service.dto.OrdPriceAlterationDTO;
import com.apptium.order.service.mapper.OrdPriceAlterationMapper;
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
 * Integration tests for the {@link OrdPriceAlterationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdPriceAlterationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_OF_MEASURE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASURE = "BBBBBBBBBB";

    private static final String DEFAULT_RECURRING_CHARGE_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_RECURRING_CHARGE_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-price-alterations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdPriceAlterationRepository ordPriceAlterationRepository;

    @Autowired
    private OrdPriceAlterationMapper ordPriceAlterationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdPriceAlterationMockMvc;

    private OrdPriceAlteration ordPriceAlteration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPriceAlteration createEntity(EntityManager em) {
        OrdPriceAlteration ordPriceAlteration = new OrdPriceAlteration()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .priceType(DEFAULT_PRICE_TYPE)
            .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE)
            .recurringChargePeriod(DEFAULT_RECURRING_CHARGE_PERIOD)
            .applicationDuration(DEFAULT_APPLICATION_DURATION)
            .priority(DEFAULT_PRIORITY);
        return ordPriceAlteration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPriceAlteration createUpdatedEntity(EntityManager em) {
        OrdPriceAlteration ordPriceAlteration = new OrdPriceAlteration()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priceType(UPDATED_PRICE_TYPE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .recurringChargePeriod(UPDATED_RECURRING_CHARGE_PERIOD)
            .applicationDuration(UPDATED_APPLICATION_DURATION)
            .priority(UPDATED_PRIORITY);
        return ordPriceAlteration;
    }

    @BeforeEach
    public void initTest() {
        ordPriceAlteration = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdPriceAlteration() throws Exception {
        int databaseSizeBeforeCreate = ordPriceAlterationRepository.findAll().size();
        // Create the OrdPriceAlteration
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);
        restOrdPriceAlterationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeCreate + 1);
        OrdPriceAlteration testOrdPriceAlteration = ordPriceAlterationList.get(ordPriceAlterationList.size() - 1);
        assertThat(testOrdPriceAlteration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdPriceAlteration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrdPriceAlteration.getPriceType()).isEqualTo(DEFAULT_PRICE_TYPE);
        assertThat(testOrdPriceAlteration.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
        assertThat(testOrdPriceAlteration.getRecurringChargePeriod()).isEqualTo(DEFAULT_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdPriceAlteration.getApplicationDuration()).isEqualTo(DEFAULT_APPLICATION_DURATION);
        assertThat(testOrdPriceAlteration.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void createOrdPriceAlterationWithExistingId() throws Exception {
        // Create the OrdPriceAlteration with an existing ID
        ordPriceAlteration.setId(1L);
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);

        int databaseSizeBeforeCreate = ordPriceAlterationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdPriceAlterationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterations() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList
        restOrdPriceAlterationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPriceAlteration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priceType").value(hasItem(DEFAULT_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE)))
            .andExpect(jsonPath("$.[*].recurringChargePeriod").value(hasItem(DEFAULT_RECURRING_CHARGE_PERIOD)))
            .andExpect(jsonPath("$.[*].applicationDuration").value(hasItem(DEFAULT_APPLICATION_DURATION)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)));
    }

    @Test
    @Transactional
    void getOrdPriceAlteration() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get the ordPriceAlteration
        restOrdPriceAlterationMockMvc
            .perform(get(ENTITY_API_URL_ID, ordPriceAlteration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordPriceAlteration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.priceType").value(DEFAULT_PRICE_TYPE))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE))
            .andExpect(jsonPath("$.recurringChargePeriod").value(DEFAULT_RECURRING_CHARGE_PERIOD))
            .andExpect(jsonPath("$.applicationDuration").value(DEFAULT_APPLICATION_DURATION))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY));
    }

    @Test
    @Transactional
    void getOrdPriceAlterationsByIdFiltering() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        Long id = ordPriceAlteration.getId();

        defaultOrdPriceAlterationShouldBeFound("id.equals=" + id);
        defaultOrdPriceAlterationShouldNotBeFound("id.notEquals=" + id);

        defaultOrdPriceAlterationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdPriceAlterationShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdPriceAlterationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdPriceAlterationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where name equals to DEFAULT_NAME
        defaultOrdPriceAlterationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordPriceAlterationList where name equals to UPDATED_NAME
        defaultOrdPriceAlterationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where name not equals to DEFAULT_NAME
        defaultOrdPriceAlterationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordPriceAlterationList where name not equals to UPDATED_NAME
        defaultOrdPriceAlterationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdPriceAlterationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordPriceAlterationList where name equals to UPDATED_NAME
        defaultOrdPriceAlterationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where name is not null
        defaultOrdPriceAlterationShouldBeFound("name.specified=true");

        // Get all the ordPriceAlterationList where name is null
        defaultOrdPriceAlterationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where name contains DEFAULT_NAME
        defaultOrdPriceAlterationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordPriceAlterationList where name contains UPDATED_NAME
        defaultOrdPriceAlterationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where name does not contain DEFAULT_NAME
        defaultOrdPriceAlterationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordPriceAlterationList where name does not contain UPDATED_NAME
        defaultOrdPriceAlterationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where description equals to DEFAULT_DESCRIPTION
        defaultOrdPriceAlterationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ordPriceAlterationList where description equals to UPDATED_DESCRIPTION
        defaultOrdPriceAlterationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where description not equals to DEFAULT_DESCRIPTION
        defaultOrdPriceAlterationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the ordPriceAlterationList where description not equals to UPDATED_DESCRIPTION
        defaultOrdPriceAlterationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOrdPriceAlterationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ordPriceAlterationList where description equals to UPDATED_DESCRIPTION
        defaultOrdPriceAlterationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where description is not null
        defaultOrdPriceAlterationShouldBeFound("description.specified=true");

        // Get all the ordPriceAlterationList where description is null
        defaultOrdPriceAlterationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where description contains DEFAULT_DESCRIPTION
        defaultOrdPriceAlterationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ordPriceAlterationList where description contains UPDATED_DESCRIPTION
        defaultOrdPriceAlterationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where description does not contain DEFAULT_DESCRIPTION
        defaultOrdPriceAlterationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ordPriceAlterationList where description does not contain UPDATED_DESCRIPTION
        defaultOrdPriceAlterationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priceType equals to DEFAULT_PRICE_TYPE
        defaultOrdPriceAlterationShouldBeFound("priceType.equals=" + DEFAULT_PRICE_TYPE);

        // Get all the ordPriceAlterationList where priceType equals to UPDATED_PRICE_TYPE
        defaultOrdPriceAlterationShouldNotBeFound("priceType.equals=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priceType not equals to DEFAULT_PRICE_TYPE
        defaultOrdPriceAlterationShouldNotBeFound("priceType.notEquals=" + DEFAULT_PRICE_TYPE);

        // Get all the ordPriceAlterationList where priceType not equals to UPDATED_PRICE_TYPE
        defaultOrdPriceAlterationShouldBeFound("priceType.notEquals=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priceType in DEFAULT_PRICE_TYPE or UPDATED_PRICE_TYPE
        defaultOrdPriceAlterationShouldBeFound("priceType.in=" + DEFAULT_PRICE_TYPE + "," + UPDATED_PRICE_TYPE);

        // Get all the ordPriceAlterationList where priceType equals to UPDATED_PRICE_TYPE
        defaultOrdPriceAlterationShouldNotBeFound("priceType.in=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priceType is not null
        defaultOrdPriceAlterationShouldBeFound("priceType.specified=true");

        // Get all the ordPriceAlterationList where priceType is null
        defaultOrdPriceAlterationShouldNotBeFound("priceType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriceTypeContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priceType contains DEFAULT_PRICE_TYPE
        defaultOrdPriceAlterationShouldBeFound("priceType.contains=" + DEFAULT_PRICE_TYPE);

        // Get all the ordPriceAlterationList where priceType contains UPDATED_PRICE_TYPE
        defaultOrdPriceAlterationShouldNotBeFound("priceType.contains=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriceTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priceType does not contain DEFAULT_PRICE_TYPE
        defaultOrdPriceAlterationShouldNotBeFound("priceType.doesNotContain=" + DEFAULT_PRICE_TYPE);

        // Get all the ordPriceAlterationList where priceType does not contain UPDATED_PRICE_TYPE
        defaultOrdPriceAlterationShouldBeFound("priceType.doesNotContain=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByUnitOfMeasureIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where unitOfMeasure equals to DEFAULT_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldBeFound("unitOfMeasure.equals=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordPriceAlterationList where unitOfMeasure equals to UPDATED_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldNotBeFound("unitOfMeasure.equals=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByUnitOfMeasureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where unitOfMeasure not equals to DEFAULT_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldNotBeFound("unitOfMeasure.notEquals=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordPriceAlterationList where unitOfMeasure not equals to UPDATED_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldBeFound("unitOfMeasure.notEquals=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByUnitOfMeasureIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where unitOfMeasure in DEFAULT_UNIT_OF_MEASURE or UPDATED_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldBeFound("unitOfMeasure.in=" + DEFAULT_UNIT_OF_MEASURE + "," + UPDATED_UNIT_OF_MEASURE);

        // Get all the ordPriceAlterationList where unitOfMeasure equals to UPDATED_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldNotBeFound("unitOfMeasure.in=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByUnitOfMeasureIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where unitOfMeasure is not null
        defaultOrdPriceAlterationShouldBeFound("unitOfMeasure.specified=true");

        // Get all the ordPriceAlterationList where unitOfMeasure is null
        defaultOrdPriceAlterationShouldNotBeFound("unitOfMeasure.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByUnitOfMeasureContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where unitOfMeasure contains DEFAULT_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldBeFound("unitOfMeasure.contains=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordPriceAlterationList where unitOfMeasure contains UPDATED_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldNotBeFound("unitOfMeasure.contains=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByUnitOfMeasureNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where unitOfMeasure does not contain DEFAULT_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldNotBeFound("unitOfMeasure.doesNotContain=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordPriceAlterationList where unitOfMeasure does not contain UPDATED_UNIT_OF_MEASURE
        defaultOrdPriceAlterationShouldBeFound("unitOfMeasure.doesNotContain=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByRecurringChargePeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where recurringChargePeriod equals to DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldBeFound("recurringChargePeriod.equals=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordPriceAlterationList where recurringChargePeriod equals to UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldNotBeFound("recurringChargePeriod.equals=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByRecurringChargePeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where recurringChargePeriod not equals to DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldNotBeFound("recurringChargePeriod.notEquals=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordPriceAlterationList where recurringChargePeriod not equals to UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldBeFound("recurringChargePeriod.notEquals=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByRecurringChargePeriodIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where recurringChargePeriod in DEFAULT_RECURRING_CHARGE_PERIOD or UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldBeFound(
            "recurringChargePeriod.in=" + DEFAULT_RECURRING_CHARGE_PERIOD + "," + UPDATED_RECURRING_CHARGE_PERIOD
        );

        // Get all the ordPriceAlterationList where recurringChargePeriod equals to UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldNotBeFound("recurringChargePeriod.in=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByRecurringChargePeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where recurringChargePeriod is not null
        defaultOrdPriceAlterationShouldBeFound("recurringChargePeriod.specified=true");

        // Get all the ordPriceAlterationList where recurringChargePeriod is null
        defaultOrdPriceAlterationShouldNotBeFound("recurringChargePeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByRecurringChargePeriodContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where recurringChargePeriod contains DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldBeFound("recurringChargePeriod.contains=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordPriceAlterationList where recurringChargePeriod contains UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldNotBeFound("recurringChargePeriod.contains=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByRecurringChargePeriodNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where recurringChargePeriod does not contain DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldNotBeFound("recurringChargePeriod.doesNotContain=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordPriceAlterationList where recurringChargePeriod does not contain UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdPriceAlterationShouldBeFound("recurringChargePeriod.doesNotContain=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByApplicationDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where applicationDuration equals to DEFAULT_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldBeFound("applicationDuration.equals=" + DEFAULT_APPLICATION_DURATION);

        // Get all the ordPriceAlterationList where applicationDuration equals to UPDATED_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldNotBeFound("applicationDuration.equals=" + UPDATED_APPLICATION_DURATION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByApplicationDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where applicationDuration not equals to DEFAULT_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldNotBeFound("applicationDuration.notEquals=" + DEFAULT_APPLICATION_DURATION);

        // Get all the ordPriceAlterationList where applicationDuration not equals to UPDATED_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldBeFound("applicationDuration.notEquals=" + UPDATED_APPLICATION_DURATION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByApplicationDurationIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where applicationDuration in DEFAULT_APPLICATION_DURATION or UPDATED_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldBeFound(
            "applicationDuration.in=" + DEFAULT_APPLICATION_DURATION + "," + UPDATED_APPLICATION_DURATION
        );

        // Get all the ordPriceAlterationList where applicationDuration equals to UPDATED_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldNotBeFound("applicationDuration.in=" + UPDATED_APPLICATION_DURATION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByApplicationDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where applicationDuration is not null
        defaultOrdPriceAlterationShouldBeFound("applicationDuration.specified=true");

        // Get all the ordPriceAlterationList where applicationDuration is null
        defaultOrdPriceAlterationShouldNotBeFound("applicationDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByApplicationDurationContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where applicationDuration contains DEFAULT_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldBeFound("applicationDuration.contains=" + DEFAULT_APPLICATION_DURATION);

        // Get all the ordPriceAlterationList where applicationDuration contains UPDATED_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldNotBeFound("applicationDuration.contains=" + UPDATED_APPLICATION_DURATION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByApplicationDurationNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where applicationDuration does not contain DEFAULT_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldNotBeFound("applicationDuration.doesNotContain=" + DEFAULT_APPLICATION_DURATION);

        // Get all the ordPriceAlterationList where applicationDuration does not contain UPDATED_APPLICATION_DURATION
        defaultOrdPriceAlterationShouldBeFound("applicationDuration.doesNotContain=" + UPDATED_APPLICATION_DURATION);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priority equals to DEFAULT_PRIORITY
        defaultOrdPriceAlterationShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the ordPriceAlterationList where priority equals to UPDATED_PRIORITY
        defaultOrdPriceAlterationShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priority not equals to DEFAULT_PRIORITY
        defaultOrdPriceAlterationShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the ordPriceAlterationList where priority not equals to UPDATED_PRIORITY
        defaultOrdPriceAlterationShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultOrdPriceAlterationShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the ordPriceAlterationList where priority equals to UPDATED_PRIORITY
        defaultOrdPriceAlterationShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priority is not null
        defaultOrdPriceAlterationShouldBeFound("priority.specified=true");

        // Get all the ordPriceAlterationList where priority is null
        defaultOrdPriceAlterationShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriorityContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priority contains DEFAULT_PRIORITY
        defaultOrdPriceAlterationShouldBeFound("priority.contains=" + DEFAULT_PRIORITY);

        // Get all the ordPriceAlterationList where priority contains UPDATED_PRIORITY
        defaultOrdPriceAlterationShouldNotBeFound("priority.contains=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByPriorityNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        // Get all the ordPriceAlterationList where priority does not contain DEFAULT_PRIORITY
        defaultOrdPriceAlterationShouldNotBeFound("priority.doesNotContain=" + DEFAULT_PRIORITY);

        // Get all the ordPriceAlterationList where priority does not contain UPDATED_PRIORITY
        defaultOrdPriceAlterationShouldBeFound("priority.doesNotContain=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByOrdPriceAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);
        OrdPriceAmount ordPriceAmount = OrdPriceAmountResourceIT.createEntity(em);
        em.persist(ordPriceAmount);
        em.flush();
        ordPriceAlteration.setOrdPriceAmount(ordPriceAmount);
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);
        Long ordPriceAmountId = ordPriceAmount.getId();

        // Get all the ordPriceAlterationList where ordPriceAmount equals to ordPriceAmountId
        defaultOrdPriceAlterationShouldBeFound("ordPriceAmountId.equals=" + ordPriceAmountId);

        // Get all the ordPriceAlterationList where ordPriceAmount equals to (ordPriceAmountId + 1)
        defaultOrdPriceAlterationShouldNotBeFound("ordPriceAmountId.equals=" + (ordPriceAmountId + 1));
    }

    @Test
    @Transactional
    void getAllOrdPriceAlterationsByOrdOrderPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);
        OrdOrderPrice ordOrderPrice = OrdOrderPriceResourceIT.createEntity(em);
        em.persist(ordOrderPrice);
        em.flush();
        ordPriceAlteration.setOrdOrderPrice(ordOrderPrice);
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);
        Long ordOrderPriceId = ordOrderPrice.getId();

        // Get all the ordPriceAlterationList where ordOrderPrice equals to ordOrderPriceId
        defaultOrdPriceAlterationShouldBeFound("ordOrderPriceId.equals=" + ordOrderPriceId);

        // Get all the ordPriceAlterationList where ordOrderPrice equals to (ordOrderPriceId + 1)
        defaultOrdPriceAlterationShouldNotBeFound("ordOrderPriceId.equals=" + (ordOrderPriceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdPriceAlterationShouldBeFound(String filter) throws Exception {
        restOrdPriceAlterationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPriceAlteration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priceType").value(hasItem(DEFAULT_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE)))
            .andExpect(jsonPath("$.[*].recurringChargePeriod").value(hasItem(DEFAULT_RECURRING_CHARGE_PERIOD)))
            .andExpect(jsonPath("$.[*].applicationDuration").value(hasItem(DEFAULT_APPLICATION_DURATION)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)));

        // Check, that the count call also returns 1
        restOrdPriceAlterationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdPriceAlterationShouldNotBeFound(String filter) throws Exception {
        restOrdPriceAlterationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdPriceAlterationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdPriceAlteration() throws Exception {
        // Get the ordPriceAlteration
        restOrdPriceAlterationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdPriceAlteration() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();

        // Update the ordPriceAlteration
        OrdPriceAlteration updatedOrdPriceAlteration = ordPriceAlterationRepository.findById(ordPriceAlteration.getId()).get();
        // Disconnect from session so that the updates on updatedOrdPriceAlteration are not directly saved in db
        em.detach(updatedOrdPriceAlteration);
        updatedOrdPriceAlteration
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priceType(UPDATED_PRICE_TYPE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .recurringChargePeriod(UPDATED_RECURRING_CHARGE_PERIOD)
            .applicationDuration(UPDATED_APPLICATION_DURATION)
            .priority(UPDATED_PRIORITY);
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(updatedOrdPriceAlteration);

        restOrdPriceAlterationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPriceAlterationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
        OrdPriceAlteration testOrdPriceAlteration = ordPriceAlterationList.get(ordPriceAlterationList.size() - 1);
        assertThat(testOrdPriceAlteration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdPriceAlteration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdPriceAlteration.getPriceType()).isEqualTo(UPDATED_PRICE_TYPE);
        assertThat(testOrdPriceAlteration.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testOrdPriceAlteration.getRecurringChargePeriod()).isEqualTo(UPDATED_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdPriceAlteration.getApplicationDuration()).isEqualTo(UPDATED_APPLICATION_DURATION);
        assertThat(testOrdPriceAlteration.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void putNonExistingOrdPriceAlteration() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();
        ordPriceAlteration.setId(count.incrementAndGet());

        // Create the OrdPriceAlteration
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPriceAlterationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPriceAlterationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdPriceAlteration() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();
        ordPriceAlteration.setId(count.incrementAndGet());

        // Create the OrdPriceAlteration
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAlterationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdPriceAlteration() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();
        ordPriceAlteration.setId(count.incrementAndGet());

        // Create the OrdPriceAlteration
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAlterationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdPriceAlterationWithPatch() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();

        // Update the ordPriceAlteration using partial update
        OrdPriceAlteration partialUpdatedOrdPriceAlteration = new OrdPriceAlteration();
        partialUpdatedOrdPriceAlteration.setId(ordPriceAlteration.getId());

        partialUpdatedOrdPriceAlteration.name(UPDATED_NAME).priceType(UPDATED_PRICE_TYPE).unitOfMeasure(UPDATED_UNIT_OF_MEASURE);

        restOrdPriceAlterationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPriceAlteration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPriceAlteration))
            )
            .andExpect(status().isOk());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
        OrdPriceAlteration testOrdPriceAlteration = ordPriceAlterationList.get(ordPriceAlterationList.size() - 1);
        assertThat(testOrdPriceAlteration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdPriceAlteration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrdPriceAlteration.getPriceType()).isEqualTo(UPDATED_PRICE_TYPE);
        assertThat(testOrdPriceAlteration.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testOrdPriceAlteration.getRecurringChargePeriod()).isEqualTo(DEFAULT_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdPriceAlteration.getApplicationDuration()).isEqualTo(DEFAULT_APPLICATION_DURATION);
        assertThat(testOrdPriceAlteration.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void fullUpdateOrdPriceAlterationWithPatch() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();

        // Update the ordPriceAlteration using partial update
        OrdPriceAlteration partialUpdatedOrdPriceAlteration = new OrdPriceAlteration();
        partialUpdatedOrdPriceAlteration.setId(ordPriceAlteration.getId());

        partialUpdatedOrdPriceAlteration
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priceType(UPDATED_PRICE_TYPE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .recurringChargePeriod(UPDATED_RECURRING_CHARGE_PERIOD)
            .applicationDuration(UPDATED_APPLICATION_DURATION)
            .priority(UPDATED_PRIORITY);

        restOrdPriceAlterationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPriceAlteration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPriceAlteration))
            )
            .andExpect(status().isOk());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
        OrdPriceAlteration testOrdPriceAlteration = ordPriceAlterationList.get(ordPriceAlterationList.size() - 1);
        assertThat(testOrdPriceAlteration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdPriceAlteration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdPriceAlteration.getPriceType()).isEqualTo(UPDATED_PRICE_TYPE);
        assertThat(testOrdPriceAlteration.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testOrdPriceAlteration.getRecurringChargePeriod()).isEqualTo(UPDATED_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdPriceAlteration.getApplicationDuration()).isEqualTo(UPDATED_APPLICATION_DURATION);
        assertThat(testOrdPriceAlteration.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void patchNonExistingOrdPriceAlteration() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();
        ordPriceAlteration.setId(count.incrementAndGet());

        // Create the OrdPriceAlteration
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPriceAlterationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordPriceAlterationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdPriceAlteration() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();
        ordPriceAlteration.setId(count.incrementAndGet());

        // Create the OrdPriceAlteration
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAlterationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdPriceAlteration() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAlterationRepository.findAll().size();
        ordPriceAlteration.setId(count.incrementAndGet());

        // Create the OrdPriceAlteration
        OrdPriceAlterationDTO ordPriceAlterationDTO = ordPriceAlterationMapper.toDto(ordPriceAlteration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAlterationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAlterationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPriceAlteration in the database
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdPriceAlteration() throws Exception {
        // Initialize the database
        ordPriceAlterationRepository.saveAndFlush(ordPriceAlteration);

        int databaseSizeBeforeDelete = ordPriceAlterationRepository.findAll().size();

        // Delete the ordPriceAlteration
        restOrdPriceAlterationMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordPriceAlteration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdPriceAlteration> ordPriceAlterationList = ordPriceAlterationRepository.findAll();
        assertThat(ordPriceAlterationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
