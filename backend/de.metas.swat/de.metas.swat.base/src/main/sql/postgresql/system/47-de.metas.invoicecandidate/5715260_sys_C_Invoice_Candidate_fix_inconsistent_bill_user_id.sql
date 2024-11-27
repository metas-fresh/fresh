
-- this fixes errors like 
-- ERROR: insert or update on table "c_invoice" violates foreign key constraint "aduser_cinvoice"
-- Detail: Key (c_bpartner_id, ad_user_id)=(3328575, 3503890) is not present in table "ad_user".
--
select backup_table('c_invoice_candidate');

-- prepare the fixing-data
create table backup.c_invoice_candidate_fix_bill_user_id_20240112 as
SELECT ic.c_invoice_candidate_id,
       ic.created,
       ic.updated,
       ic.processed,
       ic.bill_bpartner_id,
       ic.bill_user_id,
       bp.name as bp_name,
       u_fixed.ad_user_id as fixed_ad_user_id,
       u_fixed.name as fixed_username
FROM c_invoice_candidate ic
         LEFT JOIN C_BPartner bp ON bp.c_bpartner_id = ic.bill_bpartner_id -- select the partner for clarity
         LEFT JOIN ad_user u ON u.ad_user_id = ic.bill_user_id AND u.c_bpartner_id = ic.bill_bpartner_id
         LEFT JOIN LATERAL (SELECT * -- select an alternative user - if an
                            FROM ad_user u
                            WHERE u.c_bpartner_id = ic.bill_bpartner_id
                            ORDER BY u.IsBillToContact_Default DESC -- prefer the default billTo contact
                            LIMIT 1) u_fixed ON TRUE
WHERE ic.bill_user_id IS NOT NULL
  AND u.ad_user_id IS NULL
;

commit; -- not 100% sure it's needed, but we sometimes have an error about "pending trigger events" when mixing DDL and DML in the same trx.

-- execute the fix
update c_invoice_candidate ic set bill_user_id=data.fixed_ad_user_id, updatedby=99, updated='2024-01-12 11:58'
from  backup.c_invoice_candidate_fix_bill_user_id_20240112 data
WHERE data.c_invoice_candidate_id=ic.c_invoice_candidate_id and data.bill_user_id!=data.fixed_ad_user_id;
