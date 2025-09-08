package Nabil.Simplice.app.dto.request;

import java.util.UUID;

import Nabil.Simplice.app.enums.specilateOuvriere;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;

public class OuvrierRequest extends RegisterRequest {
   private UUID signalementActuelId;
	private specilateOuvriere specialite;
	private UUID serviceId;

   public UUID getSignalementActuelId() {
      return signalementActuelId;
   }

   public void setSignalementActuelId(UUID signalementActuelId) {
      this.signalementActuelId = signalementActuelId;
   }

   public specilateOuvriere getSpecialite() {
      return specialite;
   }

   public void setSpecialite(specilateOuvriere specialite) {
      this.specialite = specialite;
   }

   public UUID getServiceId() {
      return serviceId;
   }

   public void setServiceId(UUID serviceId) {
      this.serviceId = serviceId;
   }
}
