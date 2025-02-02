.data
newline: .asciiz "\n"

.text
.globl main
_suma_:
addiu $sp, $sp, -40
sw $ra, 36($sp)
sw $fp, 32($sp)
sw $s0, 0($sp)
sw $s1, 4($sp)
sw $s2, 8($sp)
sw $s3, 12($sp)
sw $s4, 16($sp)
sw $s5, 20($sp)
sw $s6, 24($sp)
sw $s7, 28($sp)
move $fp, $sp
la $t0, _a_
lw $s0, 0($t0)
la $t0, _b_
lw $s1, 0($t0)
add $s2, $s0, $s1
move $v0, $s2
j function_epilogue_0
lw $s0, 0($sp)
lw $s1, 4($sp)
lw $s2, 8($sp)
lw $s3, 12($sp)
lw $s4, 16($sp)
lw $s5, 20($sp)
lw $s6, 24($sp)
lw $s7, 28($sp)
lw $fp, 32($sp)
lw $ra, 36($sp)
addiu $sp, $sp, 40
jr $ra
main:
.data
int: .space 4
.text
addiu $sp, $sp, -40
sw $t0, 0($sp)
sw $t1, 4($sp)
sw $t2, 8($sp)
sw $t3, 12($sp)
sw $t4, 16($sp)
sw $t5, 20($sp)
sw $t6, 24($sp)
sw $t7, 28($sp)
sw $t8, 32($sp)
sw $t9, 36($sp)
addiu $sp, $sp, -40
sw $t0, 0($sp)
sw $t1, 4($sp)
sw $t2, 8($sp)
sw $t3, 12($sp)
sw $t4, 16($sp)
sw $t5, 20($sp)
sw $t6, 24($sp)
sw $t7, 28($sp)
sw $t8, 32($sp)
sw $t9, 36($sp)
li $s2, 4
move $a0, $s2
jal _suma_
lw $t0, 0($sp)
lw $t1, 4($sp)
lw $t2, 8($sp)
lw $t3, 12($sp)
lw $t4, 16($sp)
lw $t5, 20($sp)
lw $t6, 24($sp)
lw $t7, 28($sp)
lw $t8, 32($sp)
lw $t9, 36($sp)
addiu $sp, $sp, 40
li $s2, 5
move $a1, $s2
jal _suma_
lw $t0, 0($sp)
lw $t1, 4($sp)
lw $t2, 8($sp)
lw $t3, 12($sp)
lw $t4, 16($sp)
lw $t5, 20($sp)
lw $t6, 24($sp)
lw $t7, 28($sp)
lw $t8, 32($sp)
lw $t9, 36($sp)
addiu $sp, $sp, 40
move $v0, null
j function_epilogue_1
li $v0, 10
syscall
