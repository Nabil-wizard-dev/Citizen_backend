package Nabil.Simplice.app.serviceImpl;

import org.springframework.stereotype.Service;
import Nabil.Simplice.app.service.ServiceEtatService;
import Nabil.Simplice.app.entity.ServiceEtat;
import Nabil.Simplice.app.enums.TypeService;
import Nabil.Simplice.app.repository.ServiceHygieneRepository;
import Nabil.Simplice.app.repository.ServiceMunicipalRepository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ServiceEtatServiceImpl implements ServiceEtatService {

    private final ServiceHygieneRepository serviceHygieneRepository;
    private final ServiceMunicipalRepository serviceMunicipalRepository;

    public ServiceEtatServiceImpl(
            ServiceHygieneRepository serviceHygieneRepository,
            ServiceMunicipalRepository serviceMunicipalRepository) {
        this.serviceHygieneRepository = serviceHygieneRepository;
        this.serviceMunicipalRepository = serviceMunicipalRepository;
    }

    @Override
    public List<ServiceEtat> getAllServices() {
        List<ServiceEtat> allServices = new ArrayList<>();
        allServices.addAll(serviceHygieneRepository.findAll());
        allServices.addAll(serviceMunicipalRepository.findAll());
        return allServices;
    }

    @Override
    public Optional<ServiceEtat> getServiceById(Long id) {
        Optional<ServiceEtat> service = serviceHygieneRepository.findById(id).map(s -> (ServiceEtat) s);
        if (service.isEmpty()) {
            service = serviceMunicipalRepository.findById(id).map(s -> (ServiceEtat) s);
        }
        return service;
    }

    @Override
    public List<ServiceEtat> getServicesByType(TypeService type) {
        switch (type) {
            case SERVICE_HYGIENE:
                return serviceHygieneRepository.findAll().stream()
                        .map(s -> (ServiceEtat) s)
                        .collect(Collectors.toList());
            case SERVICE_MUNICIPAL:
                return serviceMunicipalRepository.findAll().stream()
                        .map(s -> (ServiceEtat) s)
                        .collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public ServiceEtat saveService(ServiceEtat service) {
        if (service instanceof Nabil.Simplice.app.entity.ServiceHygiene) {
            return serviceHygieneRepository.save((Nabil.Simplice.app.entity.ServiceHygiene) service);
        } else if (service instanceof Nabil.Simplice.app.entity.ServiceMunicipal) {
            return serviceMunicipalRepository.save((Nabil.Simplice.app.entity.ServiceMunicipal) service);
        }
        throw new IllegalArgumentException("Type de service non supporté");
    }

    @Override
    public void deleteService(Long id) {
        if (serviceHygieneRepository.existsById(id)) {
            serviceHygieneRepository.deleteById(id);
        } else if (serviceMunicipalRepository.existsById(id)) {
            serviceMunicipalRepository.deleteById(id);
        }
    }

    @Override
    public List<ServiceEtat> getServicesByZoneCouverture(String zone) {
        return getAllServices().stream()
                .filter(service -> service.getZoneCouverture() != null &&
                        service.getZoneCouverture().toLowerCase().contains(zone.toLowerCase()))
                .collect(Collectors.toList());
    }
} 