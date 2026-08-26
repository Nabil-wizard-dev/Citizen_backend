package Nabil.Simplice.app.enums;

/**
 * Nature de l'objet vise par une notification.
 *
 * Le front s'en sert pour construire le lien de redirection : le couple
 * (type, entiteId) suffit a ouvrir la bonne page sans appel supplementaire.
 */
public enum TypeNotification {
    SIGNALEMENT,
    SONDAGE,
    ANNONCE,
    TACHE
}
