package no.ntnu.dto;

import java.util.Date;
import java.util.Set;

/**
 * Represents a course entity (model) for tracking which admin created it.
 */
public class Course {
    private String name;
    private String description;
    private int createdBy;
    private String difficultyLevel;
    private Date startDate;
    private Date endDate;
    private double courseCredits;
    private int hoursPerWeek;
    private String relatedCertification;
    private Set<Integer> categoryIds;

    public Course() {
    }

    public Course(String name, String description, int createdBy, String difficultyLevel, Date startDate, Date endDate,
                  double courseCredits, int hoursPerWeek, String relatedCertification, Set<Integer> categoryIds, int imageId) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.difficultyLevel = difficultyLevel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseCredits = courseCredits;
        this.hoursPerWeek = hoursPerWeek;
        this.relatedCertification = relatedCertification;
        this.categoryIds = categoryIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(double courseCredits) {
        this.courseCredits = courseCredits;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public String getRelatedCertification() {
        return relatedCertification;
    }

    public void setRelatedCertification(String relatedCertification) {
        this.relatedCertification = relatedCertification;
    }

    public Set<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
