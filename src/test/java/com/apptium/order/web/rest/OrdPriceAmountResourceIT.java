package com.apptium.order.web.rest;

import static com.apptium.order.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderPrice;
import com.apptium.order.domain.OrdPriceAlteration;
import com.apptium.order.domain.OrdPriceAmount;
import com.apptium.order.repository.OrdPriceAmountRepository;
import com.apptium.order.service.criteria.OrdPriceAmountCriteria;
import com.apptium.order.service.dto.OrdPriceAmountDTO;
import com.apptium.order.service.mapper.OrdPriceAmountMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link OrdPriceAmountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdPriceAmountResourceIT {

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAX_INCLUDED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_INCLUDED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_INCLUDED_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DUTY_FREE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DUTY_FREE_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_DUTY_FREE_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_RECURRING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_RECURRING_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_RECURRING_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_ONE_TIME_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_ONE_TIME_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_ONE_TIME_PRICE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/ord-price-amounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdPriceAmountRepository ordPriceAmountRepository;

    @Autowired
    private OrdPriceAmountMapper ordPriceAmountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdPriceAmountMockMvc;

    private OrdPriceAmount ordPriceAmount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPriceAmount createEntity(EntityManager em) {
        OrdPriceAmount ordPriceAmount = new OrdPriceAmount()
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .taxIncludedAmount(DEFAULT_TAX_INCLUDED_AMOUNT)
            .dutyFreeAmount(DEFAULT_DUTY_FREE_AMOUNT)
            .taxRate(DEFAULT_TAX_RATE)
            .percentage(DEFAULT_PERCENTAGE)
            .totalRecurringPrice(DEFAULT_TOTAL_RECURRING_PRICE)
            .totalOneTimePrice(DEFAULT_TOTAL_ONE_TIME_PRICE);
        return ordPriceAmount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPriceAmount createUpdatedEntity(EntityManager em) {
        OrdPriceAmount ordPriceAmount = new OrdPriceAmount()
            .currencyCode(UPDATED_CURRENCY_CODE)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .dutyFreeAmount(UPDATED_DUTY_FREE_AMOUNT)
            .taxRate(UPDATED_TAX_RATE)
            .percentage(UPDATED_PERCENTAGE)
            .totalRecurringPrice(UPDATED_TOTAL_RECURRING_PRICE)
            .totalOneTimePrice(UPDATED_TOTAL_ONE_TIME_PRICE);
        return ordPriceAmount;
    }

    @BeforeEach
    public void initTest() {
        ordPriceAmount = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdPriceAmount() throws Exception {
        int databaseSizeBeforeCreate = ordPriceAmountRepository.findAll().size();
        // Create the OrdPriceAmount
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);
        restOrdPriceAmountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeCreate + 1);
        OrdPriceAmount testOrdPriceAmount = ordPriceAmountList.get(ordPriceAmountList.size() - 1);
        assertThat(testOrdPriceAmount.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testOrdPriceAmount.getTaxIncludedAmount()).isEqualByComparingTo(DEFAULT_TAX_INCLUDED_AMOUNT);
        assertThat(testOrdPriceAmount.getDutyFreeAmount()).isEqualByComparingTo(DEFAULT_DUTY_FREE_AMOUNT);
        assertThat(testOrdPriceAmount.getTaxRate()).isEqualByComparingTo(DEFAULT_TAX_RATE);
        assertThat(testOrdPriceAmount.getPercentage()).isEqualByComparingTo(DEFAULT_PERCENTAGE);
        assertThat(testOrdPriceAmount.getTotalRecurringPrice()).isEqualByComparingTo(DEFAULT_TOTAL_RECURRING_PRICE);
        assertThat(testOrdPriceAmount.getTotalOneTimePrice()).isEqualByComparingTo(DEFAULT_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void createOrdPriceAmountWithExistingId() throws Exception {
        // Create the OrdPriceAmount with an existing ID
        ordPriceAmount.setId(1L);
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);

        int databaseSizeBeforeCreate = ordPriceAmountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdPriceAmountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmounts() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList
        restOrdPriceAmountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPriceAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].taxIncludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].dutyFreeAmount").value(hasItem(sameNumber(DEFAULT_DUTY_FREE_AMOUNT))))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(sameNumber(DEFAULT_TAX_RATE))))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(sameNumber(DEFAULT_PERCENTAGE))))
            .andExpect(jsonPath("$.[*].totalRecurringPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_RECURRING_PRICE))))
            .andExpect(jsonPath("$.[*].totalOneTimePrice").value(hasItem(sameNumber(DEFAULT_TOTAL_ONE_TIME_PRICE))));
    }

    @Test
    @Transactional
    void getOrdPriceAmount() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get the ordPriceAmount
        restOrdPriceAmountMockMvc
            .perform(get(ENTITY_API_URL_ID, ordPriceAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordPriceAmount.getId().intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.taxIncludedAmount").value(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT)))
            .andExpect(jsonPath("$.dutyFreeAmount").value(sameNumber(DEFAULT_DUTY_FREE_AMOUNT)))
            .andExpect(jsonPath("$.taxRate").value(sameNumber(DEFAULT_TAX_RATE)))
            .andExpect(jsonPath("$.percentage").value(sameNumber(DEFAULT_PERCENTAGE)))
            .andExpect(jsonPath("$.totalRecurringPrice").value(sameNumber(DEFAULT_TOTAL_RECURRING_PRICE)))
            .andExpect(jsonPath("$.totalOneTimePrice").value(sameNumber(DEFAULT_TOTAL_ONE_TIME_PRICE)));
    }

    @Test
    @Transactional
    void getOrdPriceAmountsByIdFiltering() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        Long id = ordPriceAmount.getId();

        defaultOrdPriceAmountShouldBeFound("id.equals=" + id);
        defaultOrdPriceAmountShouldNotBeFound("id.notEquals=" + id);

        defaultOrdPriceAmountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdPriceAmountShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdPriceAmountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdPriceAmountShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where currencyCode equals to DEFAULT_CURRENCY_CODE
        defaultOrdPriceAmountShouldBeFound("currencyCode.equals=" + DEFAULT_CURRENCY_CODE);

        // Get all the ordPriceAmountList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultOrdPriceAmountShouldNotBeFound("currencyCode.equals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where currencyCode not equals to DEFAULT_CURRENCY_CODE
        defaultOrdPriceAmountShouldNotBeFound("currencyCode.notEquals=" + DEFAULT_CURRENCY_CODE);

        // Get all the ordPriceAmountList where currencyCode not equals to UPDATED_CURRENCY_CODE
        defaultOrdPriceAmountShouldBeFound("currencyCode.notEquals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where currencyCode in DEFAULT_CURRENCY_CODE or UPDATED_CURRENCY_CODE
        defaultOrdPriceAmountShouldBeFound("currencyCode.in=" + DEFAULT_CURRENCY_CODE + "," + UPDATED_CURRENCY_CODE);

        // Get all the ordPriceAmountList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultOrdPriceAmountShouldNotBeFound("currencyCode.in=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where currencyCode is not null
        defaultOrdPriceAmountShouldBeFound("currencyCode.specified=true");

        // Get all the ordPriceAmountList where currencyCode is null
        defaultOrdPriceAmountShouldNotBeFound("currencyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where currencyCode contains DEFAULT_CURRENCY_CODE
        defaultOrdPriceAmountShouldBeFound("currencyCode.contains=" + DEFAULT_CURRENCY_CODE);

        // Get all the ordPriceAmountList where currencyCode contains UPDATED_CURRENCY_CODE
        defaultOrdPriceAmountShouldNotBeFound("currencyCode.contains=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where currencyCode does not contain DEFAULT_CURRENCY_CODE
        defaultOrdPriceAmountShouldNotBeFound("currencyCode.doesNotContain=" + DEFAULT_CURRENCY_CODE);

        // Get all the ordPriceAmountList where currencyCode does not contain UPDATED_CURRENCY_CODE
        defaultOrdPriceAmountShouldBeFound("currencyCode.doesNotContain=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount equals to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.equals=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the ordPriceAmountList where taxIncludedAmount equals to UPDATED_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.equals=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount not equals to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.notEquals=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the ordPriceAmountList where taxIncludedAmount not equals to UPDATED_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.notEquals=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount in DEFAULT_TAX_INCLUDED_AMOUNT or UPDATED_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.in=" + DEFAULT_TAX_INCLUDED_AMOUNT + "," + UPDATED_TAX_INCLUDED_AMOUNT);

        // Get all the ordPriceAmountList where taxIncludedAmount equals to UPDATED_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.in=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount is not null
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.specified=true");

        // Get all the ordPriceAmountList where taxIncludedAmount is null
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount is greater than or equal to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.greaterThanOrEqual=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the ordPriceAmountList where taxIncludedAmount is greater than or equal to UPDATED_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.greaterThanOrEqual=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount is less than or equal to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.lessThanOrEqual=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the ordPriceAmountList where taxIncludedAmount is less than or equal to SMALLER_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.lessThanOrEqual=" + SMALLER_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount is less than DEFAULT_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.lessThan=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the ordPriceAmountList where taxIncludedAmount is less than UPDATED_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.lessThan=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxIncludedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxIncludedAmount is greater than DEFAULT_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("taxIncludedAmount.greaterThan=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the ordPriceAmountList where taxIncludedAmount is greater than SMALLER_TAX_INCLUDED_AMOUNT
        defaultOrdPriceAmountShouldBeFound("taxIncludedAmount.greaterThan=" + SMALLER_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount equals to DEFAULT_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.equals=" + DEFAULT_DUTY_FREE_AMOUNT);

        // Get all the ordPriceAmountList where dutyFreeAmount equals to UPDATED_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.equals=" + UPDATED_DUTY_FREE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount not equals to DEFAULT_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.notEquals=" + DEFAULT_DUTY_FREE_AMOUNT);

        // Get all the ordPriceAmountList where dutyFreeAmount not equals to UPDATED_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.notEquals=" + UPDATED_DUTY_FREE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount in DEFAULT_DUTY_FREE_AMOUNT or UPDATED_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.in=" + DEFAULT_DUTY_FREE_AMOUNT + "," + UPDATED_DUTY_FREE_AMOUNT);

        // Get all the ordPriceAmountList where dutyFreeAmount equals to UPDATED_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.in=" + UPDATED_DUTY_FREE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount is not null
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.specified=true");

        // Get all the ordPriceAmountList where dutyFreeAmount is null
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount is greater than or equal to DEFAULT_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.greaterThanOrEqual=" + DEFAULT_DUTY_FREE_AMOUNT);

        // Get all the ordPriceAmountList where dutyFreeAmount is greater than or equal to UPDATED_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.greaterThanOrEqual=" + UPDATED_DUTY_FREE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount is less than or equal to DEFAULT_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.lessThanOrEqual=" + DEFAULT_DUTY_FREE_AMOUNT);

        // Get all the ordPriceAmountList where dutyFreeAmount is less than or equal to SMALLER_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.lessThanOrEqual=" + SMALLER_DUTY_FREE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount is less than DEFAULT_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.lessThan=" + DEFAULT_DUTY_FREE_AMOUNT);

        // Get all the ordPriceAmountList where dutyFreeAmount is less than UPDATED_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.lessThan=" + UPDATED_DUTY_FREE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByDutyFreeAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where dutyFreeAmount is greater than DEFAULT_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldNotBeFound("dutyFreeAmount.greaterThan=" + DEFAULT_DUTY_FREE_AMOUNT);

        // Get all the ordPriceAmountList where dutyFreeAmount is greater than SMALLER_DUTY_FREE_AMOUNT
        defaultOrdPriceAmountShouldBeFound("dutyFreeAmount.greaterThan=" + SMALLER_DUTY_FREE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate equals to DEFAULT_TAX_RATE
        defaultOrdPriceAmountShouldBeFound("taxRate.equals=" + DEFAULT_TAX_RATE);

        // Get all the ordPriceAmountList where taxRate equals to UPDATED_TAX_RATE
        defaultOrdPriceAmountShouldNotBeFound("taxRate.equals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate not equals to DEFAULT_TAX_RATE
        defaultOrdPriceAmountShouldNotBeFound("taxRate.notEquals=" + DEFAULT_TAX_RATE);

        // Get all the ordPriceAmountList where taxRate not equals to UPDATED_TAX_RATE
        defaultOrdPriceAmountShouldBeFound("taxRate.notEquals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate in DEFAULT_TAX_RATE or UPDATED_TAX_RATE
        defaultOrdPriceAmountShouldBeFound("taxRate.in=" + DEFAULT_TAX_RATE + "," + UPDATED_TAX_RATE);

        // Get all the ordPriceAmountList where taxRate equals to UPDATED_TAX_RATE
        defaultOrdPriceAmountShouldNotBeFound("taxRate.in=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate is not null
        defaultOrdPriceAmountShouldBeFound("taxRate.specified=true");

        // Get all the ordPriceAmountList where taxRate is null
        defaultOrdPriceAmountShouldNotBeFound("taxRate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate is greater than or equal to DEFAULT_TAX_RATE
        defaultOrdPriceAmountShouldBeFound("taxRate.greaterThanOrEqual=" + DEFAULT_TAX_RATE);

        // Get all the ordPriceAmountList where taxRate is greater than or equal to UPDATED_TAX_RATE
        defaultOrdPriceAmountShouldNotBeFound("taxRate.greaterThanOrEqual=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate is less than or equal to DEFAULT_TAX_RATE
        defaultOrdPriceAmountShouldBeFound("taxRate.lessThanOrEqual=" + DEFAULT_TAX_RATE);

        // Get all the ordPriceAmountList where taxRate is less than or equal to SMALLER_TAX_RATE
        defaultOrdPriceAmountShouldNotBeFound("taxRate.lessThanOrEqual=" + SMALLER_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate is less than DEFAULT_TAX_RATE
        defaultOrdPriceAmountShouldNotBeFound("taxRate.lessThan=" + DEFAULT_TAX_RATE);

        // Get all the ordPriceAmountList where taxRate is less than UPDATED_TAX_RATE
        defaultOrdPriceAmountShouldBeFound("taxRate.lessThan=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTaxRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where taxRate is greater than DEFAULT_TAX_RATE
        defaultOrdPriceAmountShouldNotBeFound("taxRate.greaterThan=" + DEFAULT_TAX_RATE);

        // Get all the ordPriceAmountList where taxRate is greater than SMALLER_TAX_RATE
        defaultOrdPriceAmountShouldBeFound("taxRate.greaterThan=" + SMALLER_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage equals to DEFAULT_PERCENTAGE
        defaultOrdPriceAmountShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the ordPriceAmountList where percentage equals to UPDATED_PERCENTAGE
        defaultOrdPriceAmountShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage not equals to DEFAULT_PERCENTAGE
        defaultOrdPriceAmountShouldNotBeFound("percentage.notEquals=" + DEFAULT_PERCENTAGE);

        // Get all the ordPriceAmountList where percentage not equals to UPDATED_PERCENTAGE
        defaultOrdPriceAmountShouldBeFound("percentage.notEquals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultOrdPriceAmountShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the ordPriceAmountList where percentage equals to UPDATED_PERCENTAGE
        defaultOrdPriceAmountShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage is not null
        defaultOrdPriceAmountShouldBeFound("percentage.specified=true");

        // Get all the ordPriceAmountList where percentage is null
        defaultOrdPriceAmountShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultOrdPriceAmountShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the ordPriceAmountList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultOrdPriceAmountShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultOrdPriceAmountShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the ordPriceAmountList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultOrdPriceAmountShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage is less than DEFAULT_PERCENTAGE
        defaultOrdPriceAmountShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the ordPriceAmountList where percentage is less than UPDATED_PERCENTAGE
        defaultOrdPriceAmountShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where percentage is greater than DEFAULT_PERCENTAGE
        defaultOrdPriceAmountShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the ordPriceAmountList where percentage is greater than SMALLER_PERCENTAGE
        defaultOrdPriceAmountShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice equals to DEFAULT_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.equals=" + DEFAULT_TOTAL_RECURRING_PRICE);

        // Get all the ordPriceAmountList where totalRecurringPrice equals to UPDATED_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.equals=" + UPDATED_TOTAL_RECURRING_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice not equals to DEFAULT_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.notEquals=" + DEFAULT_TOTAL_RECURRING_PRICE);

        // Get all the ordPriceAmountList where totalRecurringPrice not equals to UPDATED_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.notEquals=" + UPDATED_TOTAL_RECURRING_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice in DEFAULT_TOTAL_RECURRING_PRICE or UPDATED_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.in=" + DEFAULT_TOTAL_RECURRING_PRICE + "," + UPDATED_TOTAL_RECURRING_PRICE);

        // Get all the ordPriceAmountList where totalRecurringPrice equals to UPDATED_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.in=" + UPDATED_TOTAL_RECURRING_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice is not null
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.specified=true");

        // Get all the ordPriceAmountList where totalRecurringPrice is null
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice is greater than or equal to DEFAULT_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_RECURRING_PRICE);

        // Get all the ordPriceAmountList where totalRecurringPrice is greater than or equal to UPDATED_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.greaterThanOrEqual=" + UPDATED_TOTAL_RECURRING_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice is less than or equal to DEFAULT_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.lessThanOrEqual=" + DEFAULT_TOTAL_RECURRING_PRICE);

        // Get all the ordPriceAmountList where totalRecurringPrice is less than or equal to SMALLER_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.lessThanOrEqual=" + SMALLER_TOTAL_RECURRING_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice is less than DEFAULT_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.lessThan=" + DEFAULT_TOTAL_RECURRING_PRICE);

        // Get all the ordPriceAmountList where totalRecurringPrice is less than UPDATED_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.lessThan=" + UPDATED_TOTAL_RECURRING_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalRecurringPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalRecurringPrice is greater than DEFAULT_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalRecurringPrice.greaterThan=" + DEFAULT_TOTAL_RECURRING_PRICE);

        // Get all the ordPriceAmountList where totalRecurringPrice is greater than SMALLER_TOTAL_RECURRING_PRICE
        defaultOrdPriceAmountShouldBeFound("totalRecurringPrice.greaterThan=" + SMALLER_TOTAL_RECURRING_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice equals to DEFAULT_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.equals=" + DEFAULT_TOTAL_ONE_TIME_PRICE);

        // Get all the ordPriceAmountList where totalOneTimePrice equals to UPDATED_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.equals=" + UPDATED_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice not equals to DEFAULT_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.notEquals=" + DEFAULT_TOTAL_ONE_TIME_PRICE);

        // Get all the ordPriceAmountList where totalOneTimePrice not equals to UPDATED_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.notEquals=" + UPDATED_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsInShouldWork() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice in DEFAULT_TOTAL_ONE_TIME_PRICE or UPDATED_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.in=" + DEFAULT_TOTAL_ONE_TIME_PRICE + "," + UPDATED_TOTAL_ONE_TIME_PRICE);

        // Get all the ordPriceAmountList where totalOneTimePrice equals to UPDATED_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.in=" + UPDATED_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice is not null
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.specified=true");

        // Get all the ordPriceAmountList where totalOneTimePrice is null
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice is greater than or equal to DEFAULT_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.greaterThanOrEqual=" + DEFAULT_TOTAL_ONE_TIME_PRICE);

        // Get all the ordPriceAmountList where totalOneTimePrice is greater than or equal to UPDATED_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.greaterThanOrEqual=" + UPDATED_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice is less than or equal to DEFAULT_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.lessThanOrEqual=" + DEFAULT_TOTAL_ONE_TIME_PRICE);

        // Get all the ordPriceAmountList where totalOneTimePrice is less than or equal to SMALLER_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.lessThanOrEqual=" + SMALLER_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice is less than DEFAULT_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.lessThan=" + DEFAULT_TOTAL_ONE_TIME_PRICE);

        // Get all the ordPriceAmountList where totalOneTimePrice is less than UPDATED_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.lessThan=" + UPDATED_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByTotalOneTimePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        // Get all the ordPriceAmountList where totalOneTimePrice is greater than DEFAULT_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldNotBeFound("totalOneTimePrice.greaterThan=" + DEFAULT_TOTAL_ONE_TIME_PRICE);

        // Get all the ordPriceAmountList where totalOneTimePrice is greater than SMALLER_TOTAL_ONE_TIME_PRICE
        defaultOrdPriceAmountShouldBeFound("totalOneTimePrice.greaterThan=" + SMALLER_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByOrdOrderPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);
        OrdOrderPrice ordOrderPrice = OrdOrderPriceResourceIT.createEntity(em);
        em.persist(ordOrderPrice);
        em.flush();
        ordPriceAmount.setOrdOrderPrice(ordOrderPrice);
        ordOrderPrice.setOrdPriceAmount(ordPriceAmount);
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);
        Long ordOrderPriceId = ordOrderPrice.getId();

        // Get all the ordPriceAmountList where ordOrderPrice equals to ordOrderPriceId
        defaultOrdPriceAmountShouldBeFound("ordOrderPriceId.equals=" + ordOrderPriceId);

        // Get all the ordPriceAmountList where ordOrderPrice equals to (ordOrderPriceId + 1)
        defaultOrdPriceAmountShouldNotBeFound("ordOrderPriceId.equals=" + (ordOrderPriceId + 1));
    }

    @Test
    @Transactional
    void getAllOrdPriceAmountsByOrdPriceAlterationIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);
        OrdPriceAlteration ordPriceAlteration = OrdPriceAlterationResourceIT.createEntity(em);
        em.persist(ordPriceAlteration);
        em.flush();
        ordPriceAmount.setOrdPriceAlteration(ordPriceAlteration);
        ordPriceAlteration.setOrdPriceAmount(ordPriceAmount);
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);
        Long ordPriceAlterationId = ordPriceAlteration.getId();

        // Get all the ordPriceAmountList where ordPriceAlteration equals to ordPriceAlterationId
        defaultOrdPriceAmountShouldBeFound("ordPriceAlterationId.equals=" + ordPriceAlterationId);

        // Get all the ordPriceAmountList where ordPriceAlteration equals to (ordPriceAlterationId + 1)
        defaultOrdPriceAmountShouldNotBeFound("ordPriceAlterationId.equals=" + (ordPriceAlterationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdPriceAmountShouldBeFound(String filter) throws Exception {
        restOrdPriceAmountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPriceAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].taxIncludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].dutyFreeAmount").value(hasItem(sameNumber(DEFAULT_DUTY_FREE_AMOUNT))))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(sameNumber(DEFAULT_TAX_RATE))))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(sameNumber(DEFAULT_PERCENTAGE))))
            .andExpect(jsonPath("$.[*].totalRecurringPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_RECURRING_PRICE))))
            .andExpect(jsonPath("$.[*].totalOneTimePrice").value(hasItem(sameNumber(DEFAULT_TOTAL_ONE_TIME_PRICE))));

        // Check, that the count call also returns 1
        restOrdPriceAmountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdPriceAmountShouldNotBeFound(String filter) throws Exception {
        restOrdPriceAmountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdPriceAmountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdPriceAmount() throws Exception {
        // Get the ordPriceAmount
        restOrdPriceAmountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdPriceAmount() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();

        // Update the ordPriceAmount
        OrdPriceAmount updatedOrdPriceAmount = ordPriceAmountRepository.findById(ordPriceAmount.getId()).get();
        // Disconnect from session so that the updates on updatedOrdPriceAmount are not directly saved in db
        em.detach(updatedOrdPriceAmount);
        updatedOrdPriceAmount
            .currencyCode(UPDATED_CURRENCY_CODE)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .dutyFreeAmount(UPDATED_DUTY_FREE_AMOUNT)
            .taxRate(UPDATED_TAX_RATE)
            .percentage(UPDATED_PERCENTAGE)
            .totalRecurringPrice(UPDATED_TOTAL_RECURRING_PRICE)
            .totalOneTimePrice(UPDATED_TOTAL_ONE_TIME_PRICE);
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(updatedOrdPriceAmount);

        restOrdPriceAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPriceAmountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
        OrdPriceAmount testOrdPriceAmount = ordPriceAmountList.get(ordPriceAmountList.size() - 1);
        assertThat(testOrdPriceAmount.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testOrdPriceAmount.getTaxIncludedAmount()).isEqualTo(UPDATED_TAX_INCLUDED_AMOUNT);
        assertThat(testOrdPriceAmount.getDutyFreeAmount()).isEqualTo(UPDATED_DUTY_FREE_AMOUNT);
        assertThat(testOrdPriceAmount.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testOrdPriceAmount.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testOrdPriceAmount.getTotalRecurringPrice()).isEqualTo(UPDATED_TOTAL_RECURRING_PRICE);
        assertThat(testOrdPriceAmount.getTotalOneTimePrice()).isEqualTo(UPDATED_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingOrdPriceAmount() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();
        ordPriceAmount.setId(count.incrementAndGet());

        // Create the OrdPriceAmount
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPriceAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPriceAmountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdPriceAmount() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();
        ordPriceAmount.setId(count.incrementAndGet());

        // Create the OrdPriceAmount
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdPriceAmount() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();
        ordPriceAmount.setId(count.incrementAndGet());

        // Create the OrdPriceAmount
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAmountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdPriceAmountWithPatch() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();

        // Update the ordPriceAmount using partial update
        OrdPriceAmount partialUpdatedOrdPriceAmount = new OrdPriceAmount();
        partialUpdatedOrdPriceAmount.setId(ordPriceAmount.getId());

        partialUpdatedOrdPriceAmount.currencyCode(UPDATED_CURRENCY_CODE).taxRate(UPDATED_TAX_RATE);

        restOrdPriceAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPriceAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPriceAmount))
            )
            .andExpect(status().isOk());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
        OrdPriceAmount testOrdPriceAmount = ordPriceAmountList.get(ordPriceAmountList.size() - 1);
        assertThat(testOrdPriceAmount.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testOrdPriceAmount.getTaxIncludedAmount()).isEqualByComparingTo(DEFAULT_TAX_INCLUDED_AMOUNT);
        assertThat(testOrdPriceAmount.getDutyFreeAmount()).isEqualByComparingTo(DEFAULT_DUTY_FREE_AMOUNT);
        assertThat(testOrdPriceAmount.getTaxRate()).isEqualByComparingTo(UPDATED_TAX_RATE);
        assertThat(testOrdPriceAmount.getPercentage()).isEqualByComparingTo(DEFAULT_PERCENTAGE);
        assertThat(testOrdPriceAmount.getTotalRecurringPrice()).isEqualByComparingTo(DEFAULT_TOTAL_RECURRING_PRICE);
        assertThat(testOrdPriceAmount.getTotalOneTimePrice()).isEqualByComparingTo(DEFAULT_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateOrdPriceAmountWithPatch() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();

        // Update the ordPriceAmount using partial update
        OrdPriceAmount partialUpdatedOrdPriceAmount = new OrdPriceAmount();
        partialUpdatedOrdPriceAmount.setId(ordPriceAmount.getId());

        partialUpdatedOrdPriceAmount
            .currencyCode(UPDATED_CURRENCY_CODE)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .dutyFreeAmount(UPDATED_DUTY_FREE_AMOUNT)
            .taxRate(UPDATED_TAX_RATE)
            .percentage(UPDATED_PERCENTAGE)
            .totalRecurringPrice(UPDATED_TOTAL_RECURRING_PRICE)
            .totalOneTimePrice(UPDATED_TOTAL_ONE_TIME_PRICE);

        restOrdPriceAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPriceAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPriceAmount))
            )
            .andExpect(status().isOk());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
        OrdPriceAmount testOrdPriceAmount = ordPriceAmountList.get(ordPriceAmountList.size() - 1);
        assertThat(testOrdPriceAmount.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testOrdPriceAmount.getTaxIncludedAmount()).isEqualByComparingTo(UPDATED_TAX_INCLUDED_AMOUNT);
        assertThat(testOrdPriceAmount.getDutyFreeAmount()).isEqualByComparingTo(UPDATED_DUTY_FREE_AMOUNT);
        assertThat(testOrdPriceAmount.getTaxRate()).isEqualByComparingTo(UPDATED_TAX_RATE);
        assertThat(testOrdPriceAmount.getPercentage()).isEqualByComparingTo(UPDATED_PERCENTAGE);
        assertThat(testOrdPriceAmount.getTotalRecurringPrice()).isEqualByComparingTo(UPDATED_TOTAL_RECURRING_PRICE);
        assertThat(testOrdPriceAmount.getTotalOneTimePrice()).isEqualByComparingTo(UPDATED_TOTAL_ONE_TIME_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdPriceAmount() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();
        ordPriceAmount.setId(count.incrementAndGet());

        // Create the OrdPriceAmount
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPriceAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordPriceAmountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdPriceAmount() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();
        ordPriceAmount.setId(count.incrementAndGet());

        // Create the OrdPriceAmount
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdPriceAmount() throws Exception {
        int databaseSizeBeforeUpdate = ordPriceAmountRepository.findAll().size();
        ordPriceAmount.setId(count.incrementAndGet());

        // Create the OrdPriceAmount
        OrdPriceAmountDTO ordPriceAmountDTO = ordPriceAmountMapper.toDto(ordPriceAmount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPriceAmountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPriceAmountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPriceAmount in the database
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdPriceAmount() throws Exception {
        // Initialize the database
        ordPriceAmountRepository.saveAndFlush(ordPriceAmount);

        int databaseSizeBeforeDelete = ordPriceAmountRepository.findAll().size();

        // Delete the ordPriceAmount
        restOrdPriceAmountMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordPriceAmount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdPriceAmount> ordPriceAmountList = ordPriceAmountRepository.findAll();
        assertThat(ordPriceAmountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
