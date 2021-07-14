package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdOrderItemChar;
import com.apptium.order.domain.OrdOrderItemProvisioning;
import com.apptium.order.domain.OrdOrderItemRelationship;
import com.apptium.order.domain.OrdOrderPrice;
import com.apptium.order.domain.OrdProduct;
import com.apptium.order.domain.OrdProductOfferingRef;
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdOrderItemRepository;
import com.apptium.order.service.criteria.OrdOrderItemCriteria;
import com.apptium.order.service.dto.OrdOrderItemDTO;
import com.apptium.order.service.mapper.OrdOrderItemMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link OrdOrderItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdOrderItemResourceIT {

    private static final Long DEFAULT_BILLER_ID = 1L;
    private static final Long UPDATED_BILLER_ID = 2L;
    private static final Long SMALLER_BILLER_ID = 1L - 1L;

    private static final Long DEFAULT_FULLFILLMENT_ID = 1L;
    private static final Long UPDATED_FULLFILLMENT_ID = 2L;
    private static final Long SMALLER_FULLFILLMENT_ID = 1L - 1L;

    private static final Long DEFAULT_ACQUISITION_ID = 1L;
    private static final Long UPDATED_ACQUISITION_ID = 2L;
    private static final Long SMALLER_ACQUISITION_ID = 1L - 1L;

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;
    private static final Long SMALLER_QUANTITY = 1L - 1L;

    private static final String DEFAULT_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CART_ITEM_ID = 1L;
    private static final Long UPDATED_CART_ITEM_ID = 2L;
    private static final Long SMALLER_CART_ITEM_ID = 1L - 1L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ord-order-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdOrderItemRepository ordOrderItemRepository;

    @Autowired
    private OrdOrderItemMapper ordOrderItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdOrderItemMockMvc;

    private OrdOrderItem ordOrderItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItem createEntity(EntityManager em) {
        OrdOrderItem ordOrderItem = new OrdOrderItem()
            .billerId(DEFAULT_BILLER_ID)
            .fullfillmentId(DEFAULT_FULLFILLMENT_ID)
            .acquisitionId(DEFAULT_ACQUISITION_ID)
            .action(DEFAULT_ACTION)
            .state(DEFAULT_STATE)
            .quantity(DEFAULT_QUANTITY)
            .itemType(DEFAULT_ITEM_TYPE)
            .itemDescription(DEFAULT_ITEM_DESCRIPTION)
            .cartItemId(DEFAULT_CART_ITEM_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return ordOrderItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdOrderItem createUpdatedEntity(EntityManager em) {
        OrdOrderItem ordOrderItem = new OrdOrderItem()
            .billerId(UPDATED_BILLER_ID)
            .fullfillmentId(UPDATED_FULLFILLMENT_ID)
            .acquisitionId(UPDATED_ACQUISITION_ID)
            .action(UPDATED_ACTION)
            .state(UPDATED_STATE)
            .quantity(UPDATED_QUANTITY)
            .itemType(UPDATED_ITEM_TYPE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .cartItemId(UPDATED_CART_ITEM_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        return ordOrderItem;
    }

    @BeforeEach
    public void initTest() {
        ordOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdOrderItem() throws Exception {
        int databaseSizeBeforeCreate = ordOrderItemRepository.findAll().size();
        // Create the OrdOrderItem
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);
        restOrdOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrdOrderItem testOrdOrderItem = ordOrderItemList.get(ordOrderItemList.size() - 1);
        assertThat(testOrdOrderItem.getBillerId()).isEqualTo(DEFAULT_BILLER_ID);
        assertThat(testOrdOrderItem.getFullfillmentId()).isEqualTo(DEFAULT_FULLFILLMENT_ID);
        assertThat(testOrdOrderItem.getAcquisitionId()).isEqualTo(DEFAULT_ACQUISITION_ID);
        assertThat(testOrdOrderItem.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testOrdOrderItem.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testOrdOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrdOrderItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testOrdOrderItem.getItemDescription()).isEqualTo(DEFAULT_ITEM_DESCRIPTION);
        assertThat(testOrdOrderItem.getCartItemId()).isEqualTo(DEFAULT_CART_ITEM_ID);
        assertThat(testOrdOrderItem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrdOrderItem.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrdOrderItem.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrdOrderItem.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createOrdOrderItemWithExistingId() throws Exception {
        // Create the OrdOrderItem with an existing ID
        ordOrderItem.setId(1L);
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);

        int databaseSizeBeforeCreate = ordOrderItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdOrderItems() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList
        restOrdOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].billerId").value(hasItem(DEFAULT_BILLER_ID.intValue())))
            .andExpect(jsonPath("$.[*].fullfillmentId").value(hasItem(DEFAULT_FULLFILLMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].acquisitionId").value(hasItem(DEFAULT_ACQUISITION_ID.intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cartItemId").value(hasItem(DEFAULT_CART_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrdOrderItem() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get the ordOrderItem
        restOrdOrderItemMockMvc
            .perform(get(ENTITY_API_URL_ID, ordOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.billerId").value(DEFAULT_BILLER_ID.intValue()))
            .andExpect(jsonPath("$.fullfillmentId").value(DEFAULT_FULLFILLMENT_ID.intValue()))
            .andExpect(jsonPath("$.acquisitionId").value(DEFAULT_ACQUISITION_ID.intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE))
            .andExpect(jsonPath("$.itemDescription").value(DEFAULT_ITEM_DESCRIPTION))
            .andExpect(jsonPath("$.cartItemId").value(DEFAULT_CART_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOrdOrderItemsByIdFiltering() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        Long id = ordOrderItem.getId();

        defaultOrdOrderItemShouldBeFound("id.equals=" + id);
        defaultOrdOrderItemShouldNotBeFound("id.notEquals=" + id);

        defaultOrdOrderItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdOrderItemShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdOrderItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdOrderItemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId equals to DEFAULT_BILLER_ID
        defaultOrdOrderItemShouldBeFound("billerId.equals=" + DEFAULT_BILLER_ID);

        // Get all the ordOrderItemList where billerId equals to UPDATED_BILLER_ID
        defaultOrdOrderItemShouldNotBeFound("billerId.equals=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId not equals to DEFAULT_BILLER_ID
        defaultOrdOrderItemShouldNotBeFound("billerId.notEquals=" + DEFAULT_BILLER_ID);

        // Get all the ordOrderItemList where billerId not equals to UPDATED_BILLER_ID
        defaultOrdOrderItemShouldBeFound("billerId.notEquals=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId in DEFAULT_BILLER_ID or UPDATED_BILLER_ID
        defaultOrdOrderItemShouldBeFound("billerId.in=" + DEFAULT_BILLER_ID + "," + UPDATED_BILLER_ID);

        // Get all the ordOrderItemList where billerId equals to UPDATED_BILLER_ID
        defaultOrdOrderItemShouldNotBeFound("billerId.in=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId is not null
        defaultOrdOrderItemShouldBeFound("billerId.specified=true");

        // Get all the ordOrderItemList where billerId is null
        defaultOrdOrderItemShouldNotBeFound("billerId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId is greater than or equal to DEFAULT_BILLER_ID
        defaultOrdOrderItemShouldBeFound("billerId.greaterThanOrEqual=" + DEFAULT_BILLER_ID);

        // Get all the ordOrderItemList where billerId is greater than or equal to UPDATED_BILLER_ID
        defaultOrdOrderItemShouldNotBeFound("billerId.greaterThanOrEqual=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId is less than or equal to DEFAULT_BILLER_ID
        defaultOrdOrderItemShouldBeFound("billerId.lessThanOrEqual=" + DEFAULT_BILLER_ID);

        // Get all the ordOrderItemList where billerId is less than or equal to SMALLER_BILLER_ID
        defaultOrdOrderItemShouldNotBeFound("billerId.lessThanOrEqual=" + SMALLER_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId is less than DEFAULT_BILLER_ID
        defaultOrdOrderItemShouldNotBeFound("billerId.lessThan=" + DEFAULT_BILLER_ID);

        // Get all the ordOrderItemList where billerId is less than UPDATED_BILLER_ID
        defaultOrdOrderItemShouldBeFound("billerId.lessThan=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByBillerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where billerId is greater than DEFAULT_BILLER_ID
        defaultOrdOrderItemShouldNotBeFound("billerId.greaterThan=" + DEFAULT_BILLER_ID);

        // Get all the ordOrderItemList where billerId is greater than SMALLER_BILLER_ID
        defaultOrdOrderItemShouldBeFound("billerId.greaterThan=" + SMALLER_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId equals to DEFAULT_FULLFILLMENT_ID
        defaultOrdOrderItemShouldBeFound("fullfillmentId.equals=" + DEFAULT_FULLFILLMENT_ID);

        // Get all the ordOrderItemList where fullfillmentId equals to UPDATED_FULLFILLMENT_ID
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.equals=" + UPDATED_FULLFILLMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId not equals to DEFAULT_FULLFILLMENT_ID
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.notEquals=" + DEFAULT_FULLFILLMENT_ID);

        // Get all the ordOrderItemList where fullfillmentId not equals to UPDATED_FULLFILLMENT_ID
        defaultOrdOrderItemShouldBeFound("fullfillmentId.notEquals=" + UPDATED_FULLFILLMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId in DEFAULT_FULLFILLMENT_ID or UPDATED_FULLFILLMENT_ID
        defaultOrdOrderItemShouldBeFound("fullfillmentId.in=" + DEFAULT_FULLFILLMENT_ID + "," + UPDATED_FULLFILLMENT_ID);

        // Get all the ordOrderItemList where fullfillmentId equals to UPDATED_FULLFILLMENT_ID
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.in=" + UPDATED_FULLFILLMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId is not null
        defaultOrdOrderItemShouldBeFound("fullfillmentId.specified=true");

        // Get all the ordOrderItemList where fullfillmentId is null
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId is greater than or equal to DEFAULT_FULLFILLMENT_ID
        defaultOrdOrderItemShouldBeFound("fullfillmentId.greaterThanOrEqual=" + DEFAULT_FULLFILLMENT_ID);

        // Get all the ordOrderItemList where fullfillmentId is greater than or equal to UPDATED_FULLFILLMENT_ID
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.greaterThanOrEqual=" + UPDATED_FULLFILLMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId is less than or equal to DEFAULT_FULLFILLMENT_ID
        defaultOrdOrderItemShouldBeFound("fullfillmentId.lessThanOrEqual=" + DEFAULT_FULLFILLMENT_ID);

        // Get all the ordOrderItemList where fullfillmentId is less than or equal to SMALLER_FULLFILLMENT_ID
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.lessThanOrEqual=" + SMALLER_FULLFILLMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId is less than DEFAULT_FULLFILLMENT_ID
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.lessThan=" + DEFAULT_FULLFILLMENT_ID);

        // Get all the ordOrderItemList where fullfillmentId is less than UPDATED_FULLFILLMENT_ID
        defaultOrdOrderItemShouldBeFound("fullfillmentId.lessThan=" + UPDATED_FULLFILLMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByFullfillmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where fullfillmentId is greater than DEFAULT_FULLFILLMENT_ID
        defaultOrdOrderItemShouldNotBeFound("fullfillmentId.greaterThan=" + DEFAULT_FULLFILLMENT_ID);

        // Get all the ordOrderItemList where fullfillmentId is greater than SMALLER_FULLFILLMENT_ID
        defaultOrdOrderItemShouldBeFound("fullfillmentId.greaterThan=" + SMALLER_FULLFILLMENT_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId equals to DEFAULT_ACQUISITION_ID
        defaultOrdOrderItemShouldBeFound("acquisitionId.equals=" + DEFAULT_ACQUISITION_ID);

        // Get all the ordOrderItemList where acquisitionId equals to UPDATED_ACQUISITION_ID
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.equals=" + UPDATED_ACQUISITION_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId not equals to DEFAULT_ACQUISITION_ID
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.notEquals=" + DEFAULT_ACQUISITION_ID);

        // Get all the ordOrderItemList where acquisitionId not equals to UPDATED_ACQUISITION_ID
        defaultOrdOrderItemShouldBeFound("acquisitionId.notEquals=" + UPDATED_ACQUISITION_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId in DEFAULT_ACQUISITION_ID or UPDATED_ACQUISITION_ID
        defaultOrdOrderItemShouldBeFound("acquisitionId.in=" + DEFAULT_ACQUISITION_ID + "," + UPDATED_ACQUISITION_ID);

        // Get all the ordOrderItemList where acquisitionId equals to UPDATED_ACQUISITION_ID
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.in=" + UPDATED_ACQUISITION_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId is not null
        defaultOrdOrderItemShouldBeFound("acquisitionId.specified=true");

        // Get all the ordOrderItemList where acquisitionId is null
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId is greater than or equal to DEFAULT_ACQUISITION_ID
        defaultOrdOrderItemShouldBeFound("acquisitionId.greaterThanOrEqual=" + DEFAULT_ACQUISITION_ID);

        // Get all the ordOrderItemList where acquisitionId is greater than or equal to UPDATED_ACQUISITION_ID
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.greaterThanOrEqual=" + UPDATED_ACQUISITION_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId is less than or equal to DEFAULT_ACQUISITION_ID
        defaultOrdOrderItemShouldBeFound("acquisitionId.lessThanOrEqual=" + DEFAULT_ACQUISITION_ID);

        // Get all the ordOrderItemList where acquisitionId is less than or equal to SMALLER_ACQUISITION_ID
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.lessThanOrEqual=" + SMALLER_ACQUISITION_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId is less than DEFAULT_ACQUISITION_ID
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.lessThan=" + DEFAULT_ACQUISITION_ID);

        // Get all the ordOrderItemList where acquisitionId is less than UPDATED_ACQUISITION_ID
        defaultOrdOrderItemShouldBeFound("acquisitionId.lessThan=" + UPDATED_ACQUISITION_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByAcquisitionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where acquisitionId is greater than DEFAULT_ACQUISITION_ID
        defaultOrdOrderItemShouldNotBeFound("acquisitionId.greaterThan=" + DEFAULT_ACQUISITION_ID);

        // Get all the ordOrderItemList where acquisitionId is greater than SMALLER_ACQUISITION_ID
        defaultOrdOrderItemShouldBeFound("acquisitionId.greaterThan=" + SMALLER_ACQUISITION_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where action equals to DEFAULT_ACTION
        defaultOrdOrderItemShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the ordOrderItemList where action equals to UPDATED_ACTION
        defaultOrdOrderItemShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where action not equals to DEFAULT_ACTION
        defaultOrdOrderItemShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the ordOrderItemList where action not equals to UPDATED_ACTION
        defaultOrdOrderItemShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultOrdOrderItemShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the ordOrderItemList where action equals to UPDATED_ACTION
        defaultOrdOrderItemShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where action is not null
        defaultOrdOrderItemShouldBeFound("action.specified=true");

        // Get all the ordOrderItemList where action is null
        defaultOrdOrderItemShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByActionContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where action contains DEFAULT_ACTION
        defaultOrdOrderItemShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the ordOrderItemList where action contains UPDATED_ACTION
        defaultOrdOrderItemShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where action does not contain DEFAULT_ACTION
        defaultOrdOrderItemShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the ordOrderItemList where action does not contain UPDATED_ACTION
        defaultOrdOrderItemShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where state equals to DEFAULT_STATE
        defaultOrdOrderItemShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the ordOrderItemList where state equals to UPDATED_STATE
        defaultOrdOrderItemShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where state not equals to DEFAULT_STATE
        defaultOrdOrderItemShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the ordOrderItemList where state not equals to UPDATED_STATE
        defaultOrdOrderItemShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where state in DEFAULT_STATE or UPDATED_STATE
        defaultOrdOrderItemShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the ordOrderItemList where state equals to UPDATED_STATE
        defaultOrdOrderItemShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where state is not null
        defaultOrdOrderItemShouldBeFound("state.specified=true");

        // Get all the ordOrderItemList where state is null
        defaultOrdOrderItemShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByStateContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where state contains DEFAULT_STATE
        defaultOrdOrderItemShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the ordOrderItemList where state contains UPDATED_STATE
        defaultOrdOrderItemShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByStateNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where state does not contain DEFAULT_STATE
        defaultOrdOrderItemShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the ordOrderItemList where state does not contain UPDATED_STATE
        defaultOrdOrderItemShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity equals to DEFAULT_QUANTITY
        defaultOrdOrderItemShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the ordOrderItemList where quantity equals to UPDATED_QUANTITY
        defaultOrdOrderItemShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity not equals to DEFAULT_QUANTITY
        defaultOrdOrderItemShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the ordOrderItemList where quantity not equals to UPDATED_QUANTITY
        defaultOrdOrderItemShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultOrdOrderItemShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the ordOrderItemList where quantity equals to UPDATED_QUANTITY
        defaultOrdOrderItemShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity is not null
        defaultOrdOrderItemShouldBeFound("quantity.specified=true");

        // Get all the ordOrderItemList where quantity is null
        defaultOrdOrderItemShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultOrdOrderItemShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the ordOrderItemList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultOrdOrderItemShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultOrdOrderItemShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the ordOrderItemList where quantity is less than or equal to SMALLER_QUANTITY
        defaultOrdOrderItemShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity is less than DEFAULT_QUANTITY
        defaultOrdOrderItemShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the ordOrderItemList where quantity is less than UPDATED_QUANTITY
        defaultOrdOrderItemShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where quantity is greater than DEFAULT_QUANTITY
        defaultOrdOrderItemShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the ordOrderItemList where quantity is greater than SMALLER_QUANTITY
        defaultOrdOrderItemShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemType equals to DEFAULT_ITEM_TYPE
        defaultOrdOrderItemShouldBeFound("itemType.equals=" + DEFAULT_ITEM_TYPE);

        // Get all the ordOrderItemList where itemType equals to UPDATED_ITEM_TYPE
        defaultOrdOrderItemShouldNotBeFound("itemType.equals=" + UPDATED_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemType not equals to DEFAULT_ITEM_TYPE
        defaultOrdOrderItemShouldNotBeFound("itemType.notEquals=" + DEFAULT_ITEM_TYPE);

        // Get all the ordOrderItemList where itemType not equals to UPDATED_ITEM_TYPE
        defaultOrdOrderItemShouldBeFound("itemType.notEquals=" + UPDATED_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemType in DEFAULT_ITEM_TYPE or UPDATED_ITEM_TYPE
        defaultOrdOrderItemShouldBeFound("itemType.in=" + DEFAULT_ITEM_TYPE + "," + UPDATED_ITEM_TYPE);

        // Get all the ordOrderItemList where itemType equals to UPDATED_ITEM_TYPE
        defaultOrdOrderItemShouldNotBeFound("itemType.in=" + UPDATED_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemType is not null
        defaultOrdOrderItemShouldBeFound("itemType.specified=true");

        // Get all the ordOrderItemList where itemType is null
        defaultOrdOrderItemShouldNotBeFound("itemType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemTypeContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemType contains DEFAULT_ITEM_TYPE
        defaultOrdOrderItemShouldBeFound("itemType.contains=" + DEFAULT_ITEM_TYPE);

        // Get all the ordOrderItemList where itemType contains UPDATED_ITEM_TYPE
        defaultOrdOrderItemShouldNotBeFound("itemType.contains=" + UPDATED_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemType does not contain DEFAULT_ITEM_TYPE
        defaultOrdOrderItemShouldNotBeFound("itemType.doesNotContain=" + DEFAULT_ITEM_TYPE);

        // Get all the ordOrderItemList where itemType does not contain UPDATED_ITEM_TYPE
        defaultOrdOrderItemShouldBeFound("itemType.doesNotContain=" + UPDATED_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemDescription equals to DEFAULT_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldBeFound("itemDescription.equals=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the ordOrderItemList where itemDescription equals to UPDATED_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldNotBeFound("itemDescription.equals=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemDescription not equals to DEFAULT_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldNotBeFound("itemDescription.notEquals=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the ordOrderItemList where itemDescription not equals to UPDATED_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldBeFound("itemDescription.notEquals=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemDescription in DEFAULT_ITEM_DESCRIPTION or UPDATED_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldBeFound("itemDescription.in=" + DEFAULT_ITEM_DESCRIPTION + "," + UPDATED_ITEM_DESCRIPTION);

        // Get all the ordOrderItemList where itemDescription equals to UPDATED_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldNotBeFound("itemDescription.in=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemDescription is not null
        defaultOrdOrderItemShouldBeFound("itemDescription.specified=true");

        // Get all the ordOrderItemList where itemDescription is null
        defaultOrdOrderItemShouldNotBeFound("itemDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemDescription contains DEFAULT_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldBeFound("itemDescription.contains=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the ordOrderItemList where itemDescription contains UPDATED_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldNotBeFound("itemDescription.contains=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByItemDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where itemDescription does not contain DEFAULT_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldNotBeFound("itemDescription.doesNotContain=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the ordOrderItemList where itemDescription does not contain UPDATED_ITEM_DESCRIPTION
        defaultOrdOrderItemShouldBeFound("itemDescription.doesNotContain=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId equals to DEFAULT_CART_ITEM_ID
        defaultOrdOrderItemShouldBeFound("cartItemId.equals=" + DEFAULT_CART_ITEM_ID);

        // Get all the ordOrderItemList where cartItemId equals to UPDATED_CART_ITEM_ID
        defaultOrdOrderItemShouldNotBeFound("cartItemId.equals=" + UPDATED_CART_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId not equals to DEFAULT_CART_ITEM_ID
        defaultOrdOrderItemShouldNotBeFound("cartItemId.notEquals=" + DEFAULT_CART_ITEM_ID);

        // Get all the ordOrderItemList where cartItemId not equals to UPDATED_CART_ITEM_ID
        defaultOrdOrderItemShouldBeFound("cartItemId.notEquals=" + UPDATED_CART_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId in DEFAULT_CART_ITEM_ID or UPDATED_CART_ITEM_ID
        defaultOrdOrderItemShouldBeFound("cartItemId.in=" + DEFAULT_CART_ITEM_ID + "," + UPDATED_CART_ITEM_ID);

        // Get all the ordOrderItemList where cartItemId equals to UPDATED_CART_ITEM_ID
        defaultOrdOrderItemShouldNotBeFound("cartItemId.in=" + UPDATED_CART_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId is not null
        defaultOrdOrderItemShouldBeFound("cartItemId.specified=true");

        // Get all the ordOrderItemList where cartItemId is null
        defaultOrdOrderItemShouldNotBeFound("cartItemId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId is greater than or equal to DEFAULT_CART_ITEM_ID
        defaultOrdOrderItemShouldBeFound("cartItemId.greaterThanOrEqual=" + DEFAULT_CART_ITEM_ID);

        // Get all the ordOrderItemList where cartItemId is greater than or equal to UPDATED_CART_ITEM_ID
        defaultOrdOrderItemShouldNotBeFound("cartItemId.greaterThanOrEqual=" + UPDATED_CART_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId is less than or equal to DEFAULT_CART_ITEM_ID
        defaultOrdOrderItemShouldBeFound("cartItemId.lessThanOrEqual=" + DEFAULT_CART_ITEM_ID);

        // Get all the ordOrderItemList where cartItemId is less than or equal to SMALLER_CART_ITEM_ID
        defaultOrdOrderItemShouldNotBeFound("cartItemId.lessThanOrEqual=" + SMALLER_CART_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId is less than DEFAULT_CART_ITEM_ID
        defaultOrdOrderItemShouldNotBeFound("cartItemId.lessThan=" + DEFAULT_CART_ITEM_ID);

        // Get all the ordOrderItemList where cartItemId is less than UPDATED_CART_ITEM_ID
        defaultOrdOrderItemShouldBeFound("cartItemId.lessThan=" + UPDATED_CART_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCartItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where cartItemId is greater than DEFAULT_CART_ITEM_ID
        defaultOrdOrderItemShouldNotBeFound("cartItemId.greaterThan=" + DEFAULT_CART_ITEM_ID);

        // Get all the ordOrderItemList where cartItemId is greater than SMALLER_CART_ITEM_ID
        defaultOrdOrderItemShouldBeFound("cartItemId.greaterThan=" + SMALLER_CART_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrdOrderItemShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the ordOrderItemList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdOrderItemShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOrdOrderItemShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the ordOrderItemList where createdBy not equals to UPDATED_CREATED_BY
        defaultOrdOrderItemShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrdOrderItemShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the ordOrderItemList where createdBy equals to UPDATED_CREATED_BY
        defaultOrdOrderItemShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdBy is not null
        defaultOrdOrderItemShouldBeFound("createdBy.specified=true");

        // Get all the ordOrderItemList where createdBy is null
        defaultOrdOrderItemShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdBy contains DEFAULT_CREATED_BY
        defaultOrdOrderItemShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the ordOrderItemList where createdBy contains UPDATED_CREATED_BY
        defaultOrdOrderItemShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrdOrderItemShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the ordOrderItemList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrdOrderItemShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOrdOrderItemShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the ordOrderItemList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdOrderItemShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOrdOrderItemShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the ordOrderItemList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOrdOrderItemShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOrdOrderItemShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the ordOrderItemList where createdDate equals to UPDATED_CREATED_DATE
        defaultOrdOrderItemShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where createdDate is not null
        defaultOrdOrderItemShouldBeFound("createdDate.specified=true");

        // Get all the ordOrderItemList where createdDate is null
        defaultOrdOrderItemShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultOrdOrderItemShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the ordOrderItemList where updatedBy equals to UPDATED_UPDATED_BY
        defaultOrdOrderItemShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultOrdOrderItemShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the ordOrderItemList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultOrdOrderItemShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultOrdOrderItemShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the ordOrderItemList where updatedBy equals to UPDATED_UPDATED_BY
        defaultOrdOrderItemShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedBy is not null
        defaultOrdOrderItemShouldBeFound("updatedBy.specified=true");

        // Get all the ordOrderItemList where updatedBy is null
        defaultOrdOrderItemShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedBy contains DEFAULT_UPDATED_BY
        defaultOrdOrderItemShouldBeFound("updatedBy.contains=" + DEFAULT_UPDATED_BY);

        // Get all the ordOrderItemList where updatedBy contains UPDATED_UPDATED_BY
        defaultOrdOrderItemShouldNotBeFound("updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedBy does not contain DEFAULT_UPDATED_BY
        defaultOrdOrderItemShouldNotBeFound("updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);

        // Get all the ordOrderItemList where updatedBy does not contain UPDATED_UPDATED_BY
        defaultOrdOrderItemShouldBeFound("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultOrdOrderItemShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the ordOrderItemList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultOrdOrderItemShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultOrdOrderItemShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the ordOrderItemList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultOrdOrderItemShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultOrdOrderItemShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the ordOrderItemList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultOrdOrderItemShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        // Get all the ordOrderItemList where updatedDate is not null
        defaultOrdOrderItemShouldBeFound("updatedDate.specified=true");

        // Get all the ordOrderItemList where updatedDate is null
        defaultOrdOrderItemShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByOrdOrderPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        OrdOrderPrice ordOrderPrice = OrdOrderPriceResourceIT.createEntity(em);
        em.persist(ordOrderPrice);
        em.flush();
        ordOrderItem.setOrdOrderPrice(ordOrderPrice);
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        Long ordOrderPriceId = ordOrderPrice.getId();

        // Get all the ordOrderItemList where ordOrderPrice equals to ordOrderPriceId
        defaultOrdOrderItemShouldBeFound("ordOrderPriceId.equals=" + ordOrderPriceId);

        // Get all the ordOrderItemList where ordOrderPrice equals to (ordOrderPriceId + 1)
        defaultOrdOrderItemShouldNotBeFound("ordOrderPriceId.equals=" + (ordOrderPriceId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByOrdOrderItemRelationshipIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        OrdOrderItemRelationship ordOrderItemRelationship = OrdOrderItemRelationshipResourceIT.createEntity(em);
        em.persist(ordOrderItemRelationship);
        em.flush();
        ordOrderItem.setOrdOrderItemRelationship(ordOrderItemRelationship);
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        Long ordOrderItemRelationshipId = ordOrderItemRelationship.getId();

        // Get all the ordOrderItemList where ordOrderItemRelationship equals to ordOrderItemRelationshipId
        defaultOrdOrderItemShouldBeFound("ordOrderItemRelationshipId.equals=" + ordOrderItemRelationshipId);

        // Get all the ordOrderItemList where ordOrderItemRelationship equals to (ordOrderItemRelationshipId + 1)
        defaultOrdOrderItemShouldNotBeFound("ordOrderItemRelationshipId.equals=" + (ordOrderItemRelationshipId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByOrdProductOfferingRefIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        OrdProductOfferingRef ordProductOfferingRef = OrdProductOfferingRefResourceIT.createEntity(em);
        em.persist(ordProductOfferingRef);
        em.flush();
        ordOrderItem.setOrdProductOfferingRef(ordProductOfferingRef);
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        Long ordProductOfferingRefId = ordProductOfferingRef.getId();

        // Get all the ordOrderItemList where ordProductOfferingRef equals to ordProductOfferingRefId
        defaultOrdOrderItemShouldBeFound("ordProductOfferingRefId.equals=" + ordProductOfferingRefId);

        // Get all the ordOrderItemList where ordProductOfferingRef equals to (ordProductOfferingRefId + 1)
        defaultOrdOrderItemShouldNotBeFound("ordProductOfferingRefId.equals=" + (ordProductOfferingRefId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByOrdProductIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        OrdProduct ordProduct = OrdProductResourceIT.createEntity(em);
        em.persist(ordProduct);
        em.flush();
        ordOrderItem.setOrdProduct(ordProduct);
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        Long ordProductId = ordProduct.getId();

        // Get all the ordOrderItemList where ordProduct equals to ordProductId
        defaultOrdOrderItemShouldBeFound("ordProductId.equals=" + ordProductId);

        // Get all the ordOrderItemList where ordProduct equals to (ordProductId + 1)
        defaultOrdOrderItemShouldNotBeFound("ordProductId.equals=" + (ordProductId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByOrdOrderItemProvisioningIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        OrdOrderItemProvisioning ordOrderItemProvisioning = OrdOrderItemProvisioningResourceIT.createEntity(em);
        em.persist(ordOrderItemProvisioning);
        em.flush();
        ordOrderItem.setOrdOrderItemProvisioning(ordOrderItemProvisioning);
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        Long ordOrderItemProvisioningId = ordOrderItemProvisioning.getId();

        // Get all the ordOrderItemList where ordOrderItemProvisioning equals to ordOrderItemProvisioningId
        defaultOrdOrderItemShouldBeFound("ordOrderItemProvisioningId.equals=" + ordOrderItemProvisioningId);

        // Get all the ordOrderItemList where ordOrderItemProvisioning equals to (ordOrderItemProvisioningId + 1)
        defaultOrdOrderItemShouldNotBeFound("ordOrderItemProvisioningId.equals=" + (ordOrderItemProvisioningId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByOrdOrderItemCharIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        OrdOrderItemChar ordOrderItemChar = OrdOrderItemCharResourceIT.createEntity(em);
        em.persist(ordOrderItemChar);
        em.flush();
        ordOrderItem.addOrdOrderItemChar(ordOrderItemChar);
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        Long ordOrderItemCharId = ordOrderItemChar.getId();

        // Get all the ordOrderItemList where ordOrderItemChar equals to ordOrderItemCharId
        defaultOrdOrderItemShouldBeFound("ordOrderItemCharId.equals=" + ordOrderItemCharId);

        // Get all the ordOrderItemList where ordOrderItemChar equals to (ordOrderItemCharId + 1)
        defaultOrdOrderItemShouldNotBeFound("ordOrderItemCharId.equals=" + (ordOrderItemCharId + 1));
    }

    @Test
    @Transactional
    void getAllOrdOrderItemsByOrdProductOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        OrdProductOrder ordProductOrder = OrdProductOrderResourceIT.createEntity(em);
        em.persist(ordProductOrder);
        em.flush();
        ordOrderItem.setOrdProductOrder(ordProductOrder);
        ordOrderItemRepository.saveAndFlush(ordOrderItem);
        Long ordProductOrderId = ordProductOrder.getId();

        // Get all the ordOrderItemList where ordProductOrder equals to ordProductOrderId
        defaultOrdOrderItemShouldBeFound("ordProductOrderId.equals=" + ordProductOrderId);

        // Get all the ordOrderItemList where ordProductOrder equals to (ordProductOrderId + 1)
        defaultOrdOrderItemShouldNotBeFound("ordProductOrderId.equals=" + (ordProductOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdOrderItemShouldBeFound(String filter) throws Exception {
        restOrdOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].billerId").value(hasItem(DEFAULT_BILLER_ID.intValue())))
            .andExpect(jsonPath("$.[*].fullfillmentId").value(hasItem(DEFAULT_FULLFILLMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].acquisitionId").value(hasItem(DEFAULT_ACQUISITION_ID.intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cartItemId").value(hasItem(DEFAULT_CART_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restOrdOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdOrderItemShouldNotBeFound(String filter) throws Exception {
        restOrdOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdOrderItem() throws Exception {
        // Get the ordOrderItem
        restOrdOrderItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdOrderItem() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();

        // Update the ordOrderItem
        OrdOrderItem updatedOrdOrderItem = ordOrderItemRepository.findById(ordOrderItem.getId()).get();
        // Disconnect from session so that the updates on updatedOrdOrderItem are not directly saved in db
        em.detach(updatedOrdOrderItem);
        updatedOrdOrderItem
            .billerId(UPDATED_BILLER_ID)
            .fullfillmentId(UPDATED_FULLFILLMENT_ID)
            .acquisitionId(UPDATED_ACQUISITION_ID)
            .action(UPDATED_ACTION)
            .state(UPDATED_STATE)
            .quantity(UPDATED_QUANTITY)
            .itemType(UPDATED_ITEM_TYPE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .cartItemId(UPDATED_CART_ITEM_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(updatedOrdOrderItem);

        restOrdOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItem testOrdOrderItem = ordOrderItemList.get(ordOrderItemList.size() - 1);
        assertThat(testOrdOrderItem.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testOrdOrderItem.getFullfillmentId()).isEqualTo(UPDATED_FULLFILLMENT_ID);
        assertThat(testOrdOrderItem.getAcquisitionId()).isEqualTo(UPDATED_ACQUISITION_ID);
        assertThat(testOrdOrderItem.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testOrdOrderItem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testOrdOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrdOrderItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testOrdOrderItem.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
        assertThat(testOrdOrderItem.getCartItemId()).isEqualTo(UPDATED_CART_ITEM_ID);
        assertThat(testOrdOrderItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdOrderItem.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrdOrderItem.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrdOrderItem.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrdOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();
        ordOrderItem.setId(count.incrementAndGet());

        // Create the OrdOrderItem
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordOrderItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();
        ordOrderItem.setId(count.incrementAndGet());

        // Create the OrdOrderItem
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();
        ordOrderItem.setId(count.incrementAndGet());

        // Create the OrdOrderItem
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdOrderItemWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();

        // Update the ordOrderItem using partial update
        OrdOrderItem partialUpdatedOrdOrderItem = new OrdOrderItem();
        partialUpdatedOrdOrderItem.setId(ordOrderItem.getId());

        partialUpdatedOrdOrderItem
            .fullfillmentId(UPDATED_FULLFILLMENT_ID)
            .action(UPDATED_ACTION)
            .state(UPDATED_STATE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION);

        restOrdOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItem testOrdOrderItem = ordOrderItemList.get(ordOrderItemList.size() - 1);
        assertThat(testOrdOrderItem.getBillerId()).isEqualTo(DEFAULT_BILLER_ID);
        assertThat(testOrdOrderItem.getFullfillmentId()).isEqualTo(UPDATED_FULLFILLMENT_ID);
        assertThat(testOrdOrderItem.getAcquisitionId()).isEqualTo(DEFAULT_ACQUISITION_ID);
        assertThat(testOrdOrderItem.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testOrdOrderItem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testOrdOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrdOrderItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testOrdOrderItem.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
        assertThat(testOrdOrderItem.getCartItemId()).isEqualTo(DEFAULT_CART_ITEM_ID);
        assertThat(testOrdOrderItem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrdOrderItem.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrdOrderItem.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrdOrderItem.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrdOrderItemWithPatch() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();

        // Update the ordOrderItem using partial update
        OrdOrderItem partialUpdatedOrdOrderItem = new OrdOrderItem();
        partialUpdatedOrdOrderItem.setId(ordOrderItem.getId());

        partialUpdatedOrdOrderItem
            .billerId(UPDATED_BILLER_ID)
            .fullfillmentId(UPDATED_FULLFILLMENT_ID)
            .acquisitionId(UPDATED_ACQUISITION_ID)
            .action(UPDATED_ACTION)
            .state(UPDATED_STATE)
            .quantity(UPDATED_QUANTITY)
            .itemType(UPDATED_ITEM_TYPE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .cartItemId(UPDATED_CART_ITEM_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restOrdOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdOrderItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
        OrdOrderItem testOrdOrderItem = ordOrderItemList.get(ordOrderItemList.size() - 1);
        assertThat(testOrdOrderItem.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testOrdOrderItem.getFullfillmentId()).isEqualTo(UPDATED_FULLFILLMENT_ID);
        assertThat(testOrdOrderItem.getAcquisitionId()).isEqualTo(UPDATED_ACQUISITION_ID);
        assertThat(testOrdOrderItem.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testOrdOrderItem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testOrdOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrdOrderItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testOrdOrderItem.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
        assertThat(testOrdOrderItem.getCartItemId()).isEqualTo(UPDATED_CART_ITEM_ID);
        assertThat(testOrdOrderItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrdOrderItem.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrdOrderItem.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrdOrderItem.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrdOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();
        ordOrderItem.setId(count.incrementAndGet());

        // Create the OrdOrderItem
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordOrderItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();
        ordOrderItem.setId(count.incrementAndGet());

        // Create the OrdOrderItem
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = ordOrderItemRepository.findAll().size();
        ordOrderItem.setId(count.incrementAndGet());

        // Create the OrdOrderItem
        OrdOrderItemDTO ordOrderItemDTO = ordOrderItemMapper.toDto(ordOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordOrderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdOrderItem in the database
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdOrderItem() throws Exception {
        // Initialize the database
        ordOrderItemRepository.saveAndFlush(ordOrderItem);

        int databaseSizeBeforeDelete = ordOrderItemRepository.findAll().size();

        // Delete the ordOrderItem
        restOrdOrderItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordOrderItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdOrderItem> ordOrderItemList = ordOrderItemRepository.findAll();
        assertThat(ordOrderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
