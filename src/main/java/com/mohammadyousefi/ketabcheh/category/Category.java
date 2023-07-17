package com.mohammadyousefi.ketabcheh.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")

    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_category_id")
    @JsonIgnoreProperties({"categories"})
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @JsonIgnoreProperties({"parentCategory"})
    private List<Category> categories;
}
