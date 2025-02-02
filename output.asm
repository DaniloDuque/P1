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
li $s2, 4
move $a0, $s2
li $s2, 5
move $a1, $s2
jal _suma_
move $v0, null
j function_epilogue_1
li $s2, 0
move $a0, $s2
li $s2, 10
move $a1, $s2
li $s2, 1
move $a2, $s2
jal _for_
move $v0, null
j function_epilogue_2
li $v0, 10
syscall
