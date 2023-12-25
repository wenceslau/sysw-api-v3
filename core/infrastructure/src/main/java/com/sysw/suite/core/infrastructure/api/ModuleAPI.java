package com.sysw.suite.core.infrastructure.api;

import com.sysw.suite.core.domain.pagination.Pagination;
import com.sysw.suite.core.infrastructure.module.models.CreateModuleRequest;
import com.sysw.suite.core.infrastructure.module.models.ModuleGetResponse;
import com.sysw.suite.core.infrastructure.module.models.UpdateModuleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("modules")
@Tag(name = "Module", description = "Module API")
public interface ModuleAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new module")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Module created"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> createModule(@RequestBody CreateModuleRequest request);

    @GetMapping
    @Operation(summary = "List modules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Module listed"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Pagination<?> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    );

    @GetMapping( value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get module by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Module found"),
            @ApiResponse(responseCode = "404", description = "Module not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ModuleGetResponse getModule(@PathVariable("id") String id);

    @PutMapping( value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update module by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Module updated"),
            @ApiResponse(responseCode = "404", description = "Module not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> updateModule(@PathVariable("id") String id, @RequestBody UpdateModuleRequest request);
}
