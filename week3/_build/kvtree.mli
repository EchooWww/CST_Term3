type ('k, 'v) t = Leaf | Node of ('k * 'v) * ('k, 'v) t * ('k, 'v) t
val empty : ('a, 'b) t
val is_empty : ('a, 'b) t -> bool
val size : ('a, 'b) t -> int
val insert : 'a -> 'b -> ('a, 'b) t -> ('a, 'b) t
val find : 'a -> ('a, 'b) t -> 'b option
val largest : ('a, 'b) t -> 'a
val smallest : ('a, 'b) t -> 'a
val delete : 'a -> ('a, 'b) t -> ('a, 'b) t 
val of_list : ('a * 'b) list -> ('a, 'b) t