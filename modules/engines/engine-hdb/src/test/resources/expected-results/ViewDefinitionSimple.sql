VIEW "DBADMIN"."acme::ViewDefinitionSimple.employees_view_basic" AS SELECT "EMP"."ID" as "EmployeeID",
            "EMP"."NAME" as "EmployeeName",
            "EMP"."ADDRESS" as "EmployeeAddress",
            "EMP"."AGE" as "EmployeeAge",
            "EMP"."PHONE" as "EmployeePhone" FROM "acme::ViewDefinitionSimple.employees" AS "EMP"