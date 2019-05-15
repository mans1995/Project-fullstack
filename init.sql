DROP SCHEMA IF EXISTS paeMobEras CASCADE;
CREATE SCHEMA paeMobEras;

CREATE TABLE paeMobEras.utilisateurs(
	id_utilisateur serial PRIMARY KEY,
	role_util varchar NOT NULL, -- TAILLE = 7
	pseudo varchar NOT NULL, -- TAILLE = 100
	mot_de_passe varchar NOT NULL, -- TAILLE = 100
	nom varchar NOT NULL, -- TAILLE = 100
	prenom varchar NOT NULL, -- TAILLE = 100
	email varchar NOT NULL, -- TAILLE = 100
	no_version integer NOT NULL

);

CREATE TABLE paeMobEras.programmes(
	id_programme serial PRIMARY KEY,
	libelle varchar NOT NULL -- TAILLE = 10
);

CREATE TABLE paeMobEras.pays(
	id_pays serial PRIMARY KEY,
	id_programme integer REFERENCES paeMobEras.programmes (id_programme) NOT NULL,
	nom_pays varchar NOT NULL, -- TAILLE = 50
	code_pays varchar NOT NULL -- TAILLE = 2
);

CREATE TABLE paeMobEras.partenaires(
	id_partenaire serial PRIMARY KEY,
	type_code varchar NOT NULL, -- TAILLE = 3
	nom_legal varchar NOT NULL, -- TAILLE = 100
	nom_affaires varchar NOT NULL, -- TAILLE = 100
	nom_complet varchar NOT NULL, -- TAILLE = 100
	departement varchar NULL, -- TAILLE = 100
	type_organisation varchar, -- TAILLE = 3
	nombre_employes integer NULL,
	adresse varchar NOT NULL, -- TAILLE = 200
	pays integer REFERENCES paeMobEras.pays (id_pays) NOT NULL,
	region varchar NULL, -- TAILLE = 100
	code_postal varchar NOT NULL,
	ville varchar NOT NULL, -- TAILLE = 100
	email varchar NULL, -- TAILLE = 100
	site_web varchar NULL, -- TAILLE = 100
	telephone varchar NULL, -- TAILLE = 32
	no_version integer NOT NULL

);

CREATE TABLE paeMobEras.infos_etudiants(
	id_utilisateur integer REFERENCES paeMobEras.utilisateurs (id_utilisateur) NOT NULL,
	statut varchar NULL, -- TAILLE = 4
	date_naissance timestamp NOT NULL,
	nationalite integer REFERENCES paeMobEras.pays (id_pays) NULL,
	adresse varchar NOT NULL, -- TAILLE = 200
	telephone varchar NOT NULL, -- TAILLE = 32
	sexe char NOT NULL,
	nb_annees_reussies integer NOT NULL,
	iban varchar NOT NULL, -- TAILLE = 34
	titulaire_compte varchar NOT NULL, -- TAILLE = 100
	nom_banque varchar NOT NULL, -- TAILLE = 100
	code_bic varchar NOT NULL, -- TAILLE = 11
	no_version integer NOT NULL,
	PRIMARY KEY(id_utilisateur)
);

CREATE TABLE paeMobEras.mobilites(
	id_mobilite serial PRIMARY KEY,
	id_pays integer REFERENCES paeMobEras.pays (id_pays) NULL,
	id_partenaire integer REFERENCES paeMobEras.partenaires (id_partenaire) NULL,
	id_utilisateur integer REFERENCES paeMobEras.utilisateurs (id_utilisateur) NOT NULL,
	type_code varchar NOT NULL, -- TAILLE = 3
	etat varchar NOT NULL, -- TAILLE = 14
	etat_avant_annulation varchar NULL, -- TAILLE = 14
	raison_annulation varchar NULL, -- TAILLE = 200
	annee_academique varchar NOT NULL, -- TAILLE = 100
	date_introduction timestamp NOT NULL,
	encode_proeco boolean NOT NULL,
	encode_mobi boolean NOT NULL,
--- ajouter les documents PRE/POST ---
-- PRE-Documents --
	pre_contrat_bourse boolean NOT NULL,
	pre_convention_de_stage_etudes boolean NOT NULL,
	pre_charte_etudiant boolean NOT NULL,
	pre_preuve_passage_tests_linguistiques boolean NULL,
	pre_documents_engagement boolean NOT NULL,
-- POST-Documents -- 
	post_attestation_sejour boolean NOT NULL,
	post_releve_notes boolean NULL,
	post_certificat_stage boolean NULL,
	post_rapport_final_complete boolean NOT NULL,
	post_preuve_passage_tests_linguistiques boolean NULL,
	id_programme integer REFERENCES paeMobEras.programmes (id_programme) NOT NULL,
	no_candidature integer NOT NULL,
	quadrimestre varchar NULL, -- TAILLE = 2
	no_version integer NOT NULL
);

-- Insertion des programmes
INSERT INTO paeMobEras.programmes (libelle) VALUES ('Erasmus+');
INSERT INTO paeMobEras.programmes (libelle) VALUES ('Erabel');
INSERT INTO paeMobEras.programmes (libelle) VALUES ('FAME');
INSERT INTO paeMobEras.programmes (libelle) VALUES ('Suisse');

-- Insertion de tous les pays
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Afghanistan', 'AF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Afrique du Sud', 'ZA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Albanie', 'AL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Algerie', 'DZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Allemagne', 'DE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Andorre', 'AD');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Angola', 'AO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Anguilla', 'AI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Antarctique', 'AQ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Antigua et Barbuda', 'AG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Antilles Néerlandaises', 'AN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Arabie Saoudite', 'SA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Argentine', 'AR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Arménie', 'AM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Aruba', 'AW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Australie', 'AU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Autriche', 'AT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Azerbaidjan', 'AZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bahamas', 'BS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bahrein', 'BH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bangladesh', 'BD');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Barbade', 'BB');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bélarus (Biélorussie)', 'BY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('2', 'Belgique', 'BE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Belize', 'BZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bénin', 'BJ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bermudes', 'BM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bhoutan', 'BT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bolivie', 'BO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bosnie et Herzégovine', 'BA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Botswana', 'BW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Bouvet Island', 'BV');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Brésil', 'BR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Brunei', 'BN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Bulgarie', 'BG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Burkina Faso', 'BF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Burundi', 'BI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Cambodge', 'KH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Cameroun', 'CM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Canada', 'CA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Cap Vert', 'CV');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Chili', 'CL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Chine', 'CN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Chypre', 'CY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Cité du Vatican (Holy See)', 'VA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Colombie', 'CO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Comores', 'KM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Congo, République', 'CG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Congo, République Démocratique du', 'CD');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Corée du Nord', 'KP');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Corée du Sud', 'KR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Costa Rica', 'CR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Côte d''Ivoire', 'CI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Croatie', 'HR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Cuba', 'CU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Curacao', 'CW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Danemark', 'DK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Djibouti', 'DJ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Dominique', 'DM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Egypte', 'EG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Emirats Arabes Unis', 'AE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Equateur', 'EC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Erythrée', 'ER');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Espagne', 'ES');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Estonie', 'EE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Etats-Unis', 'US');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Ethiopie', 'ET');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Fidji', 'FJ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Finlande', 'FI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'France', 'FR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'France, Métropolitaine', 'FX');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Gabon', 'GA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Gambie', 'GM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Gaza', 'PS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Géorgie', 'GE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Géorgie du Sud et les îles Sandwich du Sud', 'GS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Ghana', 'GH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Gibraltar', 'GI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Grèce', 'GR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Grenade', 'GD');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Greoenland', 'GL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guadeloupe', 'GP');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guam', 'GU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guatemala', 'GT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guinée', 'GN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guinée Bissau', 'GW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guinée Equatoriale', 'GQ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guyane', 'GY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Guyane Française', 'GF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Haïti', 'HT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Honduras', 'HN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Hong Kong', 'HK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Hongrie', 'HU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Ile de Man', 'IM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Caïman', 'KY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Christmas', 'CX');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Cocos', 'CC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Cook', 'CK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Féroé', 'FO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Guernesey', 'GG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Heardet McDonald', 'HM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Malouines', 'FK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Mariannes du Nord', 'MP');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Marshall', 'MH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Maurice', 'MU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles mineures éloignées des Etats-Unis', 'UM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Norfolk', 'NF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Salomon', 'SB');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Turques et Caïque', 'TC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Vierges des Etats-Unis', 'VI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iles Vierges du Royaume-Uni', 'VG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Inde', 'IN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Indonésie', 'ID');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iran', 'IR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Iraq', 'IQ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Irlande', 'IE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Islande', 'IS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Israël', 'IL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Italie', 'IT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Jamaique', 'JM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Japon', 'JP');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Jersey', 'JE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Jordanie', 'JO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Kazakhstan', 'KZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Kenya', 'KE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Kirghizistan', 'KG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Kiribati', 'KI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Kosovo', 'XK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Koweit', 'KW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Laos', 'LA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Lesotho', 'LS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Lettonie', 'LV');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Liban', 'LB');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Libéria', 'LR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Libye', 'LY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Liechtenstein', 'LI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Lituanie', 'LT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Luxembourg', 'LU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Macao', 'MO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Macédoine', 'MK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Madagascar', 'MG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Malaisie', 'MY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Malawi', 'MW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Maldives', 'MV');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Mali', 'ML');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Malte', 'MT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Maroc', 'MA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Martinique', 'MQ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Mauritanie', 'MR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Mayotte', 'YT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Mexique', 'MX');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Micronésie', 'FM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Moldavie', 'MD');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Monaco', 'MC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Mongolie', 'MN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Monténégro', 'ME');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Montserrat', 'MS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Mozambique', 'MZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Myanmar (Birmanie)', 'MM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Namibie', 'NA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Nauru', 'NR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Népal', 'NP');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Nicaragua', 'NI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Niger', 'NE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Nigeria', 'NG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Niue', 'NU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Norvège', 'NO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Nouvelle Calédonie', 'NC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Nouvelle Zélande', 'NZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Oman', 'OM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Ouganda', 'UG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Ouzbékistan', 'UZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Pakistan', 'PK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Palau', 'PW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Panama', 'PA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Papouasie Nouvelle Guinée', 'PG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Paraguay', 'PY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Pays-Bas', 'NL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Pérou', 'PE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Philippines', 'PH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Pitcairn', 'PN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Pologne', 'PL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Polynésie Française', 'PF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Porto Rico', 'PR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Portugal', 'PT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Qatar', 'QA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'République Centraficaine', 'CF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'République Dominicaine', 'DO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'République Tchèque', 'CZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Réunion', 'RE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Roumanie', 'RO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Royaume Uni', 'GB');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Russie', 'RU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Rwanda', 'RW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Sahara Occidental', 'EH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Saint Barthelemy', 'BL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Saint Hélène', 'SH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Saint Kitts et Nevis', 'KN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Saint Martin', 'MF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Saint Martin', 'SX');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Saint Pierre et Miquelon', 'PM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Saint Vincent et les Grenadines', 'VC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Sainte Lucie', 'LC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Salvador', 'SV');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Samoa Americaines', 'AS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Samoa Occidentales', 'WS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'San Marin', 'SM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Sao Tomé et Principe', 'ST');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Sénégal', 'SN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Serbie', 'RS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Seychelles', 'SC');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Sierra Léone', 'SL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Singapour', 'SG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Slovaquie', 'SK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Slovénie', 'SI');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Somalie', 'SO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Soudan', 'SD');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Sri Lanka', 'LK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Sud Soudan', 'SS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Suède', 'SE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('4', 'Suisse', 'CH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Surinam', 'SR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Svalbard et Jan Mayen', 'SJ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Swaziland', 'SZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Syrie', 'SY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Tadjikistan', 'TJ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Taiwan', 'TW');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Tanzanie', 'TZ');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Tchad', 'TD');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Terres Australes et Antarctique Françaises', 'TF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Térritoire Britannique de l''Océan Indien', 'IO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Territoires Palestiniens occupés (Gaza)', 'PS');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Thaïlande', 'TH');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Timor-Leste', 'TL');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Togo', 'TG');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Tokelau', 'TK');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Tonga', 'TO');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Trinité et Tobago', 'TT');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Tunisie', 'TN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Turkmenistan', 'TM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('1', 'Turquie', 'TR');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Tuvalu', 'TV');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Ukraine', 'UA');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Uruguay', 'UY');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Vanuatu', 'VU');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Venezuela', 'VE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Vietnam', 'VN');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Wallis et Futuna', 'WF');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Yemen', 'YE');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Zambie', 'ZM');
INSERT INTO paeMobEras.pays (id_programme, nom_pays, code_pays) VALUES ('3', 'Zimbabwe', 'ZW');

-- Insertion des partenaires
INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 code_postal, ville, email, site_web, telephone, type_code) 
 VALUES (1, 'UC Leuven-Limburg (Leuven campus)', 'UC Leuven-Limburg (Leuven campus)', 'UC Leuven-Limburg (Leuven campus)',
 'Computer Sciences', 'Abdij van Park 9', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Belgique'),
 '3001', 'Heverlee', 'international@ucll.be', 'https://www.ucll.be/', '+32 (0)16 375 735', 'SMS');

INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 code_postal, ville, email, site_web, telephone, type_code) 
 VALUES (1, 'Technological University Dublin', 'Technological University Dublin', 'Technological University Dublin',
 'Computing', '40- 45 Mount Joy Square', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Irlande'),
 'Dublin 1', 'Dublin', 'erasmus@dit.ie', 'www.dit.ie/computing/', '+35314023404', 'SMS');

INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 code_postal, ville, email, site_web, telephone, type_code) 
 VALUES (1, 'Université du Luxembourg', 'Université du Luxembourg', 'Université du Luxembourg',
 'Computing', '7, avenue des Hauts-Fourneaux', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Luxembourg'), 
 'L-4362', 'Esch-sur-Alzette', 'erasmus@uni.lu', 'https://wwwfr.uni.lu/', '(+352) 46 66 44 4000', 'SMS');

INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 code_postal, ville, email, site_web, telephone, type_code) 
 VALUES (1, 'Wölfel Engineering GmbH', 'Wölfel Engineering GmbH', 'Wölfel Engineering GmbH',
 'Data processing and analytics', 'Max-Planck-Str. 15', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Allemagne'), 
 '97204', 'Höchberg', 'tr@woelfel.de', 'https://www.woelfel.de/home.html', '+49 (931) 49708-168', 'SMP');

INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 code_postal, ville, email, telephone, type_code) 
 VALUES (1, 'HES-SO Haute école spécialisée de Suisse occidentale',
 'HES-SO Haute école spécialisée de Suisse occidentale (Haute école de gestion de Genève (HEG GE))',
 'HES-SO Haute école spécialisée de Suisse occidentale (Haute école de gestion de Genève (HEG GE))',
 'Business Information Systems', '17, Rue de la Tambourine', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Suisse'), 
 '1227', 'Carouge – Genève', 'international@hes-so.ch', '0041 22 388 17 00', 'SMS');

INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 region, code_postal, ville, email, site_web, telephone, type_code) 
 VALUES (1, 'cégep Edouard Montpetit', 'cégep Edouard Montpetit', 'cégep Edouard Montpetit',
 'Techniques de l’informatique', '945 chemin de Chambly', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Canada'), 
 'Québec', 'J4H  3M6', 'Longueuil', 'mobilites@cegepmontpetit.ca', 'http://www.cegepmontpetit.ca/', '450 679-2631', 'SMS');

INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 region, code_postal, ville, email, site_web, telephone, type_code) 
 VALUES (1, 'Collège de Maisonneuve', 'Collège de Maisonneuve', 'Collège de Maisonneuve',
 'Techniques de l’informatique', '3 800, rue Sherbrooke Est', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Canada'), 
 'Québec', 'H1X 2A2', 'Montréal', 'international@cmaisonneuve.qc.ca', 'https://www.cmaisonneuve.qc.ca/', '514 254-7131', 'SMS');

INSERT INTO paeMobEras.partenaires (no_version, nom_legal, nom_affaires, nom_complet, departement, adresse, pays,
 region, code_postal, ville, email, site_web, telephone, type_code) 
 VALUES (1, 'Cégep de Chicoutimi', 'Cégep de Chicoutimi', 'Cégep de Chicoutimi',
 'Techniques de l’informatique', '534 Jacques-Cartier Est', (SELECT id_pays FROM paeMobEras.pays WHERE nom_pays='Canada'), 
 'Québec', 'G7H 1Z6', 'Chicoutimi', 'mobilitesetudiantes@cchic.ca ', 'https://cchic.ca', '418 549.9520  | Poste 1144', 'SMS');