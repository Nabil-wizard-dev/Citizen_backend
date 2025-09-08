package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.ServiceMunicipalRequest;
import Nabil.Simplice.app.dto.response.ServiceMunicipalResponse;

import java.util.List;
import java.util.Optional;

public interface ServiceMunicipalService {
    List<ServiceMunicipalResponse> getAllServices();
    Optional<ServiceMunicipalResponse> getServiceById(Long id);
    ServiceMunicipalResponse createService(ServiceMunicipalRequest request);
    ServiceMunicipalResponse updateService(Long id, ServiceMunicipalRequest request);
    void deleteService(Long id);
    List<ServiceMunicipalResponse> getServicesByZone(String zone);
    List<ServiceMunicipalResponse> getServicesByType(String typeService);
    boolean isServiceOuvert(Long id);
    List<ServiceMunicipalResponse> getServicesByBudgetSuperieurA(Double budget);
} 