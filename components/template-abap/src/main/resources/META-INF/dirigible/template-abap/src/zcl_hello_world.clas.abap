CLASS zcl_hello_world DEFINITION PUBLIC FINAL CREATE PUBLIC.
  PUBLIC SECTION.
    CLASS-METHODS run.
ENDCLASS.

CLASS zcl_hello_world IMPLEMENTATION.
  METHOD run.
    zcl_dirigible_http_response=>println(
      EXPORTING
        message_in     = 'Hello World from ABAP' ).
  ENDMETHOD.
ENDCLASS.