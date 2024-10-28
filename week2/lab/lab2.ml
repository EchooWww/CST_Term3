(** [drop_while f lst] returns a list with elements in [lst] with leading elements satisfying [f] dropped *)
let rec drop_while f lst = 
  match lst with
  | x::xs ->
    if f x then drop_while f xs
    else lst
  | _ -> []

(** [zip_with f lst1 lst2] returns a list with elements obtained by applying function [f] to corresponding elements in [lst1] and [lst2]
 *  if [lst1] and [lst2] have different lengths, the extra elements in the longer list are ignored *)  
let zip_with f lst1 lst2 = 
  let rec zip_with_aux f lst1 lst2 acc = 
    match lst1, lst2 with
    | x::xs, y::ys -> zip_with_aux f xs ys ((f x y)::acc)
    | _, _ -> acc
  in List.rev(zip_with_aux f lst1 lst2 [])

(** [mapi f lst] returns a list with elements obtained by applying function [f] to each element and its index in [lst] *)
let mapi f lst = 
  let rec mapi_aux f lst acc idx = 
    match lst with
    | x::xs -> mapi_aux f xs (f idx x ::acc) (idx + 1)
    | _ -> acc
  in List.rev(mapi_aux f lst [] 0)


(** [every n lst] returns a list consisting every [n]th element in [lst] *)
let rec every n lst = 
  mapi (fun i x -> (i,x)) lst
  |> List.filter (fun (i,_)-> (i+1) mod n = 0)
  |> List.map (fun (_, x)->x)

(** [dedup lst] returns a list with elements obtained from removing consecutive duplicate values in [lst], implemented with fold_left *)
let dedup lst = 
  let rec dedup_aux acc elem = 
    match acc with
    | x::xs-> if elem = x then acc
      else elem::acc
    | _ -> [elem]
  in List.rev(List.fold_left dedup_aux [] lst)


(** [group lst] returns a list consists of sublists, which is obtained by grouping consecutive duplicate values in [lst], implemented with fold_left *)
let group lst = 
  let rec group_aux acc elem =
    match acc with
    | (x::xs)::lsts -> if elem = x then (elem::x::xs)::lsts
      else [elem]::acc
    | _->[[elem]]
  in List.rev(List.fold_left group_aux [] lst)

(** [frequencies lst] returns a list of pairs, each pair consists of a value and its frequency in [lst] *)
let frequencies lst = 
  List.sort compare lst
  |> group 
  |> List.map(fun lst -> (List.hd lst, List.length lst))

let test_drop_while() = 
  assert(drop_while (fun x -> x < 5) [1;2;3;4;5;6;7;8;9] = [5;6;7;8;9]);
  assert(drop_while (fun x -> x < 5) [6;7;8;9] = [6;7;8;9]);
  assert(drop_while (fun x -> x < 5) [1;2;3;4] = []);
  assert(drop_while (fun x -> x < 5) [] = [])

let test_zip_with() =
  assert(zip_with (fun x y -> x + y) [1;2;3] [4;5;6] = [5;7;9]);
  assert(zip_with (fun x y -> x * y) [1;2;3] [4;5;6] = [4;10;18]);
  assert(zip_with (fun x y -> x + y) [1;2;3] [4;5] = [5;7]);
  assert(zip_with (fun x y -> x + y) [1;2] [4;5;6] = [5;7])

let test_mapi() =
  assert(mapi (fun i x -> i + x) [1;2;3] = [1;3;5]);
  assert(mapi (fun i x -> i * x) [1;2;3] = [0;2;6]);
  assert(mapi (fun i x -> i + x) [] = []);
  assert(mapi (fun i x -> i + x) [1] = [1])

let test_every() =
  assert(every 3 [1;2;3;4;5;6;7;8;9] = [3;6;9]);
  assert(every 2 [1;2;3;4;5;6;7;8;9] = [2;4;6;8]);
  assert(every 1 [1;2;3;4;5;6;7;8;9] = [1;2;3;4;5;6;7;8;9]);
  assert(every 4 [1;2;3;4;5;6;7;8;9] = [4;8])

let test_dedup() =
  assert(dedup [1;2;2;3;3;3;4;5;5;5;5] = [1;2;3;4;5]);
  assert(dedup [1;2;3;4;5] = [1;2;3;4;5]);
  assert(dedup [1;1;1;1;1] = [1]);
  assert(dedup [] = [])

let test_group() =
  assert(group [1;2;2;3;3;3;4;5;5;5;5] = [[1];[2;2];[3;3;3];[4];[5;5;5;5]]);
  assert(group [1;2;3;4;5] = [[1];[2];[3];[4];[5]]);
  assert(group [1;1;1;1;1] = [[1;1;1;1;1]]);
  assert(group [2;1;1;3;4;5;5;5;5] = [[2];[1;1];[3];[4];[5;5;5;5]]);
  assert(group [] = [])

let test_frequencies() = 
  assert(frequencies [1;2;2;3;3;3;4;5;5;5;5] = [(1,1);(2,2);(3,3);(4,1);(5,4)]);
  assert(frequencies [1;2;3;4;5] = [(1,1);(2,1);(3,1);(4,1);(5,1)]);
  assert(frequencies [2;1;3;1;4;5;5;5;5] = [(1,2);(2,1);(3,1);(4,1);(5,4)]);
  assert(frequencies [23;12;15;12;45;15;13;45;15;12;15;15] = [(12,3);(13,1);(15,5);(23,1);(45,2)])

let run_all_tests() = 
  test_drop_while();
  test_zip_with();
  test_mapi();
  test_every();
  test_dedup();
  test_group();
  test_frequencies()