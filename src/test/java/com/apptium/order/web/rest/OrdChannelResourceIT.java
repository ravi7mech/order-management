package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdChannel;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdChannelRepository;
import com.apptium.order.service.criteria.OrdChannelCriteria;
import com.apptium.order.service.dto.OrdChannelDTO;
import com.apptium.order.service.mapper.OrdChannelMapper;
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
 * Integration tests for the {@link OrdChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdChannelResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdChannelRepository ordChannelRepository;

    @Autowired
    private OrdChannelMapper ordChannelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdChannelMockMvc;

    private OrdChannel ordChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdChannel createEntity(EntityManager em) {
        OrdChannel ordChannel = new OrdChannel().href(DEFAULT_HREF).name(DEFAULT_NAME).role(DEFAULT_ROLE);
        return ordChannel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdChannel createUpdatedEntity(EntityManager em) {
        OrdChannel ordChannel = new OrdChannel().href(UPDATED_HREF).name(UPDATED_NAME).role(UPDATED_ROLE);
        return ordChannel;
    }

    @BeforeEach
    public void initTest() {
        ordChannel = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdChannel() throws Exception {
        int databaseSizeBeforeCreate = ordChannelRepository.findAll().size();
        // Create the OrdChannel
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);
        restOrdChannelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeCreate + 1);
        OrdChannel testOrdChannel = ordChannelList.get(ordChannelList.size() - 1);
        assertThat(testOrdChannel.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdChannel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdChannel.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createOrdChannelWithExistingId() throws Exception {
        // Create the OrdChannel with an existing ID
        ordChannel.setId(1L);
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);

        int databaseSizeBeforeCreate = ordChannelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdChannelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdChannels() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList
        restOrdChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));
    }

    @Test
    @Transactional
    void getOrdChannel() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get the ordChannel
        restOrdChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, ordChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordChannel.getId().intValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE));
    }

    @Test
    @Transactional
    void getOrdChannelsByIdFiltering() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        Long id = ordChannel.getId();

        defaultOrdChannelShouldBeFound("id.equals=" + id);
        defaultOrdChannelShouldNotBeFound("id.notEquals=" + id);

        defaultOrdChannelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdChannelShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdChannelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdChannelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where href equals to DEFAULT_HREF
        defaultOrdChannelShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the ordChannelList where href equals to UPDATED_HREF
        defaultOrdChannelShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where href not equals to DEFAULT_HREF
        defaultOrdChannelShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the ordChannelList where href not equals to UPDATED_HREF
        defaultOrdChannelShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where href in DEFAULT_HREF or UPDATED_HREF
        defaultOrdChannelShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the ordChannelList where href equals to UPDATED_HREF
        defaultOrdChannelShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where href is not null
        defaultOrdChannelShouldBeFound("href.specified=true");

        // Get all the ordChannelList where href is null
        defaultOrdChannelShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdChannelsByHrefContainsSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where href contains DEFAULT_HREF
        defaultOrdChannelShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the ordChannelList where href contains UPDATED_HREF
        defaultOrdChannelShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where href does not contain DEFAULT_HREF
        defaultOrdChannelShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the ordChannelList where href does not contain UPDATED_HREF
        defaultOrdChannelShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where name equals to DEFAULT_NAME
        defaultOrdChannelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordChannelList where name equals to UPDATED_NAME
        defaultOrdChannelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where name not equals to DEFAULT_NAME
        defaultOrdChannelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordChannelList where name not equals to UPDATED_NAME
        defaultOrdChannelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdChannelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordChannelList where name equals to UPDATED_NAME
        defaultOrdChannelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where name is not null
        defaultOrdChannelShouldBeFound("name.specified=true");

        // Get all the ordChannelList where name is null
        defaultOrdChannelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdChannelsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where name contains DEFAULT_NAME
        defaultOrdChannelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordChannelList where name contains UPDATED_NAME
        defaultOrdChannelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where name does not contain DEFAULT_NAME
        defaultOrdChannelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordChannelList where name does not contain UPDATED_NAME
        defaultOrdChannelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where role equals to DEFAULT_ROLE
        defaultOrdChannelShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the ordChannelList where role equals to UPDATED_ROLE
        defaultOrdChannelShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where role not equals to DEFAULT_ROLE
        defaultOrdChannelShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the ordChannelList where role not equals to UPDATED_ROLE
        defaultOrdChannelShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultOrdChannelShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the ordChannelList where role equals to UPDATED_ROLE
        defaultOrdChannelShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where role is not null
        defaultOrdChannelShouldBeFound("role.specified=true");

        // Get all the ordChannelList where role is null
        defaultOrdChannelShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdChannelsByRoleContainsSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where role contains DEFAULT_ROLE
        defaultOrdChannelShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the ordChannelList where role contains UPDATED_ROLE
        defaultOrdChannelShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        // Get all the ordChannelList where role does not contain DEFAULT_ROLE
        defaultOrdChannelShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the ordChannelList where role does not contain UPDATED_ROLE
        defaultOrdChannelShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllOrdChannelsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordChannel.setOrdProductOrder(ordProductOrder);
        ordProductOrder.setOrdChannel(ordChannel);
        ordChannelRepository.saveAndFlush(ordChannel);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordChannelList where ordProductOrder equals to ordProductOrderId
        defaultOrdChannelShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordChannelList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdChannelShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdChannelShouldBeFound(String filter) throws Exception {
        restOrdChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));

        // Check, that the count call also returns 1
        restOrdChannelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdChannelShouldNotBeFound(String filter) throws Exception {
        restOrdChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdChannelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdChannel() throws Exception {
        // Get the ordChannel
        restOrdChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdChannel() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();

        // Update the ordChannel
        OrdChannel updatedOrdChannel = ordChannelRepository.findById(ordChannel.getId()).get();
        // Disconnect from session so that the updates on updatedOrdChannel are not directly saved in db
        em.detach(updatedOrdChannel);
        updatedOrdChannel.href(UPDATED_HREF).name(UPDATED_NAME).role(UPDATED_ROLE);
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(updatedOrdChannel);

        restOrdChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordChannelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordChannelDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
        OrdChannel testOrdChannel = ordChannelList.get(ordChannelList.size() - 1);
        assertThat(testOrdChannel.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdChannel.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingOrdChannel() throws Exception {
        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();
        ordChannel.setId(count.incrementAndGet());

        // Create the OrdChannel
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordChannelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdChannel() throws Exception {
        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();
        ordChannel.setId(count.incrementAndGet());

        // Create the OrdChannel
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdChannel() throws Exception {
        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();
        ordChannel.setId(count.incrementAndGet());

        // Create the OrdChannel
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdChannelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordChannelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdChannelWithPatch() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();

        // Update the ordChannel using partial update
        OrdChannel partialUpdatedOrdChannel = new OrdChannel();
        partialUpdatedOrdChannel.setId(ordChannel.getId());

        partialUpdatedOrdChannel.name(UPDATED_NAME);

        restOrdChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdChannel))
            )
            .andExpect(status().isOk());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
        OrdChannel testOrdChannel = ordChannelList.get(ordChannelList.size() - 1);
        assertThat(testOrdChannel.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testOrdChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdChannel.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateOrdChannelWithPatch() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();

        // Update the ordChannel using partial update
        OrdChannel partialUpdatedOrdChannel = new OrdChannel();
        partialUpdatedOrdChannel.setId(ordChannel.getId());

        partialUpdatedOrdChannel.href(UPDATED_HREF).name(UPDATED_NAME).role(UPDATED_ROLE);

        restOrdChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdChannel))
            )
            .andExpect(status().isOk());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
        OrdChannel testOrdChannel = ordChannelList.get(ordChannelList.size() - 1);
        assertThat(testOrdChannel.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testOrdChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdChannel.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdChannel() throws Exception {
        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();
        ordChannel.setId(count.incrementAndGet());

        // Create the OrdChannel
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordChannelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdChannel() throws Exception {
        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();
        ordChannel.setId(count.incrementAndGet());

        // Create the OrdChannel
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdChannel() throws Exception {
        int databaseSizeBeforeUpdate = ordChannelRepository.findAll().size();
        ordChannel.setId(count.incrementAndGet());

        // Create the OrdChannel
        OrdChannelDTO ordChannelDTO = ordChannelMapper.toDto(ordChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdChannelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordChannelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdChannel in the database
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdChannel() throws Exception {
        // Initialize the database
        ordChannelRepository.saveAndFlush(ordChannel);

        int databaseSizeBeforeDelete = ordChannelRepository.findAll().size();

        // Delete the ordChannel
        restOrdChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordChannel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdChannel> ordChannelList = ordChannelRepository.findAll();
        assertThat(ordChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
