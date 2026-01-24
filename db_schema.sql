
-- =============================================================
-- BASE DE DONNÉES : senegal_sante_db
-- VERSION : 3.0 (Inscription & Sécurité Critique)
-- CIBLE : MySQL / WAMP / XAMPP
-- ARCHITECTURE : Séparation Authentification / Médical
-- =============================================================

CREATE DATABASE IF NOT EXISTS senegal_sante_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE senegal_sante_db;

-- 1. TABLE DES UTILISATEURS (COMPTES D'ACCÈS)
-- Gère uniquement la connexion. L'accès aux données médicales est impossible sans ID ici.
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    -- Identifiant principal (Format Sénégal : +221...)
    phone_number VARCHAR(20) NOT NULL UNIQUE COMMENT 'Identifiant de connexion unique',
    
    -- Optionnel mais unique pour la récupération de compte
    email VARCHAR(100) UNIQUE DEFAULT NULL,
    
    -- Sécurité : Stockage obligatoire du HASH (BCrypt conseillé côté Java)
    password_hash VARCHAR(255) NOT NULL,
    
    -- Rôles pour restreindre les accès (Point 7 du cahier des charges)
    role ENUM('USER', 'FAMILY_HEAD', 'ADMIN') DEFAULT 'USER',
    
    -- Statut du compte pour la vérification OTP
    status ENUM('PENDING', 'ACTIVE', 'BLOCKED') DEFAULT 'PENDING',
    
    -- Audit et Historique (Point 6 du cahier des charges)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL COMMENT 'Pour désactivation sans perte de données',
    
    INDEX idx_user_auth (phone_number, status)
) ENGINE=InnoDB;

-- 2. TABLE DES PROFILS PATIENTS (CARNET DE SANTÉ NUMÉRIQUE)
-- Contient l'identité civile et médicale.
CREATE TABLE IF NOT EXISTS profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT 'Lien vers le compte responsable',
    
    -- Identité Civile (Point 5.1)
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    gender ENUM('M', 'F') NOT NULL,
    
    -- Localisation
    address VARCHAR(255),
    city VARCHAR(100),
    
    -- Données Médicales Sensibles
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'),
    allergies TEXT COMMENT 'Liste des allergies séparées par virgules',
    
    -- Gestion Familiale : permet de distinguer le titulaire du compte des proches
    is_main_profile BOOLEAN DEFAULT FALSE COMMENT 'Vrai si c est le profil du détenteur du compte',
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    
    CONSTRAINT fk_profile_owner FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_full_name (last_name, first_name)
) ENGINE=InnoDB;

-- 3. TABLE DES MÉDICAMENTS & ORDONNANCES
CREATE TABLE IF NOT EXISTS medications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    dosage VARCHAR(100),
    frequency VARCHAR(100), -- ex: 2x/jour
    duration VARCHAR(100),  -- ex: 7 jours
    start_date DATE,
    end_date DATE,
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT fk_med_patient FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 4. TABLE DES RENDEZ-VOUS MÉDICAUX (Point 5.5)
CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    doctor_name VARCHAR(150),
    appointment_date DATETIME NOT NULL,
    location VARCHAR(255),
    status ENUM('SCHEDULED', 'DONE', 'CANCELLED') DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT fk_app_patient FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5. TABLE DES SYMPTÔMES (SUIVI QUOTIDIEN - Point 5.6)
CREATE TABLE IF NOT EXISTS symptoms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    type VARCHAR(100) NOT NULL,
    intensity INT DEFAULT 3 COMMENT 'Echelle de 1 à 5',
    comment TEXT,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT fk_symp_patient FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 6. TABLE DES DOCUMENTS / PHOTOS D'ORDONNANCES (Point 5.3)
CREATE TABLE IF NOT EXISTS prescriptions_docs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    title VARCHAR(200),
    issue_date DATE,
    image_url VARCHAR(255), -- Stockage local ou S3
    ocr_text TEXT COMMENT 'Contenu texte extrait par IA pour recherche',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT fk_doc_patient FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
) ENGINE=InnoDB;
