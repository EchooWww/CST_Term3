(**[digits num] takes a non-negative integer [num] and returns a list of all the digits of [num]*)
let digits num = 
  let rec digits_aux num acc = 
    match num with
    | 0  when acc = []-> 0::[]
    | 0 -> acc
    | _ -> digits_aux (num / 10) (num mod 10::acc)
  in digits_aux num []

(**[int_of_digits lst] takes a list of digits [lst] and returns the integer that the digits represent. Leading zeros should be ignored *)
let int_of_digits lst = 
  List.fold_left (fun acc elem -> acc * 10 + elem) 0 lst

(** [list_of_string] takes a string [str] and returns a list of all characters in [str]*)
let list_of_string str = String.fold_right (fun char acc -> char::acc) str []

(** [is_permutation str1 str2] takes two strings [str1] and [str2] and returns true if [str1] is a permutation of [str2], and false otherwise*)
let is_permutation str1 str2 = 
  List.equal 
    (=) 
    (List.sort compare (list_of_string str1))  
    (List.sort compare (list_of_string str2)) 
