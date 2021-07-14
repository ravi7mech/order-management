package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdOrderItemChar;
import com.apptium.order.repository.OrdOrderItemCharRepository;
import com.apptium.order.service.criteria.OrdOrderItemCharCriteria;
import com.apptium.order.service.dto.OrdOrderItemCharDTO;
import com.apptium.order.service.mapper.OrdOrderItemCharMapper;
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
 * Integration tests for the {@link OrdOrderItemCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdOrderItemCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-order-item-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdOrderItemCharRepository ordOrderItemCharRepository;

    @Autowired
    private OrdOrderItemCharMapper ordOrderItemCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdOrderItemCharMockMvc;

    private OrdOrderItemChar ordOrderItemChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItemChar createEntity(EntityManager em) {
        OrdOrderItemChar ordOrderItemChar = new OrdOrderItemChar().name(DEFAULT_NAME).value(DEFAULT_VALUE);
        return ordOrderItemChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItemChar createUpdatedEntity(EntityManager em) {
        OrdOrderItemChar ordOrderItemChar = new OrdOrderItemChar().name(UPDATED_NAME).value(UPDATED_VALUE);
        return ordOrderItemChar;
    }

    @BeforeEach
    public void initTest() {
        ordOrderItemChar = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdOrderItemChar() throws Exception {
        int databaseSizeBeforeCreate = ordOrderItemCharRepository.findAll().size();
        // Create the OrdOrderItemChar
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);
        restOrdOrderItemCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeCreate + 1);
        OrdOrderItemChar testOrdOrderItemChar = ordOrderItemCharList.get(ordOrderItemCharList.size() - 1);
        assertThat(testOrdOrderItemChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdOrderItemChar.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createOrdOrderItemCharWithExistingId() throws Exception {
        // Create the OrdOrderItemChar with an existing ID
        ordOrderItemChar.setId(1L);
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);

        int databaseSizeBeforeCreate = ordOrderItemCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdOrderItemCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemChars() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList
        restOrdOrderItemCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItemChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getOrdOrderItemChar() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get the ordOrderItemChar
        restOrdOrderItemCharMockMvc
            .perform(get(ENTITY_API_URL_ID, ordOrderItemChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordOrderItemChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getOrdOrderItemCharsByIdFiltering() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        Long id = ordOrderItemChar.getId();

        defaultOrdOrderItemCharShouldBeFound("id.equals=" + id);
        defaultOrdOrderItemCharShouldNotBeFound("id.notEquals=" + id);

        defaultOrdOrderItemCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdOrderItemCharShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdOrderItemCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdOrderItemCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where name equals to DEFAULT_NAME
        defaultOrdOrderItemCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordOrderItemCharList where name equals to UPDATED_NAME
        defaultOrdOrderItemCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where name not equals to DEFAULT_NAME
        defaultOrdOrderItemCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordOrderItemCharList where name not equals to UPDATED_NAME
        defaultOrdOrderItemCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdOrderItemCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordOrderItemCharList where name equals to UPDATED_NAME
        defaultOrdOrderItemCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where name is not null
        defaultOrdOrderItemCharShouldBeFound("name.specified=true");

        // Get all the ordOrderItemCharList where name is null
        defaultOrdOrderItemCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where name contains DEFAULT_NAME
        defaultOrdOrderItemCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordOrderItemCharList where name contains UPDATED_NAME
        defaultOrdOrderItemCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where name does not contain DEFAULT_NAME
        defaultOrdOrderItemCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordOrderItemCharList where name does not contain UPDATED_NAME
        defaultOrdOrderItemCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where value equals to DEFAULT_VALUE
        defaultOrdOrderItemCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the ordOrderItemCharList where value equals to UPDATED_VALUE
        defaultOrdOrderItemCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where value not equals to DEFAULT_VALUE
        defaultOrdOrderItemCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the ordOrderItemCharList where value not equals to UPDATED_VALUE
        defaultOrdOrderItemCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOrdOrderItemCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the ordOrderItemCharList where value equals to UPDATED_VALUE
        defaultOrdOrderItemCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where value is not null
        defaultOrdOrderItemCharShouldBeFound("value.specified=true");

        // Get all the ordOrderItemCharList where value is null
        defaultOrdOrderItemCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByValueContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where value contains DEFAULT_VALUE
        defaultOrdOrderItemCharShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the ordOrderItemCharList where value contains UPDATED_VALUE
        defaultOrdOrderItemCharShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        // Get all the ordOrderItemCharList where value does not contain DEFAULT_VALUE
        defaultOrdOrderItemCharShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the ordOrderItemCharList where value does not contain UPDATED_VALUE
        defaultOrdOrderItemCharShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemCharsByOrdOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);
        OrdOrderItem ordOrderItem = OrdOrderItemResourceIT.createEntity(em);
        em.persist(ordOrderItem);
        em.flush();
        ordOrderItemChar.setOrdOrderItem(ordOrderItem);
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);
        Long ordOrderItemId = ordOrderItem.getId();

        // Get all the ordOrderItemCharList where ordOrderItem equals to ordOrderItemId
        defaultOrdOrderItemCharShouldBeFound("ordOrderItemId.equals=" + ordOrderItemId);

        // Get all the ordOrderItemCharList where ordOrderItem equals to (ordOrderItemId + 1)
        defaultOrdOrderItemCharShouldNotBeFound("ordOrderItemId.equals=" + (ordOrderItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdOrderItemCharShouldBeFound(String filter) throws Exception {
        restOrdOrderItemCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItemChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restOrdOrderItemCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdOrderItemCharShouldNotBeFound(String filter) throws Exception {
        restOrdOrderItemCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdOrderItemCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdOrderItemChar() throws Exception {
        // Get the ordOrderItemChar
        restOrdOrderItemCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdOrderItemChar() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();

        // Update the ordOrderItemChar
        OrdOrderItemChar updatedOrdOrderItemChar = ordOrderItemCharRepository.findById(ordOrderItemChar.getId()).get();
        // Disconnect from session so that the updates on updatedOrdOrderItemChar are not directly saved in db
        em.detach(updatedOrdOrderItemChar);
        updatedOrdOrderItemChar.name(UPDATED_NAME).value(UPDATED_VALUE);
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(updatedOrdOrderItemChar);

        restOrdOrderItemCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemChar testOrdOrderItemChar = ordOrderItemCharList.get(ordOrderItemCharList.size() - 1);
        assertThat(testOrdOrderItemChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdOrderItemChar.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingOrdOrderItemChar() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();
        ordOrderItemChar.setId(count.incrementAndGet());

        // Create the OrdOrderItemChar
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdOrderItemChar() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();
        ordOrderItemChar.setId(count.incrementAndGet());

        // Create the OrdOrderItemChar
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdOrderItemChar() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();
        ordOrderItemChar.setId(count.incrementAndGet());

        // Create the OrdOrderItemChar
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdOrderItemCharWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();

        // Update the ordOrderItemChar using partial update
        OrdOrderItemChar partialUpdatedOrdOrderItemChar = new OrdOrderItemChar();
        partialUpdatedOrdOrderItemChar.setId(ordOrderItemChar.getId());

        partialUpdatedOrdOrderItemChar.value(UPDATED_VALUE);

        restOrdOrderItemCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItemChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItemChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemChar testOrdOrderItemChar = ordOrderItemCharList.get(ordOrderItemCharList.size() - 1);
        assertThat(testOrdOrderItemChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdOrderItemChar.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateOrdOrderItemCharWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();

        // Update the ordOrderItemChar using partial update
        OrdOrderItemChar partialUpdatedOrdOrderItemChar = new OrdOrderItemChar();
        partialUpdatedOrdOrderItemChar.setId(ordOrderItemChar.getId());

        partialUpdatedOrdOrderItemChar.name(UPDATED_NAME).value(UPDATED_VALUE);

        restOrdOrderItemCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItemChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItemChar))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItemChar testOrdOrderItemChar = ordOrderItemCharList.get(ordOrderItemCharList.size() - 1);
        assertThat(testOrdOrderItemChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdOrderItemChar.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdOrderItemChar() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();
        ordOrderItemChar.setId(count.incrementAndGet());

        // Create the OrdOrderItemChar
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordOrderItemCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdOrderItemChar() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();
        ordOrderItemChar.setId(count.incrementAndGet());

        // Create the OrdOrderItemChar
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdOrderItemChar() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemCharRepository.findAll().size();
        ordOrderItemChar.setId(count.incrementAndGet());

        // Create the OrdOrderItemChar
        OrdOrderItemCharDTO ordOrderItemCharDTO = ordOrderItemCharMapper.toDto(ordOrderItemChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItemChar in the database
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdOrderItemChar() throws Exception {
        // Initialize the database
        ordOrderItemCharRepository.saveAndFlush(ordOrderItemChar);

        int databaseSizeBeforeDelete = ordOrderItemCharRepository.findAll().size();

        // Delete the ordOrderItemChar
        restOrdOrderItemCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordOrderItemChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdOrderItemChar> ordOrderItemCharList = ordOrderItemCharRepository.findAll();
        assertThat(ordOrderItemCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
