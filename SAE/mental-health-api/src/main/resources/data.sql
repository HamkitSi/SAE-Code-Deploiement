INSERT INTO utilisateur (id, nom, email)
VALUES (100, 'Demo User', 'demo@mindcare.fr')
    ON CONFLICT DO NOTHING;

INSERT INTO profil_bien_etre (id, objectif, rythme_sommeil, utilisateur_id)
VALUES (100, 'Réduire le stress pendant les examens', 'Irrégulier', 100)
    ON CONFLICT DO NOTHING;

INSERT INTO journal_humeur (id, date, humeur, niveau_stress, niveau_energie, note, utilisateur_id)
VALUES (100, '2026-06-27', 'calme', 3, 7, 'Journée plutôt équilibrée', 100)
    ON CONFLICT DO NOTHING;

INSERT INTO categorie (id, nom)
VALUES (100, 'Stress')
    ON CONFLICT DO NOTHING;

INSERT INTO categorie (id, nom)
VALUES (101, 'Sommeil')
    ON CONFLICT DO NOTHING;

INSERT INTO exercice (id, titre, description, duree_minutes)
VALUES (100, 'Respiration 4-7-8', 'Inspirer 4 secondes, retenir 7 secondes, expirer 8 secondes.', 5)
    ON CONFLICT DO NOTHING;

INSERT INTO exercice_categorie (exercice_id, categorie_id)
VALUES (100, 100)
    ON CONFLICT DO NOTHING;

INSERT INTO exercice_categorie (exercice_id, categorie_id)
VALUES (100, 101)
    ON CONFLICT DO NOTHING;