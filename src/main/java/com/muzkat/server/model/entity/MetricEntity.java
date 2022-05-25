package com.muzkat.server.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "metric")
@Schema(title = "Metric entity")
public class MetricEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    @Schema(example = "123")
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    @Schema(example = "Created account")
    private String name;
    @Column(name = "reached_amt", nullable = false)
    @ColumnDefault("0")
    private int reachedAmt;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "countedInMetrics")
    @JsonIgnore
    private Set<UserEntity> countedUsers;
}
