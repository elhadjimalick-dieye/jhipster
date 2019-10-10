package webdev.web.rest;

import webdev.ProjetJhipsterApp;
import webdev.domain.Transaction;
import webdev.repository.TransactionRepository;
import webdev.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static webdev.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = ProjetJhipsterApp.class)
public class TransactionResourceIT {

    private static final Integer DEFAULT_FRAIS = 1;
    private static final Integer UPDATED_FRAIS = 2;
    private static final Integer SMALLER_FRAIS = 1 - 1;

    private static final Long DEFAULT_MONTANT = 1L;
    private static final Long UPDATED_MONTANT = 2L;
    private static final Long SMALLER_MONTANT = 1L - 1L;

    private static final Long DEFAULT_COMMISSION_ETAT = 1L;
    private static final Long UPDATED_COMMISSION_ETAT = 2L;
    private static final Long SMALLER_COMMISSION_ETAT = 1L - 1L;

    private static final Long DEFAULT_COMMISSION_SYSTEME = 1L;
    private static final Long UPDATED_COMMISSION_SYSTEME = 2L;
    private static final Long SMALLER_COMMISSION_SYSTEME = 1L - 1L;

    private static final Long DEFAULT_COMMISSION_RECEVEUR = 1L;
    private static final Long UPDATED_COMMISSION_RECEVEUR = 2L;
    private static final Long SMALLER_COMMISSION_RECEVEUR = 1L - 1L;

    private static final Long DEFAULT_COMMISSION_ENVOYEUR = 1L;
    private static final Long UPDATED_COMMISSION_ENVOYEUR = 2L;
    private static final Long SMALLER_COMMISSION_ENVOYEUR = 1L - 1L;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionResource transactionResource = new TransactionResource(transactionRepository);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .frais(DEFAULT_FRAIS)
            .montant(DEFAULT_MONTANT)
            .commissionEtat(DEFAULT_COMMISSION_ETAT)
            .commissionSysteme(DEFAULT_COMMISSION_SYSTEME)
            .commissionReceveur(DEFAULT_COMMISSION_RECEVEUR)
            .commissionEnvoyeur(DEFAULT_COMMISSION_ENVOYEUR);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .frais(UPDATED_FRAIS)
            .montant(UPDATED_MONTANT)
            .commissionEtat(UPDATED_COMMISSION_ETAT)
            .commissionSysteme(UPDATED_COMMISSION_SYSTEME)
            .commissionReceveur(UPDATED_COMMISSION_RECEVEUR)
            .commissionEnvoyeur(UPDATED_COMMISSION_ENVOYEUR);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getFrais()).isEqualTo(DEFAULT_FRAIS);
        assertThat(testTransaction.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testTransaction.getCommissionEtat()).isEqualTo(DEFAULT_COMMISSION_ETAT);
        assertThat(testTransaction.getCommissionSysteme()).isEqualTo(DEFAULT_COMMISSION_SYSTEME);
        assertThat(testTransaction.getCommissionReceveur()).isEqualTo(DEFAULT_COMMISSION_RECEVEUR);
        assertThat(testTransaction.getCommissionEnvoyeur()).isEqualTo(DEFAULT_COMMISSION_ENVOYEUR);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.intValue())))
            .andExpect(jsonPath("$.[*].commissionEtat").value(hasItem(DEFAULT_COMMISSION_ETAT.intValue())))
            .andExpect(jsonPath("$.[*].commissionSysteme").value(hasItem(DEFAULT_COMMISSION_SYSTEME.intValue())))
            .andExpect(jsonPath("$.[*].commissionReceveur").value(hasItem(DEFAULT_COMMISSION_RECEVEUR.intValue())))
            .andExpect(jsonPath("$.[*].commissionEnvoyeur").value(hasItem(DEFAULT_COMMISSION_ENVOYEUR.intValue())));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.frais").value(DEFAULT_FRAIS))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.intValue()))
            .andExpect(jsonPath("$.commissionEtat").value(DEFAULT_COMMISSION_ETAT.intValue()))
            .andExpect(jsonPath("$.commissionSysteme").value(DEFAULT_COMMISSION_SYSTEME.intValue()))
            .andExpect(jsonPath("$.commissionReceveur").value(DEFAULT_COMMISSION_RECEVEUR.intValue()))
            .andExpect(jsonPath("$.commissionEnvoyeur").value(DEFAULT_COMMISSION_ENVOYEUR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .frais(UPDATED_FRAIS)
            .montant(UPDATED_MONTANT)
            .commissionEtat(UPDATED_COMMISSION_ETAT)
            .commissionSysteme(UPDATED_COMMISSION_SYSTEME)
            .commissionReceveur(UPDATED_COMMISSION_RECEVEUR)
            .commissionEnvoyeur(UPDATED_COMMISSION_ENVOYEUR);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getFrais()).isEqualTo(UPDATED_FRAIS);
        assertThat(testTransaction.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testTransaction.getCommissionEtat()).isEqualTo(UPDATED_COMMISSION_ETAT);
        assertThat(testTransaction.getCommissionSysteme()).isEqualTo(UPDATED_COMMISSION_SYSTEME);
        assertThat(testTransaction.getCommissionReceveur()).isEqualTo(UPDATED_COMMISSION_RECEVEUR);
        assertThat(testTransaction.getCommissionEnvoyeur()).isEqualTo(UPDATED_COMMISSION_ENVOYEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId(2L);
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }
}
