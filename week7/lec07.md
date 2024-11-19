# Lecture 7: Monads

We define 2 things:

1. A type constructor `M` that takes a type `'a` and returns a type `'a t` with the return function `val return : 'a -> 'a t`.
2. A bind operator `>>=` that takes a value of type `'a t` and a function `'a -> 'b t` and returns a value of type `'b t`. The bind operator is written as `let (>>=) : 'a t -> ('a -> 'b t) -> 'b t`.

The maybe monad is defined as follows (with the type constructor `option`):

```ocaml
let return x = Some x
let bind mx f =
  match mx with
  | None -> None
  | Some x -> f x

let (>>=) = bind
```

Can we have a function works for both `'a list` and `'a option`?

Generics works for different types, but not for different type constructors. We can use monads to abstract over different type constructors.

The fish operator `>=>` is defined as follows:

```ocaml
let (>=>) f g x = f x >>= g
(*So these 3 are equivalent*)
return >=> f
f >=> return
f
（* 3 monad laws *）
(f >=> g) >=> h = f >=> (g >=> h)
```

The list monad is defined as follows:

```ocaml
let return x = [x]

(* bind takes an list, a function takes a' and returns a list of b, and returns a list of b *)
let (>>=) l f =
  List.comcat_map f l

let guard cond l = if cond then l else []
      List.init 5 ((+) 1)

let multiply_to n =
  List.init n ((+) 1) >>= fun x ->
  List.init n ((+) 1) >>= fun y ->
  guard (x * y = n)@@ (return (x, y))
(* equivalent to *)
(* if (x*y) = n then [(x,y)] else [] *)
```

Writer monad: Another example with `'a t = ('a, string) `
Useful for logging

```ocaml
let return x = (x, "")

let (>>=) (x, s) f =
  let (y, s') = f x in
  (y, s ^ s')

let inc x = (x + 1, "inc " ^ string_of_int x ^ " ")
let dec x = (x - 1, "dec " ^ string_of_int x ^ " ")
return 1 >>= inc >>= inc >>= dec >>= inc
(* (2, "inc 1 inc 2 dec 3 inc 2 ") *)

```

We can define writer monad in another way.

State monad: a hidden state that is passed around

```ocaml
type ('a, 's) t = State of ('s -> 'a * 's)
let run_state (State f) s = f s
let state f = State f
let return x = state (fun s -> (x, s))
let (>==) m f =
  State (fun s ->
  let (x, s') = run_state m s in
  run_state (f x) s')

type mystack = int list
let push x s = x::s
let pop s =
  match s with
  | [] -> failwith "empty stack"
  | x::xs -> xs

let top s =
  match s with
  | [] -> failwith "empty stack"
  | x::xs -> x

let pop' = State (fun s -> (top s, pop s))
let push' x = State (fun s -> ((), push x s))
let top' = State (fun s -> (top s, s))
let (>>) m1 m2 = m1 >== fun _ -> m2

let ex1 = pop' >> pop'>> top' >>= funx -> pop' >> top' >>= fun y -> return (x + y)

```
