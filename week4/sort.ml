type record = {
  id:string;
  score:int
}

(** [is_valid_id] checks if the [id] consists of  A+8digits*)
let is_valid_id id = 
  let is_digit ch = ch >= '0' && ch <= '9' in
  let rec check_digits i =
    if i >= String.length id then true
    else if not (is_digit id.[i]) then false
    else check_digits (i+1)
  in
  String.length id = 9 && id.[0] = 'A' && check_digits 1

(** [is_valid_score] checks if the [score] is in the range [0,100]*)
let is_valid_score score = score >= 0 && score <= 100

(** [parse] parses a line of input and returns a record if the input is valid*)
let parse line = 
  try 
    Scanf.sscanf line " %s %s " 
      (fun id score -> 
         let sco = int_of_string score in 
         if is_valid_id id && is_valid_score sco
         then Some {id= id;score=sco} 
         else None)
  with 
  | _ -> None

(** [read_all_lines] reads all lines from a file or stdin and prints the records in descending order of score *)
let read_all_lines ?(filename=None)() = 
  try
    let ic = match filename with
      | Some fname -> open_in fname
      | None -> stdin
    in 
    let rec read_lines acc = 
      try 
        let line = input_line ic in
        read_lines (parse line :: acc)
      with End_of_file -> acc 
    in
    (* filter, sort and print the records *)
    read_lines []
    |> List.filter_map (fun x -> x) 
    |> List.sort (fun x y -> y.score - x.score) 
    |> List.iter (fun row -> Printf.printf "%3d %s\n" row.score row.id);
    close_in ic
  with 
  | _ ->()

(** [main] If there's a command line argument, read from the file, otherwise read from stdin *)
let () =
  if Array.length Sys.argv = 1 then read_all_lines () 
  else read_all_lines ~filename:(Some Sys.argv.(1)) ()