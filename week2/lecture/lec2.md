# Functions in OCaml

## Partial and Total Functions

OCaml does not have unsigned types like C. To ensure that a parameter is positive, we can either:

1. Handle cases where the parameter is negative.
2. Use a pre-condition to guarantee that the parameter is positive.

## Higher-Order Functions

A higher-order function is one that either:

- Takes another function as an argument, or
- Returns a function as a result.

In OCaml, functions are _first-class citizens_, meaning they can be:

- Passed as arguments to other functions.
- Returned as results.
- Stored in data structures.

> **Note:** In C, functions are not first-class citizens, and we pass pointers to functions instead.

### Example: `take_while`

```ocaml
let rec take_while f l =
  match l with
  | [] -> []
  | x :: xs ->
    if f x then x :: take_while f xs
    else []
```

Usage:

```ocaml
take_while (fun x -> x < 5) [1; 2; 3; 4; 5; 6; 7; 8; 9; 10]
```

### Common Higher-Order Functions

- **`@@`**: Function application operator

  ```ocaml
  let (@@) f x = f x
  (* Usage *)
  f(g(h(x)))  (* Traditional form *)
  f @@ g @@ h @@ x  (* Using @@ *)
  ```

- **`|>`**: Pipe operator (appends the last parameter to the function call)
  ```ocaml
  let (|>) x f = f x
  (* Usage *)
  f(g(h(x)))  (* Traditional form *)
  x |> h |> g |> f  (* Using |> *)
  ```

### Example:

The expression `square(square(square(3)))` can be written as:

```ocaml
3 |> square |> square |> square
```

Or:

```ocaml
square @@ square @@ square @@ 3
```

## Option Type

When a function can fail, we use the option type:

```ocaml
type 'a option = None | Some of 'a
```

This type either has a value (`Some value`) or it doesn’t (`None`).

### Example: `find`

A predicate is a function that returns a boolean value. The `find` function returns a value or `None` if the predicate is not satisfied.

```ocaml
let rec find f l =
   match l with
   | [] -> None
   | x :: xs  when f x -> Some x
   | _ :: xs -> find f xs
```

Usage:

```ocaml
find (fun x -> x = 3) [1; 2; 3; 4; 5]
```

### Default Value with Option

```ocaml
let default a x =
  match x with
  | None -> a
  | Some y -> y
```

### `flip`: Argument Reversal

The `flip` function switches the order of the arguments.

```ocaml
let flip f x y = f y x
```

## `fold_left` and `fold_right`

### `fold_left`

Processes a list from left to right. Tail-recursive.

```ocaml
let rec fold_left f acc l =
  match l with
  | [] -> acc
  | x :: xs -> fold_left f (f acc x) xs
```

Usage example:

```ocaml
[3; 2; 6; 7; 8]
|> List.filter (fun x -> x mod 2 = 0)
|> List.map (fun x -> x * x)
|> fold_left (+) 0
```

### `fold_right`

Processes a list from right to left. Not tail-recursive.

```ocaml
let rec fold_right f l acc =
  match l with
  | [] -> acc
  | x :: xs -> f x (fold_right f xs acc)
```

- **Associative functions**: Both `fold_left` and `fold_right` return the same result when the function is associative.

## Labeled Arguments

In the `ListLabels` library, functions can have labeled arguments. When using labeled arguments, the order of the arguments does not matter.

### Example:

```ocaml
ListLabels.fold_left ~f:(+) ~init:0 [1; 2; 3; 4; 5]
```

We can define our own functions with labeled arguments:

```ocaml
let add ~x ~y = x + y
```

and call it as:

```ocaml
add ~x:3 ~y:4
```

if we define a function as

```ocaml
let f ~a ~b c d = (a, b, c, d)
```

and we call it like :

```ocaml
f ~b:2 ~a:1 3 4
```

then the output will be `(1, 2, 3, 4)` (the unlabeled arguments should be passed in the order they are defined in the function definition).

## optional arguments

```ocaml
(* the default is none *)
let f ?a ?b c = (a, b, c)
(* calling the function *)
f ?a:(Some 1) ?b:(Some 2) 3
(* or *)
f ~a:1 ~b:2 3
(*define the default value*)
let f ?(a=0) ?(b=0) c = (a, b, c)
(* calling the function *)
f ?a:(Some 1) ?b:(Some 2) 3
(* or *)
f ~a:2 1;;
```

Using the tilda is always more readable than using the question mark!

## Type Annotations

```ocaml
  let rec map (f: 'a -> 'b) (l: 'a list): 'b list =
    match l with
    | [] -> []
    | x :: xs -> f x :: map f xs
```

Main use of type annotations is to restrict the type of the function to a specific type.Sometimes the compiler will be more restrictive than you want it to be.

```ocaml
let add (x: 'a') (y: 'a'): = x + y
(* the compiler will still infer the type to be int*)
```

We can have a restrictive version of map function as follows:

```ocaml
let rec map (f: 'a -> 'a) (l: 'a list): 'a list =
    match l with
    | [] -> []
    | x :: xs -> f x :: map f xs
```

Now we cannot call it with functions like `float_of_int` or `string_of_int`, because the return type of the function should be the same as the input type.

## Functions in Standard Library

In the List Module, we have `nth` and `nth_opt` functions. `nth` raises an exception if the index is out of bounds, while `nth_opt` returns an option type.

```ocaml
List.nth [1; 2; 3; 4; 5] 5 (* raises an exception *)
List.nth_opt [1; 2; 3; 4; 5] 6 (* returns None *)
List.nth_opt [1; 2; 3; 4; 5] 2 (* returns Some 3, as the return type is 'a option *)
```

In the String Module, we have similar functions with List like `fold_left`, `fold_right`, `map`, `filter`, etc.

```ocaml
String.fold_left (fun acc c -> if c = 'l' then acc + 1 else acc) 0 "hello hell"
String.fold_right (fun c acc -> if c = 'l' then acc + 1 else acc) "hello hell" 0
```
