package com.inventory.backend.server.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListBarang {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String image;
  private String urlImage;
  private int qty;

  @ManyToOne
  @JoinColumn(name = "idUser")
  private User users;

  @OneToMany(mappedBy = "listbarang")
  private List<Flow> flowList;

  @ManyToOne
  @JoinColumn(name = "idCategory")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "idSuplier")
  private Suplier suplier;
}
