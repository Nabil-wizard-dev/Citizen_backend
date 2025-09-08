package Nabil.Simplice.app.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Nabil.Simplice.app.service.ServiceHygieneService;
import Nabil.Simplice.app.entity.ServiceHygiene;
import Nabil.Simplice.app.dto.request.ServiceHygieneRequest;
import Nabil.Simplice.app.dto.response.ServiceHygieneResponse;
import Nabil.Simplice.app.repository.ServiceHygieneRepository;
import Nabil.Simplice.app.mappers.ServiceHygieneMappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ServiceHygieneServiceImpl implements ServiceHygieneService {

    private final ServiceHygieneRepository serviceHygieneRepository;
    private final ServiceHygieneMappers serviceHygieneMappers;

    public ServiceHygieneServiceImpl(
            ServiceHygieneRepository serviceHygieneRepository,
            ServiceHygieneMappers serviceHygieneMappers) {
        this.serviceHygieneRepository = serviceHygieneRepository;
        this.serviceHygieneMappers = serviceHygieneMappers;
    }

    @Override
    public List<ServiceHygieneResponse> getAllServices() {
        return serviceHygieneRepository.findAll().stream()
                .map(serviceHygieneMappers::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceHygieneResponse> getServiceById(Long id) {
        return serviceHygieneRepository.findById(id)
                .map(serviceHygieneMappers::entityToResponse);
    }

    @Override
    @Transactional
    public ServiceHygieneResponse createService(ServiceHygieneRequest request) {
        ServiceHygiene service = serviceHygieneMappers.requestToEntity(request);
        service = serviceHygieneRepository.save(service);
        return serviceHygieneMappers.entityToResponse(service);
    }

    @Override
    @Transactional
    public ServiceHygieneResponse updateService(Long id, ServiceHygieneRequest request) {
        ServiceHygiene service = serviceHygieneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service d'hygiène non trouvé avec l'ID : " + id));

        serviceHygieneMappers.updateEntityFromRequest(service, request);
        service = serviceHygieneRepository.save(service);
        return serviceHygieneMappers.entityToResponse(service);
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        if (!serviceHygieneRepository.existsById(id)) {
            throw new EntityNotFoundException("Service d'hygiène non trouvé avec l'ID : " + id);
        }
        serviceHygieneRepository.deleteById(id);
    }

    @Override
    public List<ServiceHygieneResponse> getServicesByZone(String zone) {
        return serviceHygieneRepository.findAll().stream()
                .filter(service -> service.getZoneCouverture() != null &&
                        service.getZoneCouverture().toLowerCase().contains(zone.toLowerCase()))
                .map(serviceHygieneMappers::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceHygieneResponse> getServicesByTypeInspection(String typeInspection) {
        return serviceHygieneRepository.findAll().stream()
                .filter(service -> service.getTypeInspection() != null &&
                        service.getTypeInspection().toLowerCase().contains(typeInspection.toLowerCase()))
                .map(serviceHygieneMappers::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isServiceOuvert(Long id) {
        ServiceHygiene service = serviceHygieneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service d'hygiène non trouvé avec l'ID : " + id));
        return serviceHygieneMappers.entityToResponse(service).isEstOuvert();
    }
} 