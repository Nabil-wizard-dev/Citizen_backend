package Nabil.Simplice.app.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Nabil.Simplice.app.service.ServiceMunicipalService;
import Nabil.Simplice.app.entity.ServiceMunicipal;
import Nabil.Simplice.app.dto.request.ServiceMunicipalRequest;
import Nabil.Simplice.app.dto.response.ServiceMunicipalResponse;
import Nabil.Simplice.app.repository.ServiceMunicipalRepository;
import Nabil.Simplice.app.mappers.ServiceMunicipalMappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ServiceMunicipalServiceImpl implements ServiceMunicipalService {

    private final ServiceMunicipalRepository serviceMunicipalRepository;
    private final ServiceMunicipalMappers serviceMunicipalMappers;

    public ServiceMunicipalServiceImpl(
            ServiceMunicipalRepository serviceMunicipalRepository,
            ServiceMunicipalMappers serviceMunicipalMappers) {
        this.serviceMunicipalRepository = serviceMunicipalRepository;
        this.serviceMunicipalMappers = serviceMunicipalMappers;
    }

    @Override
    public List<ServiceMunicipalResponse> getAllServices() {
        return serviceMunicipalRepository.findAll().stream()
                .map(serviceMunicipalMappers::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceMunicipalResponse> getServiceById(Long id) {
        return serviceMunicipalRepository.findById(id)
                .map(serviceMunicipalMappers::entityToResponse);
    }

    @Override
    @Transactional
    public ServiceMunicipalResponse createService(ServiceMunicipalRequest request) {
        ServiceMunicipal service = serviceMunicipalMappers.requestToEntity(request);
        service = serviceMunicipalRepository.save(service);
        return serviceMunicipalMappers.entityToResponse(service);
    }

    @Override
    @Transactional
    public ServiceMunicipalResponse updateService(Long id, ServiceMunicipalRequest request) {
        ServiceMunicipal service = serviceMunicipalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service municipal non trouvé avec l'ID : " + id));

        serviceMunicipalMappers.updateEntityFromRequest(service, request);
        service = serviceMunicipalRepository.save(service);
        return serviceMunicipalMappers.entityToResponse(service);
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        if (!serviceMunicipalRepository.existsById(id)) {
            throw new EntityNotFoundException("Service municipal non trouvé avec l'ID : " + id);
        }
        serviceMunicipalRepository.deleteById(id);
    }

    @Override
    public List<ServiceMunicipalResponse> getServicesByZone(String zone) {
        return serviceMunicipalRepository.findAll().stream()
                .filter(service -> service.getZoneCouverture() != null &&
                        service.getZoneCouverture().toLowerCase().contains(zone.toLowerCase()))
                .map(serviceMunicipalMappers::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceMunicipalResponse> getServicesByType(String typeService) {
        return serviceMunicipalRepository.findAll().stream()
                .filter(service -> service.getTypeServiceMunicipal() != null &&
                        service.getTypeServiceMunicipal().toLowerCase().contains(typeService.toLowerCase()))
                .map(serviceMunicipalMappers::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isServiceOuvert(Long id) {
        ServiceMunicipal service = serviceMunicipalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service municipal non trouvé avec l'ID : " + id));
        return serviceMunicipalMappers.entityToResponse(service).isEstOuvert();
    }

    @Override
    public List<ServiceMunicipalResponse> getServicesByBudgetSuperieurA(Double budget) {
        return serviceMunicipalRepository.findAll().stream()
                .filter(service -> service.getBudgetAnnuel() != null && service.getBudgetAnnuel() > budget)
                .map(serviceMunicipalMappers::entityToResponse)
                .collect(Collectors.toList());
    }
} 