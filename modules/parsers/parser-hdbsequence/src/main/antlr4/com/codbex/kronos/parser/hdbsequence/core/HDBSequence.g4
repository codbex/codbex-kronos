grammar HDBSequence;

hdbSequenceDefinition: property+ ;
property: schema
        | incrementBy
        | startWith
        | maxValue
        | noMaxValue
        | minValue
        | noMinValue
        | cycles
        | resetBy
        | publicc
        | dependsOnTable
        | dependsOnView;
schema: 'schema' EQ STRING SC;
incrementBy: 'increment_by' EQ  INT SC;

startWith: 'start_with' EQ INT SC;
maxValue: 'maxvalue' EQ INT SC;
noMaxValue:'nomaxvalue' EQ BOOLEAN SC;
minValue:'minvalue' EQ INT SC;
noMinValue:'nominvalue' EQ BOOLEAN SC;
cycles:'cycles' EQ BOOLEAN SC;
resetBy:'reset_by' EQ STRING SC;
publicc:'public' EQ BOOLEAN SC;
dependsOnTable: 'depends_on_table' EQ  STRING SC;
dependsOnView:  'depends_on_view'  EQ  STRING SC;

STRING: '"' (~["\\\r\n] | EscapeSequence | WS )* '"';
INT :  SIGNED_INT? [0-9]+;
BOOLEAN: TRUE | FALSE;

TRUE: 'true';
FALSE: 'false';

WS  :   [ \t\\\r\n]+ -> skip ; // toss out whitespace
LB: '[';
RB: ']';
EQ: '=';
SC: ';';
SIGNED_INT: '-';
LINE_COMMENT : '//' ~[\r\n]*  -> skip ; // Match "//" stuff '\n'
COMMENT : '/*' .*? '*/' -> skip ; // Match "/*" stuff "*/"

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' ([0-3]? [0-7])? [0-7]
    | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
    ;
fragment HexDigits
    : HexDigit ((HexDigit | '_')* HexDigit)?
    ;
fragment HexDigit
    : [0-9a-fA-F]
    ;