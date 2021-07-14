package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdAcquisition;
import com.apptium.order.domain.OrdAcquisitionChar;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdAcquisitionRepository;
import com.apptium.order.service.criteria.OrdAcquisitionCriteria;
import com.apptium.order.service.dto.OrdAcquisitionDTO;
import com.apptium.order.service.mapper.OrdAcquisitionMapper;
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
 * Integration tests for the {@link OrdAcquisitionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdAcquisitionResourceIT {

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_AFFILIATE = "AAAAAAAAAA";
    private static final String UPDATED_AFFILIATE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER = "BBBBBBBBBB";

    private static final String DEFAULT_ACQUISITION_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_ACQUISITION_AGENT = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-acquisitions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdAcquisitionRepository ordAcquisitionRepository;

    @Autowired
    private OrdAcquisitionMapper ordAcquisitionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdAcquisitionMockMvc;

    private OrdAcquisition ordAcquisition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdAcquisition createEntity(EntityManager em) {
        OrdAcquisition ordAcquisition = new OrdAcquisition()
            .channel(DEFAULT_CHANNEL)
            .affiliate(DEFAULT_AFFILIATE)
            .partner(DEFAULT_PARTNER)
            .acquisitionAgent(DEFAULT_ACQUISITION_AGENT)
            .action(DEFAULT_ACTION);
        return ordAcquisition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdAcquisition createUpdatedEntity(EntityManager em) {
        OrdAcquisition ordAcquisition = new OrdAcquisition()
            .channel(UPDATED_CHANNEL)
            .affiliate(UPDATED_AFFILIATE)
            .partner(UPDATED_PARTNER)
            .acquisitionAgent(UPDATED_ACQUISITION_AGENT)
            .action(UPDATED_ACTION);
        return ordAcquisition;
    }

    @BeforeEach
    public void initTest() {
        ordAcquisition = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdAcquisition() throws Exception {
        int databaseSizeBeforeCreate = ordAcquisitionRepository.findAll().size();
        // Create the OrdAcquisition
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);
        restOrdAcquisitionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeCreate + 1);
        OrdAcquisition testOrdAcquisition = ordAcquisitionList.get(ordAcquisitionList.size() - 1);
        assertThat(testOrdAcquisition.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testOrdAcquisition.getAffiliate()).isEqualTo(DEFAULT_AFFILIATE);
        assertThat(testOrdAcquisition.getPartner()).isEqualTo(DEFAULT_PARTNER);
        assertThat(testOrdAcquisition.getAcquisitionAgent()).isEqualTo(DEFAULT_ACQUISITION_AGENT);
        assertThat(testOrdAcquisition.getAction()).isEqualTo(DEFAULT_ACTION);
    }

    @Test
    @Transactional
    void createOrdAcquisitionWithExistingId() throws Exception {
        // Create the OrdAcquisition with an existing ID
        ordAcquisition.setId(1L);
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);

        int databaseSizeBeforeCreate = ordAcquisitionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdAcquisitionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitions() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList
        restOrdAcquisitionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordAcquisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].affiliate").value(hasItem(DEFAULT_AFFILIATE)))
            .andExpect(jsonPath("$.[*].partner").value(hasItem(DEFAULT_PARTNER)))
            .andExpect(jsonPath("$.[*].acquisitionAgent").value(hasItem(DEFAULT_ACQUISITION_AGENT)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)));
    }

    @Test
    @Transactional
    void getOrdAcquisition() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get the ordAcquisition
        restOrdAcquisitionMockMvc
            .perform(get(ENTITY_API_URL_ID, ordAcquisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordAcquisition.getId().intValue()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.affiliate").value(DEFAULT_AFFILIATE))
            .andExpect(jsonPath("$.partner").value(DEFAULT_PARTNER))
            .andExpect(jsonPath("$.acquisitionAgent").value(DEFAULT_ACQUISITION_AGENT))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION));
    }

    @Test
    @Transactional
    void getOrdAcquisitionsByIdFiltering() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        Long id = ordAcquisition.getId();

        defaultOrdAcquisitionShouldBeFound("id.equals=" + id);
        defaultOrdAcquisitionShouldNotBeFound("id.notEquals=" + id);

        defaultOrdAcquisitionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdAcquisitionShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdAcquisitionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdAcquisitionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where channel equals to DEFAULT_CHANNEL
        defaultOrdAcquisitionShouldBeFound("channel.equals=" + DEFAULT_CHANNEL);

        // Get all the ordAcquisitionList where channel equals to UPDATED_CHANNEL
        defaultOrdAcquisitionShouldNotBeFound("channel.equals=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByChannelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where channel not equals to DEFAULT_CHANNEL
        defaultOrdAcquisitionShouldNotBeFound("channel.notEquals=" + DEFAULT_CHANNEL);

        // Get all the ordAcquisitionList where channel not equals to UPDATED_CHANNEL
        defaultOrdAcquisitionShouldBeFound("channel.notEquals=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByChannelIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where channel in DEFAULT_CHANNEL or UPDATED_CHANNEL
        defaultOrdAcquisitionShouldBeFound("channel.in=" + DEFAULT_CHANNEL + "," + UPDATED_CHANNEL);

        // Get all the ordAcquisitionList where channel equals to UPDATED_CHANNEL
        defaultOrdAcquisitionShouldNotBeFound("channel.in=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByChannelIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where channel is not null
        defaultOrdAcquisitionShouldBeFound("channel.specified=true");

        // Get all the ordAcquisitionList where channel is null
        defaultOrdAcquisitionShouldNotBeFound("channel.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByChannelContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where channel contains DEFAULT_CHANNEL
        defaultOrdAcquisitionShouldBeFound("channel.contains=" + DEFAULT_CHANNEL);

        // Get all the ordAcquisitionList where channel contains UPDATED_CHANNEL
        defaultOrdAcquisitionShouldNotBeFound("channel.contains=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByChannelNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where channel does not contain DEFAULT_CHANNEL
        defaultOrdAcquisitionShouldNotBeFound("channel.doesNotContain=" + DEFAULT_CHANNEL);

        // Get all the ordAcquisitionList where channel does not contain UPDATED_CHANNEL
        defaultOrdAcquisitionShouldBeFound("channel.doesNotContain=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAffiliateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where affiliate equals to DEFAULT_AFFILIATE
        defaultOrdAcquisitionShouldBeFound("affiliate.equals=" + DEFAULT_AFFILIATE);

        // Get all the ordAcquisitionList where affiliate equals to UPDATED_AFFILIATE
        defaultOrdAcquisitionShouldNotBeFound("affiliate.equals=" + UPDATED_AFFILIATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAffiliateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where affiliate not equals to DEFAULT_AFFILIATE
        defaultOrdAcquisitionShouldNotBeFound("affiliate.notEquals=" + DEFAULT_AFFILIATE);

        // Get all the ordAcquisitionList where affiliate not equals to UPDATED_AFFILIATE
        defaultOrdAcquisitionShouldBeFound("affiliate.notEquals=" + UPDATED_AFFILIATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAffiliateIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where affiliate in DEFAULT_AFFILIATE or UPDATED_AFFILIATE
        defaultOrdAcquisitionShouldBeFound("affiliate.in=" + DEFAULT_AFFILIATE + "," + UPDATED_AFFILIATE);

        // Get all the ordAcquisitionList where affiliate equals to UPDATED_AFFILIATE
        defaultOrdAcquisitionShouldNotBeFound("affiliate.in=" + UPDATED_AFFILIATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAffiliateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where affiliate is not null
        defaultOrdAcquisitionShouldBeFound("affiliate.specified=true");

        // Get all the ordAcquisitionList where affiliate is null
        defaultOrdAcquisitionShouldNotBeFound("affiliate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAffiliateContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where affiliate contains DEFAULT_AFFILIATE
        defaultOrdAcquisitionShouldBeFound("affiliate.contains=" + DEFAULT_AFFILIATE);

        // Get all the ordAcquisitionList where affiliate contains UPDATED_AFFILIATE
        defaultOrdAcquisitionShouldNotBeFound("affiliate.contains=" + UPDATED_AFFILIATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAffiliateNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where affiliate does not contain DEFAULT_AFFILIATE
        defaultOrdAcquisitionShouldNotBeFound("affiliate.doesNotContain=" + DEFAULT_AFFILIATE);

        // Get all the ordAcquisitionList where affiliate does not contain UPDATED_AFFILIATE
        defaultOrdAcquisitionShouldBeFound("affiliate.doesNotContain=" + UPDATED_AFFILIATE);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByPartnerIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where partner equals to DEFAULT_PARTNER
        defaultOrdAcquisitionShouldBeFound("partner.equals=" + DEFAULT_PARTNER);

        // Get all the ordAcquisitionList where partner equals to UPDATED_PARTNER
        defaultOrdAcquisitionShouldNotBeFound("partner.equals=" + UPDATED_PARTNER);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByPartnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where partner not equals to DEFAULT_PARTNER
        defaultOrdAcquisitionShouldNotBeFound("partner.notEquals=" + DEFAULT_PARTNER);

        // Get all the ordAcquisitionList where partner not equals to UPDATED_PARTNER
        defaultOrdAcquisitionShouldBeFound("partner.notEquals=" + UPDATED_PARTNER);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByPartnerIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where partner in DEFAULT_PARTNER or UPDATED_PARTNER
        defaultOrdAcquisitionShouldBeFound("partner.in=" + DEFAULT_PARTNER + "," + UPDATED_PARTNER);

        // Get all the ordAcquisitionList where partner equals to UPDATED_PARTNER
        defaultOrdAcquisitionShouldNotBeFound("partner.in=" + UPDATED_PARTNER);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByPartnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where partner is not null
        defaultOrdAcquisitionShouldBeFound("partner.specified=true");

        // Get all the ordAcquisitionList where partner is null
        defaultOrdAcquisitionShouldNotBeFound("partner.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByPartnerContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where partner contains DEFAULT_PARTNER
        defaultOrdAcquisitionShouldBeFound("partner.contains=" + DEFAULT_PARTNER);

        // Get all the ordAcquisitionList where partner contains UPDATED_PARTNER
        defaultOrdAcquisitionShouldNotBeFound("partner.contains=" + UPDATED_PARTNER);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByPartnerNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where partner does not contain DEFAULT_PARTNER
        defaultOrdAcquisitionShouldNotBeFound("partner.doesNotContain=" + DEFAULT_PARTNER);

        // Get all the ordAcquisitionList where partner does not contain UPDATED_PARTNER
        defaultOrdAcquisitionShouldBeFound("partner.doesNotContain=" + UPDATED_PARTNER);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAcquisitionAgentIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where acquisitionAgent equals to DEFAULT_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldBeFound("acquisitionAgent.equals=" + DEFAULT_ACQUISITION_AGENT);

        // Get all the ordAcquisitionList where acquisitionAgent equals to UPDATED_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldNotBeFound("acquisitionAgent.equals=" + UPDATED_ACQUISITION_AGENT);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAcquisitionAgentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where acquisitionAgent not equals to DEFAULT_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldNotBeFound("acquisitionAgent.notEquals=" + DEFAULT_ACQUISITION_AGENT);

        // Get all the ordAcquisitionList where acquisitionAgent not equals to UPDATED_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldBeFound("acquisitionAgent.notEquals=" + UPDATED_ACQUISITION_AGENT);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAcquisitionAgentIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where acquisitionAgent in DEFAULT_ACQUISITION_AGENT or UPDATED_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldBeFound("acquisitionAgent.in=" + DEFAULT_ACQUISITION_AGENT + "," + UPDATED_ACQUISITION_AGENT);

        // Get all the ordAcquisitionList where acquisitionAgent equals to UPDATED_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldNotBeFound("acquisitionAgent.in=" + UPDATED_ACQUISITION_AGENT);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAcquisitionAgentIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where acquisitionAgent is not null
        defaultOrdAcquisitionShouldBeFound("acquisitionAgent.specified=true");

        // Get all the ordAcquisitionList where acquisitionAgent is null
        defaultOrdAcquisitionShouldNotBeFound("acquisitionAgent.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAcquisitionAgentContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where acquisitionAgent contains DEFAULT_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldBeFound("acquisitionAgent.contains=" + DEFAULT_ACQUISITION_AGENT);

        // Get all the ordAcquisitionList where acquisitionAgent contains UPDATED_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldNotBeFound("acquisitionAgent.contains=" + UPDATED_ACQUISITION_AGENT);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByAcquisitionAgentNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where acquisitionAgent does not contain DEFAULT_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldNotBeFound("acquisitionAgent.doesNotContain=" + DEFAULT_ACQUISITION_AGENT);

        // Get all the ordAcquisitionList where acquisitionAgent does not contain UPDATED_ACQUISITION_AGENT
        defaultOrdAcquisitionShouldBeFound("acquisitionAgent.doesNotContain=" + UPDATED_ACQUISITION_AGENT);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where action equals to DEFAULT_ACTION
        defaultOrdAcquisitionShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the ordAcquisitionList where action equals to UPDATED_ACTION
        defaultOrdAcquisitionShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where action not equals to DEFAULT_ACTION
        defaultOrdAcquisitionShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the ordAcquisitionList where action not equals to UPDATED_ACTION
        defaultOrdAcquisitionShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultOrdAcquisitionShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the ordAcquisitionList where action equals to UPDATED_ACTION
        defaultOrdAcquisitionShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where action is not null
        defaultOrdAcquisitionShouldBeFound("action.specified=true");

        // Get all the ordAcquisitionList where action is null
        defaultOrdAcquisitionShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByActionContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where action contains DEFAULT_ACTION
        defaultOrdAcquisitionShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the ordAcquisitionList where action contains UPDATED_ACTION
        defaultOrdAcquisitionShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        // Get all the ordAcquisitionList where action does not contain DEFAULT_ACTION
        defaultOrdAcquisitionShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the ordAcquisitionList where action does not contain UPDATED_ACTION
        defaultOrdAcquisitionShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByOrdAcquisitionCharIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);
        OrdAcquisitionChar ordAcquisitionChar = OrdAcquisitionCharResourceIT.createEntity(em);
        em.persist(ordAcquisitionChar);
        em.flush();
        ordAcquisition.addOrdAcquisitionChar(ordAcquisitionChar);
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);
        Long ordAcquisitionCharId = ordAcquisitionChar.getId();

        // Get all the ordAcquisitionList where ordAcquisitionChar equals to ordAcquisitionCharId
        defaultOrdAcquisitionShouldBeFound("ordAcquisitionCharId.equals=" + ordAcquisitionCharId);

        // Get all the ordAcquisitionList where ordAcquisitionChar equals to (ordAcquisitionCharId + 1)
        defaultOrdAcquisitionShouldNotBeFound("ordAcquisitionCharId.equals=" + (ordAcquisitionCharId + 1));
    }

    @Test
    @Transactional
    void getAllOrdAcquisitionsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordAcquisition.setOrdProductOrder(ordProductOrder);
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordAcquisitionList where ordProductOrder equals to ordProductOrderId
        defaultOrdAcquisitionShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordAcquisitionList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdAcquisitionShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdAcquisitionShouldBeFound(String filter) throws Exception {
        restOrdAcquisitionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordAcquisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].affiliate").value(hasItem(DEFAULT_AFFILIATE)))
            .andExpect(jsonPath("$.[*].partner").value(hasItem(DEFAULT_PARTNER)))
            .andExpect(jsonPath("$.[*].acquisitionAgent").value(hasItem(DEFAULT_ACQUISITION_AGENT)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)));

        // Check, that the count call also returns 1
        restOrdAcquisitionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdAcquisitionShouldNotBeFound(String filter) throws Exception {
        restOrdAcquisitionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdAcquisitionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdAcquisition() throws Exception {
        // Get the ordAcquisition
        restOrdAcquisitionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdAcquisition() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();

        // Update the ordAcquisition
        OrdAcquisition updatedOrdAcquisition = ordAcquisitionRepository.findById(ordAcquisition.getId()).get();
        // Disconnect from session so that the updates on updatedOrdAcquisition are not directly saved in db
        em.detach(updatedOrdAcquisition);
        updatedOrdAcquisition
            .channel(UPDATED_CHANNEL)
            .affiliate(UPDATED_AFFILIATE)
            .partner(UPDATED_PARTNER)
            .acquisitionAgent(UPDATED_ACQUISITION_AGENT)
            .action(UPDATED_ACTION);
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(updatedOrdAcquisition);

        restOrdAcquisitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordAcquisitionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
        OrdAcquisition testOrdAcquisition = ordAcquisitionList.get(ordAcquisitionList.size() - 1);
        assertThat(testOrdAcquisition.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testOrdAcquisition.getAffiliate()).isEqualTo(UPDATED_AFFILIATE);
        assertThat(testOrdAcquisition.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testOrdAcquisition.getAcquisitionAgent()).isEqualTo(UPDATED_ACQUISITION_AGENT);
        assertThat(testOrdAcquisition.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    void putNonExistingOrdAcquisition() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();
        ordAcquisition.setId(count.incrementAndGet());

        // Create the OrdAcquisition
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdAcquisitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordAcquisitionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdAcquisition() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();
        ordAcquisition.setId(count.incrementAndGet());

        // Create the OrdAcquisition
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdAcquisition() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();
        ordAcquisition.setId(count.incrementAndGet());

        // Create the OrdAcquisition
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdAcquisitionWithPatch() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();

        // Update the ordAcquisition using partial update
        OrdAcquisition partialUpdatedOrdAcquisition = new OrdAcquisition();
        partialUpdatedOrdAcquisition.setId(ordAcquisition.getId());

        partialUpdatedOrdAcquisition.affiliate(UPDATED_AFFILIATE).partner(UPDATED_PARTNER).acquisitionAgent(UPDATED_ACQUISITION_AGENT);

        restOrdAcquisitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdAcquisition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdAcquisition))
            )
            .andExpect(status().isOk());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
        OrdAcquisition testOrdAcquisition = ordAcquisitionList.get(ordAcquisitionList.size() - 1);
        assertThat(testOrdAcquisition.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testOrdAcquisition.getAffiliate()).isEqualTo(UPDATED_AFFILIATE);
        assertThat(testOrdAcquisition.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testOrdAcquisition.getAcquisitionAgent()).isEqualTo(UPDATED_ACQUISITION_AGENT);
        assertThat(testOrdAcquisition.getAction()).isEqualTo(DEFAULT_ACTION);
    }

    @Test
    @Transactional
    void fullUpdateOrdAcquisitionWithPatch() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();

        // Update the ordAcquisition using partial update
        OrdAcquisition partialUpdatedOrdAcquisition = new OrdAcquisition();
        partialUpdatedOrdAcquisition.setId(ordAcquisition.getId());

        partialUpdatedOrdAcquisition
            .channel(UPDATED_CHANNEL)
            .affiliate(UPDATED_AFFILIATE)
            .partner(UPDATED_PARTNER)
            .acquisitionAgent(UPDATED_ACQUISITION_AGENT)
            .action(UPDATED_ACTION);

        restOrdAcquisitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdAcquisition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdAcquisition))
            )
            .andExpect(status().isOk());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
        OrdAcquisition testOrdAcquisition = ordAcquisitionList.get(ordAcquisitionList.size() - 1);
        assertThat(testOrdAcquisition.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testOrdAcquisition.getAffiliate()).isEqualTo(UPDATED_AFFILIATE);
        assertThat(testOrdAcquisition.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testOrdAcquisition.getAcquisitionAgent()).isEqualTo(UPDATED_ACQUISITION_AGENT);
        assertThat(testOrdAcquisition.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    void patchNonExistingOrdAcquisition() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();
        ordAcquisition.setId(count.incrementAndGet());

        // Create the OrdAcquisition
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdAcquisitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordAcquisitionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdAcquisition() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();
        ordAcquisition.setId(count.incrementAndGet());

        // Create the OrdAcquisition
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdAcquisition() throws Exception {
        int databaseSizeBeforeUpdate = ordAcquisitionRepository.findAll().size();
        ordAcquisition.setId(count.incrementAndGet());

        // Create the OrdAcquisition
        OrdAcquisitionDTO ordAcquisitionDTO = ordAcquisitionMapper.toDto(ordAcquisition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdAcquisitionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordAcquisitionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdAcquisition in the database
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdAcquisition() throws Exception {
        // Initialize the database
        ordAcquisitionRepository.saveAndFlush(ordAcquisition);

        int databaseSizeBeforeDelete = ordAcquisitionRepository.findAll().size();

        // Delete the ordAcquisition
        restOrdAcquisitionMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordAcquisition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdAcquisition> ordAcquisitionList = ordAcquisitionRepository.findAll();
        assertThat(ordAcquisitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
