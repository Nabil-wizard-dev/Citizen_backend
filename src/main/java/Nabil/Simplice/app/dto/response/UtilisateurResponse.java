package Nabil.Simplice.app.dto.response;

import Nabil.Simplice.app.enums.UtilisateurRole;

public class UtilisateurResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String numero;
    private UtilisateurRole role;

    public UtilisateurResponse() {
    }

    public UtilisateurResponse(Long id, String nom, String prenom, String email, String numero, UtilisateurRole role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numero = numero;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public UtilisateurRole getRole() {
        return role;
    }

    public void setRole(UtilisateurRole role) {
        this.role = role;
    }

    public static UtilisateurResponseBuilder builder() {
        return new UtilisateurResponseBuilder();
    }

    public static class UtilisateurResponseBuilder {
        private Long id;
        private String nom;
        private String prenom;
        private String email;
        private String numero;
        private UtilisateurRole role;

        public UtilisateurResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UtilisateurResponseBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public UtilisateurResponseBuilder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        public UtilisateurResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UtilisateurResponseBuilder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public UtilisateurResponseBuilder role(UtilisateurRole role) {
            this.role = role;
            return this;
        }

        public UtilisateurResponse build() {
            return new UtilisateurResponse(id, nom, prenom, email, numero, role);
        }
    }
} 