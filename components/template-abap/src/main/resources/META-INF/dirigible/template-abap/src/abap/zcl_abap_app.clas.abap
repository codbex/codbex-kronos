CLASS zcl_abap_app DEFINITION PUBLIC FINAL CREATE PUBLIC.

  PUBLIC SECTION.
    CLASS-METHODS:
      run.

ENDCLASS.

CLASS zcl_abap_app IMPLEMENTATION.

  " this is the main method called by run.mjs
  METHOD run.
    zcl_codbex_http_response=>println(
      EXPORTING
        message_in = 'Hello world!' ).
  ENDMETHOD.

ENDCLASS.