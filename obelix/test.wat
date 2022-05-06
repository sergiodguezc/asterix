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
(start $init)
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(func $init
(local $temp i32)
(local $localStart i32)
i32.const 16 ;; let this be the stack size needed (params+locals+2)*4
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
call $panoramix
call $freeStack
)
(func $changeValue (result i32)
(local $temp i32)
(local $localStart i32)
i32.const 28 ;; let this be the stack size needed (params+locals+2)*4
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
;; instruccion asignacion
;; codigo del designador
get_local $localStart
i32.const 4 ;; delta: variable declarada
i32.add
i32.const 0 ;; delta interno struct
i32.add
;; calculamos el valor a guardar
;; Obtener valor de variable ya declarada
get_local $localStart
i32.const 0 ;; delta: variable declarada
i32.add
i32.load
;; guardamos el valor
i32.store
;; instruccion asignacion
;; asignacion a una lista
;; designador del elemento del vector
get_local $localStart
i32.const 4 ;; delta: variable declarada
i32.add
i32.const 4 ;; delta interno struct
i32.add
i32.const 0
i32.add
;; valor del elemento de la lista a guardar
i32.const 5
;; guardamos el valor
i32.store
;; designador del elemento del vector
get_local $localStart
i32.const 4 ;; delta: variable declarada
i32.add
i32.const 4 ;; delta interno struct
i32.add
i32.const 4
i32.add
;; valor del elemento de la lista a guardar
i32.const 6
;; guardamos el valor
i32.store
;; designador del elemento del vector
get_local $localStart
i32.const 4 ;; delta: variable declarada
i32.add
i32.const 4 ;; delta interno struct
i32.add
i32.const 8
i32.add
;; valor del elemento de la lista a guardar
i32.const 7
;; guardamos el valor
i32.store
;; valor de retorno
get_local $localStart
i32.const 4 ;; delta: variable declarada
i32.add
call $freeStack
)
(func $panoramix 
(local $temp i32)
(local $localStart i32)
i32.const 40 ;; let this be the stack size needed (params+locals+2)*4
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
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 0 ;; delta variable sol
i32.add
set_local $localStart
;; inicialización de variable valor
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 3
i32.store
;; inicialización de variable V
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 1
i32.store
get_local $localStart
i32.const 8 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
get_local $localStart
i32.const 12 ;; offset
i32.add
;; valor a guardar
i32.const 3
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
;; inicialización de variable sol2
get_local $localStart ;; save localStart, at the end we revert this change
get_local $localStart
i32.const 16 ;; delta variable sol2
i32.add
set_local $localStart
;; inicialización de variable valor
get_local $localStart
i32.const 0 ;; offset
i32.add
;; valor a guardar
i32.const 3
i32.store
;; inicialización de variable V
get_local $localStart
i32.const 4 ;; offset
i32.add
;; valor a guardar
i32.const 1
i32.store
get_local $localStart
i32.const 8 ;; offset
i32.add
;; valor a guardar
i32.const 2
i32.store
get_local $localStart
i32.const 12 ;; offset
i32.add
;; valor a guardar
i32.const 3
i32.store
set_local $localStart ;; devolvemos el localStart al valor original
;; instruccion asignacion
;; asignacion a una vector o un struct
;; calculamos el designador ($src)
get_global $SP
i32.const 4
i32.store offset=8
;; codigo del designador
get_local $localStart
i32.const 0 ;; delta: declaración
i32.add
get_global $SP
i32.const 12
i32.add
i32.const 16
call $copyn
call $changeValue
;; calculamos el designador ($dest)
;; codigo del designador
get_local $localStart
i32.const 16 ;; delta: declaración
i32.add
;; calculamos el numero de bytes a copiar
i32.const 16
;; llamamos a copyn
call $copyn

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
i32.const 3
i32.lt_s
i32.eqz
br_if 1
;; Cuerpo del for
;; Obtener valor de variable ya declarada
;; Codigo de la lista que estamos recorriendo
;; codigo del designador
get_local $localStart
i32.const 0 ;; delta: declaración
i32.add
i32.const 4 ;; delta interno struct
i32.add
;; Fin del codigo de la lista que estamos recorriendo
get_local $localStart
i32.const 32 ;; pos del iterador
i32.add
i32.load
i32.const 4 ;; tamaño del tipo interno que recorremos 
i32.mul
i32.add
i32.load
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
;; codigo del designador
get_local $localStart
i32.const 0 ;; delta: declaración
i32.add
i32.const 0 ;; delta interno struct
i32.add
i32.load ;; load accS
call $printi

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
i32.const 3
i32.lt_s
i32.eqz
br_if 1
;; Cuerpo del for
;; Obtener valor de variable ya declarada
;; Codigo de la lista que estamos recorriendo
;; codigo del designador
get_local $localStart
i32.const 16 ;; delta: declaración
i32.add
i32.const 4 ;; delta interno struct
i32.add
;; Fin del codigo de la lista que estamos recorriendo
get_local $localStart
i32.const 32 ;; pos del iterador
i32.add
i32.load
i32.const 4 ;; tamaño del tipo interno que recorremos 
i32.mul
i32.add
i32.load
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
;; codigo del designador
get_local $localStart
i32.const 16 ;; delta: declaración
i32.add
i32.const 0 ;; delta interno struct
i32.add
i32.load ;; load accS
call $printi
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
)