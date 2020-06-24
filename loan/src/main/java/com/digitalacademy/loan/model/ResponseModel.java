package com.digitalacademy.loan.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel implements Serializable {

    private StatusModel status;
    private Object data;

    public ResponseModel() {}

    public ResponseModel(StatusModel status) {
        this.status = status;
    }

    public ResponseModel(StatusModel status, Object data) {
        this.status = status;
        this.data = data;
    }

    public HttpEntity<ResponseModel> build(HttpStatus status) {
        return new ResponseEntity<>(new ResponseModel(this.status, this.data), status);
    }
}
