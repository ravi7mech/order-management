package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdProduct;
import com.apptium.order.domain.OrdProductCharacteristics;
import com.apptium.order.repository.OrdProductCharacteristicsRepository;
import com.apptium.order.service.criteria.OrdProductCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdProductCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdProductCharacteristicsMapper;
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
 * Integration tests for the {@link OrdProductCharacteristicsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdProductCharacteristicsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-product-characteristics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdProductCharacteristicsRepository ordProductCharacteristicsRepository;

    @Autowired
    private OrdProductCharacteristicsMapper ordProductCharacteristicsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdProductCharacteristicsMockMvc;

    private OrdProductCharacteristics ordProductCharacteristics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProductCharacteristics createEntity(EntityManager em) {
        OrdProductCharacteristics ordProductCharacteristics = new OrdProductCharacteristics()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE);
        return ordProductCharacteristics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProductCharacteristics createUpdatedEntity(EntityManager em) {
        OrdProductCharacteristics ordProductCharacteristics = new OrdProductCharacteristics()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE);
        return ordProductCharacteristics;
    }

    @BeforeEach
    public void initTest() {
        ordProductCharacteristics = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdProductCharacteristics() throws Exception {
        int databaseSizeBeforeCreate = ordProductCharacteristicsRepository.findAll().size();
        // Create the OrdProductCharacteristics
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);
        restOrdProductCharacteristicsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeCreate + 1);
        OrdProductCharacteristics testOrdProductCharacteristics = ordProductCharacteristicsList.get(
            ordProductCharacteristicsList.size() - 1
        );
        assertThat(testOrdProductCharacteristics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdProductCharacteristics.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdProductCharacteristics.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
    }

    @Test
    @Transactional
    void createOrdProductCharacteristicsWithExistingId() throws Exception {
        // Create the OrdProductCharacteristics with an existing ID
        ordProductCharacteristics.setId(1L);
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);

        int databaseSizeBeforeCreate = ordProductCharacteristicsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdProductCharacteristicsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristics() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList
        restOrdProductCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProductCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)));
    }

    @Test
    @Transactional
    void getOrdProductCharacteristics() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get the ordProductCharacteristics
        restOrdProductCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL_ID, ordProductCharacteristics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordProductCharacteristics.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE));
    }

    @Test
    @Transactional
    void getOrdProductCharacteristicsByIdFiltering() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        Long id = ordProductCharacteristics.getId();

        defaultOrdProductCharacteristicsShouldBeFound("id.equals=" + id);
        defaultOrdProductCharacteristicsShouldNotBeFound("id.notEquals=" + id);

        defaultOrdProductCharacteristicsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdProductCharacteristicsShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdProductCharacteristicsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdProductCharacteristicsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where name equals to DEFAULT_NAME
        defaultOrdProductCharacteristicsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordProductCharacteristicsList where name equals to UPDATED_NAME
        defaultOrdProductCharacteristicsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where name not equals to DEFAULT_NAME
        defaultOrdProductCharacteristicsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordProductCharacteristicsList where name not equals to UPDATED_NAME
        defaultOrdProductCharacteristicsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdProductCharacteristicsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordProductCharacteristicsList where name equals to UPDATED_NAME
        defaultOrdProductCharacteristicsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where name is not null
        defaultOrdProductCharacteristicsShouldBeFound("name.specified=true");

        // Get all the ordProductCharacteristicsList where name is null
        defaultOrdProductCharacteristicsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where name contains DEFAULT_NAME
        defaultOrdProductCharacteristicsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordProductCharacteristicsList where name contains UPDATED_NAME
        defaultOrdProductCharacteristicsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where name does not contain DEFAULT_NAME
        defaultOrdProductCharacteristicsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordProductCharacteristicsList where name does not contain UPDATED_NAME
        defaultOrdProductCharacteristicsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where value equals to DEFAULT_VALUE
        defaultOrdProductCharacteristicsShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the ordProductCharacteristicsList where value equals to UPDATED_VALUE
        defaultOrdProductCharacteristicsShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where value not equals to DEFAULT_VALUE
        defaultOrdProductCharacteristicsShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the ordProductCharacteristicsList where value not equals to UPDATED_VALUE
        defaultOrdProductCharacteristicsShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOrdProductCharacteristicsShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the ordProductCharacteristicsList where value equals to UPDATED_VALUE
        defaultOrdProductCharacteristicsShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where value is not null
        defaultOrdProductCharacteristicsShouldBeFound("value.specified=true");

        // Get all the ordProductCharacteristicsList where value is null
        defaultOrdProductCharacteristicsShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueContainsSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where value contains DEFAULT_VALUE
        defaultOrdProductCharacteristicsShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the ordProductCharacteristicsList where value contains UPDATED_VALUE
        defaultOrdProductCharacteristicsShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where value does not contain DEFAULT_VALUE
        defaultOrdProductCharacteristicsShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the ordProductCharacteristicsList where value does not contain UPDATED_VALUE
        defaultOrdProductCharacteristicsShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where valueType equals to DEFAULT_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProductCharacteristicsList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProductCharacteristicsList where valueType not equals to UPDATED_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the ordProductCharacteristicsList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where valueType is not null
        defaultOrdProductCharacteristicsShouldBeFound("valueType.specified=true");

        // Get all the ordProductCharacteristicsList where valueType is null
        defaultOrdProductCharacteristicsShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where valueType contains DEFAULT_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProductCharacteristicsList where valueType contains UPDATED_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        // Get all the ordProductCharacteristicsList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the ordProductCharacteristicsList where valueType does not contain UPDATED_VALUE_TYPE
        defaultOrdProductCharacteristicsShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdProductCharacteristicsByOrdProductIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);
        OrdProduct ordProduct = OrdProductResourceIT.createEntity(em);
        em.persist(ordProduct);
        em.flush();
        ordProductCharacteristics.setOrdProduct(ordProduct);
        ordProduct.setOrdProductCharacteristics(ordProductCharacteristics);
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);
        Long ordProductId = ordProduct.getId();

        // Get all the ordProductCharacteristicsList where ordProduct equals to ordProductId
        defaultOrdProductCharacteristicsShouldBeFound("ordProductId.equals=" + ordProductId);

        // Get all the ordProductCharacteristicsList where ordProduct equals to (ordProductId + 1)
        defaultOrdProductCharacteristicsShouldNotBeFound("ordProductId.equals=" + (ordProductId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdProductCharacteristicsShouldBeFound(String filter) throws Exception {
        restOrdProductCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProductCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)));

        // Check, that the count call also returns 1
        restOrdProductCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdProductCharacteristicsShouldNotBeFound(String filter) throws Exception {
        restOrdProductCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdProductCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdProductCharacteristics() throws Exception {
        // Get the ordProductCharacteristics
        restOrdProductCharacteristicsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdProductCharacteristics() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();

        // Update the ordProductCharacteristics
        OrdProductCharacteristics updatedOrdProductCharacteristics = ordProductCharacteristicsRepository
            .findById(ordProductCharacteristics.getId())
            .get();
        // Disconnect from session so that the updates on updatedOrdProductCharacteristics are not directly saved in db
        em.detach(updatedOrdProductCharacteristics);
        updatedOrdProductCharacteristics.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(updatedOrdProductCharacteristics);

        restOrdProductCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdProductCharacteristics testOrdProductCharacteristics = ordProductCharacteristicsList.get(
            ordProductCharacteristicsList.size() - 1
        );
        assertThat(testOrdProductCharacteristics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProductCharacteristics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdProductCharacteristics.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingOrdProductCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();
        ordProductCharacteristics.setId(count.incrementAndGet());

        // Create the OrdProductCharacteristics
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdProductCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();
        ordProductCharacteristics.setId(count.incrementAndGet());

        // Create the OrdProductCharacteristics
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdProductCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();
        ordProductCharacteristics.setId(count.incrementAndGet());

        // Create the OrdProductCharacteristics
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdProductCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();

        // Update the ordProductCharacteristics using partial update
        OrdProductCharacteristics partialUpdatedOrdProductCharacteristics = new OrdProductCharacteristics();
        partialUpdatedOrdProductCharacteristics.setId(ordProductCharacteristics.getId());

        partialUpdatedOrdProductCharacteristics.name(UPDATED_NAME);

        restOrdProductCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProductCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProductCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdProductCharacteristics testOrdProductCharacteristics = ordProductCharacteristicsList.get(
            ordProductCharacteristicsList.size() - 1
        );
        assertThat(testOrdProductCharacteristics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProductCharacteristics.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdProductCharacteristics.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateOrdProductCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();

        // Update the ordProductCharacteristics using partial update
        OrdProductCharacteristics partialUpdatedOrdProductCharacteristics = new OrdProductCharacteristics();
        partialUpdatedOrdProductCharacteristics.setId(ordProductCharacteristics.getId());

        partialUpdatedOrdProductCharacteristics.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);

        restOrdProductCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProductCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProductCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdProductCharacteristics testOrdProductCharacteristics = ordProductCharacteristicsList.get(
            ordProductCharacteristicsList.size() - 1
        );
        assertThat(testOrdProductCharacteristics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdProductCharacteristics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdProductCharacteristics.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdProductCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();
        ordProductCharacteristics.setId(count.incrementAndGet());

        // Create the OrdProductCharacteristics
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordProductCharacteristicsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdProductCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();
        ordProductCharacteristics.setId(count.incrementAndGet());

        // Create the OrdProductCharacteristics
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdProductCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordProductCharacteristicsRepository.findAll().size();
        ordProductCharacteristics.setId(count.incrementAndGet());

        // Create the OrdProductCharacteristics
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductCharacteristicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProductCharacteristics in the database
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdProductCharacteristics() throws Exception {
        // Initialize the database
        ordProductCharacteristicsRepository.saveAndFlush(ordProductCharacteristics);

        int databaseSizeBeforeDelete = ordProductCharacteristicsRepository.findAll().size();

        // Delete the ordProductCharacteristics
        restOrdProductCharacteristicsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordProductCharacteristics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdProductCharacteristics> ordProductCharacteristicsList = ordProductCharacteristicsRepository.findAll();
        assertThat(ordProductCharacteristicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
