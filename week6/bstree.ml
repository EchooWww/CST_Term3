type 'a t = L | N of {v: 'a; l: 'a t; r:'a t}

let empty = L
let is_empty t = t = L

let rec size t = 
  match t with
  | L -> 0
  | N {l;r}  -> 1 + size l + size r

let rec insert x t = 
  match t with 
  | L -> N {v=x; l=L; r=L}
  | N {v; l; r} -> 
    if x < v then N {v; l=insert x l; r}
    else if x > v then N {v; l; r=insert x r}
    else t

let of_list l =
  List.fold_right insert l empty

let rec mem x t = 
  match t with
  | L -> false
  | N {v; l; r} -> 
    if x < v then mem x l
    else if x > v then mem x r
    else true

let rec max t =
  match t with 
  | L -> failwith "empty"
  | N {v; r = L} -> v
  | N {r} -> max r

let rec delete x t = 
  match t with
  | L -> L
  | N {v; l; r} 
    when x < v -> N{v; l = delete x l;r}
  | N {v; l; r} 
    when x > v -> N{v; l;r = delete x r}
  | N {l = L; r} -> r
  | N {r = L; l} -> l
  | N {l;r} -> 
    let m = max l in
    N {v = m; l = delete m l; r}

let rec stringify converter t = 
  match t with 
  | L -> "*"
  | N {v; l; r} -> 
    "(" ^ (converter v) ^ " " ^ (stringify converter l) ^ " " ^ (stringify converter r) ^ ")"



