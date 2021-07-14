package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdContactDetails;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdContactDetailsRepository;
import com.apptium.order.service.criteria.OrdContactDetailsCriteria;
import com.apptium.order.service.dto.OrdContactDetailsDTO;
import com.apptium.order.service.mapper.OrdContactDetailsMapper;
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
 * Integration tests for the {@link OrdContactDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdContactDetailsResourceIT {

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-contact-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdContactDetailsRepository ordContactDetailsRepository;

    @Autowired
    private OrdContactDetailsMapper ordContactDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdContactDetailsMockMvc;

    private OrdContactDetails ordContactDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdContactDetails createEntity(EntityManager em) {
        OrdContactDetails ordContactDetails = new OrdContactDetails()
            .contactName(DEFAULT_CONTACT_NAME)
            .contactPhoneNumber(DEFAULT_CONTACT_PHONE_NUMBER)
            .contactEmailId(DEFAULT_CONTACT_EMAIL_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return ordContactDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdContactDetails createUpdatedEntity(EntityManager em) {
        OrdContactDetails ordContactDetails = new OrdContactDetails()
            .contactName(UPDATED_CONTACT_NAME)
            .contactPhoneNumber(UPDATED_CONTACT_PHONE_NUMBER)
            .contactEmailId(UPDATED_CONTACT_EMAIL_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return ordContactDetails;
    }

    @BeforeEach
    public void initTest() {
        ordContactDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdContactDetails() throws Exception {
        int databaseSizeBeforeCreate = ordContactDetailsRepository.findAll().size();
        // Create the OrdContactDetails
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);
        restOrdContactDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        OrdContactDetails testOrdContactDetails = ordContactDetailsList.get(ordContactDetailsList.size() - 1);
        assertThat(testOrdContactDetails.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testOrdContactDetails.getContactPhoneNumber()).isEqualTo(DEFAULT_CONTACT_PHONE_NUMBER);
        assertThat(testOrdContactDetails.getContactEmailId()).isEqualTo(DEFAULT_CONTACT_EMAIL_ID);
        assertThat(testOrdContactDetails.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOrdContactDetails.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void createOrdContactDetailsWithExistingId() throws Exception {
        // Create the OrdContactDetails with an existing ID
        ordContactDetails.setId(1L);
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);

        int databaseSizeBeforeCreate = ordContactDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdContactDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdContactDetails() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList
        restOrdContactDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordContactDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].contactEmailId").value(hasItem(DEFAULT_CONTACT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }

    @Test
    @Transactional
    void getOrdContactDetails() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get the ordContactDetails
        restOrdContactDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, ordContactDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordContactDetails.getId().intValue()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.contactPhoneNumber").value(DEFAULT_CONTACT_PHONE_NUMBER))
            .andExpect(jsonPath("$.contactEmailId").value(DEFAULT_CONTACT_EMAIL_ID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    @Transactional
    void getOrdContactDetailsByIdFiltering() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        Long id = ordContactDetails.getId();

        defaultOrdContactDetailsShouldBeFound("id.equals=" + id);
        defaultOrdContactDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultOrdContactDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdContactDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdContactDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdContactDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactName equals to DEFAULT_CONTACT_NAME
        defaultOrdContactDetailsShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the ordContactDetailsList where contactName equals to UPDATED_CONTACT_NAME
        defaultOrdContactDetailsShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactName not equals to DEFAULT_CONTACT_NAME
        defaultOrdContactDetailsShouldNotBeFound("contactName.notEquals=" + DEFAULT_CONTACT_NAME);

        // Get all the ordContactDetailsList where contactName not equals to UPDATED_CONTACT_NAME
        defaultOrdContactDetailsShouldBeFound("contactName.notEquals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultOrdContactDetailsShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the ordContactDetailsList where contactName equals to UPDATED_CONTACT_NAME
        defaultOrdContactDetailsShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactName is not null
        defaultOrdContactDetailsShouldBeFound("contactName.specified=true");

        // Get all the ordContactDetailsList where contactName is null
        defaultOrdContactDetailsShouldNotBeFound("contactName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactNameContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactName contains DEFAULT_CONTACT_NAME
        defaultOrdContactDetailsShouldBeFound("contactName.contains=" + DEFAULT_CONTACT_NAME);

        // Get all the ordContactDetailsList where contactName contains UPDATED_CONTACT_NAME
        defaultOrdContactDetailsShouldNotBeFound("contactName.contains=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactName does not contain DEFAULT_CONTACT_NAME
        defaultOrdContactDetailsShouldNotBeFound("contactName.doesNotContain=" + DEFAULT_CONTACT_NAME);

        // Get all the ordContactDetailsList where contactName does not contain UPDATED_CONTACT_NAME
        defaultOrdContactDetailsShouldBeFound("contactName.doesNotContain=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactPhoneNumber equals to DEFAULT_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldBeFound("contactPhoneNumber.equals=" + DEFAULT_CONTACT_PHONE_NUMBER);

        // Get all the ordContactDetailsList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldNotBeFound("contactPhoneNumber.equals=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactPhoneNumber not equals to DEFAULT_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldNotBeFound("contactPhoneNumber.notEquals=" + DEFAULT_CONTACT_PHONE_NUMBER);

        // Get all the ordContactDetailsList where contactPhoneNumber not equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldBeFound("contactPhoneNumber.notEquals=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactPhoneNumber in DEFAULT_CONTACT_PHONE_NUMBER or UPDATED_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldBeFound("contactPhoneNumber.in=" + DEFAULT_CONTACT_PHONE_NUMBER + "," + UPDATED_CONTACT_PHONE_NUMBER);

        // Get all the ordContactDetailsList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldNotBeFound("contactPhoneNumber.in=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactPhoneNumber is not null
        defaultOrdContactDetailsShouldBeFound("contactPhoneNumber.specified=true");

        // Get all the ordContactDetailsList where contactPhoneNumber is null
        defaultOrdContactDetailsShouldNotBeFound("contactPhoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactPhoneNumber contains DEFAULT_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldBeFound("contactPhoneNumber.contains=" + DEFAULT_CONTACT_PHONE_NUMBER);

        // Get all the ordContactDetailsList where contactPhoneNumber contains UPDATED_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldNotBeFound("contactPhoneNumber.contains=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactPhoneNumber does not contain DEFAULT_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldNotBeFound("contactPhoneNumber.doesNotContain=" + DEFAULT_CONTACT_PHONE_NUMBER);

        // Get all the ordContactDetailsList where contactPhoneNumber does not contain UPDATED_CONTACT_PHONE_NUMBER
        defaultOrdContactDetailsShouldBeFound("contactPhoneNumber.doesNotContain=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactEmailIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactEmailId equals to DEFAULT_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldBeFound("contactEmailId.equals=" + DEFAULT_CONTACT_EMAIL_ID);

        // Get all the ordContactDetailsList where contactEmailId equals to UPDATED_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldNotBeFound("contactEmailId.equals=" + UPDATED_CONTACT_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactEmailIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactEmailId not equals to DEFAULT_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldNotBeFound("contactEmailId.notEquals=" + DEFAULT_CONTACT_EMAIL_ID);

        // Get all the ordContactDetailsList where contactEmailId not equals to UPDATED_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldBeFound("contactEmailId.notEquals=" + UPDATED_CONTACT_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactEmailIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactEmailId in DEFAULT_CONTACT_EMAIL_ID or UPDATED_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldBeFound("contactEmailId.in=" + DEFAULT_CONTACT_EMAIL_ID + "," + UPDATED_CONTACT_EMAIL_ID);

        // Get all the ordContactDetailsList where contactEmailId equals to UPDATED_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldNotBeFound("contactEmailId.in=" + UPDATED_CONTACT_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactEmailIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactEmailId is not null
        defaultOrdContactDetailsShouldBeFound("contactEmailId.specified=true");

        // Get all the ordContactDetailsList where contactEmailId is null
        defaultOrdContactDetailsShouldNotBeFound("contactEmailId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactEmailIdContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactEmailId contains DEFAULT_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldBeFound("contactEmailId.contains=" + DEFAULT_CONTACT_EMAIL_ID);

        // Get all the ordContactDetailsList where contactEmailId contains UPDATED_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldNotBeFound("contactEmailId.contains=" + UPDATED_CONTACT_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByContactEmailIdNotContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where contactEmailId does not contain DEFAULT_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldNotBeFound("contactEmailId.doesNotContain=" + DEFAULT_CONTACT_EMAIL_ID);

        // Get all the ordContactDetailsList where contactEmailId does not contain UPDATED_CONTACT_EMAIL_ID
        defaultOrdContactDetailsShouldBeFound("contactEmailId.doesNotContain=" + UPDATED_CONTACT_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where firstName equals to DEFAULT_FIRST_NAME
        defaultOrdContactDetailsShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the ordContactDetailsList where firstName equals to UPDATED_FIRST_NAME
        defaultOrdContactDetailsShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where firstName not equals to DEFAULT_FIRST_NAME
        defaultOrdContactDetailsShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the ordContactDetailsList where firstName not equals to UPDATED_FIRST_NAME
        defaultOrdContactDetailsShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultOrdContactDetailsShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the ordContactDetailsList where firstName equals to UPDATED_FIRST_NAME
        defaultOrdContactDetailsShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where firstName is not null
        defaultOrdContactDetailsShouldBeFound("firstName.specified=true");

        // Get all the ordContactDetailsList where firstName is null
        defaultOrdContactDetailsShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where firstName contains DEFAULT_FIRST_NAME
        defaultOrdContactDetailsShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the ordContactDetailsList where firstName contains UPDATED_FIRST_NAME
        defaultOrdContactDetailsShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where firstName does not contain DEFAULT_FIRST_NAME
        defaultOrdContactDetailsShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the ordContactDetailsList where firstName does not contain UPDATED_FIRST_NAME
        defaultOrdContactDetailsShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where lastName equals to DEFAULT_LAST_NAME
        defaultOrdContactDetailsShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the ordContactDetailsList where lastName equals to UPDATED_LAST_NAME
        defaultOrdContactDetailsShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where lastName not equals to DEFAULT_LAST_NAME
        defaultOrdContactDetailsShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the ordContactDetailsList where lastName not equals to UPDATED_LAST_NAME
        defaultOrdContactDetailsShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultOrdContactDetailsShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the ordContactDetailsList where lastName equals to UPDATED_LAST_NAME
        defaultOrdContactDetailsShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where lastName is not null
        defaultOrdContactDetailsShouldBeFound("lastName.specified=true");

        // Get all the ordContactDetailsList where lastName is null
        defaultOrdContactDetailsShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where lastName contains DEFAULT_LAST_NAME
        defaultOrdContactDetailsShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the ordContactDetailsList where lastName contains UPDATED_LAST_NAME
        defaultOrdContactDetailsShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        // Get all the ordContactDetailsList where lastName does not contain DEFAULT_LAST_NAME
        defaultOrdContactDetailsShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the ordContactDetailsList where lastName does not contain UPDATED_LAST_NAME
        defaultOrdContactDetailsShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllOrdContactDetailsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordContactDetails.setOrdProductOrder(ordProductOrder);
        ordProductOrder.setOrdContactDetails(ordContactDetails);
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordContactDetailsList where ordProductOrder equals to ordProductOrderId
        defaultOrdContactDetailsShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordContactDetailsList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdContactDetailsShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdContactDetailsShouldBeFound(String filter) throws Exception {
        restOrdContactDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordContactDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].contactEmailId").value(hasItem(DEFAULT_CONTACT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));

        // Check, that the count call also returns 1
        restOrdContactDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdContactDetailsShouldNotBeFound(String filter) throws Exception {
        restOrdContactDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdContactDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdContactDetails() throws Exception {
        // Get the ordContactDetails
        restOrdContactDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdContactDetails() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();

        // Update the ordContactDetails
        OrdContactDetails updatedOrdContactDetails = ordContactDetailsRepository.findById(ordContactDetails.getId()).get();
        // Disconnect from session so that the updates on updatedOrdContactDetails are not directly saved in db
        em.detach(updatedOrdContactDetails);
        updatedOrdContactDetails
            .contactName(UPDATED_CONTACT_NAME)
            .contactPhoneNumber(UPDATED_CONTACT_PHONE_NUMBER)
            .contactEmailId(UPDATED_CONTACT_EMAIL_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(updatedOrdContactDetails);

        restOrdContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordContactDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrdContactDetails testOrdContactDetails = ordContactDetailsList.get(ordContactDetailsList.size() - 1);
        assertThat(testOrdContactDetails.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testOrdContactDetails.getContactPhoneNumber()).isEqualTo(UPDATED_CONTACT_PHONE_NUMBER);
        assertThat(testOrdContactDetails.getContactEmailId()).isEqualTo(UPDATED_CONTACT_EMAIL_ID);
        assertThat(testOrdContactDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOrdContactDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOrdContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();
        ordContactDetails.setId(count.incrementAndGet());

        // Create the OrdContactDetails
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordContactDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();
        ordContactDetails.setId(count.incrementAndGet());

        // Create the OrdContactDetails
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();
        ordContactDetails.setId(count.incrementAndGet());

        // Create the OrdContactDetails
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdContactDetailsWithPatch() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();

        // Update the ordContactDetails using partial update
        OrdContactDetails partialUpdatedOrdContactDetails = new OrdContactDetails();
        partialUpdatedOrdContactDetails.setId(ordContactDetails.getId());

        partialUpdatedOrdContactDetails.firstName(UPDATED_FIRST_NAME);

        restOrdContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdContactDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdContactDetails))
            )
            .andExpect(status().isOk());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrdContactDetails testOrdContactDetails = ordContactDetailsList.get(ordContactDetailsList.size() - 1);
        assertThat(testOrdContactDetails.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testOrdContactDetails.getContactPhoneNumber()).isEqualTo(DEFAULT_CONTACT_PHONE_NUMBER);
        assertThat(testOrdContactDetails.getContactEmailId()).isEqualTo(DEFAULT_CONTACT_EMAIL_ID);
        assertThat(testOrdContactDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOrdContactDetails.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOrdContactDetailsWithPatch() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();

        // Update the ordContactDetails using partial update
        OrdContactDetails partialUpdatedOrdContactDetails = new OrdContactDetails();
        partialUpdatedOrdContactDetails.setId(ordContactDetails.getId());

        partialUpdatedOrdContactDetails
            .contactName(UPDATED_CONTACT_NAME)
            .contactPhoneNumber(UPDATED_CONTACT_PHONE_NUMBER)
            .contactEmailId(UPDATED_CONTACT_EMAIL_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);

        restOrdContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdContactDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdContactDetails))
            )
            .andExpect(status().isOk());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrdContactDetails testOrdContactDetails = ordContactDetailsList.get(ordContactDetailsList.size() - 1);
        assertThat(testOrdContactDetails.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testOrdContactDetails.getContactPhoneNumber()).isEqualTo(UPDATED_CONTACT_PHONE_NUMBER);
        assertThat(testOrdContactDetails.getContactEmailId()).isEqualTo(UPDATED_CONTACT_EMAIL_ID);
        assertThat(testOrdContactDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOrdContactDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOrdContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();
        ordContactDetails.setId(count.incrementAndGet());

        // Create the OrdContactDetails
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordContactDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();
        ordContactDetails.setId(count.incrementAndGet());

        // Create the OrdContactDetails
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = ordContactDetailsRepository.findAll().size();
        ordContactDetails.setId(count.incrementAndGet());

        // Create the OrdContactDetails
        OrdContactDetailsDTO ordContactDetailsDTO = ordContactDetailsMapper.toDto(ordContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContactDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdContactDetails in the database
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdContactDetails() throws Exception {
        // Initialize the database
        ordContactDetailsRepository.saveAndFlush(ordContactDetails);

        int databaseSizeBeforeDelete = ordContactDetailsRepository.findAll().size();

        // Delete the ordContactDetails
        restOrdContactDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordContactDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdContactDetails> ordContactDetailsList = ordContactDetailsRepository.findAll();
        assertThat(ordContactDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
