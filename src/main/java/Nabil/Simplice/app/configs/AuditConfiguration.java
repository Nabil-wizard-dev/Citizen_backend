package Nabil.Simplice.app.configs;

import Nabil.Simplice.app.entity.Utilisateur;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

/**
 * Fournit a l'audit JPA l'identite de l'utilisateur courant.
 *
 * Sans ce bean, les annotations @CreatedBy et @LastModifiedBy de AuditTable ne sont
 * jamais renseignees : les colonnes create_by et modified_by restent nulles sur
 * toutes les tables.
 *
 * On y stocke le trackingId de l'utilisateur, pas son email : un email peut changer,
 * un trackingId non, et il s'expose dans une reponse d'API sans divulguer de donnee
 * personnelle.
 */
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            // requete non authentifiee (/api/auth/**, /Media/**) : le principal est la
            // chaine "anonymousUser", pas un Utilisateur. On ne renseigne alors rien.
            if (!(authentication.getPrincipal() instanceof Utilisateur utilisateur)) {
                return Optional.empty();
            }

            return Optional.ofNullable(utilisateur.getTrackingId()).map(UUID::toString);
        };
    }
}
