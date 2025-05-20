package ProjetoCrossJoin.Models;

import java.util.UUID;

public class Lead {

    private UUID leadId;
    private Company company;
    private String country;
    private BusinessType businessType;
    private Status status;

    public Lead(Company company, BusinessType businessType) {
        if (company == null){
            throw new IllegalArgumentException("Company cannot be null");
        }

        this.leadId = UUID.randomUUID();
        this.company = company;
        this.country = company.getCountry();
        this.businessType = businessType;
        this.status = Status.DRAFT;
    }

    @Override
    public String toString() {
        return "Lead{" +
                "leadId=" + leadId +
                ", company=" + company +
                ", country='" + country + '\'' +
                ", businessType=" + businessType +
                ", status=" + status +
                '}';
    }

    public UUID getLeadId() {
        return leadId;
    }

    public Company getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }
}
