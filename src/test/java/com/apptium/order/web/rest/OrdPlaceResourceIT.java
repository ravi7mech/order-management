package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdPlace;
import com.apptium.order.domain.OrdProduct;
import com.apptium.order.repository.OrdPlaceRepository;
import com.apptium.order.service.criteria.OrdPlaceCriteria;
import com.apptium.order.service.dto.OrdPlaceDTO;
import com.apptium.order.service.mapper.OrdPlaceMapper;
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
 * Integration tests for the {@link OrdPlaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdPlaceResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-places";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdPlaceRepository ordPlaceRepository;

    @Autowired
    private OrdPlaceMapper ordPlaceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdPlaceMockMvc;

    private OrdPlace ordPlace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPlace createEntity(EntityManager em) {
        OrdPlace ordPlace = new OrdPlace().href(DEFAULT_HREF).name(DEFAULT_NAME).role(DEFAULT_ROLE);
        return ordPlace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdPlace createUpdatedEntity(EntityManager em) {
        OrdPlace ordPlace = new OrdPlace().href(UPDATED_HREF).name(UPDATED_NAME).role(UPDATED_ROLE);
        return ordPlace;
    }

    @BeforeEach
    public void initTest() {
        ordPlace = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdPlace() throws Exception {
        int databaseSizeBeforeCreate = ordPlaceRepository.findAll().size();
        // Create the OrdPlace
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);
        restOrdPlaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO)))
            .andExpect(status().isCreated());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        OrdPlace testOrdPlace = ordPlaceList.get(ordPlaceList.size() - 1);
        assertThat(testOrdPlace.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdPlace.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createOrdPlaceWithExistingId() throws Exception {
        // Create the OrdPlace with an existing ID
        ordPlace.setId(1L);
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);

        int databaseSizeBeforeCreate = ordPlaceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdPlaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdPlaces() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList
        restOrdPlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));
    }

    @Test
    @Transactional
    void getOrdPlace() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get the ordPlace
        restOrdPlaceMockMvc
            .perform(get(ENTITY_API_URL_ID, ordPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordPlace.getId().intValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE));
    }

    @Test
    @Transactional
    void getOrdPlacesByIdFiltering() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        Long id = ordPlace.getId();

        defaultOrdPlaceShouldBeFound("id.equals=" + id);
        defaultOrdPlaceShouldNotBeFound("id.notEquals=" + id);

        defaultOrdPlaceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdPlaceShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdPlaceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdPlaceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where href equals to DEFAULT_HREF
        defaultOrdPlaceShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the ordPlaceList where href equals to UPDATED_HREF
        defaultOrdPlaceShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where href not equals to DEFAULT_HREF
        defaultOrdPlaceShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the ordPlaceList where href not equals to UPDATED_HREF
        defaultOrdPlaceShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where href in DEFAULT_HREF or UPDATED_HREF
        defaultOrdPlaceShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the ordPlaceList where href equals to UPDATED_HREF
        defaultOrdPlaceShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where href is not null
        defaultOrdPlaceShouldBeFound("href.specified=true");

        // Get all the ordPlaceList where href is null
        defaultOrdPlaceShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPlacesByHrefContainsSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where href contains DEFAULT_HREF
        defaultOrdPlaceShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the ordPlaceList where href contains UPDATED_HREF
        defaultOrdPlaceShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where href does not contain DEFAULT_HREF
        defaultOrdPlaceShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the ordPlaceList where href does not contain UPDATED_HREF
        defaultOrdPlaceShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where name equals to DEFAULT_NAME
        defaultOrdPlaceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordPlaceList where name equals to UPDATED_NAME
        defaultOrdPlaceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where name not equals to DEFAULT_NAME
        defaultOrdPlaceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordPlaceList where name not equals to UPDATED_NAME
        defaultOrdPlaceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdPlaceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordPlaceList where name equals to UPDATED_NAME
        defaultOrdPlaceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where name is not null
        defaultOrdPlaceShouldBeFound("name.specified=true");

        // Get all the ordPlaceList where name is null
        defaultOrdPlaceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPlacesByNameContainsSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where name contains DEFAULT_NAME
        defaultOrdPlaceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordPlaceList where name contains UPDATED_NAME
        defaultOrdPlaceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where name does not contain DEFAULT_NAME
        defaultOrdPlaceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordPlaceList where name does not contain UPDATED_NAME
        defaultOrdPlaceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where role equals to DEFAULT_ROLE
        defaultOrdPlaceShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the ordPlaceList where role equals to UPDATED_ROLE
        defaultOrdPlaceShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where role not equals to DEFAULT_ROLE
        defaultOrdPlaceShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the ordPlaceList where role not equals to UPDATED_ROLE
        defaultOrdPlaceShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultOrdPlaceShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the ordPlaceList where role equals to UPDATED_ROLE
        defaultOrdPlaceShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where role is not null
        defaultOrdPlaceShouldBeFound("role.specified=true");

        // Get all the ordPlaceList where role is null
        defaultOrdPlaceShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdPlacesByRoleContainsSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where role contains DEFAULT_ROLE
        defaultOrdPlaceShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the ordPlaceList where role contains UPDATED_ROLE
        defaultOrdPlaceShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        // Get all the ordPlaceList where role does not contain DEFAULT_ROLE
        defaultOrdPlaceShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the ordPlaceList where role does not contain UPDATED_ROLE
        defaultOrdPlaceShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdPlacesByOrdProductIsEqualToSomething() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);
        OrdProduct ordProduct = OrdProductResourceIT.createEntity(em);
        em.persist(ordProduct);
        em.flush();
        ordPlace.setOrdProduct(ordProduct);
        ordPlaceRepository.saveAndFlush(ordPlace);
        Long ordProductId = ordProduct.getId();

        // Get all the ordPlaceList where ordProduct equals to ordProductId
        defaultOrdPlaceShouldBeFound("ordProductId.equals=" + ordProductId);

        // Get all the ordPlaceList where ordProduct equals to (ordProductId + 1)
        defaultOrdPlaceShouldNotBeFound("ordProductId.equals=" + (ordProductId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdPlaceShouldBeFound(String filter) throws Exception {
        restOrdPlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));

        // Check, that the count call also returns 1
        restOrdPlaceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdPlaceShouldNotBeFound(String filter) throws Exception {
        restOrdPlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdPlaceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdPlace() throws Exception {
        // Get the ordPlace
        restOrdPlaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdPlace() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();

        // Update the ordPlace
        OrdPlace updatedOrdPlace = ordPlaceRepository.findById(ordPlace.getId()).get();
        // Disconnect from session so that the updates on updatedOrdPlace are not directly saved in db
        em.detach(updatedOrdPlace);
        updatedOrdPlace.href(UPDATED_HREF).name(UPDATED_NAME).role(UPDATED_ROLE);
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(updatedOrdPlace);

        restOrdPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPlaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
        OrdPlace testOrdPlace = ordPlaceList.get(ordPlaceList.size() - 1);
        assertThat(testOrdPlace.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdPlace.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingOrdPlace() throws Exception {
        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();
        ordPlace.setId(count.incrementAndGet());

        // Create the OrdPlace
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordPlaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdPlace() throws Exception {
        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();
        ordPlace.setId(count.incrementAndGet());

        // Create the OrdPlace
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdPlace() throws Exception {
        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();
        ordPlace.setId(count.incrementAndGet());

        // Create the OrdPlace
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPlaceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdPlaceWithPatch() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();

        // Update the ordPlace using partial update
        OrdPlace partialUpdatedOrdPlace = new OrdPlace();
        partialUpdatedOrdPlace.setId(ordPlace.getId());

        partialUpdatedOrdPlace.href(UPDATED_HREF);

        restOrdPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPlace))
            )
            .andExpect(status().isOk());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
        OrdPlace testOrdPlace = ordPlaceList.get(ordPlaceList.size() - 1);
        assertThat(testOrdPlace.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdPlace.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateOrdPlaceWithPatch() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();

        // Update the ordPlace using partial update
        OrdPlace partialUpdatedOrdPlace = new OrdPlace();
        partialUpdatedOrdPlace.setId(ordPlace.getId());

        partialUpdatedOrdPlace.href(UPDATED_HREF).name(UPDATED_NAME).role(UPDATED_ROLE);

        restOrdPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdPlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdPlace))
            )
            .andExpect(status().isOk());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
        OrdPlace testOrdPlace = ordPlaceList.get(ordPlaceList.size() - 1);
        assertThat(testOrdPlace.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdPlace.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdPlace() throws Exception {
        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();
        ordPlace.setId(count.incrementAndGet());

        // Create the OrdPlace
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordPlaceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdPlace() throws Exception {
        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();
        ordPlace.setId(count.incrementAndGet());

        // Create the OrdPlace
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdPlace() throws Exception {
        int databaseSizeBeforeUpdate = ordPlaceRepository.findAll().size();
        ordPlace.setId(count.incrementAndGet());

        // Create the OrdPlace
        OrdPlaceDTO ordPlaceDTO = ordPlaceMapper.toDto(ordPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordPlaceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdPlace in the database
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdPlace() throws Exception {
        // Initialize the database
        ordPlaceRepository.saveAndFlush(ordPlace);

        int databaseSizeBeforeDelete = ordPlaceRepository.findAll().size();

        // Delete the ordPlace
        restOrdPlaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordPlace.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdPlace> ordPlaceList = ordPlaceRepository.findAll();
        assertThat(ordPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
