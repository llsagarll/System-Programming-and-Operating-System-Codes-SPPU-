START	200
MOVER	AREG,=’1’
MOVEM	AREG,A
LOOP	MOVER	AREG,A
MOVER	CREG,B
ADD	CREG,=’1’
LTORG
SUB	AREG,=’1’
MULT	CREG,B
MOVEM	CREG,B
A	DS	1
B	DC	‘3’
END
