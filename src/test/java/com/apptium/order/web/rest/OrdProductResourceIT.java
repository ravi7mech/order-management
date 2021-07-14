package com.apptium.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.order.IntegrationTest;
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.domain.OrdPlace;
import com.apptium.order.domain.OrdProduct;
import com.apptium.order.domain.OrdProductCharacteristics;
import com.apptium.order.repository.OrdProductRepository;
import com.apptium.order.service.criteria.OrdProductCriteria;
import com.apptium.order.service.dto.OrdProductDTO;
import com.apptium.order.service.mapper.OrdProductMapper;
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
 * Integration tests for the {@link OrdProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdProductResourceIT {

    private static final Long DEFAULT_VERSION_ID = 1L;
    private static final Long UPDATED_VERSION_ID = 2L;
    private static final Long SMALLER_VERSION_ID = 1L - 1L;

    private static final Long DEFAULT_VARIATION_ID = 1L;
    private static final Long UPDATED_VARIATION_ID = 2L;
    private static final Long SMALLER_VARIATION_ID = 1L - 1L;

    private static final String DEFAULT_LINE_OF_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_LINE_OF_SERVICE = "BBBBBBBBBB";

    private static final Long DEFAULT_ASSET_ID = 1L;
    private static final Long UPDATED_ASSET_ID = 2L;
    private static final Long SMALLER_ASSET_ID = 1L - 1L;

    private static final Long DEFAULT_SERIAL_NO = 1L;
    private static final Long UPDATED_SERIAL_NO = 2L;
    private static final Long SMALLER_SERIAL_NO = 1L - 1L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ord-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdProductRepository ordProductRepository;

    @Autowired
    private OrdProductMapper ordProductMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdProductMockMvc;

    private OrdProduct ordProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProduct createEntity(EntityManager em) {
        OrdProduct ordProduct = new OrdProduct()
            .versionId(DEFAULT_VERSION_ID)
            .variationId(DEFAULT_VARIATION_ID)
            .lineOfService(DEFAULT_LINE_OF_SERVICE)
            .assetId(DEFAULT_ASSET_ID)
            .serialNo(DEFAULT_SERIAL_NO)
            .name(DEFAULT_NAME);
        return ordProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdProduct createUpdatedEntity(EntityManager em) {
        OrdProduct ordProduct = new OrdProduct()
            .versionId(UPDATED_VERSION_ID)
            .variationId(UPDATED_VARIATION_ID)
            .lineOfService(UPDATED_LINE_OF_SERVICE)
            .assetId(UPDATED_ASSET_ID)
            .serialNo(UPDATED_SERIAL_NO)
            .name(UPDATED_NAME);
        return ordProduct;
    }

    @BeforeEach
    public void initTest() {
        ordProduct = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdProduct() throws Exception {
        int databaseSizeBeforeCreate = ordProductRepository.findAll().size();
        // Create the OrdProduct
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);
        restOrdProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordProductDTO)))
            .andExpect(status().isCreated());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeCreate + 1);
        OrdProduct testOrdProduct = ordProductList.get(ordProductList.size() - 1);
        assertThat(testOrdProduct.getVersionId()).isEqualTo(DEFAULT_VERSION_ID);
        assertThat(testOrdProduct.getVariationId()).isEqualTo(DEFAULT_VARIATION_ID);
        assertThat(testOrdProduct.getLineOfService()).isEqualTo(DEFAULT_LINE_OF_SERVICE);
        assertThat(testOrdProduct.getAssetId()).isEqualTo(DEFAULT_ASSET_ID);
        assertThat(testOrdProduct.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testOrdProduct.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createOrdProductWithExistingId() throws Exception {
        // Create the OrdProduct with an existing ID
        ordProduct.setId(1L);
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);

        int databaseSizeBeforeCreate = ordProductRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdProducts() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList
        restOrdProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].versionId").value(hasItem(DEFAULT_VERSION_ID.intValue())))
            .andExpect(jsonPath("$.[*].variationId").value(hasItem(DEFAULT_VARIATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].lineOfService").value(hasItem(DEFAULT_LINE_OF_SERVICE)))
            .andExpect(jsonPath("$.[*].assetId").value(hasItem(DEFAULT_ASSET_ID.intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getOrdProduct() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get the ordProduct
        restOrdProductMockMvc
            .perform(get(ENTITY_API_URL_ID, ordProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordProduct.getId().intValue()))
            .andExpect(jsonPath("$.versionId").value(DEFAULT_VERSION_ID.intValue()))
            .andExpect(jsonPath("$.variationId").value(DEFAULT_VARIATION_ID.intValue()))
            .andExpect(jsonPath("$.lineOfService").value(DEFAULT_LINE_OF_SERVICE))
            .andExpect(jsonPath("$.assetId").value(DEFAULT_ASSET_ID.intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getOrdProductsByIdFiltering() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        Long id = ordProduct.getId();

        defaultOrdProductShouldBeFound("id.equals=" + id);
        defaultOrdProductShouldNotBeFound("id.notEquals=" + id);

        defaultOrdProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdProductShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdProductShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId equals to DEFAULT_VERSION_ID
        defaultOrdProductShouldBeFound("versionId.equals=" + DEFAULT_VERSION_ID);

        // Get all the ordProductList where versionId equals to UPDATED_VERSION_ID
        defaultOrdProductShouldNotBeFound("versionId.equals=" + UPDATED_VERSION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId not equals to DEFAULT_VERSION_ID
        defaultOrdProductShouldNotBeFound("versionId.notEquals=" + DEFAULT_VERSION_ID);

        // Get all the ordProductList where versionId not equals to UPDATED_VERSION_ID
        defaultOrdProductShouldBeFound("versionId.notEquals=" + UPDATED_VERSION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId in DEFAULT_VERSION_ID or UPDATED_VERSION_ID
        defaultOrdProductShouldBeFound("versionId.in=" + DEFAULT_VERSION_ID + "," + UPDATED_VERSION_ID);

        // Get all the ordProductList where versionId equals to UPDATED_VERSION_ID
        defaultOrdProductShouldNotBeFound("versionId.in=" + UPDATED_VERSION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId is not null
        defaultOrdProductShouldBeFound("versionId.specified=true");

        // Get all the ordProductList where versionId is null
        defaultOrdProductShouldNotBeFound("versionId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId is greater than or equal to DEFAULT_VERSION_ID
        defaultOrdProductShouldBeFound("versionId.greaterThanOrEqual=" + DEFAULT_VERSION_ID);

        // Get all the ordProductList where versionId is greater than or equal to UPDATED_VERSION_ID
        defaultOrdProductShouldNotBeFound("versionId.greaterThanOrEqual=" + UPDATED_VERSION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId is less than or equal to DEFAULT_VERSION_ID
        defaultOrdProductShouldBeFound("versionId.lessThanOrEqual=" + DEFAULT_VERSION_ID);

        // Get all the ordProductList where versionId is less than or equal to SMALLER_VERSION_ID
        defaultOrdProductShouldNotBeFound("versionId.lessThanOrEqual=" + SMALLER_VERSION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId is less than DEFAULT_VERSION_ID
        defaultOrdProductShouldNotBeFound("versionId.lessThan=" + DEFAULT_VERSION_ID);

        // Get all the ordProductList where versionId is less than UPDATED_VERSION_ID
        defaultOrdProductShouldBeFound("versionId.lessThan=" + UPDATED_VERSION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVersionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where versionId is greater than DEFAULT_VERSION_ID
        defaultOrdProductShouldNotBeFound("versionId.greaterThan=" + DEFAULT_VERSION_ID);

        // Get all the ordProductList where versionId is greater than SMALLER_VERSION_ID
        defaultOrdProductShouldBeFound("versionId.greaterThan=" + SMALLER_VERSION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId equals to DEFAULT_VARIATION_ID
        defaultOrdProductShouldBeFound("variationId.equals=" + DEFAULT_VARIATION_ID);

        // Get all the ordProductList where variationId equals to UPDATED_VARIATION_ID
        defaultOrdProductShouldNotBeFound("variationId.equals=" + UPDATED_VARIATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId not equals to DEFAULT_VARIATION_ID
        defaultOrdProductShouldNotBeFound("variationId.notEquals=" + DEFAULT_VARIATION_ID);

        // Get all the ordProductList where variationId not equals to UPDATED_VARIATION_ID
        defaultOrdProductShouldBeFound("variationId.notEquals=" + UPDATED_VARIATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId in DEFAULT_VARIATION_ID or UPDATED_VARIATION_ID
        defaultOrdProductShouldBeFound("variationId.in=" + DEFAULT_VARIATION_ID + "," + UPDATED_VARIATION_ID);

        // Get all the ordProductList where variationId equals to UPDATED_VARIATION_ID
        defaultOrdProductShouldNotBeFound("variationId.in=" + UPDATED_VARIATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId is not null
        defaultOrdProductShouldBeFound("variationId.specified=true");

        // Get all the ordProductList where variationId is null
        defaultOrdProductShouldNotBeFound("variationId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId is greater than or equal to DEFAULT_VARIATION_ID
        defaultOrdProductShouldBeFound("variationId.greaterThanOrEqual=" + DEFAULT_VARIATION_ID);

        // Get all the ordProductList where variationId is greater than or equal to UPDATED_VARIATION_ID
        defaultOrdProductShouldNotBeFound("variationId.greaterThanOrEqual=" + UPDATED_VARIATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId is less than or equal to DEFAULT_VARIATION_ID
        defaultOrdProductShouldBeFound("variationId.lessThanOrEqual=" + DEFAULT_VARIATION_ID);

        // Get all the ordProductList where variationId is less than or equal to SMALLER_VARIATION_ID
        defaultOrdProductShouldNotBeFound("variationId.lessThanOrEqual=" + SMALLER_VARIATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId is less than DEFAULT_VARIATION_ID
        defaultOrdProductShouldNotBeFound("variationId.lessThan=" + DEFAULT_VARIATION_ID);

        // Get all the ordProductList where variationId is less than UPDATED_VARIATION_ID
        defaultOrdProductShouldBeFound("variationId.lessThan=" + UPDATED_VARIATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByVariationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where variationId is greater than DEFAULT_VARIATION_ID
        defaultOrdProductShouldNotBeFound("variationId.greaterThan=" + DEFAULT_VARIATION_ID);

        // Get all the ordProductList where variationId is greater than SMALLER_VARIATION_ID
        defaultOrdProductShouldBeFound("variationId.greaterThan=" + SMALLER_VARIATION_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByLineOfServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where lineOfService equals to DEFAULT_LINE_OF_SERVICE
        defaultOrdProductShouldBeFound("lineOfService.equals=" + DEFAULT_LINE_OF_SERVICE);

        // Get all the ordProductList where lineOfService equals to UPDATED_LINE_OF_SERVICE
        defaultOrdProductShouldNotBeFound("lineOfService.equals=" + UPDATED_LINE_OF_SERVICE);
    }

    @Test
    @Transactional
    void getAllOrdProductsByLineOfServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where lineOfService not equals to DEFAULT_LINE_OF_SERVICE
        defaultOrdProductShouldNotBeFound("lineOfService.notEquals=" + DEFAULT_LINE_OF_SERVICE);

        // Get all the ordProductList where lineOfService not equals to UPDATED_LINE_OF_SERVICE
        defaultOrdProductShouldBeFound("lineOfService.notEquals=" + UPDATED_LINE_OF_SERVICE);
    }

    @Test
    @Transactional
    void getAllOrdProductsByLineOfServiceIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where lineOfService in DEFAULT_LINE_OF_SERVICE or UPDATED_LINE_OF_SERVICE
        defaultOrdProductShouldBeFound("lineOfService.in=" + DEFAULT_LINE_OF_SERVICE + "," + UPDATED_LINE_OF_SERVICE);

        // Get all the ordProductList where lineOfService equals to UPDATED_LINE_OF_SERVICE
        defaultOrdProductShouldNotBeFound("lineOfService.in=" + UPDATED_LINE_OF_SERVICE);
    }

    @Test
    @Transactional
    void getAllOrdProductsByLineOfServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where lineOfService is not null
        defaultOrdProductShouldBeFound("lineOfService.specified=true");

        // Get all the ordProductList where lineOfService is null
        defaultOrdProductShouldNotBeFound("lineOfService.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductsByLineOfServiceContainsSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where lineOfService contains DEFAULT_LINE_OF_SERVICE
        defaultOrdProductShouldBeFound("lineOfService.contains=" + DEFAULT_LINE_OF_SERVICE);

        // Get all the ordProductList where lineOfService contains UPDATED_LINE_OF_SERVICE
        defaultOrdProductShouldNotBeFound("lineOfService.contains=" + UPDATED_LINE_OF_SERVICE);
    }

    @Test
    @Transactional
    void getAllOrdProductsByLineOfServiceNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where lineOfService does not contain DEFAULT_LINE_OF_SERVICE
        defaultOrdProductShouldNotBeFound("lineOfService.doesNotContain=" + DEFAULT_LINE_OF_SERVICE);

        // Get all the ordProductList where lineOfService does not contain UPDATED_LINE_OF_SERVICE
        defaultOrdProductShouldBeFound("lineOfService.doesNotContain=" + UPDATED_LINE_OF_SERVICE);
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId equals to DEFAULT_ASSET_ID
        defaultOrdProductShouldBeFound("assetId.equals=" + DEFAULT_ASSET_ID);

        // Get all the ordProductList where assetId equals to UPDATED_ASSET_ID
        defaultOrdProductShouldNotBeFound("assetId.equals=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId not equals to DEFAULT_ASSET_ID
        defaultOrdProductShouldNotBeFound("assetId.notEquals=" + DEFAULT_ASSET_ID);

        // Get all the ordProductList where assetId not equals to UPDATED_ASSET_ID
        defaultOrdProductShouldBeFound("assetId.notEquals=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId in DEFAULT_ASSET_ID or UPDATED_ASSET_ID
        defaultOrdProductShouldBeFound("assetId.in=" + DEFAULT_ASSET_ID + "," + UPDATED_ASSET_ID);

        // Get all the ordProductList where assetId equals to UPDATED_ASSET_ID
        defaultOrdProductShouldNotBeFound("assetId.in=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId is not null
        defaultOrdProductShouldBeFound("assetId.specified=true");

        // Get all the ordProductList where assetId is null
        defaultOrdProductShouldNotBeFound("assetId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId is greater than or equal to DEFAULT_ASSET_ID
        defaultOrdProductShouldBeFound("assetId.greaterThanOrEqual=" + DEFAULT_ASSET_ID);

        // Get all the ordProductList where assetId is greater than or equal to UPDATED_ASSET_ID
        defaultOrdProductShouldNotBeFound("assetId.greaterThanOrEqual=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId is less than or equal to DEFAULT_ASSET_ID
        defaultOrdProductShouldBeFound("assetId.lessThanOrEqual=" + DEFAULT_ASSET_ID);

        // Get all the ordProductList where assetId is less than or equal to SMALLER_ASSET_ID
        defaultOrdProductShouldNotBeFound("assetId.lessThanOrEqual=" + SMALLER_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId is less than DEFAULT_ASSET_ID
        defaultOrdProductShouldNotBeFound("assetId.lessThan=" + DEFAULT_ASSET_ID);

        // Get all the ordProductList where assetId is less than UPDATED_ASSET_ID
        defaultOrdProductShouldBeFound("assetId.lessThan=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsByAssetIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where assetId is greater than DEFAULT_ASSET_ID
        defaultOrdProductShouldNotBeFound("assetId.greaterThan=" + DEFAULT_ASSET_ID);

        // Get all the ordProductList where assetId is greater than SMALLER_ASSET_ID
        defaultOrdProductShouldBeFound("assetId.greaterThan=" + SMALLER_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo equals to DEFAULT_SERIAL_NO
        defaultOrdProductShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the ordProductList where serialNo equals to UPDATED_SERIAL_NO
        defaultOrdProductShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo not equals to DEFAULT_SERIAL_NO
        defaultOrdProductShouldNotBeFound("serialNo.notEquals=" + DEFAULT_SERIAL_NO);

        // Get all the ordProductList where serialNo not equals to UPDATED_SERIAL_NO
        defaultOrdProductShouldBeFound("serialNo.notEquals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultOrdProductShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the ordProductList where serialNo equals to UPDATED_SERIAL_NO
        defaultOrdProductShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo is not null
        defaultOrdProductShouldBeFound("serialNo.specified=true");

        // Get all the ordProductList where serialNo is null
        defaultOrdProductShouldNotBeFound("serialNo.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo is greater than or equal to DEFAULT_SERIAL_NO
        defaultOrdProductShouldBeFound("serialNo.greaterThanOrEqual=" + DEFAULT_SERIAL_NO);

        // Get all the ordProductList where serialNo is greater than or equal to UPDATED_SERIAL_NO
        defaultOrdProductShouldNotBeFound("serialNo.greaterThanOrEqual=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo is less than or equal to DEFAULT_SERIAL_NO
        defaultOrdProductShouldBeFound("serialNo.lessThanOrEqual=" + DEFAULT_SERIAL_NO);

        // Get all the ordProductList where serialNo is less than or equal to SMALLER_SERIAL_NO
        defaultOrdProductShouldNotBeFound("serialNo.lessThanOrEqual=" + SMALLER_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsLessThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo is less than DEFAULT_SERIAL_NO
        defaultOrdProductShouldNotBeFound("serialNo.lessThan=" + DEFAULT_SERIAL_NO);

        // Get all the ordProductList where serialNo is less than UPDATED_SERIAL_NO
        defaultOrdProductShouldBeFound("serialNo.lessThan=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllOrdProductsBySerialNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where serialNo is greater than DEFAULT_SERIAL_NO
        defaultOrdProductShouldNotBeFound("serialNo.greaterThan=" + DEFAULT_SERIAL_NO);

        // Get all the ordProductList where serialNo is greater than SMALLER_SERIAL_NO
        defaultOrdProductShouldBeFound("serialNo.greaterThan=" + SMALLER_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllOrdProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where name equals to DEFAULT_NAME
        defaultOrdProductShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ordProductList where name equals to UPDATED_NAME
        defaultOrdProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where name not equals to DEFAULT_NAME
        defaultOrdProductShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ordProductList where name not equals to UPDATED_NAME
        defaultOrdProductShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrdProductShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ordProductList where name equals to UPDATED_NAME
        defaultOrdProductShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where name is not null
        defaultOrdProductShouldBeFound("name.specified=true");

        // Get all the ordProductList where name is null
        defaultOrdProductShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrdProductsByNameContainsSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where name contains DEFAULT_NAME
        defaultOrdProductShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ordProductList where name contains UPDATED_NAME
        defaultOrdProductShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        // Get all the ordProductList where name does not contain DEFAULT_NAME
        defaultOrdProductShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ordProductList where name does not contain UPDATED_NAME
        defaultOrdProductShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrdProductsByOrdProductCharacteristicsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);
        OrdProductCharacteristics ordProductCharacteristics = OrdProductCharacteristicsResourceIT.createEntity(em);
        em.persist(ordProductCharacteristics);
        em.flush();
        ordProduct.setOrdProductCharacteristics(ordProductCharacteristics);
        ordProductRepository.saveAndFlush(ordProduct);
        Long ordProductCharacteristicsId = ordProductCharacteristics.getId();

        // Get all the ordProductList where ordProductCharacteristics equals to ordProductCharacteristicsId
        defaultOrdProductShouldBeFound("ordProductCharacteristicsId.equals=" + ordProductCharacteristicsId);

        // Get all the ordProductList where ordProductCharacteristics equals to (ordProductCharacteristicsId + 1)
        defaultOrdProductShouldNotBeFound("ordProductCharacteristicsId.equals=" + (ordProductCharacteristicsId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductsByOrdPlaceIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);
        OrdPlace ordPlace = OrdPlaceResourceIT.createEntity(em);
        em.persist(ordPlace);
        em.flush();
        ordProduct.addOrdPlace(ordPlace);
        ordProductRepository.saveAndFlush(ordProduct);
        Long ordPlaceId = ordPlace.getId();

        // Get all the ordProductList where ordPlace equals to ordPlaceId
        defaultOrdProductShouldBeFound("ordPlaceId.equals=" + ordPlaceId);

        // Get all the ordProductList where ordPlace equals to (ordPlaceId + 1)
        defaultOrdProductShouldNotBeFound("ordPlaceId.equals=" + (ordPlaceId + 1));
    }

    @Test
    @Transactional
    void getAllOrdProductsByOrdOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);
        OrdOrderItem ordOrderItem = OrdOrderItemResourceIT.createEntity(em);
        em.persist(ordOrderItem);
        em.flush();
        ordProduct.setOrdOrderItem(ordOrderItem);
        ordOrderItem.setOrdProduct(ordProduct);
        ordProductRepository.saveAndFlush(ordProduct);
        Long ordOrderItemId = ordOrderItem.getId();

        // Get all the ordProductList where ordOrderItem equals to ordOrderItemId
        defaultOrdProductShouldBeFound("ordOrderItemId.equals=" + ordOrderItemId);

        // Get all the ordProductList where ordOrderItem equals to (ordOrderItemId + 1)
        defaultOrdProductShouldNotBeFound("ordOrderItemId.equals=" + (ordOrderItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdProductShouldBeFound(String filter) throws Exception {
        restOrdProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].versionId").value(hasItem(DEFAULT_VERSION_ID.intValue())))
            .andExpect(jsonPath("$.[*].variationId").value(hasItem(DEFAULT_VARIATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].lineOfService").value(hasItem(DEFAULT_LINE_OF_SERVICE)))
            .andExpect(jsonPath("$.[*].assetId").value(hasItem(DEFAULT_ASSET_ID.intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restOrdProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdProductShouldNotBeFound(String filter) throws Exception {
        restOrdProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrdProduct() throws Exception {
        // Get the ordProduct
        restOrdProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrdProduct() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();

        // Update the ordProduct
        OrdProduct updatedOrdProduct = ordProductRepository.findById(ordProduct.getId()).get();
        // Disconnect from session so that the updates on updatedOrdProduct are not directly saved in db
        em.detach(updatedOrdProduct);
        updatedOrdProduct
            .versionId(UPDATED_VERSION_ID)
            .variationId(UPDATED_VARIATION_ID)
            .lineOfService(UPDATED_LINE_OF_SERVICE)
            .assetId(UPDATED_ASSET_ID)
            .serialNo(UPDATED_SERIAL_NO)
            .name(UPDATED_NAME);
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(updatedOrdProduct);

        restOrdProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
        OrdProduct testOrdProduct = ordProductList.get(ordProductList.size() - 1);
        assertThat(testOrdProduct.getVersionId()).isEqualTo(UPDATED_VERSION_ID);
        assertThat(testOrdProduct.getVariationId()).isEqualTo(UPDATED_VARIATION_ID);
        assertThat(testOrdProduct.getLineOfService()).isEqualTo(UPDATED_LINE_OF_SERVICE);
        assertThat(testOrdProduct.getAssetId()).isEqualTo(UPDATED_ASSET_ID);
        assertThat(testOrdProduct.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testOrdProduct.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOrdProduct() throws Exception {
        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();
        ordProduct.setId(count.incrementAndGet());

        // Create the OrdProduct
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdProduct() throws Exception {
        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();
        ordProduct.setId(count.incrementAndGet());

        // Create the OrdProduct
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdProduct() throws Exception {
        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();
        ordProduct.setId(count.incrementAndGet());

        // Create the OrdProduct
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordProductDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdProductWithPatch() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();

        // Update the ordProduct using partial update
        OrdProduct partialUpdatedOrdProduct = new OrdProduct();
        partialUpdatedOrdProduct.setId(ordProduct.getId());

        partialUpdatedOrdProduct.versionId(UPDATED_VERSION_ID).variationId(UPDATED_VARIATION_ID).lineOfService(UPDATED_LINE_OF_SERVICE);

        restOrdProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProduct))
            )
            .andExpect(status().isOk());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
        OrdProduct testOrdProduct = ordProductList.get(ordProductList.size() - 1);
        assertThat(testOrdProduct.getVersionId()).isEqualTo(UPDATED_VERSION_ID);
        assertThat(testOrdProduct.getVariationId()).isEqualTo(UPDATED_VARIATION_ID);
        assertThat(testOrdProduct.getLineOfService()).isEqualTo(UPDATED_LINE_OF_SERVICE);
        assertThat(testOrdProduct.getAssetId()).isEqualTo(DEFAULT_ASSET_ID);
        assertThat(testOrdProduct.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testOrdProduct.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOrdProductWithPatch() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();

        // Update the ordProduct using partial update
        OrdProduct partialUpdatedOrdProduct = new OrdProduct();
        partialUpdatedOrdProduct.setId(ordProduct.getId());

        partialUpdatedOrdProduct
            .versionId(UPDATED_VERSION_ID)
            .variationId(UPDATED_VARIATION_ID)
            .lineOfService(UPDATED_LINE_OF_SERVICE)
            .assetId(UPDATED_ASSET_ID)
            .serialNo(UPDATED_SERIAL_NO)
            .name(UPDATED_NAME);

        restOrdProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrdProduct))
            )
            .andExpect(status().isOk());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
        OrdProduct testOrdProduct = ordProductList.get(ordProductList.size() - 1);
        assertThat(testOrdProduct.getVersionId()).isEqualTo(UPDATED_VERSION_ID);
        assertThat(testOrdProduct.getVariationId()).isEqualTo(UPDATED_VARIATION_ID);
        assertThat(testOrdProduct.getLineOfService()).isEqualTo(UPDATED_LINE_OF_SERVICE);
        assertThat(testOrdProduct.getAssetId()).isEqualTo(UPDATED_ASSET_ID);
        assertThat(testOrdProduct.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testOrdProduct.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOrdProduct() throws Exception {
        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();
        ordProduct.setId(count.incrementAndGet());

        // Create the OrdProduct
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordProductDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdProduct() throws Exception {
        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();
        ordProduct.setId(count.incrementAndGet());

        // Create the OrdProduct
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdProduct() throws Exception {
        int databaseSizeBeforeUpdate = ordProductRepository.findAll().size();
        ordProduct.setId(count.incrementAndGet());

        // Create the OrdProduct
        OrdProductDTO ordProductDTO = ordProductMapper.toDto(ordProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdProductMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdProduct in the database
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdProduct() throws Exception {
        // Initialize the database
        ordProductRepository.saveAndFlush(ordProduct);

        int databaseSizeBeforeDelete = ordProductRepository.findAll().size();

        // Delete the ordProduct
        restOrdProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordProduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdProduct> ordProductList = ordProductRepository.findAll();
        assertThat(ordProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
