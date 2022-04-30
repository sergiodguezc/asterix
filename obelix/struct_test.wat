(module 
(type $_sig_i32i32i32 (func (param i32 i32 i32) ))
(type $_sig_i32ri32 (func (param i32) (result i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_ri32 (func (result i32)))
(type $_sig_void (func ))
(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))
(import "runtime" "print" (func $print (type $_sig_i32)))
(import "runtime" "read" (func $read (type $_sig_ri32)))
(memory 2000)
(start $panoramix)
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(func $panoramix 
(local $temp i32)
(local $localStart i32)
i32.const 12 ;; let this be the stack size needed (params+locals+2)*4
call $reserveStack  ;; returns old MP (dynamic link)
set_local $temp
get_global $MP
get_local $temp
i32.store
get_global $MP
get_global $SP
i32.store offset=4
get_global $MP
i32.const 8
i32.add
set_local $localStart


 ;; instrucciones 
i32.const 0
get_local $localStart
i32.const 8
i32.store
(block
(loop
i32.const 10
get_local $localStart
i32.const 8
i32.add
i32.load
i32.lt_s
br_if 1
i32.const 1
get_local $localStart
i32.const 8
i32.add
i32.load
i32.add
get_local $localStart
i32.const 8
i32.add
i32.store
br 0
)
)
get_local $localStart
i32.const 8
i32.add
i32.load
call $print
drop
call $freeStack
)
(func $reserveStack (param $size i32)
(result i32)
get_global $MP
get_global $SP
set_global $MP
get_global $SP
get_local $size
i32.add
set_global $SP
get_global $SP
get_global $NP
i32.gt_u
if
i32.const 3
call $exception
end
)
(func $freeStack (type $_sig_void)
get_global $MP
i32.load
i32.load offset=4
set_global $SP
get_global $MP
i32.load
set_global $MP   
)
(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest
(param $src i32)
(param $dest i32)
(param $n i32)
block
loop
get_local $n
i32.eqz
br_if 1
get_local $n
i32.const 1
i32.sub
set_local $n
get_local $dest
get_local $src
i32.load
i32.store
get_local $dest
i32.const 4
i32.add
set_local $dest
get_local $src
i32.const 4
i32.add
set_local $src
br 0
end
end
)
(func $powi (export "powi") (param $x i32) (param $y i32) (result i32) ;; pow of integer
(local $out i32)
(local $index i32)
i32.const 1
set_local $out
i32.const 1
set_local $index
get_local $y
(i32.const 0)
i32.eq
if $i0
  i32.const 1
return
end
 (block $b0
(loop $l0
(i32.mul 
(get_local $out)
(get_local $x)
)
set_local $out
(i32.add 
(get_local $index)
(i32.const 1)
)
tee_local $index
get_local $y
i32.gt_s
br_if 1
br 0
)
)
  get_local $out
)
(func $powf (export "powf") (param $x f32) (param $y i32) (result f32) ;; pow of float
(local $out f32)
(local $index i32)
f32.const 1.0
set_local $out
i32.const 1
set_local $index
get_local $y
(i32.const 0)
i32.eq
if $i0
  f32.const 1.0
return
end
 (block $b0
(loop $l0
(f32.mul 
(get_local $out)
(get_local $x)
)
set_local $out
(i32.add 
(get_local $index)
(i32.const 1)
)
tee_local $index
get_local $y
i32.gt_s
br_if 1
br 0
)
)
  get_local $out
)
)