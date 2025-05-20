package ProjetoCrossJoin.Models;

import java.util.UUID;

public class Company {

    private UUID id;
    private String nif;
    private String address;
    private String country;
    private Status status;
    private String stakeholder;
    private String contact;

    public Company(String nif, String address, String country, String stakeholder, String contact) {
        this.id = UUID.randomUUID();
        this.nif = nif;
        this.address = address;
        this.country = country;
        this.stakeholder = stakeholder;
        this.contact = contact;
        this.status = Status.DRAFT;
    }

    public boolean isValid() {
        if ("Portugal".equalsIgnoreCase(this.country) && (nif == null || nif.isBlank())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", nif='" + nif + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", status=" + status +
                ", stakeholder='" + stakeholder + '\'' +
                ", Contact='" + contact + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getNif() {
        return nif;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStakeholder() {
        return stakeholder;
    }

    public String getContact() {
        return contact;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStakeholder(String stakeholder) {
        this.stakeholder = stakeholder;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
