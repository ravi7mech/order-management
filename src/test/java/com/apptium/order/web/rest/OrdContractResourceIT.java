package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdContract;
import com.apptium.order.domain.OrdContractCharacteristics;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdContractRepository;
import com.apptium.order.service.criteria.OrdContractCriteria;
import com.apptium.order.service.dto.OrdContractDTO;
import com.apptium.order.service.mapper.OrdContractMapper;
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
 * Integration tests for the {@link OrdContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdContractResourceIT {

    private static final Long DEFAULT_CONTRACT_ID = 1L;
    private static final Long UPDATED_CONTRACT_ID = 2L;
    private static final Long SMALLER_CONTRACT_ID = 1L - 1L;

    private static final Long DEFAULT_LANGUAGE_ID = 1L;
    private static final Long UPDATED_LANGUAGE_ID = 2L;
    private static final Long SMALLER_LANGUAGE_ID = 1L - 1L;

    private static final String DEFAULT_TERM_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TERM_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdContractRepository ordContractRepository;

    @Autowired
    private OrdContractMapper ordContractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdContractMockMvc;

    private OrdContract ordContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdContract createEntity(EntityManager em) {
        OrdContract ordContract = new OrdContract()
            .contractId(DEFAULT_CONTRACT_ID)
            .languageId(DEFAULT_LANGUAGE_ID)
            .termTypeCode(DEFAULT_TERM_TYPE_CODE)
            .action(DEFAULT_ACTION)
            .status(DEFAULT_STATUS);
        return ordContract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdContract createUpdatedEntity(EntityManager em) {
        OrdContract ordContract = new OrdContract()
            .contractId(UPDATED_CONTRACT_ID)
            .languageId(UPDATED_LANGUAGE_ID)
            .termTypeCode(UPDATED_TERM_TYPE_CODE)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS);
        return ordContract;
    }

    @BeforeEach
    public void initTest() {
        ordContract = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdContract() throws Exception {
        int databaseSizeBeforeCreate = ordContractRepository.findAll().size();
        // Create the OrdContract
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);
        restOrdContractMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeCreate + 1);
        OrdContract testOrdContract = ordContractList.get(ordContractList.size() - 1);
        assertThat(testOrdContract.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testOrdContract.getLanguageId()).isEqualTo(DEFAULT_LANGUAGE_ID);
        assertThat(testOrdContract.getTermTypeCode()).isEqualTo(DEFAULT_TERM_TYPE_CODE);
        assertThat(testOrdContract.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testOrdContract.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createOrdContractWithExistingId() throws Exception {
        // Create the OrdContract with an existing ID
        ordContract.setId(1L);
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);

        int databaseSizeBeforeCreate = ordContractRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdContractMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdContracts() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList
        restOrdContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].languageId").value(hasItem(DEFAULT_LANGUAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].termTypeCode").value(hasItem(DEFAULT_TERM_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getOrdContract() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get the ordContract
        restOrdContractMockMvc
            .perform(get(ENTITY_API_URL_ID, ordContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordContract.getId().intValue()))
            .andExpect(jsonPath("$.contractId").value(DEFAULT_CONTRACT_ID.intValue()))
            .andExpect(jsonPath("$.languageId").value(DEFAULT_LANGUAGE_ID.intValue()))
            .andExpect(jsonPath("$.termTypeCode").value(DEFAULT_TERM_TYPE_CODE))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getOrdContractsByIdFiltering() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        Long id = ordContract.getId();

        defaultOrdContractShouldBeFound("id.equals=" + id);
        defaultOrdContractShouldNotBeFound("id.notEquals=" + id);

        defaultOrdContractShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdContractShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdContractShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdContractShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId equals to DEFAULT_CONTRACT_ID
        defaultOrdContractShouldBeFound("contractId.equals=" + DEFAULT_CONTRACT_ID);

        // Get all the ordContractList where contractId equals to UPDATED_CONTRACT_ID
        defaultOrdContractShouldNotBeFound("contractId.equals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId not equals to DEFAULT_CONTRACT_ID
        defaultOrdContractShouldNotBeFound("contractId.notEquals=" + DEFAULT_CONTRACT_ID);

        // Get all the ordContractList where contractId not equals to UPDATED_CONTRACT_ID
        defaultOrdContractShouldBeFound("contractId.notEquals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId in DEFAULT_CONTRACT_ID or UPDATED_CONTRACT_ID
        defaultOrdContractShouldBeFound("contractId.in=" + DEFAULT_CONTRACT_ID + "," + UPDATED_CONTRACT_ID);

        // Get all the ordContractList where contractId equals to UPDATED_CONTRACT_ID
        defaultOrdContractShouldNotBeFound("contractId.in=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId is not null
        defaultOrdContractShouldBeFound("contractId.specified=true");

        // Get all the ordContractList where contractId is null
        defaultOrdContractShouldNotBeFound("contractId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId is greater than or equal to DEFAULT_CONTRACT_ID
        defaultOrdContractShouldBeFound("contractId.greaterThanOrEqual=" + DEFAULT_CONTRACT_ID);

        // Get all the ordContractList where contractId is greater than or equal to UPDATED_CONTRACT_ID
        defaultOrdContractShouldNotBeFound("contractId.greaterThanOrEqual=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId is less than or equal to DEFAULT_CONTRACT_ID
        defaultOrdContractShouldBeFound("contractId.lessThanOrEqual=" + DEFAULT_CONTRACT_ID);

        // Get all the ordContractList where contractId is less than or equal to SMALLER_CONTRACT_ID
        defaultOrdContractShouldNotBeFound("contractId.lessThanOrEqual=" + SMALLER_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId is less than DEFAULT_CONTRACT_ID
        defaultOrdContractShouldNotBeFound("contractId.lessThan=" + DEFAULT_CONTRACT_ID);

        // Get all the ordContractList where contractId is less than UPDATED_CONTRACT_ID
        defaultOrdContractShouldBeFound("contractId.lessThan=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByContractIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where contractId is greater than DEFAULT_CONTRACT_ID
        defaultOrdContractShouldNotBeFound("contractId.greaterThan=" + DEFAULT_CONTRACT_ID);

        // Get all the ordContractList where contractId is greater than SMALLER_CONTRACT_ID
        defaultOrdContractShouldBeFound("contractId.greaterThan=" + SMALLER_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId equals to DEFAULT_LANGUAGE_ID
        defaultOrdContractShouldBeFound("languageId.equals=" + DEFAULT_LANGUAGE_ID);

        // Get all the ordContractList where languageId equals to UPDATED_LANGUAGE_ID
        defaultOrdContractShouldNotBeFound("languageId.equals=" + UPDATED_LANGUAGE_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId not equals to DEFAULT_LANGUAGE_ID
        defaultOrdContractShouldNotBeFound("languageId.notEquals=" + DEFAULT_LANGUAGE_ID);

        // Get all the ordContractList where languageId not equals to UPDATED_LANGUAGE_ID
        defaultOrdContractShouldBeFound("languageId.notEquals=" + UPDATED_LANGUAGE_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId in DEFAULT_LANGUAGE_ID or UPDATED_LANGUAGE_ID
        defaultOrdContractShouldBeFound("languageId.in=" + DEFAULT_LANGUAGE_ID + "," + UPDATED_LANGUAGE_ID);

        // Get all the ordContractList where languageId equals to UPDATED_LANGUAGE_ID
        defaultOrdContractShouldNotBeFound("languageId.in=" + UPDATED_LANGUAGE_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId is not null
        defaultOrdContractShouldBeFound("languageId.specified=true");

        // Get all the ordContractList where languageId is null
        defaultOrdContractShouldNotBeFound("languageId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId is greater than or equal to DEFAULT_LANGUAGE_ID
        defaultOrdContractShouldBeFound("languageId.greaterThanOrEqual=" + DEFAULT_LANGUAGE_ID);

        // Get all the ordContractList where languageId is greater than or equal to UPDATED_LANGUAGE_ID
        defaultOrdContractShouldNotBeFound("languageId.greaterThanOrEqual=" + UPDATED_LANGUAGE_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId is less than or equal to DEFAULT_LANGUAGE_ID
        defaultOrdContractShouldBeFound("languageId.lessThanOrEqual=" + DEFAULT_LANGUAGE_ID);

        // Get all the ordContractList where languageId is less than or equal to SMALLER_LANGUAGE_ID
        defaultOrdContractShouldNotBeFound("languageId.lessThanOrEqual=" + SMALLER_LANGUAGE_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId is less than DEFAULT_LANGUAGE_ID
        defaultOrdContractShouldNotBeFound("languageId.lessThan=" + DEFAULT_LANGUAGE_ID);

        // Get all the ordContractList where languageId is less than UPDATED_LANGUAGE_ID
        defaultOrdContractShouldBeFound("languageId.lessThan=" + UPDATED_LANGUAGE_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByLanguageIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where languageId is greater than DEFAULT_LANGUAGE_ID
        defaultOrdContractShouldNotBeFound("languageId.greaterThan=" + DEFAULT_LANGUAGE_ID);

        // Get all the ordContractList where languageId is greater than SMALLER_LANGUAGE_ID
        defaultOrdContractShouldBeFound("languageId.greaterThan=" + SMALLER_LANGUAGE_ID);
    }

    @Test
    @Transactional
    void getAllOrdContractsByTermTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where termTypeCode equals to DEFAULT_TERM_TYPE_CODE
        defaultOrdContractShouldBeFound("termTypeCode.equals=" + DEFAULT_TERM_TYPE_CODE);

        // Get all the ordContractList where termTypeCode equals to UPDATED_TERM_TYPE_CODE
        defaultOrdContractShouldNotBeFound("termTypeCode.equals=" + UPDATED_TERM_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllOrdContractsByTermTypeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where termTypeCode not equals to DEFAULT_TERM_TYPE_CODE
        defaultOrdContractShouldNotBeFound("termTypeCode.notEquals=" + DEFAULT_TERM_TYPE_CODE);

        // Get all the ordContractList where termTypeCode not equals to UPDATED_TERM_TYPE_CODE
        defaultOrdContractShouldBeFound("termTypeCode.notEquals=" + UPDATED_TERM_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllOrdContractsByTermTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where termTypeCode in DEFAULT_TERM_TYPE_CODE or UPDATED_TERM_TYPE_CODE
        defaultOrdContractShouldBeFound("termTypeCode.in=" + DEFAULT_TERM_TYPE_CODE + "," + UPDATED_TERM_TYPE_CODE);

        // Get all the ordContractList where termTypeCode equals to UPDATED_TERM_TYPE_CODE
        defaultOrdContractShouldNotBeFound("termTypeCode.in=" + UPDATED_TERM_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllOrdContractsByTermTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where termTypeCode is not null
        defaultOrdContractShouldBeFound("termTypeCode.specified=true");

        // Get all the ordContractList where termTypeCode is null
        defaultOrdContractShouldNotBeFound("termTypeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractsByTermTypeCodeContainsSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where termTypeCode contains DEFAULT_TERM_TYPE_CODE
        defaultOrdContractShouldBeFound("termTypeCode.contains=" + DEFAULT_TERM_TYPE_CODE);

        // Get all the ordContractList where termTypeCode contains UPDATED_TERM_TYPE_CODE
        defaultOrdContractShouldNotBeFound("termTypeCode.contains=" + UPDATED_TERM_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllOrdContractsByTermTypeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where termTypeCode does not contain DEFAULT_TERM_TYPE_CODE
        defaultOrdContractShouldNotBeFound("termTypeCode.doesNotContain=" + DEFAULT_TERM_TYPE_CODE);

        // Get all the ordContractList where termTypeCode does not contain UPDATED_TERM_TYPE_CODE
        defaultOrdContractShouldBeFound("termTypeCode.doesNotContain=" + UPDATED_TERM_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllOrdContractsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where action equals to DEFAULT_ACTION
        defaultOrdContractShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the ordContractList where action equals to UPDATED_ACTION
        defaultOrdContractShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdContractsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where action not equals to DEFAULT_ACTION
        defaultOrdContractShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the ordContractList where action not equals to UPDATED_ACTION
        defaultOrdContractShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdContractsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultOrdContractShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the ordContractList where action equals to UPDATED_ACTION
        defaultOrdContractShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdContractsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where action is not null
        defaultOrdContractShouldBeFound("action.specified=true");

        // Get all the ordContractList where action is null
        defaultOrdContractShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractsByActionContainsSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where action contains DEFAULT_ACTION
        defaultOrdContractShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the ordContractList where action contains UPDATED_ACTION
        defaultOrdContractShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdContractsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where action does not contain DEFAULT_ACTION
        defaultOrdContractShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the ordContractList where action does not contain UPDATED_ACTION
        defaultOrdContractShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdContractsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where status equals to DEFAULT_STATUS
        defaultOrdContractShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ordContractList where status equals to UPDATED_STATUS
        defaultOrdContractShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdContractsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where status not equals to DEFAULT_STATUS
        defaultOrdContractShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ordContractList where status not equals to UPDATED_STATUS
        defaultOrdContractShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdContractsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrdContractShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ordContractList where status equals to UPDATED_STATUS
        defaultOrdContractShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdContractsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where status is not null
        defaultOrdContractShouldBeFound("status.specified=true");

        // Get all the ordContractList where status is null
        defaultOrdContractShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdContractsByStatusContainsSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where status contains DEFAULT_STATUS
        defaultOrdContractShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the ordContractList where status contains UPDATED_STATUS
        defaultOrdContractShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdContractsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        // Get all the ordContractList where status does not contain DEFAULT_STATUS
        defaultOrdContractShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the ordContractList where status does not contain UPDATED_STATUS
        defaultOrdContractShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrdContractsByOrdContractCharacteristicsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);
        OrdContractCharacteristics ordContractCharacteristics = OrdContractCharacteristicsResourceIT.createEntity(em);
        em.persist(ordContractCharacteristics);
        em.flush();
        ordContract.addOrdContractCharacteristics(ordContractCharacteristics);
        ordContractRepository.saveAndFlush(ordContract);
        Long ordContractCharacteristicsId = ordContractCharacteristics.getId();

        // Get all the ordContractList where ordContractCharacteristics equals to ordContractCharacteristicsId
        defaultOrdContractShouldBeFound("ordContractCharacteristicsId.equals=" + ordContractCharacteristicsId);

        // Get all the ordContractList where ordContractCharacteristics equals to (ordContractCharacteristicsId + 1)
        defaultOrdContractShouldNotBeFound("ordContractCharacteristicsId.equals=" + (ordContractCharacteristicsId + 1));
    }

    @Test
    @Transactional
    void getAllOrdContractsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordContract.setOrdProductOrder(ordProductOrder);
        ordContractRepository.saveAndFlush(ordContract);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordContractList where ordProductOrder equals to ordProductOrderId
        defaultOrdContractShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordContractList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdContractShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdContractShouldBeFound(String filter) throws Exception {
        restOrdContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].languageId").value(hasItem(DEFAULT_LANGUAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].termTypeCode").value(hasItem(DEFAULT_TERM_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restOrdContractMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdContractShouldNotBeFound(String filter) throws Exception {
        restOrdContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdContractMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdContract() throws Exception {
        // Get the ordContract
        restOrdContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdContract() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();

        // Update the ordContract
        OrdContract updatedOrdContract = ordContractRepository.findById(ordContract.getId()).get();
        // Disconnect from session so that the updates on updatedOrdContract are not directly saved in db
        em.detach(updatedOrdContract);
        updatedOrdContract
            .contractId(UPDATED_CONTRACT_ID)
            .languageId(UPDATED_LANGUAGE_ID)
            .termTypeCode(UPDATED_TERM_TYPE_CODE)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS);
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(updatedOrdContract);

        restOrdContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordContractDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
        OrdContract testOrdContract = ordContractList.get(ordContractList.size() - 1);
        assertThat(testOrdContract.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testOrdContract.getLanguageId()).isEqualTo(UPDATED_LANGUAGE_ID);
        assertThat(testOrdContract.getTermTypeCode()).isEqualTo(UPDATED_TERM_TYPE_CODE);
        assertThat(testOrdContract.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testOrdContract.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOrdContract() throws Exception {
        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();
        ordContract.setId(count.incrementAndGet());

        // Create the OrdContract
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordContractDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdContract() throws Exception {
        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();
        ordContract.setId(count.incrementAndGet());

        // Create the OrdContract
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdContract() throws Exception {
        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();
        ordContract.setId(count.incrementAndGet());

        // Create the OrdContract
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordContractDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdContractWithPatch() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();

        // Update the ordContract using partial update
        OrdContract partialUpdatedOrdContract = new OrdContract();
        partialUpdatedOrdContract.setId(ordContract.getId());

        partialUpdatedOrdContract.contractId(UPDATED_CONTRACT_ID).status(UPDATED_STATUS);

        restOrdContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdContract))
            )
            .andExpect(status().isOk());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
        OrdContract testOrdContract = ordContractList.get(ordContractList.size() - 1);
        assertThat(testOrdContract.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testOrdContract.getLanguageId()).isEqualTo(DEFAULT_LANGUAGE_ID);
        assertThat(testOrdContract.getTermTypeCode()).isEqualTo(DEFAULT_TERM_TYPE_CODE);
        assertThat(testOrdContract.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testOrdContract.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOrdContractWithPatch() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();

        // Update the ordContract using partial update
        OrdContract partialUpdatedOrdContract = new OrdContract();
        partialUpdatedOrdContract.setId(ordContract.getId());

        partialUpdatedOrdContract
            .contractId(UPDATED_CONTRACT_ID)
            .languageId(UPDATED_LANGUAGE_ID)
            .termTypeCode(UPDATED_TERM_TYPE_CODE)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS);

        restOrdContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdContract))
            )
            .andExpect(status().isOk());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
        OrdContract testOrdContract = ordContractList.get(ordContractList.size() - 1);
        assertThat(testOrdContract.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testOrdContract.getLanguageId()).isEqualTo(UPDATED_LANGUAGE_ID);
        assertThat(testOrdContract.getTermTypeCode()).isEqualTo(UPDATED_TERM_TYPE_CODE);
        assertThat(testOrdContract.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testOrdContract.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOrdContract() throws Exception {
        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();
        ordContract.setId(count.incrementAndGet());

        // Create the OrdContract
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordContractDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdContract() throws Exception {
        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();
        ordContract.setId(count.incrementAndGet());

        // Create the OrdContract
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdContract() throws Exception {
        int databaseSizeBeforeUpdate = ordContractRepository.findAll().size();
        ordContract.setId(count.incrementAndGet());

        // Create the OrdContract
        OrdContractDTO ordContractDTO = ordContractMapper.toDto(ordContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdContractMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordContractDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdContract in the database
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdContract() throws Exception {
        // Initialize the database
        ordContractRepository.saveAndFlush(ordContract);

        int databaseSizeBeforeDelete = ordContractRepository.findAll().size();

        // Delete the ordContract
        restOrdContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordContract.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdContract> ordContractList = ordContractRepository.findAll();
        assertThat(ordContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
