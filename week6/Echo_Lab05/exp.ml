type 'a lazystream = Cons of 'a * 'a lazystream Lazy.t

let rec from n = Cons(n, lazy(from (n+1)))

let hd (Cons(h,t)) = h
let tl (Cons(h,t)) = Lazy.force t

let rec take n s =
  if n = 0 then []
  else (hd s):: take (n-1) (tl s)

(** [sum l] *)
let sum l =
  List.fold_left (fun acc x -> acc +. x) 0.0 l

(** [unfold f x] return a lazystream of values generated by f *)
let rec unfold f x = 
  let (v, x') = f x in
  Cons(v, lazy(unfold f x'))

(** [fact n] helper function return the factorial of n *)
let rec fact n:float = 
  if n = 0 then 1.0
  else float_of_int n *. fact (n-1)

(** [exp_terms x] return a float lazystream of exponential terms of x*)
let rec exp_terms (x:float):float lazystream = 
  unfold (fun (a,b) -> (a/.(fact b), (a*.x, b+1))) (1.0, 0)

let res =  exp_terms 1.1 |> take 20 |> sum