.data
newline: .asciiz "\n"
_str1_: .asciiz  
_ch_: .byte 0
_b1_: .word 0
_x23_: .byte 0
_var_: .word 0
_x22_: .byte 0
_messi_: .word 0
_fl1_: .float 0
_i_: .word 0
_s1_: .float 0
_miChar_: .byte 0
_arr_: .asciiz 0
_id_: .word 0
_mibool_: .asciiz 0
_var2_: .float 0
_nep_: .float 0

.text
.globl main
_func1_:
addi $sp, $sp, -8
sw $ra, 4($sp)
sw $fp, 0($sp)
move $fp, $sp
.data
.text
.data
.text
.data
.text
_arr_: .space 400
li $s0, 1
sll $t0, $s0, 2
la $t1, _arr_
add $t0, $t1, $t0
lw $s1, 0($t0)
li $s0, 39
sw $s0, 0($s1)
.data
.text
.data
.text
la $t0, _mibool_
lw $s0, 0($t0)
li $s1, 56
li $s2, 8
add $s3, $s1, $s2
li $s2, 3
la $t0, _messi_
lw $s1, 0($t0)
mul $s1, $s2, $s4
slt $s4, $s3, $s1
sw $s4, 0($s0)
.data
.text
start_loop_0:
li $s4, 30
li $s0, 2
mul $s1, $s4, $s0
la $t0, _i_
lw $s0, 0($t0)
slt $s4, $s1, $s0
beqz $s4, end_loop_1
.data
.text
start_loop_2:
.data
float_literal_4: .float 12.2
.text
lwc1 $f0, float_literal_4
mfc1 $s4, $f0
la $t0, _var2_
lw $s0, 0($t0)
sgt $s1, $s4, $s0
li $s0, 12
li $s4, 34
li $s3, 33
add $s2, $s4, $s3
sgt $s3, $s0, $s2
bnez $s1, left_is_true
move $s2, $zero
b end_of_logical_op
left_is_true:
move $s2, $zero
end_of_logical_op:
beqz $s2, end_loop_3
.data
.text
.data
float_literal_5: .float 0.1
.text
lwc1 $f0, float_literal_5
mfc1 $s2, $f0
move $v0, $s2
j function_epilogue_6
j start_loop_2
end_loop_3:
li $s2, 0
la $t0, _var_
lw $s3, 0($t0)
seq $s1, $s2, $s3
beqz $s1, else_7
j end_loop_9
j end_if_8
else_7:
.data
float_literal_10: .float 1.9
.text
lwc1 $f0, float_literal_10
mfc1 $s1, $f0
move $v0, $s1
j function_epilogue_11
end_if_8:
.data
.text
.data
.text
la $t0, _i_
lw $s1, 0($t0)
la $t0, _i_
lw $s3, 0($t0)
li $s2, 1
add $s0, $s3, $s2
sw $s0, 0($s1)
j start_loop_0
end_loop_1:
.data
float_literal_12: .float 1.1
.text
lwc1 $f0, float_literal_12
mfc1 $s0, $f0
move $v0, $s0
j function_epilogue_13
lw $fp, 0($sp)
lw $ra, 4($sp)
addi $sp, $sp, 8
jr $ra
main:
.data
.text
.data
.text
.data
.text
li $v0, 5
syscall
sw $v0, _s1_
la $t0, _b1_
lw $s0, 0($t0)
li $v0, 1
move $a0, $s0
syscall
li $s0, 35
move $v0, $s0
j function_epilogue_14
li $v0, 10
syscall
