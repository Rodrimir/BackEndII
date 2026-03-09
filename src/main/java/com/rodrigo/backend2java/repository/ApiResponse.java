package com.fhi.stock_ws.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<I> {
    private boolean success;
    private String message;
    private I data;
}
