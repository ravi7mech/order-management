package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdBillingAccountRef;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdBillingAccountRefRepository;
import com.apptium.order.service.criteria.OrdBillingAccountRefCriteria;
import com.apptium.order.service.dto.OrdBillingAccountRefDTO;
import com.apptium.order.service.mapper.OrdBillingAccountRefMapper;
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
 * Integration tests for the {@link OrdBillingAccountRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdBillingAccountRefResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final Long DEFAULT_CART_PRICE_ID = 1L;
    private static final Long UPDATED_CART_PRICE_ID = 2L;
    private static final Long SMALLER_CART_PRICE_ID = 1L - 1L;

    private static final Long DEFAULT_BILLING_ACCOUNT_ID = 1L;
    private static final Long UPDATED_BILLING_ACCOUNT_ID = 2L;
    private static final Long SMALLER_BILLING_ACCOUNT_ID = 1L - 1L;

    private static final String DEFAULT_BILLING_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_QUOTE_ID = 1L;
    private static final Long UPDATED_QUOTE_ID = 2L;
    private static final Long SMALLER_QUOTE_ID = 1L - 1L;

    private static final Long DEFAULT_SALES_ORDER_ID = 1L;
    private static final Long UPDATED_SALES_ORDER_ID = 2L;
    private static final Long SMALLER_SALES_ORDER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ord-billing-account-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdBillingAccountRefRepository ordBillingAccountRefRepository;

    @Autowired
    private OrdBillingAccountRefMapper ordBillingAccountRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdBillingAccountRefMockMvc;

    private OrdBillingAccountRef ordBillingAccountRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdBillingAccountRef createEntity(EntityManager em) {
        OrdBillingAccountRef ordBillingAccountRef = new OrdBillingAccountRef()
            .name(DEFAULT_NAME)
            .href(DEFAULT_HREF)
            .cartPriceId(DEFAULT_CART_PRICE_ID)
            .billingAccountId(DEFAULT_BILLING_ACCOUNT_ID)
            .billingSystem(DEFAULT_BILLING_SYSTEM)
            .deliveryMethod(DEFAULT_DELIVERY_METHOD)
            .billingAddress(DEFAULT_BILLING_ADDRESS)
            .status(DEFAULT_STATUS)
            .quoteId(DEFAULT_QUOTE_ID)
            .salesOrderId(DEFAULT_SALES_ORDER_ID);
        return ordBillingAccountRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdBillingAccountRef createUpdatedEntity(EntityManager em) {
        OrdBillingAccountRef ordBillingAccountRef = new OrdBillingAccountRef()
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .cartPriceId(UPDATED_CART_PRICE_ID)
            .billingAccountId(UPDATED_BILLING_ACCOUNT_ID)
            .billingSystem(UPDATED_BILLING_SYSTEM)
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .status(UPDATED_STATUS)
            .quoteId(UPDATED_QUOTE_ID)
            .salesOrderId(UPDATED_SALES_ORDER_ID);
        return ordBillingAccountRef;
    }

    @BeforeEach
    public void initTest() {
        ordBillingAccountRef = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdBillingAccountRef() throws Exception {
        int databaseSizeBeforeCreate = ordBillingAccountRefRepository.findAll().size();
        // Create the OrdBillingAccountRef
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);
        restOrdBillingAccountRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeCreate + 1);
        OrdBillingAccountRef testOrdBillingAccountRef = ordBillingAccountRefList.get(ordBillingAccountRefList.size() - 1);
        assertThat(testOrdBillingAccountRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdBillingAccountRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdBillingAccountRef.getCartPriceId()).isEqualTo(DEFAULT_CART_PRICE_ID);
        assertThat(testOrdBillingAccountRef.getBillingAccountId()).isEqualTo(DEFAULT_BILLING_ACCOUNT_ID);
        assertThat(testOrdBillingAccountRef.getBillingSystem()).isEqualTo(DEFAULT_BILLING_SYSTEM);
        assertThat(testOrdBillingAccountRef.getDeliveryMethod()).isEqualTo(DEFAULT_DELIVERY_METHOD);
        assertThat(testOrdBillingAccountRef.getBillingAddress()).isEqualTo(DEFAULT_BILLING_ADDRESS);
        assertThat(testOrdBillingAccountRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrdBillingAccountRef.getQuoteId()).isEqualTo(DEFAULT_QUOTE_ID);
        assertThat(testOrdBillingAccountRef.getSalesOrderId()).isEqualTo(DEFAULT_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void createOrdBillingAccountRefWithExistingId() throws Exception {
        // Create the OrdBillingAccountRef with an existing ID
        ordBillingAccountRef.setId(1L);
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);

        int databaseSizeBeforeCreate = ordBillingAccountRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdBillingAccountRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefs() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList
        restOrdBillingAccountRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordBillingAccountRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].cartPriceId").value(hasItem(DEFAULT_CART_PRICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].billingAccountId").value(hasItem(DEFAULT_BILLING_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].billingSystem").value(hasItem(DEFAULT_BILLING_SYSTEM)))
            .andExpect(jsonPath("$.[*].deliveryMethod").value(hasItem(DEFAULT_DELIVERY_METHOD)))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].salesOrderId").value(hasItem(DEFAULT_SALES_ORDER_ID.intValue())));
    }

    @Test
    @Transactional
    void getOrdBillingAccountRef() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get the ordBillingAccountRef
        restOrdBillingAccountRefMockMvc
            .perform(get(ENTITY_API_URL_ID, ordBillingAccountRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordBillingAccountRef.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.cartPriceId").value(DEFAULT_CART_PRICE_ID.intValue()))
            .andExpect(jsonPath("$.billingAccountId").value(DEFAULT_BILLING_ACCOUNT_ID.intValue()))
            .andExpect(jsonPath("$.billingSystem").value(DEFAULT_BILLING_SYSTEM))
            .andExpect(jsonPath("$.deliveryMethod").value(DEFAULT_DELIVERY_METHOD))
            .andExpect(jsonPath("$.billingAddress").value(DEFAULT_BILLING_ADDRESS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.quoteId").value(DEFAULT_QUOTE_ID.intValue()))
            .andExpect(jsonPath("$.salesOrderId").value(DEFAULT_SALES_ORDER_ID.intValue()));
    }

    @Test
    @Transactional
    void getOrdBillingAccountRefsByIdFiltering() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        Long id = ordBillingAccountRef.getId();

        defaultOrdBillingAccountRefShouldBeFound("id.equals=" + id);
        defaultOrdBillingAccountRefShouldNotBeFound("id.notEquals=" + id);

        defaultOrdBillingAccountRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdBillingAccountRefShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdBillingAccountRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdBillingAccountRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where name equals to DEFAULT_NAME
        defaultOrdBillingAccountRefShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordBillingAccountRefList where name equals to UPDATED_NAME
        defaultOrdBillingAccountRefShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where name not equals to DEFAULT_NAME
        defaultOrdBillingAccountRefShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordBillingAccountRefList where name not equals to UPDATED_NAME
        defaultOrdBillingAccountRefShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdBillingAccountRefShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordBillingAccountRefList where name equals to UPDATED_NAME
        defaultOrdBillingAccountRefShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where name is not null
        defaultOrdBillingAccountRefShouldBeFound("name.specified=true");

        // Get all the ordBillingAccountRefList where name is null
        defaultOrdBillingAccountRefShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where name contains DEFAULT_NAME
        defaultOrdBillingAccountRefShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordBillingAccountRefList where name contains UPDATED_NAME
        defaultOrdBillingAccountRefShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where name does not contain DEFAULT_NAME
        defaultOrdBillingAccountRefShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordBillingAccountRefList where name does not contain UPDATED_NAME
        defaultOrdBillingAccountRefShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where href equals to DEFAULT_HREF
        defaultOrdBillingAccountRefShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the ordBillingAccountRefList where href equals to UPDATED_HREF
        defaultOrdBillingAccountRefShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where href not equals to DEFAULT_HREF
        defaultOrdBillingAccountRefShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the ordBillingAccountRefList where href not equals to UPDATED_HREF
        defaultOrdBillingAccountRefShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where href in DEFAULT_HREF or UPDATED_HREF
        defaultOrdBillingAccountRefShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the ordBillingAccountRefList where href equals to UPDATED_HREF
        defaultOrdBillingAccountRefShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where href is not null
        defaultOrdBillingAccountRefShouldBeFound("href.specified=true");

        // Get all the ordBillingAccountRefList where href is null
        defaultOrdBillingAccountRefShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByHrefContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where href contains DEFAULT_HREF
        defaultOrdBillingAccountRefShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the ordBillingAccountRefList where href contains UPDATED_HREF
        defaultOrdBillingAccountRefShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where href does not contain DEFAULT_HREF
        defaultOrdBillingAccountRefShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the ordBillingAccountRefList where href does not contain UPDATED_HREF
        defaultOrdBillingAccountRefShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId equals to DEFAULT_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.equals=" + DEFAULT_CART_PRICE_ID);

        // Get all the ordBillingAccountRefList where cartPriceId equals to UPDATED_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.equals=" + UPDATED_CART_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId not equals to DEFAULT_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.notEquals=" + DEFAULT_CART_PRICE_ID);

        // Get all the ordBillingAccountRefList where cartPriceId not equals to UPDATED_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.notEquals=" + UPDATED_CART_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId in DEFAULT_CART_PRICE_ID or UPDATED_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.in=" + DEFAULT_CART_PRICE_ID + "," + UPDATED_CART_PRICE_ID);

        // Get all the ordBillingAccountRefList where cartPriceId equals to UPDATED_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.in=" + UPDATED_CART_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId is not null
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.specified=true");

        // Get all the ordBillingAccountRefList where cartPriceId is null
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId is greater than or equal to DEFAULT_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.greaterThanOrEqual=" + DEFAULT_CART_PRICE_ID);

        // Get all the ordBillingAccountRefList where cartPriceId is greater than or equal to UPDATED_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.greaterThanOrEqual=" + UPDATED_CART_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId is less than or equal to DEFAULT_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.lessThanOrEqual=" + DEFAULT_CART_PRICE_ID);

        // Get all the ordBillingAccountRefList where cartPriceId is less than or equal to SMALLER_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.lessThanOrEqual=" + SMALLER_CART_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId is less than DEFAULT_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.lessThan=" + DEFAULT_CART_PRICE_ID);

        // Get all the ordBillingAccountRefList where cartPriceId is less than UPDATED_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.lessThan=" + UPDATED_CART_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByCartPriceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where cartPriceId is greater than DEFAULT_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("cartPriceId.greaterThan=" + DEFAULT_CART_PRICE_ID);

        // Get all the ordBillingAccountRefList where cartPriceId is greater than SMALLER_CART_PRICE_ID
        defaultOrdBillingAccountRefShouldBeFound("cartPriceId.greaterThan=" + SMALLER_CART_PRICE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId equals to DEFAULT_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.equals=" + DEFAULT_BILLING_ACCOUNT_ID);

        // Get all the ordBillingAccountRefList where billingAccountId equals to UPDATED_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.equals=" + UPDATED_BILLING_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId not equals to DEFAULT_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.notEquals=" + DEFAULT_BILLING_ACCOUNT_ID);

        // Get all the ordBillingAccountRefList where billingAccountId not equals to UPDATED_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.notEquals=" + UPDATED_BILLING_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId in DEFAULT_BILLING_ACCOUNT_ID or UPDATED_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.in=" + DEFAULT_BILLING_ACCOUNT_ID + "," + UPDATED_BILLING_ACCOUNT_ID);

        // Get all the ordBillingAccountRefList where billingAccountId equals to UPDATED_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.in=" + UPDATED_BILLING_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId is not null
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.specified=true");

        // Get all the ordBillingAccountRefList where billingAccountId is null
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId is greater than or equal to DEFAULT_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.greaterThanOrEqual=" + DEFAULT_BILLING_ACCOUNT_ID);

        // Get all the ordBillingAccountRefList where billingAccountId is greater than or equal to UPDATED_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.greaterThanOrEqual=" + UPDATED_BILLING_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId is less than or equal to DEFAULT_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.lessThanOrEqual=" + DEFAULT_BILLING_ACCOUNT_ID);

        // Get all the ordBillingAccountRefList where billingAccountId is less than or equal to SMALLER_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.lessThanOrEqual=" + SMALLER_BILLING_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId is less than DEFAULT_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.lessThan=" + DEFAULT_BILLING_ACCOUNT_ID);

        // Get all the ordBillingAccountRefList where billingAccountId is less than UPDATED_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.lessThan=" + UPDATED_BILLING_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAccountIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAccountId is greater than DEFAULT_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldNotBeFound("billingAccountId.greaterThan=" + DEFAULT_BILLING_ACCOUNT_ID);

        // Get all the ordBillingAccountRefList where billingAccountId is greater than SMALLER_BILLING_ACCOUNT_ID
        defaultOrdBillingAccountRefShouldBeFound("billingAccountId.greaterThan=" + SMALLER_BILLING_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingSystemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingSystem equals to DEFAULT_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldBeFound("billingSystem.equals=" + DEFAULT_BILLING_SYSTEM);

        // Get all the ordBillingAccountRefList where billingSystem equals to UPDATED_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldNotBeFound("billingSystem.equals=" + UPDATED_BILLING_SYSTEM);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingSystemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingSystem not equals to DEFAULT_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldNotBeFound("billingSystem.notEquals=" + DEFAULT_BILLING_SYSTEM);

        // Get all the ordBillingAccountRefList where billingSystem not equals to UPDATED_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldBeFound("billingSystem.notEquals=" + UPDATED_BILLING_SYSTEM);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingSystemIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingSystem in DEFAULT_BILLING_SYSTEM or UPDATED_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldBeFound("billingSystem.in=" + DEFAULT_BILLING_SYSTEM + "," + UPDATED_BILLING_SYSTEM);

        // Get all the ordBillingAccountRefList where billingSystem equals to UPDATED_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldNotBeFound("billingSystem.in=" + UPDATED_BILLING_SYSTEM);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingSystemIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingSystem is not null
        defaultOrdBillingAccountRefShouldBeFound("billingSystem.specified=true");

        // Get all the ordBillingAccountRefList where billingSystem is null
        defaultOrdBillingAccountRefShouldNotBeFound("billingSystem.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingSystemContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingSystem contains DEFAULT_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldBeFound("billingSystem.contains=" + DEFAULT_BILLING_SYSTEM);

        // Get all the ordBillingAccountRefList where billingSystem contains UPDATED_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldNotBeFound("billingSystem.contains=" + UPDATED_BILLING_SYSTEM);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingSystemNotContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingSystem does not contain DEFAULT_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldNotBeFound("billingSystem.doesNotContain=" + DEFAULT_BILLING_SYSTEM);

        // Get all the ordBillingAccountRefList where billingSystem does not contain UPDATED_BILLING_SYSTEM
        defaultOrdBillingAccountRefShouldBeFound("billingSystem.doesNotContain=" + UPDATED_BILLING_SYSTEM);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where deliveryMethod equals to DEFAULT_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldBeFound("deliveryMethod.equals=" + DEFAULT_DELIVERY_METHOD);

        // Get all the ordBillingAccountRefList where deliveryMethod equals to UPDATED_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldNotBeFound("deliveryMethod.equals=" + UPDATED_DELIVERY_METHOD);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByDeliveryMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where deliveryMethod not equals to DEFAULT_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldNotBeFound("deliveryMethod.notEquals=" + DEFAULT_DELIVERY_METHOD);

        // Get all the ordBillingAccountRefList where deliveryMethod not equals to UPDATED_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldBeFound("deliveryMethod.notEquals=" + UPDATED_DELIVERY_METHOD);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByDeliveryMethodIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where deliveryMethod in DEFAULT_DELIVERY_METHOD or UPDATED_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldBeFound("deliveryMethod.in=" + DEFAULT_DELIVERY_METHOD + "," + UPDATED_DELIVERY_METHOD);

        // Get all the ordBillingAccountRefList where deliveryMethod equals to UPDATED_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldNotBeFound("deliveryMethod.in=" + UPDATED_DELIVERY_METHOD);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByDeliveryMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where deliveryMethod is not null
        defaultOrdBillingAccountRefShouldBeFound("deliveryMethod.specified=true");

        // Get all the ordBillingAccountRefList where deliveryMethod is null
        defaultOrdBillingAccountRefShouldNotBeFound("deliveryMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByDeliveryMethodContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where deliveryMethod contains DEFAULT_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldBeFound("deliveryMethod.contains=" + DEFAULT_DELIVERY_METHOD);

        // Get all the ordBillingAccountRefList where deliveryMethod contains UPDATED_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldNotBeFound("deliveryMethod.contains=" + UPDATED_DELIVERY_METHOD);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByDeliveryMethodNotContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where deliveryMethod does not contain DEFAULT_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldNotBeFound("deliveryMethod.doesNotContain=" + DEFAULT_DELIVERY_METHOD);

        // Get all the ordBillingAccountRefList where deliveryMethod does not contain UPDATED_DELIVERY_METHOD
        defaultOrdBillingAccountRefShouldBeFound("deliveryMethod.doesNotContain=" + UPDATED_DELIVERY_METHOD);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAddress equals to DEFAULT_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldBeFound("billingAddress.equals=" + DEFAULT_BILLING_ADDRESS);

        // Get all the ordBillingAccountRefList where billingAddress equals to UPDATED_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldNotBeFound("billingAddress.equals=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAddress not equals to DEFAULT_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldNotBeFound("billingAddress.notEquals=" + DEFAULT_BILLING_ADDRESS);

        // Get all the ordBillingAccountRefList where billingAddress not equals to UPDATED_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldBeFound("billingAddress.notEquals=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAddressIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAddress in DEFAULT_BILLING_ADDRESS or UPDATED_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldBeFound("billingAddress.in=" + DEFAULT_BILLING_ADDRESS + "," + UPDATED_BILLING_ADDRESS);

        // Get all the ordBillingAccountRefList where billingAddress equals to UPDATED_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldNotBeFound("billingAddress.in=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAddress is not null
        defaultOrdBillingAccountRefShouldBeFound("billingAddress.specified=true");

        // Get all the ordBillingAccountRefList where billingAddress is null
        defaultOrdBillingAccountRefShouldNotBeFound("billingAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAddressContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAddress contains DEFAULT_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldBeFound("billingAddress.contains=" + DEFAULT_BILLING_ADDRESS);

        // Get all the ordBillingAccountRefList where billingAddress contains UPDATED_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldNotBeFound("billingAddress.contains=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByBillingAddressNotContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where billingAddress does not contain DEFAULT_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldNotBeFound("billingAddress.doesNotContain=" + DEFAULT_BILLING_ADDRESS);

        // Get all the ordBillingAccountRefList where billingAddress does not contain UPDATED_BILLING_ADDRESS
        defaultOrdBillingAccountRefShouldBeFound("billingAddress.doesNotContain=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where status equals to DEFAULT_STATUS
        defaultOrdBillingAccountRefShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ordBillingAccountRefList where status equals to UPDATED_STATUS
        defaultOrdBillingAccountRefShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where status not equals to DEFAULT_STATUS
        defaultOrdBillingAccountRefShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ordBillingAccountRefList where status not equals to UPDATED_STATUS
        defaultOrdBillingAccountRefShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrdBillingAccountRefShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ordBillingAccountRefList where status equals to UPDATED_STATUS
        defaultOrdBillingAccountRefShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where status is not null
        defaultOrdBillingAccountRefShouldBeFound("status.specified=true");

        // Get all the ordBillingAccountRefList where status is null
        defaultOrdBillingAccountRefShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByStatusContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where status contains DEFAULT_STATUS
        defaultOrdBillingAccountRefShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the ordBillingAccountRefList where status contains UPDATED_STATUS
        defaultOrdBillingAccountRefShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where status does not contain DEFAULT_STATUS
        defaultOrdBillingAccountRefShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the ordBillingAccountRefList where status does not contain UPDATED_STATUS
        defaultOrdBillingAccountRefShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId equals to DEFAULT_QUOTE_ID
        defaultOrdBillingAccountRefShouldBeFound("quoteId.equals=" + DEFAULT_QUOTE_ID);

        // Get all the ordBillingAccountRefList where quoteId equals to UPDATED_QUOTE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.equals=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId not equals to DEFAULT_QUOTE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.notEquals=" + DEFAULT_QUOTE_ID);

        // Get all the ordBillingAccountRefList where quoteId not equals to UPDATED_QUOTE_ID
        defaultOrdBillingAccountRefShouldBeFound("quoteId.notEquals=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId in DEFAULT_QUOTE_ID or UPDATED_QUOTE_ID
        defaultOrdBillingAccountRefShouldBeFound("quoteId.in=" + DEFAULT_QUOTE_ID + "," + UPDATED_QUOTE_ID);

        // Get all the ordBillingAccountRefList where quoteId equals to UPDATED_QUOTE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.in=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId is not null
        defaultOrdBillingAccountRefShouldBeFound("quoteId.specified=true");

        // Get all the ordBillingAccountRefList where quoteId is null
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId is greater than or equal to DEFAULT_QUOTE_ID
        defaultOrdBillingAccountRefShouldBeFound("quoteId.greaterThanOrEqual=" + DEFAULT_QUOTE_ID);

        // Get all the ordBillingAccountRefList where quoteId is greater than or equal to UPDATED_QUOTE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.greaterThanOrEqual=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId is less than or equal to DEFAULT_QUOTE_ID
        defaultOrdBillingAccountRefShouldBeFound("quoteId.lessThanOrEqual=" + DEFAULT_QUOTE_ID);

        // Get all the ordBillingAccountRefList where quoteId is less than or equal to SMALLER_QUOTE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.lessThanOrEqual=" + SMALLER_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId is less than DEFAULT_QUOTE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.lessThan=" + DEFAULT_QUOTE_ID);

        // Get all the ordBillingAccountRefList where quoteId is less than UPDATED_QUOTE_ID
        defaultOrdBillingAccountRefShouldBeFound("quoteId.lessThan=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByQuoteIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where quoteId is greater than DEFAULT_QUOTE_ID
        defaultOrdBillingAccountRefShouldNotBeFound("quoteId.greaterThan=" + DEFAULT_QUOTE_ID);

        // Get all the ordBillingAccountRefList where quoteId is greater than SMALLER_QUOTE_ID
        defaultOrdBillingAccountRefShouldBeFound("quoteId.greaterThan=" + SMALLER_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId equals to DEFAULT_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.equals=" + DEFAULT_SALES_ORDER_ID);

        // Get all the ordBillingAccountRefList where salesOrderId equals to UPDATED_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.equals=" + UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId not equals to DEFAULT_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.notEquals=" + DEFAULT_SALES_ORDER_ID);

        // Get all the ordBillingAccountRefList where salesOrderId not equals to UPDATED_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.notEquals=" + UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId in DEFAULT_SALES_ORDER_ID or UPDATED_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.in=" + DEFAULT_SALES_ORDER_ID + "," + UPDATED_SALES_ORDER_ID);

        // Get all the ordBillingAccountRefList where salesOrderId equals to UPDATED_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.in=" + UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId is not null
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.specified=true");

        // Get all the ordBillingAccountRefList where salesOrderId is null
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId is greater than or equal to DEFAULT_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.greaterThanOrEqual=" + DEFAULT_SALES_ORDER_ID);

        // Get all the ordBillingAccountRefList where salesOrderId is greater than or equal to UPDATED_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.greaterThanOrEqual=" + UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId is less than or equal to DEFAULT_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.lessThanOrEqual=" + DEFAULT_SALES_ORDER_ID);

        // Get all the ordBillingAccountRefList where salesOrderId is less than or equal to SMALLER_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.lessThanOrEqual=" + SMALLER_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId is less than DEFAULT_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.lessThan=" + DEFAULT_SALES_ORDER_ID);

        // Get all the ordBillingAccountRefList where salesOrderId is less than UPDATED_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.lessThan=" + UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsBySalesOrderIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        // Get all the ordBillingAccountRefList where salesOrderId is greater than DEFAULT_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldNotBeFound("salesOrderId.greaterThan=" + DEFAULT_SALES_ORDER_ID);

        // Get all the ordBillingAccountRefList where salesOrderId is greater than SMALLER_SALES_ORDER_ID
        defaultOrdBillingAccountRefShouldBeFound("salesOrderId.greaterThan=" + SMALLER_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdBillingAccountRefsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordBillingAccountRef.setOrdProductOrder(ordProductOrder);
        ordProductOrder.setOrdBillingAccountRef(ordBillingAccountRef);
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordBillingAccountRefList where ordProductOrder equals to ordProductOrderId
        defaultOrdBillingAccountRefShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordBillingAccountRefList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdBillingAccountRefShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdBillingAccountRefShouldBeFound(String filter) throws Exception {
        restOrdBillingAccountRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordBillingAccountRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].cartPriceId").value(hasItem(DEFAULT_CART_PRICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].billingAccountId").value(hasItem(DEFAULT_BILLING_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].billingSystem").value(hasItem(DEFAULT_BILLING_SYSTEM)))
            .andExpect(jsonPath("$.[*].deliveryMethod").value(hasItem(DEFAULT_DELIVERY_METHOD)))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].salesOrderId").value(hasItem(DEFAULT_SALES_ORDER_ID.intValue())));

        // Check, that the count call also returns 1
        restOrdBillingAccountRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdBillingAccountRefShouldNotBeFound(String filter) throws Exception {
        restOrdBillingAccountRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdBillingAccountRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdBillingAccountRef() throws Exception {
        // Get the ordBillingAccountRef
        restOrdBillingAccountRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdBillingAccountRef() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();

        // Update the ordBillingAccountRef
        OrdBillingAccountRef updatedOrdBillingAccountRef = ordBillingAccountRefRepository.findById(ordBillingAccountRef.getId()).get();
        // Disconnect from session so that the updates on updatedOrdBillingAccountRef are not directly saved in db
        em.detach(updatedOrdBillingAccountRef);
        updatedOrdBillingAccountRef
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .cartPriceId(UPDATED_CART_PRICE_ID)
            .billingAccountId(UPDATED_BILLING_ACCOUNT_ID)
            .billingSystem(UPDATED_BILLING_SYSTEM)
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .status(UPDATED_STATUS)
            .quoteId(UPDATED_QUOTE_ID)
            .salesOrderId(UPDATED_SALES_ORDER_ID);
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(updatedOrdBillingAccountRef);

        restOrdBillingAccountRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordBillingAccountRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
        OrdBillingAccountRef testOrdBillingAccountRef = ordBillingAccountRefList.get(ordBillingAccountRefList.size() - 1);
        assertThat(testOrdBillingAccountRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdBillingAccountRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdBillingAccountRef.getCartPriceId()).isEqualTo(UPDATED_CART_PRICE_ID);
        assertThat(testOrdBillingAccountRef.getBillingAccountId()).isEqualTo(UPDATED_BILLING_ACCOUNT_ID);
        assertThat(testOrdBillingAccountRef.getBillingSystem()).isEqualTo(UPDATED_BILLING_SYSTEM);
        assertThat(testOrdBillingAccountRef.getDeliveryMethod()).isEqualTo(UPDATED_DELIVERY_METHOD);
        assertThat(testOrdBillingAccountRef.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testOrdBillingAccountRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrdBillingAccountRef.getQuoteId()).isEqualTo(UPDATED_QUOTE_ID);
        assertThat(testOrdBillingAccountRef.getSalesOrderId()).isEqualTo(UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void putNonExistingOrdBillingAccountRef() throws Exception {
        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();
        ordBillingAccountRef.setId(count.incrementAndGet());

        // Create the OrdBillingAccountRef
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdBillingAccountRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordBillingAccountRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdBillingAccountRef() throws Exception {
        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();
        ordBillingAccountRef.setId(count.incrementAndGet());

        // Create the OrdBillingAccountRef
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdBillingAccountRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdBillingAccountRef() throws Exception {
        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();
        ordBillingAccountRef.setId(count.incrementAndGet());

        // Create the OrdBillingAccountRef
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdBillingAccountRefMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdBillingAccountRefWithPatch() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();

        // Update the ordBillingAccountRef using partial update
        OrdBillingAccountRef partialUpdatedOrdBillingAccountRef = new OrdBillingAccountRef();
        partialUpdatedOrdBillingAccountRef.setId(ordBillingAccountRef.getId());

        partialUpdatedOrdBillingAccountRef
            .billingSystem(UPDATED_BILLING_SYSTEM)
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .status(UPDATED_STATUS)
            .salesOrderId(UPDATED_SALES_ORDER_ID);

        restOrdBillingAccountRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdBillingAccountRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdBillingAccountRef))
            )
            .andExpect(status().isOk());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
        OrdBillingAccountRef testOrdBillingAccountRef = ordBillingAccountRefList.get(ordBillingAccountRefList.size() - 1);
        assertThat(testOrdBillingAccountRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdBillingAccountRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdBillingAccountRef.getCartPriceId()).isEqualTo(DEFAULT_CART_PRICE_ID);
        assertThat(testOrdBillingAccountRef.getBillingAccountId()).isEqualTo(DEFAULT_BILLING_ACCOUNT_ID);
        assertThat(testOrdBillingAccountRef.getBillingSystem()).isEqualTo(UPDATED_BILLING_SYSTEM);
        assertThat(testOrdBillingAccountRef.getDeliveryMethod()).isEqualTo(UPDATED_DELIVERY_METHOD);
        assertThat(testOrdBillingAccountRef.getBillingAddress()).isEqualTo(DEFAULT_BILLING_ADDRESS);
        assertThat(testOrdBillingAccountRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrdBillingAccountRef.getQuoteId()).isEqualTo(DEFAULT_QUOTE_ID);
        assertThat(testOrdBillingAccountRef.getSalesOrderId()).isEqualTo(UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void fullUpdateOrdBillingAccountRefWithPatch() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();

        // Update the ordBillingAccountRef using partial update
        OrdBillingAccountRef partialUpdatedOrdBillingAccountRef = new OrdBillingAccountRef();
        partialUpdatedOrdBillingAccountRef.setId(ordBillingAccountRef.getId());

        partialUpdatedOrdBillingAccountRef
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .cartPriceId(UPDATED_CART_PRICE_ID)
            .billingAccountId(UPDATED_BILLING_ACCOUNT_ID)
            .billingSystem(UPDATED_BILLING_SYSTEM)
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .status(UPDATED_STATUS)
            .quoteId(UPDATED_QUOTE_ID)
            .salesOrderId(UPDATED_SALES_ORDER_ID);

        restOrdBillingAccountRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdBillingAccountRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdBillingAccountRef))
            )
            .andExpect(status().isOk());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
        OrdBillingAccountRef testOrdBillingAccountRef = ordBillingAccountRefList.get(ordBillingAccountRefList.size() - 1);
        assertThat(testOrdBillingAccountRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdBillingAccountRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdBillingAccountRef.getCartPriceId()).isEqualTo(UPDATED_CART_PRICE_ID);
        assertThat(testOrdBillingAccountRef.getBillingAccountId()).isEqualTo(UPDATED_BILLING_ACCOUNT_ID);
        assertThat(testOrdBillingAccountRef.getBillingSystem()).isEqualTo(UPDATED_BILLING_SYSTEM);
        assertThat(testOrdBillingAccountRef.getDeliveryMethod()).isEqualTo(UPDATED_DELIVERY_METHOD);
        assertThat(testOrdBillingAccountRef.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testOrdBillingAccountRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrdBillingAccountRef.getQuoteId()).isEqualTo(UPDATED_QUOTE_ID);
        assertThat(testOrdBillingAccountRef.getSalesOrderId()).isEqualTo(UPDATED_SALES_ORDER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOrdBillingAccountRef() throws Exception {
        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();
        ordBillingAccountRef.setId(count.incrementAndGet());

        // Create the OrdBillingAccountRef
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdBillingAccountRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordBillingAccountRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdBillingAccountRef() throws Exception {
        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();
        ordBillingAccountRef.setId(count.incrementAndGet());

        // Create the OrdBillingAccountRef
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdBillingAccountRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdBillingAccountRef() throws Exception {
        int databaseSizeBeforeUpdate = ordBillingAccountRefRepository.findAll().size();
        ordBillingAccountRef.setId(count.incrementAndGet());

        // Create the OrdBillingAccountRef
        OrdBillingAccountRefDTO ordBillingAccountRefDTO = ordBillingAccountRefMapper.toDto(ordBillingAccountRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdBillingAccountRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordBillingAccountRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdBillingAccountRef in the database
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdBillingAccountRef() throws Exception {
        // Initialize the database
        ordBillingAccountRefRepository.saveAndFlush(ordBillingAccountRef);

        int databaseSizeBeforeDelete = ordBillingAccountRefRepository.findAll().size();

        // Delete the ordBillingAccountRef
        restOrdBillingAccountRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordBillingAccountRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdBillingAccountRef> ordBillingAccountRefList = ordBillingAccountRefRepository.findAll();
        assertThat(ordBillingAccountRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
