package com.study.TestStudy;

public class Study {

    private StudyStatus status = StudyStatus.Draft;

    private int limit;

    public Study(int limit) {
        if(limit < 0) {
            throw new IllegalStateException("limit은 0보다 커야합니다.");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return this.limit;
    }
}
