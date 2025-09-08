package Nabil.Simplice.app.mappers;

import org.springframework.stereotype.Component;
import Nabil.Simplice.app.entity.ServiceMunicipal;
import Nabil.Simplice.app.dto.request.ServiceMunicipalRequest;
import Nabil.Simplice.app.dto.response.ServiceMunicipalResponse;
import Nabil.Simplice.app.enums.TypeService;

import java.time.LocalTime;

@Component
public class ServiceMunicipalMappers {

    public ServiceMunicipal requestToEntity(ServiceMunicipalRequest request) {
        ServiceMunicipal service = new ServiceMunicipal();
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
        if (request.getTypeService() != null) {
            service.setTypeService(TypeService.valueOf(request.getTypeService()));
        }
        service.setBudgetAnnuel(request.getBudgetAnnuel());
        service.setNombrePersonnel(request.getNombrePersonnel());
        service.setServicesSpecifiques(request.getServicesSpecifiques());
        return service;
    }

    public ServiceMunicipalResponse entityToResponse(ServiceMunicipal service) {
        ServiceMunicipalResponse response = new ServiceMunicipalResponse();
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
        response.setTypeServiceMunicipal(service.getTypeServiceMunicipal());
        response.setBudgetAnnuel(service.getBudgetAnnuel());
        response.setNombrePersonnel(service.getNombrePersonnel());
        response.setServicesSpecifiques(service.getServicesSpecifiques());
        
        // Vérifier si le service est actuellement ouvert
        response.setEstOuvert(isServiceOuvert(service));
        
        return response;
    }

    private boolean isServiceOuvert(ServiceMunicipal service) {
        LocalTime now = LocalTime.now();
        return now.isAfter(service.getHeureOuverture()) && 
               now.isBefore(service.getHeureFermeture());
    }

    public void updateEntityFromRequest(ServiceMunicipal service, ServiceMunicipalRequest request) {
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
        if (request.getTypeService() != null) {
            service.setTypeService(TypeService.valueOf(request.getTypeService()));
        }
        service.setBudgetAnnuel(request.getBudgetAnnuel());
        service.setNombrePersonnel(request.getNombrePersonnel());
        service.setServicesSpecifiques(request.getServicesSpecifiques());
    }
} 