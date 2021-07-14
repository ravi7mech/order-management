package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdFulfillment;
import com.apptium.order.domain.OrdFulfillmentChar;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdFulfillmentRepository;
import com.apptium.order.service.criteria.OrdFulfillmentCriteria;
import com.apptium.order.service.dto.OrdFulfillmentDTO;
import com.apptium.order.service.mapper.OrdFulfillmentMapper;
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
 * Integration tests for the {@link OrdFulfillmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdFulfillmentResourceIT {

    private static final Long DEFAULT_WORKORDER_ID = 1L;
    private static final Long UPDATED_WORKORDER_ID = 2L;
    private static final Long SMALLER_WORKORDER_ID = 1L - 1L;

    private static final Long DEFAULT_APPOINTMENT_ID = 1L;
    private static final Long UPDATED_APPOINTMENT_ID = 2L;
    private static final Long SMALLER_APPOINTMENT_ID = 1L - 1L;

    private static final String DEFAULT_ORDER_FULFILLMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_FULFILLMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATE_SHIPPING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATE_SHIPPING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_CALL_AHEAD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_CALL_AHEAD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_JOB_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_JOB_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-fulfillments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdFulfillmentRepository ordFulfillmentRepository;

    @Autowired
    private OrdFulfillmentMapper ordFulfillmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdFulfillmentMockMvc;

    private OrdFulfillment ordFulfillment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdFulfillment createEntity(EntityManager em) {
        OrdFulfillment ordFulfillment = new OrdFulfillment()
            .workorderId(DEFAULT_WORKORDER_ID)
            .appointmentId(DEFAULT_APPOINTMENT_ID)
            .orderFulfillmentType(DEFAULT_ORDER_FULFILLMENT_TYPE)
            .alternateShippingAddress(DEFAULT_ALTERNATE_SHIPPING_ADDRESS)
            .orderCallAheadNumber(DEFAULT_ORDER_CALL_AHEAD_NUMBER)
            .orderJobComments(DEFAULT_ORDER_JOB_COMMENTS)
            .status(DEFAULT_STATUS);
        return ordFulfillment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdFulfillment createUpdatedEntity(EntityManager em) {
        OrdFulfillment ordFulfillment = new OrdFulfillment()
            .workorderId(UPDATED_WORKORDER_ID)
            .appointmentId(UPDATED_APPOINTMENT_ID)
            .orderFulfillmentType(UPDATED_ORDER_FULFILLMENT_TYPE)
            .alternateShippingAddress(UPDATED_ALTERNATE_SHIPPING_ADDRESS)
            .orderCallAheadNumber(UPDATED_ORDER_CALL_AHEAD_NUMBER)
            .orderJobComments(UPDATED_ORDER_JOB_COMMENTS)
            .status(UPDATED_STATUS);
        return ordFulfillment;
    }

    @BeforeEach
    public void initTest() {
        ordFulfillment = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdFulfillment() throws Exception {
        int databaseSizeBeforeCreate = ordFulfillmentRepository.findAll().size();
        // Create the OrdFulfillment
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);
        restOrdFulfillmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeCreate + 1);
        OrdFulfillment testOrdFulfillment = ordFulfillmentList.get(ordFulfillmentList.size() - 1);
        assertThat(testOrdFulfillment.getWorkorderId()).isEqualTo(DEFAULT_WORKORDER_ID);
        assertThat(testOrdFulfillment.getAppointmentId()).isEqualTo(DEFAULT_APPOINTMENT_ID);
        assertThat(testOrdFulfillment.getOrderFulfillmentType()).isEqualTo(DEFAULT_ORDER_FULFILLMENT_TYPE);
        assertThat(testOrdFulfillment.getAlternateShippingAddress()).isEqualTo(DEFAULT_ALTERNATE_SHIPPING_ADDRESS);
        assertThat(testOrdFulfillment.getOrderCallAheadNumber()).isEqualTo(DEFAULT_ORDER_CALL_AHEAD_NUMBER);
        assertThat(testOrdFulfillment.getOrderJobComments()).isEqualTo(DEFAULT_ORDER_JOB_COMMENTS);
        assertThat(testOrdFulfillment.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createOrdFulfillmentWithExistingId() throws Exception {
        // Create the OrdFulfillment with an existing ID
        ordFulfillment.setId(1L);
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);

        int databaseSizeBeforeCreate = ordFulfillmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdFulfillmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdFulfillments() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList
        restOrdFulfillmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordFulfillment.getId().intValue())))
            .andExpect(jsonPath("$.[*].workorderId").value(hasItem(DEFAULT_WORKORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].appointmentId").value(hasItem(DEFAULT_APPOINTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].orderFulfillmentType").value(hasItem(DEFAULT_ORDER_FULFILLMENT_TYPE)))
            .andExpect(jsonPath("$.[*].alternateShippingAddress").value(hasItem(DEFAULT_ALTERNATE_SHIPPING_ADDRESS)))
            .andExpect(jsonPath("$.[*].orderCallAheadNumber").value(hasItem(DEFAULT_ORDER_CALL_AHEAD_NUMBER)))
            .andExpect(jsonPath("$.[*].orderJobComments").value(hasItem(DEFAULT_ORDER_JOB_COMMENTS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getOrdFulfillment() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get the ordFulfillment
        restOrdFulfillmentMockMvc
            .perform(get(ENTITY_API_URL_ID, ordFulfillment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordFulfillment.getId().intValue()))
            .andExpect(jsonPath("$.workorderId").value(DEFAULT_WORKORDER_ID.intValue()))
            .andExpect(jsonPath("$.appointmentId").value(DEFAULT_APPOINTMENT_ID.intValue()))
            .andExpect(jsonPath("$.orderFulfillmentType").value(DEFAULT_ORDER_FULFILLMENT_TYPE))
            .andExpect(jsonPath("$.alternateShippingAddress").value(DEFAULT_ALTERNATE_SHIPPING_ADDRESS))
            .andExpect(jsonPath("$.orderCallAheadNumber").value(DEFAULT_ORDER_CALL_AHEAD_NUMBER))
            .andExpect(jsonPath("$.orderJobComments").value(DEFAULT_ORDER_JOB_COMMENTS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getOrdFulfillmentsByIdFiltering() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        Long id = ordFulfillment.getId();

        defaultOrdFulfillmentShouldBeFound("id.equals=" + id);
        defaultOrdFulfillmentShouldNotBeFound("id.notEquals=" + id);

        defaultOrdFulfillmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdFulfillmentShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdFulfillmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdFulfillmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId equals to DEFAULT_WORKORDER_ID
        defaultOrdFulfillmentShouldBeFound("workorderId.equals=" + DEFAULT_WORKORDER_ID);

        // Get all the ordFulfillmentList where workorderId equals to UPDATED_WORKORDER_ID
        defaultOrdFulfillmentShouldNotBeFound("workorderId.equals=" + UPDATED_WORKORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId not equals to DEFAULT_WORKORDER_ID
        defaultOrdFulfillmentShouldNotBeFound("workorderId.notEquals=" + DEFAULT_WORKORDER_ID);

        // Get all the ordFulfillmentList where workorderId not equals to UPDATED_WORKORDER_ID
        defaultOrdFulfillmentShouldBeFound("workorderId.notEquals=" + UPDATED_WORKORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId in DEFAULT_WORKORDER_ID or UPDATED_WORKORDER_ID
        defaultOrdFulfillmentShouldBeFound("workorderId.in=" + DEFAULT_WORKORDER_ID + "," + UPDATED_WORKORDER_ID);

        // Get all the ordFulfillmentList where workorderId equals to UPDATED_WORKORDER_ID
        defaultOrdFulfillmentShouldNotBeFound("workorderId.in=" + UPDATED_WORKORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId is not null
        defaultOrdFulfillmentShouldBeFound("workorderId.specified=true");

        // Get all the ordFulfillmentList where workorderId is null
        defaultOrdFulfillmentShouldNotBeFound("workorderId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId is greater than or equal to DEFAULT_WORKORDER_ID
        defaultOrdFulfillmentShouldBeFound("workorderId.greaterThanOrEqual=" + DEFAULT_WORKORDER_ID);

        // Get all the ordFulfillmentList where workorderId is greater than or equal to UPDATED_WORKORDER_ID
        defaultOrdFulfillmentShouldNotBeFound("workorderId.greaterThanOrEqual=" + UPDATED_WORKORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId is less than or equal to DEFAULT_WORKORDER_ID
        defaultOrdFulfillmentShouldBeFound("workorderId.lessThanOrEqual=" + DEFAULT_WORKORDER_ID);

        // Get all the ordFulfillmentList where workorderId is less than or equal to SMALLER_WORKORDER_ID
        defaultOrdFulfillmentShouldNotBeFound("workorderId.lessThanOrEqual=" + SMALLER_WORKORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId is less than DEFAULT_WORKORDER_ID
        defaultOrdFulfillmentShouldNotBeFound("workorderId.lessThan=" + DEFAULT_WORKORDER_ID);

        // Get all the ordFulfillmentList where workorderId is less than UPDATED_WORKORDER_ID
        defaultOrdFulfillmentShouldBeFound("workorderId.lessThan=" + UPDATED_WORKORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByWorkorderIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where workorderId is greater than DEFAULT_WORKORDER_ID
        defaultOrdFulfillmentShouldNotBeFound("workorderId.greaterThan=" + DEFAULT_WORKORDER_ID);

        // Get all the ordFulfillmentList where workorderId is greater than SMALLER_WORKORDER_ID
        defaultOrdFulfillmentShouldBeFound("workorderId.greaterThan=" + SMALLER_WORKORDER_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId equals to DEFAULT_APPOINTMENT_ID
        defaultOrdFulfillmentShouldBeFound("appointmentId.equals=" + DEFAULT_APPOINTMENT_ID);

        // Get all the ordFulfillmentList where appointmentId equals to UPDATED_APPOINTMENT_ID
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.equals=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId not equals to DEFAULT_APPOINTMENT_ID
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.notEquals=" + DEFAULT_APPOINTMENT_ID);

        // Get all the ordFulfillmentList where appointmentId not equals to UPDATED_APPOINTMENT_ID
        defaultOrdFulfillmentShouldBeFound("appointmentId.notEquals=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId in DEFAULT_APPOINTMENT_ID or UPDATED_APPOINTMENT_ID
        defaultOrdFulfillmentShouldBeFound("appointmentId.in=" + DEFAULT_APPOINTMENT_ID + "," + UPDATED_APPOINTMENT_ID);

        // Get all the ordFulfillmentList where appointmentId equals to UPDATED_APPOINTMENT_ID
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.in=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId is not null
        defaultOrdFulfillmentShouldBeFound("appointmentId.specified=true");

        // Get all the ordFulfillmentList where appointmentId is null
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId is greater than or equal to DEFAULT_APPOINTMENT_ID
        defaultOrdFulfillmentShouldBeFound("appointmentId.greaterThanOrEqual=" + DEFAULT_APPOINTMENT_ID);

        // Get all the ordFulfillmentList where appointmentId is greater than or equal to UPDATED_APPOINTMENT_ID
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.greaterThanOrEqual=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId is less than or equal to DEFAULT_APPOINTMENT_ID
        defaultOrdFulfillmentShouldBeFound("appointmentId.lessThanOrEqual=" + DEFAULT_APPOINTMENT_ID);

        // Get all the ordFulfillmentList where appointmentId is less than or equal to SMALLER_APPOINTMENT_ID
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.lessThanOrEqual=" + SMALLER_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId is less than DEFAULT_APPOINTMENT_ID
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.lessThan=" + DEFAULT_APPOINTMENT_ID);

        // Get all the ordFulfillmentList where appointmentId is less than UPDATED_APPOINTMENT_ID
        defaultOrdFulfillmentShouldBeFound("appointmentId.lessThan=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAppointmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where appointmentId is greater than DEFAULT_APPOINTMENT_ID
        defaultOrdFulfillmentShouldNotBeFound("appointmentId.greaterThan=" + DEFAULT_APPOINTMENT_ID);

        // Get all the ordFulfillmentList where appointmentId is greater than SMALLER_APPOINTMENT_ID
        defaultOrdFulfillmentShouldBeFound("appointmentId.greaterThan=" + SMALLER_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderFulfillmentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderFulfillmentType equals to DEFAULT_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldBeFound("orderFulfillmentType.equals=" + DEFAULT_ORDER_FULFILLMENT_TYPE);

        // Get all the ordFulfillmentList where orderFulfillmentType equals to UPDATED_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldNotBeFound("orderFulfillmentType.equals=" + UPDATED_ORDER_FULFILLMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderFulfillmentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderFulfillmentType not equals to DEFAULT_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldNotBeFound("orderFulfillmentType.notEquals=" + DEFAULT_ORDER_FULFILLMENT_TYPE);

        // Get all the ordFulfillmentList where orderFulfillmentType not equals to UPDATED_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldBeFound("orderFulfillmentType.notEquals=" + UPDATED_ORDER_FULFILLMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderFulfillmentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderFulfillmentType in DEFAULT_ORDER_FULFILLMENT_TYPE or UPDATED_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldBeFound(
            "orderFulfillmentType.in=" + DEFAULT_ORDER_FULFILLMENT_TYPE + "," + UPDATED_ORDER_FULFILLMENT_TYPE
        );

        // Get all the ordFulfillmentList where orderFulfillmentType equals to UPDATED_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldNotBeFound("orderFulfillmentType.in=" + UPDATED_ORDER_FULFILLMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderFulfillmentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderFulfillmentType is not null
        defaultOrdFulfillmentShouldBeFound("orderFulfillmentType.specified=true");

        // Get all the ordFulfillmentList where orderFulfillmentType is null
        defaultOrdFulfillmentShouldNotBeFound("orderFulfillmentType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderFulfillmentTypeContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderFulfillmentType contains DEFAULT_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldBeFound("orderFulfillmentType.contains=" + DEFAULT_ORDER_FULFILLMENT_TYPE);

        // Get all the ordFulfillmentList where orderFulfillmentType contains UPDATED_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldNotBeFound("orderFulfillmentType.contains=" + UPDATED_ORDER_FULFILLMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderFulfillmentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderFulfillmentType does not contain DEFAULT_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldNotBeFound("orderFulfillmentType.doesNotContain=" + DEFAULT_ORDER_FULFILLMENT_TYPE);

        // Get all the ordFulfillmentList where orderFulfillmentType does not contain UPDATED_ORDER_FULFILLMENT_TYPE
        defaultOrdFulfillmentShouldBeFound("orderFulfillmentType.doesNotContain=" + UPDATED_ORDER_FULFILLMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAlternateShippingAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where alternateShippingAddress equals to DEFAULT_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldBeFound("alternateShippingAddress.equals=" + DEFAULT_ALTERNATE_SHIPPING_ADDRESS);

        // Get all the ordFulfillmentList where alternateShippingAddress equals to UPDATED_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldNotBeFound("alternateShippingAddress.equals=" + UPDATED_ALTERNATE_SHIPPING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAlternateShippingAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where alternateShippingAddress not equals to DEFAULT_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldNotBeFound("alternateShippingAddress.notEquals=" + DEFAULT_ALTERNATE_SHIPPING_ADDRESS);

        // Get all the ordFulfillmentList where alternateShippingAddress not equals to UPDATED_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldBeFound("alternateShippingAddress.notEquals=" + UPDATED_ALTERNATE_SHIPPING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAlternateShippingAddressIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where alternateShippingAddress in DEFAULT_ALTERNATE_SHIPPING_ADDRESS or UPDATED_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldBeFound(
            "alternateShippingAddress.in=" + DEFAULT_ALTERNATE_SHIPPING_ADDRESS + "," + UPDATED_ALTERNATE_SHIPPING_ADDRESS
        );

        // Get all the ordFulfillmentList where alternateShippingAddress equals to UPDATED_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldNotBeFound("alternateShippingAddress.in=" + UPDATED_ALTERNATE_SHIPPING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAlternateShippingAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where alternateShippingAddress is not null
        defaultOrdFulfillmentShouldBeFound("alternateShippingAddress.specified=true");

        // Get all the ordFulfillmentList where alternateShippingAddress is null
        defaultOrdFulfillmentShouldNotBeFound("alternateShippingAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAlternateShippingAddressContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where alternateShippingAddress contains DEFAULT_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldBeFound("alternateShippingAddress.contains=" + DEFAULT_ALTERNATE_SHIPPING_ADDRESS);

        // Get all the ordFulfillmentList where alternateShippingAddress contains UPDATED_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldNotBeFound("alternateShippingAddress.contains=" + UPDATED_ALTERNATE_SHIPPING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByAlternateShippingAddressNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where alternateShippingAddress does not contain DEFAULT_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldNotBeFound("alternateShippingAddress.doesNotContain=" + DEFAULT_ALTERNATE_SHIPPING_ADDRESS);

        // Get all the ordFulfillmentList where alternateShippingAddress does not contain UPDATED_ALTERNATE_SHIPPING_ADDRESS
        defaultOrdFulfillmentShouldBeFound("alternateShippingAddress.doesNotContain=" + UPDATED_ALTERNATE_SHIPPING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderCallAheadNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderCallAheadNumber equals to DEFAULT_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldBeFound("orderCallAheadNumber.equals=" + DEFAULT_ORDER_CALL_AHEAD_NUMBER);

        // Get all the ordFulfillmentList where orderCallAheadNumber equals to UPDATED_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldNotBeFound("orderCallAheadNumber.equals=" + UPDATED_ORDER_CALL_AHEAD_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderCallAheadNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderCallAheadNumber not equals to DEFAULT_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldNotBeFound("orderCallAheadNumber.notEquals=" + DEFAULT_ORDER_CALL_AHEAD_NUMBER);

        // Get all the ordFulfillmentList where orderCallAheadNumber not equals to UPDATED_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldBeFound("orderCallAheadNumber.notEquals=" + UPDATED_ORDER_CALL_AHEAD_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderCallAheadNumberIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderCallAheadNumber in DEFAULT_ORDER_CALL_AHEAD_NUMBER or UPDATED_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldBeFound(
            "orderCallAheadNumber.in=" + DEFAULT_ORDER_CALL_AHEAD_NUMBER + "," + UPDATED_ORDER_CALL_AHEAD_NUMBER
        );

        // Get all the ordFulfillmentList where orderCallAheadNumber equals to UPDATED_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldNotBeFound("orderCallAheadNumber.in=" + UPDATED_ORDER_CALL_AHEAD_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderCallAheadNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderCallAheadNumber is not null
        defaultOrdFulfillmentShouldBeFound("orderCallAheadNumber.specified=true");

        // Get all the ordFulfillmentList where orderCallAheadNumber is null
        defaultOrdFulfillmentShouldNotBeFound("orderCallAheadNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderCallAheadNumberContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderCallAheadNumber contains DEFAULT_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldBeFound("orderCallAheadNumber.contains=" + DEFAULT_ORDER_CALL_AHEAD_NUMBER);

        // Get all the ordFulfillmentList where orderCallAheadNumber contains UPDATED_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldNotBeFound("orderCallAheadNumber.contains=" + UPDATED_ORDER_CALL_AHEAD_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderCallAheadNumberNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderCallAheadNumber does not contain DEFAULT_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldNotBeFound("orderCallAheadNumber.doesNotContain=" + DEFAULT_ORDER_CALL_AHEAD_NUMBER);

        // Get all the ordFulfillmentList where orderCallAheadNumber does not contain UPDATED_ORDER_CALL_AHEAD_NUMBER
        defaultOrdFulfillmentShouldBeFound("orderCallAheadNumber.doesNotContain=" + UPDATED_ORDER_CALL_AHEAD_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderJobCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderJobComments equals to DEFAULT_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldBeFound("orderJobComments.equals=" + DEFAULT_ORDER_JOB_COMMENTS);

        // Get all the ordFulfillmentList where orderJobComments equals to UPDATED_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldNotBeFound("orderJobComments.equals=" + UPDATED_ORDER_JOB_COMMENTS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderJobCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderJobComments not equals to DEFAULT_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldNotBeFound("orderJobComments.notEquals=" + DEFAULT_ORDER_JOB_COMMENTS);

        // Get all the ordFulfillmentList where orderJobComments not equals to UPDATED_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldBeFound("orderJobComments.notEquals=" + UPDATED_ORDER_JOB_COMMENTS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderJobCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderJobComments in DEFAULT_ORDER_JOB_COMMENTS or UPDATED_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldBeFound("orderJobComments.in=" + DEFAULT_ORDER_JOB_COMMENTS + "," + UPDATED_ORDER_JOB_COMMENTS);

        // Get all the ordFulfillmentList where orderJobComments equals to UPDATED_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldNotBeFound("orderJobComments.in=" + UPDATED_ORDER_JOB_COMMENTS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderJobCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderJobComments is not null
        defaultOrdFulfillmentShouldBeFound("orderJobComments.specified=true");

        // Get all the ordFulfillmentList where orderJobComments is null
        defaultOrdFulfillmentShouldNotBeFound("orderJobComments.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderJobCommentsContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderJobComments contains DEFAULT_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldBeFound("orderJobComments.contains=" + DEFAULT_ORDER_JOB_COMMENTS);

        // Get all the ordFulfillmentList where orderJobComments contains UPDATED_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldNotBeFound("orderJobComments.contains=" + UPDATED_ORDER_JOB_COMMENTS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrderJobCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where orderJobComments does not contain DEFAULT_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldNotBeFound("orderJobComments.doesNotContain=" + DEFAULT_ORDER_JOB_COMMENTS);

        // Get all the ordFulfillmentList where orderJobComments does not contain UPDATED_ORDER_JOB_COMMENTS
        defaultOrdFulfillmentShouldBeFound("orderJobComments.doesNotContain=" + UPDATED_ORDER_JOB_COMMENTS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where status equals to DEFAULT_STATUS
        defaultOrdFulfillmentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ordFulfillmentList where status equals to UPDATED_STATUS
        defaultOrdFulfillmentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where status not equals to DEFAULT_STATUS
        defaultOrdFulfillmentShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ordFulfillmentList where status not equals to UPDATED_STATUS
        defaultOrdFulfillmentShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrdFulfillmentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ordFulfillmentList where status equals to UPDATED_STATUS
        defaultOrdFulfillmentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where status is not null
        defaultOrdFulfillmentShouldBeFound("status.specified=true");

        // Get all the ordFulfillmentList where status is null
        defaultOrdFulfillmentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByStatusContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where status contains DEFAULT_STATUS
        defaultOrdFulfillmentShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the ordFulfillmentList where status contains UPDATED_STATUS
        defaultOrdFulfillmentShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        // Get all the ordFulfillmentList where status does not contain DEFAULT_STATUS
        defaultOrdFulfillmentShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the ordFulfillmentList where status does not contain UPDATED_STATUS
        defaultOrdFulfillmentShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrdFulfillmentCharIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);
        OrdFulfillmentChar ordFulfillmentChar = OrdFulfillmentCharResourceIT.createEntity(em);
        em.persist(ordFulfillmentChar);
        em.flush();
        ordFulfillment.addOrdFulfillmentChar(ordFulfillmentChar);
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);
        Long ordFulfillmentCharId = ordFulfillmentChar.getId();

        // Get all the ordFulfillmentList where ordFulfillmentChar equals to ordFulfillmentCharId
        defaultOrdFulfillmentShouldBeFound("ordFulfillmentCharId.equals=" + ordFulfillmentCharId);

        // Get all the ordFulfillmentList where ordFulfillmentChar equals to (ordFulfillmentCharId + 1)
        defaultOrdFulfillmentShouldNotBeFound("ordFulfillmentCharId.equals=" + (ordFulfillmentCharId + 1));
    }

    @Test
    @Transactional
    void getAllOrdFulfillmentsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordFulfillment.setOrdProductOrder(ordProductOrder);
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordFulfillmentList where ordProductOrder equals to ordProductOrderId
        defaultOrdFulfillmentShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordFulfillmentList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdFulfillmentShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdFulfillmentShouldBeFound(String filter) throws Exception {
        restOrdFulfillmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordFulfillment.getId().intValue())))
            .andExpect(jsonPath("$.[*].workorderId").value(hasItem(DEFAULT_WORKORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].appointmentId").value(hasItem(DEFAULT_APPOINTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].orderFulfillmentType").value(hasItem(DEFAULT_ORDER_FULFILLMENT_TYPE)))
            .andExpect(jsonPath("$.[*].alternateShippingAddress").value(hasItem(DEFAULT_ALTERNATE_SHIPPING_ADDRESS)))
            .andExpect(jsonPath("$.[*].orderCallAheadNumber").value(hasItem(DEFAULT_ORDER_CALL_AHEAD_NUMBER)))
            .andExpect(jsonPath("$.[*].orderJobComments").value(hasItem(DEFAULT_ORDER_JOB_COMMENTS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restOrdFulfillmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdFulfillmentShouldNotBeFound(String filter) throws Exception {
        restOrdFulfillmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdFulfillmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdFulfillment() throws Exception {
        // Get the ordFulfillment
        restOrdFulfillmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdFulfillment() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();

        // Update the ordFulfillment
        OrdFulfillment updatedOrdFulfillment = ordFulfillmentRepository.findById(ordFulfillment.getId()).get();
        // Disconnect from session so that the updates on updatedOrdFulfillment are not directly saved in db
        em.detach(updatedOrdFulfillment);
        updatedOrdFulfillment
            .workorderId(UPDATED_WORKORDER_ID)
            .appointmentId(UPDATED_APPOINTMENT_ID)
            .orderFulfillmentType(UPDATED_ORDER_FULFILLMENT_TYPE)
            .alternateShippingAddress(UPDATED_ALTERNATE_SHIPPING_ADDRESS)
            .orderCallAheadNumber(UPDATED_ORDER_CALL_AHEAD_NUMBER)
            .orderJobComments(UPDATED_ORDER_JOB_COMMENTS)
            .status(UPDATED_STATUS);
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(updatedOrdFulfillment);

        restOrdFulfillmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordFulfillmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
        OrdFulfillment testOrdFulfillment = ordFulfillmentList.get(ordFulfillmentList.size() - 1);
        assertThat(testOrdFulfillment.getWorkorderId()).isEqualTo(UPDATED_WORKORDER_ID);
        assertThat(testOrdFulfillment.getAppointmentId()).isEqualTo(UPDATED_APPOINTMENT_ID);
        assertThat(testOrdFulfillment.getOrderFulfillmentType()).isEqualTo(UPDATED_ORDER_FULFILLMENT_TYPE);
        assertThat(testOrdFulfillment.getAlternateShippingAddress()).isEqualTo(UPDATED_ALTERNATE_SHIPPING_ADDRESS);
        assertThat(testOrdFulfillment.getOrderCallAheadNumber()).isEqualTo(UPDATED_ORDER_CALL_AHEAD_NUMBER);
        assertThat(testOrdFulfillment.getOrderJobComments()).isEqualTo(UPDATED_ORDER_JOB_COMMENTS);
        assertThat(testOrdFulfillment.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOrdFulfillment() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();
        ordFulfillment.setId(count.incrementAndGet());

        // Create the OrdFulfillment
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdFulfillmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordFulfillmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdFulfillment() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();
        ordFulfillment.setId(count.incrementAndGet());

        // Create the OrdFulfillment
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdFulfillment() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();
        ordFulfillment.setId(count.incrementAndGet());

        // Create the OrdFulfillment
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdFulfillmentWithPatch() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();

        // Update the ordFulfillment using partial update
        OrdFulfillment partialUpdatedOrdFulfillment = new OrdFulfillment();
        partialUpdatedOrdFulfillment.setId(ordFulfillment.getId());

        partialUpdatedOrdFulfillment.orderJobComments(UPDATED_ORDER_JOB_COMMENTS);

        restOrdFulfillmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdFulfillment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdFulfillment))
            )
            .andExpect(status().isOk());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
        OrdFulfillment testOrdFulfillment = ordFulfillmentList.get(ordFulfillmentList.size() - 1);
        assertThat(testOrdFulfillment.getWorkorderId()).isEqualTo(DEFAULT_WORKORDER_ID);
        assertThat(testOrdFulfillment.getAppointmentId()).isEqualTo(DEFAULT_APPOINTMENT_ID);
        assertThat(testOrdFulfillment.getOrderFulfillmentType()).isEqualTo(DEFAULT_ORDER_FULFILLMENT_TYPE);
        assertThat(testOrdFulfillment.getAlternateShippingAddress()).isEqualTo(DEFAULT_ALTERNATE_SHIPPING_ADDRESS);
        assertThat(testOrdFulfillment.getOrderCallAheadNumber()).isEqualTo(DEFAULT_ORDER_CALL_AHEAD_NUMBER);
        assertThat(testOrdFulfillment.getOrderJobComments()).isEqualTo(UPDATED_ORDER_JOB_COMMENTS);
        assertThat(testOrdFulfillment.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOrdFulfillmentWithPatch() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();

        // Update the ordFulfillment using partial update
        OrdFulfillment partialUpdatedOrdFulfillment = new OrdFulfillment();
        partialUpdatedOrdFulfillment.setId(ordFulfillment.getId());

        partialUpdatedOrdFulfillment
            .workorderId(UPDATED_WORKORDER_ID)
            .appointmentId(UPDATED_APPOINTMENT_ID)
            .orderFulfillmentType(UPDATED_ORDER_FULFILLMENT_TYPE)
            .alternateShippingAddress(UPDATED_ALTERNATE_SHIPPING_ADDRESS)
            .orderCallAheadNumber(UPDATED_ORDER_CALL_AHEAD_NUMBER)
            .orderJobComments(UPDATED_ORDER_JOB_COMMENTS)
            .status(UPDATED_STATUS);

        restOrdFulfillmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdFulfillment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdFulfillment))
            )
            .andExpect(status().isOk());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
        OrdFulfillment testOrdFulfillment = ordFulfillmentList.get(ordFulfillmentList.size() - 1);
        assertThat(testOrdFulfillment.getWorkorderId()).isEqualTo(UPDATED_WORKORDER_ID);
        assertThat(testOrdFulfillment.getAppointmentId()).isEqualTo(UPDATED_APPOINTMENT_ID);
        assertThat(testOrdFulfillment.getOrderFulfillmentType()).isEqualTo(UPDATED_ORDER_FULFILLMENT_TYPE);
        assertThat(testOrdFulfillment.getAlternateShippingAddress()).isEqualTo(UPDATED_ALTERNATE_SHIPPING_ADDRESS);
        assertThat(testOrdFulfillment.getOrderCallAheadNumber()).isEqualTo(UPDATED_ORDER_CALL_AHEAD_NUMBER);
        assertThat(testOrdFulfillment.getOrderJobComments()).isEqualTo(UPDATED_ORDER_JOB_COMMENTS);
        assertThat(testOrdFulfillment.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOrdFulfillment() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();
        ordFulfillment.setId(count.incrementAndGet());

        // Create the OrdFulfillment
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdFulfillmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordFulfillmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdFulfillment() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();
        ordFulfillment.setId(count.incrementAndGet());

        // Create the OrdFulfillment
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdFulfillment() throws Exception {
        int databaseSizeBeforeUpdate = ordFulfillmentRepository.findAll().size();
        ordFulfillment.setId(count.incrementAndGet());

        // Create the OrdFulfillment
        OrdFulfillmentDTO ordFulfillmentDTO = ordFulfillmentMapper.toDto(ordFulfillment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdFulfillmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordFulfillmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdFulfillment in the database
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdFulfillment() throws Exception {
        // Initialize the database
        ordFulfillmentRepository.saveAndFlush(ordFulfillment);

        int databaseSizeBeforeDelete = ordFulfillmentRepository.findAll().size();

        // Delete the ordFulfillment
        restOrdFulfillmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordFulfillment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdFulfillment> ordFulfillmentList = ordFulfillmentRepository.findAll();
        assertThat(ordFulfillmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
