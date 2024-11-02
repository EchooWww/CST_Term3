## Lecture 5: Ocaml Modules

### Load And Use Files as Modules

The file name is the module name!

Implement `myqueue.ml`:

```ocaml
type 'a t = 'a list

exception Empty

let empty = []

let is_empty q = q = []

let enqueue x q = q @ [x]

let dequeue = function
  | [] -> raise Empty
  | _ :: xs -> xs

let dequeue_opt = function
  | [] -> None
  | _ :: xs -> Some xs

let front = function
  | [] -> raise Empty
  | x :: _ -> x

let front_opt = function
  | [] -> None
  | x :: _ -> Some x
```

We need to load the module in the toplevel:

```ocaml
(*first build the module *)
ocamlbuild myqueue.cmo
cd _build
(*then go to utop*)
utop
(*then load the module*)
#load "myqueue.cmo";;
```

3 ways we can use the module after loading it

1. Use the module name as a prefix (Note the module name is capitalized automatically), for example `Myqueue.enqueue 1 Myqueue.empty`
2. Use a parentheses to group the module name and the function, for example ` Myqueue.(empty|>enqueue 1 |> enqueue 2);;`
3. Open the module, then we can use the functions without the prefix, for example `open Myqueue;; enqueue 1 empty`
4. open the module as a local variable, for example `let open Myqueue in enqueue 1 empty`

### Creating Nested Modules

Other than using a file as module, we can also have "nested modules", defined with `module M = struct ... end`. For example, we can define a module `Queue` in the file `myqueue.ml`:

```ocaml
module ListQueue = struct
  type 'a t = 'a list

  exception Empty

  let empty = []

  let is_empty q = q =[]

  let enqueue x q = q @ [x]

  let dequeue = function
    | [] -> raise Empty
    | _::xs -> xs

  let dequeue_opt = function
    | [] -> None
    | _ :: xs -> Some xs

  let front = function
    | [] -> raise Empty
    | x::_ -> x

  let front_opt = function
    | [] -> None
    | x ::_ -> Some x
  let to_list q = q

end
```

### Module Type

`mli` files are interface files, which defines what is visible to the outside world. To maintain the encapsulation, we usually use abstract types in the mli file. For example, we can define the `myqueue.mli` file as:

```ocaml
module type QUEUE = sig
  type 'a t (* not type 'a t = 'a list as we want to 1. hide the implementation 2. make the type reusable for different implementations*)
  exception Empty
  val empty : 'a t
  val is_empty : 'a t -> bool
  val enqueue : 'a -> 'a t -> 'a t
  val dequeue : 'a t -> 'a t
  val dequeue_opt : 'a t -> 'a t option
  val front : 'a t -> 'a
  val front_opt : 'a t -> 'a option
  val to_list : 'a t -> 'a list (* in our implementation, we can just return the list as the queue is implemented as a list. But the abstraction gives us the possibility to implement it differently*)
end
```

There are some rules for module types:

1. a module can have a module type definition(struct...end) as well as a module (module M:sig...end) definition
2. A module type definition cannot contain a module definition, but a module definition can contain a module type definition
3. If M:S (which means M "implements" the type S), then:
   - Everything specified in S must be implemented in M, this includes module type definitions
   - Everything implemented in M does not have to be specified in S, but if they are not in S, they cannot be accessed from outside the module

As a result, if we have a module type definition in the mli file, we must include the module type definition in the ml file too.

With the module type, we can implement the queue with different data structures. For example, we can implement the queue with a double list as long as it complies with the signature.

```ocaml

module TwoListQueue = struct
  type 'a t = 'a list * 'a list
  exception Empty
  let empty = ([], [])
  let is_empty (l,_) = l = [] (* queue is empty only if first list is empty*)

  let enqueue x q =
    match q with
    | [], l -> ([x], l)
    | l1, l2 -> (l1, x::l2) (*A lot more efficient to add an element in the front instead of at the end*)
  let dequeue q =
    match q with
    | [], _ -> raise Empty
    | [_],l->List.rev l, []
    | x::xs, l -> (xs, l) (* we don't necessarily need the parentheses for tuples*)

  let dequeue_opt q =
    match q with
    | [], _ -> None
    | [_], l -> Some (List.rev l, [])
    | x::xs, l -> Some (xs, l)

  let front = function
    | [], _ -> raise Empty
    | x::xs , _ -> x

  let front_opt = function
    | [], _ -> None
    | x::xs, _ -> Some x

  let to_list (l1, l2) = l1 @ List.rev l2
end

```

In the mli file , we need to include the module type definition for the new module:

```ocaml
module TwoListQueue:QUEUE
```

### Parameterized Modules: Functors

We can also make modules parameterized by passing in a module as an argument. This is called a "functor".

For example, if we wanna implement a BST differently, i.e., use a different comparison function to order the nodes, we can do that by passing the comparison function to each function in the module. However, this is tedious and error-prone: what if the user forgets to pass the same comparison for insert and delete? To address this, we can use functors.

For example, we can define a module type for the comparison function:

```ocaml
module type OrderedType = sig
  type t
  val compare: t->t->int
end
```

Inside the ml file, we can create a new parameterized module `Make`, which takes a module `Ord` of type `OrderedType` (i.e., a type definition with a type `t` and a comparison function `compare`) as an argument:

```ocaml
module Make(Ord:OrderedType) = struct
  type elt = Ord.t
  type t = Leaf | Node of elt * t * t
  let empty = Leaf
  let is_empty t = t = Leaf

  (*The comparison function is now Ord.compare*)
  let rec insert x t =
    match t with
    | Leaf -> Node (x, Leaf, Leaf)
    | Node (x', l, r) when Ord.compare x x' < 0 ->
      Node (x', insert x l, r)
    | Node (x', l, r) when Ord.compare x x' > 0 ->
      Node (x', l, insert x r)
    |_ -> t
...
end
```

In the mli file, we need to include the module type definition for the new module:

```ocaml
module type S = sig
  type elt
  type t
  val empty : t
  val is_empty : t -> bool
  val insert : elt -> t -> t
  val of_list : elt list -> t
  val delete : elt -> t -> t
  val mem : elt -> t -> bool
end

module Make(Ord:OrderedType):S
```

Then we can use the functor to create a new module with a different comparison function, for example, the default comparison function for integers:

```ocaml
module IntOrder : Bstree.OrderedType = struct
type t = int
let compare = compare
end;;
```

### Use Functors for Library Modules

We can also use functors to create a new module from a library module. For example, we can use the `Map.Make` functor to create a new module with a different key types:

```ocaml
module M = Map.Make(String)
```

Then we can use `M` as a module with string keys.

```ocaml
let m = M.(empty|>add "hello" 5 |> add "hi" 2);;
```

The module is still abstract, so we cannot see the internal details of m (`val m : int M.t = <abstr>`), but we can use the functions provided by the module. For example, we can use `M.find` to find the value of a key:

```ocaml
M.find "hello" m;;
(* This will return 5*)
```

Or we can use the function `M.bindings` to get the content of the map as a list of tuples:

```ocaml
M.bindings m;;
(* This will return [("hello", 5); ("hi", 2)]*)
```

More details in `map.ml`.
