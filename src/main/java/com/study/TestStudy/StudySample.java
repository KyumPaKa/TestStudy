package com.study.TestStudy;

public class StudySample {

    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;

    private String name;

    public StudySample(int limit) {
        if(limit < 0) {
            throw new IllegalStateException("limit은 0보다 커야합니다.");
        }
        this.limit = limit;
    }

    public StudySample(int limit, String name) {
        if(limit < 0) {
            throw new IllegalStateException("limit은 0보다 커야합니다.");
        }
        this.limit = limit;
        this.name = name;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return this.limit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
