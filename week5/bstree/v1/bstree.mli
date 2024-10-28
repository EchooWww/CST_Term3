type 'a bstree 
val empty : 'a bstree
val is_empty : 'a bstree -> bool 
val size : 'a bstree -> int 
val height : 'a bstree -> int 
val insert : ('a -> 'a -> int) -> 'a -> 'a bstree -> 'a bstree 
val of_list : ('a -> 'a -> int) -> 'a list -> 'a bstree 
val largest : 'a bstree -> 'a 
val delete : ('a -> 'a -> int) -> 'a -> 'a bstree -> 'a bstree 
val mem : ('a -> 'b -> int) -> 'a -> 'b bstree -> bool 