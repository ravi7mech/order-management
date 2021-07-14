package com.apptium.order.web.rest;

import static com.apptium.order.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdPaymentRef;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdPaymentRefRepository;
import com.apptium.order.service.criteria.OrdPaymentRefCriteria;
import com.apptium.order.service.dto.OrdPaymentRefDTO;
import com.apptium.order.service.mapper.OrdPaymentRefMapper;
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
 * Integration tests for the {@link OrdPaymentRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdPaymentRefResourceIT {

    private static final Long DEFAULT_PAYMENT_ID = 1L;
    private static final Long UPDATED_PAYMENT_ID = 2L;
    private static final Long SMALLER_PAYMENT_ID = 1L - 1L;

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PAYMENT_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ENROL_RECURRING = "AAAAAAAAAA";
    private static final String UPDATED_ENROL_RECURRING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-payment-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdPaymentRefRepository ordPaymentRefRepository;

    @Autowired
    private OrdPaymentRefMapper ordPaymentRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdPaymentRefMockMvc;

    private OrdPaymentRef ordPaymentRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPaymentRef createEntity(EntityManager em) {
        OrdPaymentRef ordPaymentRef = new OrdPaymentRef()
            .paymentId(DEFAULT_PAYMENT_ID)
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .action(DEFAULT_ACTION)
            .status(DEFAULT_STATUS)
            .enrolRecurring(DEFAULT_ENROL_RECURRING);
        return ordPaymentRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPaymentRef createUpdatedEntity(EntityManager em) {
        OrdPaymentRef ordPaymentRef = new OrdPaymentRef()
            .paymentId(UPDATED_PAYMENT_ID)
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .enrolRecurring(UPDATED_ENROL_RECURRING);
        return ordPaymentRef;
    }

    @BeforeEach
    public void initTest() {
        ordPaymentRef = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdPaymentRef() throws Exception {
        int databaseSizeBeforeCreate = ordPaymentRefRepository.findAll().size();
        // Create the OrdPaymentRef
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);
        restOrdPaymentRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeCreate + 1);
        OrdPaymentRef testOrdPaymentRef = ordPaymentRefList.get(ordPaymentRefList.size() - 1);
        assertThat(testOrdPaymentRef.getPaymentId()).isEqualTo(DEFAULT_PAYMENT_ID);
        assertThat(testOrdPaymentRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdPaymentRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdPaymentRef.getPaymentAmount()).isEqualByComparingTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testOrdPaymentRef.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testOrdPaymentRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrdPaymentRef.getEnrolRecurring()).isEqualTo(DEFAULT_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void createOrdPaymentRefWithExistingId() throws Exception {
        // Create the OrdPaymentRef with an existing ID
        ordPaymentRef.setId(1L);
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);

        int databaseSizeBeforeCreate = ordPaymentRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdPaymentRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefs() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList
        restOrdPaymentRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPaymentRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(sameNumber(DEFAULT_PAYMENT_AMOUNT))))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].enrolRecurring").value(hasItem(DEFAULT_ENROL_RECURRING)));
    }

    @Test
    @Transactional
    void getOrdPaymentRef() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get the ordPaymentRef
        restOrdPaymentRefMockMvc
            .perform(get(ENTITY_API_URL_ID, ordPaymentRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordPaymentRef.getId().intValue()))
            .andExpect(jsonPath("$.paymentId").value(DEFAULT_PAYMENT_ID.intValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.paymentAmount").value(sameNumber(DEFAULT_PAYMENT_AMOUNT)))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.enrolRecurring").value(DEFAULT_ENROL_RECURRING));
    }

    @Test
    @Transactional
    void getOrdPaymentRefsByIdFiltering() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        Long id = ordPaymentRef.getId();

        defaultOrdPaymentRefShouldBeFound("id.equals=" + id);
        defaultOrdPaymentRefShouldNotBeFound("id.notEquals=" + id);

        defaultOrdPaymentRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdPaymentRefShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdPaymentRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdPaymentRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId equals to DEFAULT_PAYMENT_ID
        defaultOrdPaymentRefShouldBeFound("paymentId.equals=" + DEFAULT_PAYMENT_ID);

        // Get all the ordPaymentRefList where paymentId equals to UPDATED_PAYMENT_ID
        defaultOrdPaymentRefShouldNotBeFound("paymentId.equals=" + UPDATED_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId not equals to DEFAULT_PAYMENT_ID
        defaultOrdPaymentRefShouldNotBeFound("paymentId.notEquals=" + DEFAULT_PAYMENT_ID);

        // Get all the ordPaymentRefList where paymentId not equals to UPDATED_PAYMENT_ID
        defaultOrdPaymentRefShouldBeFound("paymentId.notEquals=" + UPDATED_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId in DEFAULT_PAYMENT_ID or UPDATED_PAYMENT_ID
        defaultOrdPaymentRefShouldBeFound("paymentId.in=" + DEFAULT_PAYMENT_ID + "," + UPDATED_PAYMENT_ID);

        // Get all the ordPaymentRefList where paymentId equals to UPDATED_PAYMENT_ID
        defaultOrdPaymentRefShouldNotBeFound("paymentId.in=" + UPDATED_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId is not null
        defaultOrdPaymentRefShouldBeFound("paymentId.specified=true");

        // Get all the ordPaymentRefList where paymentId is null
        defaultOrdPaymentRefShouldNotBeFound("paymentId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId is greater than or equal to DEFAULT_PAYMENT_ID
        defaultOrdPaymentRefShouldBeFound("paymentId.greaterThanOrEqual=" + DEFAULT_PAYMENT_ID);

        // Get all the ordPaymentRefList where paymentId is greater than or equal to UPDATED_PAYMENT_ID
        defaultOrdPaymentRefShouldNotBeFound("paymentId.greaterThanOrEqual=" + UPDATED_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId is less than or equal to DEFAULT_PAYMENT_ID
        defaultOrdPaymentRefShouldBeFound("paymentId.lessThanOrEqual=" + DEFAULT_PAYMENT_ID);

        // Get all the ordPaymentRefList where paymentId is less than or equal to SMALLER_PAYMENT_ID
        defaultOrdPaymentRefShouldNotBeFound("paymentId.lessThanOrEqual=" + SMALLER_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId is less than DEFAULT_PAYMENT_ID
        defaultOrdPaymentRefShouldNotBeFound("paymentId.lessThan=" + DEFAULT_PAYMENT_ID);

        // Get all the ordPaymentRefList where paymentId is less than UPDATED_PAYMENT_ID
        defaultOrdPaymentRefShouldBeFound("paymentId.lessThan=" + UPDATED_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentId is greater than DEFAULT_PAYMENT_ID
        defaultOrdPaymentRefShouldNotBeFound("paymentId.greaterThan=" + DEFAULT_PAYMENT_ID);

        // Get all the ordPaymentRefList where paymentId is greater than SMALLER_PAYMENT_ID
        defaultOrdPaymentRefShouldBeFound("paymentId.greaterThan=" + SMALLER_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where href equals to DEFAULT_HREF
        defaultOrdPaymentRefShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the ordPaymentRefList where href equals to UPDATED_HREF
        defaultOrdPaymentRefShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where href not equals to DEFAULT_HREF
        defaultOrdPaymentRefShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the ordPaymentRefList where href not equals to UPDATED_HREF
        defaultOrdPaymentRefShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where href in DEFAULT_HREF or UPDATED_HREF
        defaultOrdPaymentRefShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the ordPaymentRefList where href equals to UPDATED_HREF
        defaultOrdPaymentRefShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where href is not null
        defaultOrdPaymentRefShouldBeFound("href.specified=true");

        // Get all the ordPaymentRefList where href is null
        defaultOrdPaymentRefShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByHrefContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where href contains DEFAULT_HREF
        defaultOrdPaymentRefShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the ordPaymentRefList where href contains UPDATED_HREF
        defaultOrdPaymentRefShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where href does not contain DEFAULT_HREF
        defaultOrdPaymentRefShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the ordPaymentRefList where href does not contain UPDATED_HREF
        defaultOrdPaymentRefShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where name equals to DEFAULT_NAME
        defaultOrdPaymentRefShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordPaymentRefList where name equals to UPDATED_NAME
        defaultOrdPaymentRefShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where name not equals to DEFAULT_NAME
        defaultOrdPaymentRefShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordPaymentRefList where name not equals to UPDATED_NAME
        defaultOrdPaymentRefShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdPaymentRefShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordPaymentRefList where name equals to UPDATED_NAME
        defaultOrdPaymentRefShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where name is not null
        defaultOrdPaymentRefShouldBeFound("name.specified=true");

        // Get all the ordPaymentRefList where name is null
        defaultOrdPaymentRefShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where name contains DEFAULT_NAME
        defaultOrdPaymentRefShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordPaymentRefList where name contains UPDATED_NAME
        defaultOrdPaymentRefShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where name does not contain DEFAULT_NAME
        defaultOrdPaymentRefShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordPaymentRefList where name does not contain UPDATED_NAME
        defaultOrdPaymentRefShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount equals to DEFAULT_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldBeFound("paymentAmount.equals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the ordPaymentRefList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.equals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount not equals to DEFAULT_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.notEquals=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the ordPaymentRefList where paymentAmount not equals to UPDATED_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldBeFound("paymentAmount.notEquals=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount in DEFAULT_PAYMENT_AMOUNT or UPDATED_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldBeFound("paymentAmount.in=" + DEFAULT_PAYMENT_AMOUNT + "," + UPDATED_PAYMENT_AMOUNT);

        // Get all the ordPaymentRefList where paymentAmount equals to UPDATED_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.in=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount is not null
        defaultOrdPaymentRefShouldBeFound("paymentAmount.specified=true");

        // Get all the ordPaymentRefList where paymentAmount is null
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount is greater than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldBeFound("paymentAmount.greaterThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the ordPaymentRefList where paymentAmount is greater than or equal to UPDATED_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.greaterThanOrEqual=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount is less than or equal to DEFAULT_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldBeFound("paymentAmount.lessThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the ordPaymentRefList where paymentAmount is less than or equal to SMALLER_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.lessThanOrEqual=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount is less than DEFAULT_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.lessThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the ordPaymentRefList where paymentAmount is less than UPDATED_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldBeFound("paymentAmount.lessThan=" + UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where paymentAmount is greater than DEFAULT_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldNotBeFound("paymentAmount.greaterThan=" + DEFAULT_PAYMENT_AMOUNT);

        // Get all the ordPaymentRefList where paymentAmount is greater than SMALLER_PAYMENT_AMOUNT
        defaultOrdPaymentRefShouldBeFound("paymentAmount.greaterThan=" + SMALLER_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where action equals to DEFAULT_ACTION
        defaultOrdPaymentRefShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the ordPaymentRefList where action equals to UPDATED_ACTION
        defaultOrdPaymentRefShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where action not equals to DEFAULT_ACTION
        defaultOrdPaymentRefShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the ordPaymentRefList where action not equals to UPDATED_ACTION
        defaultOrdPaymentRefShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultOrdPaymentRefShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the ordPaymentRefList where action equals to UPDATED_ACTION
        defaultOrdPaymentRefShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where action is not null
        defaultOrdPaymentRefShouldBeFound("action.specified=true");

        // Get all the ordPaymentRefList where action is null
        defaultOrdPaymentRefShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByActionContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where action contains DEFAULT_ACTION
        defaultOrdPaymentRefShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the ordPaymentRefList where action contains UPDATED_ACTION
        defaultOrdPaymentRefShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where action does not contain DEFAULT_ACTION
        defaultOrdPaymentRefShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the ordPaymentRefList where action does not contain UPDATED_ACTION
        defaultOrdPaymentRefShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where status equals to DEFAULT_STATUS
        defaultOrdPaymentRefShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ordPaymentRefList where status equals to UPDATED_STATUS
        defaultOrdPaymentRefShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where status not equals to DEFAULT_STATUS
        defaultOrdPaymentRefShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ordPaymentRefList where status not equals to UPDATED_STATUS
        defaultOrdPaymentRefShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrdPaymentRefShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ordPaymentRefList where status equals to UPDATED_STATUS
        defaultOrdPaymentRefShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where status is not null
        defaultOrdPaymentRefShouldBeFound("status.specified=true");

        // Get all the ordPaymentRefList where status is null
        defaultOrdPaymentRefShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByStatusContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where status contains DEFAULT_STATUS
        defaultOrdPaymentRefShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the ordPaymentRefList where status contains UPDATED_STATUS
        defaultOrdPaymentRefShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where status does not contain DEFAULT_STATUS
        defaultOrdPaymentRefShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the ordPaymentRefList where status does not contain UPDATED_STATUS
        defaultOrdPaymentRefShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByEnrolRecurringIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where enrolRecurring equals to DEFAULT_ENROL_RECURRING
        defaultOrdPaymentRefShouldBeFound("enrolRecurring.equals=" + DEFAULT_ENROL_RECURRING);

        // Get all the ordPaymentRefList where enrolRecurring equals to UPDATED_ENROL_RECURRING
        defaultOrdPaymentRefShouldNotBeFound("enrolRecurring.equals=" + UPDATED_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByEnrolRecurringIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where enrolRecurring not equals to DEFAULT_ENROL_RECURRING
        defaultOrdPaymentRefShouldNotBeFound("enrolRecurring.notEquals=" + DEFAULT_ENROL_RECURRING);

        // Get all the ordPaymentRefList where enrolRecurring not equals to UPDATED_ENROL_RECURRING
        defaultOrdPaymentRefShouldBeFound("enrolRecurring.notEquals=" + UPDATED_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByEnrolRecurringIsInShouldWork() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where enrolRecurring in DEFAULT_ENROL_RECURRING or UPDATED_ENROL_RECURRING
        defaultOrdPaymentRefShouldBeFound("enrolRecurring.in=" + DEFAULT_ENROL_RECURRING + "," + UPDATED_ENROL_RECURRING);

        // Get all the ordPaymentRefList where enrolRecurring equals to UPDATED_ENROL_RECURRING
        defaultOrdPaymentRefShouldNotBeFound("enrolRecurring.in=" + UPDATED_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByEnrolRecurringIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where enrolRecurring is not null
        defaultOrdPaymentRefShouldBeFound("enrolRecurring.specified=true");

        // Get all the ordPaymentRefList where enrolRecurring is null
        defaultOrdPaymentRefShouldNotBeFound("enrolRecurring.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByEnrolRecurringContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where enrolRecurring contains DEFAULT_ENROL_RECURRING
        defaultOrdPaymentRefShouldBeFound("enrolRecurring.contains=" + DEFAULT_ENROL_RECURRING);

        // Get all the ordPaymentRefList where enrolRecurring contains UPDATED_ENROL_RECURRING
        defaultOrdPaymentRefShouldNotBeFound("enrolRecurring.contains=" + UPDATED_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByEnrolRecurringNotContainsSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        // Get all the ordPaymentRefList where enrolRecurring does not contain DEFAULT_ENROL_RECURRING
        defaultOrdPaymentRefShouldNotBeFound("enrolRecurring.doesNotContain=" + DEFAULT_ENROL_RECURRING);

        // Get all the ordPaymentRefList where enrolRecurring does not contain UPDATED_ENROL_RECURRING
        defaultOrdPaymentRefShouldBeFound("enrolRecurring.doesNotContain=" + UPDATED_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void getAllOrdPaymentRefsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordPaymentRef.setOrdProductOrder(ordProductOrder);
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordPaymentRefList where ordProductOrder equals to ordProductOrderId
        defaultOrdPaymentRefShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordPaymentRefList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdPaymentRefShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdPaymentRefShouldBeFound(String filter) throws Exception {
        restOrdPaymentRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPaymentRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(sameNumber(DEFAULT_PAYMENT_AMOUNT))))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].enrolRecurring").value(hasItem(DEFAULT_ENROL_RECURRING)));

        // Check, that the count call also returns 1
        restOrdPaymentRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdPaymentRefShouldNotBeFound(String filter) throws Exception {
        restOrdPaymentRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdPaymentRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdPaymentRef() throws Exception {
        // Get the ordPaymentRef
        restOrdPaymentRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdPaymentRef() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();

        // Update the ordPaymentRef
        OrdPaymentRef updatedOrdPaymentRef = ordPaymentRefRepository.findById(ordPaymentRef.getId()).get();
        // Disconnect from session so that the updates on updatedOrdPaymentRef are not directly saved in db
        em.detach(updatedOrdPaymentRef);
        updatedOrdPaymentRef
            .paymentId(UPDATED_PAYMENT_ID)
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .enrolRecurring(UPDATED_ENROL_RECURRING);
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(updatedOrdPaymentRef);

        restOrdPaymentRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPaymentRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
        OrdPaymentRef testOrdPaymentRef = ordPaymentRefList.get(ordPaymentRefList.size() - 1);
        assertThat(testOrdPaymentRef.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testOrdPaymentRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdPaymentRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdPaymentRef.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testOrdPaymentRef.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testOrdPaymentRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrdPaymentRef.getEnrolRecurring()).isEqualTo(UPDATED_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void putNonExistingOrdPaymentRef() throws Exception {
        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();
        ordPaymentRef.setId(count.incrementAndGet());

        // Create the OrdPaymentRef
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPaymentRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPaymentRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdPaymentRef() throws Exception {
        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();
        ordPaymentRef.setId(count.incrementAndGet());

        // Create the OrdPaymentRef
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPaymentRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdPaymentRef() throws Exception {
        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();
        ordPaymentRef.setId(count.incrementAndGet());

        // Create the OrdPaymentRef
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPaymentRefMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdPaymentRefWithPatch() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();

        // Update the ordPaymentRef using partial update
        OrdPaymentRef partialUpdatedOrdPaymentRef = new OrdPaymentRef();
        partialUpdatedOrdPaymentRef.setId(ordPaymentRef.getId());

        partialUpdatedOrdPaymentRef.paymentId(UPDATED_PAYMENT_ID).href(UPDATED_HREF);

        restOrdPaymentRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPaymentRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPaymentRef))
            )
            .andExpect(status().isOk());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
        OrdPaymentRef testOrdPaymentRef = ordPaymentRefList.get(ordPaymentRefList.size() - 1);
        assertThat(testOrdPaymentRef.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testOrdPaymentRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdPaymentRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdPaymentRef.getPaymentAmount()).isEqualByComparingTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testOrdPaymentRef.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testOrdPaymentRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrdPaymentRef.getEnrolRecurring()).isEqualTo(DEFAULT_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void fullUpdateOrdPaymentRefWithPatch() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();

        // Update the ordPaymentRef using partial update
        OrdPaymentRef partialUpdatedOrdPaymentRef = new OrdPaymentRef();
        partialUpdatedOrdPaymentRef.setId(ordPaymentRef.getId());

        partialUpdatedOrdPaymentRef
            .paymentId(UPDATED_PAYMENT_ID)
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .enrolRecurring(UPDATED_ENROL_RECURRING);

        restOrdPaymentRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPaymentRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPaymentRef))
            )
            .andExpect(status().isOk());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
        OrdPaymentRef testOrdPaymentRef = ordPaymentRefList.get(ordPaymentRefList.size() - 1);
        assertThat(testOrdPaymentRef.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testOrdPaymentRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdPaymentRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdPaymentRef.getPaymentAmount()).isEqualByComparingTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testOrdPaymentRef.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testOrdPaymentRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrdPaymentRef.getEnrolRecurring()).isEqualTo(UPDATED_ENROL_RECURRING);
    }

    @Test
    @Transactional
    void patchNonExistingOrdPaymentRef() throws Exception {
        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();
        ordPaymentRef.setId(count.incrementAndGet());

        // Create the OrdPaymentRef
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPaymentRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordPaymentRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdPaymentRef() throws Exception {
        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();
        ordPaymentRef.setId(count.incrementAndGet());

        // Create the OrdPaymentRef
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPaymentRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdPaymentRef() throws Exception {
        int databaseSizeBeforeUpdate = ordPaymentRefRepository.findAll().size();
        ordPaymentRef.setId(count.incrementAndGet());

        // Create the OrdPaymentRef
        OrdPaymentRefDTO ordPaymentRefDTO = ordPaymentRefMapper.toDto(ordPaymentRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPaymentRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPaymentRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPaymentRef in the database
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdPaymentRef() throws Exception {
        // Initialize the database
        ordPaymentRefRepository.saveAndFlush(ordPaymentRef);

        int databaseSizeBeforeDelete = ordPaymentRefRepository.findAll().size();

        // Delete the ordPaymentRef
        restOrdPaymentRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordPaymentRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdPaymentRef> ordPaymentRefList = ordPaymentRefRepository.findAll();
        assertThat(ordPaymentRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
