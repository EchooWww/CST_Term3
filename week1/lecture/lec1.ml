(** [length l] returns the length (number of elements) of the list [l] *)
let rec length l = 
	match l with 
	| [] -> 0
	| _ :: xs -> 1 + length xs
	
let rec length_aux acc l = 
	match l with
	| [] -> acc
	| _ :: xs -> length_aux(acc + 1) xs
	
let length_tr l = length_aux 0 l

let reverse l =
	let rec aux l acc = 
		match l with
		| [] -> acc
		| x :: xs -> aux xs (x :: acc)
	in
	aux l []

(* if we don't have the empty prentheses for this function, it will be evaluated immediately when the file is loaded, we use the parentheses *)
let test_reverse() = 
	assert(reverse [1;2;3] = [3;2;1])

	
let rec equal l1 l2 = 
  match l1, l2 with
  | [], [] -> true
  | _, [] | [], _ -> false
  | x1::xs1, x2::xs2 -> 
  x1 = x2 && equal xs1 xs2
   
(** can take a equal function like:
	equals (=)  [1;2;3] [1;2;3];;
*)

let rec equals eq l1 l2 = 
  match l1, l2 with
  | [], [] -> true
  | _, [] | [], _ -> false
  | x1::xs1, x2::xs2 -> 
    eq x1 x2 && equals eq xs1 xs2 
    
(** [alternate l] returns a list consisting of every other element of [l], starting from the first element *)
 
let rec alternate = function
 	| [] -> []
 	| [x] -> [x]
 	| x1::x2::xs->x1::alternate xs  
 	
let rec alternate' l = 
	| x1::x2::xs->x1::alternate xs  
	| _ -> l
