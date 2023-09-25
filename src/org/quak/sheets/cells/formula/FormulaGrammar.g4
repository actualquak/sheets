grammar FormulaGrammar;

// ANTLR grammar for parsing formulas
// ANTLR is a great tool - very convinent and easy to use
// The grammar can be regenerated using Ctrl-Shift-G in IDEA; there should
// be a way to do it from the command line as well, but exact procedures
// have not been documented here because it has never been done
// as there has never been a need
// If you find this code confusing, you should look up the ANTLR documentation,
// tutorials and examples, as I don't want to explain parser generators here

// Starting rule - parsing starts here
startRule: formula;

// The rule for a whole formula
formula: '=' numeric_expression EOF;

// A expression whose type is a number
numeric_expression:
    SUM '[' cells (',' cells)* ']' #Sum
    | PROD '[' cells (',' cells)* ']' #Prod
    | MEAN '[' cells (',' cells)* ']' #Mean
    | STDDEV '[' cells (',' cells)* ']' #Stddev
    | MAX '[' cells (',' cells)* ']' #Max
    | MIN '[' cells (',' cells)* ']' #Min
    | SUB '[' numeric_expression ',' numeric_expression ']' #Sub
    | DIV '[' numeric_expression ',' numeric_expression ']' #Div
    | SQRT '[' numeric_expression ']' #Sqrt
    | ROUND '[' numeric_expression ']' #Round
    | ABS '[' numeric_expression ']' #Abs
    | IFGTZ '[' numeric_expression ']'
        THEN '[' numeric_expression ']'
        ELSE '[' numeric_expression ']' #Ifgtz
    | IFLTZ '[' numeric_expression ']'
        THEN '[' numeric_expression ']'
        ELSE '[' numeric_expression ']' #Ifltz
    | IFGTEQZ '[' numeric_expression ']'
        THEN '[' numeric_expression ']'
        ELSE '[' numeric_expression ']' #Ifgteqz
    | IFLTEQZ '[' numeric_expression ']'
        THEN '[' numeric_expression ']'
        ELSE '[' numeric_expression ']' #Iflteqz
    | IFEQZ '[' numeric_expression ']'
        THEN '[' numeric_expression ']'
        ELSE '[' numeric_expression ']' #Ifeqz
    | IFNEQZ '[' numeric_expression ']'
        THEN '[' numeric_expression ']'
        ELSE '[' numeric_expression ']' #Ifneqz
    | NUMBER #Number
    | CELL #Cell
    ;

// An expression whose type is a number or list of cells
cells: CELL ':' CELL #CellRange
    | numeric_expression #Expr
    ;


// However case insensitivity with ANTLR is a bit of a pain - this seems to
// be the best way to do it
SUM: S U M;
PROD: P R O D;
MEAN: M E A N;
STDDEV: S T D D E V;
MAX: M A X;
MIN: M I N;
SUB: S U B;
DIV: D I V;
SQRT: S Q R T;
ROUND: R O U N D;
ABS: A B S;
IFGTZ: I F G T Z;
IFLTZ: I F L T Z;
IFGTEQZ: I F G T E Q Z;
IFLTEQZ: I F L T E Q Z;
IFEQZ: I F E Q Z;
IFNEQZ: I F N E Q Z;
LOOKUP: L O O K U P;
VLOOKUP: V L O O K U P;
THEN: T H E N;
ELSE: E L S E;
CELL: CHAR+ DIGIT+;
NUMBER: SIGN? DIGIT+ FP? | SIGN? FP;
fragment FP: '.' DIGIT+;

fragment A:[aA];
fragment B:[bB];
fragment C:[cC];
fragment D:[dD];
fragment E:[eE];
fragment F:[fF];
fragment G:[gG];
fragment H:[hH];
fragment I:[iI];
fragment J:[jJ];
fragment K:[kK];
fragment L:[lL];
fragment M:[mM];
fragment N:[nN];
fragment O:[oO];
fragment P:[pP];
fragment Q:[qQ];
fragment R:[rR];
fragment S:[sS];
fragment T:[tT];
fragment U:[uU];
fragment V:[vV];
fragment W:[wW];
fragment X:[xX];
fragment Y:[yY];
fragment Z:[zZ];

fragment CHAR: A | B | C | D | E | F | G | H | I | J
    | K | L | M | N | O | P | Q | R | S | T
    | U | V | W | X | Y | Z ;
fragment SIGN: [+-];
fragment DIGIT: [0-9];
// Skip whitespace
WS: [ \t\r\n]+ -> skip;
