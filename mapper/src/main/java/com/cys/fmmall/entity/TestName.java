package com.cys.fmmall.entity;

import javax.persistence.*;

@Table(name = "test_name")
public class TestName {
    @Id
    private Integer id;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}