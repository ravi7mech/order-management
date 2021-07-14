package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdAcquisition;
import com.apptium.order.domain.OrdBillingAccountRef;
import com.apptium.order.domain.OrdChannel;
import com.apptium.order.domain.OrdCharacteristics;
import com.apptium.order.domain.OrdContactDetails;
import com.apptium.order.domain.OrdContract;
import com.apptium.order.domain.OrdFulfillment;
import com.apptium.order.domain.OrdNote;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdOrderPrice;
import com.apptium.order.domain.OrdPaymentRef;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.domain.OrdReason;
import com.apptium.order.repository.OrdProductOrderRepository;
import com.apptium.order.service.criteria.OrdProductOrderCriteria;
import com.apptium.order.service.dto.OrdProductOrderDTO;
import com.apptium.order.service.mapper.OrdProductOrderMapper;
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
 * Integration tests for the {@link OrdProductOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdProductOrderResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_ORDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REQUESTED_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUESTED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REQUESTED_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUESTED_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPECTED_COMPLETION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_COMPLETION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTIFICATION_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_CONTACT = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final Integer DEFAULT_SHOPPING_CART_ID = 1;
    private static final Integer UPDATED_SHOPPING_CART_ID = 2;
    private static final Integer SMALLER_SHOPPING_CART_ID = 1 - 1;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;
    private static final Long SMALLER_LOCATION_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ord-product-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdProductOrderRepository ordProductOrderRepository;

    @Autowired
    private OrdProductOrderMapper ordProductOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdProductOrderMockMvc;

    private OrdProductOrder ordProductOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProductOrder createEntity(EntityManager em) {
        OrdProductOrder ordProductOrder = new OrdProductOrder()
            .href(DEFAULT_HREF)
            .externalId(DEFAULT_EXTERNAL_ID)
            .priority(DEFAULT_PRIORITY)
            .description(DEFAULT_DESCRIPTION)
            .category(DEFAULT_CATEGORY)
            .status(DEFAULT_STATUS)
            .orderDate(DEFAULT_ORDER_DATE)
            .completionDate(DEFAULT_COMPLETION_DATE)
            .requestedStartDate(DEFAULT_REQUESTED_START_DATE)
            .requestedCompletionDate(DEFAULT_REQUESTED_COMPLETION_DATE)
            .expectedCompletionDate(DEFAULT_EXPECTED_COMPLETION_DATE)
            .notificationContact(DEFAULT_NOTIFICATION_CONTACT)
            .customerId(DEFAULT_CUSTOMER_ID)
            .shoppingCartId(DEFAULT_SHOPPING_CART_ID)
            .type(DEFAULT_TYPE)
            .locationId(DEFAULT_LOCATION_ID);
        return ordProductOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProductOrder createUpdatedEntity(EntityManager em) {
        OrdProductOrder ordProductOrder = new OrdProductOrder()
            .href(UPDATED_HREF)
            .externalId(UPDATED_EXTERNAL_ID)
            .priority(UPDATED_PRIORITY)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .status(UPDATED_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .completionDate(UPDATED_COMPLETION_DATE)
            .requestedStartDate(UPDATED_REQUESTED_START_DATE)
            .requestedCompletionDate(UPDATED_REQUESTED_COMPLETION_DATE)
            .expectedCompletionDate(UPDATED_EXPECTED_COMPLETION_DATE)
            .notificationContact(UPDATED_NOTIFICATION_CONTACT)
            .customerId(UPDATED_CUSTOMER_ID)
            .shoppingCartId(UPDATED_SHOPPING_CART_ID)
            .type(UPDATED_TYPE)
            .locationId(UPDATED_LOCATION_ID);
        return ordProductOrder;
    }

    @BeforeEach
    public void initTest() {
        ordProductOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdProductOrder() throws Exception {
        int databaseSizeBeforeCreate = ordProductOrderRepository.findAll().size();
        // Create the OrdProductOrder
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);
        restOrdProductOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeCreate + 1);
        OrdProductOrder testOrdProductOrder = ordProductOrderList.get(ordProductOrderList.size() - 1);
        assertThat(testOrdProductOrder.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdProductOrder.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testOrdProductOrder.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testOrdProductOrder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrdProductOrder.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testOrdProductOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrdProductOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrdProductOrder.getCompletionDate()).isEqualTo(DEFAULT_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getRequestedStartDate()).isEqualTo(DEFAULT_REQUESTED_START_DATE);
        assertThat(testOrdProductOrder.getRequestedCompletionDate()).isEqualTo(DEFAULT_REQUESTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getExpectedCompletionDate()).isEqualTo(DEFAULT_EXPECTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getNotificationContact()).isEqualTo(DEFAULT_NOTIFICATION_CONTACT);
        assertThat(testOrdProductOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testOrdProductOrder.getShoppingCartId()).isEqualTo(DEFAULT_SHOPPING_CART_ID);
        assertThat(testOrdProductOrder.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrdProductOrder.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
    }

    @Test
    @Transactional
    void createOrdProductOrderWithExistingId() throws Exception {
        // Create the OrdProductOrder with an existing ID
        ordProductOrder.setId(1L);
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);

        int databaseSizeBeforeCreate = ordProductOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdProductOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdProductOrders() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList
        restOrdProductOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProductOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestedStartDate").value(hasItem(DEFAULT_REQUESTED_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestedCompletionDate").value(hasItem(DEFAULT_REQUESTED_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedCompletionDate").value(hasItem(DEFAULT_EXPECTED_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notificationContact").value(hasItem(DEFAULT_NOTIFICATION_CONTACT)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].shoppingCartId").value(hasItem(DEFAULT_SHOPPING_CART_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())));
    }

    @Test
    @Transactional
    void getOrdProductOrder() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get the ordProductOrder
        restOrdProductOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, ordProductOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordProductOrder.getId().intValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.requestedStartDate").value(DEFAULT_REQUESTED_START_DATE.toString()))
            .andExpect(jsonPath("$.requestedCompletionDate").value(DEFAULT_REQUESTED_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.expectedCompletionDate").value(DEFAULT_EXPECTED_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.notificationContact").value(DEFAULT_NOTIFICATION_CONTACT))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.shoppingCartId").value(DEFAULT_SHOPPING_CART_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID.intValue()));
    }

    @Test
    @Transactional
    void getOrdProductOrdersByIdFiltering() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        Long id = ordProductOrder.getId();

        defaultOrdProductOrderShouldBeFound("id.equals=" + id);
        defaultOrdProductOrderShouldNotBeFound("id.notEquals=" + id);

        defaultOrdProductOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdProductOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdProductOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdProductOrderShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where href equals to DEFAULT_HREF
        defaultOrdProductOrderShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the ordProductOrderList where href equals to UPDATED_HREF
        defaultOrdProductOrderShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where href not equals to DEFAULT_HREF
        defaultOrdProductOrderShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the ordProductOrderList where href not equals to UPDATED_HREF
        defaultOrdProductOrderShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where href in DEFAULT_HREF or UPDATED_HREF
        defaultOrdProductOrderShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the ordProductOrderList where href equals to UPDATED_HREF
        defaultOrdProductOrderShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where href is not null
        defaultOrdProductOrderShouldBeFound("href.specified=true");

        // Get all the ordProductOrderList where href is null
        defaultOrdProductOrderShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByHrefContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where href contains DEFAULT_HREF
        defaultOrdProductOrderShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the ordProductOrderList where href contains UPDATED_HREF
        defaultOrdProductOrderShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where href does not contain DEFAULT_HREF
        defaultOrdProductOrderShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the ordProductOrderList where href does not contain UPDATED_HREF
        defaultOrdProductOrderShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExternalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where externalId equals to DEFAULT_EXTERNAL_ID
        defaultOrdProductOrderShouldBeFound("externalId.equals=" + DEFAULT_EXTERNAL_ID);

        // Get all the ordProductOrderList where externalId equals to UPDATED_EXTERNAL_ID
        defaultOrdProductOrderShouldNotBeFound("externalId.equals=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExternalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where externalId not equals to DEFAULT_EXTERNAL_ID
        defaultOrdProductOrderShouldNotBeFound("externalId.notEquals=" + DEFAULT_EXTERNAL_ID);

        // Get all the ordProductOrderList where externalId not equals to UPDATED_EXTERNAL_ID
        defaultOrdProductOrderShouldBeFound("externalId.notEquals=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExternalIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where externalId in DEFAULT_EXTERNAL_ID or UPDATED_EXTERNAL_ID
        defaultOrdProductOrderShouldBeFound("externalId.in=" + DEFAULT_EXTERNAL_ID + "," + UPDATED_EXTERNAL_ID);

        // Get all the ordProductOrderList where externalId equals to UPDATED_EXTERNAL_ID
        defaultOrdProductOrderShouldNotBeFound("externalId.in=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExternalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where externalId is not null
        defaultOrdProductOrderShouldBeFound("externalId.specified=true");

        // Get all the ordProductOrderList where externalId is null
        defaultOrdProductOrderShouldNotBeFound("externalId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExternalIdContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where externalId contains DEFAULT_EXTERNAL_ID
        defaultOrdProductOrderShouldBeFound("externalId.contains=" + DEFAULT_EXTERNAL_ID);

        // Get all the ordProductOrderList where externalId contains UPDATED_EXTERNAL_ID
        defaultOrdProductOrderShouldNotBeFound("externalId.contains=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExternalIdNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where externalId does not contain DEFAULT_EXTERNAL_ID
        defaultOrdProductOrderShouldNotBeFound("externalId.doesNotContain=" + DEFAULT_EXTERNAL_ID);

        // Get all the ordProductOrderList where externalId does not contain UPDATED_EXTERNAL_ID
        defaultOrdProductOrderShouldBeFound("externalId.doesNotContain=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where priority equals to DEFAULT_PRIORITY
        defaultOrdProductOrderShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the ordProductOrderList where priority equals to UPDATED_PRIORITY
        defaultOrdProductOrderShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where priority not equals to DEFAULT_PRIORITY
        defaultOrdProductOrderShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the ordProductOrderList where priority not equals to UPDATED_PRIORITY
        defaultOrdProductOrderShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultOrdProductOrderShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the ordProductOrderList where priority equals to UPDATED_PRIORITY
        defaultOrdProductOrderShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where priority is not null
        defaultOrdProductOrderShouldBeFound("priority.specified=true");

        // Get all the ordProductOrderList where priority is null
        defaultOrdProductOrderShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByPriorityContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where priority contains DEFAULT_PRIORITY
        defaultOrdProductOrderShouldBeFound("priority.contains=" + DEFAULT_PRIORITY);

        // Get all the ordProductOrderList where priority contains UPDATED_PRIORITY
        defaultOrdProductOrderShouldNotBeFound("priority.contains=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByPriorityNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where priority does not contain DEFAULT_PRIORITY
        defaultOrdProductOrderShouldNotBeFound("priority.doesNotContain=" + DEFAULT_PRIORITY);

        // Get all the ordProductOrderList where priority does not contain UPDATED_PRIORITY
        defaultOrdProductOrderShouldBeFound("priority.doesNotContain=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where description equals to DEFAULT_DESCRIPTION
        defaultOrdProductOrderShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ordProductOrderList where description equals to UPDATED_DESCRIPTION
        defaultOrdProductOrderShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where description not equals to DEFAULT_DESCRIPTION
        defaultOrdProductOrderShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the ordProductOrderList where description not equals to UPDATED_DESCRIPTION
        defaultOrdProductOrderShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOrdProductOrderShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ordProductOrderList where description equals to UPDATED_DESCRIPTION
        defaultOrdProductOrderShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where description is not null
        defaultOrdProductOrderShouldBeFound("description.specified=true");

        // Get all the ordProductOrderList where description is null
        defaultOrdProductOrderShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where description contains DEFAULT_DESCRIPTION
        defaultOrdProductOrderShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ordProductOrderList where description contains UPDATED_DESCRIPTION
        defaultOrdProductOrderShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where description does not contain DEFAULT_DESCRIPTION
        defaultOrdProductOrderShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ordProductOrderList where description does not contain UPDATED_DESCRIPTION
        defaultOrdProductOrderShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where category equals to DEFAULT_CATEGORY
        defaultOrdProductOrderShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the ordProductOrderList where category equals to UPDATED_CATEGORY
        defaultOrdProductOrderShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where category not equals to DEFAULT_CATEGORY
        defaultOrdProductOrderShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the ordProductOrderList where category not equals to UPDATED_CATEGORY
        defaultOrdProductOrderShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultOrdProductOrderShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the ordProductOrderList where category equals to UPDATED_CATEGORY
        defaultOrdProductOrderShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where category is not null
        defaultOrdProductOrderShouldBeFound("category.specified=true");

        // Get all the ordProductOrderList where category is null
        defaultOrdProductOrderShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCategoryContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where category contains DEFAULT_CATEGORY
        defaultOrdProductOrderShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the ordProductOrderList where category contains UPDATED_CATEGORY
        defaultOrdProductOrderShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where category does not contain DEFAULT_CATEGORY
        defaultOrdProductOrderShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the ordProductOrderList where category does not contain UPDATED_CATEGORY
        defaultOrdProductOrderShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where status equals to DEFAULT_STATUS
        defaultOrdProductOrderShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ordProductOrderList where status equals to UPDATED_STATUS
        defaultOrdProductOrderShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where status not equals to DEFAULT_STATUS
        defaultOrdProductOrderShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ordProductOrderList where status not equals to UPDATED_STATUS
        defaultOrdProductOrderShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrdProductOrderShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ordProductOrderList where status equals to UPDATED_STATUS
        defaultOrdProductOrderShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where status is not null
        defaultOrdProductOrderShouldBeFound("status.specified=true");

        // Get all the ordProductOrderList where status is null
        defaultOrdProductOrderShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByStatusContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where status contains DEFAULT_STATUS
        defaultOrdProductOrderShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the ordProductOrderList where status contains UPDATED_STATUS
        defaultOrdProductOrderShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where status does not contain DEFAULT_STATUS
        defaultOrdProductOrderShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the ordProductOrderList where status does not contain UPDATED_STATUS
        defaultOrdProductOrderShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where orderDate equals to DEFAULT_ORDER_DATE
        defaultOrdProductOrderShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the ordProductOrderList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrdProductOrderShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrderDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where orderDate not equals to DEFAULT_ORDER_DATE
        defaultOrdProductOrderShouldNotBeFound("orderDate.notEquals=" + DEFAULT_ORDER_DATE);

        // Get all the ordProductOrderList where orderDate not equals to UPDATED_ORDER_DATE
        defaultOrdProductOrderShouldBeFound("orderDate.notEquals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultOrdProductOrderShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the ordProductOrderList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrdProductOrderShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where orderDate is not null
        defaultOrdProductOrderShouldBeFound("orderDate.specified=true");

        // Get all the ordProductOrderList where orderDate is null
        defaultOrdProductOrderShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where completionDate equals to DEFAULT_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound("completionDate.equals=" + DEFAULT_COMPLETION_DATE);

        // Get all the ordProductOrderList where completionDate equals to UPDATED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("completionDate.equals=" + UPDATED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCompletionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where completionDate not equals to DEFAULT_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("completionDate.notEquals=" + DEFAULT_COMPLETION_DATE);

        // Get all the ordProductOrderList where completionDate not equals to UPDATED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound("completionDate.notEquals=" + UPDATED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where completionDate in DEFAULT_COMPLETION_DATE or UPDATED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound("completionDate.in=" + DEFAULT_COMPLETION_DATE + "," + UPDATED_COMPLETION_DATE);

        // Get all the ordProductOrderList where completionDate equals to UPDATED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("completionDate.in=" + UPDATED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where completionDate is not null
        defaultOrdProductOrderShouldBeFound("completionDate.specified=true");

        // Get all the ordProductOrderList where completionDate is null
        defaultOrdProductOrderShouldNotBeFound("completionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedStartDate equals to DEFAULT_REQUESTED_START_DATE
        defaultOrdProductOrderShouldBeFound("requestedStartDate.equals=" + DEFAULT_REQUESTED_START_DATE);

        // Get all the ordProductOrderList where requestedStartDate equals to UPDATED_REQUESTED_START_DATE
        defaultOrdProductOrderShouldNotBeFound("requestedStartDate.equals=" + UPDATED_REQUESTED_START_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedStartDate not equals to DEFAULT_REQUESTED_START_DATE
        defaultOrdProductOrderShouldNotBeFound("requestedStartDate.notEquals=" + DEFAULT_REQUESTED_START_DATE);

        // Get all the ordProductOrderList where requestedStartDate not equals to UPDATED_REQUESTED_START_DATE
        defaultOrdProductOrderShouldBeFound("requestedStartDate.notEquals=" + UPDATED_REQUESTED_START_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedStartDate in DEFAULT_REQUESTED_START_DATE or UPDATED_REQUESTED_START_DATE
        defaultOrdProductOrderShouldBeFound("requestedStartDate.in=" + DEFAULT_REQUESTED_START_DATE + "," + UPDATED_REQUESTED_START_DATE);

        // Get all the ordProductOrderList where requestedStartDate equals to UPDATED_REQUESTED_START_DATE
        defaultOrdProductOrderShouldNotBeFound("requestedStartDate.in=" + UPDATED_REQUESTED_START_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedStartDate is not null
        defaultOrdProductOrderShouldBeFound("requestedStartDate.specified=true");

        // Get all the ordProductOrderList where requestedStartDate is null
        defaultOrdProductOrderShouldNotBeFound("requestedStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedCompletionDate equals to DEFAULT_REQUESTED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound("requestedCompletionDate.equals=" + DEFAULT_REQUESTED_COMPLETION_DATE);

        // Get all the ordProductOrderList where requestedCompletionDate equals to UPDATED_REQUESTED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("requestedCompletionDate.equals=" + UPDATED_REQUESTED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedCompletionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedCompletionDate not equals to DEFAULT_REQUESTED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("requestedCompletionDate.notEquals=" + DEFAULT_REQUESTED_COMPLETION_DATE);

        // Get all the ordProductOrderList where requestedCompletionDate not equals to UPDATED_REQUESTED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound("requestedCompletionDate.notEquals=" + UPDATED_REQUESTED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedCompletionDate in DEFAULT_REQUESTED_COMPLETION_DATE or UPDATED_REQUESTED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound(
            "requestedCompletionDate.in=" + DEFAULT_REQUESTED_COMPLETION_DATE + "," + UPDATED_REQUESTED_COMPLETION_DATE
        );

        // Get all the ordProductOrderList where requestedCompletionDate equals to UPDATED_REQUESTED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("requestedCompletionDate.in=" + UPDATED_REQUESTED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByRequestedCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where requestedCompletionDate is not null
        defaultOrdProductOrderShouldBeFound("requestedCompletionDate.specified=true");

        // Get all the ordProductOrderList where requestedCompletionDate is null
        defaultOrdProductOrderShouldNotBeFound("requestedCompletionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExpectedCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where expectedCompletionDate equals to DEFAULT_EXPECTED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound("expectedCompletionDate.equals=" + DEFAULT_EXPECTED_COMPLETION_DATE);

        // Get all the ordProductOrderList where expectedCompletionDate equals to UPDATED_EXPECTED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("expectedCompletionDate.equals=" + UPDATED_EXPECTED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExpectedCompletionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where expectedCompletionDate not equals to DEFAULT_EXPECTED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("expectedCompletionDate.notEquals=" + DEFAULT_EXPECTED_COMPLETION_DATE);

        // Get all the ordProductOrderList where expectedCompletionDate not equals to UPDATED_EXPECTED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound("expectedCompletionDate.notEquals=" + UPDATED_EXPECTED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExpectedCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where expectedCompletionDate in DEFAULT_EXPECTED_COMPLETION_DATE or UPDATED_EXPECTED_COMPLETION_DATE
        defaultOrdProductOrderShouldBeFound(
            "expectedCompletionDate.in=" + DEFAULT_EXPECTED_COMPLETION_DATE + "," + UPDATED_EXPECTED_COMPLETION_DATE
        );

        // Get all the ordProductOrderList where expectedCompletionDate equals to UPDATED_EXPECTED_COMPLETION_DATE
        defaultOrdProductOrderShouldNotBeFound("expectedCompletionDate.in=" + UPDATED_EXPECTED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByExpectedCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where expectedCompletionDate is not null
        defaultOrdProductOrderShouldBeFound("expectedCompletionDate.specified=true");

        // Get all the ordProductOrderList where expectedCompletionDate is null
        defaultOrdProductOrderShouldNotBeFound("expectedCompletionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByNotificationContactIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where notificationContact equals to DEFAULT_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldBeFound("notificationContact.equals=" + DEFAULT_NOTIFICATION_CONTACT);

        // Get all the ordProductOrderList where notificationContact equals to UPDATED_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldNotBeFound("notificationContact.equals=" + UPDATED_NOTIFICATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByNotificationContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where notificationContact not equals to DEFAULT_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldNotBeFound("notificationContact.notEquals=" + DEFAULT_NOTIFICATION_CONTACT);

        // Get all the ordProductOrderList where notificationContact not equals to UPDATED_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldBeFound("notificationContact.notEquals=" + UPDATED_NOTIFICATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByNotificationContactIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where notificationContact in DEFAULT_NOTIFICATION_CONTACT or UPDATED_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldBeFound("notificationContact.in=" + DEFAULT_NOTIFICATION_CONTACT + "," + UPDATED_NOTIFICATION_CONTACT);

        // Get all the ordProductOrderList where notificationContact equals to UPDATED_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldNotBeFound("notificationContact.in=" + UPDATED_NOTIFICATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByNotificationContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where notificationContact is not null
        defaultOrdProductOrderShouldBeFound("notificationContact.specified=true");

        // Get all the ordProductOrderList where notificationContact is null
        defaultOrdProductOrderShouldNotBeFound("notificationContact.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByNotificationContactContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where notificationContact contains DEFAULT_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldBeFound("notificationContact.contains=" + DEFAULT_NOTIFICATION_CONTACT);

        // Get all the ordProductOrderList where notificationContact contains UPDATED_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldNotBeFound("notificationContact.contains=" + UPDATED_NOTIFICATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByNotificationContactNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where notificationContact does not contain DEFAULT_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldNotBeFound("notificationContact.doesNotContain=" + DEFAULT_NOTIFICATION_CONTACT);

        // Get all the ordProductOrderList where notificationContact does not contain UPDATED_NOTIFICATION_CONTACT
        defaultOrdProductOrderShouldBeFound("notificationContact.doesNotContain=" + UPDATED_NOTIFICATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultOrdProductOrderShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the ordProductOrderList where customerId equals to UPDATED_CUSTOMER_ID
        defaultOrdProductOrderShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultOrdProductOrderShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the ordProductOrderList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultOrdProductOrderShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultOrdProductOrderShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the ordProductOrderList where customerId equals to UPDATED_CUSTOMER_ID
        defaultOrdProductOrderShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId is not null
        defaultOrdProductOrderShouldBeFound("customerId.specified=true");

        // Get all the ordProductOrderList where customerId is null
        defaultOrdProductOrderShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultOrdProductOrderShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the ordProductOrderList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultOrdProductOrderShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultOrdProductOrderShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the ordProductOrderList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultOrdProductOrderShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultOrdProductOrderShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the ordProductOrderList where customerId is less than UPDATED_CUSTOMER_ID
        defaultOrdProductOrderShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultOrdProductOrderShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the ordProductOrderList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultOrdProductOrderShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId equals to DEFAULT_SHOPPING_CART_ID
        defaultOrdProductOrderShouldBeFound("shoppingCartId.equals=" + DEFAULT_SHOPPING_CART_ID);

        // Get all the ordProductOrderList where shoppingCartId equals to UPDATED_SHOPPING_CART_ID
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.equals=" + UPDATED_SHOPPING_CART_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId not equals to DEFAULT_SHOPPING_CART_ID
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.notEquals=" + DEFAULT_SHOPPING_CART_ID);

        // Get all the ordProductOrderList where shoppingCartId not equals to UPDATED_SHOPPING_CART_ID
        defaultOrdProductOrderShouldBeFound("shoppingCartId.notEquals=" + UPDATED_SHOPPING_CART_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId in DEFAULT_SHOPPING_CART_ID or UPDATED_SHOPPING_CART_ID
        defaultOrdProductOrderShouldBeFound("shoppingCartId.in=" + DEFAULT_SHOPPING_CART_ID + "," + UPDATED_SHOPPING_CART_ID);

        // Get all the ordProductOrderList where shoppingCartId equals to UPDATED_SHOPPING_CART_ID
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.in=" + UPDATED_SHOPPING_CART_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId is not null
        defaultOrdProductOrderShouldBeFound("shoppingCartId.specified=true");

        // Get all the ordProductOrderList where shoppingCartId is null
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId is greater than or equal to DEFAULT_SHOPPING_CART_ID
        defaultOrdProductOrderShouldBeFound("shoppingCartId.greaterThanOrEqual=" + DEFAULT_SHOPPING_CART_ID);

        // Get all the ordProductOrderList where shoppingCartId is greater than or equal to UPDATED_SHOPPING_CART_ID
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.greaterThanOrEqual=" + UPDATED_SHOPPING_CART_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId is less than or equal to DEFAULT_SHOPPING_CART_ID
        defaultOrdProductOrderShouldBeFound("shoppingCartId.lessThanOrEqual=" + DEFAULT_SHOPPING_CART_ID);

        // Get all the ordProductOrderList where shoppingCartId is less than or equal to SMALLER_SHOPPING_CART_ID
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.lessThanOrEqual=" + SMALLER_SHOPPING_CART_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId is less than DEFAULT_SHOPPING_CART_ID
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.lessThan=" + DEFAULT_SHOPPING_CART_ID);

        // Get all the ordProductOrderList where shoppingCartId is less than UPDATED_SHOPPING_CART_ID
        defaultOrdProductOrderShouldBeFound("shoppingCartId.lessThan=" + UPDATED_SHOPPING_CART_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByShoppingCartIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where shoppingCartId is greater than DEFAULT_SHOPPING_CART_ID
        defaultOrdProductOrderShouldNotBeFound("shoppingCartId.greaterThan=" + DEFAULT_SHOPPING_CART_ID);

        // Get all the ordProductOrderList where shoppingCartId is greater than SMALLER_SHOPPING_CART_ID
        defaultOrdProductOrderShouldBeFound("shoppingCartId.greaterThan=" + SMALLER_SHOPPING_CART_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where type equals to DEFAULT_TYPE
        defaultOrdProductOrderShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the ordProductOrderList where type equals to UPDATED_TYPE
        defaultOrdProductOrderShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where type not equals to DEFAULT_TYPE
        defaultOrdProductOrderShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the ordProductOrderList where type not equals to UPDATED_TYPE
        defaultOrdProductOrderShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultOrdProductOrderShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the ordProductOrderList where type equals to UPDATED_TYPE
        defaultOrdProductOrderShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where type is not null
        defaultOrdProductOrderShouldBeFound("type.specified=true");

        // Get all the ordProductOrderList where type is null
        defaultOrdProductOrderShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByTypeContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where type contains DEFAULT_TYPE
        defaultOrdProductOrderShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the ordProductOrderList where type contains UPDATED_TYPE
        defaultOrdProductOrderShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where type does not contain DEFAULT_TYPE
        defaultOrdProductOrderShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the ordProductOrderList where type does not contain UPDATED_TYPE
        defaultOrdProductOrderShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId equals to DEFAULT_LOCATION_ID
        defaultOrdProductOrderShouldBeFound("locationId.equals=" + DEFAULT_LOCATION_ID);

        // Get all the ordProductOrderList where locationId equals to UPDATED_LOCATION_ID
        defaultOrdProductOrderShouldNotBeFound("locationId.equals=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId not equals to DEFAULT_LOCATION_ID
        defaultOrdProductOrderShouldNotBeFound("locationId.notEquals=" + DEFAULT_LOCATION_ID);

        // Get all the ordProductOrderList where locationId not equals to UPDATED_LOCATION_ID
        defaultOrdProductOrderShouldBeFound("locationId.notEquals=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId in DEFAULT_LOCATION_ID or UPDATED_LOCATION_ID
        defaultOrdProductOrderShouldBeFound("locationId.in=" + DEFAULT_LOCATION_ID + "," + UPDATED_LOCATION_ID);

        // Get all the ordProductOrderList where locationId equals to UPDATED_LOCATION_ID
        defaultOrdProductOrderShouldNotBeFound("locationId.in=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId is not null
        defaultOrdProductOrderShouldBeFound("locationId.specified=true");

        // Get all the ordProductOrderList where locationId is null
        defaultOrdProductOrderShouldNotBeFound("locationId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId is greater than or equal to DEFAULT_LOCATION_ID
        defaultOrdProductOrderShouldBeFound("locationId.greaterThanOrEqual=" + DEFAULT_LOCATION_ID);

        // Get all the ordProductOrderList where locationId is greater than or equal to UPDATED_LOCATION_ID
        defaultOrdProductOrderShouldNotBeFound("locationId.greaterThanOrEqual=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId is less than or equal to DEFAULT_LOCATION_ID
        defaultOrdProductOrderShouldBeFound("locationId.lessThanOrEqual=" + DEFAULT_LOCATION_ID);

        // Get all the ordProductOrderList where locationId is less than or equal to SMALLER_LOCATION_ID
        defaultOrdProductOrderShouldNotBeFound("locationId.lessThanOrEqual=" + SMALLER_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId is less than DEFAULT_LOCATION_ID
        defaultOrdProductOrderShouldNotBeFound("locationId.lessThan=" + DEFAULT_LOCATION_ID);

        // Get all the ordProductOrderList where locationId is less than UPDATED_LOCATION_ID
        defaultOrdProductOrderShouldBeFound("locationId.lessThan=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByLocationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        // Get all the ordProductOrderList where locationId is greater than DEFAULT_LOCATION_ID
        defaultOrdProductOrderShouldNotBeFound("locationId.greaterThan=" + DEFAULT_LOCATION_ID);

        // Get all the ordProductOrderList where locationId is greater than SMALLER_LOCATION_ID
        defaultOrdProductOrderShouldBeFound("locationId.greaterThan=" + SMALLER_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdContactDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdContactDetails ordContactDetails = OrdContactDetailsResourceIT.createEntity(em);
        em.persist(ordContactDetails);
        em.flush();
        ordProductOrder.setOrdContactDetails(ordContactDetails);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordContactDetailsId = ordContactDetails.getId();

        // Get all the ordProductOrderList where ordContactDetails equals to ordContactDetailsId
        defaultOrdProductOrderShouldBeFound("ordContactDetailsId.equals=" + ordContactDetailsId);

        // Get all the ordProductOrderList where ordContactDetails equals to (ordContactDetailsId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordContactDetailsId.equals=" + (ordContactDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdNote ordNote = OrdNoteResourceIT.createEntity(em);
        em.persist(ordNote);
        em.flush();
        ordProductOrder.setOrdNote(ordNote);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordNoteId = ordNote.getId();

        // Get all the ordProductOrderList where ordNote equals to ordNoteId
        defaultOrdProductOrderShouldBeFound("ordNoteId.equals=" + ordNoteId);

        // Get all the ordProductOrderList where ordNote equals to (ordNoteId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordNoteId.equals=" + (ordNoteId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdChannel ordChannel = OrdChannelResourceIT.createEntity(em);
        em.persist(ordChannel);
        em.flush();
        ordProductOrder.setOrdChannel(ordChannel);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordChannelId = ordChannel.getId();

        // Get all the ordProductOrderList where ordChannel equals to ordChannelId
        defaultOrdProductOrderShouldBeFound("ordChannelId.equals=" + ordChannelId);

        // Get all the ordProductOrderList where ordChannel equals to (ordChannelId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordChannelId.equals=" + (ordChannelId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdOrderPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdOrderPrice ordOrderPrice = OrdOrderPriceResourceIT.createEntity(em);
        em.persist(ordOrderPrice);
        em.flush();
        ordProductOrder.setOrdOrderPrice(ordOrderPrice);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordOrderPriceId = ordOrderPrice.getId();

        // Get all the ordProductOrderList where ordOrderPrice equals to ordOrderPriceId
        defaultOrdProductOrderShouldBeFound("ordOrderPriceId.equals=" + ordOrderPriceId);

        // Get all the ordProductOrderList where ordOrderPrice equals to (ordOrderPriceId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordOrderPriceId.equals=" + (ordOrderPriceId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdBillingAccountRefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdBillingAccountRef ordBillingAccountRef = OrdBillingAccountRefResourceIT.createEntity(em);
        em.persist(ordBillingAccountRef);
        em.flush();
        ordProductOrder.setOrdBillingAccountRef(ordBillingAccountRef);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordBillingAccountRefId = ordBillingAccountRef.getId();

        // Get all the ordProductOrderList where ordBillingAccountRef equals to ordBillingAccountRefId
        defaultOrdProductOrderShouldBeFound("ordBillingAccountRefId.equals=" + ordBillingAccountRefId);

        // Get all the ordProductOrderList where ordBillingAccountRef equals to (ordBillingAccountRefId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordBillingAccountRefId.equals=" + (ordBillingAccountRefId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdCharacteristicsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdCharacteristics ordCharacteristics = OrdCharacteristicsResourceIT.createEntity(em);
        em.persist(ordCharacteristics);
        em.flush();
        ordProductOrder.addOrdCharacteristics(ordCharacteristics);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordCharacteristicsId = ordCharacteristics.getId();

        // Get all the ordProductOrderList where ordCharacteristics equals to ordCharacteristicsId
        defaultOrdProductOrderShouldBeFound("ordCharacteristicsId.equals=" + ordCharacteristicsId);

        // Get all the ordProductOrderList where ordCharacteristics equals to (ordCharacteristicsId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordCharacteristicsId.equals=" + (ordCharacteristicsId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdOrderItem ordOrderItem = OrdOrderItemResourceIT.createEntity(em);
        em.persist(ordOrderItem);
        em.flush();
        ordProductOrder.addOrdOrderItem(ordOrderItem);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordOrderItemId = ordOrderItem.getId();

        // Get all the ordProductOrderList where ordOrderItem equals to ordOrderItemId
        defaultOrdProductOrderShouldBeFound("ordOrderItemId.equals=" + ordOrderItemId);

        // Get all the ordProductOrderList where ordOrderItem equals to (ordOrderItemId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordOrderItemId.equals=" + (ordOrderItemId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdPaymentRefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdPaymentRef ordPaymentRef = OrdPaymentRefResourceIT.createEntity(em);
        em.persist(ordPaymentRef);
        em.flush();
        ordProductOrder.addOrdPaymentRef(ordPaymentRef);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordPaymentRefId = ordPaymentRef.getId();

        // Get all the ordProductOrderList where ordPaymentRef equals to ordPaymentRefId
        defaultOrdProductOrderShouldBeFound("ordPaymentRefId.equals=" + ordPaymentRefId);

        // Get all the ordProductOrderList where ordPaymentRef equals to (ordPaymentRefId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordPaymentRefId.equals=" + (ordPaymentRefId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdReason ordReason = OrdReasonResourceIT.createEntity(em);
        em.persist(ordReason);
        em.flush();
        ordProductOrder.addOrdReason(ordReason);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordReasonId = ordReason.getId();

        // Get all the ordProductOrderList where ordReason equals to ordReasonId
        defaultOrdProductOrderShouldBeFound("ordReasonId.equals=" + ordReasonId);

        // Get all the ordProductOrderList where ordReason equals to (ordReasonId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordReasonId.equals=" + (ordReasonId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdContractIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdContract ordContract = OrdContractResourceIT.createEntity(em);
        em.persist(ordContract);
        em.flush();
        ordProductOrder.addOrdContract(ordContract);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordContractId = ordContract.getId();

        // Get all the ordProductOrderList where ordContract equals to ordContractId
        defaultOrdProductOrderShouldBeFound("ordContractId.equals=" + ordContractId);

        // Get all the ordProductOrderList where ordContract equals to (ordContractId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordContractId.equals=" + (ordContractId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdFulfillmentIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdFulfillment ordFulfillment = OrdFulfillmentResourceIT.createEntity(em);
        em.persist(ordFulfillment);
        em.flush();
        ordProductOrder.addOrdFulfillment(ordFulfillment);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordFulfillmentId = ordFulfillment.getId();

        // Get all the ordProductOrderList where ordFulfillment equals to ordFulfillmentId
        defaultOrdProductOrderShouldBeFound("ordFulfillmentId.equals=" + ordFulfillmentId);

        // Get all the ordProductOrderList where ordFulfillment equals to (ordFulfillmentId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordFulfillmentId.equals=" + (ordFulfillmentId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductOrdersByOrdAcquisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        OrdAcquisition ordAcquisition = OrdAcquisitionResourceIT.createEntity(em);
        em.persist(ordAcquisition);
        em.flush();
        ordProductOrder.addOrdAcquisition(ordAcquisition);
        ordProductOrderRepository.saveAndFlush(ordProductOrder);
        Long ordAcquisitionId = ordAcquisition.getId();

        // Get all the ordProductOrderList where ordAcquisition equals to ordAcquisitionId
        defaultOrdProductOrderShouldBeFound("ordAcquisitionId.equals=" + ordAcquisitionId);

        // Get all the ordProductOrderList where ordAcquisition equals to (ordAcquisitionId + 1)
        defaultOrdProductOrderShouldNotBeFound("ordAcquisitionId.equals=" + (ordAcquisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdProductOrderShouldBeFound(String filter) throws Exception {
        restOrdProductOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProductOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestedStartDate").value(hasItem(DEFAULT_REQUESTED_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestedCompletionDate").value(hasItem(DEFAULT_REQUESTED_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedCompletionDate").value(hasItem(DEFAULT_EXPECTED_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notificationContact").value(hasItem(DEFAULT_NOTIFICATION_CONTACT)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].shoppingCartId").value(hasItem(DEFAULT_SHOPPING_CART_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())));

        // Check, that the count call also returns 1
        restOrdProductOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdProductOrderShouldNotBeFound(String filter) throws Exception {
        restOrdProductOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdProductOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdProductOrder() throws Exception {
        // Get the ordProductOrder
        restOrdProductOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdProductOrder() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();

        // Update the ordProductOrder
        OrdProductOrder updatedOrdProductOrder = ordProductOrderRepository.findById(ordProductOrder.getId()).get();
        // Disconnect from session so that the updates on updatedOrdProductOrder are not directly saved in db
        em.detach(updatedOrdProductOrder);
        updatedOrdProductOrder
            .href(UPDATED_HREF)
            .externalId(UPDATED_EXTERNAL_ID)
            .priority(UPDATED_PRIORITY)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .status(UPDATED_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .completionDate(UPDATED_COMPLETION_DATE)
            .requestedStartDate(UPDATED_REQUESTED_START_DATE)
            .requestedCompletionDate(UPDATED_REQUESTED_COMPLETION_DATE)
            .expectedCompletionDate(UPDATED_EXPECTED_COMPLETION_DATE)
            .notificationContact(UPDATED_NOTIFICATION_CONTACT)
            .customerId(UPDATED_CUSTOMER_ID)
            .shoppingCartId(UPDATED_SHOPPING_CART_ID)
            .type(UPDATED_TYPE)
            .locationId(UPDATED_LOCATION_ID);
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(updatedOrdProductOrder);

        restOrdProductOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
        OrdProductOrder testOrdProductOrder = ordProductOrderList.get(ordProductOrderList.size() - 1);
        assertThat(testOrdProductOrder.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdProductOrder.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testOrdProductOrder.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testOrdProductOrder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdProductOrder.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testOrdProductOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrdProductOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrdProductOrder.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getRequestedStartDate()).isEqualTo(UPDATED_REQUESTED_START_DATE);
        assertThat(testOrdProductOrder.getRequestedCompletionDate()).isEqualTo(UPDATED_REQUESTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getExpectedCompletionDate()).isEqualTo(UPDATED_EXPECTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getNotificationContact()).isEqualTo(UPDATED_NOTIFICATION_CONTACT);
        assertThat(testOrdProductOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testOrdProductOrder.getShoppingCartId()).isEqualTo(UPDATED_SHOPPING_CART_ID);
        assertThat(testOrdProductOrder.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrdProductOrder.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void putNonExistingOrdProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();
        ordProductOrder.setId(count.incrementAndGet());

        // Create the OrdProductOrder
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();
        ordProductOrder.setId(count.incrementAndGet());

        // Create the OrdProductOrder
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();
        ordProductOrder.setId(count.incrementAndGet());

        // Create the OrdProductOrder
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOrderMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdProductOrderWithPatch() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();

        // Update the ordProductOrder using partial update
        OrdProductOrder partialUpdatedOrdProductOrder = new OrdProductOrder();
        partialUpdatedOrdProductOrder.setId(ordProductOrder.getId());

        partialUpdatedOrdProductOrder
            .href(UPDATED_HREF)
            .priority(UPDATED_PRIORITY)
            .description(UPDATED_DESCRIPTION)
            .completionDate(UPDATED_COMPLETION_DATE)
            .requestedStartDate(UPDATED_REQUESTED_START_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .type(UPDATED_TYPE)
            .locationId(UPDATED_LOCATION_ID);

        restOrdProductOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProductOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProductOrder))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
        OrdProductOrder testOrdProductOrder = ordProductOrderList.get(ordProductOrderList.size() - 1);
        assertThat(testOrdProductOrder.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdProductOrder.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testOrdProductOrder.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testOrdProductOrder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdProductOrder.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testOrdProductOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrdProductOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrdProductOrder.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getRequestedStartDate()).isEqualTo(UPDATED_REQUESTED_START_DATE);
        assertThat(testOrdProductOrder.getRequestedCompletionDate()).isEqualTo(DEFAULT_REQUESTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getExpectedCompletionDate()).isEqualTo(DEFAULT_EXPECTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getNotificationContact()).isEqualTo(DEFAULT_NOTIFICATION_CONTACT);
        assertThat(testOrdProductOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testOrdProductOrder.getShoppingCartId()).isEqualTo(DEFAULT_SHOPPING_CART_ID);
        assertThat(testOrdProductOrder.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrdProductOrder.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void fullUpdateOrdProductOrderWithPatch() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();

        // Update the ordProductOrder using partial update
        OrdProductOrder partialUpdatedOrdProductOrder = new OrdProductOrder();
        partialUpdatedOrdProductOrder.setId(ordProductOrder.getId());

        partialUpdatedOrdProductOrder
            .href(UPDATED_HREF)
            .externalId(UPDATED_EXTERNAL_ID)
            .priority(UPDATED_PRIORITY)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .status(UPDATED_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .completionDate(UPDATED_COMPLETION_DATE)
            .requestedStartDate(UPDATED_REQUESTED_START_DATE)
            .requestedCompletionDate(UPDATED_REQUESTED_COMPLETION_DATE)
            .expectedCompletionDate(UPDATED_EXPECTED_COMPLETION_DATE)
            .notificationContact(UPDATED_NOTIFICATION_CONTACT)
            .customerId(UPDATED_CUSTOMER_ID)
            .shoppingCartId(UPDATED_SHOPPING_CART_ID)
            .type(UPDATED_TYPE)
            .locationId(UPDATED_LOCATION_ID);

        restOrdProductOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProductOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProductOrder))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
        OrdProductOrder testOrdProductOrder = ordProductOrderList.get(ordProductOrderList.size() - 1);
        assertThat(testOrdProductOrder.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdProductOrder.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testOrdProductOrder.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testOrdProductOrder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdProductOrder.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testOrdProductOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrdProductOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrdProductOrder.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getRequestedStartDate()).isEqualTo(UPDATED_REQUESTED_START_DATE);
        assertThat(testOrdProductOrder.getRequestedCompletionDate()).isEqualTo(UPDATED_REQUESTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getExpectedCompletionDate()).isEqualTo(UPDATED_EXPECTED_COMPLETION_DATE);
        assertThat(testOrdProductOrder.getNotificationContact()).isEqualTo(UPDATED_NOTIFICATION_CONTACT);
        assertThat(testOrdProductOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testOrdProductOrder.getShoppingCartId()).isEqualTo(UPDATED_SHOPPING_CART_ID);
        assertThat(testOrdProductOrder.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrdProductOrder.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOrdProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();
        ordProductOrder.setId(count.incrementAndGet());

        // Create the OrdProductOrder
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordProductOrderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();
        ordProductOrder.setId(count.incrementAndGet());

        // Create the OrdProductOrder
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOrderRepository.findAll().size();
        ordProductOrder.setId(count.incrementAndGet());

        // Create the OrdProductOrder
        OrdProductOrderDTO ordProductOrderDTO = ordProductOrderMapper.toDto(ordProductOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOrderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProductOrder in the database
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdProductOrder() throws Exception {
        // Initialize the database
        ordProductOrderRepository.saveAndFlush(ordProductOrder);

        int databaseSizeBeforeDelete = ordProductOrderRepository.findAll().size();

        // Delete the ordProductOrder
        restOrdProductOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordProductOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdProductOrder> ordProductOrderList = ordProductOrderRepository.findAll();
        assertThat(ordProductOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
