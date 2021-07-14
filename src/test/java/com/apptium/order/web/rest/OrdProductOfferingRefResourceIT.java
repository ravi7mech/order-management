package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdProductOfferingRef;
import com.apptium.order.repository.OrdProductOfferingRefRepository;
import com.apptium.order.service.criteria.OrdProductOfferingRefCriteria;
import com.apptium.order.service.dto.OrdProductOfferingRefDTO;
import com.apptium.order.service.mapper.OrdProductOfferingRefMapper;
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
 * Integration tests for the {@link OrdProductOfferingRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdProductOfferingRefResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_GUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-product-offering-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdProductOfferingRefRepository ordProductOfferingRefRepository;

    @Autowired
    private OrdProductOfferingRefMapper ordProductOfferingRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdProductOfferingRefMockMvc;

    private OrdProductOfferingRef ordProductOfferingRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProductOfferingRef createEntity(EntityManager em) {
        OrdProductOfferingRef ordProductOfferingRef = new OrdProductOfferingRef()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .productGuid(DEFAULT_PRODUCT_GUID);
        return ordProductOfferingRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProductOfferingRef createUpdatedEntity(EntityManager em) {
        OrdProductOfferingRef ordProductOfferingRef = new OrdProductOfferingRef()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .productGuid(UPDATED_PRODUCT_GUID);
        return ordProductOfferingRef;
    }

    @BeforeEach
    public void initTest() {
        ordProductOfferingRef = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdProductOfferingRef() throws Exception {
        int databaseSizeBeforeCreate = ordProductOfferingRefRepository.findAll().size();
        // Create the OrdProductOfferingRef
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);
        restOrdProductOfferingRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeCreate + 1);
        OrdProductOfferingRef testOrdProductOfferingRef = ordProductOfferingRefList.get(ordProductOfferingRefList.size() - 1);
        assertThat(testOrdProductOfferingRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdProductOfferingRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdProductOfferingRef.getProductGuid()).isEqualTo(DEFAULT_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void createOrdProductOfferingRefWithExistingId() throws Exception {
        // Create the OrdProductOfferingRef with an existing ID
        ordProductOfferingRef.setId(1L);
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);

        int databaseSizeBeforeCreate = ordProductOfferingRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdProductOfferingRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefs() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList
        restOrdProductOfferingRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProductOfferingRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].productGuid").value(hasItem(DEFAULT_PRODUCT_GUID)));
    }

    @Test
    @Transactional
    void getOrdProductOfferingRef() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get the ordProductOfferingRef
        restOrdProductOfferingRefMockMvc
            .perform(get(ENTITY_API_URL_ID, ordProductOfferingRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordProductOfferingRef.getId().intValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.productGuid").value(DEFAULT_PRODUCT_GUID));
    }

    @Test
    @Transactional
    void getOrdProductOfferingRefsByIdFiltering() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        Long id = ordProductOfferingRef.getId();

        defaultOrdProductOfferingRefShouldBeFound("id.equals=" + id);
        defaultOrdProductOfferingRefShouldNotBeFound("id.notEquals=" + id);

        defaultOrdProductOfferingRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdProductOfferingRefShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdProductOfferingRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdProductOfferingRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where href equals to DEFAULT_HREF
        defaultOrdProductOfferingRefShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the ordProductOfferingRefList where href equals to UPDATED_HREF
        defaultOrdProductOfferingRefShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where href not equals to DEFAULT_HREF
        defaultOrdProductOfferingRefShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the ordProductOfferingRefList where href not equals to UPDATED_HREF
        defaultOrdProductOfferingRefShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where href in DEFAULT_HREF or UPDATED_HREF
        defaultOrdProductOfferingRefShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the ordProductOfferingRefList where href equals to UPDATED_HREF
        defaultOrdProductOfferingRefShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where href is not null
        defaultOrdProductOfferingRefShouldBeFound("href.specified=true");

        // Get all the ordProductOfferingRefList where href is null
        defaultOrdProductOfferingRefShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByHrefContainsSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where href contains DEFAULT_HREF
        defaultOrdProductOfferingRefShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the ordProductOfferingRefList where href contains UPDATED_HREF
        defaultOrdProductOfferingRefShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where href does not contain DEFAULT_HREF
        defaultOrdProductOfferingRefShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the ordProductOfferingRefList where href does not contain UPDATED_HREF
        defaultOrdProductOfferingRefShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where name equals to DEFAULT_NAME
        defaultOrdProductOfferingRefShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordProductOfferingRefList where name equals to UPDATED_NAME
        defaultOrdProductOfferingRefShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where name not equals to DEFAULT_NAME
        defaultOrdProductOfferingRefShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordProductOfferingRefList where name not equals to UPDATED_NAME
        defaultOrdProductOfferingRefShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdProductOfferingRefShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordProductOfferingRefList where name equals to UPDATED_NAME
        defaultOrdProductOfferingRefShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where name is not null
        defaultOrdProductOfferingRefShouldBeFound("name.specified=true");

        // Get all the ordProductOfferingRefList where name is null
        defaultOrdProductOfferingRefShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where name contains DEFAULT_NAME
        defaultOrdProductOfferingRefShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordProductOfferingRefList where name contains UPDATED_NAME
        defaultOrdProductOfferingRefShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where name does not contain DEFAULT_NAME
        defaultOrdProductOfferingRefShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordProductOfferingRefList where name does not contain UPDATED_NAME
        defaultOrdProductOfferingRefShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByProductGuidIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where productGuid equals to DEFAULT_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldBeFound("productGuid.equals=" + DEFAULT_PRODUCT_GUID);

        // Get all the ordProductOfferingRefList where productGuid equals to UPDATED_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldNotBeFound("productGuid.equals=" + UPDATED_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByProductGuidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where productGuid not equals to DEFAULT_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldNotBeFound("productGuid.notEquals=" + DEFAULT_PRODUCT_GUID);

        // Get all the ordProductOfferingRefList where productGuid not equals to UPDATED_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldBeFound("productGuid.notEquals=" + UPDATED_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByProductGuidIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where productGuid in DEFAULT_PRODUCT_GUID or UPDATED_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldBeFound("productGuid.in=" + DEFAULT_PRODUCT_GUID + "," + UPDATED_PRODUCT_GUID);

        // Get all the ordProductOfferingRefList where productGuid equals to UPDATED_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldNotBeFound("productGuid.in=" + UPDATED_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByProductGuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where productGuid is not null
        defaultOrdProductOfferingRefShouldBeFound("productGuid.specified=true");

        // Get all the ordProductOfferingRefList where productGuid is null
        defaultOrdProductOfferingRefShouldNotBeFound("productGuid.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByProductGuidContainsSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where productGuid contains DEFAULT_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldBeFound("productGuid.contains=" + DEFAULT_PRODUCT_GUID);

        // Get all the ordProductOfferingRefList where productGuid contains UPDATED_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldNotBeFound("productGuid.contains=" + UPDATED_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByProductGuidNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        // Get all the ordProductOfferingRefList where productGuid does not contain DEFAULT_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldNotBeFound("productGuid.doesNotContain=" + DEFAULT_PRODUCT_GUID);

        // Get all the ordProductOfferingRefList where productGuid does not contain UPDATED_PRODUCT_GUID
        defaultOrdProductOfferingRefShouldBeFound("productGuid.doesNotContain=" + UPDATED_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void getAllOrdProductOfferingRefsByOrdOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);
        OrdOrderItem ordOrderItem = OrdOrderItemResourceIT.createEntity(em);
        em.persist(ordOrderItem);
        em.flush();
        ordProductOfferingRef.setOrdOrderItem(ordOrderItem);
        ordOrderItem.setOrdProductOfferingRef(ordProductOfferingRef);
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);
        Long ordOrderItemId = ordOrderItem.getId();

        // Get all the ordProductOfferingRefList where ordOrderItem equals to ordOrderItemId
        defaultOrdProductOfferingRefShouldBeFound("ordOrderItemId.equals=" + ordOrderItemId);

        // Get all the ordProductOfferingRefList where ordOrderItem equals to (ordOrderItemId + 1)
        defaultOrdProductOfferingRefShouldNotBeFound("ordOrderItemId.equals=" + (ordOrderItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdProductOfferingRefShouldBeFound(String filter) throws Exception {
        restOrdProductOfferingRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProductOfferingRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].productGuid").value(hasItem(DEFAULT_PRODUCT_GUID)));

        // Check, that the count call also returns 1
        restOrdProductOfferingRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdProductOfferingRefShouldNotBeFound(String filter) throws Exception {
        restOrdProductOfferingRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdProductOfferingRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdProductOfferingRef() throws Exception {
        // Get the ordProductOfferingRef
        restOrdProductOfferingRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdProductOfferingRef() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();

        // Update the ordProductOfferingRef
        OrdProductOfferingRef updatedOrdProductOfferingRef = ordProductOfferingRefRepository.findById(ordProductOfferingRef.getId()).get();
        // Disconnect from session so that the updates on updatedOrdProductOfferingRef are not directly saved in db
        em.detach(updatedOrdProductOfferingRef);
        updatedOrdProductOfferingRef.href(UPDATED_HREF).name(UPDATED_NAME).productGuid(UPDATED_PRODUCT_GUID);
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(updatedOrdProductOfferingRef);

        restOrdProductOfferingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductOfferingRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
        OrdProductOfferingRef testOrdProductOfferingRef = ordProductOfferingRefList.get(ordProductOfferingRefList.size() - 1);
        assertThat(testOrdProductOfferingRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdProductOfferingRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProductOfferingRef.getProductGuid()).isEqualTo(UPDATED_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void putNonExistingOrdProductOfferingRef() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();
        ordProductOfferingRef.setId(count.incrementAndGet());

        // Create the OrdProductOfferingRef
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductOfferingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductOfferingRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdProductOfferingRef() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();
        ordProductOfferingRef.setId(count.incrementAndGet());

        // Create the OrdProductOfferingRef
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOfferingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdProductOfferingRef() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();
        ordProductOfferingRef.setId(count.incrementAndGet());

        // Create the OrdProductOfferingRef
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOfferingRefMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdProductOfferingRefWithPatch() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();

        // Update the ordProductOfferingRef using partial update
        OrdProductOfferingRef partialUpdatedOrdProductOfferingRef = new OrdProductOfferingRef();
        partialUpdatedOrdProductOfferingRef.setId(ordProductOfferingRef.getId());

        restOrdProductOfferingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProductOfferingRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProductOfferingRef))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
        OrdProductOfferingRef testOrdProductOfferingRef = ordProductOfferingRefList.get(ordProductOfferingRefList.size() - 1);
        assertThat(testOrdProductOfferingRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdProductOfferingRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdProductOfferingRef.getProductGuid()).isEqualTo(DEFAULT_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void fullUpdateOrdProductOfferingRefWithPatch() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();

        // Update the ordProductOfferingRef using partial update
        OrdProductOfferingRef partialUpdatedOrdProductOfferingRef = new OrdProductOfferingRef();
        partialUpdatedOrdProductOfferingRef.setId(ordProductOfferingRef.getId());

        partialUpdatedOrdProductOfferingRef.href(UPDATED_HREF).name(UPDATED_NAME).productGuid(UPDATED_PRODUCT_GUID);

        restOrdProductOfferingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProductOfferingRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProductOfferingRef))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
        OrdProductOfferingRef testOrdProductOfferingRef = ordProductOfferingRefList.get(ordProductOfferingRefList.size() - 1);
        assertThat(testOrdProductOfferingRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdProductOfferingRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProductOfferingRef.getProductGuid()).isEqualTo(UPDATED_PRODUCT_GUID);
    }

    @Test
    @Transactional
    void patchNonExistingOrdProductOfferingRef() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();
        ordProductOfferingRef.setId(count.incrementAndGet());

        // Create the OrdProductOfferingRef
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductOfferingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordProductOfferingRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdProductOfferingRef() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();
        ordProductOfferingRef.setId(count.incrementAndGet());

        // Create the OrdProductOfferingRef
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOfferingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdProductOfferingRef() throws Exception {
        int databaseSizeBeforeUpdate = ordProductOfferingRefRepository.findAll().size();
        ordProductOfferingRef.setId(count.incrementAndGet());

        // Create the OrdProductOfferingRef
        OrdProductOfferingRefDTO ordProductOfferingRefDTO = ordProductOfferingRefMapper.toDto(ordProductOfferingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductOfferingRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductOfferingRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProductOfferingRef in the database
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdProductOfferingRef() throws Exception {
        // Initialize the database
        ordProductOfferingRefRepository.saveAndFlush(ordProductOfferingRef);

        int databaseSizeBeforeDelete = ordProductOfferingRefRepository.findAll().size();

        // Delete the ordProductOfferingRef
        restOrdProductOfferingRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordProductOfferingRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdProductOfferingRef> ordProductOfferingRefList = ordProductOfferingRefRepository.findAll();
        assertThat(ordProductOfferingRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
