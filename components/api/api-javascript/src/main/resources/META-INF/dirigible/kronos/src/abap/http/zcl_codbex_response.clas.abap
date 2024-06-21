CLASS zcl_codbex_response DEFINITION PUBLIC.
  PUBLIC SECTION.
    CLASS-METHODS println
      IMPORTING
        message_in     TYPE any.
ENDCLASS.

CLASS zcl_codbex_response IMPLEMENTATION.
  METHOD println.
    WRITE '@KERNEL const http = await import("sdk/http");'.
    WRITE '@KERNEL const valueString = typeof message_in.value === "string" ? message_in.value : JSON.stringify(message_in.value, null, 2);'.
    WRITE '@KERNEL http.response.println(valueString);'.
  ENDMETHOD.

ENDCLASS.