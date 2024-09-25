## Variant Types in OCaml

**Variant Type (Sum Type)**

A variant type (also called a sum type) allows defining a type with several alternatives (like enums in C).

### 1. **Enum Type**

We can use variant types to define an enum type with multiple alternatives.

```ocaml
type direction = North | South | East | West

let f = function
  | North -> 1
  | South -> 2
  | East -> 3
  | West -> 4
```

### 2. **Option Type**

The option type is used when a function can fail or may not return a value (also known as a maybe type).

```ocaml
(* 'a maybe is equivalent to option type with Nothing | Just *)
type 'a maybe = Nothing | Just of 'a

let rec find_maybe f l =
  match l with
  | [] -> Nothing
  | x :: _ when f x -> Just x
  | _ :: xs -> find_maybe f xs;;

(* Usage examples *)
find_maybe (fun x -> x = 3) [1; 2; 3; 4; 5];;  (* Just 3 *)
find_maybe (fun x -> x = 6) [1; 2; 3; 4; 5];;  (* Nothing *)

(* Option type using Some and None, note the option' makes it our custom option, instead of the standard one *)
type 'a option' = None | Some of 'a
```

### 3. **Result Type**

The result type has two variants, `Ok` and `Error`, both of which can hold a value. This type is often used to return either a result or an error message.

```ocaml
(* Example of result type with error message *)
let sqrt'' x =
  if x >= 0. then Ok (Stdlib.sqrt x)
  else Error "Negative argument";;

type ('a, 'b) result' = Ok of 'a | Error of 'b
```

### 4. **Expression Type with Constructors**

This is an example of recursive variant types, where the type can be nested within itself.

```ocaml
type expr =
  | Int of int
  | Add of expr * expr
  | Sub of expr * expr
  | Mul of expr * expr

let e = Add(Int 1, Mul(Int 2, Int 3))

let rec eval e =
  match e with
  | Int n -> n
  | Add (e1, e2) -> eval e1 + eval e2
  | Sub (e1, e2) -> eval e1 - eval e2
  | Mul (e1, e2) -> eval e1 * eval e2;;

(** To make the expression type more readable, we can define helper functions. **)
let (++) e1 e2 = Add (e1, e2);;
let (--) e1 e2 = Sub (e1, e2);;
let ( ** ) e1 e2 = Mul (e1, e2);;

(* Example usage *)
let e2 = Int 1 ++ Int 2 ** Int 3;;
```

---

## Type Parameters and Type Constructors

- `'a` is a type variable, e.g., `Some 'a` can hold any type.
- `option'` is a type constructor, which creates a new type from an existing one (e.g., `int option'`).

---

## Defining Custom Types and Functions

### 1. **Custom List Type**

```ocaml
type 'a mylist = Nil | Cons of 'a * 'a mylist

let rec length l =
  match l with
  | Nil -> 0
  | Cons (_, xs) -> 1 + length xs;;
```

### 2. **Using Exceptions**

You can define custom exceptions and raise them in error cases:

```ocaml
exception Empty_list

let hd l =
  match l with
  | Cons (x, _) -> x
  | Nil -> raise Empty_list;;
```

---

## Implementing a Binary Search Tree (BST)

### 1. **BST Structure and Basic Operations**

Tree can be defined as a recursive variant type:

```ocaml
type 'a bstree = Leaf | Node of 'a * 'a bstree * 'a bstree

let bstree_empty = Leaf

let rec bstree_insert z t =
  match t with
  | Leaf -> Node (z, Leaf, Leaf)
  | Node (x, l, r) when z < x -> Node (x, bstree_insert z l, r)
  | Node (x, l, r) when z > x -> Node (x, l, bstree_insert z r)
  | _ -> t
```

### 2. **Traversals and Folding**

You can traverse the tree using a fold function:

```ocaml
let rec fold f acc t =
  match t with
  | Leaf -> acc
  | Node (x, l, r) -> f (fold f acc l) x (fold f acc r)

let inorder t = fold (fun l x r -> l @ [x] @ r) [] t
let preorder t = fold (fun l x r -> [x] @ l @ r) [] t
let postorder t = fold (fun l x r -> l @ r @ [x]) [] t
```

### 3. **Tree Operations**

```ocaml
let rec bstree_largest t =
  match t with
  | Leaf -> failwith "bstree_largest: empty tree"
  | Node (x, _, Leaf) -> x
  | Node (_, _, r) -> bstree_largest r

let rec bstree_delete z t =
  match t with
  | Leaf -> Leaf
  | Node (x, l, r) when z < x -> Node (x, bstree_delete z l, r)
  | Node (x, l, r) when z > x -> Node (x, l, bstree_delete z r)
  | Node (_, Leaf, r) -> r
  | Node (_, l, Leaf) -> l
  | Node (_, l, r) ->
    let max = bstree_largest l in
    Node(max, bstree_delete max l, r)
```

---

## Compiling and Using a Module

To compile and use a module in `utop`:

1. Create an `mli` file with the module signature, we can get the signature from utop
2. Compile using: `ocamlbuild filename.cmo`
3. In `utop`, load the module:
   - `#directory "_build";;`
   - `#load "filename.cmo";;`
4. Use the module with the format `ModuleName.function_name`.
