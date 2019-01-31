package com.gg.proj.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TopicEntity {

    @Id @GeneratedValue
    private Integer id;
}
