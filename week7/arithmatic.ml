let return x = Some x
let bind mx f =
  match mx with
  | None -> None
  | Some x -> f x 

let (>>=) = bind

let div a b = if b = 0 then None else Some (a / b)

(*To make the quare function work with options*)
let square mx = mx >>= fun x -> return (x * x)

(* e.g. square (div 6 2) returns Some 9*)

let lift f mx = mx >>= fun x -> return f x
(* The lift function makes a regular function work with options*)

let cube x = x * x * x

let cube' = lift cube

let add mx my = 
  mx >>= fun x -> 
  my >>= fun y -> 
  return (x + y)

let lift2 f mx my = 
  mx >>= fun x -> 
  my >>= fun y -> 
  return (f x y)

let (+) = lift2 (+)
let (-) = lift2 (-)
let ( * ) = lift2 ( * )
let (/) = lift2 (/)