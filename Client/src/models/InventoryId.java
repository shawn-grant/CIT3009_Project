package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class InventoryId implements Serializable {

    @Column(name = "product_code")
    private String code;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_modified")
    private Date dateModified;

    public InventoryId() {
    }

    public InventoryId(String code, Date dateModified) {
        setCode(code);
        setDateModified(dateModified);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return "InventoryId{" +
                "code='" + getCode() + '\'' +
                ", dateModified=" + getDateModified() +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
