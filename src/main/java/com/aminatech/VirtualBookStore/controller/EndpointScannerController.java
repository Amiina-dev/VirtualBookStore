package com.aminatech.VirtualBookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.*;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden // from springdoc-openapi
@RestController
@RequestMapping("/admin") // You can change this path prefix if needed
public class EndpointScannerController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping("/endpoints")
    public ResponseEntity<Map<String, List<String>>> getEndpointsGroupedByMethod() {
        Map<String, List<String>> endpointMap = new HashMap<>();

        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            String methods = requestMappingInfo.getMethodsCondition().toString();
            String patterns = requestMappingInfo.getPatternsCondition().toString();

            methods = methods.replaceAll("[\\[\\]]", "");
            String endpoint = patterns.replaceAll("[\\[\\]]", "");

            endpointMap.computeIfAbsent(methods, k -> new ArrayList<>()).add(endpoint);
        });

        return ResponseEntity.ok(endpointMap);
    }
}