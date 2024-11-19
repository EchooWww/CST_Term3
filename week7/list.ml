let return x = [x]

(* bind takes an list, a function takes a' and returns a list of b, and returns a list of b *)
let (>>=) l f =
  List.comcat_map f l

let (let*) = (>>=)

let guard cond l = if cond then l else []
      List.init 5 ((+) 1)

let multiply_to n = 
  List.init n ((+) 1) >>= fun x -> 
  List.init n ((+) 1) >>= fun y -> 
  guard (x * y = n)@@ (return (x, y))
(* equivalent to *)
(* if (x*y) = n then [(x,y)] else [] *)

let multiply_to' n = 
  let* x = List.init n ((+) 1) in
  let* y = List.init n ((+) 1) in
  guard (x * y = n)@@ (return (x, y)) 