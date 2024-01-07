package com.example.rekrutacja.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class Movie extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID uuid;
  private String title;
  private String plot;
  private String genre;
  private String director;
  private String poster;
  private Boolean favourite;

}