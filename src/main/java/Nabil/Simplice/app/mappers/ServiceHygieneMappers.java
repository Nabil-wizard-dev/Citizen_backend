package Nabil.Simplice.app.mappers;

import org.springframework.stereotype.Component;
import Nabil.Simplice.app.entity.ServiceHygiene;
import Nabil.Simplice.app.dto.request.ServiceHygieneRequest;
import Nabil.Simplice.app.dto.response.ServiceHygieneResponse;
import Nabil.Simplice.app.enums.TypeService;

import java.time.LocalTime;

@Component
public class ServiceHygieneMappers {

    public ServiceHygiene requestToEntity(ServiceHygieneRequest request) {
        ServiceHygiene service = new ServiceHygiene();
        service.setNom(request.getNom());
        service.setAdresse(request.getAdresse());
        service.setTelephone(request.getTelephone());
        service.setEmail(request.getEmail());
        service.setHeureOuverture(request.getHeureOuverture());
        service.setHeureFermeture(request.getHeureFermeture());
        service.setDescription(request.getDescription());
        service.setJoursOuverture(request.getJoursOuverture());
        service.setResponsableNom(request.getResponsableNom());
        service.setZoneCouverture(request.getZoneCouverture());
        service.setTypeInspection(request.getTypeInspection());
        service.setFrequenceControle(request.getFrequenceControle());
        service.setEquipementsDisponibles(request.getEquipementsDisponibles());
        service.setCertifications(request.getCertifications());
        if (request.getTypeService() != null) {
            service.setTypeService(TypeService.valueOf(request.getTypeService()));
        }
        return service;
    }

    public ServiceHygieneResponse entityToResponse(ServiceHygiene service) {
        ServiceHygieneResponse response = new ServiceHygieneResponse();
        response.setId(service.getId());
        response.setNom(service.getNom());
        response.setAdresse(service.getAdresse());
        response.setTelephone(service.getTelephone());
        response.setEmail(service.getEmail());
        response.setHeureOuverture(service.getHeureOuverture());
        response.setHeureFermeture(service.getHeureFermeture());
        response.setDescription(service.getDescription());
        response.setJoursOuverture(service.getJoursOuverture());
        response.setResponsableNom(service.getResponsableNom());
        response.setZoneCouverture(service.getZoneCouverture());
        response.setTypeInspection(service.getTypeInspection());
        response.setFrequenceControle(service.getFrequenceControle());
        response.setEquipementsDisponibles(service.getEquipementsDisponibles());
        response.setCertifications(service.getCertifications());
        
        // Vérifier si le service est actuellement ouvert
        response.setEstOuvert(isServiceOuvert(service));
        
        return response;
    }

    private boolean isServiceOuvert(ServiceHygiene service) {
        LocalTime now = LocalTime.now();
        return now.isAfter(service.getHeureOuverture()) && 
               now.isBefore(service.getHeureFermeture());
    }

    public void updateEntityFromRequest(ServiceHygiene service, ServiceHygieneRequest request) {
        service.setNom(request.getNom());
        service.setAdresse(request.getAdresse());
        service.setTelephone(request.getTelephone());
        service.setEmail(request.getEmail());
        service.setHeureOuverture(request.getHeureOuverture());
        service.setHeureFermeture(request.getHeureFermeture());
        service.setDescription(request.getDescription());
        service.setJoursOuverture(request.getJoursOuverture());
        service.setResponsableNom(request.getResponsableNom());
        service.setZoneCouverture(request.getZoneCouverture());
        service.setTypeInspection(request.getTypeInspection());
        service.setFrequenceControle(request.getFrequenceControle());
        service.setEquipementsDisponibles(request.getEquipementsDisponibles());
        service.setCertifications(request.getCertifications());
        if (request.getTypeService() != null) {
            service.setTypeService(TypeService.valueOf(request.getTypeService()));
        }
    }
} 