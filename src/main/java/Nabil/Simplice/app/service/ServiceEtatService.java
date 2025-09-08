package Nabil.Simplice.app.service;

import Nabil.Simplice.app.entity.ServiceEtat;
import Nabil.Simplice.app.enums.TypeService;

import java.util.List;
import java.util.Optional;

public interface ServiceEtatService {
    List<ServiceEtat> getAllServices();
    Optional<ServiceEtat> getServiceById(Long id);
    List<ServiceEtat> getServicesByType(TypeService type);
    ServiceEtat saveService(ServiceEtat service);
    void deleteService(Long id);
    List<ServiceEtat> getServicesByZoneCouverture(String zone);
} 