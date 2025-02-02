.data
newline: .asciiz "\n"

.text
.globl main
_suma_:
addi $sp, $sp, -8
sw $ra, 4($sp)
sw $fp, 0($sp)
move $fp, $sp
la $t0, _a_
lw $s0, 0($t0)
la $t0, _b_
lw $s1, 0($t0)
add $s2, $s0, $s1
move $v0, $s2
j function_epilogue_0
lw $fp, 0($sp)
lw $ra, 4($sp)
addi $sp, $sp, 8
jr $ra
main:
.data
_numero_: .space 4
.text
.data
_char_: .byte 0
.text
la $t0, _numero_
lw $s2, 0($t0)
la $t0, _char_
lw $s1, 0($t0)
add $s0, $s2, $s1
move $v0, $s0
j function_epilogue_1
li $s0, 4
move $a0, $s0
li $s0, 2
move $a1, $s0
jal _suma_
move $a0, null
li $s0, 5
move $a1, $s0
jal _suma_
move $v0, null
j function_epilogue_2
li $v0, 10
syscall
