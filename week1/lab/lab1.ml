(** [drop n l] returns the list [l] with first [n] elements dropped *)
let rec drop n l = 
	if  n <= 0 then l
	else
		match l with
		| [] -> l
		| _::xs -> drop (n-1) xs 

(** [zip lst1 lst2] returns a list consisting of pairs of corresponding elements of [lst1] and [lst2], non-tail-recursive version *)
let rec zip lst1 lst2 = 
	match lst1, lst2 with
	| x1::x1s, x2::x2s -> (x1, x2)::(zip x1s x2s)
	| _, _ -> []
	
(** [zip_tr lst1 l2] returns a list consisting of pairs of corresponding elements of [lst1] and [lst2], tail-recursive version *)
let zip_tr lst1 lst2 = 
	let rec zip_aux lst1 lst2 acc = 
		match lst1, lst2 with
		| x1::x1s, x2::x2s -> zip_aux x1s x2s ((x1, x2)::acc)
		| _, _ -> acc
	in
	List.rev (zip_aux lst1 lst2 [])
	
(** [unzip lst] takes a list of pairs [lst] return 2 lists, one for the 1st element in each pair, one for second, non-tail-recursive version *) 
let rec unzip lst = 
	match lst with
	| [] -> ([],[])
	| (x1, x2)::xs -> 
		let (res1, res2) = unzip xs in (x1::res1, x2::res2)
	
(** [unzip_tr lst] takes a list of pairs [lst] return 2 lists, one for the 1st element in each pair, one for second,tail-recursive version *) 
let unzip_tr lst = 
	let rec unzip_aux lst acc1 acc2 = 
		match lst with
		| [] -> (List.rev acc1, List.rev acc2)
		| (x1, x2)::xs -> unzip_aux xs (x1::acc1) (x2::acc2)
	in
	unzip_aux lst [] []

(** [dedup lst] takes a list [lst], and returns a new list with the consecutive duplicate elements being collapsed into one eleement, non-tail-recursive version *)
let rec dedup lst = 
	match lst with
	| x1::x2::xs -> if x1 = x2 then dedup(x2::xs) else x1::dedup(x2::xs)
	| _ -> lst
	
(** [dedup lst] takes a list [lst], and returns a new list with the consecutive duplicate elements being collapsed into one eleement, tail-recursive version *)
let dedup_tr lst =
	let rec dedup_aux lst acc =
		match lst with
		| [] -> acc
		| x::[] -> x::acc
		| x1::x2::xs ->
			if x1 = x2 then dedup_aux (x2::xs) acc
			else dedup_aux (x2::xs) (x1::acc)
	in
  List.rev (dedup_aux lst [])	


let test_drop() = 
	assert(drop 3 [4;2;6;7;6;8;1] = [7;6;8;1]);
	assert(drop (-1) [3;2;7] = [3;2;7]);
	assert(drop 4 [3;2;7] = [])

let test_zip()=
	assert(zip [1;2;3;4] ['a'; 'b'; 'c'; 'd'] = [(1, 'a'); (2, 'b'); (3, 'c');(4, 'd')]);
	assert(zip [5;6;7] ['e'; 'f'; 'g'; 'i'] = [(5, 'e'); (6, 'f'); (7, 'g')]);
	assert(zip [8;9] ['h'] = [(8, 'h')]);
	assert(zip[] [] = []);
	assert (zip [1;2;3] [4;5] = [(1, 4); (2, 5)])
	
let test_zip_tr()=
	assert(zip_tr [1;2;3;4] ['a'; 'b'; 'c'; 'd'] = [(1, 'a'); (2, 'b'); (3, 'c');(4, 'd')]);	
	assert(zip_tr [5;6;7] ['e'; 'f'; 'g'; 'i'] = [(5, 'e'); (6, 'f'); (7, 'g')]);
	assert(zip_tr [8;9] ['h'] = [(8, 'h')]);
	assert(zip_tr[] [] = []);
	assert (zip_tr [1;2;3] [4;5] = [(1, 4); (2, 5)])

let test_unzip() = 
	assert(unzip [(1, 'a'); (2, 'b'); (3, 'c');(4, 'd')] = ([1;2;3;4], ['a'; 'b'; 'c'; 'd']));
	assert(unzip [(5, 'e'); (6, 'f'); (7, 'g')] = ([5;6;7], ['e'; 'f'; 'g']));
	assert(unzip [(8, 'h')] = ([8], ['h']));
	assert(unzip [] = ([], []));
	assert(unzip [(1, 2)] = ([1], [2]))

let test_unzip_tr() = 
	assert(unzip_tr [(1, 'a'); (2, 'b'); (3, 'c');(4, 'd')] = ([1;2;3;4], ['a'; 'b'; 'c'; 'd']));
	assert(unzip_tr [(5, 'e'); (6, 'f'); (7, 'g')] = ([5;6;7], ['e'; 'f'; 'g']));
	assert(unzip_tr [(8, 'h')] = ([8], ['h']))

let test_dedup() = 
	assert(dedup [1;1;2;3;4] = [1;2;3;4]);
	assert(dedup [1;1;2;3;3;3;2;1;1] = [1;2;3;2;1]);
	assert(dedup [1] = [1])

let test_dedup_tr() =
	assert(dedup_tr [1;1;2;3;4] = [1;2;3;4]);
	assert(dedup_tr [1;1;2;3;3;3;2;1;1] = [1;2;3;2;1]);
	assert(dedup_tr [1] = [1])
	

let run_all_tests() = 
	test_drop();
	test_zip();
	test_zip_tr();
	test_unzip();
	test_unzip_tr();
	test_dedup();
	test_dedup_tr()	