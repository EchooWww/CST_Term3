Infinite stream: we wanna defer the evaluation.

So we can define it like

```ocaml
type 'a infstream = Cons of 'a * (unit -> 'a stream) (*at least one element*)
(* start with a value and a function that returns the rest of the stream *)
let rec from n = Cons (n, fun () -> from (n+1))

let nats = from 1

let hd (Cons (h, _)) = h
let tl (Cons (_, t)) = t ()

let rec take n (Cons (h, t)) =
  if n = 0 then []
  else h :: take (n-1) (t ()) (* t is a function but not a stream, we need to call it to get the next stream *)

let rec take n s =
  if n <= 0 then []
  else hd s :: take (n-1) (tl s)

let rec drop n (Cons (h, t) as s) =
  if n = 0 then s
  else drop (n-1) (t ())

let rec map f (Cons (h, t)) =
  Cons (f h, fun () -> map f (t ()))

let rec map2 f (Cons(h1, t1)) (Cons (h2, t2)) =
  Cons (f h1 h2, fun () -> map2 f (t1 ()) (t2 ()))

let rec fib = Cons (0, fun () -> Cons (1, fun () -> map2 (+) fib (tl fib)))

let sum = map2 (+)

let rec fibs = Cons (0, fun () -> Cons (1, fun () -> sum fibs (tl fibs)))

let rec unfold f x =
  let (v, x') = f x in
  Cons (v, fun () -> unfold f x')

let fibs = unfold (fun (a, b) -> (a, (b, a+b))) (0, 1)
```

Unfold: keeps looping through the stream, and

Strict & Lazy:

- Strict(Eager): evaluate the value when it's defined;
- Lazy: evaluate the value when it's needed.
- e.g.,

```ocaml
let gety x y = if y > 0 then y else x
(* For a lazy language x gets evaluated only when y <=0, so if y>0, we can pass 1/0 to x *)

```

ocaml is not a lazy language by default, but we can use the lazy module to make it lazy.

```ocaml
let x = lazy (1+1)
(* x is a lazy value, it's not evaluated yet, it is int lazy_t <lazy> *)
Lazy.force x
(* int lazy_t = lazy 2 *)

```

A lazy stream!

```ocaml
type 'a lazystream = Cons of 'a * 'a lazystream Lazy.t (* lazystream wrapped in Lazy.t *)
let rec from n = Cons (n, lazy (from (n+1)));;
let nats = from 1;;
let rec take n (Cons (h, t)) =
  if n = 0 then []
  else h :: take (n-1) (Lazy.force t)

let hd (Cons (h, _)) = h

let tl (Cons (_, t)) = Lazy.force t

let rec drop n (Cons (h,t) as s)=
  if n <= 0 then s
  else drop (n-1) (Lazy.force t)

let rec map f (Cons (h, t)) =
  Cons (f h, lazy (map f (Lazy.force t)))

let rec map2 f (Cons (h1, t1)) (Cons (h2, t2)) =

let sum = map2 (+)

let rec fib = Cons (0, lazy (Cons (1, lazy (sum fib (tl fib)))))

let rec unfold f x =
  let (v, x') = f x in
  Cons (v, lazy (unfold f x'))

let rec fibs2 = unfold (fun (a, b) -> (a, (b, a+b))) (0, 1)

```

The laziness also enables the computed values to be stored in the memory, so it would be faster to access the values later.

### Records

Many ways we can define a new data type,

```ocaml

type 'a bstree = Leaf | Node of 'a bstree * 'a * 'a bstree

type 'a bstree = Leaf | Node of {v: 'a; l: 'a bstree; r: 'a bstree}

type 'a mylist = Nil | Cons of 'a * 'a mylist

type 'a mylist = Nil | Cons of {hd: 'a; tl: 'a mylist}
(*or*)
type 'a mylist = Nil | Cons of {v: 'a; next: 'a mylist}
```

### Extend module with a function

The include keyword can be used to extend a module with a function.

```ocaml
module ListExt = struct
  include List
  let rec dedup l =
  match l with
  | x::y::ys ->if x = y then dedup (y::ys) else x::dedup (y::ys)
  | _ -> l
end;;
```

now we can use ListExt.dedup to dedup a list.
We can also overwrite the function in the module. The latter one will be used.
