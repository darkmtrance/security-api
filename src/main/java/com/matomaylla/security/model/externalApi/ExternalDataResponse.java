package com.matomaylla.security.model.externalApi;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalDataResponse {
    @JsonProperty("ID")
    public int iD;
    @JsonProperty("TITULO")
    public String tITULO;
    @JsonProperty("COLOR")
    public String cOLOR;
    @JsonProperty("TIPO")
    public int tIPO;
    @JsonProperty("TIPOC")
    public int tIPOC;
    @JsonProperty("USUNOM")
    public String uSUNOM;
    @JsonProperty("FI")
    public Date fI;
    @JsonProperty("FECINI")
    public Date fECINI;
    @JsonProperty("FECFIN")
    public Date fECFIN;
    @JsonProperty("PRETITULO")
    public String pRETITULO;
    @JsonProperty("FH_CALENDAR")
    public Date fH_CALENDAR;
    @JsonProperty("FF")
    public Date fF;

}
