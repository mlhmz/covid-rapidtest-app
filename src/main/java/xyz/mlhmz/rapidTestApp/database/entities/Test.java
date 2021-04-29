package xyz.mlhmz.rapidTestApp.database.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;
    private Long personId;
    private Date testDate;
    private boolean positive;

    public Test(Long testid, Long personid, Date testDate, boolean positive) {
        this.testId = testid;
        this.personId = personid;
        this.testDate = testDate;
        this.positive = positive;
    }

    public Test() {}

    public Long getTestId() {
        return testId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personid) {
        this.personId = personid;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Override
    public String toString() {
        return "Test{" +
                "testId=" + testId +
                ", personId=" + personId +
                ", testDate=" + testDate +
                ", positive=" + positive +
                '}';
    }
}
