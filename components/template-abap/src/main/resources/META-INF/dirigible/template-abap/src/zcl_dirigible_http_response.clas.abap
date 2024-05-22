CLASS zcl_dirigible_http_response DEFINITION PUBLIC.
  PUBLIC SECTION.
    CLASS-METHODS println
      IMPORTING
        message_in     TYPE string.
ENDCLASS.

CLASS zcl_dirigible_http_response IMPLEMENTATION.
  METHOD println.
    WRITE '@KERNEL const http = await import("sdk/http");'.
    WRITE '@KERNEL http.response.println(message_in.get());'.
  ENDMETHOD.

ENDCLASS.