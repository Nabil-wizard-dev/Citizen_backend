package Nabil.Simplice.app.service;

import Nabil.Simplice.app.entity.ServiceHygiene;
import Nabil.Simplice.app.dto.request.ServiceHygieneRequest;
import Nabil.Simplice.app.dto.response.ServiceHygieneResponse;

import java.util.List;
import java.util.Optional;

public interface ServiceHygieneService {
    List<ServiceHygieneResponse> getAllServices();
    Optional<ServiceHygieneResponse> getServiceById(Long id);
    ServiceHygieneResponse createService(ServiceHygieneRequest request);
    ServiceHygieneResponse updateService(Long id, ServiceHygieneRequest request);
    void deleteService(Long id);
    List<ServiceHygieneResponse> getServicesByZone(String zone);
    List<ServiceHygieneResponse> getServicesByTypeInspection(String typeInspection);
    boolean isServiceOuvert(Long id);
} 