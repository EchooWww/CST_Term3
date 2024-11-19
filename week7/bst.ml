type 'a t = L | N of 'a * 'a t * 'a t
let empty = L
let rec insert x t = 
  match t with
  | L -> N (x, L, L)
  | N (y, l, r) -> 
    if x < y then N (y, insert x l, r)
    else if x > y then N (y, l, insert x r)
    else t

let of_list l = List.fold_left (fun t x -> insert x t) empty l

let right t = match t with
  | L -> None
  | N (_, _, r) -> Some r

let right_left t = 
  match t with
  | L -> None
  | N (_, _, r) -> 
    match r with
    | L -> L
    | N (_,l,_) -> Some l

let right_left' t = 
  match right t with
  | None -> None
  | Some l -> left l

let left t = 
  match t with 
  | L -> None
  | N (l, _, _) -> Some l

(* we cannot use  t|> right |> left, because right returns an option instead of a tree*)

let return x = Some x
let bind mx f =
  match mx with
  | None -> None
  | Some x -> f x 

let (>>=) = bind

(* Now we can call it like *)
let t = of_list [1;2;3;4;5]
let () = return t >>= right >>= left
