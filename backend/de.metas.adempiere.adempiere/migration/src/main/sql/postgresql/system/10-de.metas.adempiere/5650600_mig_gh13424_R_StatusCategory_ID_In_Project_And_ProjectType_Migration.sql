UPDATE c_projecttype SET R_StatusCategory_ID = 540010 WHERE R_StatusCategory_ID IS NULL; -- Seminarstatus
UPDATE c_Project SET R_StatusCategory_ID = 540010 WHERE R_StatusCategory_ID IS NULL; -- Seminarstatus