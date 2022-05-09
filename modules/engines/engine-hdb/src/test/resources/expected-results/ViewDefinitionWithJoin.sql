VIEW "DBADMIN"."acme::ViewDefinitionWithJoin.employees_view_with_join" AS SELECT "acme::ViewDefinitionWithJoin.employees"."ID" as "EmployeeId",
          "acme::ViewDefinitionWithJoin.employees"."NAME" as "EmployeeName",
          "ER"."TYPE" as "EmployeeRoleName",
          "ES"."AMOUNT" as "EmployeeSalary" FROM "acme::ViewDefinitionWithJoin.employees" join "acme::ViewDefinitionWithJoin.employee_roles" AS "ER" on "ER"."ID" = "acme::ViewDefinitionWithJoin.employees"."ID" join "acme::ViewDefinitionWithJoin.employee_salaries" AS "ES" on "ES"."ID" = "acme::ViewDefinitionWithJoin.employees"."ID"