VIEW "DBADMIN"."acme::ViewDefinitionWithUnion.employees_view_with_union" AS SELECT "acme::ViewDefinitionWithUnion.employees"."ID" as "EmployeeId",
          "acme::ViewDefinitionWithUnion.employees"."NAME" as "EmployeeName",
          "ER"."TYPE" as "EmployeeRoleType",
          "ES"."AMOUNT" as "EmployeeSalary" FROM "acme::ViewDefinitionWithUnion.employees" join "acme::ViewDefinitionWithUnion.employee_roles" AS "ER" on "ER"."ID" = "acme::ViewDefinitionWithUnion.employees"."ID" join "acme::ViewDefinitionWithUnion.employee_salaries" AS "ES" on "ES"."ID" = "acme::ViewDefinitionWithUnion.employees"."ID"  WHERE "acme::ViewDefinitionWithUnion.employees"."NAME" = 'John' UNION SELECT 0 as "EmployeeId",
          'Ben' as "EmployeeName",
          'Developer' as "EmployeeRoleType",
          '2200' as "EmployeeSalary" FROM "DUMMY"