package io.chillplus.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "tv_show")
public class TvShow extends PanacheEntity
{


    @NotBlank
    @Column(name = "title")
    public String title;
    @Column(name ="category")
    public String category;


    public static List<TvShow> findByOrderTille() {

       return TvShow.listAll(Sort.by("title"));

    }
    public static TvShow findByTitle(String title)
    {
        return TvShow.find("title",title).firstResult();
    }
}
