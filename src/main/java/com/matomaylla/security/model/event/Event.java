package com.matomaylla.security.model.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="event")
public class Event {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "title is mandatory")
    private String title;
    private Date startDate;
    private Date endDate;
    private String backgroundColor;
    private String textColor;
}
