(** [type ('k, 'v) t] a recursive type definition for KV tree: a binary search tree
    with keys of type ['k] and values of type ['v]. *)
type ('k, 'v) t = Leaf | Node of ('k * 'v) * ('k, 'v) t * ('k, 'v) t

(** [empty] an empty KV tree. *)
let empty = Leaf

(** [is_empty] returns true if the KV tree is empty, and false otherwise. *)
let is_empty t = t = Leaf

(** [size] returns the number of nodes in the KV tree [t]. *)
let rec size t =
  match t with
  | Leaf -> 0
  | Node (_, l, r) -> 1 + size l + size r

(** [insert] returns a new KV tree with key [k] and value [v] inserted into [t]. If the key [k] already exists in [t], update the node to hold value [v]*)
let rec insert k v t =
  match t with
  | Leaf -> Node ((k,v), Leaf, Leaf)
  | Node ((xkey, xval), l, r) when k < xkey -> Node ((xkey, xval), insert k v l, r)
  | Node ((xkey, xval), l, r) when k > xkey -> Node ((xkey, xval), l, insert k v r)
  | Node ((xkey, xval), l, r) when k = xkey -> Node ((xkey, v), l, r)
  | _ -> t

(** [find] returns the value associated with key [k] in [t], or None if [k] is not in [t]. *)
let rec find k t =
  match t with
  | Node ((xkey, xval), l, r) when k < xkey -> find k l
  | Node ((xkey, xval), l, r) when k > xkey -> find k r
  | Node ((xkey, xval), l, r) when k = xkey -> Some(xval)
  | _ -> None

(** [largest] returns the largest key in [t]. *)
let rec largest t =
  match t with 
  | Leaf -> failwith "largest: empty tree"
  | Node ((k, v),_, Leaf) -> (k,v)
  | Node(_, _, r)  -> largest r

(** [smallest] returns the smallest key in [t]. *)
let rec smallest t =
  match t with
  | Leaf -> failwith "smallest: empty tree"
  | Node ((k, v), Leaf, _) -> (k,v)
  | Node (_, l, _) -> smallest l

(** [delete] returns a new KV tree with key [k] removed from [t]. *)
(** [delete] returns a new KV tree with key [k] removed from [t]. *)
let rec delete k t =
  match t with
  | Leaf -> Leaf
  | Node ((xkey, xval), l, r) when k < xkey ->
    Node((xkey, xval), delete k l, r)
  | Node ((xkey, xval), l, r) when k > xkey ->
    Node ((xkey, xval), l, delete k r)
  | Node (_, Leaf, r) -> r
  | Node (_, l, Leaf) -> l
  | Node ((xkey, xval), l, r) ->
    let (largest_key_left, val_with_largest_key) = largest l in
    let (smallest_key_right, val_with_smallest_key) = smallest r in
    if largest_key_left > smallest_key_right then
      Node ((largest_key_left, val_with_largest_key), delete largest_key_left l, r)
    else
      Node ((smallest_key_right, val_with_smallest_key), l, delete smallest_key_right r)


(** [of_list] returns a new KV tree with key-value pairs from [lst]. *)
let of_list lst =
  List.fold_left (fun acc (k,v) -> insert k v acc) empty lst