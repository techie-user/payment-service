package com.finreach.paymentservice.api;

import com.finreach.paymentservice.api.request.AccountRequest;
import com.finreach.paymentservice.api.response.AccountResponse;
import com.finreach.paymentservice.api.response.ErrorResponse;
import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final ModelMapper modelMapper;

    private final AccountService accountService;

    public AccountController(ModelMapper modelMapper, AccountService accountService) {
        this.modelMapper = modelMapper;
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create New Account", notes = "API to create a new account", nickname = "createAccount")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful."), @ApiResponse(code = 400, message = "Bad Request.Please provide valid input."), @ApiResponse(code = 404, message = "Resource not found"), @ApiResponse(code = 405, message = "Method not allowed"), @ApiResponse(code = 500, message = "Unable to process request. Please try after sometime")})
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@ApiParam("Add a new account request") @RequestBody @Valid AccountRequest request) {
        Account account = accountService.addAccountsEntry(convertToDomain(request));
        return new ResponseEntity<>(convertToResponse(account), HttpStatus.CREATED);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful."), @ApiResponse(code = 400, message = "Bad Request.Please provide valid input."), @ApiResponse(code = 404, message = "Resource not found"), @ApiResponse(code = 405, message = "Method not allowed"), @ApiResponse(code = 500, message = "Unable to process request. Please try after sometime")})
    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountResponse> getAccountDetails(@ApiParam("Account ID") @PathVariable("id") String id) {
        Account account = accountService.getAccountDetails(id);
        return ResponseEntity.ok(convertToResponse(account));
    }

    /**
     * A Generic exception handler
     *
     * @param exception Invalid argument exception
     * @return ErrorResponse instance with errror message
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {

        String errorMsg = exception.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse(exception.getMessage());

        return ErrorResponse.builder().message(errorMsg).build();
    }

    private AccountResponse convertToResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }

    private Account convertToDomain(AccountRequest request) {
        TypeMap<AccountRequest, Account> typeMap = modelMapper.getTypeMap(AccountRequest.class, Account.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(AccountRequest.class, Account.class);
            typeMap.addMappings(mapper -> mapper.map(AccountRequest::getBalance, Account::setBalance));
        }
        return typeMap.map(request);
    }
}
