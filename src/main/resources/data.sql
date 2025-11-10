-- -----------------------------------------------------
-- 2. Inserción de los datos del JSON (DML)
-- -----------------------------------------------------

-- 1. Insertamos el usuario.
-- El campo "ID" no se especifica, HSQLDB lo generará automáticamente.
INSERT INTO USERS (ID, NAME, EMAIL, PASSWORD, ISACTIVE, TOKEN)
VALUES ('745e1daf-d860-4157-b8a0-607e7b2d6e9e',
        'Juan Rodriguez',
        'juan@rodriguez.org',
        'hunter2',
        TRUE,
        'a8183030-81f6-11eb-8dc0-0242ac130003');

-- 2. Insertamos el teléfono.
INSERT INTO PHONES (USER_ID, NUMBER, CITY_CODE, COUNTRY_CODE)
VALUES ('745e1daf-d860-4157-b8a0-607e7b2d6e9e',
        '1234567',
        '1',
        '57');

INSERT INTO PHONES (USER_ID, NUMBER, CITY_CODE, COUNTRY_CODE)
VALUES ('745e1daf-d860-4157-b8a0-607e7b2d6e9e',
        '9876543',
        '1',
        '57');