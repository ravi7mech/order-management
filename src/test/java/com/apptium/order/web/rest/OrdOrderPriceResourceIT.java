package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdOrderPrice;
import com.apptium.order.domain.OrdPriceAlteration;
import com.apptium.order.domain.OrdPriceAmount;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdOrderPriceRepository;
import com.apptium.order.service.criteria.OrdOrderPriceCriteria;
import com.apptium.order.service.dto.OrdOrderPriceDTO;
import com.apptium.order.service.mapper.OrdOrderPriceMapper;
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
 * Integration tests for the {@link OrdOrderPriceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdOrderPriceResourceIT {

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

    private static final Long DEFAULT_PRICE_ID = 1L;
    private static final Long UPDATED_PRICE_ID = 2L;
    private static final Long SMALLER_PRICE_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ord-order-prices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdOrderPriceRepository ordOrderPriceRepository;

    @Autowired
    private OrdOrderPriceMapper ordOrderPriceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdOrderPriceMockMvc;

    private OrdOrderPrice ordOrderPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderPrice createEntity(EntityManager em) {
        OrdOrderPrice ordOrderPrice = new OrdOrderPrice()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .priceType(DEFAULT_PRICE_TYPE)
            .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE)
            .recurringChargePeriod(DEFAULT_RECURRING_CHARGE_PERIOD)
            .priceId(DEFAULT_PRICE_ID);
        return ordOrderPrice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderPrice createUpdatedEntity(EntityManager em) {
        OrdOrderPrice ordOrderPrice = new OrdOrderPrice()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priceType(UPDATED_PRICE_TYPE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .recurringChargePeriod(UPDATED_RECURRING_CHARGE_PERIOD)
            .priceId(UPDATED_PRICE_ID);
        return ordOrderPrice;
    }

    @BeforeEach
    public void initTest() {
        ordOrderPrice = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdOrderPrice() throws Exception {
        int databaseSizeBeforeCreate = ordOrderPriceRepository.findAll().size();
        // Create the OrdOrderPrice
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);
        restOrdOrderPriceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeCreate + 1);
        OrdOrderPrice testOrdOrderPrice = ordOrderPriceList.get(ordOrderPriceList.size() - 1);
        assertThat(testOrdOrderPrice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdOrderPrice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrdOrderPrice.getPriceType()).isEqualTo(DEFAULT_PRICE_TYPE);
        assertThat(testOrdOrderPrice.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
        assertThat(testOrdOrderPrice.getRecurringChargePeriod()).isEqualTo(DEFAULT_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdOrderPrice.getPriceId()).isEqualTo(DEFAULT_PRICE_ID);
    }

    @Test
    @Transactional
    void createOrdOrderPriceWithExistingId() throws Exception {
        // Create the OrdOrderPrice with an existing ID
        ordOrderPrice.setId(1L);
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);

        int databaseSizeBeforeCreate = ordOrderPriceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdOrderPriceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdOrderPrices() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList
        restOrdOrderPriceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priceType").value(hasItem(DEFAULT_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE)))
            .andExpect(jsonPath("$.[*].recurringChargePeriod").value(hasItem(DEFAULT_RECURRING_CHARGE_PERIOD)))
            .andExpect(jsonPath("$.[*].priceId").value(hasItem(DEFAULT_PRICE_ID.intValue())));
    }

    @Test
    @Transactional
    void getOrdOrderPrice() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get the ordOrderPrice
        restOrdOrderPriceMockMvc
            .perform(get(ENTITY_API_URL_ID, ordOrderPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordOrderPrice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.priceType").value(DEFAULT_PRICE_TYPE))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE))
            .andExpect(jsonPath("$.recurringChargePeriod").value(DEFAULT_RECURRING_CHARGE_PERIOD))
            .andExpect(jsonPath("$.priceId").value(DEFAULT_PRICE_ID.intValue()));
    }

    @Test
    @Transactional
    void getOrdOrderPricesByIdFiltering() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        Long id = ordOrderPrice.getId();

        defaultOrdOrderPriceShouldBeFound("id.equals=" + id);
        defaultOrdOrderPriceShouldNotBeFound("id.notEquals=" + id);

        defaultOrdOrderPriceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdOrderPriceShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdOrderPriceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdOrderPriceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where name equals to DEFAULT_NAME
        defaultOrdOrderPriceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordOrderPriceList where name equals to UPDATED_NAME
        defaultOrdOrderPriceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where name not equals to DEFAULT_NAME
        defaultOrdOrderPriceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordOrderPriceList where name not equals to UPDATED_NAME
        defaultOrdOrderPriceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdOrderPriceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordOrderPriceList where name equals to UPDATED_NAME
        defaultOrdOrderPriceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where name is not null
        defaultOrdOrderPriceShouldBeFound("name.specified=true");

        // Get all the ordOrderPriceList where name is null
        defaultOrdOrderPriceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByNameContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where name contains DEFAULT_NAME
        defaultOrdOrderPriceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordOrderPriceList where name contains UPDATED_NAME
        defaultOrdOrderPriceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where name does not contain DEFAULT_NAME
        defaultOrdOrderPriceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordOrderPriceList where name does not contain UPDATED_NAME
        defaultOrdOrderPriceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where description equals to DEFAULT_DESCRIPTION
        defaultOrdOrderPriceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ordOrderPriceList where description equals to UPDATED_DESCRIPTION
        defaultOrdOrderPriceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where description not equals to DEFAULT_DESCRIPTION
        defaultOrdOrderPriceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the ordOrderPriceList where description not equals to UPDATED_DESCRIPTION
        defaultOrdOrderPriceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOrdOrderPriceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ordOrderPriceList where description equals to UPDATED_DESCRIPTION
        defaultOrdOrderPriceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where description is not null
        defaultOrdOrderPriceShouldBeFound("description.specified=true");

        // Get all the ordOrderPriceList where description is null
        defaultOrdOrderPriceShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where description contains DEFAULT_DESCRIPTION
        defaultOrdOrderPriceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ordOrderPriceList where description contains UPDATED_DESCRIPTION
        defaultOrdOrderPriceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where description does not contain DEFAULT_DESCRIPTION
        defaultOrdOrderPriceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ordOrderPriceList where description does not contain UPDATED_DESCRIPTION
        defaultOrdOrderPriceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceType equals to DEFAULT_PRICE_TYPE
        defaultOrdOrderPriceShouldBeFound("priceType.equals=" + DEFAULT_PRICE_TYPE);

        // Get all the ordOrderPriceList where priceType equals to UPDATED_PRICE_TYPE
        defaultOrdOrderPriceShouldNotBeFound("priceType.equals=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceType not equals to DEFAULT_PRICE_TYPE
        defaultOrdOrderPriceShouldNotBeFound("priceType.notEquals=" + DEFAULT_PRICE_TYPE);

        // Get all the ordOrderPriceList where priceType not equals to UPDATED_PRICE_TYPE
        defaultOrdOrderPriceShouldBeFound("priceType.notEquals=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceType in DEFAULT_PRICE_TYPE or UPDATED_PRICE_TYPE
        defaultOrdOrderPriceShouldBeFound("priceType.in=" + DEFAULT_PRICE_TYPE + "," + UPDATED_PRICE_TYPE);

        // Get all the ordOrderPriceList where priceType equals to UPDATED_PRICE_TYPE
        defaultOrdOrderPriceShouldNotBeFound("priceType.in=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceType is not null
        defaultOrdOrderPriceShouldBeFound("priceType.specified=true");

        // Get all the ordOrderPriceList where priceType is null
        defaultOrdOrderPriceShouldNotBeFound("priceType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceTypeContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceType contains DEFAULT_PRICE_TYPE
        defaultOrdOrderPriceShouldBeFound("priceType.contains=" + DEFAULT_PRICE_TYPE);

        // Get all the ordOrderPriceList where priceType contains UPDATED_PRICE_TYPE
        defaultOrdOrderPriceShouldNotBeFound("priceType.contains=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceType does not contain DEFAULT_PRICE_TYPE
        defaultOrdOrderPriceShouldNotBeFound("priceType.doesNotContain=" + DEFAULT_PRICE_TYPE);

        // Get all the ordOrderPriceList where priceType does not contain UPDATED_PRICE_TYPE
        defaultOrdOrderPriceShouldBeFound("priceType.doesNotContain=" + UPDATED_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByUnitOfMeasureIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where unitOfMeasure equals to DEFAULT_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldBeFound("unitOfMeasure.equals=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordOrderPriceList where unitOfMeasure equals to UPDATED_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldNotBeFound("unitOfMeasure.equals=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByUnitOfMeasureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where unitOfMeasure not equals to DEFAULT_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldNotBeFound("unitOfMeasure.notEquals=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordOrderPriceList where unitOfMeasure not equals to UPDATED_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldBeFound("unitOfMeasure.notEquals=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByUnitOfMeasureIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where unitOfMeasure in DEFAULT_UNIT_OF_MEASURE or UPDATED_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldBeFound("unitOfMeasure.in=" + DEFAULT_UNIT_OF_MEASURE + "," + UPDATED_UNIT_OF_MEASURE);

        // Get all the ordOrderPriceList where unitOfMeasure equals to UPDATED_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldNotBeFound("unitOfMeasure.in=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByUnitOfMeasureIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where unitOfMeasure is not null
        defaultOrdOrderPriceShouldBeFound("unitOfMeasure.specified=true");

        // Get all the ordOrderPriceList where unitOfMeasure is null
        defaultOrdOrderPriceShouldNotBeFound("unitOfMeasure.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByUnitOfMeasureContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where unitOfMeasure contains DEFAULT_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldBeFound("unitOfMeasure.contains=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordOrderPriceList where unitOfMeasure contains UPDATED_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldNotBeFound("unitOfMeasure.contains=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByUnitOfMeasureNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where unitOfMeasure does not contain DEFAULT_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldNotBeFound("unitOfMeasure.doesNotContain=" + DEFAULT_UNIT_OF_MEASURE);

        // Get all the ordOrderPriceList where unitOfMeasure does not contain UPDATED_UNIT_OF_MEASURE
        defaultOrdOrderPriceShouldBeFound("unitOfMeasure.doesNotContain=" + UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByRecurringChargePeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where recurringChargePeriod equals to DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldBeFound("recurringChargePeriod.equals=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordOrderPriceList where recurringChargePeriod equals to UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldNotBeFound("recurringChargePeriod.equals=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByRecurringChargePeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where recurringChargePeriod not equals to DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldNotBeFound("recurringChargePeriod.notEquals=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordOrderPriceList where recurringChargePeriod not equals to UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldBeFound("recurringChargePeriod.notEquals=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByRecurringChargePeriodIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where recurringChargePeriod in DEFAULT_RECURRING_CHARGE_PERIOD or UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldBeFound(
            "recurringChargePeriod.in=" + DEFAULT_RECURRING_CHARGE_PERIOD + "," + UPDATED_RECURRING_CHARGE_PERIOD
        );

        // Get all the ordOrderPriceList where recurringChargePeriod equals to UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldNotBeFound("recurringChargePeriod.in=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByRecurringChargePeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where recurringChargePeriod is not null
        defaultOrdOrderPriceShouldBeFound("recurringChargePeriod.specified=true");

        // Get all the ordOrderPriceList where recurringChargePeriod is null
        defaultOrdOrderPriceShouldNotBeFound("recurringChargePeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByRecurringChargePeriodContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where recurringChargePeriod contains DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldBeFound("recurringChargePeriod.contains=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordOrderPriceList where recurringChargePeriod contains UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldNotBeFound("recurringChargePeriod.contains=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByRecurringChargePeriodNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where recurringChargePeriod does not contain DEFAULT_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldNotBeFound("recurringChargePeriod.doesNotContain=" + DEFAULT_RECURRING_CHARGE_PERIOD);

        // Get all the ordOrderPriceList where recurringChargePeriod does not contain UPDATED_RECURRING_CHARGE_PERIOD
        defaultOrdOrderPriceShouldBeFound("recurringChargePeriod.doesNotContain=" + UPDATED_RECURRING_CHARGE_PERIOD);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId equals to DEFAULT_PRICE_ID
        defaultOrdOrderPriceShouldBeFound("priceId.equals=" + DEFAULT_PRICE_ID);

        // Get all the ordOrderPriceList where priceId equals to UPDATED_PRICE_ID
        defaultOrdOrderPriceShouldNotBeFound("priceId.equals=" + UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId not equals to DEFAULT_PRICE_ID
        defaultOrdOrderPriceShouldNotBeFound("priceId.notEquals=" + DEFAULT_PRICE_ID);

        // Get all the ordOrderPriceList where priceId not equals to UPDATED_PRICE_ID
        defaultOrdOrderPriceShouldBeFound("priceId.notEquals=" + UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId in DEFAULT_PRICE_ID or UPDATED_PRICE_ID
        defaultOrdOrderPriceShouldBeFound("priceId.in=" + DEFAULT_PRICE_ID + "," + UPDATED_PRICE_ID);

        // Get all the ordOrderPriceList where priceId equals to UPDATED_PRICE_ID
        defaultOrdOrderPriceShouldNotBeFound("priceId.in=" + UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId is not null
        defaultOrdOrderPriceShouldBeFound("priceId.specified=true");

        // Get all the ordOrderPriceList where priceId is null
        defaultOrdOrderPriceShouldNotBeFound("priceId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId is greater than or equal to DEFAULT_PRICE_ID
        defaultOrdOrderPriceShouldBeFound("priceId.greaterThanOrEqual=" + DEFAULT_PRICE_ID);

        // Get all the ordOrderPriceList where priceId is greater than or equal to UPDATED_PRICE_ID
        defaultOrdOrderPriceShouldNotBeFound("priceId.greaterThanOrEqual=" + UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId is less than or equal to DEFAULT_PRICE_ID
        defaultOrdOrderPriceShouldBeFound("priceId.lessThanOrEqual=" + DEFAULT_PRICE_ID);

        // Get all the ordOrderPriceList where priceId is less than or equal to SMALLER_PRICE_ID
        defaultOrdOrderPriceShouldNotBeFound("priceId.lessThanOrEqual=" + SMALLER_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId is less than DEFAULT_PRICE_ID
        defaultOrdOrderPriceShouldNotBeFound("priceId.lessThan=" + DEFAULT_PRICE_ID);

        // Get all the ordOrderPriceList where priceId is less than UPDATED_PRICE_ID
        defaultOrdOrderPriceShouldBeFound("priceId.lessThan=" + UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByPriceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        // Get all the ordOrderPriceList where priceId is greater than DEFAULT_PRICE_ID
        defaultOrdOrderPriceShouldNotBeFound("priceId.greaterThan=" + DEFAULT_PRICE_ID);

        // Get all the ordOrderPriceList where priceId is greater than SMALLER_PRICE_ID
        defaultOrdOrderPriceShouldBeFound("priceId.greaterThan=" + SMALLER_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByOrdPriceAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        OrdPriceAmount ordPriceAmount = OrdPriceAmountResourceIT.createEntity(em);
        em.persist(ordPriceAmount);
        em.flush();
        ordOrderPrice.setOrdPriceAmount(ordPriceAmount);
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        Long ordPriceAmountId = ordPriceAmount.getId();

        // Get all the ordOrderPriceList where ordPriceAmount equals to ordPriceAmountId
        defaultOrdOrderPriceShouldBeFound("ordPriceAmountId.equals=" + ordPriceAmountId);

        // Get all the ordOrderPriceList where ordPriceAmount equals to (ordPriceAmountId + 1)
        defaultOrdOrderPriceShouldNotBeFound("ordPriceAmountId.equals=" + (ordPriceAmountId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByOrdPriceAlterationIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        OrdPriceAlteration ordPriceAlteration = OrdPriceAlterationResourceIT.createEntity(em);
        em.persist(ordPriceAlteration);
        em.flush();
        ordOrderPrice.addOrdPriceAlteration(ordPriceAlteration);
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        Long ordPriceAlterationId = ordPriceAlteration.getId();

        // Get all the ordOrderPriceList where ordPriceAlteration equals to ordPriceAlterationId
        defaultOrdOrderPriceShouldBeFound("ordPriceAlterationId.equals=" + ordPriceAlterationId);

        // Get all the ordOrderPriceList where ordPriceAlteration equals to (ordPriceAlterationId + 1)
        defaultOrdOrderPriceShouldNotBeFound("ordPriceAlterationId.equals=" + (ordPriceAlterationId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordOrderPrice.setOrdProductOrder(ordProductOrder);
        ordProductOrder.setOrdOrderPrice(ordOrderPrice);
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordOrderPriceList where ordProductOrder equals to ordProductOrderId
        defaultOrdOrderPriceShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordOrderPriceList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdOrderPriceShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderPricesByOrdOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        OrdOrderItem ordOrderItem = OrdOrderItemResourceIT.createEntity(em);
        em.persist(ordOrderItem);
        em.flush();
        ordOrderPrice.setOrdOrderItem(ordOrderItem);
        ordOrderItem.setOrdOrderPrice(ordOrderPrice);
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);
        Long ordOrderItemId = ordOrderItem.getId();

        // Get all the ordOrderPriceList where ordOrderItem equals to ordOrderItemId
        defaultOrdOrderPriceShouldBeFound("ordOrderItemId.equals=" + ordOrderItemId);

        // Get all the ordOrderPriceList where ordOrderItem equals to (ordOrderItemId + 1)
        defaultOrdOrderPriceShouldNotBeFound("ordOrderItemId.equals=" + (ordOrderItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdOrderPriceShouldBeFound(String filter) throws Exception {
        restOrdOrderPriceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priceType").value(hasItem(DEFAULT_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE)))
            .andExpect(jsonPath("$.[*].recurringChargePeriod").value(hasItem(DEFAULT_RECURRING_CHARGE_PERIOD)))
            .andExpect(jsonPath("$.[*].priceId").value(hasItem(DEFAULT_PRICE_ID.intValue())));

        // Check, that the count call also returns 1
        restOrdOrderPriceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdOrderPriceShouldNotBeFound(String filter) throws Exception {
        restOrdOrderPriceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdOrderPriceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdOrderPrice() throws Exception {
        // Get the ordOrderPrice
        restOrdOrderPriceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdOrderPrice() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();

        // Update the ordOrderPrice
        OrdOrderPrice updatedOrdOrderPrice = ordOrderPriceRepository.findById(ordOrderPrice.getId()).get();
        // Disconnect from session so that the updates on updatedOrdOrderPrice are not directly saved in db
        em.detach(updatedOrdOrderPrice);
        updatedOrdOrderPrice
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priceType(UPDATED_PRICE_TYPE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .recurringChargePeriod(UPDATED_RECURRING_CHARGE_PERIOD)
            .priceId(UPDATED_PRICE_ID);
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(updatedOrdOrderPrice);

        restOrdOrderPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderPriceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderPrice testOrdOrderPrice = ordOrderPriceList.get(ordOrderPriceList.size() - 1);
        assertThat(testOrdOrderPrice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdOrderPrice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdOrderPrice.getPriceType()).isEqualTo(UPDATED_PRICE_TYPE);
        assertThat(testOrdOrderPrice.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testOrdOrderPrice.getRecurringChargePeriod()).isEqualTo(UPDATED_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdOrderPrice.getPriceId()).isEqualTo(UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void putNonExistingOrdOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();
        ordOrderPrice.setId(count.incrementAndGet());

        // Create the OrdOrderPrice
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderPriceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();
        ordOrderPrice.setId(count.incrementAndGet());

        // Create the OrdOrderPrice
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();
        ordOrderPrice.setId(count.incrementAndGet());

        // Create the OrdOrderPrice
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderPriceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdOrderPriceWithPatch() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();

        // Update the ordOrderPrice using partial update
        OrdOrderPrice partialUpdatedOrdOrderPrice = new OrdOrderPrice();
        partialUpdatedOrdOrderPrice.setId(ordOrderPrice.getId());

        partialUpdatedOrdOrderPrice.description(UPDATED_DESCRIPTION).priceId(UPDATED_PRICE_ID);

        restOrdOrderPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderPrice))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderPrice testOrdOrderPrice = ordOrderPriceList.get(ordOrderPriceList.size() - 1);
        assertThat(testOrdOrderPrice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdOrderPrice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdOrderPrice.getPriceType()).isEqualTo(DEFAULT_PRICE_TYPE);
        assertThat(testOrdOrderPrice.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
        assertThat(testOrdOrderPrice.getRecurringChargePeriod()).isEqualTo(DEFAULT_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdOrderPrice.getPriceId()).isEqualTo(UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void fullUpdateOrdOrderPriceWithPatch() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();

        // Update the ordOrderPrice using partial update
        OrdOrderPrice partialUpdatedOrdOrderPrice = new OrdOrderPrice();
        partialUpdatedOrdOrderPrice.setId(ordOrderPrice.getId());

        partialUpdatedOrdOrderPrice
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priceType(UPDATED_PRICE_TYPE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .recurringChargePeriod(UPDATED_RECURRING_CHARGE_PERIOD)
            .priceId(UPDATED_PRICE_ID);

        restOrdOrderPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderPrice))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderPrice testOrdOrderPrice = ordOrderPriceList.get(ordOrderPriceList.size() - 1);
        assertThat(testOrdOrderPrice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdOrderPrice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdOrderPrice.getPriceType()).isEqualTo(UPDATED_PRICE_TYPE);
        assertThat(testOrdOrderPrice.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testOrdOrderPrice.getRecurringChargePeriod()).isEqualTo(UPDATED_RECURRING_CHARGE_PERIOD);
        assertThat(testOrdOrderPrice.getPriceId()).isEqualTo(UPDATED_PRICE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOrdOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();
        ordOrderPrice.setId(count.incrementAndGet());

        // Create the OrdOrderPrice
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordOrderPriceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();
        ordOrderPrice.setId(count.incrementAndGet());

        // Create the OrdOrderPrice
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderPriceRepository.findAll().size();
        ordOrderPrice.setId(count.incrementAndGet());

        // Create the OrdOrderPrice
        OrdOrderPriceDTO ordOrderPriceDTO = ordOrderPriceMapper.toDto(ordOrderPrice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderPriceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderPriceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderPrice in the database
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdOrderPrice() throws Exception {
        // Initialize the database
        ordOrderPriceRepository.saveAndFlush(ordOrderPrice);

        int databaseSizeBeforeDelete = ordOrderPriceRepository.findAll().size();

        // Delete the ordOrderPrice
        restOrdOrderPriceMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordOrderPrice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdOrderPrice> ordOrderPriceList = ordOrderPriceRepository.findAll();
        assertThat(ordOrderPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
