package com.matomaylla.security.model.event;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String title;
    private Date start;
    private Date end;
    private String textColor;
    private String url;
    private String backgroundColor;
    private String usuNomPublicacion;
    private Integer idSieweb;
}
