package com.matomaylla.security.model.event;

import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private Integer id;
    private String title;
    private Date start;
    private Date end;
    private String backgroundColor;
    private String textColor;
    private Integer idSieweb;
    private String usuNomPublicacion;
}
