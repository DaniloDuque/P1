.data
newline: .asciiz "\n"

.text
.globl main
_for_:
addi $sp, $sp, -8
sw $ra, 4($sp)
sw $fp, 0($sp)
move $fp, $sp
.data
_c_: .space 4
.text
.data
_i_: .space 4
.text
start_loop_0:
la $t0, _cotaMax_
lw $s0, 0($t0)
la $t0, _i_
lw $s1, 0($t0)
slt $s2, $s0, $s1
beqz $s2, end_loop_1
.data
_j_: .space 4
.text
start_loop_2:
la $t0, _cotaMax_
lw $s2, 0($t0)
la $t0, _j_
lw $s1, 0($t0)
slt $s0, $s2, $s1
beqz $s0, end_loop_3
la $t0, _j_
lw $s0, 0($t0)
la $t0, _j_
lw $s1, 0($t0)
li $s2, 1
add $s3, $s1, $s2
sw $s3, 0($s0)
la $t0, _c_
lw $s3, 0($t0)
la $t0, _c_
lw $s0, 0($t0)
li $s2, 1
add $s1, $s0, $s2
sw $s1, 0($s3)
j start_loop_2
end_loop_3:
la $t0, _i_
lw $s1, 0($t0)
la $t0, _i_
lw $s3, 0($t0)
la $t0, _inc_
lw $s2, 0($t0)
add $s0, $s3, $s2
sw $s0, 0($s1)
j start_loop_0
end_loop_1:
la $t0, _c_
lw $s0, 0($t0)
move $v0, $s0
j function_epilogue_4
lw $fp, 0($sp)
lw $ra, 4($sp)
addi $sp, $sp, 8
jr $ra
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
j function_epilogue_5
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
j function_epilogue_6
li $s2, 0
move $a0, $s2
li $s2, 10
move $a1, $s2
li $s2, 1
move $a2, $s2
jal _for_
move $v0, null
j function_epilogue_7
li $v0, 10
syscall
