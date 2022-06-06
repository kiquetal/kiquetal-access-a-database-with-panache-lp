package io.chillplus.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tv_show")
public class TvShow extends PanacheEntity
{


    @NotBlank
    @Column(name = "title")
    public String title;
    @Column(name ="category")
    public String category;
}
