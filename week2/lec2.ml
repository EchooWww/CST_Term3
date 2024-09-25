(** 
   [insert x l] Insert [x] into [l] to preserve the sorting order
   requires: [l] is sorted in ascending order 
*)
let rec insert x l = 
  match l with
  | [] -> [x]
  | y :: ys -> 
    if x <= y then x :: l
    else y :: insert x ys
(** 
   [insertion_sort l] returns a list with the elements of [l] in ascending order 
*)
let rec insertion_sort l = 
  match l with
  | [] ->[]
  | x :: xs -> 
    insert x (insertion_sort xs)


let rec insert_v2 compare x l = 
  match l with
  | [] -> [x]
  | y :: ys ->
    if compare x y <= 0 then x :: l
    else y :: insert_v2 compare x ys 


let rec insertion_sort_v2 compare l = 
  match l with
  | [] -> []
  | x ::xs -> insert_v2 compare x (insertion_sort_v2 compare xs)


let rec insert_v3 ~cmp x l = 
  match l with
  | [] -> [x]
  | y :: ys ->
    if cmp x y <= 0 then x :: l
    else y :: insert_v3 ~cmp x ys 

(* call it by insertion_sort_v3 ~cmp:compare [1;2;3;4;5]*)

let rec insertion_sort_v3 ~cmp l = 
  match l with
  | [] -> []
  | x ::xs -> insert_v3 ~cmp x (insertion_sort_v3 ~cmp xs)



(**
    [take n l] returns a list consisting of the first [n] elements of [l]
    if [n < 0], returns an empty list 
*)
let rec take n l = 
  if n <= 0 then []
  else
    match l with
    | [] -> []
    | x :: xs -> x :: take (n-1) xs


let rec take_while f l = 
  match l with
  | [] -> []
  | x :: xs -> 
    if f x then x :: take_while f xs
    else []

let rec find f l = 
  match l with
  | [] -> None
  | x :: xs  when f x -> Some x 
  | _ :: xs -> find f xs

(**
   standard list functions. pre-defined in the module
*)

let rec fold_left f acc l =
  match l with
  | [] -> acc
  | x :: xs -> fold_left f (f acc x) xs

let rec fold_right f l acc = 
  match l with 
  | [] -> acc
  | x :: xs -> f x (fold_right f xs acc)


let rec map f  = function 
  | [] -> []
  | x :: xs -> f x :: map f xs

let rec filter f l =
  match l with
  | [] -> []
  | x :: xs when f x -> x :: filter f xs
  | _ :: xs -> filter f xs