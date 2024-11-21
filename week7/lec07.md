# Lecture 7: Monads

## Basic Monad Concepts

Monads are a design pattern that allows us to chain operations while handling computational context (like optionality, multiple values, or state).

1. Two fundamental components:

   ```ocaml
   (* Type constructor M transforms a regular type to a monadic type *)
   (* Example: 'a -> 'a option, or 'a -> 'a list *)
   M : 'a -> 'a t

   (* Return (or unit) wraps a value in the monadic type *)
   (* Example: 5 -> Some 5, or 5 -> [5] *)
   val return : 'a -> 'a t
   ```

2. Bind operator (>>=):
   ```ocaml
   (* Bind chains monadic operations together *)
   (* Takes a monadic value and a function that produces a monadic value *)
   val (>>=) : 'a t -> ('a -> 'b t) -> 'b t
   ```

## Fish Operator (>=>) - Kleisli Composition

The fish operator (>=>) is a way to compose monadic functions directly. It's similar to regular function composition (.), but works with functions that return monadic values.

```ocaml
(* Definition *)
let (>=>) f g x = f x >>= g

(* Type: ('a -> 'b t) -> ('b -> 'c t) -> ('a -> 'c t) *)
(* It takes:
   1. f: a function from 'a to 'b t
   2. g: a function from 'b to 'c t
   Returns: a function from 'a to 'c t *)

(* Example with Maybe monad *)
let safe_div x y = if y = 0 then None else Some (x / y)
let safe_root x = if x < 0 then None else Some (sqrt x)

(* Compose these functions using >=> *)
let safe_div_then_root = safe_div 16 >=> safe_root

(* This is equivalent to: *)
let safe_div_then_root' x =
  safe_div 16 x >>= fun result ->
  safe_root result
```

### Monad Laws with Fish Operator

```ocaml
(* 1. Left identity: return acts as a neutral element on the left *)
return >=> f = f

(* 2. Right identity: return acts as a neutral element on the right *)
f >=> return = f

(* 3. Associativity: composition is associative *)
(f >=> g) >=> h = f >=> (g >=> h)
```

## Common Monads

### Maybe Monad

```ocaml
(* Maybe monad handles computations that might fail *)
let return x = Some x

let bind mx f =
  match mx with
  | None -> None    (* If we have None, propagate the failure *)
  | Some x -> f x   (* If we have a value, apply the function *)

let (>>=) = bind

(* Example usage *)
let safe_div x y = if y = 0 then None else Some (x / y)
let safe_root x = if x < 0 then None else Some (sqrt x)

(* Chain operations that might fail *)
let result =
  safe_div 16 2 >>= fun x ->  (* x = 8 *)
  safe_root x                 (* sqrt(8) *)
```

### List Monad

```ocaml
(* List monad handles multiple possibilities/non-determinism *)
let return x = [x]

(* bind applies f to each element and concatenates results *)
let (>>=) l f = List.concat_map f l

(* Helper function for filtering possibilities *)
let guard cond l = if cond then l else []

(* Example: Find all pairs of numbers that multiply to give n *)
let multiply_to n =
  List.init n ((+) 1) >>= fun x ->    (* Generate numbers 1 to n *)
  List.init n ((+) 1) >>= fun y ->    (* For each x, generate 1 to n *)
  guard (x * y = n) @ (return (x, y)) (* Keep only pairs that multiply to n *)

(* Example usage *)
(* multiply_to 12 would return [(1,12); (2,6); (3,4); (4,3); (6,2); (12,1)] *)
```

### Writer Monad

```ocaml
(* Writer monad combines computations with a log *)
let return x = (x, "")  (* Return value with empty log *)

let (>>=) (x, s) f =
  let (y, s') = f x in  (* Run the computation *)
  (y, s ^ s')           (* Concatenate the logs *)

(* Example operations with logging *)
let inc x = (x + 1, "inc " ^ string_of_int x ^ " ")
let dec x = (x - 1, "dec " ^ string_of_int x ^ " ")

(* Example usage *)
(* This chains several operations while collecting their logs *)
let computation =
  return 1 >>= inc >>= inc >>= dec >>= inc
(* Results in: (2, "inc 1 inc 2 dec 3 inc 2 ") *)
```

### State Monad

```ocaml
(* State monad encapsulates mutable state in a functional way *)
type ('a, 's) t = State of ('s -> 'a * 's)
(* 's is the type of state, 'a is the type of value *)

(* Basic operations *)
let run_state (State f) s = f s      (* Execute a stateful computation *)
let state f = State f                (* Create a new stateful computation *)
let return x = state (fun s -> (x, s)) (* Wrap a value in a stateful computation *)

let (>==) m f =
  State (fun s ->
    let (x, s') = run_state m s in   (* Run first computation *)
    run_state (f x) s')              (* Run second computation with updated state *)

(* Example: Stack implementation *)
type mystack = int list

(* Stack operations *)
let push x s = x::s
let pop s = match s with
  | [] -> failwith "empty stack"
  | x::xs -> xs
let top s = match s with
  | [] -> failwith "empty stack"
  | x::xs -> x

(* Monadic stack operations *)
let pop' = State (fun s -> (top s, pop s))  (* Returns top value and removes it *)
let push' x = State (fun s -> ((), push x s)) (* Adds value to stack *)
let top' = State (fun s -> (top s, s))      (* Just returns top value *)
let (>>) m1 m2 = m1 >== fun _ -> m2        (* Sequence operations, ignore first result *)

(* Example: Complex stack manipulation *)
let ex1 =
  pop' >> pop' >> top' >>= fun x ->  (* Pop twice, look at top *)
  pop' >> top' >>= fun y ->          (* Pop once more, look at top *)
  return (x + y)                     (* Return sum of two values *)
```

The key advantage of monads is that they allow us to:

1. Separate the handling of computational effects (failure, multiple values, state) from the core logic
2. Chain operations in a clean, composable way
3. Reuse common patterns across different types of computations
