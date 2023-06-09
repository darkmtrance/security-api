package com.matomaylla.security.model.externalApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalParentResponse {
    public ArrayList<ExternalDataResponse> json;
}
