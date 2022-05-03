(module 
(type $_sig_i32i32i32 (func (param i32 i32 i32) ))
(type $_sig_i32ri32 (func (param i32) (result i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_ri32 (func (result i32)))
(type $_sig_rf32 (func (result f32)))
(type $_sig_void (func ))
(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))
(import "runtime" "print" (func $printi (type $_sig_i32)))
(import "runtime" "print" (func $printf (param f32)))
(import "runtime" "read" (func $readi (type $_sig_ri32)))
(import "runtime" "read" (func $readf (type $_sig_rf32)))
(memory 2000)
(start $panoramix)
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(func $esPar (param $x i32)(result i32)
(local $temp i32)
(local $localStart i32)
i32.const 4 ;; let this be the stack size needed (params+locals+2)*4
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
;; inicialización de variable sol
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 0
i32.store
;; Obtener valor de variable ya declarada
get_local $x
i32.const 2
i32.rem_s
i32.const 0
i32.eq
if
;; codigo del designador
get_local $localStart
i32.const 0 ;; delta: declaración
i32.add
i32.const 1
i32.store
end
;; Obtener valor de variable ya declarada
get_local $localStart
i32.const 0 ;; delta: variable declarada
i32.add
i32.load
call $freeStack
)
(func $panoramix 
(local $temp i32)
(local $localStart i32)
i32.const 36 ;; let this be the stack size needed (params+locals+2)*4
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
;; inicialización de variable rectas
;; struct 0 del vector rectas
get_local $localStart ;; guardamos el valor para recuperarlo luego
get_local $localStart
i32.const 0 ;; size*i: 16*0
i32.add
set_local $localStart
;; inicialización de variable p1
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 0 ;; delta variable p1
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
;; inicialización de variable p2
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 8 ;; delta variable p2
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
set_local $localStart ;; recuperamos el valor original
;; struct 1 del vector rectas
get_local $localStart ;; guardamos el valor para recuperarlo luego
get_local $localStart
i32.const 16 ;; size*i: 16*1
i32.add
set_local $localStart
;; inicialización de variable p1
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 0 ;; delta variable p1
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
;; inicialización de variable p2
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 8 ;; delta variable p2
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
set_local $localStart ;; recuperamos el valor original
;; struct 2 del vector rectas
get_local $localStart ;; guardamos el valor para recuperarlo luego
get_local $localStart
i32.const 32 ;; size*i: 16*2
i32.add
set_local $localStart
;; inicialización de variable p1
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 0 ;; delta variable p1
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
;; inicialización de variable p2
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 8 ;; delta variable p2
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
set_local $localStart ;; recuperamos el valor original
;; struct 3 del vector rectas
get_local $localStart ;; guardamos el valor para recuperarlo luego
get_local $localStart
i32.const 48 ;; size*i: 16*3
i32.add
set_local $localStart
;; inicialización de variable p1
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 0 ;; delta variable p1
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
;; inicialización de variable p2
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 8 ;; delta variable p2
i32.add
set_local $localStart
;; inicialización de variable x
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
;; inicialización de variable y
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 4
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
set_local $localStart ;; recuperamos el valor original

;; Bucle for
get_local $localStart
i32.const 32
i32.add
i32.const 0
i32.store
(block
(loop
get_local $localStart
i32.const 32
i32.add
i32.load
i32.const 2
i32.lt_s
i32.eqz
br_if 1
;; Cuerpo del for
;; codigo del designador
;; codigo del designador
get_local $localStart
i32.const 0 ;; delta: declaración
i32.add
get_local $localStart
i32.const 32 ;; pos del iterador
i32.add
i32.load
i32.const 16 ;; tamaño del tipo interno que recorremos 
i32.mul
i32.add
i32.const 0 ;; delta interno struct
i32.add
i32.const 0 ;; delta interno struct
i32.add
i32.load ;; load accS
call $printi
;; codigo del designador
;; codigo del designador
get_local $localStart
i32.const 0 ;; delta: declaración
i32.add
get_local $localStart
i32.const 32 ;; pos del iterador
i32.add
i32.load
i32.const 16 ;; tamaño del tipo interno que recorremos 
i32.mul
i32.add
i32.const 8 ;; delta interno struct
i32.add
i32.const 4 ;; delta interno struct
i32.add
i32.load ;; load accS
call $printi
;; Fin del cuerpo del for
get_local $localStart
i32.const 32
i32.add
get_local $localStart
i32.const 32
i32.add
i32.load
i32.const 1
i32.add
i32.store
br 0
)
)
;; codigo designador accA
;; codigo designador operando 1
;; codigo del designador
get_local $localStart
i32.const 0 ;; delta: declaración
i32.add
;; codigo E operando 2
i32.const 0
i32.const 16 ;; tamaño vector
i32.mul
i32.add
i32.const 0 ;; delta interno struct
i32.add
i32.const 0 ;; delta interno struct
i32.add
i32.load ;; load accS
call $esPar
i32.load
if
i32.const 1
call $printi
else
i32.const 0
call $printi
end
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
)