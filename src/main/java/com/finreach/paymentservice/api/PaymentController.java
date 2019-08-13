package com.finreach.paymentservice.api;

import com.finreach.paymentservice.api.request.PaymentRequest;
import com.finreach.paymentservice.api.response.PaymentResponse;
import com.finreach.paymentservice.domain.Payment;
import com.finreach.paymentservice.exceptions.PaymentServiceException;
import com.finreach.paymentservice.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final ModelMapper modelMapper;

    private final PaymentService paymentService;

    public PaymentController(ModelMapper modelMapper, PaymentService paymentService) {
        this.modelMapper = modelMapper;
        this.paymentService = paymentService;
    }

    @ApiOperation(value = "Get Payment Details for given ID", notes = "API to get payment details", nickname = "getPaymentDetails")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful."), @ApiResponse(code = 400, message = "Bad Request.Please provide valid input."), @ApiResponse(code = 404, message = "Resource not found"), @ApiResponse(code = 405, message = "Method not allowed"), @ApiResponse(code = 500, message = "Unable to process request. Please try after sometime")})
    @GetMapping(path = "/{id}")
    public ResponseEntity<PaymentResponse> getPaymentDetails(@PathVariable String id) throws PaymentServiceException {

        Payment payment = paymentService.getPaymentDetails(id);
        PaymentResponse paymentResponse = modelMapper.map(payment, PaymentResponse.class);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Payments", notes = "API to get payment details", nickname = "getAllPayments")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful."), @ApiResponse(code = 400, message = "Bad Request.Please provide valid input."), @ApiResponse(code = 404, message = "Resource not found"), @ApiResponse(code = 405, message = "Method not allowed"), @ApiResponse(code = 500, message = "Unable to process request. Please try after sometime")})
    @GetMapping(path = "/")
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPayments();
        if (paymentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentList.stream().map(this::convertToResponse).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Create Payment Entry", notes = "API to create a new payment", nickname = "createPayment")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful."), @ApiResponse(code = 400, message = "Bad Request.Please provide valid input."), @ApiResponse(code = 404, message = "Resource not found"), @ApiResponse(code = 405, message = "Method not allowed"), @ApiResponse(code = 500, message = "Unable to process request. Please try after sometime")})
    @PostMapping
    public ResponseEntity<?> createPayment(@ApiParam("paymentRequest") @RequestBody @Valid PaymentRequest request, BindingResult result) throws PaymentServiceException {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getCode).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        return new ResponseEntity<>(paymentService.createPayment(convertToDomain(request)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Execute Payment", notes = "API to execute payment", nickname = "executePayment")

    @PutMapping(path = "/execute/{id}")
    public ResponseEntity<PaymentResponse> execute(@ApiParam(value = "Payment ID", required = true) @PathVariable("id") String id) {
        PaymentResponse response = convertToResponse(paymentService.executePayment(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel Payment", notes = "API to cancel payment", nickname = "cancelPayment")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful."), @ApiResponse(code = 400, message = "Bad Request.Please provide valid input."), @ApiResponse(code = 404, message = "Resource not found"), @ApiResponse(code = 405, message = "Method not allowed"), @ApiResponse(code = 500, message = "Unable to process request. Please try after sometime")})
    @PutMapping(path = "/cancel/{id}")
    public ResponseEntity<?> cancel(@ApiParam(value = "Payment ID", required = true) @PathVariable("id") String id) throws PaymentServiceException {
        return new ResponseEntity<>(paymentService.cancelPayment(id), HttpStatus.OK);
    }

    private PaymentResponse convertToResponse(Payment payment) {
        return modelMapper.map(payment, PaymentResponse.class);
    }

    private Payment convertToDomain(PaymentRequest request) {
        return modelMapper.map(request, Payment.class);
    }
}
