package webdev.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frais")
    private Integer frais;

    @Column(name = "montant")
    private Long montant;

    @Column(name = "commission_etat")
    private Long commissionEtat;

    @Column(name = "commission_systeme")
    private Long commissionSysteme;

    @Column(name = "commission_receveur")
    private Long commissionReceveur;

    @Column(name = "commission_envoyeur")
    private Long commissionEnvoyeur;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private Client clientEnvoyeur;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private Client clientReceveur;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private User userEnvoyer;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private User userReceveur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFrais() {
        return frais;
    }

    public Transaction frais(Integer frais) {
        this.frais = frais;
        return this;
    }

    public void setFrais(Integer frais) {
        this.frais = frais;
    }

    public Long getMontant() {
        return montant;
    }

    public Transaction montant(Long montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public Long getCommissionEtat() {
        return commissionEtat;
    }

    public Transaction commissionEtat(Long commissionEtat) {
        this.commissionEtat = commissionEtat;
        return this;
    }

    public void setCommissionEtat(Long commissionEtat) {
        this.commissionEtat = commissionEtat;
    }

    public Long getCommissionSysteme() {
        return commissionSysteme;
    }

    public Transaction commissionSysteme(Long commissionSysteme) {
        this.commissionSysteme = commissionSysteme;
        return this;
    }

    public void setCommissionSysteme(Long commissionSysteme) {
        this.commissionSysteme = commissionSysteme;
    }

    public Long getCommissionReceveur() {
        return commissionReceveur;
    }

    public Transaction commissionReceveur(Long commissionReceveur) {
        this.commissionReceveur = commissionReceveur;
        return this;
    }

    public void setCommissionReceveur(Long commissionReceveur) {
        this.commissionReceveur = commissionReceveur;
    }

    public Long getCommissionEnvoyeur() {
        return commissionEnvoyeur;
    }

    public Transaction commissionEnvoyeur(Long commissionEnvoyeur) {
        this.commissionEnvoyeur = commissionEnvoyeur;
        return this;
    }

    public void setCommissionEnvoyeur(Long commissionEnvoyeur) {
        this.commissionEnvoyeur = commissionEnvoyeur;
    }

    public Client getClientEnvoyeur() {
        return clientEnvoyeur;
    }

    public Transaction clientEnvoyeur(Client client) {
        this.clientEnvoyeur = client;
        return this;
    }

    public void setClientEnvoyeur(Client client) {
        this.clientEnvoyeur = client;
    }

    public Client getClientReceveur() {
        return clientReceveur;
    }

    public Transaction clientReceveur(Client client) {
        this.clientReceveur = client;
        return this;
    }

    public void setClientReceveur(Client client) {
        this.clientReceveur = client;
    }

    public User getUserEnvoyer() {
        return userEnvoyer;
    }

    public Transaction userEnvoyer(User user) {
        this.userEnvoyer = user;
        return this;
    }

    public void setUserEnvoyer(User user) {
        this.userEnvoyer = user;
    }

    public User getUserReceveur() {
        return userReceveur;
    }

    public Transaction userReceveur(User user) {
        this.userReceveur = user;
        return this;
    }

    public void setUserReceveur(User user) {
        this.userReceveur = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", frais=" + getFrais() +
            ", montant=" + getMontant() +
            ", commissionEtat=" + getCommissionEtat() +
            ", commissionSysteme=" + getCommissionSysteme() +
            ", commissionReceveur=" + getCommissionReceveur() +
            ", commissionEnvoyeur=" + getCommissionEnvoyeur() +
            "}";
    }
}
