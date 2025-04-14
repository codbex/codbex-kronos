VIEW "DBADMIN"."gstr2::ViewDefinitionWithCase.employees_view_with_where" AS SELECT "gstr2::ViewDefinitionWithCase.employees"."ID" as "EmployeeId",
          "gstr2::ViewDefinitionWithCase.employees"."NAME" as "EmployeeName",
          "ER"."TYPE" as "EmployeeRoleType",
          "ES"."AMOUNT" as "EmployeeSalary",
          CASE WHEN aTime < '1990-01-01T01:00:00' THEN 0 ELSE 1 END as aFlag,
          cWork / tWork * 100 as percent FROM "gstr2::ViewDefinitionWithCase.employees" join "gstr2::ViewDefinitionWithCase.employee_roles" AS "ER" on "ER"."ID" = "gstr2::ViewDefinitionWithCase.employees"."ID" join "gstr2::ViewDefinitionWithCase.employee_salaries" AS "ES" on "ES"."ID" = "gstr2::ViewDefinitionWithCase.employees"."ID"  WHERE "gstr2::employees"."NAME" = 'John'