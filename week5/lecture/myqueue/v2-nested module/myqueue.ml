module type QUEUE = sig
  type 'a t
  exception Empty
  val empty : 'a t
  val is_empty : 'a t -> bool
  val enqueue : 'a -> 'a t -> 'a t
  val dequeue : 'a t -> 'a t
  val dequeue_opt : 'a t -> 'a t option
  val front : 'a t -> 'a
  val front_opt : 'a t -> 'a option
end

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
