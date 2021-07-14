package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdCharacteristics;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdCharacteristicsRepository;
import com.apptium.order.service.criteria.OrdCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdCharacteristicsMapper;
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
 * Integration tests for the {@link OrdCharacteristicsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdCharacteristicsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-characteristics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdCharacteristicsRepository ordCharacteristicsRepository;

    @Autowired
    private OrdCharacteristicsMapper ordCharacteristicsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdCharacteristicsMockMvc;

    private OrdCharacteristics ordCharacteristics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdCharacteristics createEntity(EntityManager em) {
        OrdCharacteristics ordCharacteristics = new OrdCharacteristics()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE);
        return ordCharacteristics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdCharacteristics createUpdatedEntity(EntityManager em) {
        OrdCharacteristics ordCharacteristics = new OrdCharacteristics()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE);
        return ordCharacteristics;
    }

    @BeforeEach
    public void initTest() {
        ordCharacteristics = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdCharacteristics() throws Exception {
        int databaseSizeBeforeCreate = ordCharacteristicsRepository.findAll().size();
        // Create the OrdCharacteristics
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);
        restOrdCharacteristicsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeCreate + 1);
        OrdCharacteristics testOrdCharacteristics = ordCharacteristicsList.get(ordCharacteristicsList.size() - 1);
        assertThat(testOrdCharacteristics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdCharacteristics.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdCharacteristics.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
    }

    @Test
    @Transactional
    void createOrdCharacteristicsWithExistingId() throws Exception {
        // Create the OrdCharacteristics with an existing ID
        ordCharacteristics.setId(1L);
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);

        int databaseSizeBeforeCreate = ordCharacteristicsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdCharacteristicsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristics() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList
        restOrdCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)));
    }

    @Test
    @Transactional
    void getOrdCharacteristics() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get the ordCharacteristics
        restOrdCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL_ID, ordCharacteristics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordCharacteristics.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE));
    }

    @Test
    @Transactional
    void getOrdCharacteristicsByIdFiltering() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        Long id = ordCharacteristics.getId();

        defaultOrdCharacteristicsShouldBeFound("id.equals=" + id);
        defaultOrdCharacteristicsShouldNotBeFound("id.notEquals=" + id);

        defaultOrdCharacteristicsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdCharacteristicsShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdCharacteristicsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdCharacteristicsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where name equals to DEFAULT_NAME
        defaultOrdCharacteristicsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordCharacteristicsList where name equals to UPDATED_NAME
        defaultOrdCharacteristicsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where name not equals to DEFAULT_NAME
        defaultOrdCharacteristicsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordCharacteristicsList where name not equals to UPDATED_NAME
        defaultOrdCharacteristicsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdCharacteristicsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordCharacteristicsList where name equals to UPDATED_NAME
        defaultOrdCharacteristicsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where name is not null
        defaultOrdCharacteristicsShouldBeFound("name.specified=true");

        // Get all the ordCharacteristicsList where name is null
        defaultOrdCharacteristicsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where name contains DEFAULT_NAME
        defaultOrdCharacteristicsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordCharacteristicsList where name contains UPDATED_NAME
        defaultOrdCharacteristicsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where name does not contain DEFAULT_NAME
        defaultOrdCharacteristicsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordCharacteristicsList where name does not contain UPDATED_NAME
        defaultOrdCharacteristicsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where value equals to DEFAULT_VALUE
        defaultOrdCharacteristicsShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the ordCharacteristicsList where value equals to UPDATED_VALUE
        defaultOrdCharacteristicsShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where value not equals to DEFAULT_VALUE
        defaultOrdCharacteristicsShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the ordCharacteristicsList where value not equals to UPDATED_VALUE
        defaultOrdCharacteristicsShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOrdCharacteristicsShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the ordCharacteristicsList where value equals to UPDATED_VALUE
        defaultOrdCharacteristicsShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where value is not null
        defaultOrdCharacteristicsShouldBeFound("value.specified=true");

        // Get all the ordCharacteristicsList where value is null
        defaultOrdCharacteristicsShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueContainsSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where value contains DEFAULT_VALUE
        defaultOrdCharacteristicsShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the ordCharacteristicsList where value contains UPDATED_VALUE
        defaultOrdCharacteristicsShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where value does not contain DEFAULT_VALUE
        defaultOrdCharacteristicsShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the ordCharacteristicsList where value does not contain UPDATED_VALUE
        defaultOrdCharacteristicsShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where valueType equals to DEFAULT_VALUE_TYPE
        defaultOrdCharacteristicsShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordCharacteristicsList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdCharacteristicsShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultOrdCharacteristicsShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the ordCharacteristicsList where valueType not equals to UPDATED_VALUE_TYPE
        defaultOrdCharacteristicsShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultOrdCharacteristicsShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the ordCharacteristicsList where valueType equals to UPDATED_VALUE_TYPE
        defaultOrdCharacteristicsShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where valueType is not null
        defaultOrdCharacteristicsShouldBeFound("valueType.specified=true");

        // Get all the ordCharacteristicsList where valueType is null
        defaultOrdCharacteristicsShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where valueType contains DEFAULT_VALUE_TYPE
        defaultOrdCharacteristicsShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the ordCharacteristicsList where valueType contains UPDATED_VALUE_TYPE
        defaultOrdCharacteristicsShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        // Get all the ordCharacteristicsList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultOrdCharacteristicsShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the ordCharacteristicsList where valueType does not contain UPDATED_VALUE_TYPE
        defaultOrdCharacteristicsShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdCharacteristicsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordCharacteristics.setOrdProductOrder(ordProductOrder);
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordCharacteristicsList where ordProductOrder equals to ordProductOrderId
        defaultOrdCharacteristicsShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordCharacteristicsList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdCharacteristicsShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdCharacteristicsShouldBeFound(String filter) throws Exception {
        restOrdCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)));

        // Check, that the count call also returns 1
        restOrdCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdCharacteristicsShouldNotBeFound(String filter) throws Exception {
        restOrdCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdCharacteristics() throws Exception {
        // Get the ordCharacteristics
        restOrdCharacteristicsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdCharacteristics() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();

        // Update the ordCharacteristics
        OrdCharacteristics updatedOrdCharacteristics = ordCharacteristicsRepository.findById(ordCharacteristics.getId()).get();
        // Disconnect from session so that the updates on updatedOrdCharacteristics are not directly saved in db
        em.detach(updatedOrdCharacteristics);
        updatedOrdCharacteristics.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(updatedOrdCharacteristics);

        restOrdCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdCharacteristics testOrdCharacteristics = ordCharacteristicsList.get(ordCharacteristicsList.size() - 1);
        assertThat(testOrdCharacteristics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdCharacteristics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdCharacteristics.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingOrdCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();
        ordCharacteristics.setId(count.incrementAndGet());

        // Create the OrdCharacteristics
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();
        ordCharacteristics.setId(count.incrementAndGet());

        // Create the OrdCharacteristics
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();
        ordCharacteristics.setId(count.incrementAndGet());

        // Create the OrdCharacteristics
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();

        // Update the ordCharacteristics using partial update
        OrdCharacteristics partialUpdatedOrdCharacteristics = new OrdCharacteristics();
        partialUpdatedOrdCharacteristics.setId(ordCharacteristics.getId());

        restOrdCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdCharacteristics testOrdCharacteristics = ordCharacteristicsList.get(ordCharacteristicsList.size() - 1);
        assertThat(testOrdCharacteristics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdCharacteristics.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrdCharacteristics.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateOrdCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();

        // Update the ordCharacteristics using partial update
        OrdCharacteristics partialUpdatedOrdCharacteristics = new OrdCharacteristics();
        partialUpdatedOrdCharacteristics.setId(ordCharacteristics.getId());

        partialUpdatedOrdCharacteristics.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);

        restOrdCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        OrdCharacteristics testOrdCharacteristics = ordCharacteristicsList.get(ordCharacteristicsList.size() - 1);
        assertThat(testOrdCharacteristics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdCharacteristics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrdCharacteristics.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();
        ordCharacteristics.setId(count.incrementAndGet());

        // Create the OrdCharacteristics
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordCharacteristicsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();
        ordCharacteristics.setId(count.incrementAndGet());

        // Create the OrdCharacteristics
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = ordCharacteristicsRepository.findAll().size();
        ordCharacteristics.setId(count.incrementAndGet());

        // Create the OrdCharacteristics
        OrdCharacteristicsDTO ordCharacteristicsDTO = ordCharacteristicsMapper.toDto(ordCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordCharacteristicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdCharacteristics in the database
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdCharacteristics() throws Exception {
        // Initialize the database
        ordCharacteristicsRepository.saveAndFlush(ordCharacteristics);

        int databaseSizeBeforeDelete = ordCharacteristicsRepository.findAll().size();

        // Delete the ordCharacteristics
        restOrdCharacteristicsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordCharacteristics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdCharacteristics> ordCharacteristicsList = ordCharacteristicsRepository.findAll();
        assertThat(ordCharacteristicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
