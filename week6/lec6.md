## Infinite Streams

**Note**: An infinite stream allows us to defer evaluation, making it useful for handling unbounded sequences of values.

```ocaml
(* Define an infinite stream type with at least one element. *)
type 'a infstream = Cons of 'a * (unit -> 'a infstream)

(* Generates an infinite stream starting from a given number. *)
let rec from n = Cons (n, fun () -> from (n + 1))

(* Represents the infinite stream of natural numbers starting from 1. *)
let nats = from 1
```

### Basic Stream Operations

```ocaml
(* Extract the head of the stream. *)
let hd (Cons (h, _)) = h

(* Extract the tail of the stream by calling the function. *)
let tl (Cons (_, t)) = t ()

(* Take the first 'n' elements from the stream. *)
let rec take n (Cons (h, t)) =
  if n = 0 then []
  else h :: take (n - 1) (t ())  (* Note: Call t() to get the next part of the stream *)

(* Alternative version of 'take' using 'hd' and 'tl' for clarity. *)
let rec take n s =
  if n <= 0 then []
  else hd s :: take (n - 1) （(tl s)()）

(* Drop the first 'n' elements and return the rest of the stream. *)
let rec drop n (Cons (h, t) as s) =
  if n = 0 then s
  else drop (n - 1) (t ())
```

- Note that hd returns the actual element, while for tl it returns the stream. We also need to call the function when passing it.
- We can destructure the parameter in the function definition itself, as shown in the first `take` and `drop` function.

### Mapping and Filtering

Both should return a new stream, not a list.

```ocaml
let rec map f (Cons (h, t)) = Cons (f h, fun () -> map f (t ()))
let rec filter f (Cons (h, t)) =
  if f h then Cons (h, fun () -> filter f (t ()))
  else filter f (t ())
(* map function which takes a binary function and two streams *)
let rec map2 f (Cons (h1, t1)) (Cons (h2, t2)) =
  Cons (f h1 h2, fun () -> map2 f (t1 ()) (t2 ()))
```

### Fibonacci Sequence

We can define the Fibonacci sequence using infinite streams.

1. One way is using map2: a fibonacci number is the sum of the previous two numbers, so basically a stream add its own tail will give us the next number.

```ocaml

let sum = map2 (+)
let rec fib = Cons (0, fun () -> Cons (1, fun () -> sum fib (tl fib)))
```

2. Another way is using unfold: we can define a function that generates the next element of the stream based on the previous two elements.

```ocaml
(* General form of unfold. In application, we fan pass init with different types as we nedeed. *)
let rec unfold f x =
  let (v, x') = f x in
  Cons (v, fun () -> unfold f x')

(* In our case, we need the init to be a tuple of two elements. *)
let rec fib = unfold (fun (a, b) -> (a, (b, a + b))) (0, 1)

```

The function we pass into unfold should: take the current state, return the first value in tuple as the current value, and the second value as the next state.

For example, to cauculate x^n/n!, we can pass in the following function:

```ocaml
let fact n =
  if n = 0 then 1
  else n * fact (n - 1)

let rec exp_terms x=
  unfold (fun (a, b) -> (a/fact b, (a*x, b+1))) (1, 0)
```

We use the 2 elements to generate x^n/n!

## Lazy Streams

### Lazy Evaluation

- Lazy evaluation delays computation until the value is needed, while strict (or eager) evaluation computes values as soon as they are defined.

```ocaml
(* Example of lazy behavior: In a lazy language, 'x' is only evaluated if y <= 0. *)
let gety x y = if y > 0 then y else x
(* In this case, if y > 0, we could pass something like 1/0 to x without triggering an error. *)
```

- OCaml is strict by default, but the Lazy module allows for lazy evaluation

```ocaml
let x = lazy (1 + 1)  (* 'x' is a lazy value and hasn't been evaluated yet *)
let y =Lazy.force x          (* Forces evaluation of 'x', resulting in 2 ,now y = 2 but x is lazy 2 *)
```

### Creating Lazy Streams

Everything else is quite similar to infinite streams, but we wrap the tail in a Lazy.t.

- Instead of wrapping with a function with 0 arguments, we wrap the tail with lazy().
- When we use the tail, we need to use Lazy.force to evaluate it.

````ocaml
(* Define a lazy stream type with the tail wrapped in Lazy.t *)
type 'a lazystream = Cons of 'a * 'a lazystream Lazy.t

(* Generate an infinite lazy stream starting from 'n' *)
let rec from n = Cons (n, lazy (from (n + 1)))
let nats = from 1
```

### Basic Lazy Stream Operations

```ocaml
(* Take the first 'n' elements from a lazy stream. *)
let rec take n (Cons (h, t)) =
  if n = 0 then []
  else h :: take (n - 1) (Lazy.force t)  (* Note: Use Lazy.force to evaluate the tail *)

(* Extract head and tail functions for lazy streams. *)
let hd (Cons (h, _)) = h
let tl (Cons (_, t)) = Lazy.force t

(* Drop 'n' elements from the lazy stream. *)
let rec drop n (Cons (h, t) as s) =
  if n <= 0 then s
  else drop (n - 1) (Lazy.force t)
````

### Mapping and Filtering

Note that mapping and filtering functions should all return a lazy stream: cons the head and a lazy tail (a lazy stream wrapped in Lazy.t).

```ocaml
(* Map a function over a lazy stream. *)
let rec map f (Cons (h, t)) =
  Cons (f h, lazy (map f (Lazy.force t)))

(* Map a binary function over two lazy streams. *)
let rec map2 f (Cons (h1, t1)) (Cons (h2, t2)) =
  Cons (f h1 h2, lazy (map2 f (Lazy.force t1) (Lazy.force t2)))

(* Define Fibonacci sequence using lazy stream with 'map2'. *)
let sum = map2 (+)
let rec fib = Cons (0, lazy (Cons (1, lazy (sum fib (tl fib)))))
```

### Unfold for Lazy Streams

```ocaml
(* Unfold function for lazy streams *)
let rec unfold f x =
  let (v, x') = f x in
  Cons (v, lazy (unfold f x'))

(* Another Fibonacci sequence using unfold *)
let rec fibs2 = unfold (fun (a, b) -> (a, (b, a + b))) (0, 1)
```

### Laziness and Memoization

Note: Laziness also enables computed values to be stored in memory, so subsequent accesses are faster. This technique is known as memoization.

## Records

OCaml provides multiple ways to define new data types. Take a binary search tree (BST) as an example. We used to define it as a recursive variant type in Lecture 3:

```ocaml
type 'a tree = Leaf | Node of 'a tree * 'a * 'a tree
```

We can also define it as a record type:

```ocaml
type 'a tree = {left: 'a tree; value: 'a; right: 'a tree}
```

For a custom list type, we can define it as:

```ocaml
type 'a mylist = Nil | Cons of 'a * 'a mylist
```

Or as a record type:

```ocaml
type 'a mylist = Nil | Cons of {hd: 'a; tl: 'a mylist}
```

## Extending a Module

The include keyword allows us to extend a module with additional functions or overwrite existing ones.

```ocaml
module ListExt = struct
  include List
  (* Define a function to remove duplicates from a list *)
  let rec dedup l =
    match l with
    | x :: y :: ys -> if x = y then dedup (y :: ys) else x :: dedup (y :: ys)
    | _ -> l
end

(* Now we can use ListExt.dedup to remove duplicates from a list *)
ListExt.dedup [1; 2; 2; 3]
```

Redefining functions in the module will overwrite the original functions, and the new definitions will take precedence.
